package com.vvvv.user.view.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vvvv.user.model.Action;
import com.vvvv.user.model.Role;
import com.vvvv.user.model.UserInfo;



/**
 * @className:CheckLoginInterceptor.java
 * @classDescription:检测登录拦截器
 * @author:xiayingjie
 * @createTime:2011-5-26
 */

public class CheckLoginInterceptor extends HandlerInterceptorAdapter{
		

	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// TODO Auto-generated method stub
		//过滤登录
		String requestUrl = request.getRequestURI();
		if(requestUrl.indexOf("login.do")>-1){
			return true;
		}		
		// 获得登陆时的用户
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"userAdmin");
		
		if(null==userInfo){
			response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/manage/login.jsp");
			return false;
		}
		//判断用户是否有权限访问
//		for(Role role:userInfo.getRoles()){
//			for(Action action : role.getActions()){
//				if(requestUrl.indexOf(action.getPath())>-1){
					return super.preHandle(request, response, handler);
//				}
//			}
//		}
//		response.sendRedirect(request.getContextPath()
//				+ "/commons/noAction.jsp");
//		return false;
	}
	

}
