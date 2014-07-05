package cjc.weixinmp.merchant.bean;

import java.io.Serializable;
import java.util.List;

import cjc.weixinmp.DataBuilder.Requried;
import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.merchant.builder.ProductBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 商品基础信息
 * 提示：构建Product对象请使用快速安全的{@link ProductBuilder}构造器
 * 
 * @author jianqing.cai@qq.com, 2014年6月9日 下午9:05:33, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 商品ID（修改产品信息时必须提供） */
    @Requried({ RequriedType.ADD, RequriedType.UPDATE })
    @SerializedName("product_id")
    public String productId;

    /** 基本属性 */
    @SerializedName("product_base")
    public BaseAttr baseAttr;

    /** sku 信息列表(可为多个)，每个 sku 信息串即为一个确定的商品，比如白色的37码的鞋子 */
    @SerializedName("sku_list")
    public List<Sku> skuList;

    /** 商品其他属性 */
    @SerializedName("attrext")
    public ExtAttr extattr;

    /** 运费信息 */
    @SerializedName("delivery_info")
    public DeliverInfo deliveryInfo;

    /** 商品状态(0-全部, 1-上架, 2-下架) */
    @SerializedName("status")
    public Integer status;

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", baseAttr=" + baseAttr + ", skuList=" + skuList + ", extattr=" + extattr + ", deliveryInfo=" + deliveryInfo + ", status=" + status + "]";
    }

    /**
     * 基本属性
     */
    public static class BaseAttr implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 【必选】商品名称 */
        @Requried({ RequriedType.ADD })
        @SerializedName("name")
        public String name;

        /** 【必选】商品分类 id，商品分类列表请通过《获取指定分类的所有子分类》获取 */
        @Requried({ RequriedType.ADD })
        @SerializedName("category_id")
        public List<String> categoryIdList;

        /** 【必选】商品主图(图片需调用图片上传接口获得图片 Url 填写至此，否则无法添加商品。图片分辨率推荐尺寸为 640×600) */
        @Requried({ RequriedType.ADD })
        @SerializedName("main_img")
        public String mainImg;

        /** 【必选】商品图片列表(图片需调用图片上传接口获得图片 Url 填写至此，否则无法添加商品。图片分辨率推荐尺寸为640×600) */
        @Requried({ RequriedType.ADD })
        @SerializedName("imgs")
        public List<String> imgList;

        /** 【必选】商品详情列表，显示在客户端的商品详情页内 */
        @Requried({ RequriedType.ADD })
        @SerializedName("detail")
        public List<Detail> detailList;

        /** 商品属性列表，属性列表请通过《获取指定分类的所有属性》获取 */
        @SerializedName("property")
        public List<Property> propertyList;

        /** 商品 sku 定义，SKU 列表请通过《获取指定子分类的所有 SKU》获取 */
        @SerializedName("sku_info")
        public List<SkuInfo> skuInfoList;

        /** 用户商品限购数量 */
        @SerializedName("buy_limit")
        public Integer buyLimit;

        @Override
        public String toString() {
            return "BaseAttr [name=" + name + ", categoryIdList=" + categoryIdList + ", mainImg=" + mainImg + ", imgList=" + imgList + ", detailList=" + detailList + ", propertyList=" + propertyList + ", skuInfoList=" + skuInfoList + ", buyLimit=" + buyLimit + "]";
        }

        /**
         * 商品详情列表，显示在客户端的商品详情页内
         */
        public static class Detail implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 文字描述 */
            @SerializedName("text")
            public String text;

            /** 图片(图片需调用图片上传接口获得图片 Url 填写至此，否则无法添加商品) */
            @SerializedName("img")
            public String img;

            @Override
            public String toString() {
                return "Detail [text=" + text + ", img=" + img + "]";
            }

        }

        /**
         * 商品属性列表，属性列表请通过《获取指定分类的所有属性》获取
         */
        public static class Property implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 属性 id */
            @SerializedName("id")
            public String id;

            /** 属性值 id */
            @SerializedName("vid")
            public String vid;

            @Override
            public String toString() {
                return "Property [id=" + id + ", vid=" + vid + "]";
            }

        }

        /**
         * 商品 sku 定义，SKU 列表请通过《获取指定子分类的所有 SKU》获取
         */
        public static class SkuInfo implements Serializable {

            private static final long serialVersionUID = 1L;

            /** sku 属性(SKU 列表中 id, 支持自定义 SKU，格式为 "$xxx"，xxx 即为显示在客户端中的字符串) */
            @SerializedName("id")
            public String id;

            /** sku 值(SKU 列表中 vid, 如需自定义 SKU，格式为 "$xxx"，xxx 即为显示在客户端中的字符串) */
            @SerializedName("vid")
            public List<String> vidList;

            @Override
            public String toString() {
                return "SkuInfo [id=" + id + ", vidList=" + vidList + "]";
            }

        }

    }

    /**
     * sku 信息列表(可为多个)，每个 sku 信息串即为一个确定的商品，比如白色的37码的鞋子
     */
    public static class Sku implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * sku 信息, 参照上述 sku_table 的定义; <br>
         * 格式 : "id1:vid1;id2:vid2" <br>
         * 规则 : id_info 的组合个数必须与 sku_table 个数一致<br>
         * (若商品无 sku 信息, 即商品为统一规格，则此处赋值为空字符串即可)<br>
         */
        @SerializedName("sku_id")
        public String skuId;

        /** sku 原价(单位 : 分) */
        @SerializedName("ori_price")
        public Integer oriPrice;

        /** sku 微信价(单位 : 分, 微信价必须比原价小, 否则添加商品失败) */
        @SerializedName("price")
        public Integer price;

        /** sku iconurl(图片需调用图片上传接口获得图片 Url) */
        @SerializedName("icon_url")
        public String iconUrl;

        /** sku 库存 */
        @SerializedName("quantity")
        public Integer quantity;

        /** 商家商品编码 */
        @SerializedName("product_code")
        public String productCode;

        @Override
        public String toString() {
            return "Sku [skuId=" + skuId + ", oriPrice=" + oriPrice + ", price=" + price + ", iconUrl=" + iconUrl + ", quantity=" + quantity + ", productCode=" + productCode + "]";
        }

    }

    /**
     * 商品其他属性
     */
    public static class ExtAttr implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 是否包邮(0-否, 1-是), 如果包邮 delivery_info 字段可省略 */
        @SerializedName("isPostFree")
        public Integer isPostFree;

        /** 是否提供发票(0-否, 1-是) */
        @SerializedName("isHasReceipt")
        public Integer isHasReceipt;

        /** 是否保修(0-否, 1-是) */
        @SerializedName("isUnderGuaranty")
        public Integer isUnderGuaranty;

        /** 是否支持退换货(0-否, 1-是) */
        @SerializedName("isSupportReplace")
        public Integer isSupportReplace;

        /** 商品所在地地址 */
        @SerializedName("location")
        public Location location;

        @Override
        public String toString() {
            return "ExtAttr [isPostFree=" + isPostFree + ", isHasReceipt=" + isHasReceipt + ", isUnderGuaranty=" + isUnderGuaranty + ", isSupportReplace=" + isSupportReplace + ", location=" + location + "]";
        }

        /**
         * 商品所在地地址
         */
        public static class Location implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 国家(详见《地区列表》说明) */
            @SerializedName("country")
            public String country;

            /** 省份(详见《地区列表》说明) */
            @SerializedName("province")
            public String province;

            /** 城市(详见《地区列表》说明) */
            @SerializedName("city")
            public String city;

            /** 地址 */
            @SerializedName("address")
            public String address;

            @Override
            public String toString() {
                return "Location [country=" + country + ", province=" + province + ", city=" + city + ", address=" + address + "]";
            }

        }

    }

    /**
     * 运费信息
     */
    public static class DeliverInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 运费类型(0-使用下面 express 字段的默认模板, 1-使用 template_id 代表的邮费模板, 详见邮费模板相关 API)
         */
        @Requried({ RequriedType.ADD })
        @SerializedName("delivery_type")
        public Integer deliveryType;

        /** 邮费模板 ID */
        @SerializedName("template_id")
        public Integer templateId;

        /** 快递列表 */
        @SerializedName("express")
        public List<Express> expressList;

        @Override
        public String toString() {
            return "DeliverInfo [deliveryType=" + deliveryType + ", templateId=" + templateId + ", expressList=" + expressList + "]";
        }

        /**
         * 快递
         */
        public static class Express implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 快递 ID（参考ExpressType） */
            @SerializedName("id")
            public Integer id;

            /** 运费(单位 : 分) */
            @SerializedName("price")
            public Integer price;

            @Override
            public String toString() {
                return "Express [id=" + id + ", price=" + price + "]";
            }

        }

    }

    /**
     * 快递类型
     */
    public static enum ExpressType {

        /** 平邮 */
        Surface(10000027),

        /** 快递 */
        Express(10000028),

        /** EMS */
        EMS(10000029);

        Integer id;

        ExpressType(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

    }

}
