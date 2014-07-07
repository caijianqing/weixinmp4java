package cjc.weixinmp.bean;

/**
 * 主动视频消息<br>
 * msgtype = "video"
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class VideoMessage extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    public VideoMessage() {
        msgtype = "voice";
    }

    /** 发送的视频的媒体ID */
    public String media_id;

    /** 视频消息的标题 [可选] */
    public String title;

    /** 视频消息的描述 [可选] */
    public String description;

    @Override
    public String toString() {
        return "VideoMessage [touser=" + touser + ", msgtype=" + msgtype + ", media_id=" + media_id + ", title=" + title + ", description=" + description + "]";
    }

}
