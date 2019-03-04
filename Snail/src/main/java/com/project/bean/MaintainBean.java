package com.project.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * @author tokiri
 * 设施维护信息
 *
 */
public class MaintainBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//主键id
	@DecimalMin(value="0",message="请输入正确的维护id！")
	private int m_id;
	//对应房间id（外键）
	@DecimalMin(value="0",message="请输入正确的房间id！")
	private int m_r_id;
	//损坏描述
	private String m_info;
	//报损日期
	private String m_date;
	//是否以维修完成（1:未维修，2:维修完成）
	@DecimalMin(value="0",message="请输入正确的烟房类型！")
	@DecimalMax(value="2",message="请输入正确的烟房类型！")
	private int m_result;
	//软删除
	private int m_flage;
	
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public int getM_r_id() {
		return m_r_id;
	}
	public void setM_r_id(int m_r_id) {
		this.m_r_id = m_r_id;
	}
	public String getM_info() {
		return m_info;
	}
	public void setM_info(String m_info) {
		this.m_info = m_info;
	}
	public String getM_date() {
		return m_date;
	}
	public void setM_date(String m_date) {
		this.m_date = m_date;
	}
	public int getM_result() {
		return m_result;
	}
	public void setM_result(int m_result) {
		this.m_result = m_result;
	}
	public int getM_flage() {
		return m_flage;
	}
	public void setM_flage(int m_flage) {
		this.m_flage = m_flage;
	}
	
	
	
}
