package cjc.weixinmp;

import java.io.IOException;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.ExpressRequest;
import cjc.weixinmp.merchant.bean.ExpressTemplate;
import cjc.weixinmp.merchant.bean.ExpressTemplate.DeliveryTemplate;

/**
 * <pre>
 * 邮费模板管理接口
 * @author jianqing.cai@qq.com, 2014年6月9日 下午8:52:58, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class MerchantExpressService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MerchantExpressService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 增加一个邮费模板
     * 
     * @param deliveryTemplate 模板信息
     * @return 返回新模板的ID
     * @throws WeixinException 如果发生错误
     */
    public int addExpress(DeliveryTemplate deliveryTemplate) throws WeixinException {
        String url = controller.getProperty("merchant_express_add_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, deliveryTemplate, ExpressRequest.Response.class, "addExpress");
            controller.logInfo("增加一个邮费模板结果：" + result);
            ExpressRequest.Response response = (ExpressRequest.Response) result;
            return response.templateId;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_addExpressError", e.getMessage(), e);
        }
    }

    /**
     * 删除指定ID的邮费模板
     * 
     * @param templateId 邮费模板ID
     * @throws WeixinException 如果发生错误
     */
    public void deleteExpress(int templateId) throws WeixinException {
        String url = controller.getProperty("merchant_express_del_url", null, false);
        try {
            ExpressRequest req = new ExpressRequest();
            req.templateId = templateId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, ExpressRequest.Response.class, "deleteExpress");
            controller.logInfo("删除指定ID的邮费模板结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_deleteExpressError", e.getMessage(), e);
        }
    }

    /**
     * 更新一个邮费模板
     * 
     * @param expressTemplate 包含templateId的邮费模板
     * @throws WeixinException
     */
    public void updateExpress(ExpressTemplate expressTemplate) throws WeixinException {
        String url = controller.getProperty("merchant_express_update_url", null, false);
        try {
            // 发送请求
            GlobalError result = controller.postWithJson(url, expressTemplate.deliveryTemplate, ExpressRequest.Response.class, "updateExpress");
            controller.logInfo("更新一个邮费模板：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_updateExpressError", e.getMessage(), e);
        }
    }

    /**
     * 获取指定ID的邮费模板
     * 
     * @param templateId 模板ID
     * @return
     * @throws WeixinException 如果发生错误
     */
    public DeliveryTemplate getExpressById(int templateId) throws WeixinException {
        String url = controller.getProperty("merchant_express_getbyid_url", null, false);
        try {
            ExpressRequest req = new ExpressRequest();
            req.templateId = templateId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, ExpressRequest.Response.class, "getExpressById");
            controller.logInfo("获取指定ID的邮费模板结果：" + result);
            ExpressRequest.Response response = (ExpressRequest.Response) result;
            return response.templateInfo;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getExpressByIdError", e.getMessage(), e);
        }
    }

    /**
     * 获取所有邮费模板
     * 
     * @return
     * @throws WeixinException
     */
    public List<DeliveryTemplate> getAllExpress() throws WeixinException {
        String url = controller.getProperty("merchant_express_getall_url", null, false);
        try {
            GlobalError result = controller.postWithJson(url, null, ExpressRequest.Response.class, "getAllExpress");
            controller.logInfo("获取所有邮费模板结果：" + result);
            ExpressRequest.Response response = (ExpressRequest.Response) result;
            return response.templateList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getAllExpressError", e.getMessage(), e);
        }
    }

}
