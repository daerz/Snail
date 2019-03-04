package com.project.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.bean.UserBean;
import com.project.service.IUserService;

public class TelephoneRealm extends AuthorizingRealm {
	@Autowired
	private IUserService service;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String telphoneNum = (String) token.getPrincipal();
		UserBean bean = service.findUserByName(telphoneNum);
		System.out.println(" TelephoneRealm"+telphoneNum);
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(bean.getU_phone(), "ok", getName());
		return authcInfo;
	}

}
