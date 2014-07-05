package cjc.weixinmp.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Servlet全局上下文
 * 
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 * 
 */
public class Engine implements ServletContextListener, ServletRequestListener, HttpSessionListener {

    /** 微信开放平台控制器 */
    private static WeixinmpController weixinmpController;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 初始化一个全局唯一的控制器实例
        weixinmpController = new WeixinmpController();
        // 声明为使用本地测试服务器
        weixinmpController.useLocalTestServer(false);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {

    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    public static WeixinmpController getWeixinmpController() {
        return weixinmpController;
    }

}
