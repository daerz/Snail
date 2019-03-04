package com.project.bean;

import java.io.Serializable;

/**
 * 前台用户登陆、注册
 * @author Administrator
 *
 */
public class UserBean implements Serializable {
	//用户id
	private int u_id;
	//用户手机号码
	private String u_phone;
	//密码
	private String u_pass;
	
	public String pass_two;
	
	private String code;
	
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getU_pass() {
		return u_pass;
	}
	public void setU_pass(String u_pass) {
		this.u_pass = u_pass;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getPass_two() {
		return pass_two;
	}
	public void setPass_two(String pass_two) {
		this.pass_two = pass_two;
	}
	@Override
	public String toString() {
		return "UserBean [u_id=" + u_id + ", u_phone=" + u_phone + ", u_pass=" + u_pass + ", pass_two=" + pass_two
				+ ", code=" + code + "]";
	}
	
	
	
	
	
	
}
