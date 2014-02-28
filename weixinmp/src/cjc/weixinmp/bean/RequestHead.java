package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 被动消息请求的请求头<br>
 * <br>
 * 把消息头独立出来的原因：<br>
 * 在JXBM解组的时候，如果父对象也使用了@XmlRootElement，那么只会得到父对象而得不到子对象
 * @author jcc_000
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestHead extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "RequestHead [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", MsgId="
                + MsgId + "]";
    }

}
