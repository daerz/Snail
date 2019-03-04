package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.service.RedisService;
import com.project.util.GetTimeByEveryDay;
import com.project.util.RedisUtil;

@Service
public class RedisServiceImpl implements RedisService{
	
	@Autowired
	private  RedisUtil redis;
	
	@Override
	public void saveToRedis(String r_id, String beginDay, String endDay) {
		GetTimeByEveryDay getTim = new GetTimeByEveryDay();
		List<Long> list = getTim.getByTimeStamp(beginDay, endDay);
		for (Long i : list) {
			redis.sSet(r_id,i);
		}
		
	}

	@Override
	public boolean findTimeExist(String r_id, String beginDay, String endDay) {
		boolean ex = true;
		GetTimeByEveryDay getTim = new GetTimeByEveryDay();
		List<Long> list = getTim.getByTimeStamp(beginDay, endDay);
		for (Long i : list) {
			if(redis.sHasKey(r_id,i)){
				ex = false;
				break;
			}
		}
		return ex;
	}

	@Override
	public void delTimeToRedis(String r_id, String beginDay, String endDay) {
		GetTimeByEveryDay getTim = new GetTimeByEveryDay();
		List<Long> list = getTim.getByTimeStamp(beginDay, endDay);
		for (Long i : list) {
			redis.setRemove(r_id,i);
		}
	}

	
}
