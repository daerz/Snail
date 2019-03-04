package com.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tokiri
 * 通过开始时间和结束时间计算中间所有时间
 *
 */
public class GetTimeByEveryDay {
	
	//定义时间格式
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 传入"yyyy-MM-dd"类型的两个字符串时间区间,返回时间戳（Long类型）
	 * @param beginDay	开始时间
	 * @param endDay	结束时间
	 * @return
	 */
	public static List<Long> getByTimeStamp(String beginDay,String endDay){
		List<Long> timeStamp = new ArrayList<Long>();
		//存储时间段
		List<Date> list = getDatesBetweenTwoDate(beginDay, endDay);
		for (Date date : list) {
			//将时间类型转换为时间戳
			timeStamp.add(date.getTime());
		}
		return timeStamp;
	}
	
	/**
	 * 传入"yyyy-MM-dd"类型的两个字符串时间区间，返回一个此区间内的所有时间(List<Date>)
	 * @param beginDay
	 * @param endDay
	 * @return
	 */
	private static List<Date> getDatesBetweenTwoDate(String beginDay,String endDay){
		//存储时间段
		List<Date> dateList = new ArrayList<Date>();
		try {
			Date beginDate = null;
			Date endDate = null;
			//将字符串类型转换为时间类型
			beginDate = sdf.parse(beginDay);
			endDate = sdf.parse(endDay);
			if(beginDate != null && endDate != null && endDate.getTime()>=beginDate.getTime()){
				//判断时间不能为空，且结束时间大于等于开始时间
				if(beginDate.equals(endDate)){
					//判断开始时间和结束时间相同，则存入一个时间(避免重复存入相同时间，项目中用不到)
					dateList.add(beginDate);
				}else{
					//开始时间和结束时间不相同时过去时间段
					dateList = datesBetweenTwoDate(beginDate, endDate);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateList;
	}
	
	/**
	 * 用于getDatesBetweenTwoDate()方法取出此区间内的时间段
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private static List<Date> datesBetweenTwoDate(Date beginDate, Date endDate){
		//存储时间段
		List<Date> allDate = new ArrayList<Date>();
		//开始时间存入List
		allDate.add(beginDate);
		Calendar cal = Calendar.getInstance();
		//使用给定的Date设置Calendar的时间
		cal.setTime(beginDate);
		boolean boo = true;
		while(boo){
			//根据日历规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			if(endDate.after(cal.getTime())){
				allDate.add(cal.getTime());
			}else{
				break;
			}
		}
		return allDate;
	}
}