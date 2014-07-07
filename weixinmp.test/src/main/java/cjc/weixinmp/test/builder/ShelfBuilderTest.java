package cjc.weixinmp.test.builder;

import cjc.weixinmp.merchant.builder.ShelfBuilder;

/**
 * 货架测试样例
 * 
 * @author jianqing.cai@qq.com, 2014年7月5日 下午4:07:26, https://github.com/caijianqing/weixinmp4java/
 */
public class ShelfBuilderTest {

    public static ShelfBuilder build() {
        ShelfBuilder p = new ShelfBuilder();
        p.setId(111);
        p.setName("测试货架");
        p.setBanner("http://xxxxxxxxxxxxxxxxx");
//        p.getShelf1Builder().setCount(2).setGroupId(222);
//        p.getShelf2Builder().addGroupId(1111).addGroupId(222);
//        p.getShelf3Builder().setGroupId(11).setImg("http://xxxxxxxxxxx");
//        p.getShelf4Builder().addGroupId(1, "http://xxx");
        p.getShelf5Builder().setBackground("http:////");
        return p;
    }

    public static void main(String[] args) {
        build();
    }
}
