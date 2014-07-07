package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 语音消息请求<br>
 * MsgType 语音为voice
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VoiceRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    /** 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。 */
    public String MediaId;

    /** 语音格式，如amr，speex等 */
    public String Format;

    /** 语音识别结果，UTF8编码 */
    public String Recognition;

    @Override
    public String toString() {
        return "VoiceRequest [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", MsgId="
                + MsgId + ", MediaId=" + MediaId + ", Format=" + Format + ", Recognition=" + Recognition + "]";
    }

}
