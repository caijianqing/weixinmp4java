package cjc.weixinmp;

/**
 * 某个方法未实现时抛出的异常
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class NotImplException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotImplException() {
        super("请先实现这个方法");
    }

}
