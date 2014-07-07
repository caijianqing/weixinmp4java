package cjc.weixinmp.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 被动响应消息的公用部分
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 接收方帐号（收到的OpenID），自动赋值 */
    public String ToUserName;

    /** 开发者微信号，自动赋值 */
    public String FromUserName;

    /** 消息创建时间，自动赋值 */
    public String CreateTime;

    /** 消息类型，自动赋值 */
    public String MsgType;

    @Override
    public String toString() {
        return "AbstractResponse [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + "]";
    }

}
