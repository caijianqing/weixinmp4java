package cjc.weixinmp;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.OrderRequest;

/**
 * <pre>
 * 订单管理接口
 * @author jianqing.cai@qq.com, 2014年6月9日 下午8:57:15, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class MerchantOrderService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MerchantOrderService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 根据订单ID获取订单详情
     * 
     * @param orderId
     * @return
     * @throws WeixinException
     */
    public OrderRequest.Response.Order getOrderById(String orderId) throws WeixinException {
        String url = controller.getProperty("merchant_order_getbyid_url", null, false);
        try {
            OrderRequest req = new OrderRequest();
            req.orderId = orderId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, OrderRequest.Response.class, "getOrderById");
            controller.logInfo("根据订单ID获取订单详情结果：" + result);
            OrderRequest.Response response = (OrderRequest.Response) result;
            return response.order;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getOrderByIdError", e.getMessage(), e);
        }
    }

    /**
     * 根据订单状态/创建时间获取订单详情
     * 
     * @param status 订单状态(不带该字段-全部状态, 2-待发货, 3-已发货, 5-已完成, 8-维权中, )
     * @param beginTime 订单创建时间起始时间(不带该字段则不按照时间做筛选)
     * @param endTime 订单创建时间终止时间(不带该字段则不按照时间做筛选)
     * @return 订单集合
     * @throws WeixinException
     */
    public List<OrderRequest.Response.Order> getOrderByFilter(OrderStatus status, Date beginTime, Date endTime) throws WeixinException {
        String url = controller.getProperty("merchant_order_getbyfilter_url", null, false);
        try {
            OrderRequest req = new OrderRequest();
            if (status == null || status == OrderStatus.All) {
                req.status = null;
            } else {
                req.status = status.getId();
            }
            if (beginTime != null) {
                req.begintime = (int) (beginTime.getTime() / 1000);
            }
            if (endTime != null) {
                req.endtime = (int) (endTime.getTime() / 1000);
            }
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, OrderRequest.Response.class, "getOrderByFilter");
            controller.logInfo("根据订单状态/创建时间获取订单详情结果：" + result);
            OrderRequest.Response response = (OrderRequest.Response) result;
            return response.orderList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getOrderByFilterError", e.getMessage(), e);
        }
    }

    /**
     * 设置订单发货信息
     * 
     * @param orderId 订单号
     * @param deliveryCompany 快递公司ID，参考如下：<br>
     * 
     *            <pre>
     *             物流公司 ID
     *             邮政EMS   Fsearch_code
     *             申通快递    002shentong
     *             中通速递    066zhongtong
     *             圆通速递    056yuantong
     *             天天快递    042tiantian
     *             顺丰速运    003shunfeng
     *             韵达快运    059Yunda
     *             宅急送 064zhaijisong
     *             汇通快运    020huitong
     *             易迅快递    zj001yixun
     * </pre>
     * @param deliveryTrackNo 运单号（快递单号）
     * @throws WeixinException
     */
    public void setOrderDelivery(String orderId, String deliveryCompany, String deliveryTrackNo) throws WeixinException {
        String url = controller.getProperty("merchant_order_setdelivery_url", null, false);
        try {
            OrderRequest req = new OrderRequest();
            req.orderId = orderId;
            req.deliveryCompany = deliveryCompany;
            req.deliveryTrackNo = deliveryTrackNo;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, OrderRequest.Response.class, "setOrderDelivery");
            controller.logInfo("设置订单发货信息结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_setOrderDeliveryError", e.getMessage(), e);
        }
    }

    /**
     * 关闭订单
     * 
     * @param orderId 订单号
     * @throws WeixinException
     */
    public void closeOrder(String orderId) throws WeixinException {
        String url = controller.getProperty("merchant_order_close_url", null, false);
        try {
            OrderRequest req = new OrderRequest();
            req.orderId = orderId;
            // 发送请求
            GlobalError result = controller.postWithJson(url, req, OrderRequest.Response.class, "closeOrder");
            controller.logInfo("关闭订单结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_closeOrderError", e.getMessage(), e);
        }
    }

    public static enum OrderStatus {

        /** 全部状态 */
        All(0),

        /** 待发货 */
        Wait_For_Shipped(2),

        /** 运输中（已发货） */
        Shipped(3),

        /** 已完成 */
        Completed(5),

        /** 维权中 */
        Rights(8);

        int id;

        OrderStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

}
