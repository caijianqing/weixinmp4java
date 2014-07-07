package cjc.weixinmp.merchant.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import cjc.weixinmp.bean.AbstractEventRequest;

/**
 * 用户订单支付完成的事件的请求<br>
 * MsgId 字段无效
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderPayEventRequest extends AbstractEventRequest {

    private static final long serialVersionUID = 1L;

    /** 订单号，数据举例：test_order_id */
    public String OrderId;
    
    /** 商品状态(0-全部, 1-上架, 2-下架) */
    public Integer OrderStatus;
    
    /** 订单ID，数据举例：test_product_id */
    public String ProductId;
    
    /** SKU信息，数据举例：10001:1000012;10002:100021 */
    public String SkuInfo;

    @Override
	public String toString() {
		return "OrderPayEventRequest [Event=" + Event + ", ToUserName="
				+ ToUserName + ", FromUserName=" + FromUserName
				+ ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
				+ ", MsgId=" + MsgId + ", OrderId=" + OrderId
				+ ", OrderStatus=" + OrderStatus + ", ProductId=" + ProductId
				+ ", SkuInfo=" + SkuInfo + "]";
	}

}
