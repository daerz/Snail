package com.project.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 房间类型
 * @author 大耳贼
 *
 */
public class RoomTypeBean implements Serializable{


	private static final long serialVersionUID = 1L;
	//主键
	private int rt_id;		
	//可住人数
	@DecimalMin(value="1",message="可住人数不能小于1！")
	@DecimalMax(value="20",message="可住人数不能大于20！")
	private int rt_num;
	//房间面积
	@DecimalMin(value="20",message="房型面积不能小于20平米！")
	@DecimalMax(value="1000",message="房型面积不能大于1000平米！")
	private int rt_area;
	//首页图片
	private String rt_img;
	//房型名
	@NotEmpty(message="房型名称不能为空！")
	@Size(max=20,message="名称长度不得大于20！")
	private String rt_name;
	//备注信息
	@NotEmpty(message="备注信息不能为空！")
	@Size(max=100,message="备注信息长度不得大于100！")
	private String rt_note;
	//软删除默认为0
	private int rt_flage;
	
	public int getRt_id() {
		return rt_id;
	}
	public void setRt_id(int rt_id) {
		this.rt_id = rt_id;
	}
	public int getRt_num() {
		return rt_num;
	}
	public void setRt_num(int rt_num) {
		this.rt_num = rt_num;
	}
	public int getRt_area() {
		return rt_area;
	}
	public void setRt_area(int rt_area) {
		this.rt_area = rt_area;
	}
	public String getRt_img() {
		return rt_img;
	}
	public void setRt_img(String rt_img) {
		this.rt_img = rt_img;
	}
	public String getRt_name() {
		return rt_name;
	}
	public void setRt_name(String rt_name) {
		this.rt_name = rt_name;
	}
	public String getRt_note() {
		return rt_note;
	}
	public void setRt_note(String rt_note) {
		this.rt_note = rt_note;
	}
	public int getRt_flage() {
		return rt_flage;
	}
	public void setRt_flage(int rt_flage) {
		this.rt_flage = rt_flage;
	}
	@Override
	public String toString() {
		return "RoomTypeBean [rt_id=" + rt_id + ", rt_num=" + rt_num + ", rt_area=" + rt_area + ", rt_img=" + rt_img
				+ ", rt_name=" + rt_name + ", rt_note=" + rt_note + ", rt_flage=" + rt_flage + "]";
	}
	
	
}
