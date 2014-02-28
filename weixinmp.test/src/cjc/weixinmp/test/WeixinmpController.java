/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午1:57:02
 */
package cjc.weixinmp.test;

import java.util.HashMap;
import java.util.Map;

import cjc.weixinmp.AbstractUserOperate;
import cjc.weixinmp.AbstractWeixinmpController;

/**
 * 公众平台控制器实现类
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午1:57:02
 */
public class WeixinmpController extends AbstractWeixinmpController {

    final protected Map<String, AbstractUserOperate> userOperateMap = new HashMap<String, AbstractUserOperate>();

    public WeixinmpController() {
        // 必须调用super进行初始化
        super();
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
