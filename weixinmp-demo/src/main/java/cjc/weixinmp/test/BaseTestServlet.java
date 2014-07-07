package cjc.weixinmp.test;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cjc.weixinmp.WeixinException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <pre>
 * 基础测试控制器
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年6月25日 下午11:58:16, https://github.com/caijianqing/weixinmp4java/
 */
public class BaseTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ///////////////////////////////////////////////////////////////////////////////////////////

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            Method m = this.getClass().getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            m.invoke(this, request, response);
        } catch (Exception e) {
            if (e.getCause() != null) {
                if (RuntimeException.class.isAssignableFrom(e.getCause().getClass())) {
                    e.getCause().printStackTrace();
                    write(response, e.getCause().getMessage());
                } else if (WeixinException.class.isAssignableFrom(e.getCause().getClass())) {
                    System.err.println(e.getCause());
                    write(response, e.getCause().getMessage());
                } else {
                    e.printStackTrace();
                }
            } else {
                e.printStackTrace();
                write(response, e.getMessage());
            }
        }
    }

    protected void write(HttpServletResponse response, Object obj) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String str = gson.toJson(obj);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8;");
        Writer w = response.getWriter();
        w.write("操作结果：\n<pre>" + str + "</pre>");
        w.flush();
        w.close();
    }

}
