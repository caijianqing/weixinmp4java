/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午1:49:05
 */
package cjc.weixinmp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import cjc.weixinmp.AbstractWeixinmpController.PostEntities.Entity;
import cjc.weixinmp.bean.AbstractRequest;
import cjc.weixinmp.bean.AbstractResponse;
import cjc.weixinmp.bean.AccessToken;
import cjc.weixinmp.bean.ClickEventRequest;
import cjc.weixinmp.bean.EventRequest;
import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.bean.Helps;
import cjc.weixinmp.bean.Helps.Help;
import cjc.weixinmp.bean.ImageRequest;
import cjc.weixinmp.bean.LinkRequest;
import cjc.weixinmp.bean.LocationEventRequest;
import cjc.weixinmp.bean.LocationRequest;
import cjc.weixinmp.bean.RequestHead;
import cjc.weixinmp.bean.ScanEventRequest;
import cjc.weixinmp.bean.SignatureInfo;
import cjc.weixinmp.bean.SubscribeEventRequest;
import cjc.weixinmp.bean.TextRequest;
import cjc.weixinmp.bean.TextResponse;
import cjc.weixinmp.bean.VideoRequest;
import cjc.weixinmp.bean.VoiceRequest;
import cjc.weixinmp.merchant.bean.OrderPayEventRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * 微信公众平台API控制器<br>
 * 该控制器一个实例对应一个公众号
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午1:49:05
 */
public abstract class AbstractWeixinmpController {

    /** 微信(默认)配置文件 */
    public final static String WEIXIN_DEFAULT_PROPERTIES_FILENAME = "/weixinmp.default.properties";

    /** 微信配置文件 */
    public final static String WEIXIN_PROPERTIES_FILENAME = "/weixinmp.properties";

    /** 数据文件后缀名 */
    final static String DATA_FILENAME_SUFFIC = ".data";

    /** XML文件储存路径 */
    private File dataDir;
    
    /** 配置文件路径 */
    private String propertiesPath;

    /** 编码类型 */
    private String encoding;

    /** token */
    private String token;

    /** 开发者账号 */
    private String username;

    /** appID */
    private String appid;

    /** appsecret */
    private String appsecret;

    /** AccessToken有效时间，单位：秒 */
    private Integer accessTokenExpiresTime;

    /** 时间戳限制，正负n秒钟 */
    private Integer timestampLimit;

    /** 默认配置文件 */
    private Properties defaultProperties;

    /** 配置文件 */
    private Properties properties;

    /** 代理服务器地址 */
    private String httpProxyHost;

    /** 代理服务器端口 */
    private Integer httpProxyPort;

    /** 代理服务器用户名 */
    private String httpProxyUsername;

    /** 代理服务器用户密码 */
    private String httpProxyPassword;

    /** 代理服务器 */
    private Proxy proxy;

    // //////////////////////////////////////////////////////

    /**
     * 创建一个微信公众平台控制器（使用默认配置文件：weixinmp.properties）
     * @see #AbstractWeixinmpController(String)
     */
    public AbstractWeixinmpController() {
    	this(WEIXIN_PROPERTIES_FILENAME);
    }
    	
