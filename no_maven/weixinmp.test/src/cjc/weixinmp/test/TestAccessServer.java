package cjc.weixinmp.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cjc.weixinmp.AbstractWeixinmpController;

/**
 * <pre>
 * 用于模拟结果的测试服务器
 * 
 * 难为屌丝木有小店的测试入口，只能建个测试服务器自我安慰一下。
 * 
 * 使用方法：
 * 1. 请在cjc.weixinmp.test.Engine.contextInitialized(ServletContextEvent sce)调用weixinmpController.useLocalTestServer(true)开启过滤;
 * 2. 参考weixinmp.properties文件的xxxxUrl，给你需要测试的接口在包test下建立xxx_url.txt，表示需要返回的内容
 * 3. 如果需要对接口返回不同的内容，则自己重写一个拦截的方法：menu_query_url(request, response){返回文件名，或者直接response.write内容返回null}
 * 
 * @author jianqing.cai@qq.com, 2014年6月22日 上午11:59:16, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class TestAccessServer implements Filter {

    private static final Map<String, String> map = new HashMap<String, String>();

    public static String localTestServerPrefix;

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // 配置文件名，可以自行修改
        String propertiesPath = AbstractWeixinmpController.WEIXIN_PROPERTIES_FILENAME;
        // 读取配置文件
        Properties defaultProperties;
        Properties properties;
        InputStream in = null;
        try {
            // 加载默认配置文件
            URL url = this.getClass().getResource(AbstractWeixinmpController.WEIXIN_DEFAULT_PROPERTIES_FILENAME);
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
            // 合并配置文件
            Enumeration<Object> keys = defaultProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                map.put(key, defaultProperties.getProperty(key));
            }
            keys = properties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                map.put(key, defaultProperties.getProperty(key));
            }
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

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // 对于URL不包含~localTestServer的请求直接放行
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String contentPath = request.getServletContext().getContextPath();
        String url = request.getRequestURL().toString();
        int index = url.indexOf(contentPath);
        localTestServerPrefix = url.substring(0, index) + contentPath + "/~localTestServer/";
        if (!url.startsWith(localTestServerPrefix)) {
            chain.doFilter(req, resp);
            return;
        }
        url = url.replaceFirst(localTestServerPrefix, "");
        logInfo("拦截API请求：" + url);
        // 在配置文件中搜索匹配的KEY
        Iterator<Entry<String, String>> iter = map.entrySet().iterator();
        String key = null;
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            String val = entry.getValue();
            if (val != null && val.indexOf(url) != -1) {
                key = entry.getKey();
                break;
            }
        }
        if (key == null) {
            logError("在配置文件中找不到这个接口：" + url);
            response.sendError(404, "在配置文件中找不到这个接口：" + url);
            return;
        }
        // 尝试调用拦截方法
        String filename = key;
        try {
            Method method = this.getClass().getDeclaredMethod(filename, HttpServletRequest.class, HttpServletResponse.class);
            filename = (String) method.invoke(this, request, response);
            // 返回null表示其已经处理完成
            if (filename == null) {
                return;
            }
        } catch (NoSuchMethodException e) {
            // 忽略
        } catch (SecurityException e) {
            // 忽略
        } catch (IllegalAccessException e) {
            // 忽略
        } catch (IllegalArgumentException e) {
            // 忽略
        } catch (InvocationTargetException e) {
            throw new ServletException(e.getMessage(), e.getCause());
        }
        if(!filename.endsWith(".txt")){
            filename += ".txt";
        }
        // 读取文件
        URL u = this.getClass().getResource("/test/" + filename);
        if (u == null) {
            logError("请先在测试代码包新建文件" + filename + "并填入需要返回的字符窜");
            response.sendError(404, "请先在测试代码包新建文件" + filename + "并填入需要返回的字符窜");
            return;
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            File file = new File(u.getFile());
            in = u.openStream();
            response.setContentType("text/html; charset=utf-8");
            response.setContentLength((int) file.length());
            out = response.getOutputStream();
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                out.flush();
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }

    }

    @Override
    public void destroy() {

    }

    public void logDebug(String msg) {
        System.out.println(new Date() + "DEBUG\t" + msg);
    }

    public void logInfo(String msg) {
        System.out.println(new Date() + "INFO\t" + msg);
    }

    public void logWarn(String msg) {
        System.out.println(new Date() + "WARN\t" + msg);
    }

    public void logError(String msg) {
        System.err.println(new Date() + "ERROR\t" + msg);
    }

    // //////////////////////////////////////////////////

    // 拦截方法样例
    String menu_query_url(HttpServletRequest request, HttpServletResponse response) {
        // 方法一：直接通过response输出内容
        // 方法二：返回需要调用的文件名，无需添加.txt后缀
        return "menu_query_url";
    }

}
