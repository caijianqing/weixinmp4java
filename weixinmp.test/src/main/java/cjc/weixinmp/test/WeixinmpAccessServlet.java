package cjc.weixinmp.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接入控制器
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-28 上午11:43:23
 */
public class WeixinmpAccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 直接接管get请求，在微信公众平台填写url和token的时候由微信服务器调用一次
        Engine.getWeixinmpController().doGet(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 直接接管post请求，微信用户的每一个动作都将由微信服务器转发到这里，例如：发送消息到公众号，订阅，退订等等
        Engine.getWeixinmpController().doPost(request, response);
    }

}