    /**
     * 创建一个微信公众平台控制器（自定义配置文件）
     * 
     * @param propertiesPath 配置文件路径<br>
     *    多公众号时使用（一个weixinmp.properties文件对应一个公众号）<br>
     *    默认为：weixinmp.properties
     * @see #AbstractWeixinmpController()
     */
	public AbstractWeixinmpController(String propertiesPath) {
		this.propertiesPath = propertiesPath;
        InputStream in = null;
        try {
            // 加载默认配置文件
            URL url = this.getClass().getResource(WEIXIN_DEFAULT_PROPERTIES_FILENAME);
            in = url.openStream();
            defaultProperties = new Properties();
            defaultProperties.load(in);
            in.close();
            // 加载用户配置文件
            url = this.getClass().getResource(propertiesPath);
            if (url == null) {
                throw new RuntimeException("缺少配置文件：" + propertiesPath);
            }
            in = url.openStream();
            properties = new Properties();
            properties.load(in);
            in.close();
            // 读取配置
            token = getProperty("token", null, false);
            username = getProperty("username", null, false);
            encoding = getProperty("encoding", "utf-8", false);
            appid = getProperty("appid", null, true);
            appsecret = getProperty("appsecret", null, true);
            timestampLimit = Integer.valueOf(getProperty("timestampLimit", null, false));
            accessTokenExpiresTime = Integer.valueOf(getProperty("accessTokenExpiresTime", null, false));
            dataDir = new File(getProperty("dataDir", null, false));
            httpProxyHost = getProperty("http.proxyHost", null, true);
            String hpp = getProperty("http.proxyPort", null, true);
            if (hpp != null) {
                httpProxyPort = Integer.valueOf(hpp);
            }
            httpProxyUsername = getProperty("http.proxyHost.username", null, true);
            httpProxyPassword = getProperty("http.proxyHost.password", null, true);
            // 初始化HTTP代理
            initHttpProxy();
            // 初始化SSL
            initSSLSocketFactory();
            // 基础XML解析
            headCoxtext = JAXBContext.newInstance(RequestHead.class);
            helpsCoxtext = JAXBContext.newInstance(Helps.class);
            // 普通消息
            unMarshallerJAXBContexts.put("text", JAXBContext.newInstance(TextRequest.class));
            unMarshallerJAXBContexts.put("image", JAXBContext.newInstance(ImageRequest.class));
            unMarshallerJAXBContexts.put("location", JAXBContext.newInstance(LocationRequest.class));
            unMarshallerJAXBContexts.put("link", JAXBContext.newInstance(LinkRequest.class));
            unMarshallerJAXBContexts.put("video", JAXBContext.newInstance(VideoRequest.class));
            unMarshallerJAXBContexts.put("voice", JAXBContext.newInstance(VoiceRequest.class));
            // 事件消息
            unMarshallerJAXBContexts.put("event", JAXBContext.newInstance(EventRequest.class));
            unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.subscribe, JAXBContext.newInstance(SubscribeEventRequest.class));
            unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.unsubscribe, JAXBContext.newInstance(SubscribeEventRequest.class));
            unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.SCAN, JAXBContext.newInstance(ScanEventRequest.class));
            unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.LOCATION, JAXBContext.newInstance(LocationEventRequest.class));
            unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.CLICK, JAXBContext.newInstance(ClickEventRequest.class));
            unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.VIEW, JAXBContext.newInstance(ClickEventRequest.class));
            unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.merchant_order, JAXBContext.newInstance(OrderPayEventRequest.class));
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化HTTP代理服务器
     */
    private void initHttpProxy() {
        // 初始化代理服务器
        if (httpProxyHost != null && httpProxyPort != null) {
            SocketAddress sa = new InetSocketAddress(httpProxyHost, httpProxyPort);
            proxy = new Proxy(Type.HTTP, sa);
            // 初始化代理服务器的用户验证
            if (httpProxyUsername != null && httpProxyPassword != null) {
                Authenticator.setDefault(new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(httpProxyUsername, httpProxyPassword.toCharArray());
                    }
                });
            }

        }
    }

    /** SSL */
    private SSLSocketFactory sslSocketFactory;

    /**
     * 初始化SSL
     */
    private void initSSLSocketFactory() {
        try {
            // fix: handshake alert: unrecognized_name
            System.setProperty("jsse.enableSNIExtension", "false");
            // fix: Received fatal alert: bad_record_mac
            System.setProperty("https.protocols", "SSLv3,SSLv2Hello");
            // instance TLS
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从配置文件获取一个属性
     * @param key
     *            属性名称
     * @param defaultValue
     *            默认属性值
     * @param allowNull
     *            是否允许为NULL
     * @return
     */
    protected String getProperty(String key, String defaultValue, boolean allowNull) {
        // 首先从用户配置文件读取，读取到为NULL时再从默认配置读取
        Properties pro;
        if (properties.containsKey(key)) {
            pro = properties;
        } else {
            pro = defaultProperties;
        }
        // 读取配置值
        String value = pro.getProperty(key);
        if (value == null) {
            value = defaultValue;
        }
        if (value == null) {
            if (!allowNull) {
                throw new RuntimeException("配置文件（" + propertiesPath + "）缺少属性：key=" + key);
            }
            return null;
        }
        return value.trim();
    }

    // //////////////////////////////////////////////////////

    /** 请求头解释器 */
    JAXBContext headCoxtext;

    /** XML解释器集合 */
    Map<String, JAXBContext> unMarshallerJAXBContexts = new HashMap<String, JAXBContext>();

    // //////////////////////////////////////////////////////

    /** 基础支持服务 */
    private BaseSupportService baseSupportService = new BaseSupportService(this);

    /** 自定义菜单服务 */
    private CustomerMenuService customMenuService = new CustomerMenuService(this);

    /** 媒体库服务 */
    private MediaLibraryService mediaLibraryService = new MediaLibraryService(this);

    /** 主动消息（客服消息）服务 */
    private MessageService messageService = new MessageService(this);

    /** 参数二维码服务 */
    private QRCodeService qrCodeService = new QRCodeService(this);

    /** 用户管理服务 */
    private UserManagerService userManagerService = new UserManagerService(this);

    // //////////////////////////////////////////////////////

    /** 小店功能接口 */
    private MerchantCommonService merchantCommonService = new MerchantCommonService(this);

    /** 邮费模板管理接口 */
    private MerchantExpressService merchantExpressService = new MerchantExpressService(this);
    
    /** 商品分组管理接口  */
    private MerchantGroupService merchantGroupService = new MerchantGroupService(this);
    
    /** 订单管理接口 */
    private MerchantOrderService merchantOrderService = new MerchantOrderService(this);
    
    /** 商品管理接口 */
    private MerchantProductService merchantProductService = new MerchantProductService(this);
    
    /** 货架管理接口 */
    private MerchantShelfService merchantShelfService = new MerchantShelfService(this);
    
    /** 库存管理接口 */
    private MerchantStockService merchantStockService = new MerchantStockService(this);
    
    // //////////////////////////////////////////////////////

    /** GSON缓存 */
    private static Map<Long, Gson> gsonCache = new HashMap<Long, Gson>();

    /**
     * 返回每个线程的gson解析器
     * @return
     */
    private Gson getGson() {
        long id = Thread.currentThread().getId();
        Gson gson = gsonCache.get(id);
        if (gson == null) {
            GsonBuilder g = new GsonBuilder();
            g = g.disableHtmlEscaping(); // 禁用HTML转义
            gson = g.create();
            gsonCache.put(id, gson);
        }
        return gson;
    }

    // //////////////////////////////////////////////////////

    /**
     * 接管来自微信服务器的接入请求的方法
     * @param request
     * @param response
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(encoding);
        response.setContentType("text/html");
        response.setCharacterEncoding(encoding);
        // 收集参数
        SignatureInfo sign = getSignatureInfo(request);
        // 接入
        try {
            String result = baseSupportService.onAccess(sign, timestampLimit);
            // 返回接入结果
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
            // 保存记录
            StringBuffer sb = new StringBuffer();
            sb.append("signature=").append(sign.signature).append("\r\n");
            sb.append("timestamp=").append(sign.timestamp).append("\r\n");
            sb.append("nonce=").append(sign.nonce).append("\r\n");
            sb.append("echostr=").append(sign.echostr).append("\r\n");
            saveToFile(CommonUtils.getNextId() + "_accessOK", sb.toString());
        } catch (WeixinException e) {
            // 返回错误信息
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "GET OUT HERE");
            // 验证出错
            logWarn(e.getMessage());
            // 保存日志
            if (e.isNeedLog()) {
                saveToFile(e.getLogFilename(), e.getLogContent());
            }
        }
    }

    /***
     * 接管来自微信服务器的推送请求，文本消息、图片消息、语音消息、菜单事件等等<br>
     * 首先接收了请求的xml字符串并保存在日志文件中<br>
     * 然后根据请求调用{@link AbstractUserOperate}接口的方法<br>
     * 最后把回复返回给微信服务器，让其推送给客户微信客户端
     * @param request
     * @param response
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置编码
        request.setCharacterEncoding(encoding);
        response.setContentType("text/html");
        response.setCharacterEncoding(encoding);
        File reqXmlFile = null;
        StringReader reader = null;
        try {
            // 消息流水号
            String id = CommonUtils.getNextId();
            // 读取并保存xml内容
            Object[] data = CommonUtils.readXml(getDataFileDir(), encoding, //
                    id + "_req.data", request.getContentLength(), request.getInputStream());
            reqXmlFile = (File) data[0];
            // 分离xml数据
            StringBuffer xmlSb = (StringBuffer) data[1];
            int start = xmlSb.indexOf("<xml>");
            if (start == -1) {
                throw new UnmarshalException("miss \"<xml>\"");
            }
            xmlSb.delete(0, start);
            int end = xmlSb.indexOf("</xml>");
            if (start == -1) {
                throw new UnmarshalException("miss \"</xml>\"");
            }
            xmlSb.delete(end + "</xml>".length(), xmlSb.length());
            String xml = xmlSb.toString();
            reader = new StringReader(xml);
            // 读取请求头
            Unmarshaller headUnMar = headCoxtext.createUnmarshaller();
            RequestHead head = (RequestHead) headUnMar.unmarshal(reader);
            // 解析为实际消息类型
            reader = new StringReader(xml);
            JAXBContext handlerCoxtext = unMarshallerJAXBContexts.get(head.MsgType);
            Unmarshaller reqUnMar = handlerCoxtext.createUnmarshaller();
            AbstractRequest req = (AbstractRequest) reqUnMar.unmarshal(reader);
            // 准备响应结果
            AbstractResponse resp = null;
            // 检查开发者账号
            if (!username.equals(head.ToUserName)) {
                CommonUtils.renameFile(reqXmlFile, "UserNameNotMatch", DATA_FILENAME_SUFFIC);
                TextResponse resp2 = new TextResponse();
                resp2.Content = findHelp("开发者账号不正确");
                resp = resp2;
            }
            // 鉴权
            if (resp == null) {
                SignatureInfo sign = getSignatureInfo(request);
                try {
                    baseSupportService.checkSignature(sign, timestampLimit);
                } catch (WeixinException e) {
                    // 签名不正确
                    logWarn("签名不正确：" + e.getMessage());
                    TextResponse resp2 = new TextResponse();
                    resp2.Content = findHelp("签名不正确");
                    resp = resp2;
                    // 保存日志，同事修改文件名为当前消息的相同ID
                    if (e.isNeedLog()) {
                        saveToFile(id + "_checkFail", e.getLogContent());
                    }
                }
            }
            // 处理用户事件
            if (resp == null) {
                // 获取用户操作器
                AbstractUserOperate operate = getUserOperate(req.FromUserName);
                operate.setController(this);
                // 调用处理器
                try {
                    if (req instanceof TextRequest) { // 用户发送文本信息事件
                        logInfo(req.toString());
                        resp = operate.onTextMessage((TextRequest) req);
                    } else if (req instanceof ImageRequest) { // 用户发送图片信息事件
                        logInfo(req.toString());
                        resp = operate.onImageMessage((ImageRequest) req);
                    } else if (req instanceof LocationRequest) { // 用户发送地理位置事件
                        logInfo(req.toString());
                        resp = operate.onLocationMessage((LocationRequest) req);
                    } else if (req instanceof LinkRequest) { // 用户发送链接信息事件
                        logInfo(req.toString());
                        resp = operate.onLinkMessage((LinkRequest) req);
                    } else if (req instanceof VideoRequest) { // 用户发送视频事件
                        logInfo(req.toString());
                        resp = operate.onVideoMessage((VideoRequest) req);
                    } else if (req instanceof VoiceRequest) { // 用户发送语音事件
                        logInfo(req.toString());
                        resp = operate.onVoiceMessage((VoiceRequest) req);
                    } else if (req instanceof EventRequest) { // 事件类型
                        reader = new StringReader(xml);
                        EventRequest event = (EventRequest) req;
                        switch (event.Event) {
                        case subscribe: // 用户订阅
                            SubscribeEventRequest subscribeEvent = (SubscribeEventRequest) unMarshallerJAXBContexts. //
                                    get("event_" + EventRequest.EventType.subscribe).createUnmarshaller().unmarshal(reader);
                            logInfo(subscribeEvent.toString());
                            resp = operate.onSubscribeEvent(subscribeEvent);
                            break;
                        case unsubscribe: // 用户退订
                            SubscribeEventRequest unsubscribeEvent = (SubscribeEventRequest) unMarshallerJAXBContexts. //
                                    get("event_" + EventRequest.EventType.unsubscribe).createUnmarshaller().unmarshal(reader);
                            logInfo(unsubscribeEvent.toString());
                            resp = operate.onUnsubscribeEvent(unsubscribeEvent);
                            break;
                        case SCAN: // 用户已关注时的事件推送
                            ScanEventRequest scanEvent = (ScanEventRequest) unMarshallerJAXBContexts. //
                                    get("event_" + EventRequest.EventType.SCAN).createUnmarshaller().unmarshal(reader);
                            logInfo(scanEvent.toString());
                            resp = operate.onScanEvent(scanEvent);
                            break;
                        case LOCATION: // 上报地理位置事件
                            LocationEventRequest locationEvent = (LocationEventRequest) unMarshallerJAXBContexts. //
                                    get("event_" + EventRequest.EventType.LOCATION).createUnmarshaller().unmarshal(reader);
                            logInfo(locationEvent.toString());
                            resp = operate.onLocationEvent(locationEvent);
                            break;
                        case CLICK: // 自定义菜单点击按钮事件
                            ClickEventRequest clickEvent = (ClickEventRequest) unMarshallerJAXBContexts. //
                                    get("event_" + EventRequest.EventType.CLICK).createUnmarshaller().unmarshal(reader);
                            logInfo(clickEvent.toString());
                            resp = operate.onClickEvent(clickEvent);
                            break;
                        case VIEW: // 点击菜单跳转链接时的事件推送 
                        	ClickEventRequest viewEvent = (ClickEventRequest) unMarshallerJAXBContexts. //
                        			get("event_" + EventRequest.EventType.VIEW).createUnmarshaller().unmarshal(reader);
                        	logInfo(viewEvent.toString());
                        	resp = operate.onViewEvent(viewEvent);
                        	break;
                        case merchant_order: // “小店”订单支付完成事件
                        	OrderPayEventRequest merchantOrderEvent = (OrderPayEventRequest) unMarshallerJAXBContexts. //
                        	get("event_" + EventRequest.EventType.merchant_order).createUnmarshaller().unmarshal(reader);
                        	logInfo(merchantOrderEvent.toString());
                        	resp = operate.onMerchantOrderPayEvent(merchantOrderEvent);
                        	break;
                        }
                    } else {
                        // 无效的请求
                        logError("无效的事件：" + req);
                    }
                } catch (WeixinException e) {
                    // 发生业务异常，以文本方式返回
                    TextResponse msg = new TextResponse();
                    msg.Content = e.getMessage();
                    resp = msg;
                    // 保存到日志文件
                    if (e.isNeedLog()) {
                        saveToFile(e.getLogFilename(), e.getLogContent());
                    }
                } catch (NotImplException e) {
                    // 调用了未实现的接口
                    TextResponse msg = new TextResponse();
                    msg.Content = this.findHelp("不支持的接口");
                    resp = msg;
                } catch (Throwable t) {
                    t.printStackTrace();
                    TextResponse msg = new TextResponse();
                    msg.Content = this.findHelp("系统出错");
                    resp = msg;
                }
                // 没有返回值
                if (resp == null) {
                    logError("没有回复：req=" + req);
                    TextResponse msg = new TextResponse();
                    msg.Content = this.findHelp("没有回复");
                    resp = msg;
                }
            }
            // 设置固定参数
            resp.ToUserName = req.FromUserName;
            resp.FromUserName = req.ToUserName;
            resp.CreateTime = String.valueOf(new Date().getTime());
            logInfo(resp.toString());
            // 转换为xml
            Class<? extends AbstractResponse> respType = resp.getClass();
            Marshaller respMar = getMarshaller(respType);
            StringWriter sw = new StringWriter();
            respMar.marshal(resp, sw);
            // 保存响应结果
            String result = sw.toString();
            // int index = result.indexOf("<xml>");
            // result = result.substring(index, result.length());
            saveToFile(id + "_resp" + DATA_FILENAME_SUFFIC, result);
            // 输出响应结果
            PrintWriter out = response.getWriter();
            out.write(result);
            out.flush();
            out.close();
        } catch (UnmarshalException e) {
            e.printStackTrace();
            logError(e.getMessage());
            CommonUtils.renameFile(reqXmlFile, "XmlParseError", DATA_FILENAME_SUFFIC);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "XML PARSE ERROR");
        } catch (Exception e) {
            logError(e.getMessage());
            e.printStackTrace();
            CommonUtils.renameFile(reqXmlFile, "InternalServerError", DATA_FILENAME_SUFFIC);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ENERGY DEFICIENCY");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    // ///////////////////////////////////////////////////////

    /** 上次获取accessToken的时间 */
    private long lastAccessTokenTime = 0;

    /** 上次获取到的accessToken */
    private final AccessToken lastAccessToken = new AccessToken();

    /**
     * 从缓存中获取AccessToken <br>
     * 在超时有效期时自动重新获取
     * @param renew
     *            是否更新token
     * @return
     */
    public final AccessToken getAccessToken(boolean renew) throws WeixinException {
        synchronized (lastAccessToken) {
            // 如果上次获取到的token仍然在有效期则直接返回
            long now = new Date().getTime();
            if (renew || now / 1000 - lastAccessTokenTime >= accessTokenExpiresTime) {
                AccessToken token = baseSupportService.getAccessToken(appid, appsecret);
                lastAccessTokenTime = now;
                lastAccessToken.access_token = token.access_token;
                lastAccessToken.expires_in = token.expires_in;
            }
            return lastAccessToken;
        }
    }

    // ///////////////////////////////////////////////////////

    /**
     * 从请求中收集并创建签名信息对象
     * @param request
     * @return
     */
    private SignatureInfo getSignatureInfo(HttpServletRequest request) {
        SignatureInfo sign = new SignatureInfo();
        sign.signature = request.getParameter("signature");
        sign.timestamp = request.getParameter("timestamp");
        sign.nonce = request.getParameter("nonce");
        sign.echostr = request.getParameter("echostr");
        sign.token = token;
        return sign;
    }

    // ///////////////////////////////////////////////////

    /**
     * 打开一个URL连接
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午1:52:57
     * @param url
     *            需要访问的url，如果URL带有“ACCESS_TOKEN”时自动替换为有效的access_token
     * @return 返回一个有效的connection对象
     * @throws MalformedURLException
     *             如果URL不符合规范
     * @throws IOException
     *             如果发生IO错误
     * @throws WeixinException
     *             如果获取accessToken时发生错误
     */
    private HttpURLConnection openConnection(String url) throws MalformedURLException, IOException, WeixinException {
        // 填充accessToken
        url = replaceAccessToken(url);
        // 创建connection
        HttpURLConnection conn;
        if (proxy != null) {
            conn = (HttpURLConnection) new URL(url).openConnection(proxy);
        } else {
            conn = (HttpURLConnection) new URL(url).openConnection();
        }
        // 配置SSL
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection https = (HttpsURLConnection) conn;
            https.setSSLSocketFactory(sslSocketFactory);
        }
        return conn;
    }

    /**
     * 替换AccessToken
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午3:12:53
     * @param url
     * @return
     * @throws WeixinException
     */
    protected String replaceAccessToken(String url) throws WeixinException {
        if (url.indexOf("ACCESS_TOKEN") != -1) {
            url = url.replaceFirst("ACCESS_TOKEN", getAccessToken(false).access_token);
        }
        return url;
    }

    /**
     * 请求（GET）一个URL，返回数据流
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:31:01
     * @param url 资源地址
     * @param actionName 请求名称，用于日志
     * @return 成功返回这个数据流，需要手动关闭
     * @throws IOException 如果发生错误
     * @throws WeixinException 如果发生错误
     */
    protected final InputStream getAsStream(String url, String actionName) throws IOException, WeixinException {
        try {
            HttpURLConnection conn = openConnection(url);
            return conn.getInputStream();
        } catch (WeixinException e) {
            logError(e.getMessage());
            if (e.isNeedLog()) {
                saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        }
    }

    /**
     * 请求（GET）一个资源，以文件对象返回
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:33:04
     * @param url 资源地址
     * @param filenameSuffix 文件名后缀
     * @param actionName 请求名称，用于日志
     * @return 成功返回这个资源的文件对象
     * @throws IOException 如果发生错误
     * @throws WeixinException 如果发生错误
     */
    protected final File getAsFile(String url, String filenameSuffix, String actionName) throws IOException, WeixinException {
        FileOutputStream fos = null;
        InputStream in = null;
        String id = CommonUtils.getNextId();
        try {
            File file = new File(getDataFileDir(), id + "_" + filenameSuffix);
            fos = new FileOutputStream(file);
            in = getAsStream(url, actionName);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                if (len == 0) {
                    Thread.sleep(10); // 如果读取到0字节，则留休息一会免得CPU走火入魔
                    continue;
                } else {
                    fos.write(buf, 0, len);
                }
            }
            fos.flush();
            in.close();
            return file;
        } catch (IOException e) {
            throw new WeixinException(id + "_GetAsFile", e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new WeixinException(id + "_GetAsFile", e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行一个post请求（以json的方式发送参数）
     * @param url
     *            请求地址
     * @param param
     *            请求参数对象，会被转换为json字符串
     * @param returnType
     *            要求返回的类型，从响应json字符串映射为这个类型的对象
     * @param actionName
     *            用于储存日志的动作名称
     * @return 返回returnType指定的对象
     * @throws WeixinException
     *             如果序列化、反序列化json出错
     * @throws IOException
     *             如果打开连接或者保存文件的时候出错
     */
    protected final <T> T postWithJson(String url, Object param, Class<T> returnType, String actionName) throws WeixinException, IOException {
        String id = CommonUtils.getNextId();
        OutputStream os = null;
        InputStream is = null;
        try {
            Gson gson = getGson();
            // 打开连接
            url = replaceAccessToken(url);
            HttpURLConnection conn = openConnection(url);
            conn.setRequestMethod("POST");
            if (param != null) {
                conn.setDoOutput(true);
                String reqJson = gson.toJson(param);
                logDebug("JSON:\t"+reqJson);
                os = conn.getOutputStream();
                os.write(reqJson.getBytes(encoding)); // 必须这样getByte(encoding)才不会乱码
                os.flush();
                os.close();
            }
            // 接收响应
            is = conn.getInputStream();
            Object[] data = CommonUtils.readXml(getDataFileDir(), encoding, //
                    id + "_" + actionName + ".data", conn.getContentLength(), is);
            String json = data[1].toString();
            logDebug("请求:" + url + " ，返回: " + json);
            // 检查是否出现错误
            GlobalError error = gson.fromJson(json, GlobalError.class);
            if (error.errcode != null) {
                if (error.errcode == 40001) {
                    // 如果token超时则重新获取
                    logInfo("AccessToken过时，重新获取。");
                    AccessToken token = getAccessToken(true);
                    // 重新请求
                    url = url.replaceAll("access_token=[^&]+", "access_token=" + token.access_token);
                    return postWithJson(url, param, returnType, actionName);
                } else if (error.errcode != 0) {
                    // 其他错误
                    throw new WeixinException(error.errcode, error.toString(), null);
                }
            }
            // 请求完成
            T obj = gson.fromJson(data[1].toString(), returnType);
            return obj;
        } catch (JsonSyntaxException e) {
            throw new WeixinException(id + "_JsonSyntaxException", e.getMessage(), e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行一个post请求（以实体的方式发送）
     * @param url
     *            请求地址
     * @param entities
     *            请求参数对象，会被转换为json字符串
     * @param actionName
     *            用于储存日志的动作名称
     * @param returnType
     *            要求返回的类型，从响应json字符串映射为这个类型的对象
     * @return 返回returnType指定的对象
     * @throws WeixinException
     *             如果序列化、反序列化json出错
     */
    protected final <T> T post(String url, PostEntities entities, Class<T> returnType, String actionName) throws WeixinException {
        String id = CommonUtils.getNextId();
        OutputStream out = null;
        InputStream is = null;
        DataInputStream dis = null;
        try {
            Gson gson = getGson();
            // 打开连接
            url = replaceAccessToken(url);
            HttpURLConnection conn = openConnection(url);
            // 设置请求头
            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 发送实体附件
            if (entities != null) {
                // 准备输出流
                out = new DataOutputStream(conn.getOutputStream());
                byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(encoding);// 定义最后数据分隔线
                Iterator<Entity> iter = entities.entities.iterator();
                StringBuilder sb = new StringBuilder();
                int bytes = 0;
                byte[] bufferOut = new byte[8192];
                // 循环输出实体
                while (iter.hasNext()) {
                    // 输出分割线
                    sb.setLength(0);
                    sb.append("--").append(BOUNDARY).append("\r\n");
                    // 输出实体头和内容
                    Entity entity = iter.next();
                    switch (entity.type) {
                    case stream:
                        InputStream stream = (InputStream) entity.obj;
                        sb.append("Content-Disposition: form-data;name=\"" + entity.name + "\";filename=\"file1\"\r\n");
                        sb.append("Content-Type:application/octet-stream\r\n\r\n");
                        out.write(sb.toString().getBytes(encoding));
                        dis = new DataInputStream(stream);
                        while ((bytes = dis.read(bufferOut)) != -1) {
                            out.write(bufferOut, 0, bytes);
                        }
                        break;
                    case binary:
                        File file = (File) entity.obj;
                        sb.append("Content-Disposition: form-data;name=\"" + entity.name + "\";filename=\"" + file.getAbsolutePath() + "\"\r\n");
                        sb.append("Content-Type:application/octet-stream\r\n\r\n");
                        out.write(sb.toString().getBytes(encoding));
                        dis = new DataInputStream(new FileInputStream(file));
                        while ((bytes = dis.read(bufferOut)) != -1) {
                            out.write(bufferOut, 0, bytes);
                        }
                        break;
                    case json:
                        sb.append("Content-Disposition: form-data;name=\"" + entity.name + "\"\r\n");
                        sb.append("Content-Type:text/plain\r\n\r\n");
                        out.write(sb.toString().getBytes(encoding));
                        String json = gson.toJson(entity.obj);
                        out.write(json.getBytes(encoding));
                        break;
                    case text:
                        sb.append("Content-Disposition: form-data;name=\"" + entity.name + "\"\r\n");
                        sb.append("Content-Type:text/plain\r\n\r\n");
                        out.write(sb.toString().getBytes(encoding));
                        out.write(entity.obj.toString().getBytes(encoding));
                        break;
                    }
                    out.write("\r\n".getBytes(encoding)); // 多个文件时，二个文件之间加入这个
                    dis.close();
                    out.flush();
                }
                // 输出结束符
                out.write(end_data);
                out.flush();
                out.close();
            }
            // 接收响应
            is = conn.getInputStream();
            Object[] data = CommonUtils.readXml(getDataFileDir(), encoding, //
                    id + "_" + actionName + ".data", conn.getContentLength(), is);
            String json = data[1].toString();
            logDebug("请求:" + url + " ，返回: " + json);
            // 检查是否出现错误
            GlobalError error = gson.fromJson(json, GlobalError.class);
            if (error != null && error.errcode != null) {
                if (error.errcode == 40001) {
                    // 如果token超时则重新获取
                    logInfo("AccessToken过时，重新获取。");
                    AccessToken token = getAccessToken(true);
                    // 重新请求
                    url = url.replaceAll("access_token=[^&]+", "access_token=" + token.access_token);
                    return post(url, entities, returnType, actionName);
                } else if (error.errcode != 0) {
                    // 其他错误
                    throw new WeixinException(error.errcode, error.toString(), null);
                }
            }
            // 请求完成
            T obj = gson.fromJson(data[1].toString(), returnType);
            if(obj == null){
                throw new WeixinException(id + "_BadResponse", "获取不到响应值：" + url, null);
            }
            return obj;
        } catch (JsonSyntaxException e) {
            throw new WeixinException(id + "_JsonSyntaxException", e.getMessage(), e);
        } catch (IOException e) {
            throw new WeixinException(id + "_IOException", e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    // //////////////////////////存储/////////////////////////////

    /**
     * 返回数据目录的路径（根据年月日产生不同的目录）
     * @return
     */
    protected final File getDataFileDir() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);
        String year = String.valueOf(y);
        String month = m >= 10 ? String.valueOf(m) : "0" + m;
        String day = d >= 10 ? String.valueOf(d) : "0" + m;
        StringBuffer sb = new StringBuffer();
        sb.append(year).append('/');
        sb.append(year).append('-').append(month).append('/');
        sb.append(year).append('-').append(month).append('-').append(day).append('/');
        File dir = new File(dataDir, sb.toString());
        dir.mkdirs();
        return dir;
    }

    /**
     * 把文本内容储存到数据文件
     * @param name
     *            文件名
     * @param context
     *            需要储存的内容
     * @throws IOException
     */
    protected final void saveToFile(String name, String context) {
        File file = new File(getDataFileDir(), name);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file, encoding);
            pw.write(context);
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    // ///////////////////////////XML解析器////////////////////////

    /** XML编组器集合 */
    Map<Class<? extends AbstractResponse>, JAXBContext> marshallerJAXBContexts = new HashMap<Class<? extends AbstractResponse>, JAXBContext>();

    /**
     * 获取指定响应类型的编组器
     * @param respType
     * @return
     * @throws JAXBException
     */
    private Marshaller getMarshaller(Class<? extends AbstractResponse> respType) throws JAXBException {
        JAXBContext context = marshallerJAXBContexts.get(respType);
        if (context == null) {
            context = JAXBContext.newInstance(respType);
            marshallerJAXBContexts.put(respType, context);
        }
        return context.createMarshaller();
    }

    // ///////////////////////////帮助信息////////////////////////

    /** 帮助信息文件名 */
    final String HELPS_FILENAME = "weixinmp_helps.xml";

    /** 向导文件解组解释器 */
    JAXBContext helpsCoxtext;

    /** 向导集合信息 */
    Helps helps;

    /** 向导集合最后更新时间（标志位） */
    long guidesLastModifiedTime = 0;

    /**
     * 读取帮助信息文件
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午2:49:26
     * @return 如果出错可能返回null
     * @throws JAXBException
     *             xml解组异常
     */
    private synchronized Helps loadHelps() throws JAXBException {
        URL helpFileURL = this.getClass().getResource("/" + HELPS_FILENAME);
        if (helpFileURL == null) {
            throw new RuntimeException("没有配置“" + HELPS_FILENAME + "”文件");
        }
        String helpsFilePath = helpFileURL.getFile();
        File helpsFile = new File(helpsFilePath);
        if (helpsFile.exists()) { // 读取用户配置文件
            if (helpsFile.lastModified() != guidesLastModifiedTime) {
                Unmarshaller helpUnMar = helpsCoxtext.createUnmarshaller();
                helps = (Helps) helpUnMar.unmarshal(helpsFile);
                guidesLastModifiedTime = helpsFile.lastModified();
            }
        } else { // 读取jar封装文件
            Unmarshaller helpUnMar = helpsCoxtext.createUnmarshaller();
            InputStream in = null;
            try {
                in = helpFileURL.openStream();
                helps = (Helps) helpUnMar.unmarshal(in);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return helps;
    }

    /**
     * 从帮助信息中返回指定标题的帮助信息<br>
     * 请参考“weixinmp_helps.xml”文件格式创建一个自己的描述文件
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午2:48:30
     * @param title
     *            帮助信息标题，位于“weixinmp_helps.xml”文件中的help节点
     * @return 如果没有则返回null
     */
    public String findHelp(String title) {
        Helps helps;
        String help = null;
        try {
            helps = loadHelps();
            if (helps != null) {
                Help h = helps.findHelp(title);
                if (h != null) {
                    help = h.getContent();
                }
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        if (help == null) {
            logWarn("无效帮助信息条目：" + title);
            return title;
        } else {
            logDebug("查找帮助信息：" + title + "=" + help);
            return help;
        }
    }

    // /////////////////////////日志//////////////////////////

    /**
     * 提示“调试”信息，可以覆盖这个方法使用log4j等第三方包
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-22 上午9:42:32
     * @param msg
     */
    protected void logDebug(String msg) {
        System.out.println("debug-->" + msg);
    }

    /**
     * 提示“一般”信息，可以覆盖这个方法使用log4j等第三方包
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-22 上午9:42:32
     * @param msg
     */
    protected void logInfo(String msg) {
        System.out.println("信息-->" + msg);
    }

    /**
     * 提示“警告”信息
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午7:35:18
     * @param msg
     */
    protected void logWarn(String msg) {
        System.err.println("警告-->" + msg);
    }

    /**
     * 提示“错误”信息
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2013-10-21 下午7:35:18
     * @param msg
     */
    protected void logError(String msg) {
        System.err.println("错误-->" + msg);
    }

    // /////////////////////////高级接口（主动操作）//////////////////////////

    /**
     * @return 返回 自定义菜单服务
     */
    public final CustomerMenuService getCustomMenuService() {
        return customMenuService;
    }

    /**
     * @return 返回 多媒体内容库服务
     */
    public final MediaLibraryService getMediaLibraryService() {
        return mediaLibraryService;
    }

    /**
     * @return 返回 主动消息服务
     */
    public final MessageService getMessageService() {
        return messageService;
    }

    /**
     * @return 返回 推广二维码服务
     */
    public final QRCodeService getQrCodeService() {
        return qrCodeService;
    }

    /**
     * @return 返回 用户管理服务
     */
    public final UserManagerService getUserManagerService() {
        return userManagerService;
    }

    // /////////////////////////小店接口//////////////////////////
    

    /**
     * @return 返回 小店功能接口
     */
    public final MerchantCommonService getMerchantCommonService() {
        return merchantCommonService;
    }

    /**
     * @return 返回 邮费模板管理接口 
     */
    public final MerchantExpressService getMerchantExpressService() {
        return merchantExpressService;
    }

    /**
     * @return 返回 商品分组管理接口 
     */
    public final MerchantGroupService getMerchantGroupService() {
        return merchantGroupService;
    }

    /**
     * @return 返回 订单管理接口
     */
    public final MerchantOrderService getMerchantOrderService() {
        return merchantOrderService;
    }

    /**
     * @return 返回 商品管理接口
     */
    public final MerchantProductService getMerchantProductService() {
        return merchantProductService;
    }

    /**
     * @return 返回 货架管理接口
     */
    public final MerchantShelfService getMerchantShelfService() {
        return merchantShelfService;
    }

    /**
     * @return 返回 库存管理接口
     */
    public final MerchantStockService getMerchantStockService() {
        return merchantStockService;
    }

    
    // /////////////////////////基础接口（被动消息）//////////////////////////

    /**
     * 需要返回一个与FromUserNameOpenID用户有关的AbstractUserOperate对象<br>
     * 建议为每个用户创建一个AbstractUserOperate对象，以保持其会话的连续性。
     * @param FromUserNameOpenID
     *            微信用户的OpenID
     * @return 必须返回一个AbstractUserOperate实例
     */
    public abstract AbstractUserOperate getUserOperate(String FromUserNameOpenID);

    // /////////////////////////getter//////////////////////////

    /**
     * 返回编码名称
     * @return the {@link #encoding}
     */
    public final String getEncoding() {
        return encoding;
    }

    // ///////////////////////////////////////////////////

    /**
     * 请求的数据实体，用于进行post提交
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    protected static class PostEntities {

        /** 实体集合 */
        List<Entity> entities = new ArrayList<Entity>();

        /**
         * 增加一个实体
         * @param type
         *            数据类型
         * @param name
         *            变量名
         * @param obj
         *            对象
         * @return 返回当前对象，以支持链式操作
         */
        public PostEntities addEntity(TYPE type, String name, Object obj) {
            entities.add(new Entity(type, name, obj));
            return this;
        }

        /**
         * 数据类型
         * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
         */
        public static enum TYPE {
            /** 普通文本，直接调用key=value.toString */
            text,
            /** 需要转换为json格式 */
            json,
            /** 二进制数据，对象必须为文件对象 */
            binary,
            /** 二进制数据数据流，对象必须为InputStream的子类 */
            stream;
        }

        /**
         * 数据实体
         * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
         */
        public static class Entity {

            /** 数据类型 */
            TYPE type;

            /** 属性名 */
            String name;

            /** 值对象，根据type固定的对象 */
            Object obj;

            public Entity(TYPE type, String name, Object obj) {
                this.type = type;
                this.name = name;
                this.obj = obj;
            }

        }

    }

}
