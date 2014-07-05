package cjc.weixinmp.merchant.builder;

import java.util.ArrayList;

import cjc.weixinmp.DataBuilder;
import cjc.weixinmp.merchant.bean.ShelfRequest;
import cjc.weixinmp.merchant.bean.ShelfRequest.ShelfData;
import cjc.weixinmp.merchant.bean.ShelfRequest.ShelfData.ModuleInfo;
import cjc.weixinmp.merchant.bean.ShelfRequest.ShelfData.ModuleInfo.GroupInfo;
import cjc.weixinmp.merchant.bean.ShelfRequest.ShelfData.ModuleInfo.GroupInfo.Filter;
import cjc.weixinmp.merchant.bean.ShelfRequest.ShelfData.ModuleInfo.GroupInfos;
import cjc.weixinmp.merchant.bean.ShelfRequest.ShelfData.ModuleInfo.GroupInfos.Group;

/**
 * <pre>
 * 货架控件数据构造器
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年7月5日 上午10:52:14, https://github.com/caijianqing/weixinmp4java/
 */
public class ShelfBuilder extends DataBuilder<ShelfRequest> {

    private Shelf1Builder shelf1Builder;

    private Shelf2Builder shelf2Builder;

    private Shelf3Builder shelf3Builder;

    private Shelf4Builder shelf4Builder;

    private Shelf5Builder shelf5Builder;

    /** 设置 货架控件ID，查询和更新时使用 */
    public void setId(int shelfId) {
        p.shelfId = shelfId;
    }

    /** 设置 货架控件名称 */
    public void setName(String shelfName) {
        p.shelfName = shelfName;
    }

    /**
     * 设置 货架控件招牌图片
     * 
     * @param shelfBanner 货架控件招牌图片Url(图片需调用图片上传接口获得图片Url填写至此，否则添加货架控件失败，建议尺寸为640*120，仅货架控件1-4有banner，货架控件5没有banner)
     */
    public void setBanner(String shelfBanner) {
        p.shelfBanner = shelfBanner;
    }

    /**
     * 返回货架控件1构造器<br>
     * 注意：货架控件1、2、3、4不能与货架控件5联合使用
     * 
     * @return 支持链式操作
     * @see #getShelf5Builder()
     */
    public Shelf1Builder getShelf1Builder() {
        if (shelf5Builder != null) {
            throw new RuntimeException("货架控件1、2、3、4不能与货架控件5联合使用");
        }
        if (shelf1Builder == null) {
            shelf1Builder = new Shelf1Builder();
            shelf1Builder.m.eid = 1;
            getShelfData().moduleInfoList.add(shelf1Builder.m);
        }
        return shelf1Builder;
    }

    /**
     * 返回货架控件2构造器<br>
     * 注意：货架控件1、2、3、4不能与货架控件5联合使用
     * 
     * @return 支持链式操作
     * @see #getShelf5Builder()
     */
    public Shelf2Builder getShelf2Builder() {
        if (shelf5Builder != null) {
            throw new RuntimeException("货架控件1、2、3、4不能与货架控件5联合使用");
        }
        if (shelf2Builder == null) {
            shelf2Builder = new Shelf2Builder();
            shelf2Builder.m.eid = 2; // 固定值
            getShelfData().moduleInfoList.add(shelf2Builder.m);
        }
        return shelf2Builder;
    }

    /**
     * 返回货架控件3构造器<br>
     * 注意：货架控件1、2、3、4不能与货架控件5联合使用
     * 
     * @return 支持链式操作
     * @see #getShelf5Builder()
     */
    public Shelf3Builder getShelf3Builder() {
        if (shelf5Builder != null) {
            throw new RuntimeException("货架控件1、2、3、4不能与货架控件5联合使用");
        }
        if (shelf3Builder == null) {
            shelf3Builder = new Shelf3Builder();
            shelf3Builder.m.eid = 3; // 固定值
            getShelfData().moduleInfoList.add(shelf3Builder.m);
        }
        return shelf3Builder;
    }

    /**
     * 返回货架控件4构造器<br>
     * 注意：货架控件1、2、3、4不能与货架控件5联合使用
     * 
     * @return 支持链式操作
     * @see #getShelf5Builder()
     */
    public Shelf4Builder getShelf4Builder() {
        if (shelf5Builder != null) {
            throw new RuntimeException("货架控件1、2、3、4不能与货架控件5联合使用");
        }
        if (shelf4Builder == null) {
            shelf4Builder = new Shelf4Builder();
            shelf4Builder.m.eid = 4; // 固定值
            getShelfData().moduleInfoList.add(shelf4Builder.m);
        }
        return shelf4Builder;
    }

    /**
     * 返回货架控件5构造器<br>
     * 注意：货架控件1、2、3、4不能与货架控件5联合使用
     * 
     * @return 支持链式操作
     * @see #getShelf1Builder()
     * @see #getShelf2Builder()
     * @see #getShelf3Builder()
     * @see #getShelf4Builder()
     */
    public Shelf5Builder getShelf5Builder() {
        if (shelf1Builder != null//
                || shelf2Builder != null//
                || shelf3Builder != null//
                || shelf4Builder != null) {
            throw new RuntimeException("货架控件5不能与货架控件1、2、3、4联合使用");
        }
        if (shelf5Builder == null) {
            shelf5Builder = new Shelf5Builder();
            shelf5Builder.m.eid = 5; // 固定值
            getShelfData().moduleInfoList.add(shelf5Builder.m);
        }
        return shelf5Builder;
    }

