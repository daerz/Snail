package com.project.util;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class SMSUtil {
	// url前半部分
	public static final String BASE_URL = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";

	// 开发者注册后系统自动生成的账号，可在官网登录后查看
	public static final String ACCOUNT_SID = "b32cbce302694fe18223dd0f22cdc1e6";

	// 开发者注册后系统自动生成的TOKEN，可在官网登录后查看
	public static final String AUTH_TOKEN = "3f23e22af9394e399b9164cede3afaae";

	// 响应数据类型, JSON或XML
	public static final String RESP_DATA_TYPE = "json";

	private static String code = smsCode();
	//
	private static String smsContent = "【CTO科技】登录验证码：" + code + "，请五分钟内提交，否则无效。如非本人操作，请忽略此短信。";

	/**
	 * 构造通用参数timestamp、sig和respDataType
	 * 
	 * @return
	 */
	public static String createCommonParam() {
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		// 签名
		String sig = DigestUtils.md5Hex(ACCOUNT_SID + AUTH_TOKEN + timestamp);
		return "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType=" + RESP_DATA_TYPE;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            功能和操作
	 * @param body
	 *            要post的数据
	 * @return
	 * @throws IOException
	 */

	public static String post(String url, String body) {
		System.out.println("url:" + System.lineSeparator() + url);
		System.out.println("body:" + System.lineSeparator() + body);
		String result = "";
		try {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();
			// 读取返回参数
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true;

			while ((line = in.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
					result += System.lineSeparator();
				}
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static String execute(String to) {
		String tmpSmsContent = null;
		try {
			tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
		} catch (Exception e) {

		}

		String url = BASE_URL;
		// accountSid=a14f6bfd43ce44c9b019de57f4e2de4b&smsContent=�����ֿƼ���������֤����345678��30����������Ч��
		// &to=13896543210&timestamp=20150821100312&sig=a14f6bfd43ue44c9b019du57f4e2ee4r&respDataType=JSON
		String body = "accountSid=" + ACCOUNT_SID + "&smsContent=" + tmpSmsContent + "&to=" + to + createCommonParam();
		// 提交请求
		String result = post(url, body);
		System.out.println("result:" + System.lineSeparator() + result);

		return code;
	}

	// 验证码随机生成
	public static String smsCode() {
		String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
		System.out.println("验证码" + random);
		return random;
	}
}
