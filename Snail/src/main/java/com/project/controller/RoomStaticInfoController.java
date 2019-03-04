package com.project.controller;

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

import com.project.bean.RoomStaticInfoBean;
import com.project.service.IRoomStaticInfoService;
import com.project.util.ValidationUtil;

/**
 * 控制层
 * 房型静态信息业务
 * @author 大耳贼
 *
 */
@Controller
@RequestMapping("/roomStaticInfo")
public class RoomStaticInfoController {

	@Autowired
	private IRoomStaticInfoService service;
	
	/**
	 * 根据房型id查询房型静态信息
	 * @param rt_id 房型id
	 * @return 返回静态信息对象
	 */
	@RequestMapping(value="/source",method=RequestMethod.GET)
	@ResponseBody
	public List<RoomStaticInfoBean> findStaticByRtid(String rt_id){
		int id = ValidationUtil.stringToInt(rt_id);
		List<RoomStaticInfoBean> list = service.findStaticByRtid(id);
		return list;
	}
	
	/**
	 * 修改指定房型静态信息
	 * @param bean 信息对象
	 * @return 返回是否修改成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.PUT,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String changeStatic(String si_id, String si_rt_id, String si_furniture, 
			String si_bath, String si_entertainment, String si_airport, String si_cancleRule,
			String si_useRule){	
		String result = "修改失败！";
		int id_rt_si = -1;
		if(si_rt_id == null){
			id_rt_si = 0;
		}else{
			try {
				id_rt_si = Integer.parseInt(si_rt_id);
			} catch (Exception e) {
				return result;
			}
		}
		int id = ValidationUtil.stringToInt(si_id);
		if(id == 0 || id_rt_si < 0)
			return result;
		if(si_furniture != null){
			if(si_furniture.length() > 20){
				return result;
			}
		}
		if(si_bath != null){
			if(si_bath.length() > 20){
				return result;
			}
		}
		if(si_entertainment != null){
			if(si_entertainment.length() > 20){
				return result;
			}
		}
		if(si_airport != null){
			if(si_airport.length() > 20){
				return result;
			}
		}
		if(si_cancleRule != null){
			if(si_cancleRule.length() > 100){
				return result;
			}
		}
		if(si_useRule != null){
			if(si_useRule.length() > 100){
				return result;
			}
		}
		RoomStaticInfoBean bean = new RoomStaticInfoBean();
		bean.setSi_id(id);
		bean.setSi_rt_id(id_rt_si);
		bean.setSi_airport(si_airport);
		bean.setSi_bath(si_bath);
		bean.setSi_cancleRule(si_cancleRule);
		bean.setSi_entertainment(si_entertainment);
		bean.setSi_furniture(si_furniture);
		bean.setSi_useRule(si_useRule);		
		boolean boo = service.changeStatic(bean);
		if(boo)
			result = "修改成功！";
		return result;
	}
	
	/**
	 * 修改指定房型静态信息
	 * @param bean 信息对象
	 * @return 返回是否修改成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStatic(@Validated RoomStaticInfoBean bean, BindingResult result){	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result","添加失败！");
		boolean boo = false;
		if(result.hasErrors()){
			List<FieldError> list = result.getFieldErrors();
			for (FieldError fieldError : list) {
				map.put("err_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			if(map.get("err_si_rt_id") != null){
				if(map.get("err_si_rt_id").toString().indexOf("Failed") > -1)
					map.put("err_si_rt_id", "请输入正确格式内容！");	
			}
			if(map.get("err_si_id") != null){	
				if(map.get("err_si_id").toString().indexOf("Failed") > -1)
					map.put("err_si_id", "请输入正确格式内容！");	
			}
			map.put("bean", bean);
			map.put("result","添加失败！");
			return map;
		}
		boo = service.addStatic(bean);
		if(boo)
			map.put("result", "添加成功！");
		return map;
	}
	
	/**
	 * 软删除静态信息(根据静态信息id)
	 * @param si_id 静态信息id
	 * @return 返回是否删除
	 */
	@RequestMapping(value="/source",method=RequestMethod.DELETE,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteStatic(String si_id){
		String result = "删除失败！";
		int id = ValidationUtil.stringToInt(si_id);
		if(id  == 0)
			return result;
		boolean boo = service.deleteStaticBySiid(id);
		if(boo)
			result = "删除成功！";
		return result;
	}
}
