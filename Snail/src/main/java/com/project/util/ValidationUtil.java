package com.project.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidationUtil {

	
	/**
	 * 用于条件居住人数验证，验证失败返回int为0
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str){
		int i = 0;
		try {
			i = Integer.parseInt(str);
			if(i < 0)
				i = 0;
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	
	
	
	/**
	 * 用于条件筛选验证，验证失败返回int为0
	 * @param str
	 * @return
	 */
	public static int condition(String str){
		int i = 0;
		try {
			i = Integer.parseInt(str);
			if(i != 1 && i != 2)
				i = 0;
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	
	/**
	 * 入住时间和离开时间验证
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static Map<String,String> dateValidation(String beginDay, String endDay){
		Map<String,String> map = new HashMap<String,String>();
		String begin = beginDay;
		String end = endDay;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.setLenient(false);
			sdf.parse(begin);
			sdf.parse(end);
			int i = sdf.parse(begin).compareTo(sdf.parse(sdf.format(new Date())));
			int j = sdf.parse(end).compareTo(sdf.parse(begin));
			if(i < 0 || j <= 0){
				begin = sdf.format(new Date());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, 1);
				end = sdf.format(cal.getTime());				
			}
			map.put("beginDay", begin);
			map.put("endDay", end);
		} catch (Exception e) {
			begin = sdf.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 1);
			end = sdf.format(cal.getTime());
			map.put("beginDay", begin);
			map.put("endDay", end);
		}
		return map;
	}
}
