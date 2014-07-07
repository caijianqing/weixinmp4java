package cjc.weixinmp;

import java.io.IOException;
import java.util.List;

import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.Product;
import cjc.weixinmp.merchant.bean.ProductRequest;
import cjc.weixinmp.merchant.bean.ProductRequest.ProductStatus;
import cjc.weixinmp.merchant.bean.ProductRequest.Response.Properties;
import cjc.weixinmp.merchant.bean.ProductRequest.Response.SkuTable;
import cjc.weixinmp.merchant.builder.ProductBuilder;

/**
 * <pre>
 * 商品管理接口
 * @author jianqing.cai@qq.com, 2014年6月9日 下午8:11:43, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class MerchantProductService {

    /** 分类根节点ID */
    public static final String ROOT_CATEGORY_ID = "1";

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected MerchantProductService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 增加商品
     * 
     * @param product 通过ProductBuilder生成的Product
     * @return 这个商品的ID
     * @throws WeixinException 如果调用接口失败
     */
    public String addProduct(Product product) throws WeixinException {
        String url = controller.getProperty("merchant_product_create_url", null, false);
        try {
            GlobalError result = controller.postWithJson(url, product, ProductRequest.Response.class, "addProduct");
            controller.logInfo("创建新商品操作结果：" + result);
            ProductRequest.Response r = (ProductRequest.Response) result;
            return r.productId;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_createProductError", e.getMessage(), e);
        }
    }

    /**
     * 删除商品
     * 
     * @param productId 商品ID
     * @throws WeixinException 如果调用接口失败
     */
    public void deleteProduct(String productId) throws WeixinException {
        String url = controller.getProperty("merchant_product_del_url", null, false);
        try {
            ProductBuilder pb = new ProductBuilder();
            pb.setId(productId);
            Product product = pb.build(RequriedType.DELETE);
            GlobalError result = controller.postWithJson(url, product, GlobalError.class, "deleteProduct");
            controller.logInfo("删除商品操作结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_deleteProductError", e.getMessage(), e);
        }
    }

    /**
     * 更新商品
     * 
     * @param product 通过ProductBuilder生成的Product<br>
     *            1. product_id表示要更新的商品的ID，其他字段说明请参考增加商品接口。<br>
     *            2. 从未上架的商品所有信息均可修改，否则商品的名称(name)、商品分类(category)、商品属性(property)这三个字段不可修改。
     * @throws WeixinException 如果调用接口失败
     */
    public void updateProduct(Product product) throws WeixinException {
        String url = controller.getProperty("merchant_product_update_url", null, false);
        try {
            GlobalError result = controller.postWithJson(url, product, GlobalError.class, "updateProduct");
            controller.logInfo("更新新商品操作结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_updateProductError", e.getMessage(), e);
        }
    }

    /**
     * 根据商品ID查询一个商品
     * 
     * @param productId 商品ID，例如：pDF3iYwktviE3BzU3BKiSWWi9Nkw
     * @return 商品详情
     * @throws WeixinException
     */
    public Product getProduct(String productId) throws WeixinException {
        String url = controller.getProperty("merchant_product_get_url", null, false);
        try {
            ProductRequest request = new ProductRequest();
            request.productId = productId;
            GlobalError result = controller.postWithJson(url, request, ProductRequest.Response.class, "getProduct");
            controller.logInfo("查询商品操作结果：" + result);
            ProductRequest.Response response = (ProductRequest.Response) result;
            return response.product;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getProductError", e.getMessage(), e);
        }
    }

    /**
     * 获取指定状态的所有商品
     * @param status 商品上下架标识
     * @return 返回符合条件的商品集合
     * @throws WeixinException 如果发生错误
     */
    public List<Product> getProductByStatus(ProductStatus status) throws WeixinException {
        String url = controller.getProperty("merchant_product_getbystatus_url", null, false);
        try {
            ProductRequest request = new ProductRequest();
            request.status = status.getStatus();
            GlobalError result = controller.postWithJson(url, request, ProductRequest.Response.class, "getProductByStatus");
            controller.logInfo("获取指定状态的所有商品操作结果：" + result);
            ProductRequest.Response response = (ProductRequest.Response) result;
            return response.productList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getProductByStatusError", e.getMessage(), e);
        }
    }

    /**
     * 商品上下架
     * @param productId 商品ID
     * @param status 商品上下架标识
     * @throws WeixinException
     */
    public void updateProductStatus(String productId, ProductStatus status) throws WeixinException {
        String url = controller.getProperty("merchant_product_modproductstatus_url", null, false);
        try {
            ProductRequest request = new ProductRequest();
            request.productId = productId;
            request.status = status.getStatus();
            GlobalError result = controller.postWithJson(url, request, ProductRequest.Response.class, "updateProductStatus");
            controller.logInfo("商品上下架操作结果：" + result);
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_updateProductStatusError", e.getMessage(), e);
        }
    }

    /**
     * 获取指定分类的所有子分类
     * 
     * @param categoryId 分类ID，NULL表示根节点分类（ID为1）
     * @return 返回子分类的集合，对象包含有效字段有：errcode, errmsg, categoryList
     * @throws WeixinException 如果发生未处理的错误
     */
    public List<ProductRequest.Response.Category> getSubCategory(String categoryId) throws WeixinException {
        String url = controller.getProperty("merchant_category_getsub_url", null, false);
        try {
            // 默认根目录ID
            if (categoryId == null) {
                categoryId = ROOT_CATEGORY_ID;
            }
            // 准备请求
            ProductRequest req = new ProductRequest();
            req.cateId = categoryId;
            // 发送请求
            GlobalError error = controller.postWithJson(url, req, ProductRequest.Response.class, "getSubCategory");
            controller.logInfo("获取指定分类的子分类结果：" + error);
            // 返回
            ProductRequest.Response r = (ProductRequest.Response) error;
            return r.categoryList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_GetSubCategoryError", e.getMessage(), e);
        }
    }

    /**
     * 获取指定子分类的所有SKU
     * @param categoryId 商品子分类ID，NULL表示根节点分类（ID为1）
     * @return 返回这个子分类的所有SKU集合
     * @throws WeixinException
     */
    public List<SkuTable> getSkuByCategory(String categoryId) throws WeixinException {
        String url = controller.getProperty("merchant_category_getsku_url", null, false);
        try {
            // 默认根目录ID
            if (categoryId == null) {
                categoryId = ROOT_CATEGORY_ID;
            }
            // 准备请求
            ProductRequest req = new ProductRequest();
            req.cateId = categoryId;
            // 发送请求
            GlobalError error = controller.postWithJson(url, req, ProductRequest.Response.class, "getSkuByCategory");
            controller.logInfo("获取指定子分类的所有SKU结果：" + error);
            // 返回
            ProductRequest.Response r = (ProductRequest.Response) error;
            return r.skuList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getSkuByCategoryError", e.getMessage(), e);
        }
    }

    /**
     * 获取指定分类的所有属性
     * @param categoryId 商品子分类ID，NULL表示根节点分类（ID为1）
     * @return
     * @throws WeixinException
     */
    public List<Properties> getPropertyByCategory(String categoryId) throws WeixinException {
        String url = controller.getProperty("merchant_category_getproperty_url", null, false);
        try {
            // 默认根目录ID
            if (categoryId == null) {
                categoryId = ROOT_CATEGORY_ID;
            }
            // 准备请求
            ProductRequest req = new ProductRequest();
            req.cateId = categoryId;
            // 发送请求
            GlobalError error = controller.postWithJson(url, req, ProductRequest.Response.class, "getPropertyByCategory");
            controller.logInfo("获取指定分类的所有属性结果：" + error);
            // 返回
            ProductRequest.Response r = (ProductRequest.Response) error;
            return r.propertiesList;
        } catch (WeixinException e) {
            controller.logError(e.getMessage());
            if (e.isNeedLog()) {
                controller.saveToFile(e.getLogFilename(), e.getLogContent());
            }
            throw e;
        } catch (IOException e) {
            throw new WeixinException(CommonUtils.getNextId() + "_getPropertyByCategoryError", e.getMessage(), e);
        }
    }

}
