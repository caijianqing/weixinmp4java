package cjc.weixinmp.merchant.bean;

import java.io.Serializable;
import java.util.List;

import cjc.weixinmp.DataBuilder.Requried;
import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.bean.GlobalError;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 货架请求对象
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年6月10日 下午1:35:08, https://github.com/caijianqing/weixinmp4java/
 * 
 */
public class ShelfRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 货架ID */
    @Requried({ RequriedType.QUERY, RequriedType.UPDATE })
    @SerializedName("shelf_id")
    public Integer shelfId;

    /** 货架名称 */
    @Requried({ RequriedType.ADD, RequriedType.UPDATE })
    @SerializedName("shelf_name")
    public String shelfName;

    /**
     * 货架招牌图片 Url(图片需调用图片上传接口 获得图片 Url 填写至此，否则添加货架失败， 建议尺寸为 640*120，仅控件 1-4 有 banner， 控件 5 没有 banner)
     */
    @SerializedName("shelf_banner")
    @Requried({ RequriedType.ADD, RequriedType.UPDATE })
    public String shelfBanner;

    /**
     * 货架信息(数据说明详见《货架控件说明》) 【XXX 增加、修改数据时使用shelfData】<br>
     * 
     * @see #shelfInfo
     */
    @SerializedName("shelf_data")
    @Requried({ RequriedType.ADD, RequriedType.UPDATE })
    public ShelfData shelfData;

    /**
     * 货架信息(数据说明详见《货架控件说明》) 【XXX 获取数据时使用shelfInfo】<br>
     * 
     * @see #shelfData
     */
    @SerializedName("shelf_info")
    public ShelfData shelfInfo;

    /**
     * 货架信息(数据说明详见《货架控件说明》)
     */
    public static class ShelfData implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 模块集合 */
        @SerializedName("module_infos")
        @Requried({ RequriedType.ADD, RequriedType.UPDATE })
        public List<ModuleInfo> moduleInfoList;

        /**
         * 模块信息
         */
        public static class ModuleInfo implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 控件ID（目前有1-5个控件） */
            @Requried({ RequriedType.ADD, RequriedType.UPDATE })
            @SerializedName("eid")
            public Integer eid;

            /** 分组信息 */
            @SerializedName("group_info")
            public GroupInfo groupInfo;

            /** 分组数组 */
            @SerializedName("group_infos")
            public GroupInfos groupInfos;

            /**
             * 单个分组信息
             */
            public static class GroupInfo implements Serializable {

                private static final long serialVersionUID = 1L;

                /** filter（不知道怎么理解） */
                @SerializedName("filter")
                public Filter filter;

                /** 分组ID */
                @SerializedName("group_id")
                public Integer groupId;

                /** 分组照片( 图片需调用图片上传接口获得图 片 Url 填写至此，否则添加货架失败，建议 分辨率 600*208) */
                @SerializedName("img")
                public String img;

                /**
                 * 貌似过滤参数之类的东东
                 */
                public static class Filter implements Serializable {

                    private static final long serialVersionUID = 1L;

                    /** 该控件展示商品个数 */
                    @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                    @SerializedName("count")
                    public Integer count;

                }
            }

            /**
             * 多个分组的数组
             */
            public static class GroupInfos implements Serializable {

                private static final long serialVersionUID = 1L;

                /** 分组集合 */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                public List<Group> groups;

                /** 分组照片( 图片需调用图片上传接口获得图 片 Url 填写至此，否则添加货架失败，建议 分辨率 640*1008) */
                @SerializedName("img_background")
                public String imgBackground;

                /**
                 * 分组
                 */
                public static class Group implements Serializable {

                    private static final long serialVersionUID = 1L;

                    /** 分组ID */
                    @SerializedName("group_id")
                    public Integer groupId;

                    /** 分组照片( 图片需调用图片上传接口获得图 片 Url 填写至此，否则添加货架失败，建议 分辨率 600*208) */
                    @SerializedName("img")
                    public String img;

                }

            }

        }
    }

    /**
     * 服务器响应
     */
    public static class Response extends GlobalError {

        private static final long serialVersionUID = 1L;

        /** 货架ID */
        @SerializedName("shelf_id")
        public Integer shelfId;

        /** 货架名称 */
        @SerializedName("shelf_name")
        public String shelfName;

        /**
         * 货架招牌图片 Url(图片需调用图片上传接口 获得图片 Url 填写至此，否则添加货架失败， 建议尺寸为 640*120，仅控件 1-4 有 banner， 控件 5 没有 banner)
         */
        @SerializedName("shelf_banner")
        public String shelfBanner;

        /**
         * 货架信息(数据说明详见《货架控件说明》)
         */
        @SerializedName("shelf_info")
        public ShelfData shelfInfo;

        /** 货架集合 */
        @SerializedName("shelves")
        public List<ShelfRequest> shelveList;

    }
}
