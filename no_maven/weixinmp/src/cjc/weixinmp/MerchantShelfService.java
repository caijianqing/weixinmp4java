package cjc.weixinmp;

import java.io.IOException;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.ShelfRequest;
import cjc.weixinmp.merchant.builder.ShelfBuilder;

/**
 * <pre>
 * 货架管理接口
 * @author jianqing.cai@qq.com, 2014年6月9日 下午8:56:13, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class MerchantShelfService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MerchantShelfService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 增加货架
     * 
     * @param shelf 货架信息，由{@link ShelfBuilder}构造
     * @return 新货架的ID
     * @throws WeixinException
     */
    public int addShelf(ShelfRequest shelf) throws WeixinException {
        String url = controller.getProperty("merchant_shelf_add_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, shelf, ShelfRequest.Response.class, "addShelf");
            controller.logInfo("增加货架结果：" + result);
            // 返回
            ShelfRequest.Response response = (ShelfRequest.Response) result;
            return response.shelfId;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_addShelfError", e.getMessage(), e);
        }
    }

    /**
     * 删除货架
     * 
     * @param shelfId 货架ID
     * @throws WeixinException
     */
    public void deleteShelf(int shelfId) throws WeixinException {
        String url = controller.getProperty("merchant_shelf_del_url", null, false);
        try {
            ShelfRequest shelf = new ShelfRequest();
            shelf.shelfId = shelfId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, shelf, ShelfRequest.Response.class, "deleteShelf");
            controller.logInfo("删除货架结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_deleteShelfError", e.getMessage(), e);
        }
    }

    /**
     * 修改货架
     * 
     * @param shelf 货架信息，由{@link ShelfBuilder}构造
     * @throws WeixinException
     */
    public void updateShelf(ShelfRequest shelf) throws WeixinException {
        String url = controller.getProperty("merchant_shelf_mod_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, shelf, ShelfRequest.Response.class, "updateShelf");
            controller.logInfo("修改货架结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_updateShelfError", e.getMessage(), e);
        }
    }

    /**
     * 获取所有货架
     * 
     * @return
     * @throws WeixinException
     */
    public List<ShelfRequest> getAllShelf() throws WeixinException {
        String url = controller.getProperty("merchant_shelf_getall_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, null, ShelfRequest.Response.class, "getAllShelf");
            controller.logInfo("删除货架结果：" + result);
            ShelfRequest.Response response = (ShelfRequest.Response) result;
            return response.shelveList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getAllShelfError", e.getMessage(), e);
        }
    }

    /**
     * 根据货架ID获取货架信息
     * 
     * @param shelfId 货架ID
     * @return
     * @throws WeixinException
     */
    public ShelfRequest getSelfById(int shelfId) throws WeixinException {
        String url = controller.getProperty("merchant_shelf_getbyid_url", null, false);
        try {
            ShelfRequest shelf = new ShelfRequest();
            shelf.shelfId = shelfId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, shelf, ShelfRequest.Response.class, "getSelfById");
            controller.logInfo("删除货架结果：" + result);
            ShelfRequest.Response response = (ShelfRequest.Response) result;
            ShelfRequest shelf2 = new ShelfRequest();
            shelf2.shelfId = response.shelfId;
            shelf2.shelfBanner = response.shelfBanner;
            shelf2.shelfName = response.shelfName;
            shelf2.shelfInfo = response.shelfInfo;
            return shelf2;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getSelfByIdError", e.getMessage(), e);
        }
    }

}
