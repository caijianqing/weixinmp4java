package cjc.weixinmp.merchant.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cjc.weixinmp.bean.GlobalError;
import cjc.weixinmp.merchant.bean.ExpressTemplate.DeliveryTemplate;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 邮费模板请求对象
 * @author jianqing.cai@qq.com, 2014年6月9日 下午11:25:02, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class ExpressRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 邮费模板ID */
	@SerializedName("template_id")
	public Integer templateId;

	/**
	 * 服务器响应
	 */
	public static class Response extends GlobalError {

		private static final long serialVersionUID = 1L;

		/** 邮费模板ID */
		@SerializedName("template_id")
		public Integer templateId;

		/** 单个邮费详情 */
		@SerializedName("template_info")
		public DeliveryTemplate templateInfo;

		/** 邮费模板集合 */
		@SerializedName("templates_info")
		public List<DeliveryTemplate> templateList = new ArrayList<DeliveryTemplate>();

	}
}
