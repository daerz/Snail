package com.project.bean;

import java.io.Serializable;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * 订单对象
 * @author acer
 *
 */
public class OrderBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//订单编号 32位字符串
	@NotEmpty(message="订单号不能为空！")
	private String 	o_id;
	//用户电话号码
	private String	o_u_phone;
	//订单电话号码（入住者电话）
	private String	o_phone;
	//应付金额（已打折）
	private double 	o_r_price;
	//下单时间（年月日时分秒-数据库自动生成）
	private String	o_ordertime;
	//支付状态（0-未付，1-支付,2-已办理入住手续，3-取消订单且未入房，4-已退房）
	private int 	o_state;
	//入住时间（年月日）
	@NotEmpty(message="预计入住时间不能为空")
	@Pattern(regexp="(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)",message="预计入住日期格式错误")
	private String	o_intime;
	//退房时间（同上）
	@NotEmpty(message="预计退房时间不能为空")
	@Pattern(regexp="(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)",message="预计退房日期格式错误")
	private String	o_outtime;
	//软删除（默认0，删除为1）
	private int		o_flag;
	//一个订单对应多个订单详情表，即一对多
	private List<OrderInfoBean> listOrderInfo;
	public String getO_id() {
		return o_id;
	}
	public void setO_id(String o_id) {
		this.o_id = o_id;
	}
	public String getO_u_phone() {
		return o_u_phone;
	}
	public void setO_u_phone(String o_u_phone) {
		this.o_u_phone = o_u_phone;
	}
	public String getO_phone() {
		return o_phone;
	}
	public void setO_phone(String o_phone) {
		this.o_phone = o_phone;
	}
	public double getO_r_price() {
		return o_r_price;
	}
	public void setO_r_price(double o_r_price) {
		this.o_r_price = o_r_price;
	}
	public String getO_ordertime() {
		return o_ordertime;
	}
	public void setO_ordertime(String o_ordertime) {
		this.o_ordertime = o_ordertime;
	}
	public int getO_state() {
		return o_state;
	}
	public void setO_state(int o_state) {
		this.o_state = o_state;
	}

	public String getO_intime() {
		return o_intime;
	}
	public void setO_intime(String o_intime) {
		this.o_intime = o_intime;
	}
	public String getO_outtime() {
		return o_outtime;
	}
	public void setO_outtime(String o_outtime) {
		this.o_outtime = o_outtime;
	}
	public int getO_flag() {
		return o_flag;
	}
	public void setO_flag(int o_flag) {
		this.o_flag = o_flag;
	}
	public List<OrderInfoBean> getListOrderInfo() {
		return listOrderInfo;
	}
	public void setListOrderInfo(List<OrderInfoBean> listOrderInfo) {
		this.listOrderInfo = listOrderInfo;
	}
	@Override
	public String toString() {
		return "OrderBean [o_id=" + o_id + ", o_u_phone=" + o_u_phone + ", o_phone=" + o_phone + ", o_r_price="
				+ o_r_price + ", o_ordertime=" + o_ordertime + ", o_state=" + o_state + ", o_intime=" + o_intime
				+ ", o_outtime=" + o_outtime + ", o_flag=" + o_flag + ", listOrderInfo=" + listOrderInfo + "]";
	}





	
	
}
