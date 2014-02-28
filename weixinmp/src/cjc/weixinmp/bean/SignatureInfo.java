package cjc.weixinmp.bean;

/**
 * 签名信息
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class SignatureInfo {

    /** TOKEN */
    public String token;

    /** 请求中的timestamp参数、nonce参数。 */
    public String signature;

    /** 时间戳 */
    public String timestamp;

    /** 随机数 */
    public String nonce;

    /** 随机字符串 */
    public String echostr;

    @Override
    public String toString() {
        return "SignatureInfo [token=" + token + ", signature=" + signature + ", timestamp=" + timestamp + ", nonce=" + nonce + ", echostr=" + echostr + "]";
    }

}
