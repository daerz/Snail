package com.project.dao;


import java.util.List;
import com.project.bean.LiveBean;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.bean.OrderBean;
import com.project.bean.OrderConditionBean;
import com.project.bean.OrderInfoBean;


/**
 * 持久层
 * 订单业务接口
 * @author Administrator
 *
 */
public interface IOrderDao {
	
	/**
	 * 查询所有入住信息条数
	 * @return
	 */
	@Select("select count(*) from t_live")
	public int selectAllLiveNum();
	
	/**
	 * 查询所有入住信息
	 * @return
	 */
	@Select("select * from t_live order by l_id desc limit #{nowPage},7")
	public List<LiveBean> selectAllLive(int nowPage);
	
	/**
	 * 添加入住
	 * @param liveBean
	 */
	@Insert("insert into t_live (l_uid,l_uname,l_usex,l_o_id,l_r_num,l_intime,l_o_outtime) values(#{l_uid},#{l_uname},#{l_usex},#{l_o_id},#{l_r_num},now(),#{l_o_outtime})")
	public int insertLive(LiveBean liveBean);
	/**
	 * 编辑
	 * @param liveBean
	 * @return
	 */
	public int updateEdit(LiveBean liveBean);
	/**
	 * 根据条件查询入住信息总条数
	 * @param bean
	 * @return
	 */
	public int selectLiveNum(LiveBean bean);
	
	/**
	 * 根据条件查询入住信息
	 * @return 
	 */
	public List<LiveBean> selectLive(LiveBean bean);
	/** 
	 * 退房
	 * @param outtime
	 * @param l_r_num
	 * @return
	 */
	@Update("update t_live set l_outtime = now(),l_flag = 2 where l_r_num =#{l_r_num} and l_flag = 1")
	public int updateLive(@Param("l_r_num")int l_r_num);
	/**
	 * 换房
	 * @param l_uid
	 * @param l_r_num
	 * @return
	 */
	@Update("update t_live set l_r_num = #{l_r_num} where l_uid =#{l_uid} and l_flag = 1")
	public int updateRoom(@Param("l_uid")String l_uid,@Param("l_r_num")int l_r_num);
	/**
	 * 查询入住房间的订单号
	 * @param l_r_num
	 * @return
	 */
	@Select("select l_o_id from t_live where l_r_num =#{l_r_num} and l_flag = 1")
	public String selectLiveOrder(int l_r_num);
	/**
	 * 添加订单
	 * @param ob	类型 OrderBean
	 * @return	int
	 */
	@Insert("insert into t_order (o_id,o_u_phone,o_phone,o_r_price,o_intime,o_outtime) values(#{o_id},#{o_u_phone},#{o_phone},#{o_r_price},#{o_intime},#{o_outtime})")
	public int insertOrder(OrderBean  ob);
	/**
	 * 删除订单
	 * @param o_id（订单ID）	
	 * @return int
	 */
	@Update("update t_order set o_flag=1 where o_id=#{o_id}")
	public int deleteOrderByOid(String o_id);
	/**
	 * 修改订单
	 * @param ob 类型 OrderBean
	 * @return int
	 */
	public int updateOrderByOid(OrderBean ob);
	/**
	 * 通过订单ID，查询订单表和订单详情表
	 * @param o_id
	 * @return	OrderBean
	 */
	public OrderBean selectOrderOneByOid(String o_id);
	/**
	 * 通过用户电话，查询订单表和订单详情表
	 * @param o_u_phone
	 * @return List<OrderBean>
	 */
	public List<OrderBean> selectOrderAllByPhone(String o_u_phone);
	/**
	 * 通过-入住者的电话-查看订单表和订单详情表
	 * @param o_phone
	 * @return	OrderBean
	 */
	public List<OrderBean> selectOrderOneByPhone(String o_phone);
	/**
	 * 通过房间ID查询当前订单详情表
	 * @param o_r_id
	 * @return
	 */
	public List<OrderInfoBean> selectOrderInfoOneByRid(int o_r_id);
	/**
	 * 通过前端传的入住时间查询订单表和订单详情表
	 * @param intime
	 * @return
	 */
	public List<OrderBean> selectOrderAllByIntime(String intime);
	/**
	 * 通过前端传的退房时间查询订单表和订单详情表
	 * @param outtime
	 * @return
	 */
	public List<OrderBean> selectOrderAllByOuttime(String outtime);
	/**
	 * 插入订单详情
	 * @param list
	 * @return
	 */
	public int insertOrderInfo(List<OrderInfoBean> list);
	/**
	 * 修改订单详情表
	 * @param list
	 * @return
	 */
	public int updateOrderInfo(OrderInfoBean oib);
	/**
	 *	通过订单ID查询订单详情表对应信息
	 * @param i_o_id
	 * @return List<OrderInfoBean
	 */
	public List<OrderInfoBean> selectOrderInfoByOid(String i_o_id);
	/**
	 * 查询实际金额
	 * @param i_o_id
	 * @param i_r_num
	 * @return
	 */
	@Select("select i_r_price from t_orderinfo where i_o_id =#{i_o_id} and i_r_id = #{i_r_id}")
	public double selectOneOrderInfo(@Param("i_o_id")String i_o_id, @Param("i_r_id")int i_r_id);
	/**
	 * 正式查询所有订单详情表
	 * @param order
	 * @return
	 */
	public List<OrderBean> findOrderAllAndAll(OrderBean order);


	/**
	 * 查询订单
	 * @param conditioin
	 * @return
	 */
	public List<OrderBean> selectOrderByPage(OrderConditionBean condition);
	/**
	 * 查询订单总数（用于计算总页数）
	 * @param o_u_phone
	 * @return
	 */
	@Select("select count(*) from t_order where o_u_phone = #{o_u_phone} and o_flag = 0")
	public int selectCountPage(String o_u_phone);
	
	/**
	 * 查询订单总数（用于计算总页数）
	 * @param o_u_phone
	 * @return
	 */
	@Select("select o_r_price from t_order where o_intime Like CONCAT(CONCAT('%', #{yearMon}), '%') and o_flag = 0")
	public List<Double> selectMoneyByMon(String yearMon);
	
	/**
	 * 删除订单
	 * @param o_id（订单ID）	
	 * @return int
	 */
	@Update("update t_orderinfo set i_flag=1 where i_o_id=#{i_o_id}")
	public int deleteOrderInfoByFlag(String i_o_id);
	
	@Update("update t_order set o_state=1 where o_id=#{o_id}")
	public int updateO_stateByO_id(String o_id);

	@Select("select * from t_order where o_flag=0 and #{timeNow}<= o_outtime order by o_id DESC LIMIT #{page},5;")
	public List<OrderBean> selectFindAllOrder(@Param("page")int currentPage,@Param("timeNow")String timeNow);

	@Select("select count(*) from t_order where o_flag=0 and #{timeNow}<= o_outtime")
	public int selectCountALLPage(String timeNow);
	
	@Update("update t_orderinfo set i_flag=1 where i_o_id=#{i_o_id} and i_r_id=#{i_r_id}")
	public int removeOrderInfoByFlagAndirid(@Param("i_o_id")String o_id,@Param("i_r_id")int i_r_id);
	
	


}
