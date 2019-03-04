package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bean.LogBean;
import com.project.service.ILoginLogService;

@Controller
public class LoginLogController {
	
	@Autowired
	private ILoginLogService service;
	
	@RequestMapping(value="/queryLog",method=RequestMethod.GET)
	@ResponseBody
	public List<LogBean> queryLog(String pageNum,String pageSize){
		int pageN;
		int pageS;
		try {
			pageN = Integer.parseInt(pageNum);
			pageS = Integer.parseInt(pageSize);
			if(pageN<=1) {
				pageN = 1;
			}
			if(pageN>=99) {
				pageN = 99;
			}
			if(pageS<=1) {
				pageS = 1;
			}
			if(pageS>=15) {
				pageS = 15;
			}
		} catch (Exception e) {
			pageN = 1;
			pageS = 15;
		}
		return service.findLog(pageN, pageS);
	}
	
	@RequestMapping(value="/countLog",method=RequestMethod.GET)
	@ResponseBody
	public int countLog() {
		return service.countLog();
	}
	
	@RequestMapping(value="/addLog",method=RequestMethod.POST)
	@ResponseBody
	public int addLog(String m_name , String cname ,String cip) {
		service.addLog(m_name , cname , cip);
		return 0;
	}
}
