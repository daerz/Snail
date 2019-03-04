package com.project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bean.ORParamBean;
import com.project.bean.RoomBean;
import com.project.service.IRoomService;

/**
 * 控制层
 * 房间业务
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/room")
public class RoomController {
	
	private final String regexTime = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	
	@Autowired
	private IRoomService rser;
	
	/**
	 * 查询所有房间信息
	 * @return 返回所有房间信息的集合
	 */
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public List<RoomBean> findAllRoom(){
		List<RoomBean> list = rser.findAllRoom();
		return list;
	}
	
	/**
	 * 查询分页房间信息
	 * @return 返回房间信息的集合
	 */
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findRoomByPage(String s_page,String s_count){
		String result = "查询失败";
		Map<String,Object> map = new HashMap<>();
		int page = 0;
		int count = 0;
		try {
			page = Integer.parseInt(s_page);
			count = Integer.parseInt(s_count);
			map = rser.findRoomByPage(page, count);
			result="查询成功";
			map.put("result", result);
		} catch (Exception e) {
			map.put("result", result);
			result = "查询失败";
		}
		return map;
	}
	
	/**
	 * 查询可入住的房间
	 * @param bean	条件信息对象
	 * @param beginDay	开始日期
	 * @param endDay	结束日期
	 * @return
	 */
