/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午1:57:02
 */
package cjc.weixinmp.test;

import java.util.HashMap;
import java.util.Map;

import cjc.weixinmp.AbstractUserOperate;
import cjc.weixinmp.AbstractWeixinmpController;
import cjc.weixinmp.WeixinException;

/**
 * 公众平台控制器实现类
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午1:57:02
 */
public class WeixinmpController extends AbstractWeixinmpController {

    final protected Map<String, AbstractUserOperate> userOperateMap = new HashMap<String, AbstractUserOperate>();
    
    /** 赋值表示使用本地测试服务器时，远程调用API时使用该值替换http(s)://api.weixin.qq.com */
    boolean useLocalTestServer = false;

    public WeixinmpController() {
        // 必须调用super进行初始化
        super();
        // 如果多公众号，请多开几个实例，编写不同的配置文件并且调用这个方法
        // super("weixinmp2.properties"); 
    }

    @Override
    public AbstractUserOperate getUserOperate(String FromUserName) {
        // 详见接口注释
        synchronized (userOperateMap) {
            AbstractUserOperate operate = userOperateMap.get(FromUserName);
            if (operate == null) {
                operate = new UserOperate(FromUserName);
                userOperateMap.put(FromUserName, operate);
            }
            return operate;
        }
    }
    
    public void useLocalTestServer(boolean useLocalTestServer){
        this.useLocalTestServer = useLocalTestServer;
    }
    
    
    @Override
    protected String replaceAccessToken(String url) throws WeixinException {
        // 替换测试服务器地址
        if(useLocalTestServer){
            url = url.replaceFirst("http[s]?://api\\.weixin\\.qq\\.com/", TestAccessServer.localTestServerPrefix);
        }
        return super.replaceAccessToken(url);
    }

    @Override
    protected void logDebug(String msg) {
        // 重写日志方法，默认为sysout输出，可以设置为log4j之类的第三方日志处理。下同。
        super.logDebug(msg);
    }

    @Override
    protected void logInfo(String msg) {
        super.logInfo(msg);
    }

    @Override
    protected void logWarn(String msg) {
        super.logWarn(msg);
    }

    @Override
    protected void logError(String msg) {
        super.logError(msg);
    }

}
