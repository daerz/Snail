package com.project.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.project.bean.LiveBean;
import com.project.bean.ORParamBean;
import com.project.bean.OrderBean;
import com.project.bean.OrderConditionBean;
import com.project.bean.OrderInfoBean;

/**
 * 业务层
 * 订单业务接口
 * @author Administrator
 *
 */
public interface IOrderService {

	
	/**
	 * 添加订单
	 * @param ob	类型	OrderBean
	 * @return		boolean
	 */
	public boolean addOrder(OrderBean  ob,List<OrderInfoBean> list);	
	/**
	 * 删除订单
	 * @param o_id	
	 * @return	boolean
	 */
	public boolean removeOrderByOid(String o_id);
	/**
	 * 删除订单详情表
	 * @param o_id	
	 * @return	boolean
	 */
	public boolean removeOrderInfo(OrderInfoBean oib);
	/**
	 * 更新订单
	 * @param ob	类型 OrderBean
	 * @return	boolean
	 */
	public boolean changeOrderByOid(OrderBean ob);
	
	/**
	 * 通过-订单号-查看订单
	 * @param o_id
	 * @return	类型 OrderBean
	 */
	public OrderBean findOrderOneByOid(String o_id);
	/**
	 * 通过-用户电话号码-查看所有订单
	 * @param o_u_phone
	 * @return	类型 List<OrderBean>
	 */
	public List<OrderBean> 	findOrderAllByPhone(String o_u_phone);
	/**
	 * 通过-入住者的电话-查看当前订单
	 * @param o_phone
	 * @return	OrderBean
	 */
	public List<OrderBean> findOrderOneByPhone(String o_phone);
	/**
	 * 通过-房间ID-查看订单详情表
	 * @param o_r_id
	 * @return	OrderBean
	 */
	public List<OrderInfoBean> findOrderInfoOneByRid(int o_r_id);
	/**
	 * 通过入住时间查看订单数
	 * @param intime
	 * @return
	 */
	public List<OrderBean> findOrderAllByIntime(String intime);
	/**
	 * 通过退房时间查看订单数
	 * @param outtime
	 * @return
	 */
	public List<OrderBean> findOrderAllByOuttime(String outtime);

	/**
	 * 通过-房间ID-查看订单详情
	 * @param o_r_id
	 * @return	OrderBean
	 */
	public List<OrderInfoBean> findOrderOneByRid(int o_r_id);
	

	/**
	 * 查询可换房间
	 * @param r_num
	 * @return
	 */
	public Map<String, Object> findRoom(int r_num);
	
	/**
	 * 条件查询入住信息
	 * @param bean
	 * @return
	 * @throws ParseException 
	 */
	public List<LiveBean> findLive(LiveBean bean,String beginTime,String outTime) throws ParseException;
	/**
	 * 添加入住
	 * @param bean
	 * @return
	 */
	public Map<String,Object> addLive(LiveBean bean);
	/**
	 * 退房
	 * @param l_outtime
	 * @param l_r_num
	 */
	public Map<String, Object> updateLive(String l_outtime,int l_r_num);
	/**
	 * 根据订单编号查询所有订单详情表
	 * @param i_o_id
	 * 
	 */
	public List<OrderInfoBean> findOederInfoAll(String i_o_id);
	/**
	 *正式查询所有订单详情表
	 * @param bean	条件对象
	 * 
	 */
	public List<OrderBean> findOrderAllAndAll(OrderBean order);
	/**
	 * 
	 * 正式添加订单
	 * @param orp 前台传过来封装的对象
	 * @param session  传过来的session
	 * @return
	 */
	public Map<String, Object> createOrderALL(ORParamBean orp);
	/**
	 * 取消订单
	 * @param oldorder
	 * @return
	 */
	public Map<String, Object> quitOrderAll(OrderBean oldorder);
	/**
	 * 换房
	 * @param l_r_num 原房间号
	 * @param l_uid   身份证号
	 * @param r_id 新房间id
	 */
	public Map<String, Object> changeRoom(int l_r_num,String l_uid,int r_id);
	/**
	 * 查询订单
	 * 
	 * @return
	 */
	public Map<String, Object> findOrderByIdAndPage(OrderConditionBean condition);
	
	public Boolean changeO_state(String o_id);
	
	public Map<String, Object> createOrderNotLineAll(OrderBean orderbean,OrderInfoBean orderinfobean,String r_price);
	
	/**
	 * 在后台查询所有订单
	 * @param page 
	 * @return
	 */
	public Map<String,Object> findAllOrder(int currentPage);
	/**
	 * 查询所有入住信息
	 * @return
	 */
	public Map<String, Object> findAllLive(int nowPage);

	/**
	 * 处理撤销订单（判定时间，支付状态，是否退款）
	 * @param oldorder
	 * @return
	 */
	public Map<String, Object> dealWithOrder(OrderBean oldorder);

	/**
	 * 根据各种条件查询后台订单
	 * @param orderBean
	 * @param oTime
	 * @param endTime 
	 * @return
	 */
	public Map<String, Object> findAllOrderTime(OrderBean orderBean, String beginTime, String endTime);

	/**
	 * 编辑入住信息
	 * @param liveBean
	 * @return
	 */
	public String updateEdit(LiveBean liveBean);
	/**
	 * 查询营业额，返回每月的金额存在list，再用map包装每月的金额
	 * @param year 
	 * @return
	 */
	public Map<String, Object> findMoneyByMonth(String year);

	/**
	 * 通过订单号，房间号，取消预定此房间并退钱
	 * @param o_id
	 * @param i_o_id
	 * @return
	 */
	public Map<String, Object> quitRoomOrderPer(String o_id, int i_o_id);

	/**
	 * 线上取消订单
	 * @param oldorder
	 * @return
	 */
	public Map<String, Object> quitOrderAllOnline(OrderBean oldorder);


	
}