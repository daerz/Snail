package com.project.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 会员级别
 * @author Administrator
 *
 */
public class VIPBean implements Serializable{
	private int v_id;
	//用户姓名
	private String v_name;
	//用户性别
	private String v_sex;
	//注册日期
	private Date v_birthday;
	
	private int v_code;
	
	private int u_id;
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public int getV_id() {
		return v_id;
	}
	public void setV_id(int v_id) {
		this.v_id = v_id;
	}
	
	public String getV_name() {
		return v_name;
	}
	public void setV_name(String v_name) {
		this.v_name = v_name;
	}
	public String getV_sex() {
		return v_sex;
	}
	public void setV_sex(String v_sex) {
		this.v_sex = v_sex;
	}
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getV_birthday() {
		return v_birthday;
	}
	public void setV_birthday(Date v_birthday) {
		this.v_birthday = v_birthday;
	}
	public int getV_code() {
		return v_code;
	}
	public void setV_code(int v_code) {
		this.v_code = v_code;
	}
	@Override
	public String toString() {
		return "VIPBean [v_id=" + v_id + ", v_name=" + v_name + ", v_sex=" + v_sex + ", v_birthday=" + v_birthday
				+ ", v_code=" + v_code + ", u_id=" + u_id + "]";
	}
	
}
