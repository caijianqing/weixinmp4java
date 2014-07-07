package cjc.weixinmp;

import java.io.IOException;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.bean.GroupInfo;
import cjc.weixinmp.bean.GroupInfo.Group;
import cjc.weixinmp.bean.GroupInfo.Groups;
import cjc.weixinmp.bean.GroupInfo.UserGroup;
import cjc.weixinmp.bean.WeixinmpUser;
import cjc.weixinmp.bean.Users;

/**
 * 用户管理接口
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class UserManagerService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected UserManagerService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 增加一个用户分组
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:54:14
     * @param groupName 分组名称
     * @throws WeixinException 如果发生错误
     */
    public void addGroup(String groupName) throws WeixinException {
        controller.logInfo("创建用户分组：" + groupName);
        String url = controller.getProperty("groups_create_url", null, false);
        try {
            GroupInfo group = new GroupInfo();
            group.group.name = groupName;
            GroupInfo result = controller.postWithJson(url, group, GroupInfo.class, "addGroup");
            controller.logInfo("创建用户分组结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_AddUserGroupError", e.getMessage(), e);
        }
    }

    /**
     * 返回所有分组集合
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:54:36
     * @return 分组集合
     * @throws WeixinException 如果发生错误
     */
    public List<Group> getAllGroupList() throws WeixinException {
        controller.logInfo("查询所有用户分组");
        String url = controller.getProperty("groups_get_url", null, false);
        try {
            Groups result = controller.post(url, null, Groups.class, "getAllGroupList");
            controller.logInfo("查询所有用户分组结果：" + result);
            return result.groups;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        }
    }

    /**
     * 查找一个用户所在分组的ID（每个用户只能存在与一个分组）
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:55:44
     * @param openId 用户OpenID
     * @return 分组的ID，无分组返回null
     * @throws WeixinException 如果发生错误
     */
    public Integer getGroupIdByUser(String openId) throws WeixinException {
        controller.logInfo("查询用过所在分组：" + openId);
        String url = controller.getProperty("groups_getid_url", null, false);
        try {
            UserGroup ug = new UserGroup();
            ug.openid = openId;
            UserGroup result = controller.postWithJson(url, ug, UserGroup.class, "getGroupByUser");
            controller.logInfo("查询用过所在分组结果：" + result);
            return result.groupid;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_GetGroupByUserError", e.getMessage(), e);
        }
    }

    /**
     * 更新一个分组名称
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:56:58
     * @param groupdId 分组ID
     * @param groupName 新名词
     * @throws WeixinException 如果发生错误
     */
    public void updateGroupName(Integer groupdId, String groupName) throws WeixinException {
        controller.logInfo("修改分组名：groupdId=" + groupName + ", groupName=" + groupName);
        String url = controller.getProperty("groups_update_url", null, false);
        try {
            GroupInfo info = new GroupInfo();
            info.group.id = groupdId;
            info.group.name = groupName;
            GlobalError result = controller.postWithJson(url, info, GlobalError.class, "updateGroupName");
            controller.logInfo("修改分组名结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_UpdateUserGroupNameError", e.getMessage(), e);
        }
    }

    /**
     * 移动用户到指定分组
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:57:16
     * @param openId 用户OpenID
     * @param toGroupId 移动到这个分组
     * @throws WeixinException 如果发生错误
     */
    public void moveUserGroup(String openId, Integer toGroupId) throws WeixinException {
        controller.logInfo("移动用户到指定分组：openId=" + openId + ", toGroupId=" + toGroupId);
        String url = controller.getProperty("groups_members_update_url", null, false);
        try {
            UserGroup ug = new UserGroup();
            ug.openid = openId;
            ug.to_groupid = toGroupId;
            GlobalError result = controller.postWithJson(url, ug, GlobalError.class, "moveUserGroup");
            controller.logInfo("移动用户到指定分组结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_MoveUserGroupError", e.getMessage(), e);
        }
    }

    /**
     * 查询一个用户的详细信息
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:57:50
     * @param openId 用户OpenId
     * @return 这个用户的详细信息，null为找不到
     * @throws WeixinException 如果发生错误
     */
    public WeixinmpUser getUser(String openId) throws WeixinException {
        controller.logInfo("获取用户基本信息：openId=" + openId);
        String url = controller.getProperty("user_info_url", null, false);
        try {
            url = url.replaceFirst("OPENID", openId);
            WeixinmpUser result = controller.post(url, null, WeixinmpUser.class, "getUser");
            controller.logInfo("获取用户基本信息结果：" + result);
            return result;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        }
    }

    /**
     * 查询关注者列表
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:58:17
     * @param nextOpenId 用于分页查询的变量，从这个用户OpenId开始查询，null为从第一个用户开始
     * @return 用户OpenId的集合，每次查询返回10000个，超过10000个时使用nextOpenId参数
     * @throws WeixinException 如果发生错误
     */
    public Users getUserList(String nextOpenId) throws WeixinException {
        controller.logInfo("获取关注者列表 ：nextOpenId=" + nextOpenId);
        String url = controller.getProperty("user_list_url", null, false);
        if (nextOpenId != null) {
            url = url.replaceFirst("NEXT_OPENID", nextOpenId);
        } else {
            url = url.replaceFirst("NEXT_OPENID", "");
        }
        try {
            Users result = controller.post(url, null, Users.class, "getUserList");
            controller.logInfo("获取关注者列表 结果：" + result);
            return result;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        }
    }

}
