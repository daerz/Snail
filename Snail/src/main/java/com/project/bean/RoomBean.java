package com.project.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * @author tokiri
 * 房间信息
 *
 */
public class RoomBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//房间id
	@DecimalMin(value="0",message="请输入正确的房间id！")
	private int r_id;
	//房间号
	@DecimalMin(value="0",message="请输入正确的房间号！")
	private int r_num;
	//对应房型id（外键）
	@DecimalMin(value="0",message="请输入正确的房型号！")
	private int r_rt_id;
	//价格字段
	@DecimalMin(value="0",message="请输入正确的价格！")
	private double r_price;
	//折扣字段(0.0~1)
	@DecimalMin(value="0",message="请输入正确的折扣！")
	@DecimalMax(value="1",message="请输入正确的折扣！")
	private double r_discount;
	//是否是烟房（0：无要求，1：无烟房，2：有烟房）
	@DecimalMin(value="0",message="请输入正确的烟房类型！")
	@DecimalMax(value="2",message="请输入正确的烟房类型！")
	private int r_smoken;
	//是否有早（0：无要求，1：无早，2：有早）
	@DecimalMin(value="0",message="请输入正确的早餐类型！")
	@DecimalMax(value="2",message="请输入正确的早餐类型！")
	private int r_breakfast;
	//窗户状态（0：无要求，1：普通窗，2：落地窗）
	@DecimalMin(value="0",message="请输入正确的窗户类型！")
	@DecimalMax(value="2",message="请输入正确的窗户类型！")
	private int r_window;
	//房间状态（0：无需求，1:可以入住，2:不可入住）
	@DecimalMin(value="0",message="请输入正确的房间状态！")
	@DecimalMax(value="2",message="请输入正确的房间状态！")
	private int r_state;
	//备注信息
	private String r_note;
	//软删除
	private int r_flage;
	
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}
	public int getR_num() {
		return r_num;
	}
	public void setR_num(int r_num) {
		this.r_num = r_num;
	}
	public int getR_rt_id() {
		return r_rt_id;
	}
	public void setR_rt_id(int r_rt_id) {
		this.r_rt_id = r_rt_id;
	}
	public double getR_price() {
		return r_price;
	}
	public void setR_price(double r_price) {
		this.r_price = r_price;
	}
	public double getR_discount() {
		return r_discount;
	}
	public void setR_discount(double r_discount) {
		this.r_discount = r_discount;
	}
	public int getR_smoken() {
		return r_smoken;
	}
	public void setR_smoken(int r_smoken) {
		this.r_smoken = r_smoken;
	}
	public int getR_breakfast() {
		return r_breakfast;
	}
	public void setR_breakfast(int r_breakfast) {
		this.r_breakfast = r_breakfast;
	}
	public int getR_window() {
		return r_window;
	}
	public void setR_window(int r_window) {
		this.r_window = r_window;
	}
	public int getR_state() {
		return r_state;
	}
	public void setR_state(int r_state) {
		this.r_state = r_state;
	}
	public String getR_note() {
		return r_note;
	}
	public void setR_note(String r_note) {
		this.r_note = r_note;
	}
	public int getR_flage() {
		return r_flage;
	}
	public void setR_flage(int r_flage) {
		this.r_flage = r_flage;
	}
	@Override
	public String toString() {
		return "RoomBean [r_id=" + r_id + ", r_num=" + r_num + ", r_rt_id=" + r_rt_id + ", r_price=" + r_price
				+ ", r_discount=" + r_discount + ", r_smoken=" + r_smoken + ", r_breakfast=" + r_breakfast
				+ ", r_window=" + r_window + ", r_state=" + r_state + ", r_note=" + r_note + ", r_flage=" + r_flage
				+ "]";
	}
	
}
