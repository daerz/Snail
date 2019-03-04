package com.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackOutOrderTimeJudgeUtil {
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static boolean judgeTime(String intime) {
		//预计入住当天下午6点
		Date date1;
		//当前时间
		Date date2;
		try {
		 date1=format.parse(intime+" 18:00:00");
		 date2=format.parse(format.format(new Date()));
		} catch (ParseException e) {
			e.getMessage();
			return false;
		}
		System.out.println("date1:"+date1+" date2:"+date2);
		int num=date2.compareTo(date1);
		if(num<0) {
			return true;
		}
		return false;
	}
	public static boolean liveTime(String intime) {
		//预计入住当天下午6点
		Date date1;
		//当前时间
		Date date2;
		try {
		 date1=format.parse(intime+" 12:00:00");
		 date2=format.parse(format.format(new Date()));
		} catch (ParseException e) {
			e.getMessage();
			return false;
		}
		
		int num=date2.compareTo(date1);
		if(num<0) {
			return true;
		}
		return false;
	}
}
