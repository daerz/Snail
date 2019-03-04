package com.project.util;

import java.util.ResourceBundle;

public class AlipayUtil {

	private static ResourceBundle resource=null;
	static {
		 resource= ResourceBundle.getBundle("alipayConfig");
	}
	/**
	 * 获取alipayConfig.properties对应参数
	 * @param serverUrl 
	 * @param appId 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	 * @param privateKey 商户私钥，您的PKCS8格式RSA2私钥
	 * @param format	
	 * @param charset	字符编码格式
	 * @param alipayPublicKey	支付宝公钥
	 * @param signType	签名方式RSA2
	 * @param return_url 返回地址
	 * @param notify_url 通知地址
	 * @param
	 */
	public static String getValue(String key) {
		String val=resource.getString(key);
		return val;
	}
}
