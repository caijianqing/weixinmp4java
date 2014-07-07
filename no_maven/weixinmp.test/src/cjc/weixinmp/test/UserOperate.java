package cjc.weixinmp.test;

import cjc.weixinmp.AbstractUserOperate;
import cjc.weixinmp.WeixinException;
import cjc.weixinmp.bean.AbstractResponse;
import cjc.weixinmp.bean.ClickEventRequest;
import cjc.weixinmp.bean.ImageRequest;
import cjc.weixinmp.bean.LinkRequest;
import cjc.weixinmp.bean.LocationEventRequest;
import cjc.weixinmp.bean.LocationRequest;
import cjc.weixinmp.bean.ScanEventRequest;
import cjc.weixinmp.bean.SubscribeEventRequest;
import cjc.weixinmp.bean.TextRequest;
import cjc.weixinmp.bean.VideoRequest;
import cjc.weixinmp.bean.VoiceRequest;

/**
 * 用户主动操作的实现类
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-28 上午11:38:45
 */
public class UserOperate extends AbstractUserOperate {

    public UserOperate(String FromUserOpenID) {
        // 为每一个用户绑定一个对象，可以非常方便地维持每个用户的会话状态
        super(FromUserOpenID);
    }

    @Override
    public AbstractResponse onSubscribeEvent(SubscribeEventRequest event) throws WeixinException {
        // 关注事件
        String help = controller.findHelp("欢迎关注");
        return buildTextResponse(help);
    }

    @Override
    public AbstractResponse onUnsubscribeEvent(SubscribeEventRequest event) throws WeixinException {
        // 取消关注事件
        System.out.println(event.FromUserName + "已取消关注");
        return buildTextResponse("");
    }

    @Override
    public AbstractResponse onTextMessage(TextRequest text) throws WeixinException {
        // 回复文本消息，除此以外，还可以以回复语音、图片、新闻等等
        String help = controller.findHelp("接收到文本消息");
        help = help.replaceFirst("%str%", text.Content);
        help = help.replaceFirst("%length%", String.valueOf(text.Content.length()));
        return buildTextResponse(help);
    }

    @Override
    public AbstractResponse onImageMessage(ImageRequest image) throws WeixinException {
        System.out.println("图片=" + image.PicUrl);
        return buildTextResponse("图片很好看");
    }

    @Override
    public AbstractResponse onVoiceMessage(VoiceRequest voice) throws WeixinException {
        System.out.println("语音消息" + voice);
        return buildTextResponse("你的声音很好听");
    }

    @Override
    public AbstractResponse onVideoMessage(VideoRequest video) throws WeixinException {
        System.out.println("视频消息=" + video);
        return buildTextResponse("你好漂亮");
    }

    @Override
    public AbstractResponse onLocationMessage(LocationRequest location) throws WeixinException {
        System.out.println("定位消息=" + location);
        return buildTextResponse("你在这里" + location);
    }

    @Override
    public AbstractResponse onLinkMessage(LinkRequest link) throws WeixinException {
        System.out.println("链接=" + link);
        return buildTextResponse("你的链接" + link);
    }

    @Override
    public AbstractResponse onScanEvent(ScanEventRequest event) throws WeixinException {
        // 扫描带参数二维码事件
        return null;
    }

    @Override
    public AbstractResponse onLocationEvent(LocationEventRequest event) throws WeixinException {
        // 上报地理位置事件
        System.out.println("报告地理位置：" + event);
        return buildTextResponse("你在这里：" //
                + "\n时间=" + event.CreateTime //
                + "\n经度=" + event.Longitude //
                + "\n纬度=" + event.Latitude //
                + "\n精度=" + event.Precision);
    }

    @Override
    public AbstractResponse onClickEvent(ClickEventRequest click) throws WeixinException {
        // 自定义菜单事件
        System.out.println("收到点击菜单事件：" + click);
        return buildTextResponse(click.EventKey);
    }
    
    @Override
    public AbstractResponse onViewEvent(ClickEventRequest click) throws WeixinException {
    	// 自定义菜单事件
    	System.out.println("收到点击链接事件：" + click);
    	return buildTextResponse(click.EventKey);
    }

}
