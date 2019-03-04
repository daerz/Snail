package com.project.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.RoomBean;
import com.project.dao.IRoomDao;

/**
 * 持久层
 * 房间业务实现类
 * @author tokiri
 *
 */
@Component
public  class RoomDaoImpl implements IRoomDao{
	
	@Autowired
	private SqlSessionFactory factory;

	@Override
	public List<RoomBean> selectAllRoom() {
		SqlSession session = factory.openSession();
		IRoomDao dao = session.getMapper(IRoomDao.class);
		List<RoomBean> list = null;
		try {
			list = dao.selectAllRoom();
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
	public List<RoomBean> selectByTerm(RoomBean bean) {
		SqlSession session = factory.openSession();
		IRoomDao dao = session.getMapper(IRoomDao.class);
		List<RoomBean> list = null;
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
	public int updateRoom(RoomBean bean) {
		SqlSession session = factory.openSession();
		IRoomDao dao = session.getMapper(IRoomDao.class);
		int num= 0;
		try {
			num = dao.updateRoom(bean);
		} catch (Exception e) {
			e.printStackTrace();
			num = 0;
		}finally{
			session.commit();
			session.close();
		}
		return num;
	}

	@Override
	public int insertRoom(RoomBean bean) {
		SqlSession session = factory.openSession();
		IRoomDao dao = session.getMapper(IRoomDao.class);
		int num=0;
		try {
			num=dao.insertRoom(bean);
		} catch (Exception e) {
			e.printStackTrace();
			num = 0;
		}finally{
			session.commit();
			session.close();
		}
		return num;
	}


	@Override
	public List<RoomBean> selectRoomByPage(int page, int count) {
		SqlSession session = factory.openSession();
		IRoomDao dao = session.getMapper(IRoomDao.class);
		List<RoomBean> list = null;
		try {
			list = dao.selectRoomByPage(page, count);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.commit();
			session.close();
		}
		return list;
	}


	
}
