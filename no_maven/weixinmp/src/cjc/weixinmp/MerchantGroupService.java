package cjc.weixinmp;

import java.io.IOException;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.ProductGroup;
import cjc.weixinmp.merchant.bean.ProductGroup.GroupDetail;
import cjc.weixinmp.merchant.builder.ProductGroupBuilder;

/**
 * 商品分组管理接口
 * 
 * <pre>
 * @author jianqing.cai@qq.com, 2014年6月9日 下午8:54:20, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class MerchantGroupService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MerchantGroupService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 增加分组
     * 
     * @param productGroup
     * @return
     * @throws WeixinException
     */
    public Integer addGroup(ProductGroup productGroup) throws WeixinException {
        String url = controller.getProperty("merchant_group_add_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, productGroup, ProductGroup.Response.class, "addGroup");
            controller.logInfo("增加分组结果：" + result);
            ProductGroup.Response response = (ProductGroup.Response) result;
            return response.groupId;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_addGroupError", e.getMessage(), e);
        }
    }

    /**
     * 删除分组
     * 
     * @param groupId
     * @throws WeixinException
     */
    public void deleteGroup(int groupId) throws WeixinException {
        String url = controller.getProperty("merchant_group_del_url", null, false);
        try {
            ProductGroup req = new ProductGroup();
            req.groupId = groupId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, ProductGroup.Response.class, "deleteGroup");
            controller.logInfo("删除分组结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_deleteGroupError", e.getMessage(), e);
        }
    }

    /**
     * 修改分组属性
     * 
     * @param groupId 分组ID
     * @param groupName 分组名称
     * @throws WeixinException 如果发生错误
     */
    public void updateGroupProperty(int groupId, String groupName) throws WeixinException {
        String url = controller.getProperty("merchant_group_propertymod_url", null, false);
        try {
            ProductGroup.GroupDetail req = new ProductGroup.GroupDetail();
            req.groupId = groupId;
            req.groupName = groupName;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, ProductGroup.Response.class, "updateGroupProperty");
            controller.logInfo("修改分组名称结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_updateGroupPropertyError", e.getMessage(), e);
        }
    }

    /**
     * 批量新增或修改指定分组的商品
     * 
     * @param productGroup 请通过{@link ProductGroupBuilder#addModAction()}方法添加修改动作
     * @throws WeixinException
     */
    public void updateGroupProduct(ProductGroup productGroup) throws WeixinException {
        String url = controller.getProperty("merchant_group_productymod_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, productGroup, ProductGroup.Response.class, "updateGroupProduct");
            controller.logInfo("批量新增或修改指定分组的商品结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_updateGroupProductError", e.getMessage(), e);
        }
    }

    /**
     * 获取所有分组
     * 
     * @throws WeixinException
     * @return 返回分组集合
     */
    public List<GroupDetail> getAllGroup() throws WeixinException {
        String url = controller.getProperty("merchant_group_getall_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, null, ProductGroup.Response.class, "getAllGroup");
            controller.logInfo("获取所有分组结果：" + result);
            // 返回
            ProductGroup.Response response = (ProductGroup.Response) result;
            return response.groupDetailList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getAllGroupProductError", e.getMessage(), e);
        }
    }

    /**
     * 返回指定ID的分组信息
     * 
     * @param groupId 分组ID
     * @return
     * @throws WeixinException
     */
    public GroupDetail getGroupById(int groupId) throws WeixinException {
        String url = controller.getProperty("merchant_group_getbyid_url", null, false);
        try {
            ProductGroup req = new ProductGroup();
            req.groupId = groupId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, ProductGroup.Response.class, "getGroupById");
            controller.logInfo("返回指定ID的分组信息结果：" + result);
            // 返回
            ProductGroup.Response response = (ProductGroup.Response) result;
            return response.groupDetail;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getGroupByIdProductError", e.getMessage(), e);
        }
    }

}
