package com.project.bean;

/**
 * 分页条件bean
 * @author acer
 *
 */
public class OrderConditionBean {

	private String o_u_phone;
	
	private int limitNum;
	public String getO_u_phone() {
		return o_u_phone;
	}
	public void setO_u_phone(String o_u_phone) {
		this.o_u_phone = o_u_phone;
	}

	public int getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}
	
}
