package com.project.tools;

import java.io.Serializable;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserNamePasswordTelphoneToken extends UsernamePasswordToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
		// 手机号码
		private String telphoneNum;
		//登录方式
		private LoginEnum loginType;
	 
		/**
		 * 重写getPrincipal方法
		 */
		public Object getPrincipal() {
			// TODO Auto-generated method stub
			// 如果获取到用户名，则返回用户名，否则返回电话号码
			if (telphoneNum == null) {
				return getUsername();
			} else {
				return getTelphoneNum();
			}
		}
	 
		/**
		 * 重写getCredentials方法
		 */
		public Object getCredentials() {
			// TODO Auto-generated method stub
			// 如果获取到密码，则返回密码，否则返回null
			if (telphoneNum == null) {
				return getPassword();
			} else {
				return "ok";
			}
		}
	 
		public UserNamePasswordTelphoneToken() {
			// TODO Auto-generated constructor stub
		}
	 
		public UserNamePasswordTelphoneToken(final String telphoneNum) {
			// TODO Auto-generated constructor stub
			this.telphoneNum = telphoneNum;
		}
	 
		public UserNamePasswordTelphoneToken(final String userName, final String password) {
			// TODO Auto-generated constructor stub
			super(userName, password);
		}
	 
		public String getTelphoneNum() {
			return telphoneNum;
		}
	 
		public void setTelphoneNum(String telphoneNum) {
			this.telphoneNum = telphoneNum;
		}
	 
		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public final LoginEnum getLoginType() {
			return loginType;
		}

		public final void setLoginType(LoginEnum admin) {
			this.loginType = admin;
		}
		
}
