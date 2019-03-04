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

import com.project.bean.MaintainBean;
import com.project.service.IMaintainService;

@Controller
@RequestMapping("/maintain")
public class MaintainController {
	
	private final String regexTime = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	
	@Autowired
	private IMaintainService mser;
	
	
	/**
	 * 返回所有维护信息
	 * @return 维护信息集合
	 */
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public List<MaintainBean> findAllMaintain(){
		return mser.findAllMaintain();
	}
	
	
	/**
	 * 按条件查询维护信息
	 * @param bean 条件信息对象
	 * @param result 错误结果信息
	 * @return 返回结果
	 */
	@RequestMapping(value="/term",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findMaintainByTerm(@Validated MaintainBean bean,BindingResult result){
		Map<String,Object> map = new HashMap<>();
		//添加回显
		map.put("bean", bean);
		//==================手动校验
		int status=0;
		if(bean.getM_date()!=null && !bean.getM_date().matches(regexTime)){
			map.put("err_m_date", "请输入正确的日期");
			status=1;
		}
		//==================返回手动和自动校验的结果
		if(result.hasErrors() || status==1){
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
		List<MaintainBean> list = mser.findByTerm(bean);
		map.put("maintain", list);
		map.put("result", "查询成功");
		return map;
	}
	
	/**
	 * 添加维护信息
	 * @param bean	维护信息对象
	 * @param result 错误信息
	 * @return	返回结果
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMaintain(@Validated MaintainBean bean,BindingResult result){
		Map<String,Object> map = new HashMap<>();
		//添加回显信息
		map.put("bean", bean);
		//==================手动校验
		int status = 0;
		if(bean.getM_r_id()==0){
			map.put("err_m_r_id", "请输入正确的房间号");
			status = 1;
		}
		if(bean.getM_info()==null || bean.getM_info().equals("")){
			map.put("err_m_info", "请输入损坏描述");
			status = 1;
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
		//添加房间
		int num = mser.addMaintain(bean);
		if(num==0){
			map.put("result", "添加失败");
		}else{
			map.put("result", "添加成功");
		}
		return map;
	}
	
	
	/**
	 * 删除维护信息
	 * @param bean	维护信息对象
	 * @param result 错误信息
	 * @return	返回结果
	 */
	@RequestMapping(value="/remove",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,Object> removeMaintain(@Validated MaintainBean bean,BindingResult result){
		Map<String,Object> map = new HashMap<>();
		//初始化无效数据
		bean.setM_flage(1);
		bean.setM_info(null);
		bean.setM_r_id(0);
		bean.setM_result(0);
		//添加返回信息
		map.put("bean", bean);
		//==================手动校验
		int status = 0;
		if(bean.getM_id()==0){
			map.put("err_m_id", "请输入正确的维修id");
			status = 1;
		}
		//==================返回手动和自动校验的结果
		if(result.hasErrors() || status==1){
			//添加状态信息
			map.put("result", "删除失败");
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
		int num = mser.changeMaintain(bean);
		if(num==0){
			//添加状态信息
			map.put("result", "删除失败");
		}else{
			//添加状态信息
			map.put("result", "删除成功");
		}
		return map;
		
	}
	
	/**
	 * 更新维护信息
	 * @param bean	信息对象
	 * @param result 错误信息
	 * @return	返回结果
	 */
	@RequestMapping(value="/change",method=RequestMethod.PUT)
	@ResponseBody
	public Map<String,Object> changeMaintain(@Validated MaintainBean bean,BindingResult result){
		Map<String,Object> map = new HashMap<>();
		//初始化参数
		bean.setM_flage(0);
		//添加回显信息
		map.put("bean", bean);
		//==================手动校验
		int status=0;
		if(bean.getM_id()==0){
			map.put("err_m_id", "请输入正确的维护id");
			status=1;
		}
		//==================返回手动和自动校验的结果
		if(result.hasErrors() || status==1){
			//添加状态信息
			map.put("result", "更新失败");
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
		int num = mser.changeMaintain(bean);
		if(num==0){
			//添加状态信息
			map.put("result", "更新失败");
		}else{
			//添加状态信息
			map.put("result", "更新成功");
		}
		return map;
	}
	
	
	/**
	 * 查询分页维护信息
	 * @return 返回维护信息的集合
	 */
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findMaintainByPage(String s_page,String s_count){
		String result = "查询失败";
		Map<String,Object> map = new HashMap<>();
		List<MaintainBean> list= new ArrayList<>();
		int page = 0;
		int count = 0;
		try {
			page = Integer.parseInt(s_page);
			count = Integer.parseInt(s_count);
			list = mser.findMaintainByPage(page, count);
			result = "查询成功";
			map.put("result", result);
			map.put("maintain", list);
		} catch (Exception e) {
			result = "查询失败";
			map.put("result", result);
			map.put("maintain", list);
		}
		return map;
	}
	
	/**
	 * 获取总的页码数
	 * @param s_count
	 * @return
	 */
	@RequestMapping(value="/pagenum",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findMaintainByPage(String s_count){
		String result="获取失败";
		Map<String,Object> map = new HashMap<>();
		int count = 0;
		int pageNum = 0;
		try {
			count = Integer.parseInt(s_count);
			pageNum = mser.findPageNum(count);
			result="获取成功";
			map.put("result", result);
			map.put("pageNum", pageNum);
		} catch (Exception e) {
			result="获取失败";
			map.put("result", result);
			map.put("pageNum", pageNum);
		}
		return map;
	}
}
