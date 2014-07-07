package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 语音请求的响应消息<Br>
 * MsgType 是 voice
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VoiceResponse extends AbstractResponse {

    private static final long serialVersionUID = 1L;

    public VoiceResponse() {
        MsgType = "voice";
    }

    /** 通过上传多媒体文件，得到的id。 */
    public Integer MediaId;

    @Override
    public String toString() {
        return "VoiceResponse [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
                + ", MediaId=" + MediaId + "]";
    }

}
