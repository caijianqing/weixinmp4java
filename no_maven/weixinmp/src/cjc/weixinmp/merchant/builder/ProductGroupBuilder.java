package cjc.weixinmp.merchant.builder;

import java.util.ArrayList;
import java.util.List;

import cjc.weixinmp.DataBuilder;
import cjc.weixinmp.merchant.bean.ProductGroup;
import cjc.weixinmp.merchant.bean.ProductGroup.ModAction;
import cjc.weixinmp.merchant.bean.ProductGroup.GroupDetail;

/**
 * <pre>
 * 商品分组数据构造器
 * 
 * 用于方便快捷地构造商品分组数据
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年7月4日 下午10:48:26, https://github.com/caijianqing/weixinmp4java/
 */
public class ProductGroupBuilder extends DataBuilder<ProductGroup> {

    GroupDetailBuilder groupDetailBuilder;

    /**
     * 设置分组ID
     * 
     * @param groupId
     */
    public void setGroupId(int groupId) {
        p.groupId = groupId;
    }

    private List<ModAction> getModActionList() {
        if (p.ModActionList == null) {
            p.ModActionList = new ArrayList<>();
        }
        return p.ModActionList;
    }

    /**
     * 增加一个修改动作
     * 
     * @return
     */
    public ModActionBuilder addModAction() {
        ModActionBuilder builder = new ModActionBuilder();
        getModActionList().add(builder.modAction);
        return builder;
    }

    /**
     * 返回分组详情构造器
     * 
     * @return 支持链式操作
     */
    public GroupDetailBuilder getGroupDetailBuilder() {
        if (groupDetailBuilder == null) {
            groupDetailBuilder = new GroupDetailBuilder().p(this);
            p.groupDetail = groupDetailBuilder.groupDetail;
        }
        return groupDetailBuilder;
    }

    /**
     * 分组详情数据构造器
     */
    public static class GroupDetailBuilder extends SubBuilder<ProductGroupBuilder, GroupDetailBuilder> {

        final GroupDetail groupDetail = new GroupDetail();

        /**
         * 设置分组ID
         * 
         * @param groupId 分组ID
         * @return
         */
        public GroupDetailBuilder setGroupId(Integer groupId) {
            groupDetail.groupId = groupId;
            return this;
        }

        /**
         * 设置分组名称
         * 
         * @param groupName 分组名称
         * @return
         */
        public GroupDetailBuilder setGroupName(String groupName) {
            groupDetail.groupName = groupName;
            return this;
        }

        /**
         * 增加一个产品ID
         * 
         * @param productId 产品ID，例如：pDF3iY9cEWyMimNlKbik_NYJTzYU
         * @return
         */
        public GroupDetailBuilder addProductId(String productId) {
            if (groupDetail.productIdList == null) {
                groupDetail.productIdList = new ArrayList<>();
            }
            groupDetail.productIdList.add(productId);
            return this;
        }

    }

    /**
     * 修改动作
     */
    public static class ModActionBuilder extends SubBuilder<ProductGroupBuilder, GroupDetailBuilder> {

        final ModAction modAction = new ModAction();

        /**
         * 增加一个商品，与removeProduct二选一
         * 
         * @param productId 商品ID
         */
        public void addProduct(String productId) {
            modAction.modAction = 1;
            modAction.productId = productId;
        }

        /**
         * 删除一个商品，与addProduct二选一
         * 
         * @param productId 商品ID
         */
        public void removeProduct(String productId) {
            modAction.modAction = 0;
            modAction.productId = productId;
        }

    }

}
