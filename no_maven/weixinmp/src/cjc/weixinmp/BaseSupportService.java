/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午2:15:32
 */
package cjc.weixinmp;

import java.util.Arrays;
import java.util.Date;

import cjc.weixinmp.bean.AccessToken;
import cjc.weixinmp.bean.SignatureInfo;

/**
 * 基础支持接口
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午2:15:32
 */
public class BaseSupportService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected BaseSupportService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 微信服务器接入事件（微信主动访问）
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午3:58:39
     * @param sign
     * @param timestampLimit
     * @return
     * @throws WeixinException
     */
    protected String onAccess(SignatureInfo sign, int timestampLimit) throws WeixinException {
        controller.logInfo("微信服务器请求接入：" + sign);
        checkSignature(sign, timestampLimit);
        return sign.echostr;
    }

    /**
     * 获取AccessToken
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:02:46
     * @param appid
     * @param secert
     * @return
     * @throws WeixinException
     */
    protected AccessToken getAccessToken(String appid, String secert) throws WeixinException {
        controller.logInfo("请求AccessToken：appid=" + appid + "，secert=" + secert);
        if (appid == null || secert == null) {
            throw new WeixinException(CommonUtils.getNextId() + "_NoAppIDOrAppSecert", "调用高级服务需要提供appid和secert", null);
        }
        String url = controller.getProperty("accessToken_url", null, false);
        url = url.replaceFirst("APPID", appid).replaceFirst("APPSECRET", secert);
        try {
            AccessToken token = controller.post(url, null, AccessToken.class, "getAccessToken");
            return token;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw new WeixinException(CommonUtils.getNextId() + "_ErrorAccessToken", "获取AccessToken失败", e);
        }
    }

    /**
     * 检查签名
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:02:59
     * @param sign
     * @param timestampLimit
     * @throws WeixinException
     */
    protected void checkSignature(SignatureInfo sign, int timestampLimit) throws WeixinException {
        // 预期签名
        String expectSignature = null;
        long expectTimestamp = new Date().getTime() / 1000;
        String err = null;
        // 验证数字签名
        if (sign.signature != null && sign.timestamp != null && sign.nonce != null) {
            // 0. 鉴定时间差
            try {
                long timestamp = Long.valueOf(sign.timestamp);
                if (Math.abs(expectTimestamp - timestamp) <= timestampLimit) {
                    // 1. sort
                    String[] tmpArr = new String[] { sign.token, sign.timestamp, sign.nonce };
                    Arrays.sort(tmpArr);
                    // 2. implode
                    StringBuffer sb = new StringBuffer();
                    for (String s : tmpArr) {
                        sb.append(s);
                    }
                    // 3. sha1
                    expectSignature = CommonUtils.sha1(sb.toString());
                    // 4. equals
                    if (expectSignature != null && expectSignature.equals(sign.signature)) {
                        return; // 验证通过
                    }
                }
            } catch (NumberFormatException e) {
                err = "时间格式不正确：" + e.getMessage();
            }
        }
        // 验证出错，抛出异常
        StringBuffer sb = new StringBuffer();
        sb.append("signature=").append(sign.signature).append("\r\n");
        sb.append("timestamp=").append(sign.timestamp).append("\r\n");
        sb.append("nonce=").append(sign.nonce).append("\r\n");
        sb.append("echostr=").append(sign.echostr).append("\r\n");
        sb.append("expectSignature=").append(expectSignature).append("\r\n");
        sb.append("expectTimestamp=").append(expectTimestamp).append("\r\n");
        sb.append("err=").append(err).append("\r\n");
        throw new WeixinException(CommonUtils.getNextId() + "_checkFail.dat", sb.toString(), null);
    }

}
