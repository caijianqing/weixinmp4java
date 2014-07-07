package cjc.weixinmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cjc.weixinmp.AbstractWeixinmpController.PostEntities;
import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.UploadImageRequest;

/**
 * <pre>
 * 小店功能接口
 * @author jianqing.cai@qq.com, 2014年6月9日 下午8:59:02, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class MerchantCommonService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MerchantCommonService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 上传图片
     * 
     * @param filename 图片文件名(带文件类型后缀)
     * @param input 图片数据
     * @return 图片URL
     * @throws WeixinException
     */
    public String updateImage(String filename, InputStream input) throws WeixinException {
        controller.logInfo("上传文件：filename=" + filename);
        String url = controller.getProperty("merchant_common_upload_img_url", null, false);
        url = url.replaceFirst("FILENAME", filename);
        try {
            // 发送文件
            PostEntities entities = new PostEntities();
            entities.addEntity(PostEntities.TYPE.stream, "file", input);
            GlobalError result = controller.post(url, entities, UploadImageRequest.Response.class, "putMedia");
            controller.logInfo("上传文件结果：" + result);
            UploadImageRequest.Response response = (UploadImageRequest.Response) result;
            return response.imageUrl;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename() + "_updateImageError", e.getLogContent());
            }
            throw e;
        }
    }

    /**
     * 上传图片
     * 
     * @param filename 图片文件名(带文件类型后缀)
     * @param file 图片文件
     * @return 图片URL
     * @throws WeixinException
     * @throws FileNotFoundException
     */
    public String updateImage(String filename, File file) throws WeixinException, FileNotFoundException {
        controller.logInfo("上传文件：filename=" + filename + ", file=" + file);
        String url = controller.getProperty("merchant_common_upload_img_url", null, false);
        url = url.replaceFirst("FILENAME", filename);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return updateImage(filename, fis);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

}
