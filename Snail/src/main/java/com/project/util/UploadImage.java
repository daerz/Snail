package com.project.util;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用于图片(文件)上传
 * @author 大耳贼
 *
 */
public class UploadImage {

	public static String handleImage(MultipartFile imageFile,HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/upload/images/");
		String oldName = null;
		String newName = null;
		if(imageFile.getOriginalFilename().equals("") || !imageFile.getOriginalFilename().matches(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$")) {
			newName = "defaultImage.jpg";
			return path = "/Snail/upload/images/" + newName;
		}else{
			oldName = imageFile.getOriginalFilename();
			newName = changeName(oldName);
		}
		String imagePath = path + newName;
		String changePath = "/Snail/upload/images/" + newName;
		File desFile = new File(imagePath);
		if(!desFile.getParentFile().exists()) {
			desFile.mkdirs();
		}
		try {
			imageFile.transferTo(desFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} 
		return changePath;
	}
	
	public static String changeName(String oldName) {
		String last = oldName.substring(oldName.lastIndexOf(".") + 1);
		String newName = UUID.randomUUID().toString()+"_"+new Date().getTime()+"."+last;
		
		return newName;
	}
}
