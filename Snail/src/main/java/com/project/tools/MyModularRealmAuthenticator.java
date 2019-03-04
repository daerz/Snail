package com.project.tools;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import com.project.realm.UserRealm;

public class MyModularRealmAuthenticator extends ModularRealmAuthenticator{
	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        UserNamePasswordTelphoneToken telphoneToken = (UserNamePasswordTelphoneToken) authenticationToken;
        // 所有Realm
        Collection<Realm> realms = getRealms();
     // 判断是单Realm还是多Realm
        if (realms.size() == 1) {
        	return doSingleRealmAuthentication(realms.iterator().next(), telphoneToken);
        }
        else if(telphoneToken.getLoginType().equals(LoginEnum.ADMIN)) {
        	return doSingleRealmAuthentication(realms.iterator().next(), telphoneToken);
        }else {
            return doMultiRealmAuthentication(realms, telphoneToken);
        }
	}
}
