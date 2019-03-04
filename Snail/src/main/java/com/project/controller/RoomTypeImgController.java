package com.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.bean.RoomTypeImgBean;
import com.project.service.IRoomTypeImgService;
import com.project.util.UploadImage;
import com.project.util.ValidationUtil;

/**
 * 控制层
 * 房型图片信息业务
 * @author 大耳贼
 *
 */
@Controller
@RequestMapping("/roomTypeImg")
public class RoomTypeImgController {

	@Autowired
	private IRoomTypeImgService service;
	
	/**
	 * 根据房型id查询其旗下图片
	 * @param rt_id 房型id
	 * @return 返回对应房型图片集合
	 */
	@RequestMapping(value="/source",method=RequestMethod.GET)
	@ResponseBody
	public List<RoomTypeImgBean> findImgByRtid(String rt_id){
		int id = ValidationUtil.stringToInt(rt_id);
		List<RoomTypeImgBean> list = service.findImgByRtid(id);		
		return list;
	}
	
	/**
	 * 添加图片信息
	 * @param bean 信息对象
	 * @return 返回是否添加成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImg(String rti_rt_id, String rti_note ,MultipartFile imageFile, HttpServletRequest request){
		RoomTypeImgBean bean = new RoomTypeImgBean();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "请输入正确格式内容！");
		int id = ValidationUtil.stringToInt(rti_rt_id);
		if(id == 0){
			map.put("result", "请输入正确格式内容！");
			return map;
		}
		if(rti_note.length() > 20){
			map.put("result", "备注内容不得超过20字！");
			return map;
		}
		String imgPath = "/Snail/upload/defaultImages/defaultImage.jpg";
		if(imageFile == null){
			map.put("result", "请添加正确图片格式！");
			return map;
		}else if(imageFile.getOriginalFilename().equals("")) {
			imgPath = "/Snail/upload/defaultImages/defaultImage.jpg";
		}else if(!imageFile.getOriginalFilename().matches(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$")){
			map.put("result", "请添加正确图片格式！");
			return map;
		}else{
			imgPath = UploadImage.handleImage(imageFile, request);
		}
		bean.setRti_path(imgPath);
		bean.setRti_rt_id(id);
		bean.setRti_note(rti_note);

		boolean boo = service.addImg(bean);		
		if(boo)
			map.put("result", "添加成功！");
		return map;
	}
	
	/**
	 * 修改图片信息
	 * @param bean 信息对象
	 * @return 返回是否修改成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.PUT,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String changeImg(String rti_id, String rti_rt_id, String rti_path, String rti_note){
		String result = "修改失败！";
		int id_rt_rti = -1;
		if(rti_rt_id == null){
			id_rt_rti = 0;
		}else{
			try {
				id_rt_rti = Integer.parseInt(rti_rt_id);
			} catch (Exception e) {
				return result;
			}
		}
		int id = ValidationUtil.stringToInt(rti_id);
		if(id == 0 || id_rt_rti == -1)
			return result;
		if(rti_note != null){
			if(rti_note.length() > 100)
				return result;
		}
		if(rti_path != null){
			if(!rti_path.matches(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$") || rti_path.length() > 100)
				return result;
		}
		RoomTypeImgBean bean = new RoomTypeImgBean();
		bean.setRti_id(id);
		bean.setRti_rt_id(id_rt_rti);
		bean.setRti_note(rti_note);
		bean.setRti_path(rti_path);
		boolean boo = service.changeImg(bean);	
		if(boo)
			result = "修改成功！";
		return result;
	}
		
	/**
	 * 软删除指定图片id
	 * @param rti_id 图片id
	 * @return 是否删除成功
	 */
	@RequestMapping(value="/source",method=RequestMethod.DELETE,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteImgById(String rti_id){
		String result = "删除失败！";
		int id = ValidationUtil.stringToInt(rti_id);
		if(id == 0)
			return result;
		boolean boo = service.deleteImgById(id);
		if(boo)
			result = "删除成功！";
		return result;
	}
}
