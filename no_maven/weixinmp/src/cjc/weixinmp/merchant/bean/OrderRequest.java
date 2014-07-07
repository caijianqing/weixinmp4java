package cjc.weixinmp.merchant.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 订单请求的对象
 * @author jianqing.cai@qq.com, 2014年6月10日 下午11:25:56, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class OrderRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @SerializedName("order_id")
    public String orderId;

    /** 订单状态(不带该字段-全部状态, 2-待发货, 3-已发货, 5-已完成, 8-维权中, ) */
    @SerializedName("status")
    public Integer status;

    /** 订单创建时间起始时间( 不带该字段则不按照时间做筛选) */
    @SerializedName("begintime")
    public Integer begintime;

    /** 订单创建时间终止时间( 不带该字段则不按照时间做筛选) */
    @SerializedName("endtime")
    public Integer endtime;

    /**
     * <pre>
     *  物流公司 ID
     *  
     *  (参考《物流公司 ID》)：
     *  邮政 EMS Fsearch_code
     *  申通快递  002shentong
     *  中通速递  066zhongtong
     *  圆通速递  056yuantong
     *  天天快递  042tiantian
     *  顺丰速运  003shunfeng
     *  韵达快运  059Yunda
     *  宅急送  064zhaijisong
     *  汇通快运  020huitong
     *  易迅快递  zj001yixun
     * </pre>
     */
    @SerializedName("delivery_company")
    public String deliveryCompany;

    /** 运单 ID */
    @SerializedName("delivery_track_no ")
    public String deliveryTrackNo;

    /**
     * 服务器响应
     */
    public static class Response extends GlobalError {

        private static final long serialVersionUID = 1L;

        /** 单个订单详情 */
        @SerializedName("order")
        public Order order;

        /** 所有订单集合(字段说明详见根据订单 ID 获取订单详情) */
        @SerializedName("order_list")
        public List<Order> orderList = new ArrayList<Order>();

        /** 订单状态(不带该字段- 全部状态, 2- 待发货, 3-已发货, 5-已完成, 8-维权中, ) */
        @SerializedName("status")
        public Integer status;

        /***
         * 订单详情
         */
        public static class Order extends GlobalError {

            private static final long serialVersionUID = 1L;

            /** 订单 ID */
            @SerializedName("order_id")
            public String ordeId;

            /** 订单状态 */
            @SerializedName("order_status")
            public Integer orderStatus;

            /** 订单总价格(单位 : 分) */
            @SerializedName("order_total_price")
            public Integer orderTotalPrice;

            /** 订单创建时间 */
            @SerializedName("order_create_time")
            public Integer orderCreateTime;

            /** 订单运费价格(单位 : 分) */
            @SerializedName("order_express_price")
            public Integer orderExpressPrice;

            /** 买家微信 OPENID */
            @SerializedName("buyer_openid")
            public String buyerOpenid;

            /** 买家微信昵称 */
            @SerializedName("buyer_nick")
            public String buyerNick;

            /** 收货人姓名 */
            @SerializedName("receiver_name")
            public String receiverName;

            /** 收货地址省份 */
            @SerializedName("receiver_province")
            public String receiverProvince;

            /** 收货地址城市 */
            @SerializedName("receiver_city")
            public String receiverCity;

            /** 收货详细地址 */
            @SerializedName("receiver_address")
            public String receiverAddress;

            /** 收货人移动电话 */
            @SerializedName("receiver_mobile")
            public String receiverMobile;

            /** 收货人固定电话 */
            @SerializedName("receiver_phone")
            public String receiverPhone;

            /** 商品 ID */
            @SerializedName("product_id")
            public String productId;

            /** 商品名称 */
            @SerializedName("product_name")
            public String productName;

            /** 商品价格(单位 : 分) */
            @SerializedName("product_price")
            public Integer productPrice;

            /** 商品 SKU */
            @SerializedName("product_sku")
            public String productSku;

            /** 商品个数 */
            @SerializedName("product_count")
            public Integer productCount;

            /** 商品图片 */
            @SerializedName("product_img")
            public String productImg;

            /** 运单 ID */
            @SerializedName("delivery_id")
            public String deliveryId;

            /** 物流公司编码 */
            @SerializedName("delivery_company")
            public String deliveryCompany;

            /** 交易 ID */
            @SerializedName("trans_id")
            public String transId;

        }

    }

}
