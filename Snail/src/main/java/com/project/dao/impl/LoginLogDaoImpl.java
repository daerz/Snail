package com.project.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.LogBean;
import com.project.dao.ILoginLogDao;

@Component
public class LoginLogDaoImpl implements ILoginLogDao {

	@Autowired
	private SqlSessionFactory factory;
	
	@Override
	public List<LogBean> selectLog(int startNum,int pageSize) {
		SqlSession session = factory.openSession();
		ILoginLogDao dao = session.getMapper(ILoginLogDao.class);
		List<LogBean> lists = null;
		try {
			lists = dao.selectLog(startNum,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.commit();
			session.close();
		}
		return lists;
	}

	@Override
	public int insertLog(LogBean bean) {
		SqlSession session = factory.openSession();
		ILoginLogDao dao = session.getMapper(ILoginLogDao.class);
		int num = 0;
		try {
			num = dao.insertLog(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.commit();
			session.close();
		}
		return num;
	}

	@Override
	public int countLog() {
		SqlSession session = factory.openSession();
		ILoginLogDao dao = session.getMapper(ILoginLogDao.class);
		int num = 0;
		try {
			num = dao.countLog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.commit();
			session.close();
		}
		return num;
	}

}
