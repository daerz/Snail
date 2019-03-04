package com.project.service;

import java.util.List;

import com.project.bean.LogBean;

public interface ILoginLogService {
	/**
	 * 查找登录日志
	 * @param pageNum 查找的页数
	 * @return 返回日志对象的集合
	 */
	public List<LogBean> findLog(int pageNum,int pageSize);
	
	/**
	 * 添加登录日志
	 * @param cip 
	 * @param cname 
	 * @return 受影响行数
	 */
	public int addLog(String name, String cname, String cip);
	/**
	 * 查询日志数
	 * @return 日志数
	 */
	public int countLog();
}
