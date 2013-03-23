package com.vvvv.common.tool.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className:CookieUtil.java
 * @classDescription:cookie操作工具类
 * @author:xiayingjie
 * @createTime:2010-10-27
 */

public class CookieUtil {

	/**
	 * 增加cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value) {
		
		addCookie(response,name,value,-1,null,null);
	}
	/**
	 * 增加cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param time
	 *            以分钟来算
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int time) {

		addCookie(response,name,value,time,null,null);
	}
	/**
	 * 增加cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param time
	 *            以分钟来算
	 * @param domain
	 *            域名
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int time, String path,String domain) {

		// 创建Cookie
		Cookie cookie = new Cookie(name, value);

		// 设置过期时间 以秒为单位
		if (time > 0) {
			cookie.setMaxAge(time * 60);
		}
		// 设置路径
		if (!"".equals(path) && null != path) {
			cookie.setPath(path);
		}
		// 设置域
		if (!"".equals(domain) && null != domain) {
			cookie.setDomain(domain);
		}

		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param domain
	 */
	public static void deleteCookie(HttpServletResponse response, String name) {
		deleteCookie(response,name,null,null);
	}
	
	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param domain
	 */
	public static void deleteCookie(HttpServletResponse response, String name, String path,String domain) {
	    Cookie c=new Cookie(name,"");
	    // 设置路径
		if (!"".equals(path) && null != path) {
			c.setPath(path);
		}
		// 设置域
		if (!"".equals(domain) && null != domain) {
			c.setDomain(domain);
		}
	    c.setMaxAge(0);
	    response.addCookie(c);
	}



	/**
	 * 获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie c = null;
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					c = cookie;
				}
			}
		}
		return c;
	}

	/**
	* 直接获取cookie的值，未找到则返回null
	* @author wandonghai
	* @createTime 2011-10-19
	* @param request
	* @param name
	* @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	


}
