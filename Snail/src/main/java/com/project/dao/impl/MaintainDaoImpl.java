package com.project.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.MaintainBean;
import com.project.dao.IMaintainDAO;


@Component
public class MaintainDaoImpl implements IMaintainDAO{
	
	@Autowired
	private SqlSessionFactory factory;
	
	@Override
	public List<MaintainBean> selectAllMaintain() {
		SqlSession session = factory.openSession();
		IMaintainDAO dao = session.getMapper(IMaintainDAO.class);
		List<MaintainBean> list = null;
		try {
			list = dao.selectAllMaintain();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			session.commit();
			session.close();
		}
		return list;
	}

	@Override
	public List<MaintainBean> selectByTerm(MaintainBean bean) {
		SqlSession session = factory.openSession();
		IMaintainDAO dao = session.getMapper(IMaintainDAO.class);
		List<MaintainBean> list = null;
		try {
			list = dao.selectByTerm(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			session.commit();
			session.close();
		}
		return list;
	}

	@Override
	public int insertMaintain(MaintainBean bean) {
		SqlSession session = factory.openSession();
		IMaintainDAO dao = session.getMapper(IMaintainDAO.class);
		int num = 0;
		try {
			num = dao.insertMaintain(bean);
		} catch (Exception e) {
			// TODO: handle exception
			num = 0;
		}finally{
			session.commit();
			session.close();
		}
		return num;
	}

	@Override
	public int updateMaintain(MaintainBean bean) {
		SqlSession session = factory.openSession();
		IMaintainDAO dao = session.getMapper(IMaintainDAO.class);
		int num = 0;
		try {
			num = dao.updateMaintain(bean);
		} catch (Exception e) {
			// TODO: handle exception
			num = 0;
		}finally{
			session.commit();
			session.close();
		}
		return num;
	}

	@Override
	public List<MaintainBean> selectMaintainByPage(int page, int count) {
		SqlSession session = factory.openSession();
		IMaintainDAO dao = session.getMapper(IMaintainDAO.class);
		List<MaintainBean> list = null;
		try {
			list = dao.selectMaintainByPage(page, count);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			session.commit();
			session.close();
		}
		return list;
	}

}
