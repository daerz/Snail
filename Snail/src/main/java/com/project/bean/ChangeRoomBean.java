package com.project.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;

/**
 * 换房输入数据
 * @author Administrator
 *
 */
public class ChangeRoomBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//原房间号
	@DecimalMin(value="1",message="请写正确格式")
	private int l_r_num;
	//身份证
	private String l_uid;
	//新房间id
	@DecimalMin(value="1",message="请写正确格式")
	private int r_id;
	
	
	public int getL_r_num() {
		return l_r_num;
	}
	public void setL_r_num(int l_r_num) {
		this.l_r_num = l_r_num;
	}
	public String getL_uid() {
		return l_uid;
	}
	public void setL_uid(String l_uid) {
		this.l_uid = l_uid;
	}
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}

	
	
	
	
}
