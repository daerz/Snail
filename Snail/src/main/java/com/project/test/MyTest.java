package com.project.test;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.project.bean.LogBean;
import com.project.dao.ILoginLogDao;
import com.project.dao.impl.LoginLogDaoImpl;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

public class MyTest {
	@Test
	public void test1() {
		GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io","BC-22d9da6f0d0146f98a7aebf2420c8c69");
		goEasy.publish("my_channel","1",new PublishListener() {
			@Override
			public void onSuccess() {
				System.out.println("Publish success");
			}
			@Override
			public void onFailed(GoEasyError error) {
				System.out.println("Error code:"+ error.getCode() +"; error content:"+error.getContent());
			}
		});
		
	}

}
