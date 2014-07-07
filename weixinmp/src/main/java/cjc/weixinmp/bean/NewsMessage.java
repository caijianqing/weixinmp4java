package cjc.weixinmp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 主动图文消息<br>
 * msgtype = "news"
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class NewsMessage extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    /** 新闻列表 */
    public News news;

    public NewsMessage() {
        msgtype = "news";
    }

    @Override
    public String toString() {
        return "NewsMessage [touser=" + touser + ", msgtype=" + msgtype + ", news=" + news + "]";
    }

    /**
     * 图文列表
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public static class News implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应 */
        public List<Article> articles = new ArrayList<Article>();

        @Override
        public String toString() {
            return "News [articles=" + articles + "]";
        }

        /**
         * 增加一条图文消息
         * @param Title
         *            图文消息标题
         * @param Description
         *            图文消息描述
         * @param PicUrl
         *            图片链接，支持JPG、PNG格式，较好的效果为大图720*400，小图100*100
         * @return 返回this对象，支持链式操作
         */
        public News addArticle(String Title, String Description, String PicUrl) {
            Article item = new Article();
            item.title = Title;
            item.description = Description;
            item.picurl = PicUrl;
            articles.add(item);
            return this;
        }
    }

    /** 文章 */
    public static class Article implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 图文消息标题 */
        public String title;

        /** 图文消息描述 */
        public String description;

        /** 点击后跳转的链接 */
        public String url;

        /** 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80 */
        public String picurl;

        @Override
        public String toString() {
            return "Article [title=" + title + ", description=" + description + ", url=" + url + ", picurl=" + picurl + "]";
        }

    }

}
