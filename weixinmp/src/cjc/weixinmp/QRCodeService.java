package cjc.weixinmp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import cjc.weixinmp.bean.QrCodeRequest;
import cjc.weixinmp.bean.QrCodeResponse;

/**
 * 推广二维码接口
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class QRCodeService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected QRCodeService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 申请带参数二维码
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:52:19
     * @param type 类型，永久或者临时
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param expreSeconds 该二维码有效时间，以秒为单位。 最大不超过1800。申请临时二维码时使用，永久二维码不使用。
     * @return 微信服务器返回ticket和有效时间
     * @throws WeixinException 如果发生错误
     */
    public QrCodeResponse createQrcode(QrCodeRequest.TYPE type, Integer sceneId, Integer expreSeconds) throws WeixinException {
        QrCodeRequest qrcode = new QrCodeRequest(type, sceneId, expreSeconds);
        controller.logInfo("申请带参数的二维码：" + qrcode);
        String url = controller.getProperty("qrcode_create_url", null, false);
        try {
            QrCodeResponse result = controller.postWithJson(url, qrcode, QrCodeResponse.class, "createQrcode");
            controller.logInfo("申请带参数的二维码结果：" + result);
            return result;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_CreateQrcodeError", e.getMessage(), e);
        }
    }

    /**
     * 拉取二维码
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午2:09:08
     * @param ticket
     *            二维码凭证
     * @return 返回一个数据流
     * @throws WeixinException
     *             如果发生错误
     */
    public InputStream getQrCodeAsStream(String ticket) throws WeixinException {
        controller.logInfo("下载带参数的二维码：ticket=" + ticket);
        try {
            String url = controller.getProperty("qrcode_get_url", null, false);
            url = url.replaceFirst("TICKET", URLEncoder.encode(ticket, controller.getEncoding()));
            return controller.getAsStream(url, "GetQRCodeAsStream");
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_GetQRCodeAsStreamError", e.getMessage(), e);
        }
    }

    /**
     * 拉取二维码
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午2:08:20
     * @param ticket
     *            二维码凭证
     * @return 返回一个文件
     * @throws WeixinException
     *             如果发生错误
     */
    public File getQrCodeAsFile(String ticket) throws WeixinException {
        controller.logInfo("下载带参数的二维码：ticket=" + ticket);
        try {
            String url = controller.getProperty("qrcode_get_url", null, false);
            url = url.replaceFirst("TICKET", URLEncoder.encode(ticket, controller.getEncoding()));
            String filename = CommonUtils.getNextId() + "_QRCODE.jpg";
            File file = controller.getAsFile(url, filename, "GetQRCodeAsFile");
            return file;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_GetQRCodeAsFileError", e.getMessage(), e);
        }
    }

}
