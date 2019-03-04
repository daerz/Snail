package com.project.bean;


import javax.validation.constraints.DecimalMin;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 订单传递的信息
 * @author acer
 *
 */
public class ORParamBean {
	@NotEmpty(message="入住时间不能为空")
	@Pattern(regexp="(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)",message="预计入住日期格式错误")
	private String beginTime;
	@NotEmpty(message="退房时间不能为空")
	@Pattern(regexp="(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)",message="预计退房日期格式错误")
	private String endTime;
	
	@DecimalMin(value="1",message="内容错误")
	private int num;
	@NotEmpty(message="电话不能为空")
	@Pattern(regexp="^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",message="电话格式错误")
	private String phone;
	//订单下所有房间id，用于动态分配房间
	@NotEmpty(message="内容不能为空")
	private String idCount;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getIdCount() {
		return idCount;
	}
	public void setIdCount(String idCount) {
		this.idCount = idCount;
	}
	@Override
	public String toString() {
		return "ORParamBean [beginTime=" + beginTime + ", endTime=" + endTime + ", num=" + num + ", idCount=" + idCount
				+ "]";
	}
	
}
