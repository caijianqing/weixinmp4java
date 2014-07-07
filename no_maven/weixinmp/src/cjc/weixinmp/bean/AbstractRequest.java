package cjc.weixinmp.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 被动请求消息的公用部分
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 开发者微信号 */
    public String ToUserName;

    /** 发送方帐号（一个OpenID） */
    public String FromUserName;

    /** 消息创建时间 （整型） */
    public String CreateTime;

    /** 消息类型 */
    public String MsgType;

    /** 消息id，64位整型（事件消息{@link SubscribeEventRequest}没有该属性） */
    public String MsgId;

    @Override
    public String toString() {
        return "AbstractRequest [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
                + ", MsgId=" + MsgId + "]";
    }

}
