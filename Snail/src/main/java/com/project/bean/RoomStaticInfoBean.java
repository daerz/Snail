package com.project.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

/**
 * 房间静态信息
 * @author 大耳贼
 *
 */
public class RoomStaticInfoBean implements Serializable{


	private static final long serialVersionUID = 1L;
	//主键
	private int si_id;
	//房间类型外键
	@DecimalMin(value="1",message="请选择房型！")
	private int si_rt_id;
	//家具
	@Size(max=20,message="家具信息不得超过20字！")
	private String si_furniture;
	//浴室配套
	@Size(max=20,message="浴室信息不得超过20字！")
	private String si_bath;
	//休闲娱乐
	@Size(max=20,message="休闲信息不得超过20字！")
	private String si_entertainment;
	//接机服务
	@Size(max=20,message="交通信息不得超过20字！")
	private String si_airport;
	//取消规则
	@Size(max=100,message="取消规则不得超过100字！")
	private String si_cancleRule;
	//使用规则
	@Size(max=100,message="使用规则不得超过100字！")
	private String si_useRule;
	//软删除：默认为0
	private int si_flage;
	public int getSi_id() {
		return si_id;
	}
	public void setSi_id(int si_id) {
		this.si_id = si_id;
	}
	public int getSi_rt_id() {
		return si_rt_id;
	}
	public void setSi_rt_id(int si_rt_id) {
		this.si_rt_id = si_rt_id;
	}
	public String getSi_furniture() {
		return si_furniture;
	}
	public void setSi_furniture(String si_furniture) {
		this.si_furniture = si_furniture;
	}
	public String getSi_bath() {
		return si_bath;
	}
	public void setSi_bath(String si_bath) {
		this.si_bath = si_bath;
	}
	public String getSi_entertainment() {
		return si_entertainment;
	}
	public void setSi_entertainment(String si_entertainment) {
		this.si_entertainment = si_entertainment;
	}
	public String getSi_airport() {
		return si_airport;
	}
	public void setSi_airport(String si_airport) {
		this.si_airport = si_airport;
	}
	public String getSi_cancleRule() {
		return si_cancleRule;
	}
	public void setSi_cancleRule(String si_cancleRule) {
		this.si_cancleRule = si_cancleRule;
	}
	public String getSi_useRule() {
		return si_useRule;
	}
	public void setSi_useRule(String si_useRule) {
		this.si_useRule = si_useRule;
	}
	public int getSi_flage() {
		return si_flage;
	}
	public void setSi_flage(int si_flage) {
		this.si_flage = si_flage;
	}
	@Override
	public String toString() {
		return "RoomStaticInfoBean [si_id=" + si_id + ", si_rt_id=" + si_rt_id + ", si_furniture=" + si_furniture
				+ ", si_bath=" + si_bath + ", si_entertainment=" + si_entertainment + ", si_airport=" + si_airport
				+ ", si_cancleRule=" + si_cancleRule + ", si_useRule=" + si_useRule + ", si_flage=" + si_flage + "]";
	}
	
	
	
	
}
