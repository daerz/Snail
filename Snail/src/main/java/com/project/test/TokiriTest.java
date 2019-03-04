package com.project.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.project.bean.MaintainBean;
import com.project.bean.ORParamBean;
import com.project.bean.RoomBean;
import com.project.dao.IMaintainDAO;
import com.project.dao.IRoomDao;
import com.project.service.IMaintainService;
import com.project.service.IRoomService;
import com.project.service.RedisService;
import com.project.util.GetTimeByEveryDay;
import com.project.util.RedisUtil;

public class TokiriTest {
	
	@Test
	public void test(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		IMaintainService ser = context.getBean(IMaintainService.class);
//		MaintainBean bean = new MaintainBean();
//		bean.setM_r_id(1);
//		bean.setM_info("李四睿");
//		System.out.println(ser.addMaintain(bean));
		
//		IRoomService ser = context.getBean(IRoomService.class);
//		ORParamBean bean = new ORParamBean();
//		bean.setIdCount("1,2,3");
//		ser.allotRoom(bean);
//		
//		RedisUtil redis = context.getBean(RedisUtil.class);
//		redis.sSet("303",234);
//		System.out.println(redis.sGetSetSize("303"));
//		System.out.println(redis.sHasKey("303", "fsda"));
//		redis.setRemove("303", "fsda");
//		System.out.println(redis.sGet("303"));
		
		RedisService re = context.getBean(RedisService.class);
//		re.saveToRedis("303","2018-01-01", "2018-01-02");
//		re.delTimeToRedis("303","2018-01-01", "2018-01-2");
		System.out.println(re.findTimeExist("304","2018-01-02", "2018-01-05"));
		
//		System.out.println(GetTimeByEveryDay.getByTimeStamp("2018-01-12", "2018-01-13"));
		
	}
	
	
//	@Test
//	public void test1(){
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		IMaintainDAO dao = context.getBean(IMaintainDAO.class);
//		MaintainBean bean = new MaintainBean();
//		bean.setM_info("nishi");
//		bean.setM_result(0);
//		bean.setM_flage(1);
//		bean.setM_id(1);
//		dao.updateMaintain(bean);
//	}
	
}
