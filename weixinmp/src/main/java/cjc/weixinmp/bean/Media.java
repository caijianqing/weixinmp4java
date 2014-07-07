package cjc.weixinmp.bean;

import java.io.Serializable;

/**
 * 多媒体信息
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图） */
    public String type;

    /** 媒体文件上传后，获取时的唯一标识 */
    public String media_id;

    /** 媒体文件上传时间戳 */
    public String created_at;

    @Override
    public String toString() {
        return "Media [type=" + type + ", media_id=" + media_id + ", created_at=" + created_at + "]";
    }

}
