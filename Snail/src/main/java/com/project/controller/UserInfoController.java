package com.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
		@RequestMapping(value="/getUserInfoByShiro",method=RequestMethod.GET)
		@ResponseBody
		public Map<String , Object> getUserInfoByShiro(){
			Map<String , Object> map=new HashMap<String , Object>();
			System.out.println("进入UserInfo接口");
			try {
				
				Subject sub=SecurityUtils.getSubject();
				Session session=sub.getSession();

				map.put("userPhone", session.getAttribute("phone"));
				map.put("discount", session.getAttribute("discount"));
				map.put("vip", session.getAttribute("vip"));
			} catch (InvalidSessionException e) {
				map.put("err_userInfo", "信息错误");
				e.printStackTrace();
			}
			return map;
			
		}
}
