package com.project.service;

import java.util.List;

import com.project.bean.RoomTypeImgBean;

/**
 * 房型对应图片业务接口
 * @author 大耳贼
 *
 */
public interface IRoomTypeImgService {

	/**
	 * 查询所有图片
	 * @return
	 */
	public List<RoomTypeImgBean> findAllImg();
	
	/**
	 * 查询房型对应图片
	 * @param rt_id
	 * @return
	 */
	public List<RoomTypeImgBean> findImgByRtid(int rt_id);
	
	/**
	 * 软删除房型下所有图片
	 * @param rt_id
	 * @return
	 */
	public boolean deleteImgByRtid(int rt_id);
	
	/**
	 * 软删除指定图片
	 * @param id 图片id
	 * @return
	 */
	public boolean deleteImgById(int id);
	
	/**
	 * 修改图片信息
	 * @param bean 信息对象
	 * @return
	 */
	public boolean changeImg(RoomTypeImgBean bean);
	
	/**
	 * 添加图片信息
	 * @param bean 信息对象
	 * @return
	 */
	public boolean addImg(RoomTypeImgBean bean);
}
