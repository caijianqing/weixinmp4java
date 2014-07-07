package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文本消息的请求
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    /** 文本消息内容 */
    public String Content;

    @Override
    public String toString() {
        return "TextRequest [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", MsgId="
                + MsgId + ", Content=" + Content + "]";
    }

}
