package com.project.service;
/**
 * 业务层
 * 房间业务接口
 * @author Administrator
 *
 */

import java.util.List;
import java.util.Map;

import com.project.bean.ORParamBean;
import com.project.bean.RoomBean;

public interface IRoomService {
	
	/**
	 * 查询所有房间
	 * @return 返回房间集合
	 */
	public List<RoomBean> findAllRoom();
	
	/**
	 * 查询分页房间
	 * @return 返回房间集合
	 */
	public Map<String,Object> findRoomByPage(int page, int count);
	
	/**
	 * 按要求查询房间
	 * @param bean	信息对象
	 * @return	返回房间集合
	 */
	public List<RoomBean> findByTerm(RoomBean bean);
	
	/**
	 * 筛选时间段内可入住的房间数
	 * @param bean	房间的条件信息
	 * @param beginDay	开始时间
	 * @param endDay	结束时间
	 * @return 返回信息(格式[[价格1,数量,房间],[价格2,数量,房间]])
	 */
	public List<List<String>> findByTermAndTime(RoomBean bean,String beginDay,String endDay);
	
	/**
	 * 根据所有条件筛选房间
	 * @param bean	房间的条件信息
	 * @param beginDay	开始时间
	 * @param endDay	结束时间
	 * @return 返回信息(格式[房间,房间])
	 */
	public List<RoomBean> findAllByTermAndTime(RoomBean bean,String beginDay,String endDay);
	
	/**
	 * 添加新的房间
	 * @param bean 新房间的对象
	 * @return 返回影响的数量
	 */
	public int addRoom(RoomBean bean);
	
	/**
	 * 修改房间的信息
	 * @param bean 房间信息对象
	 * @return 返回影响的数量
	 */
	public int changeRoom(RoomBean bean);
	
	/**
	 * 给订单分配房间
	 * @param bean 订单信息
	 * @return 返回分配的房间集合
	 */
	public List<RoomBean> allotRoom(ORParamBean bean);
	
	/**
	 * 获得每个房型的房间数量
	 * @return
	 */
	public List<Map<String,Object>> findRoomGroup();
}
