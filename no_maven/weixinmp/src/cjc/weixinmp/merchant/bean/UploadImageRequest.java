package cjc.weixinmp.merchant.bean;

import java.io.Serializable;

import cjc.weixinmp.bean.GlobalError;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * 上传图片时使用的对象
 * @author jianqing.cai@qq.com, 2014年6月10日 下午11:45:51, https://github.com/caijianqing/weixinmp4java/
 * </pre>
 */
public class UploadImageRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	

	/**
	 * 响应结果
	 */
	public static class Response extends GlobalError {

		private static final long serialVersionUID = 1L;

		/** 图片URL */
		@SerializedName("image_url")
		public String imageUrl;

	}

}
