package cjc.weixinmp;

import cjc.weixinmp.bean.AbstractResponse;
import cjc.weixinmp.bean.ClickEventRequest;
import cjc.weixinmp.bean.ImageRequest;
import cjc.weixinmp.bean.LinkRequest;
import cjc.weixinmp.bean.LocationEventRequest;
import cjc.weixinmp.bean.LocationRequest;
import cjc.weixinmp.bean.ScanEventRequest;
import cjc.weixinmp.bean.SubscribeEventRequest;
import cjc.weixinmp.bean.TextRequest;
import cjc.weixinmp.bean.TextResponse;
import cjc.weixinmp.bean.VideoRequest;
import cjc.weixinmp.bean.VoiceRequest;
import cjc.weixinmp.merchant.bean.OrderPayEventRequest;

/**
 * 用户主动操作出发的事件的抽象接口<br>
 * 例如：用户向公众号发送消息、点击菜单事件、关注事件等等<br>
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public abstract class AbstractUserOperate {

    /** 微信API控制器，由控制器调用时主动注入 */
    protected AbstractWeixinmpController controller;

    /** 当前操作者的用户名（Open ID） */
    protected final String FromUserOpenID;

    /**
     * 创建一个接口实现
     * @param FromUserOpenID
     *            用户OpenID，绑定这个用户
     */
    protected AbstractUserOperate(String FromUserOpenID) {
        this.FromUserOpenID = FromUserOpenID;
    }

    /**
     * 注入控制器
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午3:29:35
     * @param controller
     */
    protected final void setController(AbstractWeixinmpController controller) {
        if (this.controller != controller) {
            this.controller = controller;
        }
    }

    /**
     * 创建文本消息的便捷方法
     * @param content
     *            文本内容，如果为null或者空字符串，微信不会显示
     * @return 文本消息响应对象
     */
    protected TextResponse buildTextResponse(String content) {
        TextResponse resp = new TextResponse();
        resp.Content = content;
        return resp;
    }

    /**
     * 用户发送文本信息事件
     * @param text
     *            文本信息
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onTextMessage(TextRequest text) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户发送图片信息事件，需要调用PicUrl或者MediaId下载图片
     * @param image
     *            图片信息
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onImageMessage(ImageRequest image) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户发送语音信息事件
     * @param voice
     *            语音消息，需要调用MediaId下载语音文件
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onVoiceMessage(VoiceRequest voice) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户发送视频信息事件
     * @param video
     *            视频消息，，需要调用MediaId下载视频文件
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onVideoMessage(VideoRequest video) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户发送地理位置信息事件
     * @param location
     *            定位消息
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onLocationMessage(LocationRequest location) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户发送链接信息事件
     * @param link
     *            链接消息
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onLinkMessage(LinkRequest link) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户订阅事件<br>
     * 可能有两种情况，一是普通关注事件，二是扫描了带参数的二维码触发的（EventKey 和Ticket 属性有值）
     * @param event
     * @return 支持返回文本消息、图片消息、语音消息、视频消息、音乐消息、图文消息
     * @throws WeixinException
     *             如果发生义务异常则抛出这个异常，系统会捕捉其并向用户返回e.getMessage()的文本消息
     */
    protected AbstractResponse onSubscribeEvent(SubscribeEventRequest event) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户退订事件
     * @param event
     *            退订事件
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onUnsubscribeEvent(SubscribeEventRequest event) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户扫描事件（推广支持）<br>
     * @param event
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onScanEvent(ScanEventRequest event) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 用户上报地理位置信息事件
     * @param event 包含地理位置的事件对象
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onLocationEvent(LocationEventRequest event) throws WeixinException {
        throw new NotImplException();
    }

    /**
     * 点击菜单拉取消息时的事件推送
     * @param click 包含点击参数的事件对象
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onClickEvent(ClickEventRequest click) throws WeixinException {
        throw new NotImplException();
    }
    
    /**
     * 点击菜单跳转链接时的事件推送 
     * @param click 包含点击参数的事件对象
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onViewEvent(ClickEventRequest click) throws WeixinException {
    	throw new NotImplException();
    }
    
    /**
     * 用户支付“小店”订单完成时的事件推送 
     * @param orderPayEvent 包含点击参数的事件对象
     * @return 参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     * @throws WeixinException
     *             参见 {@link #onSubscribeEvent(SubscribeEventRequest)}
     */
    protected AbstractResponse onMerchantOrderPayEvent(OrderPayEventRequest orderPayEvent) throws WeixinException {
    	throw new NotImplException();
    }

}
