package com.project.bean;

import java.io.Serializable;

/**
 * 登录日志
 * @author zyz
 *
 */
public class LogBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//登录日志id
	private int l_id;
	//登录用户名
	private String l_name;
	//登录地址
	private String l_address;
	//登录ip
	private String l_ip;
	//登录时间
	private String l_time;
	
	public final int getL_id() {
		return l_id;
	}
	public final void setL_id(int l_id) {
		this.l_id = l_id;
	}
	public final String getL_name() {
		return l_name;
	}
	public final void setL_name(String l_name) {
		this.l_name = l_name;
	}
	public final String getL_addres() {
		return l_address;
	}
	public final void setL_addres(String l_addres) {
		this.l_address = l_addres;
	}
	public final String getL_ip() {
		return l_ip;
	}
	public final void setL_ip(String l_ip) {
		this.l_ip = l_ip;
	}
	public final String getL_time() {
		return l_time;
	}
	public final void setL_time(String l_time) {
		this.l_time = l_time;
	}
}
