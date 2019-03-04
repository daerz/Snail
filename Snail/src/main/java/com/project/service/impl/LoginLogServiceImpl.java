package com.project.service.impl;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bean.LogBean;
import com.project.dao.ILoginLogDao;
import com.project.service.ILoginLogService;
import com.project.tools.IpTool;

@Service
public class LoginLogServiceImpl implements ILoginLogService {

	@Autowired
	private ILoginLogDao dao;
	
	@Override
	public List<LogBean> findLog(int pageNum,int pageSize) {
		int startNum = (pageNum-1)*12;
		List<LogBean> lists = dao.selectLog(startNum,pageSize);
		return lists;
	}

	@Override
	public int addLog(String name,String cname,String cip) {
		LogBean bean = new LogBean();
		bean.setL_name(name);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bean.setL_time(sdf.format(new Date()));
		bean.setL_addres(cname);
		bean.setL_ip(cip);
		return dao.insertLog(bean);
	}

	@Override
	public int countLog() {
		return dao.countLog();
	}

}
