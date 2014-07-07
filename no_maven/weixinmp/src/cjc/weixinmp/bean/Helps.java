/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午2:16:29
 */
package cjc.weixinmp.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 帮助信息<Br>
 * 文件名：weixinmp_helps.xml
 * 结构：
 * < ?xml version="1.0" encoding="UTF-8"?>
 * < helps>
 *     < help title="标题1"><![CDATA[内容1]]></help>
 *     < help title="标题2"><![CDATA[内容2]]></help>
 * < /helps> *
 * </pre>
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午2:16:29
 */
@XmlRootElement(name = "helps")
@XmlAccessorType(XmlAccessType.FIELD)
public class Helps implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 帮助信息集合 */
    @XmlElement(name = "help")
    private List<Help> helps;

    @Override
    public String toString() {
        return "Helps [helps=" + helps + "]";
    }

    /**
     * @return the {@link #helps}
     */
    public List<Help> getHelps() {
        return helps;
    }

    /**
     * @param helps the {@link #helps} to set
     */
    public void setHelps(List<Help> helps) {
        this.helps = helps;
    }

    /**
     * 根据标题查找一个帮助信息
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午2:19:55
     * @param title
     * @return
     */
    public Help findHelp(String title) {
        if (helps != null) {
            for (Help help : helps) {
                if (title.equalsIgnoreCase(help.getTitle())) {
                    return help;
                }
            }
        }
        return null;
    }

    /**
     * 一条帮助信息
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午2:18:08
     */
    @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
    public static class Help implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 帮助信息标题 */
        private String title;

        /** 帮助信息内容 */
        private String content;

        /**
         * @param title the {@link #title} to set
         */
        public void setTitle(String title) {
            if (title != null) {
                this.title = title.trim();
            } else {
                this.title = title;
            }
        }

        /**
         * @param content the {@link #content} to set
         */
        public void setContent(String content) {
            if (content != null) {
                this.content = content.trim();
            } else {
                this.content = content;
            }

        }

        @Override
        public String toString() {
            return "Help [title=" + title + ", content=" + content + "]";
        }

        /**
         * @return the {@link #title}
         */
        public String getTitle() {
            return title;
        }

        /**
         * @return the {@link #content}
         */
        public String getContent() {
            return content;
        }

    }

}
