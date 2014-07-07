package cjc.weixinmp.bean;

import java.io.Serializable;

/**
 * 公众号全局唯一票据
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 获取到的凭证 */
    public String access_token;

    /** 凭证有效时间，单位：秒 */
    public String expires_in;

    @Override
    public String toString() {
        return "AccessToken [access_token=" + access_token + ", expires_in=" + expires_in + "]";
    }

}