//	@RequestMapping(value="/search",method=RequestMethod.GET)
//	@ResponseBody
//	public List<List<String>> findRoomByTermAndTime(RoomBean bean,String beginDay,String endDay){
//		List<List<String>> list = rser.findByTermAndTime(bean, beginDay, endDay);
//		return list;
//	}
	
	/**
	 * 查找每个房型的数量
	 * @return 
	 */
	@RequestMapping(value="/group",method=RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> findRoomGroup(){
		List<Map<String,Object>> list = new ArrayList<>();
		list = rser.findRoomGroup();
		return list;
	}
	
	
	/**
	 * 添加房间
	 * @param bean	新房间类型
	 * @param result 校验结果
	 * @return	返回添加结果
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRoom(@Validated RoomBean bean,BindingResult result){
		Map<String, Object> map = new HashMap<String,Object>();
		//添加回显信息
		map.put("bean", bean);
		//=================将参数默认化
		if(bean.getR_smoken()==0){
			bean.setR_smoken(1);
		}
		if(bean.getR_breakfast()==0){
			bean.setR_breakfast(1);
		}
		if(bean.getR_window()==0){
			bean.setR_window(1);
		}
		if(bean.getR_price()==0){
			bean.setR_price(9999);
		}
		//==================手动校验
		int status = 0;
		if(bean.getR_num() == 0){
			map.put("err_r_num","请输入正确的房间号");
			status =1;
		}
		if(bean.getR_rt_id() == 0){
			map.put("err_r_rt_id","请输入正确的房型号");
			status =1;
		}
		if(bean.getR_note().length()>100){
			map.put("err_r_note","描述不能超过100字");
			status =1;
		}
		//==================返回手动和自动校验的结果
		if(result.hasErrors() || status==1){
			//添加状态信息
			map.put("result", "添加失败");
			//获得错误信息数组
			List<FieldError> list = result.getFieldErrors();
			//将错误信息放入map
			for (FieldError fieldError : list) {
				map.put("err_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			//将类型错误转换成中文提示
			for (String key : map.keySet()) {
				if(map.get(key)!=null && map.get(key).toString().indexOf("Failed") >= 0){
					map.put(key, "请输入正确的格式！");
				}
			}
			return map;
		}		
		int num = rser.addRoom(bean);
		if(num==1){
			map.put("result", "添加成功");
		}else{
			map.put("result", "添加失败");
		}
		return map;
	}
	
	/**
	 * 获取当天空余的房间
	 * @return
	 */
	@RequestMapping(value="/empty",method=RequestMethod.GET)
	@ResponseBody
	public List<RoomBean> findEmptyRoomNum(){
		RoomBean bean = new RoomBean();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String day = format.format(date);
		List<RoomBean> list = rser.findAllByTermAndTime(bean, day, day);
		return list;
	}
	
	/**
	 * 软删除房间信息
	 * @param bean	条件存储对象
	 * @param result	校验错误结果
	 * @return	返回删除结果
	 */
	@RequestMapping(value="/remove",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> removeRoom(@Validated RoomBean bean,BindingResult result){
		Map<String, Object> map = new HashMap<String,Object>();
		//==================初始化无效参数
		bean.setR_breakfast(0);
		bean.setR_discount(0);
		bean.setR_flage(1);
		bean.setR_num(0);
		bean.setR_window(0);
		bean.setR_smoken(0);
		bean.setR_price(0);
		bean.setR_discount(0);
		bean.setR_note(null);
		//=================手动校验
		if(bean.getR_id()<=0){
			map.put("result", "房间删除失败");
			return map;
		}
		//==================软删除房间
		int num = rser.changeRoom(bean);
		if(num==0){
			map.put("result", "房间删除失败");
		}else{
			map.put("result", "房间删除成功");
		}
		return map;
	}
	
	
	/**
	 * 更具条件查询相应的房间
	 * @param bean	条件对象
	 * @param beginDay	开始时间
	 * @param endDay	结束时间
	 * @param result	错误结果
	 * @return	返回结果信息
	 */
	@RequestMapping(value="/term",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findRoomByTerm(@Validated RoomBean bean,BindingResult result,String beginDay,String endDay){
		Map<String,Object> map = new HashMap<>();
		//添加回显信息
		map.put("bean", bean);
		//==================手动校验
		int status = 0;
		if(beginDay!=null && beginDay!=""){
			if(!beginDay.matches(regexTime)){
				status = 1;
				map.put("err_beginDay","请输入正确开始时间");
			}
		}
		if(endDay!=null && endDay!=""){
			if(!endDay.matches(regexTime)){
				status = 1;
				map.put("err_endDay","请输入正确结束时间");
			}
		}
		//==================返回手动和自动校验的结果
		if(result.hasErrors() || status ==1){
			//添加状态信息
			map.put("result", "查询失败");
			//获得错误信息数组
			List<FieldError> list = result.getFieldErrors();
			//将错误信息放入map
			for (FieldError fieldError : list) {
				map.put("err_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			//将类型错误转换成中文提示
			for (String key : map.keySet()) {
				if(map.get(key)!=null && map.get(key).toString().indexOf("Failed") >= 0){
					map.put(key, "请输入正确的格式！");
				}
			}
			return map;
		}		
		List<RoomBean> list = rser.findAllByTermAndTime(bean, beginDay, endDay);
		map.put("result", "查询成功");
		map.put("room", list);
		return map;
	}
	
	/**
	 * 改变房间信息
	 * @param bean
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/change",method=RequestMethod.PUT)
	@ResponseBody
	public Map<String,Object> changeRoom(@Validated RoomBean bean,BindingResult result){
		Map<String,Object> map = new HashMap<>();
		//初始化参数
		bean.setR_flage(0);
		//添加回显信息
		map.put("bean", bean);
		//==================手动校验
		int status = 0 ;
		if(bean.getR_id()==0){
			map.put("err_r_id", "请输入正确的房间号");
			status=1;
		}
		//==================返回手动和自动校验的结果
		if(result.hasErrors() || status==1){
			//添加状态信息
			map.put("result", "修改失败");
			//获得错误信息数组
			List<FieldError> list = result.getFieldErrors();
			//将错误信息放入map
			for (FieldError fieldError : list) {
				map.put("err_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			//将类型错误转换成中文提示
			for (String key : map.keySet()) {
				if(map.get(key)!=null && map.get(key).toString().indexOf("Failed") >= 0){
					map.put(key, "请输入正确的格式！");
				}
			}
			return map;
		}	
		int num = rser.changeRoom(bean);
		if(num == 0){
			//添加状态信息
			map.put("result", "修改失败");
		}else{
			//添加状态信息
			map.put("result", "修改成功");
		}
		return map;
	}
	
	@RequestMapping(value="/allot",method=RequestMethod.GET)
	@ResponseBody
	public List<RoomBean> allot(ORParamBean bean){
		
		return rser.allotRoom(bean);
	}
	
}
