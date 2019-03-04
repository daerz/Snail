package com.project.bean;
/**
 * 会员折扣表
 * @author Administrator
 *
 */
public class DiscountBean {
	private int d_id;
	private int d_class;
	private double d_dis;
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public int getD_class() {
		return d_class;
	}
	public void setD_class(int d_class) {
		this.d_class = d_class;
	}
	public double getD_dis() {
		return d_dis;
	}
	public void setD_dis(double d_dis) {
		this.d_dis = d_dis;
	}
	@Override
	public String toString() {
		return "Discount [d_id=" + d_id + ", d_class=" + d_class + ", d_dis=" + d_dis + "]";
	}
	
}
