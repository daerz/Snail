package com.project.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.RoomStaticInfoBean;
import com.project.dao.IRoomStaticInfoDao;

/**
 * 持久层
 * 房型静态信息持久层接口实现
 * @author 大耳贼
 */
@Component
public class RoomStaticInfoDaoImpl implements IRoomStaticInfoDao{

	@Autowired
	private SqlSessionFactory factory;
	
	@Override
	public List<RoomStaticInfoBean> selectAllRoomStaticInfo() {
		SqlSession session = factory.openSession();
		List<RoomStaticInfoBean> list = null;
		try {
			IRoomStaticInfoDao dao = session.getMapper(IRoomStaticInfoDao.class);
			list = dao.selectAllRoomStaticInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public List<RoomStaticInfoBean> selectRoomStaticInfoByRtid(int rt_id) {
		SqlSession session = factory.openSession();
		List<RoomStaticInfoBean> list = new ArrayList<RoomStaticInfoBean>();
		try {
			IRoomStaticInfoDao dao = session.getMapper(IRoomStaticInfoDao.class);
			list = dao.selectRoomStaticInfoByRtid(rt_id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public List<RoomStaticInfoBean> selectRoomStaticInfoByRtidBackStage(int rt_id) {
		SqlSession session = factory.openSession();
		List<RoomStaticInfoBean> list = null;
		try {
			IRoomStaticInfoDao dao = session.getMapper(IRoomStaticInfoDao.class);
			list = dao.selectRoomStaticInfoByRtidBackStage(rt_id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public int updateRoomStaticInfo(RoomStaticInfoBean bean) {
		SqlSession session = factory.openSession();
		int row = -1;
		try {
			IRoomStaticInfoDao dao = session.getMapper(IRoomStaticInfoDao.class);
			row = dao.updateRoomStaticInfo(bean);
		} catch (Exception e) {
			return row;
		}finally{
			session.close();
		}
		return row;
	}

	@Override
	public int deleteRoomStaticInfo(int si_id) {
		SqlSession session = factory.openSession();
		int row = -1;
		try {
			IRoomStaticInfoDao dao = session.getMapper(IRoomStaticInfoDao.class);
			row = dao.deleteRoomStaticInfo(si_id);
		} catch (Exception e) {
			return row;
		}finally{
			session.close();
		}
		return row;
	}

	@Override
	public int insertRoomStaticInfo(RoomStaticInfoBean bean) {
		SqlSession session = factory.openSession();
		int row = -1;
		try {
			IRoomStaticInfoDao dao = session.getMapper(IRoomStaticInfoDao.class);
			row = dao.insertRoomStaticInfo(bean);
		} catch (Exception e) {
			return row;
		}finally{
			session.close();
		}
		return row;
	}

}
