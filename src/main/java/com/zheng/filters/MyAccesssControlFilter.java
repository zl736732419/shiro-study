package com.zheng.filters;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 仿formAuthenticationFilter实现用户登录成功后直接跳转到访问的页面路径下
 *
 * @author Administrator
 * @data 2016年5月30日 下午11:25:03
 */
public class MyAccesssControlFilter extends FormAuthenticationFilter {

	private String loginUrl = "/login";
	private String successUrl = "/";

	public String getLoginUrl() {
		return loginUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject.isAuthenticated()) {
			return true;
		}

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) { // post
				boolean loginSuccess = login(request);
				if (loginSuccess) {
					return redirectToSuccessUrl(req, resp);
				} else {
					WebUtils.issueRedirect(req, resp, loginUrl); // 直接跳转到登录页面
				}
			} else {
				return true; // get方式，直接跳转到登录页面
			}
		} else { // 没有登录，但访问其他资源
			saveRequestAndRedirectToLogin(req, resp);
			return false;
		}

		return false;
	}

	private void saveRequestAndRedirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		WebUtils.saveRequest(req);
		WebUtils.issueRedirect(req, resp, loginUrl);
	}

	private boolean redirectToSuccessUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 这里如果之前保存过访问的路径，就会再次访问之前的路径，否则就访问successUrl
		WebUtils.redirectToSavedRequest(req, resp, successUrl);
		return false;
	}

	/**
	 * 简单的用户登录
	 *
	 * @author Administrator
	 * @data 2016年5月30日 下午11:33:43
	 * @param req
	 * @return
	 */
	private boolean login(ServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		try {
			SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
		} catch (AuthenticationException e) {
			req.setAttribute("shiroLoginFailure", e.getClass());
			return false;
		}
		return true;

	}
}
