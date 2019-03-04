package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.project.bean.LogBean;

public interface ILoginLogDao {
	/**
	 * 查找登录日志
	 * @param pageNum 查找起始的位置
	 * @return 返回日志对象的集合
	 */
	public List<LogBean> selectLog(int startNum,int pageSize);
	
	/**
	 * 添加登录日志
	 * @param bean 日志对象
	 * @return 受影响行数
	 */
	@Insert("insert into loginlog(l_name,l_address,l_ip,l_time) values (#{l_name},#{l_address},#{l_ip},#{l_time})")
	public int insertLog(LogBean bean);
	/**
	 * 查询日志数
	 * @return 日志数
	 */
	@Select("select count(l_id) from loginlog")
	public int countLog();
}
