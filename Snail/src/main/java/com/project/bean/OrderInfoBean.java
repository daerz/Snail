package com.project.bean;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 订单详情
 * @author acer
 *
 */
public class OrderInfoBean {
	//主键
	@DecimalMin(value="0",message="请写正确格式")
	private int i_id;
	//房间ID
	@DecimalMin(value="0",message="请写正确格式")
	private int i_r_id;
	//房间号
	@DecimalMin(value="0",message="请写正确格式")
	private int i_r_num;
	//订单编号
	@NotEmpty
	private String i_o_id;
	//房间实际单价
	private double i_r_price;
	//软删除
	private int i_flag;
	public int getI_id() {
		return i_id;
	}
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}
	public int getI_r_id() {
		return i_r_id;
	}
	public void setI_r_id(int i_r_id) {
		this.i_r_id = i_r_id;
	}
	public String getI_o_id() {
		return i_o_id;
	}
	public void setI_o_id(String i_o_id) {
		this.i_o_id = i_o_id;
	}
	public int getI_flag() {
		return i_flag;
	}
	public void setI_flag(int i_flag) {
		this.i_flag = i_flag;
	}
	
	public double getI_r_price() {
		return i_r_price;
	}
	public void setI_r_price(double i_r_price) {
		this.i_r_price = i_r_price;
	}
	public int getI_r_num() {
		return i_r_num;
	}
	public void setI_r_num(int i_r_num) {
		this.i_r_num = i_r_num;
	}
	@Override
	public String toString() {
		return "OrderInfoBean [i_id=" + i_id + ", i_r_id=" + i_r_id + ", i_r_num=" + i_r_num + ", i_o_id=" + i_o_id
				+ ", i_r_price=" + i_r_price + ", i_flag=" + i_flag + "]";
	}

	
}
