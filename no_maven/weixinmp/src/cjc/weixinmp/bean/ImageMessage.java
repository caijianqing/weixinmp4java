package cjc.weixinmp.bean;

import java.io.Serializable;

/**
 * 主动图片消息<br>
 * msgtype = "image"
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class ImageMessage extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    /** 图片 */
    public Media image = new Media();

    public ImageMessage() {
        msgtype = "image";
    }

    /** 媒体 */
    public static class Media implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 发送的图片的媒体ID */
        public String media_id;

        @Override
        public String toString() {
            return "Media [media_id=" + media_id + "]";
        }

    }

    @Override
    public String toString() {
        return "ImageMessage [touser=" + touser + ", msgtype=" + msgtype + ", image=" + image + "]";
    }

}
