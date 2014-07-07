package cjc.weixinmp.merchant.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.ProductRequest.Response.Category;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 商品查询对象
 * @author jianqing.cai@qq.com, 2014年6月9日 下午10:38:44, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 商品ID */
    @SerializedName("product_id")
    public String productId;

    /** 商品状态(0-全部, 1-上架, 2-下架) */
    @SerializedName("status")
    public Integer status;

    /** 大分类 ID(根节点分类 id为1) */
    @SerializedName("cate_id")
    public String cateId;

    /**
     * 商品状态
     */
    public static enum ProductStatus {
        /** 0-全部 */
        ALL(0),
        /** 1-上架 */
        ONLINE(1),
        /** 2-下架 */
        OFFLINE(2);
        int status;

        ProductStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

    }

    /**
     * 服务器响应
     */
    public static class Response extends GlobalError {

        private static final long serialVersionUID = 1L;

        /** 商品ID（新增商品时使用） */
        @SerializedName("product_id")
        public String productId;

        /** 子分类集合（查询子分类时使用） */
        @SerializedName("cate_list")
        public List<Category> categoryList;

        /** SKU集合（查询SKU时使用） */
        @SerializedName("sku_table")
        public List<SkuTable> skuList;

        /** 属性集合（查询属性时使用） */
        @SerializedName("properties")
        public List<Properties> propertiesList;

        /** 商品详情 */
        @SerializedName("product_info")
        public Product product;

        /** 商品详情集合 */
        @SerializedName("products_info")
        public List<Product> productList;

        @Override
        public String toString() {
            return "Response [errcode=" + errcode + ", errmsg=" + errmsg + ", productId=" + productId + ", categoryList=" + categoryList + ", skuList=" + skuList + ", propertiesList=" + propertiesList + ", product=" + product + ", productList=" + productList + "]";
        }

        /**
         * 子分类
         */
        public static class Category implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 子分类ID */
            @SerializedName("id")
            public String id;

            /** 子分类名称 */
            @SerializedName("name")
            public String name;

            /** 子分类（不是API标准字段） */
            @Expose(deserialize = false)
            public List<Category> subCategoryList;

            @Override
            public String toString() {
                return "Category [id=" + id + ", name=" + name + "]";
            }

        }

        /**
         * SKU集合
         */
        public static class SkuTable implements Serializable {

            private static final long serialVersionUID = 1L;

            /** SKU ID */
            @SerializedName("id")
            public String id;

            /** SKU名称 */
            @SerializedName("name")
            public String name;

            /** SKU集合 */
            @SerializedName("value_list")
            public List<SkuValue> valueList;

            @Override
            public String toString() {
                return "SkuTable [id=" + id + ", name=" + name + ", valueList=" + valueList + "]";
            }

            /**
             * SkuValue
             */
            public static class SkuValue implements Serializable {

                private static final long serialVersionUID = 1L;

                /** Value Id */
                @SerializedName("id")
                public String id;

                /** Value名称 */
                @SerializedName("name")
                public String name;

                @Override
                public String toString() {
                    return "SkuValue [id=" + id + ", name=" + name + "]";
                }

            }
        }

        /**
         * 属性集合
         */
        public static class Properties implements Serializable {

            private static final long serialVersionUID = 1L;

            /** 属性 ID */
            @SerializedName("id")
            public String id;

            /** 属性名称 */
            @SerializedName("name")
            public String name;

            /** 属性值集合 */
            @SerializedName("property_list")
            public List<Property> propertyList;

            @Override
            public String toString() {
                return "Properties [id=" + id + ", name=" + name + ", propertyList=" + propertyList + "]";
            }

            /**
             * 属性值
             */
            public static class Property implements Serializable {

                private static final long serialVersionUID = 1L;

                /** 属性值 Id */
                @SerializedName("id")
                public String id;

                /** 属性值名称 */
                @SerializedName("name")
                public String name;

                @Override
                public String toString() {
                    return "Property [id=" + id + ", name=" + name + "]";
                }

            }
        }

    }

    public static void main(String[] args) {
        Response r = new Response();
        r.errcode = 0;
        r.errmsg = "success";
        r.categoryList = new ArrayList<Category>();
        Category c = new Category();
        c.id = "404040";
        c.name = "康妮";
        r.categoryList.add(c);
        Category c2 = new Category();
        c2.id = "88888888";
        c2.name = "康师傅";
        r.categoryList.add(c2);
        String s = new Gson().toJson(r);
        Response r2 = (new Gson().fromJson(s, Response.class));
        System.out.println(s);
        System.out.println(r2);
    }

}
