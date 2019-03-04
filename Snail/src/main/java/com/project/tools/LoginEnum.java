package com.project.tools;

public enum LoginEnum {
	//用户登录和管理员登录
	USER("1"),ADMIN("2");
	
	private String type;
	
	private LoginEnum(String type){
		this.type = type;
	}
	@Override
	public  String toString(){
		return this.type.toString();
	}
}
