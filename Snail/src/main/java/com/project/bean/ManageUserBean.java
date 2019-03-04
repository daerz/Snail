package com.project.bean;

import java.io.Serializable;

/**
 * 管理员用户
 * @author Administrator
 *
 */
public class ManageUserBean implements Serializable{
	private int m_id;
	private String m_name;
	
	private String m_pass;
	
	private String pass_two;
	//权限
	private String role;
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_pass() {
		return m_pass;
	}
	public void setM_pass(String m_pass) {
		this.m_pass = m_pass;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getPass_two() {
		return pass_two;
	}
	public void setPass_two(String pass_two) {
		this.pass_two = pass_two;
	}
	@Override
	public String toString() {
		return "ManageUserBean [m_id=" + m_id + ", m_name=" + m_name + ", m_pass=" + m_pass + ", role=" + role + "]";
	}
	
}
