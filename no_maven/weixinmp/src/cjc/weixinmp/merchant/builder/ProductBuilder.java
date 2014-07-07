package cjc.weixinmp.merchant.builder;

import java.util.ArrayList;
import java.util.List;

import cjc.weixinmp.MerchantProductService;
import cjc.weixinmp.DataBuilder;
import cjc.weixinmp.merchant.bean.Product;
import cjc.weixinmp.merchant.bean.Product.BaseAttr;
import cjc.weixinmp.merchant.bean.Product.BaseAttr.Detail;
import cjc.weixinmp.merchant.bean.Product.BaseAttr.Property;
import cjc.weixinmp.merchant.bean.Product.BaseAttr.SkuInfo;
import cjc.weixinmp.merchant.bean.Product.DeliverInfo;
import cjc.weixinmp.merchant.bean.Product.DeliverInfo.Express;
import cjc.weixinmp.merchant.bean.Product.ExpressType;
import cjc.weixinmp.merchant.bean.Product.ExtAttr;
import cjc.weixinmp.merchant.bean.Product.ExtAttr.Location;
import cjc.weixinmp.merchant.bean.Product.Sku;

/**
 * <pre>
 * 商品数据构造器，用于便捷地构造商品数据模型
 * @author jianqing.cai@qq.com, 2014年6月24日 下午9:47:43, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class ProductBuilder extends DataBuilder<Product> {

    private BaseAttrBuilder baseAttrBuilder;

    private ExtAttrBuilder extAttrBuilder;

    private DeliverBuilder deliverBuilder;

    private LocationBuilder locationBuilder;

    /**
     * 设置商品ID
     * 
     * @param productId 商品ID
     */
    public void setId(String productId) {
        p.productId = productId;
    }

    /**
     * 设置商品状态
     * 
     * @param status 商品状态
     */
    public void setStatus(Status status) {
        p.status = status.state;
    }

    /**
     * 返回支持链式操作的 基本属性 构造器
     * 
     * @return
     */
    public BaseAttrBuilder getBaseAttrBuilder() {
        if (baseAttrBuilder == null) {
            baseAttrBuilder = new BaseAttrBuilder().p(this);
        }
        return baseAttrBuilder;
    }

    /**
     * 返回支持链式操作的 扩展属性 构造器
     * 
     * @return
     */
    public ExtAttrBuilder getExtAttrBuilder() {
        if (extAttrBuilder == null) {
            extAttrBuilder = new ExtAttrBuilder().p(this);
        }
        return extAttrBuilder;
    }

    /**
     * 返回支持链式操作的 运费属性 构造器
     * 
     * @return
     */
    public DeliverBuilder getDeliverBuilder() {
        if (deliverBuilder == null) {
            deliverBuilder = new DeliverBuilder().p(this);
        }
        return deliverBuilder;
    }

    /**
     * 返回支持链式操作的 商品所在地信息 构造器
     */
    public LocationBuilder getLocationBuilder() {
        if (locationBuilder == null) {
            locationBuilder = new LocationBuilder();
            getAttrext().location = locationBuilder.location;
        }
        return locationBuilder;
    }

    /**
     * 新增一个SKU（最小商品单元）
     * 
     * @return 返回的是一个支持链式操作的SKU构造器
     */
    public SkuBuilder addSku() {
        SkuBuilder sku = new SkuBuilder();
        getSkuList().add(sku.sku);
        return sku;
    }

    // //////////////////////////////////////////////

    private BaseAttr getBaseAttr() {
        if (p.baseAttr == null) {
            p.baseAttr = new BaseAttr();
        }
        return p.baseAttr;
    }

    private List<Sku> getSkuList() {
        if (p.skuList == null) {
            p.skuList = new ArrayList<Sku>();
        }
        return p.skuList;
    }

    private ExtAttr getAttrext() {
        if (p.extattr == null) {
            p.extattr = new ExtAttr();
        }
        return p.extattr;
    }

    private DeliverInfo getDeliverInfo() {
        if (p.deliveryInfo == null) {
            p.deliveryInfo = new DeliverInfo();
        }
        return p.deliveryInfo;
    }

    private List<String> getCategoryIdList() {
        if (getBaseAttr().categoryIdList == null) {
            getBaseAttr().categoryIdList = new ArrayList<String>();
        }
        return getBaseAttr().categoryIdList;
    }

    private List<Property> getPropertyList() {
        if (getBaseAttr().propertyList == null) {
            getBaseAttr().propertyList = new ArrayList<Property>();
        }
        return getBaseAttr().propertyList;
    }

    private List<SkuInfo> getSkuInfoList() {
        if (getBaseAttr().skuInfoList == null) {
            getBaseAttr().skuInfoList = new ArrayList<SkuInfo>();
        }
        return getBaseAttr().skuInfoList;
    }

    private List<String> getImgList() {
        if (getBaseAttr().imgList == null) {
            getBaseAttr().imgList = new ArrayList<String>();
        }
        return getBaseAttr().imgList;
    }

    private List<Detail> getDetailList() {
        if (getBaseAttr().detailList == null) {
            getBaseAttr().detailList = new ArrayList<Detail>();
        }
        return getBaseAttr().detailList;
    }

    // //////////////////////////////////////////////

    /**
     * 商品状态枚举
     */
    public static enum Status {

        /** 全部 */
        ALL(0),

        /** 上架 */
        ONLINE(1),

        /** 下架 */
        OFFLINE(2);
        int state;

        Status(int state) {
            this.state = state;
        }

    }

    /**
     * 基本属性构造器
     */
    public static class BaseAttrBuilder extends SubBuilder<ProductBuilder, BaseAttrBuilder> {

        /**
         * 设置商品名称
         * 
         * @param productName 商品名称
         */
        public BaseAttrBuilder setName(String productName) {
            p.getBaseAttr().name = productName;
            return this;
        }

        /**
         * 设置商品分类（已存在的分类）
         * 
         * @param categoryId 商品分类ID
         * @see MerchantProductService#getSkuByCategory()
         * @see MerchantProductService#getSubCategory(String, boolean)
         */
        public BaseAttrBuilder setCategoryId(String categoryId) {
            p.getCategoryIdList().clear();
            p.getCategoryIdList().add(categoryId);
            return this;
        }

        /**
         * 设置商品分类集合（已存在的分类）
         * 
         * @param categoryIds 商品分类ID集合
         * @see MerchantProductService#getSkuByCategory()
         * @see MerchantProductService#getSubCategory(String, boolean)
         */
        public BaseAttrBuilder setCategoryIdList(List<String> categoryIdList) {
            p.getCategoryIdList().clear();
            p.getCategoryIdList().addAll(categoryIdList);
            return this;
        }

        /**
         * 设置商品限购数量
         * 
         * @param limit 用户商品限购数量
         */
        public BaseAttrBuilder setBuyLimit(int limit) {
            p.getBaseAttr().buyLimit = limit;
            return this;
        }

        /**
         * 设置主图URL
         * 
         * @param mainImg 商品主图(图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品。图片分辨率推荐尺寸为640×600)
         */
        public BaseAttrBuilder setMainImg(String mainImg) {
            p.getBaseAttr().mainImg = mainImg;
            return this;
        }

        /**
         * 添加一张商品图片
         * 
         * @param img 商品图片列表(图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品。图片分辨率推荐尺寸为640×600)
         */
        public BaseAttrBuilder addImg(String img) {
            p.getImgList().add(img);
            return this;
        }

        /**
         * 添加一个属性
         * 
         * @param prop 字符窜格式：id:vid，例如：12345:000903
         */
        public BaseAttrBuilder addProperty(String prop) {
            String[] ps = prop.split(":");
            Property property = new Property();
            property.id = ps[0];
            property.vid = ps[1];
            p.getPropertyList().add(property);
            return this;
        }

        /**
         * 增加一个商品详情，显示在客户端的商品详情页内
         * 
         * @param text 文字描述
         * @param img 图片(图片需调用图片上传接口获得图片Url填写至此，否则无法添加商品)
         */
        public BaseAttrBuilder addDeatil(String text, String img) {
            Detail d = new Detail();
            d.text = text;
            d.img = img;
            p.getDetailList().add(d);
            return this;
        }

        /**
         * 添加一个SKU
         * 
         * @param id sku属性(SKU列表中id, 支持自定义SKU，格式为"$xxx"，xxx即为显示在客户端中的字符串)
         * @param vidList sku值的集合，(SKU列表中vid, 如需自定义SKU，格式为"$xxx"，xxx即为显示在客户端中的字符串)
         */
        public BaseAttrBuilder addSku(String id, List<String> vidList) {
            SkuInfo sku = new SkuInfo();
            sku.id = id;
            sku.vidList = vidList;
            p.getSkuInfoList().add(sku);
            return this;
        }

        /**
         * 添加一个SKU定义（不同于SKU单元(一个确定的商品)）
         * 
         * @param id sku属性(SKU列表中id, 支持自定义SKU，格式为"$xxx"，xxx即为显示在客户端中的字符串)
         * @param vids sku值的集合，(SKU列表中vid, 如需自定义SKU，格式为"$xxx"，xxx即为显示在客户端中的字符串)
         */
        public BaseAttrBuilder addSkuInfo(String id, String[] vids) {
            SkuInfo sku = new SkuInfo();
            sku.id = id;
            sku.vidList = new ArrayList<String>();
            for (String vid : vids) {
                sku.vidList.add(vid);
            }
            p.getSkuInfoList().add(sku);
            return this;
        }

    }

    /**
     * SKU构造器
     */
    public static class SkuBuilder {

        final Sku sku = new Sku();

        /**
         * 设置SKU信息
         * 
         * @param skuId sku信息, 参照上述sku_table的定义; 格式 : "id1:vid1;id2:vid2" 规则 : id_info的组合个数必须与sku_table个数一致(若商品无sku信息, 即商品为统一规格，则此处赋值为空字符串即可)
         * @return 返回当前对象以支持链式操作
         */
        public SkuBuilder setSkuId(String skuId) {
            sku.skuId = skuId;
            return this;
        }

        /**
         * 设置SKU原价
         * 
         * @param oriPrice sku原价(单位 : 分)
         * @return 返回当前对象以支持链式操作
         */
        public SkuBuilder setOriPrice(Integer oriPrice) {
            sku.oriPrice = oriPrice;
            return this;
        }

        /**
         * 设置微信价
         * 
         * @param price sku微信价(单位 : 分, 微信价必须比原价小, 否则添加商品失败)
         * @return 返回当前对象以支持链式操作
         */
        public SkuBuilder setPrice(Integer price) {
            sku.price = price;
            return this;
        }

        /**
         * 设置商品图标（超小图）
         * 
         * @param iconUrl sku iconurl(图片需调用图片上传接口获得图片Url)
         * @return 返回当前对象以支持链式操作
         */
        public SkuBuilder setIconUrl(String iconUrl) {
            sku.iconUrl = iconUrl;
            return this;
        }

        /**
         * 设置sku库存
         * 
         * @param quantity sku库存
         * @return 返回当前对象以支持链式操作
         */
        public SkuBuilder setQuantity(Integer quantity) {
            sku.quantity = quantity;
            return this;
        }

        /**
         * 设置商家商品编码
         * 
         * @param productCode 商家商品编码
         * @return 返回当前对象以支持链式操作
         */
        public SkuBuilder setProductCode(String productCode) {
            sku.productCode = productCode;
            return this;
        }

    }

    /**
     * 扩展属性构造器
     */
    public static class ExtAttrBuilder extends SubBuilder<ProductBuilder, ExtAttrBuilder> {

        /**
         * 设置是否包邮
         * 
         * @param isPostFree 是否包邮(0-否, 1-是), 如果包邮delivery_info字段可省略
         */
        public ExtAttrBuilder setPostFree(boolean isPostFree) {
            p.getAttrext().isPostFree = isPostFree ? 1 : 0;
            return this;
        }

        /**
         * 设置是否提供发票
         * 
         * @param isHasReceipt 是否提供发票(0-否, 1-是)
         */
        public ExtAttrBuilder setHasReceipt(boolean isHasReceipt) {
            p.getAttrext().isHasReceipt = isHasReceipt ? 1 : 0;
            return this;
        }

        /**
         * 设置是否支持保修
         * 
         * @param isUnderGuaranty 是否保修(0-否, 1-是)
         */
        public ExtAttrBuilder setUnderGuaranty(boolean isUnderGuaranty) {
            p.getAttrext().isUnderGuaranty = isUnderGuaranty ? 1 : 0;
            return this;
        }

        /**
         * 设置是否支持退换货
         * 
         * @param isSupportReplace 是否支持退换货(0-否, 1-是)
         */
        public ExtAttrBuilder setSupportReplace(boolean isSupportReplace) {
            p.getAttrext().isSupportReplace = isSupportReplace ? 1 : 0;
            return this;
        }

    }

    /**
     * 商品所在地信息构造器
     */
    public static class LocationBuilder {

        Location location = new Location();

        public LocationBuilder setCountry(String country) {
            location.country = country;
            return this;
        }

        public LocationBuilder setProvince(String province) {
            location.province = province;
            return this;
        }

        public LocationBuilder setCity(String city) {
            location.city = city;
            return this;
        }

        public LocationBuilder setAddress(String address) {
            location.address = address;
            return this;
        }

    }

    /**
     * 运费性构造器
     */
    public static class DeliverBuilder extends SubBuilder<ProductBuilder, DeliverBuilder> {

        /**
         * 设置使用邮费模板<br>
         * 注意：此方法与addDeliverExpress(ExpressType type, int price)互相覆盖
         * 
         * @param templateId 邮费模板ID
         */
        public DeliverBuilder setDeliverTemplateId(int templateId) {
            p.getDeliverInfo().deliveryType = 0;
            p.getDeliverInfo().templateId = templateId;
            p.getDeliverInfo().expressList = null;
            return this;
        }

        /**
         * 增加一个快递方式 <br>
         * 注意：此方法与setDeliverTemplateId(int templateId)互相覆盖
         * 
         * @param type
         * @param price
         */
        public DeliverBuilder addDeliverExpress(ExpressType type, int price) {
            p.getDeliverInfo().deliveryType = 1;
            p.getDeliverInfo().templateId = null;
            if (p.getDeliverInfo().expressList == null) {
                p.getDeliverInfo().expressList = new ArrayList<Express>();
            }
            Express e = new Express();
            e.id = type.getId();
            e.price = price;
            p.getDeliverInfo().expressList.add(e);
            return this;
        }
    }

}
