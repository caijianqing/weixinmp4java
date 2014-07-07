package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 音乐消息的响应
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MusicResponse extends AbstractResponse {

    private static final long serialVersionUID = 1L;

    public MusicResponse() {
        MsgType = "music";
    }

    /** 音乐标题 */
    public String Title;

    /** 音乐描述 */
    public String Description;

    /** 音乐链接 */
    public String MusicURL;

    /** 高质量音乐链接，WIFI环境优先使用该链接播放音乐 */
    public String HQMusicUrl;

    /** 缩略图的媒体id，通过上传多媒体文件，得到的id */
    public Integer ThumbMediaId;

    @Override
    public String toString() {
        return "MusicResponse [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
                + ", Title=" + Title + ", Description=" + Description + ", MusicURL=" + MusicURL + ", HQMusicUrl=" + HQMusicUrl + ", ThumbMediaId="
                + ThumbMediaId + "]";
    }

}
