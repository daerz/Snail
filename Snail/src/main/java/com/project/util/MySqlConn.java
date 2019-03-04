package com.project.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlConn {
	
	private final static String path = "mybatis.xml";
	private static SqlSessionFactory fa = null;
	
	public static SqlSession getSession(){
		
		InputStream in = null;
		if(fa==null){
			try {
				in = Resources.getResourceAsStream(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fa = new SqlSessionFactoryBuilder().build(in);
		}
		
		return fa.openSession();
		
	}

}