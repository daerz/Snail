package com.project.controller;

import java.util.ArrayList;
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

import com.project.bean.RoomBean;
import com.project.bean.RoomTypeBean;
import com.project.service.IRoomTypeService;
import com.project.util.ValidationUtil;

/**
 * 控制层
 * 房型业务
 * @author 大耳贼
 *
 */
@Controller
@RequestMapping("/roomType")
public class RoomTypeController {
	
	@Autowired
	private IRoomTypeService service;
	
	/**
	 * 通过时间和房间信息进行筛选后的所有信息
	 * @param bean 筛选房间信息
	 * @param beginDay 租房开始日期
	 * @param endDay 租房结束日期
	 * @return
	 */
	@RequestMapping(value="/source",method=RequestMethod.GET)
	@ResponseBody
	public List<Object> findAllRoomTypeByTermAndTime(String r_window, String r_breakfast, String r_smoken, String rt_num, String beginTime, String endTime){				
		List<Object> list = new ArrayList<Object>();
		/*========================查询数据验证=========================*/
		int num = ValidationUtil.stringToInt(rt_num);
		int window = ValidationUtil.condition(r_window);
		int breakfast = ValidationUtil.condition(r_breakfast);
		int smoken = ValidationUtil.condition(r_smoken);
		RoomBean bean = new RoomBean();
		bean.setR_window(window);
		bean.setR_breakfast(breakfast);
		bean.setR_smoken(smoken);
		/*========================查询数据验证=========================*/
		Map<String, String> map = ValidationUtil.dateValidation(beginTime, endTime);
		list = service.findRoomTypeByTermAndTime(bean, num, map.get("beginDay"), map.get("endDay"));
		return list;
	} 	
	
	/**
	 * 添加房型信息
	 * @param bean 信息对象
	 * @return 返回是否添加成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRoomType(@Validated RoomTypeBean bean, BindingResult result){
		String imgPath = "/Snail/upload/defaultImages/defaultImage.jpg";
		bean.setRt_img(imgPath);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("result", "添加失败！");
		/*========================验证错误信息=========================*/
		if(result.hasErrors()){
			List<FieldError> list = result.getFieldErrors();
			for (FieldError fieldError : list) {
				map.put("err_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			if(map.get("err_rt_num") != null){
				if(map.get("err_rt_num").toString().indexOf("Failed") > -1)
					map.put("err_rt_num", "请输入正确格式内容！");
			}
			if(map.get("err_rt_id") != null){
				if(map.get("err_rt_id").toString().indexOf("Failed") > -1)
					map.put("err_rt_id", "请输入正确格式内容！");
			}
			if(map.get("err_rt_area") != null){
				if(map.get("err_rt_area").toString().indexOf("Failed") > -1)
					map.put("err_rt_area", "请输入正确格式内容！");
			}
			bean.setRt_img(null);
			map.put("bean", bean);
			map.put("result", "添加失败！");
			return map;
		}
		/*========================验证错误信息=========================*/
		boolean boo = service.addRoomType(bean);
		if(boo)
			map.put("result", "添加成功！");
		return map;
	}
	
	/**
	 * 修改房型信息
	 * @param bean 信息对象
	 * @return 返回是否修改成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.PUT,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String changeRoomType(String rt_id, String rt_num, String rt_area, String rt_img, String rt_name, String rt_note){
		String result = "修改失败！";
		int area = -1;
		if(rt_area == null){
			area = 0;
		}else{
			try {
				area = Integer.parseInt(rt_area);
				if(area < 20 || area > 1000)
					return result = "房间面积不得大于1000或小于20平米！";
			} catch (Exception e) {
				return result;
			}
		}
		
		int id_num = -1;
		if(rt_num == null){
			id_num = 0;
		}else{
			try {
				id_num = Integer.parseInt(rt_num);
				if(id_num < 1 || id_num > 20)
					return result = "可居住人数不得大于20或小于1人！";
			} catch (Exception e) {
				return result;
			}
		}
		if(rt_name != null){
			if(rt_name.length() > 20)
				return result = "名称长度不得大于20！";	
		}
		if(rt_note != null){
			if(rt_note.length() > 100)
				return result = "备注信息长度不得大于100！";	
		}		
		int id = ValidationUtil.stringToInt(rt_id);
		if(id == 0 || id_num == -1 || area == -1)
			return result;
		RoomTypeBean bean = new RoomTypeBean();
		bean.setRt_id(id);
		bean.setRt_num(id_num);
		bean.setRt_area(area);
		if(rt_img != null){
			if(!rt_img.matches(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$")){
				return result;
			}
		}
		bean.setRt_img(rt_img);
		bean.setRt_name(rt_name);
		bean.setRt_note(rt_note);
		boolean boo = service.changeRoomType(bean);
		if(boo)
			result = "修改成功！";
		return result;
	}
	
	/**
	 * 软删除房型指定房型
	 * @param rt_id 房型id
	 * @return 返回是否删除成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.DELETE,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteRoomType(String rt_id){
		String result = "删除失败！";
		int id = -1;
		if(rt_id == null)
			return result = "删除失败！";
		if(!rt_id.matches("^\\d+$"))
			return result = "删除失败！";
		id = Integer.parseInt(rt_id);
		if(id <= 0)
			return result = "删除失败！";
		result = service.deleteRoomType(id);
		return result;
	}
	
	/**
	 * 只查询所有房型(包含软删除的)
	 * @return
	 */
	@RequestMapping(value="/source/all",method=RequestMethod.GET)
	@ResponseBody
	public List<RoomTypeBean> findAllRoomType(){
		List<RoomTypeBean> list = service.findAllRoomType();
		return list;
	}
	
	/**
	 * 查询房型(用于index页面)
	 * @return
	 */
	@RequestMapping(value="/source/index",method=RequestMethod.GET)
	@ResponseBody
	public List<RoomTypeBean> findRoomTypeUseIndex(){
		List<RoomTypeBean> list = service.findRoomTypeUseIndex();
		return list;
	}
	
	@RequestMapping(value="/source/rtid",method=RequestMethod.GET)
	@ResponseBody
	public List<Object> findRoomTypeByRtid(String rt_id,String idCount){
		List<Object> list = new ArrayList<Object>();
		int countId = -1;
		try {
			String idArr [] = idCount.split(",");
			countId = Integer.parseInt(idArr[0]);
		} catch (Exception e) {
			list.add("页面走丢了");
			//数据不合法，后台判定是否为"页面走丢了",是则跳转404
			return list;
		}
		int id = ValidationUtil.stringToInt(rt_id);
		if(id == 0 && countId == -1){
			list.add("页面走丢了");
			return list;
		}
		list = service.findRoomTypeByRtid(id,countId);
		return list;
	}
	
	/**
	 * 购物车获取数据
	 * @param rt_id
	 * @param idCount
	 * @return
	 */
	@RequestMapping(value="/source/cart",method=RequestMethod.GET)
	@ResponseBody
	public List<Object> findRoomTypeByCart (String rt_id,String idCount){
		List<Object> list = new ArrayList<Object>();
		int countId = -1;
		try {
			String idArr [] = idCount.split(",");
			countId = Integer.parseInt(idArr[0]);
		} catch (Exception e) {
			list.add("页面走丢了");
			//数据不合法，后台判定是否为"页面走丢了",是则跳转404
			return list;
		}
		int id = ValidationUtil.stringToInt(rt_id);
		if(id == 0 && countId == -1){
			list.add("页面走丢了");
			return list;
		}
		list = service.findRoomTypeByCart(id,countId);
		return list;
	}
}
