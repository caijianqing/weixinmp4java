package cjc.weixinmp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义菜单
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class CustomMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 自定义菜单 */
    public CustomButton menu;

    @Override
    public String toString() {
        return "CustomMenu [menu=" + menu + "]";
    }

    /***
     * 自定义按钮
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public static class CustomButton implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 一级菜单数组，个数应为1~3个 */
        public List<Button> button;

        @Override
        public String toString() {
            return "CustomMenu [button=" + button + "]";
        }

        /**
         * 增加一个一级菜单
         * @param type
         * @param name
         * @param key
         * @param url
         * @return 返回新建的这个按钮对象，可以进一步添加二级菜单
         */
        public Button addButton(TYPE type, String name, String key, String url) {
            if (button == null) {
                button = new ArrayList<Button>();
            }
            Button btn = new Button();
            btn.type = type;
            btn.name = name;
            btn.key = key;
            btn.url = url;
            button.add(btn);
            return btn;
        }

    }

    /**
     * 一级菜单按钮
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public static class Button extends SubButton implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 二级菜单数组，个数应为1~5个[可选] */
        public List<SubButton> sub_button;

        /**
         * 增加一个二级菜单
         * @param type
         * @param name
         * @param key
         * @param url
         * @return 返回对象自身，可以链式添加二级菜单
         */
        public Button addSubButton(TYPE type, String name, String key, String url) {
            if (sub_button == null) {
                sub_button = new ArrayList<CustomMenu.SubButton>();
            }
            SubButton sub = new SubButton();
            sub.type = type;
            sub.name = name;
            sub.key = key;
            sub.url = url;
            sub_button.add(sub);
            return this;
        }

        @Override
        public String toString() {
            return "Button [type=" + type + ", name=" + name + ", key=" + key + ", url=" + url + ", sub_button=" + sub_button + "]";
        }

    }

    /**
     * 二级菜单按钮
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public static class SubButton implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 菜单的响应动作类型，目前有click、view两种类型 */
        public TYPE type;

        /** 菜单标题，不超过16个字节，子菜单不超过40个字节 */
        public String name;

        /** 菜单KEY值，用于消息接口推送，不超过128字节【click类型必须】 */
        public String key;

        /** 网页链接，用户点击菜单可打开链接，不超过256字节【view类型必须 】 */
        public String url;

        @Override
        public String toString() {
            return "SubButton [type=" + type + ", name=" + name + ", key=" + key + ", url=" + url + "]";
        }

    }

    /**
     * 菜单按钮类型
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public static enum TYPE {
        /**
         * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
         * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
         */
        click,
        /**
         * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的url值
         * （即网页链接），达到打开网页的目的，建议与网页授权获取用户基本信息接口结合，获得用户的登入个人信息。
         */
        view;
    }

}
