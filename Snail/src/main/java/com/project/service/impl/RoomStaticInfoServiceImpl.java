package com.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.bean.RoomStaticInfoBean;
import com.project.dao.IRoomStaticInfoDao;
import com.project.service.IRoomStaticInfoService;

/**
 * 业务层
 * 房型静态信息业务接口实现
 * @author 大耳贼
 *
 */
@Service
public class RoomStaticInfoServiceImpl implements IRoomStaticInfoService{

	@Autowired
	private IRoomStaticInfoDao dao;
	
	@Override
	public List<RoomStaticInfoBean> findAllStatic() {
		List<RoomStaticInfoBean> list = new ArrayList<RoomStaticInfoBean>();
		list = dao.selectAllRoomStaticInfo();
		return list;
	}

	@Override
	public List<RoomStaticInfoBean> findStaticByRtid(int rt_id) {
		List<RoomStaticInfoBean> list = dao.selectRoomStaticInfoByRtid(rt_id);
		return list;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean addStatic(RoomStaticInfoBean bean) {
		boolean boo = false;
		int row = dao.insertRoomStaticInfo(bean);
		if(row > 0)
			boo = true;
		return boo;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean changeStatic(RoomStaticInfoBean bean) {
		boolean boo = false;
		int row = dao.updateRoomStaticInfo(bean);
		if(row > 0)
			boo = true;
		return boo;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean deleteStaticBySiid(int si_id) {
		boolean boo = false;
		int row = dao.deleteRoomStaticInfo(si_id);
		if(row > 0)
			boo = true;
		return boo;
	}

}
