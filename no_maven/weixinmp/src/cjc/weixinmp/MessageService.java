package cjc.weixinmp;

import java.io.IOException;

import cjc.weixinmp.bean.AbstractMessage;
import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.bean.ImageMessage;
import cjc.weixinmp.bean.MusicMessage;
import cjc.weixinmp.bean.NewsMessage;
import cjc.weixinmp.bean.TextMessage;
import cjc.weixinmp.bean.VideoMessage;
import cjc.weixinmp.bean.VoiceMessage;

/**
 * 主动消息接口
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class MessageService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MessageService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 发送一个主动消息（文本、图片、语音、视频、音乐、图文）
     * @param message
     *            消息（文本、图片、语音、视频、音乐、图文）
     * @throws WeixinException
     *             如果发生错误
     */
    private void sendMessae(AbstractMessage message) throws WeixinException {
        String url = controller.getProperty("message_send_url", null, false);
        try {
            GlobalError error = controller.postWithJson(url, message, GlobalError.class, "sendMessae");
            controller.logInfo("发送主动消息结果：" + error);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_SendMessageError(" + message.msgtype + ")", e.getMessage(), e);
        }
    }

    /**
     * 主动发送一个文本消息
     * @param toUser
     *            接收用户的OpenID
     * @param content
     *            发送内容
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendText(String toUser, String content) throws WeixinException {
        TextMessage msg = new TextMessage();
        msg.touser = toUser;
        msg.addContent(content);
        controller.logInfo("发送主动文本消息：" + msg);
        sendMessae(msg);
    }

    /**
     * 主动发送一个图片消息
     * @param toUser
     *            接收用户的OpenID
     * @param mediaId
     *            图片的资源ID
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendImage(String toUser, String mediaId) throws WeixinException {
        ImageMessage msg = new ImageMessage();
        msg.touser = toUser;
        msg.image.media_id = mediaId;
        controller.logInfo("发送主动图片消息：" + msg);
        sendMessae(msg);
    }

    /**
     * 主动发送一个语音消息
     * @param toUser
     *            接收用户的OpenID
     * @param mediaId
     *            语音资源ID
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendVoice(String toUser, String mediaId) throws WeixinException {
        VoiceMessage msg = new VoiceMessage();
        msg.touser = toUser;
        msg.media_id = mediaId;
        controller.logInfo("发送主动语音消息：" + msg);
        sendMessae(msg);
    }

    /**
     * 主动发送一个视频消息
     * @param toUser
     *            接收用户的OpenID
     * @param mediaId
     *            视频资源ID
     * @param title
     *            视频标题【可选】
     * @param description
     *            视频描述【可选】
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendVideo(String toUser, String mediaId, String title, String description) throws WeixinException {
        VideoMessage msg = new VideoMessage();
        msg.touser = toUser;
        msg.media_id = mediaId;
        msg.title = title;
        msg.description = description;
        controller.logInfo("发送主动视频消息：" + msg);
        sendMessae(msg);
    }

    /**
     * 主动发送一个音乐消息消息
     * @param toUser
     *            接收用户的OpenID
     * @param musicUrl
     *            普通音质的音乐地址
     * @param hqMusicUrl
     *            高音质的音乐地址
     * @param thumbMediaId
     *            缩略图的资源ID
     * @param title
     *            音乐名称【可选】
     * @param description
     *            音乐描述【可选】
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendMusic(String toUser, String musicUrl, String hqMusicUrl, String thumbMediaId, String title, String description) throws WeixinException {
        MusicMessage msg = new MusicMessage();
        msg.touser = toUser;
        msg.musicurl = musicUrl;
        msg.hqmusicurl = hqMusicUrl;
        msg.title = title;
        msg.description = description;
        controller.logInfo("发送主动音乐消息：" + msg);
        sendMessae(msg);
    }

    /**
     * 主动发送一组文章消息
     * @param toUser
     *            接收用户的OpenID
     * @param news
     *            文章消息，至少添加一个Article，最多10个
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendNews(String toUser, NewsMessage.News news) throws WeixinException {
        NewsMessage msg = new NewsMessage();
        msg.touser = toUser;
        msg.news = news;
        controller.logInfo("发送主动音乐消息：" + msg);
        sendMessae(msg);
    }

}
