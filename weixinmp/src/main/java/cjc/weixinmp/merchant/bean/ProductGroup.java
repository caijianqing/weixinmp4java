package cjc.weixinmp.merchant.bean;

import java.io.Serializable;
import java.util.List;

import cjc.weixinmp.DataBuilder.Requried;
import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.bean.GlobalError;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 商品分组请求对象
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年6月10日 下午1:15:11, https://github.com/caijianqing/weixinmp4java/
 * 
 */
public class ProductGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 分组ID */
    @Requried({ RequriedType.UPDATE })
    @SerializedName("group_id")
    public Integer groupId;

    /** 分组详情 */
    @Requried({ RequriedType.ADD })
    @SerializedName("group_detail")
    public GroupDetail groupDetail;

    /**
     * 动作集合<br>
     * API名称为“product”，个人认为这个名字更合适
     */
    @Requried({ RequriedType.UPDATE })
    @SerializedName("product")
    public List<ModAction> ModActionList;

    /**
     * 分组详情
     */
    public static class GroupDetail implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 分组ID */
        @SerializedName("group_id")
        public Integer groupId;

        /** 分组名称 */
        @Requried({ RequriedType.ADD })
        @SerializedName("group_name")
        public String groupName;

        /** 商品 ID 集合 */
        @Requried({ RequriedType.ADD })
        @SerializedName("product_list")
        public List<String> productIdList;

    }

    /**
     * 动作描述
     */
    public static class ModAction implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 商品ID */
        @Requried({ RequriedType.UPDATE })
        @SerializedName("product_id")
        public String productId;

        /** 修改操作(0-删除, 1-增加) */
        @Requried({ RequriedType.UPDATE })
        @SerializedName("mod_action")
        public Integer modAction;

    }

    /**
     * 服务器响应
     */
    public static class Response extends GlobalError {

        private static final long serialVersionUID = 1L;

        /** 分组ID */
        @SerializedName("group_id")
        public Integer groupId;

        /** 分组信息 */
        @SerializedName("group_detail")
        public GroupDetail groupDetail;

        /** 分组集合 */
        @SerializedName("groups_detail")
        public List<GroupDetail> groupDetailList;

    }

}
