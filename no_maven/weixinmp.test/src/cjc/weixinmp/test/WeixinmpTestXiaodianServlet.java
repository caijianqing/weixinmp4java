package cjc.weixinmp.test;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cjc.weixinmp.MerchantExpressService;
import cjc.weixinmp.MerchantGroupService;
import cjc.weixinmp.MerchantOrderService;
import cjc.weixinmp.MerchantOrderService.OrderStatus;
import cjc.weixinmp.MerchantProductService;
import cjc.weixinmp.MerchantShelfService;
import cjc.weixinmp.MerchantStockService;
import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.merchant.bean.ExpressTemplate;
import cjc.weixinmp.merchant.bean.ExpressTemplate.DeliveryTemplate;
import cjc.weixinmp.merchant.bean.OrderRequest;
import cjc.weixinmp.merchant.bean.Product;
import cjc.weixinmp.merchant.bean.ProductGroup.GroupDetail;
import cjc.weixinmp.merchant.bean.ProductRequest.ProductStatus;
import cjc.weixinmp.merchant.bean.ProductRequest.Response.Category;
import cjc.weixinmp.merchant.bean.ProductRequest.Response.Properties;
import cjc.weixinmp.merchant.bean.ProductRequest.Response.SkuTable;
import cjc.weixinmp.merchant.bean.ShelfRequest;
import cjc.weixinmp.merchant.builder.ProductBuilder;
import cjc.weixinmp.merchant.builder.ProductGroupBuilder;
import cjc.weixinmp.merchant.builder.ShelfBuilder;
import cjc.weixinmp.merchant.builder.StockBuilder;
import cjc.weixinmp.test.builder.ExpressTemplateBuilderTest;
import cjc.weixinmp.test.builder.ProductBuilderTest;
import cjc.weixinmp.test.builder.ShelfBuilderTest;

