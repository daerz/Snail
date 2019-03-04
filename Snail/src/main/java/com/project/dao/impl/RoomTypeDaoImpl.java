package com.project.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.RoomTypeBean;
import com.project.dao.IRoomTypeDao;

/**
 * 持久层
 * 房型持久层接口实现
 * @author 大耳贼
 *
 */
@Component
public class RoomTypeDaoImpl implements IRoomTypeDao{

	@Autowired
	private SqlSessionFactory factory;
	@Override
	public List<RoomTypeBean> selectAllRoomType() {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		List<RoomTypeBean> list = null;
		try {
			list = dao.selectAllRoomType();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}
	

	@Override
	public List<RoomTypeBean> selectRoomTypeByNum(int rt_num) {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		List<RoomTypeBean> list = null;
		try {
			list = dao.selectRoomTypeByNum(rt_num);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public List<RoomTypeBean> selectAllRoomTypeBackStage() {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		List<RoomTypeBean> list = null;
		try {
			list = dao.selectAllRoomTypeBackStage();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public RoomTypeBean selectRoomTypeById(int rt_id) {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		RoomTypeBean bean = null;
		try {
			bean = dao.selectRoomTypeById(rt_id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return bean;
	}

	@Override
	public int insertRoomType(RoomTypeBean bean) {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		int row = -1;
		try {
			row = dao.insertRoomType(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public int updateRoomType(RoomTypeBean bean) {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		int row = -1;
		try {
			row = dao.updateRoomType(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public int deleteRoomTypeById(int rt_id) {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		int row = -1;
		try {
			row = dao.deleteRoomTypeById(rt_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}


	@Override
	public List<RoomTypeBean> selectRoomTypeUseIndex() {
		SqlSession session = factory.openSession();
		IRoomTypeDao dao = session.getMapper(IRoomTypeDao.class);
		List<RoomTypeBean> list = null;
		try {
			list = dao.selectRoomTypeUseIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}


}
