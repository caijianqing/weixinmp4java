package cjc.weixinmp.bean;

import java.io.Serializable;

/**
 * 用户信息
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class WeixinmpUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 */
    public Integer subscribe;

    /** 用户的标识，对当前公众号唯一 */
    public String openid;

    /** 用户的昵称 */
    public String nickname;

    /** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
    public Integer sex;

    /** 用户所在城市 */
    public String city;

    /** 用户所在国家 */
    public String country;

    /** 用户所在省份 */
    public String province;

    /** 用户的语言，简体中文为zh_CN（zh_CN 简体，zh_TW 繁体，en 英语 ） */
    public String language;

    /** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
    public String headimgurl;

    /** 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 */
    public String subscribe_time;

    @Override
    public String toString() {
        return "WeixinmpUser [subscribe=" + subscribe + ", openid=" + openid + ", nickname=" + nickname + ", sex=" + sex + ", city=" + city + ", country=" + country
                + ", province=" + province + ", language=" + language + ", headimgurl=" + headimgurl + ", subscribe_time=" + subscribe_time + "]";
    }

}
