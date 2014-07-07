package cjc.weixinmp.test.builder;

import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.merchant.bean.Product;
import cjc.weixinmp.merchant.bean.Product.ExpressType;
import cjc.weixinmp.merchant.builder.ProductBuilder;

/**
 * <pre>
 * 商品测试样例
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年6月26日 上午12:01:11, https://github.com/caijianqing/weixinmp4java/
 */
public class ProductBuilderTest {

    public static Product build() {
        ProductBuilder p = new ProductBuilder();
        p.setId("39324808324");
        p.getBaseAttrBuilder() // 构建商品基本属性
                .setCategoryId("537074298") //
                .addProperty("1075741879:1079749967") //
                .addProperty("1075754127:1079795198") //
                .addProperty("1075777334:1079837440") //
                .setName("美源发彩") //
                .addSkuInfo("1075741873", new String[] { "1079742386", "1079742363" }) //
                .setMainImg("http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjD3ulEKogfsiaua49pvLfUS8Ym0GSYjViaLic0FD3vN0V8PILcibEGb2fPfEOmw/0") //
                .addImg("http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjD3ulEKogfsiaua49pvLfUS8Ym0GSYjViaLic0FD3vN0V8PILcibEGb2fPfEOmw/0") //
                .addImg("http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjD3ulEKogfsiaua49pvLfUS8Ym0GSYjViaLic0FD3vN0V8PILcibEGb2fPfEOmw/0") //
                .addDeatil("测试字段", null) //
                .addDeatil(null, "http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjD3ul1UcLcwxrFdwTKYhH9Q5YZoCfX4Ncx655ZK6ibnlibCCErbKQtReySaVA/0") //
                .addDeatil("测试字段", "http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjD3ul1UcLcwxrFdwTKYhH9Q5YZoCfX4Ncx655ZK6ibnlibCCErbKQtReySaVA/0") //
                .setBuyLimit(10);
        p.addSku() // 添加SKU
                .setSkuId("1075741873:1079742386") //
                .setPrice(30) //
                .setIconUrl("http://mmbiz.qpic.cn/mmbiz/4whpV1VZl28bJj62XgfHPibY3ORKicN1oJ4CcoIr4BMbfA8LqyyjzOZzqrOGz3f5KWq1QGP3fo6TOTSYD3TBQjuw/0") //
                .setOriPrice(20) //
                .setProductCode("testing") //
                .setQuantity(100);
        p.addSku() // 添加SKU
                .setSkuId("1075741873:1079742386") //
                .setPrice(30) //
                .setIconUrl("http://mmbiz.qpic.cn/mmbiz/4whpV1VZl28bJj62XgfHPibY3ORKicN1oJ4CcoIr4BMbfA8LqyyjzOZzqrOGz3f5KWq1QGP3fo6TOTSYD3TBQjuw/0") //
                .setOriPrice(20) //
                .setProductCode("testing") //
                .setQuantity(100);
        p.getExtAttrBuilder() // 设置扩展属性
                .setPostFree(true) //
                .setHasReceipt(true) //
                .setUnderGuaranty(false) //
                .setSupportReplace(true);
        p.getLocationBuilder() // 设置商品所在地信息
                .setCountry("中国") //
                .setProvince("广东省") //
                .setCity("广州市") //
                .setAddress("T.I.T创意园");
        p.getDeliverBuilder() // 设置运费信息
                .setDeliverTemplateId(1000) //
                .addDeliverExpress(ExpressType.EMS, 200);
        Product product;
        // product = p.build(null);
        // product = p.build(VerifyType.NONE);
        product = p.build(RequriedType.ADD);
        // product = p.build(VerifyType.DELETE);
        // product = p.build(VerifyType.QUERY);
        return product;
    }

    public static void main(String[] args) {
        build();
    }
}
