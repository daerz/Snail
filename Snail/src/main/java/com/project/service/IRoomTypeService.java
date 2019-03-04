package com.project.service;

import java.util.List;

import com.project.bean.RoomBean;
import com.project.bean.RoomTypeBean;
/**
 * 房型业务接口
 * @author 大耳贼
 *
 */
public interface IRoomTypeService {

		
	/**
	 * 筛选房型：将选中过滤的条件存入RoomBean中，通过动态sql进行条件筛选
	 * 并将过滤的时间区间分解成多个时间(按天)，从而过滤到时间内不可住的房间
	 * 遍历房型id，通过房型id查询对应静态信息，图片，房间信息，然后存入List<Object>，
	 * 再将每个房型对应的List<Object>存入另一个List<Object>中 
	 * @param bean 信息对象, beginDay-endDay 订单时间区间
	 * @return List<List<Objet>>:[[RoomType,RoomStaticInfoBean,imgList,[[价格1,数量,房间],[价格2,数量,房间]]]]
	 */
	public List<Object> findRoomTypeByTermAndTime(RoomBean bean, int num, String beginDay, String endDay);
	
	/**
	 * 查询所有房型(包括已软删除的)
	 * @return
	 */
	public List<RoomTypeBean> findAllRoomType();
	
	/**
	 * 查询房型(用于index页面)
	 * @return
	 */
	public List<RoomTypeBean> findRoomTypeUseIndex();
	
	/**
	 * 通过房型id查询房型及图片、静态信息
	 * @param rt_id
	 * @return
	 */
	public List<Object> findRoomTypeByRtid(int rt_id, int countId);
	
	/**
	 * 通过房型id和房间id查询房型及价格
	 * @param rt_id
	 * @return
	 */
	public List<Object> findRoomTypeByCart(int rt_id, int countId);
	
	/**
	 * 添加房型操作
	 * @param bean 信息对象
	 * @return 是否添加成功
	 */
	public boolean addRoomType(RoomTypeBean bean);
	
	/**
	 * 修改房型操作
	 * @param bean 信息对象
	 * @return 是否修改成功
	 */
	public boolean changeRoomType(RoomTypeBean bean);
	
	/**
	 * 软删除房型操作
	 * @param rt_id 对象id
	 * @return 是否删除成功
	 */
	public String deleteRoomType(int rt_id);
}
