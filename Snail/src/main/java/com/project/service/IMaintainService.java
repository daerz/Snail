package com.project.service;

import java.util.List;

import com.project.bean.MaintainBean;

public interface IMaintainService {
	
	/**
	 * 查询所有设施维护信息
	 * @return 返回设施维护信息集合
	 */
	public List<MaintainBean> findAllMaintain();
	
	/**
	 * 查询没有的维护信息
	 * @param page
	 * @param count
	 * @return
	 */
	public List<MaintainBean> findMaintainByPage(int page, int count);
	
	/**
	 * 返回中的页数
	 * @return
	 */
	public  int findPageNum(int count);
	
	/**
	 * 查询某房间的设施维护信息
	 * @param m_r_id
	 * @return
	 */
	public List<MaintainBean> findByTerm(MaintainBean bean);
	
	/**
	 * 插入一条维护信息
	 * @param bean	维护信息对象
	 * @return 返回影响的行数
	 */
	public int addMaintain(MaintainBean bean);
	
	/**
	 * 更新一条维护信息
	 * @param bean 信息对象
	 * @return 返回影响的行数
	 */
	public int changeMaintain(MaintainBean bean);
	
}
