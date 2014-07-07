package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 链接消息的请求
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    /** 消息标题 */
    public String Title;

    /** 消息描述 */
    public String Description;

    /** 消息链接 */
    public String Url;

    @Override
    public String toString() {
        return "LinkRequest [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", MsgId="
                + MsgId + ", Title=" + Title + ", Description=" + Description + ", Url=" + Url + "]";
    }

}
