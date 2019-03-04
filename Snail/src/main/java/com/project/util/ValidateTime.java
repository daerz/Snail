package com.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateTime {

	public static boolean checkTime(String beginTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=null;
		Date date2=null;
		try {
			date1 = sdf.parse(beginTime);
			date2 = sdf.parse(endTime);
	
		}catch(Exception e){
			return false;
		}
		int check1 = -1;
		try {
			check1 = date1.compareTo(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			return false;
		}
		int check2 = date1.compareTo(date2);
//		System.out.println(check1+":"+check2+":"+sdf.format(date1)+":"+sdf.format(date2));
		if(check1>=0&&check2<0) {
			return true;
		}
		
		
		return false;
	}

}
