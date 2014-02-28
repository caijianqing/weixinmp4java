package cjc.weixinmp.bean;


/**
 * 主动音乐消息<br>
 * msgtype = "music"
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class MusicMessage extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    public MusicMessage() {
        msgtype = "music";
    }

    /** 发送的视频的媒体ID */
    public String media_id;

    /** 音乐标题 [可选] */
    public String title;

    /** 音乐描述 [可选] */
    public String description;

    /** 音乐链接 */
    public String musicurl;

    /** 高品质音乐链接，wifi环境优先使用该链接播放音乐 */
    public String hqmusicurl;

    /** 缩略图的媒体ID */
    public String thumb_media_id;

    @Override
    public String toString() {
        return "MusicMessage [touser=" + touser + ", msgtype=" + msgtype + ", media_id=" + media_id + ", title=" + title + ", description=" + description
                + ", musicurl=" + musicurl + ", hqmusicurl=" + hqmusicurl + ", thumb_media_id=" + thumb_media_id + "]";
    }

}
