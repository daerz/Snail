package com.project.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.RoomTypeImgBean;
import com.project.dao.IRoomTypeImgDao;

/**
 * 持久层
 * 房型图片持久层接口实现
 * @author 大耳贼
 *
 */
@Component
public class RoomTypeImgDaoImpl implements IRoomTypeImgDao{
	
	@Autowired
	private SqlSessionFactory factory;
	
	@Override
	public List<RoomTypeImgBean> selectAllImg() {
		SqlSession session = factory.openSession();
		List<RoomTypeImgBean> list = null;
		try {
			IRoomTypeImgDao dao = session.getMapper(IRoomTypeImgDao.class);
			list = dao.selectAllImg();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public List<RoomTypeImgBean> selectImgByRtidBackStage(int rt_id) {
		SqlSession session = factory.openSession();
		List<RoomTypeImgBean> list = null;
		try {
			IRoomTypeImgDao dao = session.getMapper(IRoomTypeImgDao.class);
			list = dao.selectImgByRtidBackStage(rt_id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public List<RoomTypeImgBean> selectImgByRtid(int rt_id) {
		SqlSession session = factory.openSession();
		List<RoomTypeImgBean> list = null;
		try {
			IRoomTypeImgDao dao = session.getMapper(IRoomTypeImgDao.class);
			list = dao.selectImgByRtid(rt_id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public int deleteImgByRtid(int rt_id) {
		SqlSession session = factory.openSession();
		int row = -1;
		try {
			IRoomTypeImgDao dao = session.getMapper(IRoomTypeImgDao.class);
			row = dao.deleteImgByRtid(rt_id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return row;
	}

	@Override
	public int deleteImgById(int id) {
		SqlSession session = factory.openSession();
		int row = -1;
		try {
			IRoomTypeImgDao dao = session.getMapper(IRoomTypeImgDao.class);
			row = dao.deleteImgById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return row;
	}

	@Override
	public int insertImg(RoomTypeImgBean bean) {
		SqlSession session = factory.openSession();
		int row = -1;
		try {
			IRoomTypeImgDao dao = session.getMapper(IRoomTypeImgDao.class);
			row = dao.insertImg(bean);
		} catch (Exception e) {
			return row;
		}finally{
			session.close();
		}
		return row;
	}

	@Override
	public int updateRoomTypeImg(RoomTypeImgBean bean) {
		SqlSession session = factory.openSession();
		int row = -1;
		try {
			IRoomTypeImgDao dao = session.getMapper(IRoomTypeImgDao.class);
			row = dao.updateRoomTypeImg(bean);
		} catch (Exception e) {
			return row;
		}finally{
			session.close();
		}
		return row;
	}

}
