package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.bean.RoomStaticInfoBean;

/**
 * 针对房间静态信息表的操作
 * @author 大耳贼
 *
 */
public interface IRoomStaticInfoDao {
	
	/**
	 * 查询所有静态信息(后台使用)
	 * @return
	 */
	@Select("select * from roomstaticinfo")
	public List<RoomStaticInfoBean> selectAllRoomStaticInfo();

	
	/**
	 * 查询指定房间的静态信息(前台使用)
	 * @return
	 */
	@Select("select * from roomstaticinfo where si_rt_id = #{id} and si_flage = 0")
	public List<RoomStaticInfoBean> selectRoomStaticInfoByRtid(int rt_id);
	
	/**
	 * 查询指定房间的静态信息(后台使用)
	 * @return
	 */
	@Select("select * from roomstaticinfo where si_rt_id = #{id}")
	public List<RoomStaticInfoBean> selectRoomStaticInfoByRtidBackStage(int rt_id);
	
	/**
	 * 更改房型静态信息(后台使用)
	 * @param bean
	 * @return
	 */
	public int updateRoomStaticInfo(RoomStaticInfoBean bean);
	
	/**
	 * 软删除房型静态信息(后台使用)
	 * @param id
	 * @return
	 */
	@Update("update roomstaticinfo set si_flage = 1 where si_id = #{id}")
	public int deleteRoomStaticInfo(int si_id);
	
	/**
	 * 新增房型静态信息(后台使用)
	 * @param bean
	 * @return
	 */
	public int insertRoomStaticInfo(RoomStaticInfoBean bean);
}
