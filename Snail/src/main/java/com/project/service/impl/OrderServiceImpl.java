package com.project.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.project.bean.LiveBean;
import com.project.bean.ORParamBean;
import com.project.bean.OrderBean;
import com.project.bean.OrderConditionBean;
import com.project.bean.OrderInfoBean;
import com.project.bean.RoomBean;
import com.project.dao.IOrderDao;
import com.project.dao.IRoomDao;
import com.project.service.IOrderService;
import com.project.service.RedisService;
import com.project.util.AlipayUtil;
import com.project.util.BackOutOrderTimeJudgeUtil;
import com.project.util.GetTimeByEveryDay;
import com.project.service.IRoomService;
import com.project.service.IUserService;
import com.project.util.OrderIDUtil;
import com.project.util.RedisUtil;

/**
 * 业务层 订单业务实现类
 * 
 * @author Administrator
 *
 */

@Service
public class OrderServiceImpl implements IOrderService {

	private final String regexTime = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	@Autowired
	private RedisService redservice;

	@Autowired
	private IRoomService rservice;

	@Autowired
	private IOrderDao dao;

	@Autowired
	private IRoomDao r_dao;

	@Autowired
	private IUserService usersevice;

	@Autowired
	private RedisUtil redis;

	@Override
	public boolean addOrder(OrderBean ob, List<OrderInfoBean> list) {
		int num = dao.insertOrder(ob);
		if (num > 0) {
			int num2 = dao.insertOrderInfo(list);
			if (num2 > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeOrderByOid(String o_id) {
		int num = dao.deleteOrderByOid(o_id);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean changeOrderByOid(OrderBean ob) {
		int num = dao.updateOrderByOid(ob);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public OrderBean findOrderOneByOid(String o_id) {
		OrderBean order = dao.selectOrderOneByOid(o_id);
		return order;
	}

	@Override
	public List<OrderInfoBean> findOrderInfoOneByRid(int o_r_id) {
		List<OrderInfoBean> orderInfo = dao.selectOrderInfoOneByRid(o_r_id);
		return orderInfo;
	}

	@Override
	public List<OrderBean> findOrderOneByPhone(String o_phone) {
		List<OrderBean> order = dao.selectOrderOneByPhone(o_phone);
		return order;
	}

	@Override
	public List<OrderBean> findOrderAllByPhone(String o_u_phone) {
		List<OrderBean> order = dao.selectOrderAllByPhone(o_u_phone);
		return order;
	}

	@Override
	public List<OrderBean> findOrderAllByIntime(String intime) {
		List<OrderBean> order = dao.selectOrderAllByIntime(intime);
		return order;
	}

	@Override
	public List<OrderBean> findOrderAllByOuttime(String outtime) {
		List<OrderBean> order = dao.selectOrderAllByOuttime(outtime);
		return order;
	}

	@Override
	public List<OrderInfoBean> findOrderOneByRid(int o_r_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateLive(String l_outtime, int l_r_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询房间id
		RoomBean bean = new RoomBean();
		bean.setR_num(l_r_num);
		List<RoomBean> room = r_dao.selectByTerm(bean);
		if (room.size() != 0) {
			// 查询订单编号
			String l_o_id = dao.selectLiveOrder(l_r_num);
			// 获得房间id
			int r_id = room.get(0).getR_id();
			// 修改入住表
			int num = dao.updateLive(l_r_num);

			OrderInfoBean infoBean = new OrderInfoBean();
			double roomPrice = 0;
			if (num > 0) {
				// 查询订单
				OrderBean order = dao.selectOrderOneByOid(l_o_id);
				if (l_outtime.equals(order.getO_outtime())) {
					infoBean.setI_flag(1);
					infoBean.setI_r_id(r_id);
					infoBean.setI_o_id(l_o_id);
					dao.updateOrderInfo(infoBean);
					order.setO_state(4);
					dao.updateOrderByOid(order);
				} else {
					// 提前退房，获得相差天数
					List<Long> list = GetTimeByEveryDay.getByTimeStamp(l_outtime, order.getO_outtime());
					int day = list.size();
					if(BackOutOrderTimeJudgeUtil.liveTime(l_outtime)){
						day= day - 1;
						if(day < 0){
							day = 0;
						}
						
					}
					// 获得退还金额
					roomPrice = (dao.selectOneOrderInfo(l_o_id, r_id)) * day;
					double orderPrice = order.getO_r_price() - roomPrice;
					// 修改订单金额
					order.setO_r_price(orderPrice);
					order.setO_state(4);
					dao.updateOrderByOid(order);
					infoBean.setI_r_id(r_id);
					infoBean.setI_o_id(l_o_id);
					dao.updateOrderInfo(infoBean);

				}
				// 删除房间时间
				redservice.delTimeToRedis(String.valueOf(r_id), order.getO_intime(), order.getO_outtime());
			}
			
			map.put("price", roomPrice);
			map.put("result", "退房成功");
			return map;
		}
		map.put("result", "房间号错误");
		return map;
	}

	@Override
	public List<LiveBean> findLive(LiveBean bean, String beginTime, String outTime) throws ParseException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<LiveBean> list = null;
		if (beginTime != null && outTime != null) {
			if (beginTime.matches(regexTime) && !outTime.matches(regexTime)) {
				bean.setL_flag(1);
				list = dao.selectLive(bean);
				String beginTime1 = beginTime+" 00:00:00";
				String beginTime2 = beginTime+" 24:00:00";
				for (int i = list.size()-1; i >=0; i--) {
					String intime = list.get(i).getL_intime();
					if (format.parse(intime).getTime()>=format.parse(beginTime1).getTime() && format.parse(intime).getTime()<=format.parse(beginTime2).getTime()) {
						
					}else {
						list.remove(i);
					}
				}
				return list;
			}
			if (!beginTime.matches(regexTime) && outTime.matches(regexTime)) {
				bean.setL_flag(2);
				list = dao.selectLive(bean);
				String outTime1 = outTime+" 00:00:00";
				String outTime2 = outTime+" 24:00:00";
				for (int i = list.size()-1; i >=0; i--) {
					String outtime = list.get(i).getL_outtime();
					if (outtime!=null && format.parse(outtime).getTime()>=format.parse(outTime1).getTime() && format.parse(outtime).getTime()<=format.parse(outTime2).getTime()) {
						
					}else {
						list.remove(i);
					}
				}
				return list;
			}
		}
		list = dao.selectLive(bean);
		if (beginTime != null && outTime != null && beginTime != "" && outTime != "") {
			if (beginTime.matches(regexTime) && outTime.matches(regexTime)) {
				String beginTime3= beginTime+" 00:00:00";
				String outTime3 = outTime+" 24:00:00";
				for (int i = list.size() - 1; i >= 0; i--) {
					String l_inttime = list.get(i).getL_intime();
					String l_outtime = list.get(i).getL_outtime();
					if ((format.parse(l_inttime).getTime() >= format.parse(beginTime3).getTime()
							&& format.parse(l_inttime).getTime() <= format.parse(outTime3).getTime())) {

					} else if (l_outtime != null && (format.parse(l_outtime).getTime() >= format.parse(beginTime3).getTime()
							&& format.parse(l_outtime).getTime() <= format.parse(outTime).getTime())) {

					} else {
						list.remove(i);
					}
				}

			}
		}
		return list;
	}

	@Override
	public Map<String, Object> addLive(LiveBean bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		RoomBean roomBean = new RoomBean();
		roomBean.setR_num(bean.getL_r_num());
		List<RoomBean> roomBeans = r_dao.selectByTerm(roomBean);
		OrderBean orderBean = dao.selectOrderOneByOid(bean.getL_o_id());
		if (roomBeans.size() == 0) {
			map.put("result", "房间不存在");
			return map;
		}
		if (orderBean == null) {
			map.put("result", "订单不存在");
			return map;
		}
		int num = dao.insertLive(bean);
		if (num == 0) {
			map.put("result", "插入失败");
			return map;
		}
		map.put("result", "插入成功");
		orderBean.setO_state(2);
		dao.updateOrderByOid(orderBean);
		return map;
	}

	@Override
	public boolean removeOrderInfo(OrderInfoBean oib) {
		int num = dao.updateOrderInfo(oib);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<OrderInfoBean> findOederInfoAll(String i_o_id) {
		List<OrderInfoBean> list = dao.selectOrderInfoByOid(i_o_id);
		return list;
	}

	@Override
	@Transactional
	public Map<String, Object> createOrderALL(ORParamBean orp) {

		System.out.println("orp:" + orp);
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取用户电话与折扣
		String phone = null;
		double discount = 0;
		try {
			phone = (String) session.getAttribute("phone");
			discount = (double) session.getAttribute("discount");
		} catch (Exception e) {

			map.put("message", "未登录，下单失败");
			map.put("statecode", 0);
			return map;
		}
		// String phone = "13302010501";
		// double discount = 0.8;
		// 生成订单号
		String o_id = OrderIDUtil.getOrderId(phone);
		// 房间接口
		List<RoomBean> roomList = rservice.allotRoom(orp);
		System.out.println("roomList:" + roomList);
		// 订单详情表
		List<OrderInfoBean> oibList = new ArrayList<OrderInfoBean>();
		double totalPrice = 0;
		int day =0;
		// 防止下单的时候，房间已经被预定
		if (roomList.size() != orp.getNum()) {
			map.put("message", "房间已被预订，下单失败");
			map.put("statecode", 0);
			return map;
		} else {
			for (RoomBean rb : roomList) {
				OrderInfoBean orderInfoBean = new OrderInfoBean();
				List<Long> listday = GetTimeByEveryDay.getByTimeStamp(orp.getBeginTime(), orp.getEndTime());

				day = listday.size();

				// 算总价
				double realPrice = rb.getR_price() * rb.getR_discount() * discount ;
				orderInfoBean.setI_r_price(realPrice);
				orderInfoBean.setI_r_id(rb.getR_id());
				orderInfoBean.setI_o_id(o_id);
				orderInfoBean.setI_r_num(rb.getR_num());
				// 缓存
				redservice.saveToRedis(String.valueOf(rb.getR_id()), orp.getBeginTime(), orp.getEndTime());
				totalPrice += realPrice;
				oibList.add(orderInfoBean);
			}
		}
		// 订单生成
		OrderBean ob = new OrderBean();
		ob.setO_id(o_id);
		if (!orp.getPhone().equals(phone)) {
			ob.setO_u_phone(phone);
			ob.setO_phone(orp.getPhone());
		} else {
			ob.setO_phone(phone);
			ob.setO_u_phone(phone);
		}

		ob.setO_intime(orp.getBeginTime());
		ob.setO_outtime(orp.getEndTime());
		ob.setO_r_price(totalPrice*day);
		System.out.println("ob:" + ob);
		boolean bo = this.addOrder(ob, oibList);
		System.out.println("bo:" + bo);
		if (bo) {
			// 把订单号存入缓存，做计时器
			redis.set("order_" + o_id, o_id, 200);

			// 返回当前订单信息
			OrderBean orderBean = this.findOrderOneByOid(o_id);
			map.put("orderBean", orderBean);
			map.put("statecode", 1);
			return map;
		} else {
			map.put("message", "出现错误，下单失败");
			map.put("statecode", 0);
		}
		return map;
	}

	@Override

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Map<String, Object> createOrderNotLineAll(OrderBean orderbean,OrderInfoBean orderinfobean,String r_price) {

		// 通过后台查找用户电话，获取用户的session信息
		usersevice.findUserByName(orderbean.getO_phone());

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		double discount=0;
		// 获取用户电话与折扣
		try{
			 discount = (double) session.getAttribute("discount");
		}catch(Exception e){
			 discount=1;
		}
		
		// 生成订单号
		String o_id = OrderIDUtil.getOrderId(orderbean.getO_phone());
		
		
		// 算出住宿的天数
		List<Long> listday = GetTimeByEveryDay.getByTimeStamp(orderbean.getO_intime(),orderbean.getO_outtime());
		int day = listday.size();
		double moneyit = Double.valueOf(r_price);
		// 算总价
		double realPrice = moneyit * discount * day;
		orderinfobean.setI_r_price(moneyit * discount);
		orderinfobean.setI_o_id(o_id);
		// 缓存
		redservice.saveToRedis(String.valueOf(orderinfobean.getI_r_id()), orderbean.getO_intime(),orderbean.getO_outtime());
		// 订单生成
		orderbean.setO_id(o_id);
		orderbean.setO_intime(orderbean.getO_intime());
		orderbean.setO_outtime(orderbean.getO_outtime());
		orderbean.setO_r_price(realPrice);
		orderbean.setO_state(0);
		
		// 房间详情表
		List<OrderInfoBean> oibList = new ArrayList<OrderInfoBean>();
		oibList.add(orderinfobean);
		
		boolean bo = this.addOrder(orderbean, oibList);
		if (bo) {
			map.put("message", "成功");
			return map;
		} else {
			map.put("message", "失败");
		}
		return map;
	}

	/**
	 * 取消订单，删除名下所有的房间
	 */
	@Override
	public Map<String, Object> quitOrderAll(OrderBean oldorder) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		OrderBean neworder = this.findOrderOneByOid(oldorder.getO_id());
		int state = 0;
		state = neworder.getO_state();
		if(state==0||state==2||state==3||state==4){
			map.put("state", state);
			return map;
		}
		// 获取在数据库中用户支付的总价
		Double money = neworder.getO_r_price();
		// 获得当前时间
		Date dayn = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String timeNow = df.format(dayn);
		OrderBean orderMoneyChange = new OrderBean();
		List<OrderInfoBean> infoSize = dao.selectOrderInfoByOid(oldorder.getO_id());
		if (infoSize.size() <= 0) {
			map.put("price", ",您已经取消了所有房间，无更多房间可取消");
			map.put("message", "取消订单失败");
			return map;
		}
		try {
			// 如果当前时间小于预计入住时间，就退全款
			if (df.parse(timeNow).getTime() < df.parse(neworder.getO_intime()).getTime()) {
				// 软删除订单表
				/*this.removeOrderByOid(oldorder.getO_id());*/
				orderMoneyChange.setO_flag(1);
				
				map.put("price", money);
			} else if (df.parse(timeNow).getTime() >= df.parse(neworder.getO_intime()).getTime()
					&& df.parse(timeNow).getTime() <= df.parse(neworder.getO_outtime()).getTime()) {

				// 否则就退一部分
				List<Long> list = GetTimeByEveryDay.getByTimeStamp(neworder.getO_intime(), timeNow);
				int day = list.size();
				double moneyroomper = 0.0;
				// 算出每间房的实际单价
				List<OrderInfoBean> OrderInfoBean = dao.selectOrderInfoByOid(oldorder.getO_id());
				for (int i = 0; i < OrderInfoBean.size(); i++) {
					moneyroomper += OrderInfoBean.get(i).getI_r_price() * day;
				}
				// 返回金额=已付总价-不退还的金额
				money = money - moneyroomper;
				if (money > 0) {
					orderMoneyChange.setO_id(oldorder.getO_id());
					orderMoneyChange.setO_r_price(moneyroomper);
					/*dao.updateOrderByOid(orderMoneyChange);*/
					map.put("price", money);
					if(state==3){
					map.put("state", state);
					return map;
					}
				} else {
					map.put("price", "0,退款金额大于所付金额，故无法退还任何金额");
				}
			} else {
				map.put("price", "已超过可取消预定的最终时间，现已无法退还任何金额");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 将状态码改为3，即已经取消订单，且未入住的状态
		orderMoneyChange.setO_id(oldorder.getO_id());
		orderMoneyChange.setO_state(3);
		dao.updateOrderByOid(orderMoneyChange);

		List<OrderInfoBean> list = new ArrayList<OrderInfoBean>();
		list = this.findOederInfoAll(oldorder.getO_id());
		for (OrderInfoBean bean : list) {
			// 退房时从redis缓存删除该房间的时间戳
			redservice.delTimeToRedis(String.valueOf(bean.getI_r_id()), neworder.getO_intime(),
					neworder.getO_outtime());
		}
		
		
		// 软删除订单详情表
		boolean be = this.removeOrderInfoByFlag(oldorder.getO_id());
		if(be){
			map.put("message", "取消订单成功");
			return map;
		}
		
		map.put("message", "取消订单失败");
		return map;
	}

	/**
	 * 取消某个预定房间
	 */
	@Override
	public Map<String, Object> quitRoomOrderPer(String o_id, int i_r_id) {
		Map<String, Object> map = new HashMap<String, Object>();

		OrderBean neworder = this.findOrderOneByOid(o_id);
		int state = 0;
		state = neworder.getO_state();
		if(state==0||state==2||state==3||state==4){
			map.put("state", state);
			return map;
		}
		// 获取在数据库中用户支付的总价
		Double money = neworder.getO_r_price();
		// 获得当前时间
		Date dayn = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String timeNow = df.format(dayn);
		OrderBean orderMoneyChange = new OrderBean();
		try {
			// 如果当前时间小于预计入住时间，就退房间全款
			if (df.parse(timeNow).getTime() < df.parse(neworder.getO_intime()).getTime()) {
				// 先算出房间本该住的天数
				List<Long> list2 = GetTimeByEveryDay.getByTimeStamp(neworder.getO_intime(), neworder.getO_outtime());
				int day3 = list2.size();
				// 查出该房间应退金额
				double MoneyRe = dao.selectOneOrderInfo(o_id, i_r_id);
				map.put("price", MoneyRe*day3);
				orderMoneyChange.setO_id(o_id);
				if(money==MoneyRe*day3){
					orderMoneyChange.setO_flag(1);
				}else{
					orderMoneyChange.setO_r_price(money-MoneyRe*day3);
				}
				/*dao.updateOrderByOid(orderMoneyChange);*/
			} else if (df.parse(timeNow).getTime() >= df.parse(neworder.getO_intime()).getTime()
					&& df.parse(timeNow).getTime() <= df.parse(neworder.getO_outtime()).getTime()) {
				// 否则就退一部分
				// 先算出房间本该住的天数
				List<Long> list2 = GetTimeByEveryDay.getByTimeStamp(neworder.getO_intime(), neworder.getO_outtime());
				int day2 = list2.size();
				// 再算出该房间未能及时取消预定导致延后的天数
				List<Long> list = GetTimeByEveryDay.getByTimeStamp(neworder.getO_intime(), timeNow);
				int day = list.size();
				int dayxx = day2 - day;
				// 退钱金额为已取消预定该房间的天数*刚房间单价
				double roomPrice = (dao.selectOneOrderInfo(o_id, i_r_id)) * dayxx;
				// 应退钱的钱<该房间付过的金额，才给退
				if (roomPrice < dao.selectOneOrderInfo(o_id, i_r_id) * day2) {
					map.put("price", roomPrice);
					// 数据库金额=已付总价-退还的金额
					money = money - roomPrice;
					orderMoneyChange.setO_id(o_id);
					orderMoneyChange.setO_r_price(money);
					// 将数据库的订单表进行更新到最近的金额
					/*dao.updateOrderByOid(orderMoneyChange);*/
				} else {
					map.put("price", "0，应退的钱超过了该房间付过的金额现已无法退还任何金额");
					return map;
				}

			} else {
				map.put("price", "已超过可取消预定的最终时间，现已无法退还任何金额");
				return map;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
				
		//判断此时名下还是否有房间，如果没有，将整个订单改为已取消状态
		List<OrderInfoBean> infoSize = dao.selectOrderInfoByOid(o_id);
		if (infoSize.size() <= 0) {
			orderMoneyChange.setO_state(3);
			dao.updateOrderByOid(orderMoneyChange);
		}else{
		//更新订单详情表的内容
		dao.updateOrderByOid(orderMoneyChange);
		}
		
		// 退房时从redis缓存删除该房间的时间戳
		redservice.delTimeToRedis(String.valueOf(i_r_id), neworder.getO_intime(),
				neworder.getO_outtime());
		
		// 软删除订单详情表
		int be = dao.removeOrderInfoByFlagAndirid(o_id, i_r_id);
		if(be>0){
			map.put("message", "取消订单成功");
			return map;
		}
		map.put("message", "取消订单失败");
		return map;
	}

	private boolean removeOrderInfoByFlag(String o_id) {
		int num = dao.deleteOrderInfoByFlag(o_id);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<OrderBean> findOrderAllAndAll(OrderBean order) {
		return dao.findOrderAllAndAll(order);
	}
	
	@Override
	public Map<String, Object> changeRoom(int l_r_num, String l_uid, int r_id) {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		// 查询房间
		RoomBean room = new RoomBean();
		room.setR_num(l_r_num);
		List<RoomBean> roomBeans = r_dao.selectByTerm(room);
		if (roomBeans.size() != 0) {
			// 查询订单号
			String l_o_id = dao.selectLiveOrder(l_r_num);
			RoomBean roomBean = roomBeans.get(0);
			int rid = roomBean.getR_id();
			double l_price = roomBean.getR_price() * roomBean.getR_discount();
			// 查询房间实际价格
			double o_price = dao.selectOneOrderInfo(l_o_id, rid);
			// 查询订单
			OrderBean order = dao.selectOrderOneByOid(l_o_id);
			// 查询用户折扣
			usersevice.findUserByName(order.getO_phone());
			double discount = (double) session.getAttribute("discount");
			// 判断是否为折扣房
			if (o_price == (l_price * discount)) {
				// 查询新房间信息
				room.setR_id(r_id);
				List<RoomBean> bean = r_dao.selectByTerm(room);
				if (bean.size() != 0) {
					RoomBean room1 = bean.get(0);
					// 更改缓存
					int num = 0;
					num = dao.updateRoom(l_uid, bean.get(0).getR_num());
					if (num != 0) {
						OrderInfoBean infoBean = new OrderInfoBean();
						infoBean.setI_r_id(r_id);
						infoBean.setI_o_id(l_o_id);
						infoBean.setI_r_num(room1.getR_num());
						num = dao.updateOrderInfo(infoBean);
						redservice.saveToRedis(String.valueOf(r_id), order.getO_intime(), order.getO_outtime());
						redservice.delTimeToRedis(String.valueOf(rid), order.getO_intime(), order.getO_outtime());
						map.put("result", "换房成功");
						return map;
					}
					map.put("result", "身份证号错误");
					return map;
				}
				map.put("result", "新房间id错误");
				return map;
			}
			map.put("result", "该房间为折扣房，无法换房");
			return map;
		}
		map.put("result", "房间号错误");
		return map;
	}

	@Override
	public Map<String, Object> findOrderByIdAndPage(OrderConditionBean condition) {

		List<OrderBean> orderList = dao.selectOrderByPage(condition);
		int totalPage = dao.selectCountPage(condition.getO_u_phone());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPage", totalPage % 5 > 0 ? totalPage / 5 + 1 : totalPage / 5);
		map.put("orderList", orderList);
		return map;
	}

	@Override
	public Boolean changeO_state(String o_id) {
		int num = dao.updateO_stateByO_id(o_id);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> findAllOrder(int currentPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date dayn = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String timeNow = df.format(dayn);
		System.out.println(timeNow);
		List<OrderBean> orderList = dao.selectFindAllOrder(currentPage, timeNow);
		int totalPage = dao.selectCountALLPage(timeNow);
		if (totalPage % 5 != 0) {
			totalPage = totalPage / 5 + 1;
		} else {
			totalPage = totalPage / 5;
		}
		map.put("orderList", orderList);
		map.put("totalPage", totalPage);
		return map;
	}

	@Override
	public Map<String, Object> findAllLive(int nowPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<LiveBean> list = dao.selectAllLive(nowPage);
		int num = dao.selectAllLiveNum();
		int totalPage = 0;
		if (num % 7 == 0) {
			totalPage = num / 7;
		} else {
			totalPage = (num / 7) + 1;
		}
		map.put("list", list);
		map.put("totalPage", totalPage);
		return map;
	}

	public Map<String, Object> dealWithOrder(OrderBean oldorder) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询订单
		List<OrderBean> orderList = dao.findOrderAllAndAll(oldorder);
		// 判断
		if (orderList.size() == 0) {
			map.put("message", "处理错误，请稍后再试");
			map.put("state", 0);
			return map;
		}
		//判断支付状态：支付状态大于1，不能退单
		if(orderList.get(0).getO_state()>1) {
			map.put("message", "处理错误，请稍后再试");
			map.put("state", 0);
			return map;
		}
		// 判断时间是否符合撤销订单:条件1.是否到达入住时间的下午6点
		boolean bo = BackOutOrderTimeJudgeUtil.judgeTime(orderList.get(0).getO_intime());
		if (!bo) {
			map.put("message", "时间超过预计入住时间当天下午6点，无法退单");
			map.put("state", 0);
			return map;
		}
		
		// 判断是否已付款，已付款则退款
		if (orderList.get(0).getO_state() == 1) {
	
			
			// 调用支付宝退款接口

			// 获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayUtil.getValue("serverUrl"),
					AlipayUtil.getValue("appId"), AlipayUtil.getValue("privateKey"), AlipayUtil.getValue("format"),
					AlipayUtil.getValue("charset"), AlipayUtil.getValue("alipayPublicKey"),
					AlipayUtil.getValue("signType"));

			// 设置请求参数
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			String out_trade_no = null;
			String refund_amount = null;
			try {
				// 商户订单号，商户网站订单系统中唯一订单号，必填
				out_trade_no = new String(orderList.get(0).getO_id().getBytes("UTF-8"), "UTF-8");
				// 付款金额，必填
				refund_amount = new String(String.valueOf(orderList.get(0).getO_r_price()).getBytes("UTF-8"), "UTF-8");

			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}

			request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," 
					+ "\"refund_amount\":\"" + refund_amount + "\"," + "\"refund_reason\":\"" + null + "\"}");

			// 请求

			AlipayTradeRefundResponse response = null;
			try {
				response = alipayClient.execute(request);
			} catch (AlipayApiException e) {

				e.printStackTrace();
			}

			if (response.isSuccess()) {
				 System.out.println("调用成功");
				 Map<String, Object> result=this.quitOrderAllOnline(orderList.get(0));
				if(result.get("message").equals("取消订单失败")) {
					System.out.println("订单退款成功,err--订单修改退款状态失败，请手动修改，订单号为："+orderList.get(0).getO_id());
				}
				 map.put("result", "撤销订单成功");
				 map.put("state", 1);
			} else {
				System.out.println("调用失败");
				
				 map.put("result", "撤销订单失败");
				 map.put("state", 0);
				 return map;
			}

		
			 
		} else {
			// 删除订单
			 Map<String, Object> result= this.quitOrderAllOnline(orderList.get(0));
			if (result.get("message").equals("取消订单成功")) {
				map.put("result", "撤销订单成功");
				map.put("state", 1);
				redis.del("order_"+oldorder.getO_id());
			} else {
				
				 map.put("result", "撤销订单失败");
				 map.put("state", 0);
				
			}
		}

		return map;
	}

	@Override
	public Map<String, Object> findRoom(int r_num) {
		Map<String, Object> map = new HashMap<String, Object>();
		RoomBean bean = new RoomBean();
		bean.setR_num(r_num);
		List<RoomBean> roomBeans = rservice.findByTerm(bean);
		if (roomBeans.size() > 0) {
			bean = roomBeans.get(0);
			bean.setR_id(0);
			bean.setR_num(0);
			bean.setR_note("");
			String l_o_id = dao.selectLiveOrder(r_num);
			OrderBean order = dao.selectOrderOneByOid(l_o_id);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String time = null;
			time = df.format(new Date());
			List<RoomBean> room = rservice.findAllByTermAndTime(bean, time, order.getO_outtime());
			map.put("list", room);
		} else {
			map.put("result", "房间不存在");
		}
		return map;
	}

	@Override
	public Map<String, Object> findAllOrderTime(OrderBean orderBean, String beginTime, String endTime) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderBean> orderAny = dao.findOrderAllAndAll(orderBean);
		System.out.println(orderAny.size());
		if (beginTime != null && beginTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = orderAny.size() - 1; i > -1; i--) {
				try {
					String timeO = orderAny.get(i).getO_ordertime();
					if (format.parse(endTime).getTime() >= format.parse(timeO).getTime()
							&& format.parse(beginTime).getTime() <= format.parse(timeO).getTime()) {

					} else {
						orderAny.remove(orderAny.get(i));
					}
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("？");
				}
			}
		}

		map.put("orderAny", orderAny);

		return map;
	}

	@Override
	public String updateEdit(LiveBean liveBean) {
		String result = null;
		int num = dao.updateEdit(liveBean);
		if (num > 0) {
			result = "编辑成功";
		} else {
			result = "编辑失败";
		}
		return result;
	}

	@Override
	public Map<String, Object> findMoneyByMonth(String year) {

		Map<String, Object> map = new HashMap<String, Object>();
		String yearMon = null;
		for (int i = 1; i < 13; i++) {
			if (i < 10) {
				yearMon = year + "-0" + i;
			} else {
				yearMon = year + "-" + i;
			}
			List<Double> list = dao.selectMoneyByMon(yearMon);

			Double monthMoney = 0.0;
			if (list != null) {
				for (int j = 0; j < list.size(); j++) {
					monthMoney += list.get(j);
				}
				map.put("" + i, monthMoney);
			}
		}

		return map;

	}

	@Override
	public Map<String, Object> quitOrderAllOnline(OrderBean oldorder) {

		Map<String, Object> map = new HashMap<String, Object>();
		//查询订单详情
		List<OrderInfoBean> list = this.findOederInfoAll(oldorder.getO_id());
		// 软删除订单表
		boolean bo = this.removeOrderByOid(oldorder.getO_id());
		if (bo) {

			// 软删除订单详情表
			boolean be = this.removeOrderInfoByFlag(oldorder.getO_id());
			if (be) {

				for (OrderInfoBean bean : list) {
					// 退房时从redis缓存删除该房间的时间戳
					redservice.delTimeToRedis(String.valueOf(bean.getI_r_id()), oldorder.getO_intime(),
							oldorder.getO_outtime());
				}
				map.put("message", "取消订单成功");
				return map;
			}
		}
		map.put("message", "取消订单失败");
		return map;

	}

}
