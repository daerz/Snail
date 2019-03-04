package com.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.bean.MaintainBean;
import com.project.bean.RoomBean;
import com.project.dao.IMaintainDAO;
import com.project.dao.IRoomDao;
import com.project.service.IMaintainService;

@Service
public class MaintainServiceImpl implements IMaintainService{
	
	@Autowired
	private IMaintainDAO mdao;
	
	@Autowired
	private IRoomDao rdao;
	
	@Override
	public List<MaintainBean> findAllMaintain() {
		// TODO Auto-generated method stub
		return mdao.selectAllMaintain();
	}

	@Override
	public List<MaintainBean> findByTerm(MaintainBean bean) {
		// TODO Auto-generated method stub
		return mdao.selectByTerm(bean);
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public int addMaintain(MaintainBean bean) {
		RoomBean term = new RoomBean();
		term.setR_id(bean.getM_r_id());
		//查询房间是否存在
		int num=rdao.selectByTerm(term).size();
		if(num==1 && bean.getM_info()!="" && bean.getM_info()!=null){
			//当房间存在，且必须有损坏描述才能添加
			num = mdao.insertMaintain(bean);
			//更改房间状态为不可入住
			term.setR_state(2);
			rdao.updateRoom(term);
		}
		return num;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	public int changeMaintain(MaintainBean bean) {
		RoomBean term = new RoomBean();
		term.setR_id(bean.getM_r_id());
		MaintainBean mterm = new MaintainBean();
		mterm.setM_id(bean.getM_id());
		int number = 0;
		//查询维修是否存在
		if(mdao.selectByTerm(mterm).size()!=0){
			//判断房间是否存在
			if(bean.getM_r_id() ==0 || rdao.selectByTerm(term).size()!=0){
				number = mdao.updateMaintain(bean);
				//确定维护的房间
				int rid = mdao.selectByTerm(mterm).get(0).getM_r_id();
				if(bean.getM_result()==2){
					//查找该房间是否有未维护玩的信息
					bean.setM_id(0);
					bean.setM_r_id(rid);
					bean.setM_result(1);
					int count = mdao.selectByTerm(bean).size();
					if(count==0){
						//如果所有维护都维护完毕房间可以入住
						term.setR_id(rid);
						term.setR_state(1);
						rdao.updateRoom(term);
					}
				}else if(bean.getM_result()==1){
					term.setR_id(rid);
					term.setR_state(2);
					rdao.updateRoom(term);
				}
			}
			
		}
		return number;
	}

	@Override
	public List<MaintainBean> findMaintainByPage(int page, int count) {
		List<MaintainBean> list =  mdao.selectMaintainByPage((page-1)*count, count);
		return list;
	}

	@Override
	public int findPageNum(int count) {
		int number = 0;
		int num = mdao.selectAllMaintain().size();
		if(num%count!=0){
			number=(num/count)+1;
		}else{
			number=num/count;
		}
		return number;
	}

}
