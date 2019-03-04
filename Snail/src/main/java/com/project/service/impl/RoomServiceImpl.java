package com.project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.bean.ORParamBean;
import com.project.bean.RoomBean;
import com.project.bean.RoomTypeBean;
import com.project.dao.IRoomDao;
import com.project.dao.IRoomTypeDao;
import com.project.service.IRoomService;
import com.project.service.RedisService;
/**
 * 业务层
 * 房间业务实现类
 * @author Administrator
 *
 */
@Service
public class RoomServiceImpl implements IRoomService {
	
	private final String regexTime = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	
	@Autowired
	private IRoomDao rdao;
	
	@Autowired
	private IRoomTypeDao rtdao;
	
	@Autowired
	private RedisService redis;

	@Override
	public List<RoomBean> findAllRoom() {
		// TODO Auto-generated method stub
		return rdao.selectAllRoom();
	}

	@Override
	public List<RoomBean> findByTerm(RoomBean bean) {
		// TODO Auto-generated method stub
		return rdao.selectByTerm(bean);
	}
	
	
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public int addRoom(RoomBean bean) {
		RoomBean term = new RoomBean();
		term.setR_num(bean.getR_num());
		//查询添加的房间号是否存在
		int num = 0;
		if(rdao.selectByTerm(term).size()==0){
			//判断房型是否存在
			RoomTypeBean rtb = null;
			int rtstatus = 0;
			if(bean.getR_rt_id()>0){
				rtb = rtdao.selectRoomTypeById(bean.getR_rt_id());
				rtstatus = 1;
			}
			//添加房间
			if(rtstatus==0 || rtb != null){
				num = rdao.insertRoom(bean);
			}
			
		}
		return num;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public int changeRoom(RoomBean bean) {
		RoomBean term = new RoomBean();
		term.setR_id(bean.getR_id());
		//查询房间是否存在
		int num = rdao.selectByTerm(term).size();
		if(num==1){
			//判断更新的房间是否存在
			term.setR_id(0);
			term.setR_num(bean.getR_num());
			num=0;
			if(bean.getR_num()!=0 && rdao.selectByTerm(term).size()>0){
			}else{
				int rtstatus = 0;
				RoomTypeBean rtb = null;
				//判断房型是否存在
				if(bean.getR_rt_id()>0){
					rtb = rtdao.selectRoomTypeById(bean.getR_rt_id());
					rtstatus = 1;
				}
				//存在则更新房间信息
				if(rtstatus == 0 || rtb != null){
					num = rdao.updateRoom(bean);
				}
			}
		}
		return num;
	}

	@Override
	public List<List<String>> findByTermAndTime(RoomBean bean, String beginDay, String endDay) {
		//去除房间和房号信息
		bean.setR_id(0);
		bean.setR_num(0);
		//设置无设施损坏的房间
		bean.setR_state(1);
		//存放返回的信息
		List<List<String>> obj = new ArrayList<>();
		//查询满足条件的房间
		List<RoomBean> list = findByTerm(bean);
		//判断该房是否可住,移除不可入住的房间
		for(int i = list.size()-1 ; i>=0 ;i--){
			if(!redis.findTimeExist(String.valueOf(list.get(i).getR_id()), beginDay, endDay)){
				//不可住则移除
				list.remove(i);
			}
		}
		//拼接格式
		for (RoomBean rb : list) {
			int j = -1 ;
			for( int i = 0 ; i < obj.size() ; i++){
				if(Integer.parseInt(obj.get(i).get(0))==(int)(rb.getR_price()*rb.getR_discount())){
					j = i;
					break;
				}
			}
			if(j>=0){
				obj.get(j).set(1, String.valueOf(Integer.parseInt(obj.get(j).get(1))+1));
				obj.get(j).set(2, obj.get(j).get(2)+","+String.valueOf(rb.getR_id()));
			}else{
				List<String> info = new ArrayList<>();
				//添加价格
				info.add(String.valueOf((int)(rb.getR_price()*rb.getR_discount())));
				//添加数量
				info.add("1");
				//添加房间字符串
				info.add(String.valueOf(rb.getR_id()));
				obj.add(info);
			}
		}
		
		return obj;
	}

	@Override
	public List<RoomBean> allotRoom(ORParamBean bean) {
		//存放被选定的房间
		List<RoomBean> rooms = new ArrayList<>();
		//存放房间的条件信息
		RoomBean term = new RoomBean();
		//存放分配的房间数量
		int num = 0;
		//获取房间字符串
		String allRomm = bean.getIdCount();
		//分割字符串
		String[] roomList = allRomm.split(",");
		//再次遍历校验房间是否可以入住
		for (String i : roomList) {
			if(redis.findTimeExist(i, bean.getBeginTime(), bean.getEndTime())){
				//该房间可以入住
				try {
					int r_id = Integer.parseInt(i);
					//添加房间id检索信息
					term.setR_id(r_id);
					//获得房间对象
					List<RoomBean> rbean = rdao.selectByTerm(term);
					if(rbean.size()!=0){
						rooms.add(rdao.selectByTerm(term).get(0));
						num+=1;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					continue;
				}finally {
					//分配完房间结束分配
					if(num>=bean.getNum()){
						break;
					}
				}
			}
		}
		System.out.println(roomList[0]);
		return rooms;
	}

	@Override
	public List<Map<String, Object>> findRoomGroup() {
		//存放数据
		List<Map<String, Object>> list = new ArrayList<>();
		//查询所有房间类型
		List<RoomTypeBean> roomType = rtdao.selectAllRoomType();
		//存储房间条件
		RoomBean bean = new RoomBean();
		for (RoomTypeBean roomTypeBean : roomType) {
			Map<String, Object> map = new HashMap<>();
			//设置房型条件
			bean.setR_rt_id(roomTypeBean.getRt_id());
			//查询得到房型数量
			int num = rdao.selectByTerm(bean).size();
			//添加信息
			map.put("value", num);
			map.put("name", roomTypeBean.getRt_name());
			list.add(map);
		}
		return list;
	}

	@Override
	public List<RoomBean> findAllByTermAndTime(RoomBean bean, String beginDay, String endDay) {
		//查询满足条件的房间
		List<RoomBean> list = findByTerm(bean);
		//是否通过时间筛选
		if(beginDay!=null && endDay!=null && beginDay!="" && endDay!=""){
			if(beginDay.matches(regexTime) && endDay.matches(regexTime)){
				//判断该房是否可住,移除不可入住的房间
				for(int i = list.size()-1 ; i>=0 ;i--){
					if(!redis.findTimeExist(String.valueOf(list.get(i).getR_id()), beginDay, endDay)){
						//不可住则移除
						list.remove(i);
					}
				}
			}
		}
		return list;
	}

	@Override
	public Map<String,Object> findRoomByPage(int page, int count) {
		 Map<String,Object> map = new HashMap<>();
		List<RoomBean> room =  rdao.selectRoomByPage((page-1)*count, count);
		int num = rdao.selectAllRoom().size();
		map.put("room", room);
		map.put("number", num);
		return map;
	}

}
