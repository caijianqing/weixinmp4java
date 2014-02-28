package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 事件请求消息的公共请求头<br>
 * MsgId 字段无效
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventRequest extends AbstractEventRequest {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "EventRequest [Event=" + Event + ", ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType="
                + MsgType + ", MsgId=" + MsgId + "]";
    }

}
