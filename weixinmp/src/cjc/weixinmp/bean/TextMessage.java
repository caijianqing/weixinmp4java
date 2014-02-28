package cjc.weixinmp.bean;

import java.io.Serializable;

/**
 * 主动文本消息<br>
 * msgtype = "text"
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class TextMessage extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    public TextMessage() {
        msgtype = "text";
    }

    /** 文本消息内容 */
    public Content text;

    @Override
    public String toString() {
        return "TextMessage [touser=" + touser + ", msgtype=" + msgtype + ", text=" + text + "]";
    }

    /**
     * 设置消息内容
     * @param content
     */
    public void addContent(String content) {
        text = new Content();
        text.content = content;
    }

    /**
     * 内容
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public static class Content implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 文本消息内容 */
        public String content;

        @Override
        public String toString() {
            return "Content [content=" + content + "]";
        }

    }

}
