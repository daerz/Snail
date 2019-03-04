package com.project.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.project.bean.OrderBean;
import com.project.dao.IOrderDao;

public class TestLLL {
	
	@Test
	public void test(){
		ApplicationContext ap = new ClassPathXmlApplicationContext("applicationContext.xml");
		IOrderDao dao = ap.getBean(IOrderDao.class);
		int currentPage =1;
		OrderBean orderBean = new OrderBean();
		//orderBean.setO_id("1");
		//int totalPage = dao.selectTimeALLPage(orderBean,currentPage);
		List<OrderBean> totalPage = dao.findOrderAllAndAll(orderBean);
		System.out.println(totalPage);
		
	}

}
