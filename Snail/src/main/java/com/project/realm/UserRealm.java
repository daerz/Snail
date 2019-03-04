package com.project.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.bean.UserBean;
import com.project.service.IUserService;

/*
@Service("userRealm")*/
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private IUserService Service;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//用户手机号码
		String phone = (String) token.getPrincipal();
		System.out.println("zzzzz");
		UserBean bean = Service.findUserByName(phone);
		System.out.println("UserRealm"+phone);
		if (bean !=null) {
			System.out.println(phone);
			ByteSource by = ByteSource.Util.bytes(bean.getU_phone());
			AuthenticationInfo info = new SimpleAuthenticationInfo(bean.getU_phone(), bean.getU_pass(),by, getName());
			System.out.println(info);
			return info;
		}
		return null;
		
	}

}
