package cjc.weixinmp;

import java.io.IOException;

import cjc.weixinmp.bean.CustomMenu;
import cjc.weixinmp.bean.CustomMenu.CustomButton;
import cjc.weixinmp.bean.GlobalError;

/**
 * 自定义菜单接口
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class CustomerMenuService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected CustomerMenuService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 重新定义整个自定义菜单，包括一级和二级
     * @param button 自定义菜单数据
     * @return WeixinException 如发生错误
     */
    public void updateMenu(CustomButton button) throws WeixinException {
        controller.logInfo("更新自定义菜单：" + button);
        String url = controller.getProperty("menu_create_url", null, false);
        try {
            GlobalError error = controller.postWithJson(url, button, GlobalError.class, "addMenu");
            controller.logInfo("更新自定义菜单结果：" + error);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_AddMenuError", e.getMessage(), e);
        }
    }

    /**
     * 获取现有的自定义菜单
     * @return 自定义菜单数据
     * @return WeixinException 如发生错误
     */
    public CustomMenu getMenu() throws WeixinException {
        controller.logInfo("查询自定义菜单");
        String url = controller.getProperty("menu_query_url", null, false);
        try {
            CustomMenu menu = controller.post(url, null, CustomMenu.class, "getMenu");
            controller.logInfo("查询自定义菜单：" + menu);
            return menu;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename() + "_QueryMenuError", e.getLogContent());
            }
            return null;
        }
    }

    /***
     * 删除整个自定义菜单
     * @return WeixinException 如发生错误
     */
    public void deleteMenu() throws WeixinException {
        controller.logInfo("删除自定义菜单");
        String url = controller.getProperty("menu_delete_url", null, false);
        try {
            GlobalError error = controller.post(url, null, GlobalError.class, "deleteMenu");
            controller.logInfo("删除自定义菜单：" + error);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename() + "_DeleteMenuError", e.getLogContent());
            }
        }
    }

}
