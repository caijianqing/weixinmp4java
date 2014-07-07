package cjc.weixinmp.merchant.bean;

import java.io.Serializable;
import java.util.List;

import cjc.weixinmp.DataBuilder.Requried;
import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.merchant.builder.ExpressTemplateBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 邮费模板
 * 提示：构建这个对象请使用方便快捷的{@link ExpressTemplateBuilder}数据构造器
 * @author jianqing.cai@qq.com, 2014年6月9日 下午11:26:24, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class ExpressTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 邮费模板ID */
    @Requried({ RequriedType.UPDATE })
    @SerializedName("template_id")
    public Integer templateId;

    /** 邮费模板信息 */
    @Requried({ RequriedType.ADD, RequriedType.UPDATE })
    @SerializedName("delivery_template")
    public DeliveryTemplate deliveryTemplate;

    @Override
    public String toString() {
        return "ExpressTemplate [templateId=" + templateId + ", deliveryTemplate=" + deliveryTemplate + "]";
    }

    /**
     * 邮费模板信息<br>
     * 提示：构建这个对象请使用方便快捷的{@link ExpressTemplateBuilder#build(RequriedType)}.deliveryTemplate数据构造器
     */
    public static class DeliveryTemplate implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 邮费模板ID(仅在查询时返回) */
        @SerializedName("Id")
        public String id;

        /** 邮费模板名称 */
        @Requried({ RequriedType.ADD, RequriedType.UPDATE })
        @SerializedName("Name")
        public String name;

        /** 支付方式(0-买家承担运费, 1-卖家承担运费) */
        @Requried({ RequriedType.ADD, RequriedType.UPDATE })
        @SerializedName("Assumer")
        public Integer assumer;

        /** 计费单位(0-按件计费, 1-按重量计费, 2-按体 积计费，目前只支持按件计费，默认为0) */
        @Requried({ RequriedType.ADD, RequriedType.UPDATE })
        @SerializedName("Valuation")
        public Integer valuation;

        /** 具体运费计算 */
        @Requried({ RequriedType.ADD, RequriedType.UPDATE })
        @SerializedName("TopFee")
        public List<TopFee> topFee;

        @Override
        public String toString() {
            return "DeliveryTemplate [name=" + name + ", assumer=" + assumer + ", valuation=" + valuation + ", topFee=" + topFee + "]";
        }

        /**
         * 具体运费计算
         */
        public static class TopFee implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 快递类型 ID(参见增加商品/快递列表) */
            @Requried({ RequriedType.ADD, RequriedType.UPDATE })
            @SerializedName("Type")
            public Integer type;

            /** 默认邮费计算方法 */
            @Requried({ RequriedType.ADD, RequriedType.UPDATE })
            @SerializedName("Normal")
            public Normal normal;

            /** 指定地区邮费计算方法 */
            @SerializedName("Custom")
            public List<Custom> list;

            @Override
            public String toString() {
                return "TopFee [type=" + type + ", normal=" + normal + ", list=" + list + "]";
            }

            /**
             * 默认邮费计算方法
             */
            public static class Normal implements Serializable {

                private static final long serialVersionUID = 1L;

                /** 起始计费数量(比如计费单位是按件, 填 2 代表起始计费为 2 件) */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("StartStandards")
                public Integer startStandards;

                /** 起始计费金额(单位: 分） */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("StartFees")
                public Integer startFees;

                /** 递增计费数量 */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("AddStandards")
                public Integer addStandards;

                /** 递增计费金额(单位 : 分) */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("AddFees")
                public Integer addFees;

                @Override
                public String toString() {
                    return "Normal [startStandards=" + startStandards + ", startFees=" + startFees + ", addStandards=" + addStandards + ", addFees=" + addFees + "]";
                }

            }

            /**
             * 指定地区邮费计算方法
             */
            public static class Custom implements Serializable {

                private static final long serialVersionUID = 1L;

                /** 起始计费数量 */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("StartStandards")
                public Integer startStandards;

                /** 起始计费金额(单位: 分） */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("StartFees")
                public Integer startFees;

                /** 递增计费数量 */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("AddStandards")
                public Integer addStandards;

                /** 递增计费金额(单位 : 分) */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("AddFees")
                public Integer addFees;

                /** 指定国家(详见《地区列表》说明) */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("DestCountry")
                public String destCountry;

                /** 指定省份(详见《地区列表》说明) */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("DestProvince")
                public String destProvince;

                /** 指定城市(详见《地区列表》说明) */
                @Requried({ RequriedType.ADD, RequriedType.UPDATE })
                @SerializedName("DestCity")
                public String destCity;

                @Override
                public String toString() {
                    return "Custom [startStandards=" + startStandards + ", startFees=" + startFees + ", addStandards=" + addStandards + ", addFees=" + addFees + ", destCountry=" + destCountry + ", destProvince=" + destProvince + ", destCity=" + destCity + "]";
                }

            }

        }
    }

}
