package com.project.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.bean.ManageUserBean;
import com.project.service.IUserService;



public class AdminRealm extends AuthorizingRealm {
	@Autowired
	private IUserService Service;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("AuthorizationInfo  33");

		String name = (String) principals.getPrimaryPrincipal();
		System.out.println("AuthorizationInfo  33"+name);
		ManageUserBean bean = Service.findManageUserByName(name);
		if (bean != null) {
			System.out.println("ManageUserBean"+bean);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermission(bean.getRole());
			return info;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String name = (String) token.getPrincipal();
		ManageUserBean bean = Service.findManageUserByName(name);
		if (bean !=null) {
			System.out.println("AuthenticationInfo"+bean);
			ByteSource by = ByteSource.Util.bytes(bean.getM_name());
			AuthenticationInfo info = new SimpleAuthenticationInfo(bean.getM_name(), bean.getM_pass(),by, getName());
			System.out.println(info);
			return info;
		}
		return null;
	}

}
