微信公众平台API接口JAVA版
=============
  - 别名：weixinmp4java、wechat4java
  - 这个包是微信公众平台纯API接口实现，除了一个gson的jar包以外，没有多余的东西在里面。
  - 支持公众号（包括订阅号和服务号）的所有主动接口和被动接口。


项目介绍
=============
    /weixinmp          实现代码主项目，可以输出为jar包使用。注意依赖gson.jar。
    /weixinmp.test     示例&测试项目，看这里快速上手。

软件包介绍
=============
##cjc.weixinmp
包含事件分发、主动调用、通讯，解析json，解析xml，日志储存，读取配置文件，读取帮助文件信息等功能。

##cjc.weixinmp.bean
接口通讯中涉及到的数据格式（xml或者json），已被封装为实体。

配置文件
=============
##/weixinmp.default.properties
框架基本设置，请复制一份并重命名为weixinmp.properties存放在你的src根目录下。
  - 公众号的基本信息（token、appid、appsecret等）
  - 公众平台API接口配置（各种URL）
  - 代理设置（支持通过代理上网的环境）

##/weixinmp_helps.xml
帮助信息文件，请复制一份weixinmp_helps.xml存放在你的src目录下。
  - 帮助信息配置文件，由controller.getHelp(key)获得内容。

接口介绍
=============

##cjc.weixinmp.AbstractUserOperate
由微信服务器对你的服务器进行调用的接口称之为被动接口。
例如：发送文本消息、语音消息、订阅事件、退订事件、点击菜单事件等。
该接口已包含了所有开放的接口，选择需要的方法进行重写，并编写自己的业务代码即可。

    // 用户主动发送文本消息事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onTextMessage(TextRequest text) 
    
    // 用户主动发图片消息事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onImageMessage(ImageRequest image)
    
    // 用户主动发语音消息事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onVoiceMessage(VoiceRequest voice)
    
    // 用户主动发视频消息事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onVideoMessage(VideoRequest video)
    
    // 用户主动发位置消息事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onLocationMessage(LocationRequest location)
    
    // 用户主动发链接消息事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onLinkMessage(LinkRequest link) 
    
    // 用户订阅事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onSubscribeEvent(SubscribeEventRequest event)
    
    // 用户退订事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onUnsubscribeEvent(SubscribeEventRequest event)
    
    // 用户扫描参数二维码事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onScanEvent(ScanEventRequest event) 
    
    // 自动上报位置信息事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onLocationEvent(LocationEventRequest event) 
    
    // 用户点击自定义菜单事件
    AbstractResponse cjc.weixinmp.AbstractUserOperate.onClickEvent(ClickEventRequest click) 
    

以“用户订阅事件”为例：

    AbstractResponse cjc.weixinmp.AbstractUserOperate.onSubscribeEvent(SubscribeEventRequest event){
        // 从帮助信息文件（weixinmp_helps.xml）中加载。也可以自己new TextResponse对象。
        String help = controller.findHelp("欢迎关注"); 
        // 这个是内置的一个快速构建“文本回复”的方法，这里意思是向用户回复“欢迎关注”的文字。
        return buildTextResponse(help); 
    }

##cjc.weixinmp.AbstractWeixinmpController
这个是API的总控制器，包含主动接口和被动接口。
这个控制器应该在Application上下文的生命周期内只创建一个实例，并接管某一个Servlet的doPost和doGet请求。

####AbstractUserOperate cjc.weixinmp.test.WeixinmpController.getUserOperate(String FromUserName)
这个抽象方法为每次用户主动发送消息时调用，一般返回与用户绑定的AbstractUserOperate对象，这样保证用户会话不被串线。

####void cjc.weixinmp.test.WeixinmpController.logInfo(String msg)
这是框架默认的记录日志方法，你可以重写它以及其他几个日志方法，用你自己的记录方式，例如log4j。

####主动接口
由你的服务器对微信服务器进行主动调用的接口称之为主动服务。
例如：自定义菜单、客户消息用户管理等。

以下为可用的接口（注意：这些主动接口大部分为高级接口，需要服务号才能使用）：

    // 自定义菜单接口
    CustomerMenuService cjc.weixinmp.AbstractWeixinmpController.getCustomMenuService()
    
    // 上传下载接口
    MediaLibraryService cjc.weixinmp.AbstractWeixinmpController.getMediaLibraryService()
    
    // 客户消息接口
    MessageService cjc.weixinmp.AbstractWeixinmpController.getMessageService()
    
    // 参数二维码接口
    QRCodeService cjc.weixinmp.AbstractWeixinmpController.getQrCodeService()
    
    // 用户管理接口
    UserManagerService cjc.weixinmp.AbstractWeixinmpController.getUserManagerService()


以创建自定义菜单为例：

    CustomButton button = new CustomButton();
    button.addButton(CustomMenu.TYPE.click, "空按钮", "anniu1", null);
    button.addButton(CustomMenu.TYPE.view, "百度", null, "http://www.baidu.com");
    button.addButton(CustomMenu.TYPE.click, "菜单", "anniu1", null) //
            .addSubButton(CustomMenu.TYPE.click, "按钮一", "anniu1", null) //
            .addSubButton(CustomMenu.TYPE.click, "按钮二", "anniu2", null) //
            .addSubButton(CustomMenu.TYPE.view, "视频", null, "http://v.qq.com");
    AbstractWeixinmpController.getCustomMenuService().updateMenu(button);


联系方式
==========================================
这里是唯一的更新地点。

欢迎加入QQ群讨论：289709908

专用Email：github@caijianqing.cn

有任何疑问请优先使用QQ群获得帮助，同时接受微信公众号定制开发。



