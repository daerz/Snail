package com.project.service;

import java.util.List;

import com.project.bean.RoomStaticInfoBean;

/**
 * 房型静态信息业务接口
 * @author 大耳贼
 *
 */
public interface IRoomStaticInfoService {
	
	/**
	 * 查询所有房型静态信息
	 * @return
	 */
	public List<RoomStaticInfoBean> findAllStatic();
	
	/**
	 * 通过房型id查询对应房型静态信息(不包括软删除的)
	 * @param rt_id
	 * @return
	 */
	public List<RoomStaticInfoBean> findStaticByRtid(int rt_id);
	
	/**
	 * 添加静态信息
	 * @param bean 信息对象
	 * @return 是否添加成功
	 */
	public boolean addStatic(RoomStaticInfoBean bean);
	
	/**
	 * 修改静态信息
	 * @param bean 信息对象
	 * @return 是否修改成功
	 */
	public boolean changeStatic(RoomStaticInfoBean bean);
	
	/**
	 * 通过id删除指定静态信息
	 * @param si_id 静态信息id
	 * @return 是否修改成功
	 */
	public boolean deleteStaticBySiid(int si_id);
}
