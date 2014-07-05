package cjc.weixinmp.merchant.builder;

import cjc.weixinmp.DataBuilder;
import cjc.weixinmp.merchant.bean.StockRequest;

/**
 * <pre>
 * 库存数据构造器
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年7月5日 上午10:33:00, https://github.com/caijianqing/weixinmp4java/
 */
public class StockBuilder extends DataBuilder<StockRequest> {

    /**
     * 设置商品ID
     * 
     * @param productId 商品ID
     */
    public void setProductId(String productId) {
        p.productId = productId;
    }

    /**
     * 设置增加或减少的库存数量
     * 
     * @param quantity 正整数
     */
    public void setQuantity(int quantity) {
        p.quantity = quantity;
    }

    /**
     * 设置sku字符窜信息<br>
     * 注意：使用这个方法将会覆盖addSkuInfo方法传入的值
     * 
     * @param skuInfo sku信息,格式"id1:vid1;id2:vid2",如商品为统一规格，则此处赋值为空字符串即可
     * @ses {@link #addSkuInfo(int, int)}
     */
    public void setSkuInfo(String skuInfo) {
        p.skuInfo = skuInfo;
    }

    /**
     * 追加一个SKU信息<br>
     * 注意：使用setSkuInfo方法会覆盖这个方法传入的值
     * 
     * @param pid 属性ID
     * @param vid 值ID
     * @see #setSkuInfo(String)
     */
    public void addSkuInfo(int pid, int vid) {
        if (p.skuInfo == null) {
            p.skuInfo += pid + ":" + vid;
        } else {
            p.skuInfo += ";" + pid + ":" + vid;
        }
    }

}
