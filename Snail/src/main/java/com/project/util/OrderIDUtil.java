package com.project.util;

import java.text.SimpleDateFormat;

public class OrderIDUtil {
	/**
	 * 生成订单ID
	 * @param phone
	 * @return ID
	 */
	public static String getOrderId(String phone) {
		SimpleDateFormat fa=new SimpleDateFormat("yyyyMMddHHmmss");
		long time=System.currentTimeMillis();
		String o_id=fa.format(time)+phone;
		return o_id;
	}
}
