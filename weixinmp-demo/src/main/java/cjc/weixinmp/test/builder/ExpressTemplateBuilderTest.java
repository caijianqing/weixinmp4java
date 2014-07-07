package cjc.weixinmp.test.builder;

import cjc.weixinmp.merchant.bean.Product.ExpressType;
import cjc.weixinmp.merchant.builder.ExpressTemplateBuilder;
import cjc.weixinmp.merchant.builder.ExpressTemplateBuilder.Assumer;
import cjc.weixinmp.merchant.builder.ExpressTemplateBuilder.TopFeeBuilder;
import cjc.weixinmp.merchant.builder.ExpressTemplateBuilder.Valuation;

import com.google.gson.Gson;

/**
 * <pre>
 * 商品测试样例
 * </pre>
 * 
 * @author jianqing.cai@qq.com, 2014年6月26日 上午12:01:11, https://github.com/caijianqing/weixinmp4java/
 */
public class ExpressTemplateBuilderTest {

    public static ExpressTemplateBuilder build() {
        ExpressTemplateBuilder p = new ExpressTemplateBuilder();
        p.setId(123456);
        p.setName("xxxx");
        p.setAssumer(Assumer.Buyers);
        p.setValuation(Valuation.Item);
        TopFeeBuilder t = p.addTopFee();
        t.setType(ExpressType.EMS);
        t.getNormalBuilder().setStartStandards(1).setStartFees(2).setAddStandards(3).setAddFees(4);
        t.addCustomer().setStartStandards(1).setStartFees(2).setAddStandards(3).setAddFees(4).setDestCountry("aa").setDestProvince("bb").setDestCity("cc");
        t.addCustomer().setStartStandards(1).setStartFees(2).setAddStandards(3).setAddFees(4).setDestCountry("aa").setDestProvince("bb").setDestCity("cc");
        return p;
    }

    public static void main(String[] args) {
        System.out.println(new Gson().toJson(build()));
    }
}
