package com.project.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * 入住信息
 * @author Administrator
 *
 */
public class LiveBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//入住id
	@DecimalMin(value="0",message="id格式不正确")
	private int l_id;
	//身份证号码
	private String l_uid;
	//姓名
	private String l_uname;
	//性别	
	private String l_usex;
	//预计退房时间
	private String l_o_outtime;
	//订单id（外键）
	private String l_o_id;
	//实际入住时间
	private String l_intime;
	//实际退房时间
	private String l_outtime;
	//入住房间号
	@DecimalMin(value="0",message="请写正确格式")
	private int l_r_num;
	//软删除（0是所有，1是已住，2是退房）
	@DecimalMin(value="0",message="请写正确格式")
	@DecimalMax(value="2",message="请写正确格式")
	private int l_flag;
	public int getL_id() {
		return l_id;
	}
	public void setL_id(int l_id) {
		this.l_id = l_id;
	}
	public String getL_uid() {
		return l_uid;
	}
	public void setL_uid(String l_uid) {
		this.l_uid = l_uid;
	}
	public String getL_uname() {
		return l_uname;
	}
	public void setL_uname(String l_uname) {
		this.l_uname = l_uname;
	}
	public String getL_usex() {
		return l_usex;
	}
	public void setL_usex(String l_usex) {
		this.l_usex = l_usex;
	}
	public String getL_o_id() {
		return l_o_id;
	}
	public void setL_o_id(String l_o_id) {
		this.l_o_id = l_o_id;
	}
	public String getL_intime() {
		return l_intime;
	}
	public void setL_intime(String l_intime) {
		this.l_intime = l_intime;
	}
	public String getL_outtime() {
		return l_outtime;
	}
	public void setL_outtime(String l_outtime) {
		this.l_outtime = l_outtime;
	}
	public int getL_r_num() {
		return l_r_num;
	}
	public void setL_r_num(int l_r_num) {
		this.l_r_num = l_r_num;
	}
	public int getL_flag() {
		return l_flag;
	}
	public void setL_flag(int l_flag) {
		this.l_flag = l_flag;
	}
	
	public String getL_o_outtime() {
		return l_o_outtime;
	}
	public void setL_o_outtime(String l_o_outtime) {
		this.l_o_outtime = l_o_outtime;
	}
	@Override
	public String toString() {
		return "LiveBean [l_id=" + l_id + ", l_uid=" + l_uid + ", l_uname=" + l_uname + ", l_usex=" + l_usex
				+ ", l_o_id=" + l_o_id + ", l_intime=" + l_intime + ", l_outtime=" + l_outtime + ", l_r_num=" + l_r_num
				+ ", l_flag=" + l_flag + "]";
	}
	
	
	
	
}
