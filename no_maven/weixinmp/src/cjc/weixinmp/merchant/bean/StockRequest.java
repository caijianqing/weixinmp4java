package cjc.weixinmp.merchant.bean;

import java.io.Serializable;

import cjc.weixinmp.DataBuilder.Requried;
import cjc.weixinmp.DataBuilder.RequriedType;
import cjc.weixinmp.bean.GlobalError;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 库存请求对象
 * @author jianqing.cai@qq.com, 2014年6月9日 下午11:19:33, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class StockRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 商品 ID */
    @Requried({ RequriedType.UPDATE })
    @SerializedName("product_id")
    public String productId;

    /** sku 信息,格式"id1:vid1;id2:vid2",如商品为统一规格，则此处赋值为空字符串即可 */
    @SerializedName("sku_info")
    @Requried({ RequriedType.UPDATE })
    public String skuInfo;

    /** 增加或减少的库存数量 */
    @Requried({ RequriedType.UPDATE })
    @SerializedName("quantity")
    public Integer quantity;

    @Override
    public String toString() {
        return "StockRequest [productId=" + productId + ", skuInfo=" + skuInfo + ", quantity=" + quantity + "]";
    }

    /**
     * 服务器响应
     */
    public static class Response extends GlobalError {

        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return "Response []";
        }

    }

}
