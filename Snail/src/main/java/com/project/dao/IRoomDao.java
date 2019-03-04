package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bean.RoomBean;

/**
 * 持久层
 * 房间业务接口
 * @author tokiri
 *
 */
public interface IRoomDao {
	
	/*----------------房间表dao-----------------------*/
	
	
	/**
	 *查询所有房间信息
	 * @return 返回所有房间信息的集合
	 */
	@Select("select * from room where r_flage=0")
	public List<RoomBean> selectAllRoom();
	
	/**
	 *查询每页的房间信息
	 * @return 返回房间信息
	 */
	@Select("select * from room where r_flage=0 limit #{page},#{count}")
	public List<RoomBean> selectRoomByPage(@Param("page")int page,@Param("count")int count);
	
	/**
	 * 按条件查询房间信息
	 * @param bean	条件对象
	 * @return	返回所有房间信息的集合
	 */
	public List<RoomBean> selectByTerm(RoomBean bean);
	
	/**
	 * 更新单个房间的信息
	 * @param bean 更新的房间信息
	 */
	public int updateRoom(RoomBean bean);
	
	
	/**
	 * 添加房间信息
	 * @param bean 新房间信息
	 */
	@Insert("insert into room (r_num,r_rt_id,r_price,r_smoken,r_breakfast,r_window,r_note) value(#{r_num},#{r_rt_id},#{r_price},#{r_smoken},#{r_breakfast},#{r_window},#{r_note}) ")
	public int insertRoom(RoomBean bean);
	
}
