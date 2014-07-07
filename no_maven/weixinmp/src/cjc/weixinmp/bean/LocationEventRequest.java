package cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 上报地理位置事件的请求<br>
 * MsgId 字段无效
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationEventRequest extends AbstractEventRequest {

    private static final long serialVersionUID = 1L;

    /** 地理位置纬度 */
    public String Latitude;

    /** 地理位置经度 */
    public String Longitude;

    /** 地理位置精度 */
    public String Precision;

    @Override
    public String toString() {
        return "LocationEventRequest [Event=" + Event + ", ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime
                + ", MsgType=" + MsgType + ", MsgId=" + MsgId + ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", Precision=" + Precision + "]";
    }

}
