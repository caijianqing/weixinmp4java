package cjc.weixinmp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 关注着列表
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 关注该公众账号的总用户数 */
    public Integer total;

    /** 拉取的OPENID个数，最大值为10000 */
    public Integer count;

    /** 列表数据，OPENID的列表 */
    public OpenIdList data;

    /** 拉取列表的后一个用户的OPENID */
    public String next_openid;

    @Override
    public String toString() {
        return "Users [total=" + total + ", count=" + count + ", data=" + data + ", next_openid=" + next_openid + "]";
    }

    /**
     * OpenId集合
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午5:14:30
     */
    public static class OpenIdList implements Serializable {

        private static final long serialVersionUID = 1L;

        /** OPENID 集合 */
        public List<String> openid;

        @Override
        public String toString() {
            return "OpenIdList [openid=" + openid + "]";
        }

    }

}
