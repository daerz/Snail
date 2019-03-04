package com.project.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;


/**
 * 房型详细图片信息
 * @author 大耳贼
 *
 */
public class RoomTypeImgBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//主键
	private int rti_id;
	//房型外键
	@DecimalMin(value="1",message="请选择指定房型！")
	@DecimalMax(value="20",message="可住人数不能大于20！")
	private int rti_rt_id;
	//图片路径
	private String rti_path;
	//图片备注
	@Size(max=20,message="图片备注长度不得大于20字！")
	private String rti_note;
	//软删除
	private int rti_flage;
	public int getRti_id() {
		return rti_id;
	}
	public void setRti_id(int rti_id) {
		this.rti_id = rti_id;
	}
	public int getRti_rt_id() {
		return rti_rt_id;
	}
	public void setRti_rt_id(int rti_rt_id) {
		this.rti_rt_id = rti_rt_id;
	}
	public String getRti_path() {
		return rti_path;
	}
	public void setRti_path(String rti_path) {
		this.rti_path = rti_path;
	}
	public String getRti_note() {
		return rti_note;
	}
	public void setRti_note(String rti_note) {
		this.rti_note = rti_note;
	}
	public int getRti_flage() {
		return rti_flage;
	}
	public void setRti_flage(int rti_flage) {
		this.rti_flage = rti_flage;
	}
	@Override
	public String toString() {
		return "RoomTypeImgBean [rti_id=" + rti_id + ", rti_rt_id=" + rti_rt_id + ", rti_path=" + rti_path
				+ ", rti_note=" + rti_note + ", rti_flage=" + rti_flage + "]";
	}
	
	
}
