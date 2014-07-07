package cjc.weixinmp.test;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cjc.weixinmp.MediaLibraryService;
import cjc.weixinmp.bean.AccessToken;
import cjc.weixinmp.bean.CustomMenu;
import cjc.weixinmp.bean.CustomMenu.CustomButton;
import cjc.weixinmp.bean.GroupInfo.Group;
import cjc.weixinmp.bean.Media;
import cjc.weixinmp.bean.QrCodeRequest.TYPE;
import cjc.weixinmp.bean.QrCodeResponse;
import cjc.weixinmp.bean.WeixinmpUser;

/**
 * 测试Servlet
 * 
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class WeixinmpTestServlet extends BaseTestServlet {

    private static final long serialVersionUID = 1L;

    // ///////////////////////////////////////////ACCESS TOKEN////////////////////////////////////////////////

    public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccessToken token = Engine.getWeixinmpController().getAccessToken(true);
        write(response, token);
    }

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
        write(response, "已更新自定义菜单，请查看微信。可能需要重新打开对话框或重新关注。");
    }

    public void menuQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CustomMenu menu = Engine.getWeixinmpController().getCustomMenuService().getMenu();
        write(response, menu);
    }

    public void menuDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getCustomMenuService().deleteMenu();
        write(response, "已删除自定义菜单，请查看微信。可能需要重新打开对话框或重新关注。");
    }

    // //////////////////////////////////////////主动消息/////////////////////////////////////////////////

    public void sendText(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String to = request.getParameter("to");
        String text = request.getParameter("text");
        Engine.getWeixinmpController().getMessageService().sendText(to, text + "\n\n" + new Date());
    }

    // //////////////////////////////////////////用户管理/////////////////////////////////////////////////

    public void groupCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String groupName = request.getParameter("groupName");
        Engine.getWeixinmpController().getUserManagerService().addGroup(groupName);
        write(response, "操作成功，请到微信公众平台的用户管理界面查看。");
    }

    public void groupGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Group> list = Engine.getWeixinmpController().getUserManagerService().getAllGroupList();
        write(response, list);
    }

    public void groupId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        Integer id2 = Engine.getWeixinmpController().getUserManagerService().getGroupIdByUser(id);
        write(response, id2);
    }

    public void groupUpdateName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        Engine.getWeixinmpController().getUserManagerService().updateGroupName(id, name);
        write(response, "操作成功，请到微信公众平台的用户管理界面查看。");
    }

    public void groupMoveUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Integer groupId = Integer.valueOf(request.getParameter("groupId"));
        Engine.getWeixinmpController().getUserManagerService().moveUserGroup(userId, groupId);
        write(response, "操作成功，请到微信公众平台的用户管理界面查看。");
    }

    public void userInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        WeixinmpUser user = Engine.getWeixinmpController().getUserManagerService().getUser(userId);
        write(response, user);
    }

    public void userList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Engine.getWeixinmpController().getUserManagerService().getUserList(null);
    }

    // //////////////////////////////////////////参数二维码/////////////////////////////////////////////////

    public void qrcodeCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        TYPE type = TYPE.valueOf(request.getParameter("type"));
        Integer sceneId = Integer.valueOf(request.getParameter("sceneId"));
        Integer expreSeconds = Integer.valueOf(request.getParameter("expreSeconds"));
        QrCodeResponse qrcode2 = Engine.getWeixinmpController().getQrCodeService().createQrcode(type, sceneId, expreSeconds);
        File file = Engine.getWeixinmpController().getQrCodeService().getQrCodeAsFile(qrcode2.ticket);
        write(response, qrcode2.toString() + "  二维码已下载到本地：<a href='file:///" + file.getAbsolutePath().replaceAll("\\\\", "/") + "'>" + file.getAbsolutePath() + "</a>");
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
        String file = request.getParameter("file");
        String userId = request.getParameter("userId");
        Media media = Engine.getWeixinmpController().getMediaLibraryService().uploadMedia(MediaLibraryService.TYPE.image, new File(file));
        if (userId != null && userId.trim().length() > 0) {
            Engine.getWeixinmpController().getMessageService().sendImage(userId, media.media_id);
        }
        write(response, media);
    }

    public void downloadMedia(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String mediaId = request.getParameter("mediaId");
        String filenameSuffix = request.getParameter("filenameSuffix");
        File file = Engine.getWeixinmpController().getMediaLibraryService().getMedia(mediaId, filenameSuffix);
        write(response, "媒体文件已下载到本地：<a href='file:///" + file.getAbsolutePath().replaceAll("\\\\", "/") + "'>" + file.getAbsolutePath() + "</a>");
    }

}
