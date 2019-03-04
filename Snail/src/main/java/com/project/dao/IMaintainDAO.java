package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bean.MaintainBean;
import com.project.bean.RoomBean;

public interface IMaintainDAO {
	
/*----------------设施表dao-----------------------*/
	
	/**
	 * 查询所有需要维护的信息
	 * @return 返回设施维护信息的集合
	 */
	@Select("select * from maintain where m_flage = 0")
	public List<MaintainBean> selectAllMaintain();
	
	/**
	 *查询每页的维护信息
	 * @return 返回维护信息
	 */
	@Select("select * from maintain where m_flage=0 limit #{page},#{count}")
	public List<MaintainBean> selectMaintainByPage(@Param("page")int page,@Param("count")int count);
	
	/**
	 * 按房间查询维护信息
	 * @param m_r_id 房间的id
	 * @return	返回设施维护信息集合
	 */
	public List<MaintainBean> selectByTerm(MaintainBean bean);
	
	/**
	 * 插入设备维护信息
	 * @param bean  设备维护信息对象
	 */
	@Insert("insert into maintain(m_r_id,m_info,m_date) value(#{m_r_id},#{m_info},now())")
	public int insertMaintain(MaintainBean bean);
	
	/**
	 * 软删除设备维护信息
	 * @param m_id 设备维护信息id
	 * @return	返回的数量
	 */
//	public void deleteMaintain(int m_id);
	
	/**
	 * 更新设备维护信息
	 * @param bean 设备维护信息对象
	 * @return 返回影响的数量
	 */
	public int updateMaintain(MaintainBean bean);
	
}
