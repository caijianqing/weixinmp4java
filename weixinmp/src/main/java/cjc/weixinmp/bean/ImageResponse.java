package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 图片消息的响应 <Br>
 * MsgType 是 image
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageResponse extends AbstractResponse {

    private static final long serialVersionUID = 1L;

    public ImageResponse() {
        MsgType = "image";
    }

    /** 通过上传多媒体文件，得到的id。 */
    public String MediaId;

    @Override
    public String toString() {
        return "ImageResponse [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
                + ", MediaId=" + MediaId + "]";
    }

}