    private ShelfData getShelfData() {
        if (p.shelfData == null) {
            p.shelfData = new ShelfData();
            p.shelfData.moduleInfoList = new ArrayList<>();
        }
        return p.shelfData;
    }

    /**
     * <pre>
     * 货架控件1构造器
     * 
     * 货架控件1是由一个分组组成，展示该分组指定数量的商品列表，
     * 可与货架控件2、货架控件3、货架控件4联合使用。
     * </pre>
     */
    public static class Shelf1Builder {

        final ModuleInfo m;

        Shelf1Builder() {
            m = new ModuleInfo();
            m.groupInfo = new GroupInfo();
            m.groupInfo.filter = new Filter();
        }

        /**
         * 设置 该货架控件展示商品个数
         * 
         * @param count 该货架控件展示商品个数
         */
        public Shelf1Builder setCount(int count) {
            m.groupInfo.filter.count = count;
            return this;
        }

        /**
         * 分组ID
         * 
         * @param groupId 分组ID
         */
        public Shelf1Builder setGroupId(int groupId) {
            m.groupInfo.groupId = groupId;
            return this;
        }

    }

    /**
     * 货架控件2构造器<br>
     * 货架控件2是由多个分组组成（最多有4个分组），展示指定分组的名称，可与货架控件1、货架控件3、货架控件4联合使用。
     */
    public static class Shelf2Builder {

        final ModuleInfo m;

        Shelf2Builder() {
            m = new ModuleInfo();
            m.groupInfos = new GroupInfos();
            m.groupInfos.groups = new ArrayList<>();
        }

        /**
         * 增加一个分组
         * 
         * @param groupId 分组ID
         */
        public Shelf2Builder addGroupId(int groupId) {
            if (m.groupInfos.groups.size() >= 4) {
                throw new RuntimeException("货架控件2最多可以添加4个分组");
            }
            Group g = new Group();
            g.groupId = groupId;
            m.groupInfos.groups.add(g);
            return this;
        }

    }

    /**
     * 货架控件3构造器<br>
     * 货架控件3是由一个分组组成，展示指定分组的分组图片，可与货架控件1、货架控件2、货架控件4联合使用。
     */
    public static class Shelf3Builder {

        final ModuleInfo m;

        Shelf3Builder() {
            m = new ModuleInfo();
            m.groupInfo = new GroupInfo();
        }

        /**
         * 设置分组ID
         * 
         * @param groupId 分组ID
         */
        public Shelf3Builder setGroupId(int groupId) {
            m.groupInfo.groupId = groupId;
            return this;
        }

        /**
         * 设置分组照片
         * 
         * @param img 分组照片(图片需调用图片上传接口获得图片Url填写至此，否则添加货架控件失败，建议分辨率600*208)
         * @return
         */
        public Shelf3Builder setImg(String img) {
            m.groupInfo.img = img;
            return this;
        }

    }

    /**
     * 货架控件4构造器<br>
     * 货架控件4是由多个分组组成（最多3个分组），展示指定分组的分组图片，可与货架控件1、货架控件2、货架控件4联合使用。
     */
    public static class Shelf4Builder {

        final ModuleInfo m;

        Shelf4Builder() {
            m = new ModuleInfo();
            m.groupInfos = new GroupInfos();
            m.groupInfos.groups = new ArrayList<>();
        }

        /**
         * 增加一个分组，最多可以增加3个分组
         * 
         * @param groupId 分组ID
         * @param img 分组照片(图片需调用图片上传接口获得图片Url填写至此，否则添加货架控件失败，3个分组建议分辨率分别为: 350*350, 244*172, 244*172)
         * @return
         */
        public Shelf4Builder addGroupId(int groupId, String img) {
            if (m.groupInfos.groups.size() >= 3) {
                throw new RuntimeException("货架控件4最多只能增加3个分组");
            }
            Group g = new Group();
            g.groupId = groupId;
            g.img = img;
            m.groupInfos.groups.add(g);
            return this;
        }

    }

    /**
     * 货架控件5构造器<br>
     * 货架控件5是由多个分组组成，展示指定分组的名称，不可与其他货架控件联合使用。
     */
    public static class Shelf5Builder {

        final ModuleInfo m;

        Shelf5Builder() {
            m = new ModuleInfo();
            m.groupInfos = new GroupInfos();
            m.groupInfos.groups = new ArrayList<>();
        }

        /**
         * 设置背景图片（这个在API手册中没有描述，但在数据样例中有出现）
         * 
         * @param backgroundImg 图片地址，要求同其他一样
         * @return
         */
        public Shelf5Builder setBackground(String backgroundImg) {
            m.groupInfos.imgBackground = backgroundImg;
            return this;
        }

        /**
         * 增加一个分组
         * 
         * @param groupId 分组ID
         */
        public Shelf5Builder addGroupId(int groupId) {
            Group g = new Group();
            g.groupId = groupId;
            m.groupInfos.groups.add(g);
            return this;
        }

        /**
         * 增加一个分组
         * 
         * @param groupId 分组ID
         * @param img 分组照片(图片需调用图片上传接口获得图片Url填写至此，否则添加货架控件失败，建议分辨率640*1008)
         * @return
         */
        public Shelf5Builder addGroupId(int groupId, String img) {
            Group g = new Group();
            g.groupId = groupId;
            g.img = img;
            m.groupInfos.groups.add(g);
            return this;
        }

    }

}
