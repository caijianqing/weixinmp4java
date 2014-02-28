package cjc.weixinmp.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 图文消息的响应
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsResponse extends AbstractResponse {

    private static final long serialVersionUID = 1L;

    public NewsResponse() {
        MsgType = "news";
    }

    /** 图文消息个数，限制为10条以内 */
    public int ArticleCount;

    /** 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应 */
    public List<Item> Articles = new ArrayList<Item>();

    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Item {

        /** 图文消息标题 */
        public String Title;

        /** 图文消息描述 */
        public String Description;

        /** 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 */
        public String PicUrl;

        /** 点击图文消息跳转链接 */
        public String Url;

        @Override
        public String toString() {
            return "Item [Title=" + Title + ", Description=" + Description + ", PicUrl=" + PicUrl + ", Url=" + Url + "]";
        }

    }

    /**
     * 增加一条图文消息
     * @param Title
     *            图文消息标题
     * @param Description
     *            图文消息描述
     * @param PicUrl
     *            图片链接，支持JPG、PNG格式，较好的效果为大图720*400，小图100*100
     */
    public void addArticle(String Title, String Description, String PicUrl) {
        Item item = new Item();
        item.Title = Title;
        item.Description = Description;
        item.PicUrl = PicUrl;
        Articles.add(item);
        ArticleCount = Articles.size();
    }

    @Override
    public String toString() {
        return "NewsResponse [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
                + ", ArticleCount=" + ArticleCount + ", Articles=" + Articles + "]";
    }

}
