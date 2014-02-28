package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 图片消息的请求
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    /** 图片链接 */
    public String PicUrl;

    /** 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。 */
    public String MediaId;

    @Override
    public String toString() {
        return "ImageRequest [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", MsgId="
                + MsgId + ", PicUrl=" + PicUrl + ", MediaId=" + MediaId + "]";
    }
}
