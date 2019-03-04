package com.project.service;

public interface RedisService {
	
	
	/**
	 * 将预定的时间保存到缓存
	 * @param r_id 预定的房间id(字符串类型)
	 * @param beginDay 预定的起始时间(字符串格式:"2018-01-01")
	 * @param endDay 预定的结束时间(字符串格式:"2018-01-01")
	 */
	public  void saveToRedis(String r_id,String beginDay,String endDay);
	
	/**
	 * 查询该时间段是否有时间被预定
	 * @param r_id 房间id(字符串类型)
	 * @param beginDay 查询的起始时间(字符串格式:"2018-01-01")
	 * @param endDay 查询的结束时间(字符串格式:"2018-01-01")
	 * @return (true:可以预定,false:不能预约)
	 */
	public boolean findTimeExist(String r_id,String beginDay,String endDay);
	
	/**
	 * 将指定时间段移除缓存
	 * @param r_id 指定的房间id(字符串类型)
	 * @param beginDay 指定的起始时间(字符串格式:"2018-01-01")
	 * @param endDay 指定的结束时间(字符串格式:"2018-01-01")
	 */
	public void delTimeToRedis(String r_id,String beginDay,String endDay);
}
