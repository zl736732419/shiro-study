package com.zheng.cache;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.zheng.domain.User;
import com.zheng.service.UserService;
import com.zheng.service.impl.UserServiceImpl;

public class MyCacheRealm extends AuthorizingRealm {
	private UserService userService = new UserServiceImpl();

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		Set<String> roles = userService.findRoles(username);
		Set<String> permissions = userService.findPermissions(username);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(permissions);

		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		User user = userService.findByUsername(username);

		if (user == null) {
			throw new UnknownAccountException("用户不存在!");
		}

		if (Boolean.TRUE.equals(user.getLocked())) {
			throw new LockedAccountException("当前用户已禁用!");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), getName());

		return info;
	}
	
	public void clearAllAuthentication() {
		super.getAuthenticationCache().clear();
	}
	
	public void clearAllAuthorization() {
		super.getAuthorizationCache().clear();
	}

	public void clearAllCache() {
		clearAllAuthentication();
		clearAllAuthorization();
	}
	
}
