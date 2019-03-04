package com.project.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentBean {
	private int c_id;
	private String c_name;
	private String c_content;
	private Date c_time;
	private int c_score;
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getC_time() {
		return c_time;
	}
	public void setC_time(Date c_time) {
		this.c_time = c_time;
	}
	
	public int getC_score() {
		return c_score;
	}
	public void setC_score(int c_score) {
		this.c_score = c_score;
	}
	@Override
	public String toString() {
		return "CommentBean [c_id=" + c_id + ", c_name=" + c_name + ", c_content=" + c_content + ", c_time=" + c_time
				+ ", c_score=" + c_score + "]";
	}
	
	
	
}
