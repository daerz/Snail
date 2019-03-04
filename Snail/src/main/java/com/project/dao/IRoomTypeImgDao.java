package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.bean.RoomTypeImgBean;

public interface IRoomTypeImgDao {

	/**
	 * 查询所有图片(后台)
	 * @return
	 */
	@Select("select * from roomtypeimg")
	public List<RoomTypeImgBean> selectAllImg();
	
	
	/**
	 * 查询房型下图片(后台)
	 * @return
	 */
	@Select("select * from roomtypeimg where rti_rt_id = #{id}")
	public List<RoomTypeImgBean> selectImgByRtidBackStage(int rt_id);
	
	/**
	 * 查询房型下图片
	 * @return
	 */
	@Select("select * from roomtypeimg where rti_rt_id = #{id} and rti_flage = 0 limit 0,3")
	public List<RoomTypeImgBean> selectImgByRtid(int rt_id);
	
	/**
	 * 软删除房型下所有图片(后台使用)
	 * @return
	 */
	@Update("update roomtypeimg set rti_flage = 1 where rti_rt_id = #{id}")
	public int deleteImgByRtid(int rt_id);
	
	/**
	 * 软删除根据图片id(后台使用)
	 * @return
	 */
	@Update("update roomtypeimg set rti_flage = 1 where rti_id = #{id}")
	public int deleteImgById(int id);
	
	/**
	 * 添加图片(后台使用)
	 * @param bean
	 * @return
	 */
	public int insertImg(RoomTypeImgBean bean);
	
	/**
	 * 修改图片信息(后台使用)
	 * @param bean
	 * @return
	 */
	public int updateRoomTypeImg(RoomTypeImgBean bean);
}
