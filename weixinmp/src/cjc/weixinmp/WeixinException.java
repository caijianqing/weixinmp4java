/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午2:53:10
 */
package cjc.weixinmp;

/**
 * 微信业务异常
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午2:53:10
 */
public class WeixinException extends Exception {

    /**
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午2:53:13
     */
    private static final long serialVersionUID = 1L;

    /** 是否需要保存log */
    private boolean needLog = false;

    /** 需要保存到日志文件的文件名 */
    private String logFilename;

    /** 需要保存到日志文件的内容 */
    private String logContent;

    /** 异常代码 */
    private Integer errcode;

    /** 错误信息 */
    private String errmsg;

    /**
     * 需要写入日志文件的异常
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午3:33:34
     * @param logFilename
     * @param logContent
     * @param cause
     */
    public WeixinException(String logFilename, String logContent, Throwable cause) {
        super((cause == null ? "" : "cause by: " + cause + ", ") + "logFilename=" + logFilename, cause);
        setLog(logFilename, logContent);
    }

    /**
     * 有全局错误信息+需要写入文件的异常
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午3:33:24
     * @param errcode
     * @param errmsg
     * @param logFilename
     * @param logContent
     * @param cause
     */
    public WeixinException(Integer errcode, String errmsg, String logFilename, String logContent, Throwable cause) {
        super((cause == null ? "" : "cause by: " + cause + ", ") + "errcode=" + errcode + ", errmsg=" + errmsg + ", logFilename=" + logFilename, cause);
        setLog(logFilename, logContent);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    /**
     * 全局错误信息
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午3:33:50
     * @param errcode
     * @param errmsg
     * @param cause
     */
    public WeixinException(Integer errcode, String errmsg, Throwable cause) {
        super((cause == null ? "" : "cause by: " + cause + ", ") + "errcode=" + errcode + ", errmsg=" + errmsg, cause);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    /**
     * 声明需要写入日志文件的文件名和内容
     * @param logFilename
     *            the {@link #logFilename} to set
     * @param logContent
     *            the {@link #logContent} to set
     */
    public void setLog(String logFilename, String logContent) {
        this.logFilename = logFilename;
        this.logContent = logContent;
        if (logFilename != null) {
            needLog = true;
        }
    }

    /**
     * @return the {@link #needLog}
     */
    public boolean isNeedLog() {
        return needLog;
    }

    /**
     * @return the {@link #logFilename}
     */
    public String getLogFilename() {
        return logFilename;
    }

    /**
     * @return the {@link #logContent}
     */
    public String getLogContent() {
        return logContent;
    }

    /**
     * @return 异常代码 {@link #errcode}
     */
    public Integer getErrcode() {
        return errcode;
    }

    /**
     * @return 错误信息 {@link #errmsg}
     */
    public String getErrmsg() {
        return errmsg;
    }
}
