package com.project.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bean.ChangeRoomBean;
import com.project.bean.LiveBean;
import com.project.bean.ORParamBean;
import com.project.bean.OrderBean;
import com.project.bean.OrderConditionBean;
import com.project.bean.OrderInfoBean;
import com.project.service.IOrderService;
import com.project.util.RedisUtil;
import com.project.util.ValidateTime;

/**
 * 控制层 订单业务
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	private final String regexTime = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	private final String regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	@Autowired
	private IOrderService oservice;

	/**
	 * 线下单独下单
	 * 
	 * @param o_phone
	 *            入住手机号
	 * @param i_r_id
	 *            房间ID
	 * @param i_r_num
	 *            房间号
	 * @param r_price
	 *            房间单价
	 * @param o_intime
	 *            房间起始时间
	 * @param o_outtime
	 *            房间结束时间
	 * @return
	 */
	@RequestMapping(value = "/createOrderNotLine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOrderNotLine(String o_phone, String i_r_id, String i_r_num, String r_price,
			String o_intime, String o_outtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 验证信息
		if (o_phone == null || o_phone.trim().equals("") || !o_phone.matches(regexp)) {
			map.put("message", "电话号码格式错误");
			return map;
		}
		if (i_r_id == null || i_r_id.trim().equals("")) {
			map.put("message", "房间ID格式有误");
			return map;
		}
		if (i_r_num == null || i_r_num.trim().equals("")) {
			map.put("message", "房间号码格式有误");
			return map;
		}
		if (r_price == null || r_price.trim().equals("")) {
			map.put("message", "单价格式有误");
			return map;
		}
		// 验证时间是否正确
		boolean timeBo = ValidateTime.checkTime(o_intime, o_outtime);
		if (!timeBo) {
			map.put("message", "时间输入有误");
			return map;
		}

		OrderBean orderbean = new OrderBean();
		OrderInfoBean orderinfobean = new OrderInfoBean();

		// 封装订单表
		orderbean.setO_phone(o_phone);
		orderbean.setO_intime(o_intime);
		orderbean.setO_outtime(o_outtime);
		orderbean.setO_u_phone(o_phone);
		// 封装订单详情表
		int i_r_id1 = Integer.parseInt(i_r_id);
		orderinfobean.setI_r_id(i_r_id1);
		int i_r_num1 = Integer.parseInt(i_r_num);
		orderinfobean.setI_r_num(i_r_num1);

		map = oservice.createOrderNotLineAll(orderbean, orderinfobean, r_price);
		return map;
	}

	/**
	 * 在后台订单里根据页码查询所有订单,即刷新页面的时候自动调用的方法
	 * 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findAllList(String page) {
		int currentPage = 0;
		try {
			currentPage = Integer.parseInt(page);
			currentPage = (currentPage - 1) * 5;
		} catch (Exception e) {
			currentPage = 1;
		}
		Map<String, Object> map = oservice.findAllOrder(currentPage);
		return map;
	}

	/**
	 * 线上下订单
	 * 
	 * @param orp
	 * @return
	 */
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOrder(@Validated ORParamBean orp, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<FieldError> list = result.getFieldErrors();
			for (FieldError fieldError : list) {
				map.put("err_" + fieldError.getField(), fieldError.getDefaultMessage());
			}
			System.out.println(map);
			map.put("statecode", 0);
			return map;
		}

		boolean timeBo = ValidateTime.checkTime(orp.getBeginTime(), orp.getEndTime());

		if (!timeBo) {
			map.put("message", "时间输入有误");
			map.put("statecode", 0);
			return map;
		}

		map = oservice.createOrderALL(orp);
		return map;
	}

	/**
	 * 客户撤销订单接口判定
	 * 
	 * @param o_id
	 * @return
	 */
	@RequestMapping(value = "/quitOrderClient", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> quitOrderClient(String o_id) {
		System.out.println(o_id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (o_id == null || o_id.trim().equals("")) {
			map.put("message", "订单号不能为空");
			map.put("state", 0);
			return map;
		}
		Subject subject = SecurityUtils.getSubject();
		String phone;
		try {
			Session session = subject.getSession();
			phone = (String) session.getAttribute("phone");
		} catch (Exception e) {
			map.put("message", "用户未登录");
			map.put("state", 0);
			return map;
		}
		// String phone="13302010501";
		// System.out.println("phone:"+phone);
		if (phone == null) {
			map.put("message", "用户未登录");
			map.put("state", 0);
			return map;
		}
		OrderBean oldorder = new OrderBean();
		oldorder.setO_id(o_id);
		oldorder.setO_u_phone(phone);
		map = oservice.dealWithOrder(oldorder);
		return map;
	}

	/**
	 * 前端查询订单（条件必须登录能获取电话号码）
	 *
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryOrderByPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOrder(String page) {
		Map<String, Object> map = new HashMap<String, Object>();
		OrderConditionBean condition = new OrderConditionBean();
		// 获取当前用户的手机号，只能查询自己的订单
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String phone = null;
		try {
			phone = (String) session.getAttribute("phone");
		} catch (InvalidSessionException e1) {

			e1.printStackTrace();
		}
		// String phone="13302010501";
		if (phone == null) {
			map.put("err", "错误");
			return map;
		}
		condition.setO_u_phone(phone);

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(page);
			if (currentPage < 1) {
				currentPage = 1;
			}
		} catch (NumberFormatException e) {
			currentPage = 1;

		}
		condition.setLimitNum((currentPage - 1) * 5);

		map = oservice.findOrderByIdAndPage(condition);

		return map;
	}

	/**
	 * 修改为已支付
	 * 
	 * @param o_id
	 * @return
	 */
	@RequestMapping(value = "/updateO_state", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updateO_state(String o_id) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (o_id == null || o_id.trim().equals("")) {
			map.put("message", "订单号有误");
			return map;
		}
		// 状态码为未支付
		int state = 0;
		// 查询当前状态码
		OrderBean neworder = oservice.findOrderOneByOid(o_id);
		state = neworder.getO_state();
		if (state == 3 || state == 1 || state == 2 || state == 4) {
			map.put("state", state);
			return map;
		}
		if (state == 0) {
			Boolean re = oservice.changeO_state(o_id);
			if (re) {
				map.put("message", "ok");
				return map;
			}

		}
		map.put("message", "fail");
		return map;
	}

	/**
	 * 线下取消总订单
	 * 
	 * @param o_id
	 * @return
	 */
	@RequestMapping(value = "/quitOrder", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> quitOrder(String o_id) {
		System.out.println(o_id);
		Map<String, Object> map = new HashMap<String, Object>();

		if (o_id == null || o_id.trim().equals("")) {
			map.put("err_o_id", "订单号不能为空");
			return map;
		}

		OrderBean oldorder = new OrderBean();
		oldorder.setO_id(o_id);
		map = oservice.quitOrderAll(oldorder);
		return map;
	}

	/**
	 * 线下根据单个房间取消部分订单的部分东西
	 * 
	 * @param i_o_id
	 *            订单id
	 * @param i_r_id
	 *            房间id
	 * @return
	 */
	@RequestMapping(value = "/quitRoomOrder", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> quitRoomOrder(String i_o_id, String i_r_id_s) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (i_o_id == null || i_o_id.equals("")) {
			map.put("err_i_o_id", "输入有误");
			return map;
		}
		
		int i_r_id = 0;
		try {
			i_r_id = Integer.parseInt(i_r_id_s);
		} catch (Exception e) {
			map.put("err_o_id", "房间号有误");
			return map;
		}
		if(i_r_id==0){
			map.put("err_o_id", "房间号不能为空");
			return map;
		}
		map = oservice.quitRoomOrderPer(i_o_id, i_r_id);
		return map;
	}

	/**
	 * 查询所有入住信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectAllLive", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectAllLive(String spage) {
		int nowPage = 0;
		try {
			nowPage = Integer.parseInt(spage);
			nowPage = (nowPage - 1) * 7;
		} catch (Exception e) {
			nowPage = 0;
		}
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> sr = oservice.findAllLive(nowPage);
		map.put("list", sr.get("list"));
		map.put("totalPage", sr.get("totalPage"));
		return map;
	}

	/**
	 * 根据输入的部分信息进行订单查询筛选
	 * 
	 * @param orderBean
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/findTimeOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findTimeOrder(OrderBean orderBean, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(beginTime!=null && beginTime!=""){
			if(!beginTime.matches(regexTime)){
				map.put("err_beginDay","请输入正确开始时间");
				return map;
			}
		}
		if(endTime!=null && endTime!=""){
			if(!endTime.matches(regexTime)){
				map.put("err_endDay","请输入正确结束时间");
				return map;
			}
		}
		
		map = oservice.findAllOrderTime(orderBean, beginTime, endTime);
		return map;

	}

	/**
	 * 根据条件查入住信息
	 * 
	 * @param liveBean
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/selectLive", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectLive(@Validated LiveBean liveBean, BindingResult result, String beginTime,
			String outTime) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		int sr = 0;
		// 初始化无效参数
		liveBean.setL_intime(null);
		liveBean.setL_outtime(null);
		// 手动校验
		if (liveBean.getL_uid() == null || liveBean.getL_uid().equals("") || liveBean.getL_uid().matches(
				"(^[1-9]\\d{5}(19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)")) {

		} else {
			map.put("result", "身份证格式不正确");
			sr = 1;
			return map;
		}
		if (liveBean.getL_usex() == null || liveBean.getL_usex().equals("")
				|| liveBean.getL_usex().matches("[男女]{1}")) {

		} else {
			map.put("result", "性别格式不正确");
			sr = 1;
			return map;
		}
		if (liveBean.getL_uname() == null || liveBean.getL_uname().equals("")
				|| liveBean.getL_uname().matches("^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$")) {

		} else {
			map.put("result", "姓名格式不正确");
			sr = 1;
			return map;
		}
		if (liveBean.getL_o_outtime() == null || liveBean.getL_o_outtime().equals("")
				|| liveBean.getL_o_outtime().matches(regexTime)) {

		} else {
			map.put("result", "时间格式错误");
			sr = 1;
			return map;
		}
		if (beginTime == null || beginTime.equals("") || beginTime.matches(regexTime)) {

		} else {
			map.put("result", "时间格式错误");
			sr = 1;
			return map;
		}
		if (outTime == null || outTime.equals("") || outTime.matches(regexTime)) {

		} else {
			map.put("result", "时间格式错误");
			sr = 1;
			return map;
		}
		if (beginTime != null && outTime != null && beginTime != "" && outTime != "") {
			if (beginTime.matches(regexTime) && beginTime.matches(regexTime)) {
				if (time.parse(beginTime).getTime() > time.parse(outTime).getTime()) {
					map.put("result", "开始时间不能大于结束时间");
					sr = 1;
					return map;
				}
			}
		}

		if (result.hasErrors() || sr == 1) {
			List<FieldError> listErr = result.getFieldErrors();
			for (FieldError fieldError : listErr) {
				map.put("result", fieldError.getDefaultMessage());
			}
			for (String key : map.keySet()) {
				if (map.get(key) != null && map.get(key).toString().indexOf("Failed") >= 0) {
					map.put(key, "请输入正确的格式！");
				}
			}
			return map;
		}
		List<LiveBean> list = oservice.findLive(liveBean, beginTime, outTime);
		map.put("list", list);
		return map;
	}

	/**
	 * 添加入住
	 * 
	 * @param liveBean
	 * @return
	 */
	@RequestMapping(value = "/addLive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addLive(@Validated LiveBean liveBean, BindingResult result) {
		Map<String, Object> map = new HashMap<>();
		int sr = 0;
		// 手动校验
		if (liveBean.getL_uid().matches(
				"(^[1-9]\\d{5}(19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)")) {

		} else {
			map.put("result", "身份证格式不正确");
			sr = 1;
			return map;
		}
		if (liveBean.getL_o_outtime().matches(regexTime)) {

		} else {
			map.put("result", "时间格式错误");
			sr = 1;
			return map;
		}

		if (liveBean.getL_usex().matches("[男女]{1}")) {

		} else {
			map.put("result", "性别格式不正确");
			sr = 1;
			return map;
		}
		if (liveBean.getL_uname().length() >= 2
				&& liveBean.getL_uname().matches("^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$")) {

		} else {
			map.put("result", "姓名格式不正确");
			sr = 1;
			return map;
		}
		if (result.hasErrors() || sr == 1) {
			List<FieldError> listErr = result.getFieldErrors();
			for (FieldError fieldError : listErr) {
				map.put("result", fieldError.getDefaultMessage());
			}
			for (String key : map.keySet()) {
				if (map.get(key) != null && map.get(key).toString().indexOf("Failed") >= 0) {
					map.put(key, "请输入正确的格式！");
				}
			}
			return map;
		}
		Map<String, Object> add = oservice.addLive(liveBean);
		map.put("result", add.get("result"));
		return map;
	}

	/**
	 * 编辑入住信息
	 * 
	 * @param liveBean
	 * @return
	 */
	@RequestMapping(value = "/changeEdit", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> changeEdit(@Validated LiveBean liveBean, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		int sr = 0;
		// 手动校验
		if (liveBean.getL_uid() == null || liveBean.getL_uid().equals("") || liveBean.getL_uid().matches(
				"(^[1-9]\\d{5}(19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)")) {

		} else {
			map.put("result", "身份证格式不正确");
			sr = 1;
			return map;
		}
		if (liveBean.getL_usex() == null || liveBean.getL_usex().equals("")
				|| liveBean.getL_usex().matches("[男女]{1}")) {

		} else {
			map.put("result", "性别格式不正确");
			sr = 1;
			return map;
		}
		if (liveBean.getL_uname() == null || liveBean.getL_uname().equals("")
				|| liveBean.getL_uname().matches("^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$")) {

		} else {
			map.put("result", "姓名格式不正确");
			sr = 1;
			return map;
		}
		if (result.hasErrors() || sr == 1) {
			List<FieldError> listErr = result.getFieldErrors();
			for (FieldError fieldError : listErr) {
				map.put("result", fieldError.getDefaultMessage());
				map.put("bean", liveBean);
			}
			for (String key : map.keySet()) {
				if (map.get(key) != null && map.get(key).toString().indexOf("Failed") >= 0) {
					map.put(key, "请输入正确的格式！");
				}
			}
			return map;
		}
		String result1 = oservice.updateEdit(liveBean);
		map.put("result", result1);
		return map;
	}

	/**
	 * 退房
	 * 
	 * @param liveBean
	 * @return
	 */
	@RequestMapping(value = "/deleteLive", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteLive(@Validated LiveBean liveBean, BindingResult result) {
		Map<String, Object> map = new HashMap<>();
		if (result.hasErrors()) {
			List<FieldError> listErr = result.getFieldErrors();
			for (FieldError fieldError : listErr) {
				map.put("result", fieldError.getDefaultMessage());
			}
			for (String key : map.keySet()) {
				if (map.get(key) != null && map.get(key).toString().indexOf("Failed") >= 0) {
					map.put(key, "请输入正确的格式！");
				}
			}
			map.put("result", "退房失败");
			return map;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String time = null;
		time = df.format(new Date());
		Map<String, Object> delete = oservice.updateLive(time, liveBean.getL_r_num());
		if (delete.containsKey("price")) {
			map.put("price", delete.get("price"));
			map.put("result", delete.get("result"));
		} else {
			map.put("result", delete.get("result"));
		}

		return map;
	}

	/**
	 * 查询可换的房间
	 * 
	 * @param r_num
	 * @return
	 */
	@RequestMapping(value = "/selectRoom", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> changeRoom(String room) {
		Map<String, Object> map = new HashMap<>();
		int num = 0;
		if (room == null || room.equals("")) {
			map.put("err_room", "房间号不能为空");
			map.put("result", "房间号错误");
			return map;
		}
		try {
			num = Integer.parseInt(room);
		} catch (Exception e) {
			map.put("err_room", "房间号只能是数字");
			map.put("result", "房间号错误");
			return map;
		}
		Map<String, Object> roomBeans = oservice.findRoom(num);
		if (roomBeans.containsKey("list")) {
			map.put("list", roomBeans.get("list"));
		} else {
			map.put("result", roomBeans.get("result"));
		}
		return map;
	}

	/**
	 * 换房
	 * 
	 * @param l_r_num
	 * @param l_uid
	 * @param r_id
	 * @return
	 */
	@RequestMapping(value = "/updateLive", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> updateLive(@Validated ChangeRoomBean aBean, BindingResult result) {
		Map<String, Object> map = new HashMap<>();
		int sr = 0;
		// 手动校验
		if (!aBean.getL_uid().matches(
				"(^[1-9]\\d{5}(19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)")) {
			map.put("result", "身份证格式不正确");
			sr = 1;
			return map;
		}
		if (result.hasErrors() || sr == 1) {
			List<FieldError> listErr = result.getFieldErrors();
			for (FieldError fieldError : listErr) {
				map.put("result", fieldError.getDefaultMessage());
			}
			for (String key : map.keySet()) {
				if (map.get(key) != null && map.get(key).toString().indexOf("Failed") >= 0) {
					map.put(key, "请输入正确的格式！");
				}
			}
			return map;
		}
		Map<String, Object> change = oservice.changeRoom(aBean.getL_r_num(), aBean.getL_uid(), aBean.getR_id());
		map.put("result", change.get("result"));
		return map;
	}
	/**
	 * 根据订单id查询订单详情表
	 * @param i_o_id
	 * @return
	 */
	@RequestMapping(value = "/queryOrderInfoByi_o_id", method = RequestMethod.GET)
	@ResponseBody
	public List<OrderInfoBean> queryOrderInfoByi_o_id(String i_o_id) {
		List<OrderInfoBean> list = new ArrayList<OrderInfoBean>();

		if (i_o_id == null || i_o_id.trim().equals("")) {
			return list;
		}
		list = oservice.findOederInfoAll(i_o_id);
		System.out.println(list);
		return list;
	}
	
	/**
	 * 展示财务数据图
	 * 
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/queryMoneyByMonth", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryMoneyByMonth(String year) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (year.matches("/[0-9]{4}/")) {
			map.put("message", "请输入正确年月日格式");
			return map;
		}
		map = oservice.findMoneyByMonth(year);
		return map;

	}
	
	/**
	 * 后台首页显示今日预约人数和未入住人数
	 * @param o_id
	 * @param o_u_phone
	 * @param o_phone
	 * @param o_r_price
	 * @param o_ordertime
	 * @param o_state
	 * @param o_intime
	 * @param o_outtime
	 * @return
	 */
	@RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
	@ResponseBody
	public List<OrderBean> queryOrder(String o_id,String o_u_phone,String o_phone,String o_r_price,String o_ordertime,String o_state,String o_intime,String o_outtime) {
		List<OrderBean> list = new ArrayList<OrderBean>();
		OrderBean order=new OrderBean();
		double r_price=0;
		int state=0;
		System.out.println(o_r_price);
		System.out.println(o_state);
		if(o_r_price!=null) {
			 try {
				r_price=Double.parseDouble(o_r_price);
			} catch (NumberFormatException e) {
				
				list.add(null);
				return list;
			}
		}
		if(o_state!=null) {
			try {
				state=Integer.parseInt(o_state);
			} catch (NumberFormatException e) {
				
				list.add(null);
				return list;
			}
		}
	
		order.setO_id(o_id);
		order.setO_u_phone(o_u_phone);
		order.setO_phone(o_phone);
		order.setO_r_price(r_price);
		order.setO_ordertime(o_ordertime);
		order.setO_outtime(o_outtime);
		order.setO_state(state);
		order.setO_intime(o_intime);
	
		
		
		list = oservice.findOrderAllAndAll(order);
		System.out.println(list);
		return list;
	}

}
