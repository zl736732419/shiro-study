package com.zheng.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

//@WebServlet(name="loginSevlet", urlPatterns="/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String error = null;
		String username= req.getParameter("username");
		String password = req.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		}catch(UnknownAccountException e) {
			error = "登录失败，用户名不存在!";
		}catch(LockedAccountException e1) {
			error = "登录失败，用户已禁用!";
		}catch(IncorrectCredentialsException e2) {
			error = "登录失败，用户名/密码错误!";
		}catch(AuthenticationException e3) {
			error = "其他错误：" + e3.getMessage();
		}
		
		if(error != null) { //说明登录发生错误
			req.setAttribute("error", error);
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
		}else { //登录成功
			req.getSession().setAttribute("subject", subject);
			resp.sendRedirect(req.getContextPath() + "/authenticated");
		}
	}
	
}
