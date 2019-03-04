package com.project.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.bean.RoomBean;
import com.project.bean.RoomStaticInfoBean;
import com.project.bean.RoomTypeBean;
import com.project.bean.RoomTypeImgBean;
import com.project.dao.IRoomStaticInfoDao;
import com.project.dao.IRoomTypeDao;
import com.project.dao.IRoomTypeImgDao;
import com.project.service.IRoomService;
import com.project.service.IRoomTypeService;

/**
 * 业务层
 * 房型业务接口实现
 * @author 大耳贼
 *
 */
@Service
public class RoomTypeServiceImpl implements IRoomTypeService{

	@Autowired
	private IRoomTypeDao rtDao;
	@Autowired
	private IRoomStaticInfoDao rsiDao;
	@Autowired
	private IRoomTypeImgDao rtiDao;
	@Autowired
	private IRoomService rService;


	@Override
	public List<Object> findRoomTypeByTermAndTime(RoomBean bean, int rt_num, String beginDay, String endDay) {
		List<Object> list = new ArrayList<Object>();
		List<RoomTypeBean> rtList = null;
		if(rt_num == 0){
			rtList = rtDao.selectAllRoomType();
		}else{
			rtList = rtDao.selectRoomTypeByNum(rt_num);
		}
		for (RoomTypeBean rtBean : rtList) {
			List<Object> innerList = new ArrayList<Object>();
			int rt_id = rtBean.getRt_id();
			bean.setR_rt_id(rt_id);
			List<List<String>> rList = rService.findByTermAndTime(bean, beginDay, endDay);
			List<RoomStaticInfoBean> rsiList = rsiDao.selectRoomStaticInfoByRtid(rt_id);
			List<RoomTypeImgBean> rtiBean = rtiDao.selectImgByRtid(rt_id);
			if(rList.size() != 0 && rsiList.size() != 0 && rtiBean.size() != 0){				
				innerList.add(rtBean);
				innerList.add(rsiList);
				innerList.add(rtiBean);
				innerList.add(rList);
				Collections.sort(rList,new Comparator<List<String>>(){

					@Override
					public int compare(List<String> o1, List<String> o2) {
						int num = o1.get(0).hashCode() - o2.get(0).hashCode();
						return num;
					}
					
				});
				list.add(innerList);	
			}		
		}
		return list;
	}
	
	
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean addRoomType(RoomTypeBean bean) {
		boolean boo = false;
		int row = rtDao.insertRoomType(bean);
		if(row > 0)
			boo = true;
		return boo;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public boolean changeRoomType(RoomTypeBean bean) {
		boolean boo = false;
		int row = rtDao.updateRoomType(bean);
		if(row > 0)
			boo = true;
		return boo;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public String deleteRoomType(int rt_id) {
		String result = "删除失败！";
		RoomBean bean = new RoomBean();
		bean.setR_rt_id(rt_id);
		List<RoomBean> list = rService.findByTerm(bean);
		if(list.size() > 0){
			result = "房型内还有房间，无法删除！";
			return result;
		}
		int row = rtDao.deleteRoomTypeById(rt_id);
		if(row > 0)
			result = "删除成功！";
		return result;
	}


	@Override
	public List<RoomTypeBean> findAllRoomType() {
		List<RoomTypeBean> list = new ArrayList<RoomTypeBean>();
	    list = rtDao.selectAllRoomTypeBackStage();
		return list;
	}


	@Override
	public List<Object> findRoomTypeByRtid(int rt_id, int countId) {
		List<Object> list = new ArrayList<Object>();
		RoomTypeBean roomType = rtDao.selectRoomTypeById(rt_id);
		RoomBean bean = new RoomBean();
		bean.setR_id(countId);
		List<RoomBean> rList = rService.findByTerm(bean);
		List<RoomTypeImgBean> iList = rtiDao.selectImgByRtid(rt_id);
		List<RoomStaticInfoBean> sList = rsiDao.selectRoomStaticInfoByRtid(rt_id);
		if(rList.size() != 0 && iList.size() != 0 && sList.size() != 0){
			list.add(roomType);
			list.add(iList);
			list.add(sList);
			list.add(rList);	
		}else{
			list.add("页面走丢了");
		}
		
		return list;
	}


	@Override
	public List<RoomTypeBean> findRoomTypeUseIndex() {
	    List<RoomTypeBean> list = rtDao.selectRoomTypeUseIndex();
		return list;
	}


	@Override
	public List<Object> findRoomTypeByCart(int rt_id, int countId) {
		List<Object> list = new ArrayList<Object>();
		RoomTypeBean roomType = rtDao.selectRoomTypeById(rt_id);
		RoomBean bean = new RoomBean();
		bean.setR_id(countId);
		List<RoomBean> rList = rService.findByTerm(bean);
		if(rList != null){
			list.add(roomType);
			list.add(rList);	
		}else{
			list.add("页面走丢了");
		}
		return list;
	}




}
