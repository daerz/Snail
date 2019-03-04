package com.project.dao;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.project.bean.RoomTypeBean;

/**
 * 持久层
 * 房型业务接口
 * @author 大耳贼
 *
 */
public interface IRoomTypeDao {


	/**
	 * 查询所有房型(前台使用)
	 * @return
	 */
	@Select("select * from roomtype where rt_flage = 0")
	public List<RoomTypeBean> selectAllRoomType();
	
	/**
	 * 根据可住人数(或床型)查询
	 * @param rt_num
	 * @return
	 */
	@Select("select * from roomtype where rt_num = #{rt_num} and rt_flage = 0")
	public List<RoomTypeBean> selectRoomTypeByNum(int rt_num);
	
	/**
	 * 查询所有房型(后台使用)
	 * @return
	 */
	@Select("select * from roomtype")
	public List<RoomTypeBean> selectAllRoomTypeBackStage();
	
	/**
	 * 查询所有房型(后台使用)
	 * @return
	 */
	@Select("select * from roomtype limit 0,4")
	public List<RoomTypeBean> selectRoomTypeUseIndex();
	
	/**
	 * 根据房型id只查询房型信息
	 * @param rt_id
	 * @return
	 */
	@Select("select * from roomtype WHERE rt_id=#{id}")
	public RoomTypeBean selectRoomTypeById(int rt_id);
	
	/**
	 * 插入房型信息(后台使用)
	 * @param bean
	 * @return
	 */
	public int insertRoomType(RoomTypeBean bean);
	
	/**
	 * 更新房型信息(后台)
	 * @param bean
	 * @return
	 */
	public int updateRoomType(RoomTypeBean bean);
	
	/**
	 * 软删除房型(后台)
	 * @param bean
	 * @return
	 */
	@Update("update roomtype set rt_flage=1 where rt_id=#{id}")
	public int deleteRoomTypeById(int rt_id);	
	
}
