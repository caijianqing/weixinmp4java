package cjc.weixinmp.merchant.builder;

import java.util.ArrayList;
import java.util.List;

import cjc.weixinmp.DataBuilder;
import cjc.weixinmp.merchant.bean.ExpressTemplate;
import cjc.weixinmp.merchant.bean.ExpressTemplate.DeliveryTemplate;
import cjc.weixinmp.merchant.bean.ExpressTemplate.DeliveryTemplate.TopFee;
import cjc.weixinmp.merchant.bean.ExpressTemplate.DeliveryTemplate.TopFee.Custom;
import cjc.weixinmp.merchant.bean.ExpressTemplate.DeliveryTemplate.TopFee.Normal;
import cjc.weixinmp.merchant.bean.Product.ExpressType;

/**
 * <pre>
 * 快递模板构造器
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年7月4日 下午8:56:59, https://github.com/caijianqing/weixinmp4java/
 */
public class ExpressTemplateBuilder extends DataBuilder<ExpressTemplate> {

    /**
     * 设置邮费模板ID（更新和查询时使用）
     * 
     * @param templateId
     */
    public void setId(int templateId) {
        p.templateId = templateId;
    }

    /**
     * 设置邮费模板名称
     * 
     * @param name 邮费模板名称
     */
    public void setName(String name) {
        getDeliveryTemplate().name = name;
    }

    /**
     * 设置支付方式
     * 
     * @param assumer (0-买家承担运费, 1-卖家承担运费)
     */
    public void setAssumer(Assumer assumer) {
        getDeliveryTemplate().assumer = assumer.getValue();
    }

    /**
     * 设置计费单位
     * 
     * @param valuation (0-按件计费, 1-按重量计费, 2-按体积计费，目前只支持按件计费，默认为0)
     */
    public void setValuation(Valuation valuation) {
        getDeliveryTemplate().valuation = valuation.getValue();
    }

    /**
     * 增加一个运费计费策略
     * 
     * @return 返回支持链式操作的运费构造器
     */
    public TopFeeBuilder addTopFee() {
        TopFeeBuilder topFeeBuilder = new TopFeeBuilder();
        getTopFeeList().add(topFeeBuilder.topFee);
        return topFeeBuilder;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////

    private DeliveryTemplate getDeliveryTemplate() {
        if (p.deliveryTemplate == null) {
            p.deliveryTemplate = new DeliveryTemplate();
        }
        return p.deliveryTemplate;
    }

    private List<TopFee> getTopFeeList() {
        if (getDeliveryTemplate().topFee == null) {
            getDeliveryTemplate().topFee = new ArrayList<>();
        }
        return getDeliveryTemplate().topFee;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 邮费承担方
     */
    public static enum Assumer {
        /** 买家承担，即不包邮 */
        Buyers(0),
        /** 卖家承担，即包邮 */
        Seller(1);

        int value;

        Assumer(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    /**
     * 计费方式
     */
    public static enum Valuation {
        /** 按件计费(目前只支持按件计费，默认为0) */
        Item(0),
        /** 按重量收费 */
        Weight(1),
        /** 按体积收费 */
        Volume(2);

        int value;

        Valuation(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    // //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 运费构造器
     */
    public static class TopFeeBuilder {

        final TopFee topFee = new TopFee();

        final NormalBuilder normalBuilder = new NormalBuilder();

        /**
         * 设置快递类型ID
         * 
         * @param expressType 快递类型ID(参见增加商品/快递列表)
         * @return
         */
        public TopFeeBuilder setType(ExpressType expressType) {
            topFee.type = expressType.getId();
            return this;
        }

        /**
         * 返回“默认邮费计算方法”构造器
         * 
         * @return 支持链式操作
         */
        public NormalBuilder getNormalBuilder() {
            topFee.normal = normalBuilder.normal;
            return normalBuilder;
        }

        /**
         * 增加一个“指定地区邮费计算方法”构造器
         * 
         * @return
         */
        public CustomerBuilder addCustomer() {
            CustomerBuilder builder = new CustomerBuilder();
            if (topFee.list == null) {
                topFee.list = new ArrayList<>();
            }
            topFee.list.add(builder.custom);
            return builder;
        }

    }

    /**
     * 具体运费计算
     */
    public static class NormalBuilder {

        final Normal normal = new Normal();

        /**
         * 设置起始计费数量
         * 
         * @param tartStandards 起始计费数量(比如计费单位是按件, 填2代表起始计费为2件)
         * @return
         */
        public NormalBuilder setStartStandards(int tartStandards) {
            normal.startStandards = tartStandards;
            return this;
        }

        /**
         * 设置起始计费金额(单位: 分）
         * 
         * @param startFees 起始计费金额(单位: 分）
         * @return
         */
        public NormalBuilder setStartFees(int startFees) {
            normal.startFees = startFees;
            return this;
        }

        /**
         * 设置递增计费数量
         * 
         * @param addStandards 递增计费数量
         * @return
         */
        public NormalBuilder setAddStandards(int addStandards) {
            normal.addStandards = addStandards;
            return this;
        }

        /**
         * 设置递增计费金额(单位 : 分)
         * 
         * @param addFees 递增计费金额(单位 : 分)
         * @return
         */
        public NormalBuilder setAddFees(int addFees) {
            normal.addFees = addFees;
            return this;
        }

    }

    public static class CustomerBuilder {

        final Custom custom = new Custom();

        /**
         * 设置起始计费数量
         * 
         * @param tartStandards 起始计费数量(比如计费单位是按件, 填2代表起始计费为2件)
         * @return
         */
        public CustomerBuilder setStartStandards(int tartStandards) {
            custom.startStandards = tartStandards;
            return this;
        }

        /**
         * 设置起始计费金额(单位: 分）
         * 
         * @param startFees 起始计费金额(单位: 分）
         * @return
         */
        public CustomerBuilder setStartFees(int startFees) {
            custom.startFees = startFees;
            return this;
        }

        /**
         * 设置递增计费数量
         * 
         * @param addStandards 递增计费数量
         * @return
         */
        public CustomerBuilder setAddStandards(int addStandards) {
            custom.addStandards = addStandards;
            return this;
        }

        /**
         * 设置递增计费金额(单位 : 分)
         * 
         * @param addFees 递增计费金额(单位 : 分)
         * @return
         */
        public CustomerBuilder setAddFees(int addFees) {
            custom.addFees = addFees;
            return this;
        }

        /**
         * 指定国家(详见《地区列表》说明)
         * 
         * @param destCountry 国家
         * @return
         */
        public CustomerBuilder setDestCountry(String destCountry) {
            custom.destCountry = destCountry;
            return this;
        }

        /**
         * 指定省份(详见《地区列表》说明)
         * 
         * @param destProvince 省份
         * @return
         */
        public CustomerBuilder setDestProvince(String destProvince) {
            custom.destProvince = destProvince;
            return this;
        }

        /**
         * 指定城市(详见《地区列表》说明)
         * 
         * @param destCity 地区
         * @return
         */
        public CustomerBuilder setDestCity(String destCity) {
            custom.destCity = destCity;
            return this;
        }

    }

}
