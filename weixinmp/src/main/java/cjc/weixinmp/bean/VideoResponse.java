package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 视频消息的响应 <Br>
 * MsgType 是 video
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoResponse extends AbstractResponse {

    private static final long serialVersionUID = 1L;

    public VideoResponse() {
        MsgType = "video";
    }

    /** 通过上传多媒体文件，得到的id。 */
    public Integer MediaId;

    /** 视频消息的标题 */
    public Integer Title;

    /** 视频消息的描述 */
    public Integer Description;

    @Override
    public String toString() {
        return "VideoResponse [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
                + ", MediaId=" + MediaId + ", Title=" + Title + ", Description=" + Description + "]";
    }

}
