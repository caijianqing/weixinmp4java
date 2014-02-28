package cjc.weixinmp.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cjc.weixinmp.MediaLibraryService;
import cjc.weixinmp.bean.CustomMenu;
import cjc.weixinmp.bean.CustomMenu.CustomButton;
import cjc.weixinmp.bean.Media;
import cjc.weixinmp.bean.QrCodeRequest.TYPE;
import cjc.weixinmp.bean.QrCodeResponse;

/**
 * 测试Servlet
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class WeixinmpTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ///////////////////////////////////////////自定义菜单操作////////////////////////////////////////////////

    public void menuCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CustomButton button = new CustomButton();
        button.addButton(CustomMenu.TYPE.click, "空按钮", "anniu1", null);
        button.addButton(CustomMenu.TYPE.view, "百度", null, "http://www.baidu.com");
        button.addButton(CustomMenu.TYPE.click, "菜单", "anniu1", null) //
                .addSubButton(CustomMenu.TYPE.click, "按钮一", "anniu1", null) //
                .addSubButton(CustomMenu.TYPE.click, "按钮二", "anniu2", null) //
                .addSubButton(CustomMenu.TYPE.view, "视频", null, "http://v.qq.com");
        Engine.getWeixinmpController().getCustomMenuService().updateMenu(button);
    }

    public void menuQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getCustomMenuService().getMenu();
    }

    public void menuDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getCustomMenuService().deleteMenu();
    }

    // //////////////////////////////////////////主动消息/////////////////////////////////////////////////

    public void sendText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getMessageService().sendText("o7R_hjgD2254yWvNk33Ij0tg9Kgk", "现在时间是：" + new Date());
    }

    // //////////////////////////////////////////用户管理/////////////////////////////////////////////////

    public void groupCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().addGroup("大猩猩");
    }

    public void groupGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().getAllGroupList();
    }

    public void groupId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().getGroupIdByUser("o7R_hjgD2254yWvNk33Ij0tg9Kgk");
    }

    public void groupUpdateName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().updateGroupName(100, "大猩猩2");
    }

    public void groupMoveUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().moveUserGroup("o7R_hjgD2254yWvNk33Ij0tg9Kgk", 100);
    }

    public void userInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().getUser("o7R_hjgD2254yWvNk33Ij0tg9Kgk");
    }

    public void userList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().getUserList(null);
    }

    // //////////////////////////////////////////参数二维码/////////////////////////////////////////////////

    public void qrcodeCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QrCodeResponse qrcode2 = Engine.getWeixinmpController().getQrCodeService().createQrcode(TYPE.QR_LIMIT_SCENE, 1234, 200);
        File file = Engine.getWeixinmpController().getQrCodeService().getQrCodeAsFile(qrcode2.ticket);
        System.out.println(file);
    }

    // 访问这个方法的时候，提供的ticket参数注意url编码，特别是+号使用%20代替
    public void qrcodeGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ticket = request.getParameter("ticket");
        InputStream in = Engine.getWeixinmpController().getQrCodeService().getQrCodeAsStream(ticket);
        response.setContentType("image/jpg");
        OutputStream out = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    // //////////////////////////////////////////上传文件/////////////////////////////////////////////////

    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Media media = Engine.getWeixinmpController().getMediaLibraryService().uploadMedia(MediaLibraryService.TYPE.image, new File("d:/img2.jpg"));
        Engine.getWeixinmpController().getMessageService().sendImage("o7R_hjgD2254yWvNk33Ij0tg9Kgk", media.media_id);
    }

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
            e.printStackTrace();
        }
    }

}