/**
 * <pre>
 * 小店测试样例
 * @author jianqing.cai@qq.com, 2014年6月23日 下午11:22:14, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class WeixinmpTestXiaodianServlet extends BaseTestServlet {

    private static final long serialVersionUID = 1L;

    WeixinmpController c = Engine.getWeixinmpController();

    MerchantProductService productService = c.getMerchantProductService();

    MerchantStockService stockService = c.getMerchantStockService();

    MerchantExpressService expressService = c.getMerchantExpressService();

    MerchantGroupService groupService = c.getMerchantGroupService();

    MerchantShelfService shelfService = c.getMerchantShelfService();

    MerchantOrderService orderService = c.getMerchantOrderService();
    
    // //////////////////////////////////////////商品管理///////////////////////////////////

    // 1. 商品管理接口
    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Product p = ProductBuilderTest.build();
        String productId = productService.addProduct(p);
        write(response, productId);
    }

    // 1.2 删除商品
    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        productService.deleteProduct("xxxxxxxxxxxx");
        write(response, "删除成功");
    }

    // 1.3 修改商品
    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductBuilder p = new ProductBuilder();
        p.setId("xxxxxx");
        Product product = p.build(RequriedType.UPDATE);
        productService.updateProduct(product);
        write(response, "修改成功");
    }

    // 1.4 查询商品
    public void getProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Product p = productService.getProduct("xxxxxxxx");
        write(response, p);
    }

    // 1.5 获取指定状态的所有商品
    public void getProductByStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> pList = productService.getProductByStatus(ProductStatus.ONLINE);
        write(response, pList);
    }

    // 1.6 商品上下架
    public void updateProductStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        productService.updateProductStatus("xxxxxx", ProductStatus.ONLINE);
        write(response, "操作成功");
    }

    // 1.7 获取指定分类的所有子分类
    public void getSubCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Category> categoryList = productService.getSubCategory("999");
        write(response, categoryList);
    }

    // 1.8 获取指定子分类的所有 SKU的
    public void getSkuByCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SkuTable> skuTable = productService.getSkuByCategory("999");
        write(response, skuTable);
    }

    // 1.8 获取指定子分类的所有 SKU的
    public void getPropertyByCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Properties> propertiesList = productService.getPropertyByCategory("999");
        write(response, propertiesList);
    }

    // /////////////////////////////////////////库存管理////////////////////////////////////

    // 2.1 增加库存
    public void addStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StockBuilder b = new StockBuilder();
        b.setProductId("xxxx");
        b.setQuantity(10);
        b.setSkuInfo("id1:vid1;id2:vid2"); // 这两个方法二选一
        b.addSkuInfo(123, 456); // 这两个方法二选一
        stockService.addStock(b.build(RequriedType.UPDATE));
        write(response, "增加库存成功");
    }

    // 2.2 减少库存
    public void reduceStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StockBuilder b = new StockBuilder();
        b.setProductId("xxxx");
        b.setQuantity(10);
        b.setSkuInfo("id1:vid1;id2:vid2"); // 这两个方法二选一
        b.addSkuInfo(123, 456); // 这两个方法二选一
        stockService.reduceStock(b.build(RequriedType.UPDATE));
        write(response, "减少库存成功");
    }

    // /////////////////////////////////////////邮费模版管理////////////////////////////////////

    // 3.1 增加邮费模板
    public void addExpress(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpressTemplate expressTemplate = ExpressTemplateBuilderTest.build().build(RequriedType.ADD);
        Integer expressTemplateId = expressService.addExpress(expressTemplate.deliveryTemplate);
        write(response, expressTemplateId);
    }

    // 3.2 删除邮费模板
    public void deleteExpress(HttpServletRequest request, HttpServletResponse response) throws Exception {
        expressService.deleteExpress(123456);
        write(response, "删除邮费模板成功");
    }

    // 3.3 修改邮费模板
    public void updateExpress(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpressTemplate expressTemplate = ExpressTemplateBuilderTest.build().build(RequriedType.UPDATE);
        expressService.updateExpress(expressTemplate);
        write(response, "修改邮费模板成功");
    }

    // 3.3 修改邮费模板
    public void getExpressById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DeliveryTemplate template = expressService.getExpressById(123456);
        write(response, template);
    }

    // 3.3 修改邮费模板
    public void getAllExpress(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DeliveryTemplate> list = expressService.getAllExpress();
        write(response, list);
    }

    // ///////////////////////////////////////分组管理//////////////////////////////////////

    // 4.1 增加分组
    public void addGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductGroupBuilder builder = new ProductGroupBuilder();
        builder.getGroupDetailBuilder().setGroupName("分组名").addProductId("商品ID");
        Integer groupId = groupService.addGroup(builder.build(RequriedType.ADD));
        write(response, groupId);
    }

    // 4.2 删除分组
    public void deleteGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        groupService.deleteGroup(123446);
        write(response, "删除产品分组成功");
    }

    // 4.3 修改分组属性
    public void updateGroupProperty(HttpServletRequest request, HttpServletResponse response) throws Exception {
        groupService.updateGroupProperty(123446, "ttttttt");
        write(response, "修改产品分组成功");
    }

    // 4.4 修改分组商品
    public void updateGroupProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductGroupBuilder b = new ProductGroupBuilder();
        b.setGroupId(123);
        b.addModAction().addProduct("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        groupService.updateGroupProduct(b.build(RequriedType.UPDATE));
        write(response, "修改产品分组成功");
    }

    // 4.5 获取所有分组
    public void getAllGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<GroupDetail> list = groupService.getAllGroup();
        write(response, list);
    }

    // 4.6 根据分组 ID 获取分组信息
    public void getGroupById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GroupDetail group = groupService.getGroupById(12345);
        write(response, group);
    }

    // ///////////////////////////////////////货架管理//////////////////////////////////////

    // 5.1 增加货架
    public void addShelf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShelfBuilder builder = ShelfBuilderTest.build();
        Integer groupId = shelfService.addShelf(builder.build(RequriedType.ADD));
        write(response, groupId);
    }

    // 5.2 删除货架
    public void deleteShelf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        shelfService.deleteShelf(111);
        write(response, "删除货架成功");
    }

    // 5.3 修改货架
    public void updateShelf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShelfBuilder builder = ShelfBuilderTest.build();
        shelfService.updateShelf(builder.build(RequriedType.UPDATE));
        write(response, "删除货架成功");
    }

    // 5.4 获取所有货架
    public void getAllShelf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ShelfRequest> list = shelfService.getAllShelf();
        write(response, list);
    }

    // 5.5 根据货架 ID 获取货架信息
    public void getSelfById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShelfRequest shelf = shelfService.getSelfById(111);
        write(response, shelf);
    }

    // /////////////////////////////////////订单管理////////////////////////////////////////

    // 6.2 根据订单ID获取订单详情
    public void getOrderById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderRequest.Response.Order order = orderService.getOrderById("xxxxx");
        write(response, order);
    }

    // 6.3 根据订单状态/创建时间获取订单详情
    public void getOrderByFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<OrderRequest.Response.Order> orderList = orderService.getOrderByFilter(OrderStatus.Shipped, new Date(), new Date());
        write(response, orderList);
    }

    // 6.4 设置订单发货信息
    public void setOrderDelivery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        orderService.setOrderDelivery("xxxx", "shunfeng", "999999999999");
        write(response, "设置运单信息成功");
    }

    // 6.5 关闭订单
    public void closeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        orderService.closeOrder("xxxx");
        write(response, "关闭订单成功");
    }

    // /////////////////////////////////////功能接口////////////////////////////////////////

}
