package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.bean.RoomTypeImgBean;
import com.project.dao.IRoomTypeImgDao;
import com.project.service.IRoomTypeImgService;

/**
 * 业务接口
 * 房型图片业务接口实现
 * @author 大耳贼
 *
 */
@Service
public class RoomTypeImgServiceImpl implements IRoomTypeImgService{

	@Autowired
	private IRoomTypeImgDao dao;
	
	@Override
	public List<RoomTypeImgBean> findAllImg() {
		List<RoomTypeImgBean> list = dao.selectAllImg();
		return list;
	}

	@Override
	public List<RoomTypeImgBean> findImgByRtid(int rt_id) {
		List<RoomTypeImgBean> list = dao.selectImgByRtidBackStage(rt_id);
		return list;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean deleteImgByRtid(int rt_id) {
		boolean boo = false;
		int row = dao.deleteImgByRtid(rt_id);
		if(row > 0)
			boo = true;
		return boo;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean deleteImgById(int id) {
		boolean boo = false;
		int row = dao.deleteImgById(id);
		if(row > 0)
			boo = true;
		return boo;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean changeImg(RoomTypeImgBean bean) {
		boolean boo = false;
		int row = dao.updateRoomTypeImg(bean);
		if(row > 0)
			boo = true;
		return boo;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean addImg(RoomTypeImgBean bean) {
		boolean boo = false;
		int	row = dao.insertImg(bean);
		if(row > 0)
			boo = true;
		return boo;
	}

}
