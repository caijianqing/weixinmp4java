package cjc.weixinmp;

import java.io.File;
import java.io.IOException;

import cjc.weixinmp.AbstractWeixinmpController.PostEntities;
import cjc.weixinmp.bean.Media;

/**
 * 多媒体内容库接口
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class MediaLibraryService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MediaLibraryService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 上传一个文件
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:17:43
     * @param type 文件类型
     * @param file 文件对象
     * @return 上传成功后微信服务器返回的信息
     * @return WeixinException 如发生错误
     */
    public Media uploadMedia(MediaLibraryService.TYPE type, File file) throws WeixinException {
        controller.logInfo("上传文件：type=" + type + ", file=" + file);
        String url = controller.getProperty("file_upload_url", null, false);
        url = url.replaceFirst("TYPE", type.name());
        try {
            // 检查文件大小
            String fileLimitVarname = "file_upload_limit_" + type; // eg: file_upload_limit_image
            long fileLimit = Long.valueOf(controller.getProperty(fileLimitVarname, null, false));
            if (file.length() > fileLimit) {
                throw new WeixinException(CommonUtils.getNextId() + "_UploadFileOverMaxLimit", file.getAbsolutePath(), null);
            }
            // 发送文件
            PostEntities entities = new PostEntities();
            entities.addEntity(PostEntities.TYPE.binary, "media", file);
            Media media = controller.post(url, entities, Media.class, "putMedia");
            controller.logInfo("上传文件结果：" + media);
            return media;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename() + "_UploadFileError(type=" + type + ")", e.getLogContent());
            }
            throw e;
        }
    }

    /**
     * 下载一个文件
     * @param mediaId 媒体ID
     * @param filenameSuffix 文件名后缀
     * @return 以文件的形式返回
     * @return WeixinException 如发生错误
     */
    public File getMedia(String mediaId, String filenameSuffix) throws WeixinException {
        controller.logInfo("下载文件：mediaId=" + mediaId);
        try {
            String url = controller.getProperty("file_download_url", null, false);
            url = url.replaceFirst("MEDIA_ID", mediaId);
            String filename = CommonUtils.getNextId() + "_" + filenameSuffix;
            File file = controller.getAsFile(url, filename, "GetQRCodeAsFile");
            return file;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_GetQRCodeAsFileError", e.getMessage(), e);
        }
    }

    /**
     * 媒体文件类型
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public enum TYPE {
        /** 图片文件，限制128k */
        image,
        /** 语音文件，限制256k */
        voice,
        /** 视频文件，限制1M */
        video,
        /** 缩略图文件，限制64k */
        thumb;
    }

}
