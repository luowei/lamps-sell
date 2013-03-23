package com.vvvv.lamps.view.head;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.DateUtil;
import com.vvvv.common.view.BaseController;
import com.vvvv.lamps.model.Orders;
import com.vvvv.lamps.view.dto.Cart;
import com.vvvv.lamps.view.dto.CartRowValue;
import com.vvvv.user.model.UserInfo;

/**
 * @className:LoginController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-5-3下午12:37:59 
 */
@Controller
@RequestMapping("/head")
public class LoginController extends BaseController{
	
	/**
	 * 用户登录
	 * @author wei.luo
	 * @createTime 2012-5-3
	 * @param request
	 * @param response
	 * @param model
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,HttpServletResponse response,
			Model model,String userName,String userPassword, RedirectAttributes redirectAttrs){
		
		List<UserInfo> userInfoList=(List<UserInfo>)EHCacheUtil.getValue("user_cache", "userInfoList");
		Map<Integer,UserInfo> userInfoMap=(Map<Integer,UserInfo>)EHCacheUtil.getValue("user_cache", "userInfoMap");
//		if(userInfoList==null){
//			userInfoList=this.userService.findList(" from UserInfo u where u.isEnable > '0'");
//			try {
//				EHCacheUtil.setValue("user_cache", "userInfoList", userInfoList);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		if(userInfoList==null){
			userInfoList=this.userService.findList(" from UserInfo u where u.isEnable > '0'");
			try {
				EHCacheUtil.setValue("user_cache", "userInfoList", userInfoList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(userInfoMap == null ){
			userInfoMap = new HashMap<Integer, UserInfo>();
			for(UserInfo user:userInfoList){
				userInfoMap.put(user.getId(), user);
			}
			try {
				EHCacheUtil.setValue("user_cache", "userInfoMap", userInfoMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String login_flag=null;
		boolean flag=false;
		for(UserInfo user:userInfoList){
			if((user.getUserName()).equals(userName) && (user.getUserPassword()).equals(userPassword)){
				request.getSession().setAttribute("user", user);
				//Cart cart = new Cart();
				Map<String,CartRowValue> cartRowMap = new HashMap<String,CartRowValue>();
				Cart cart=new Cart(cartRowMap);
				request.getSession().setAttribute("cart", cart);
				login_flag="success";
				flag=true;
			}
		}
		if(flag==false){
			login_flag="failed";
		}
		//redirectAttrs.addAttribute("login_flag", login_flag);		//直接在url后拼参数
		redirectAttrs.addFlashAttribute("login_flag", login_flag);	//将参数放在session中，请求处理完毕
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		currentPage=currentPage.replace(this.getRoot(request),"");
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		//UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).queryParam("login_flag", login_flag).build().encode();
			
		return "redirect:" + redirectUri.toUriString();
		//String forwardPath=this.getHttpRoot(request)+currentPage;
		
//		this.sendRedirect(response, currentPage);
//		return null;
	}
	
	@RequestMapping(value = "/reg")
	public String register(HttpServletRequest request,HttpServletResponse response,
			Model model,UserInfo user,String repassword,RedirectAttributes redirectAttrs){
		String register_flag="failed";
		if(repassword.equals(user.getUserPassword())){
			List<UserInfo> userInfoList=(List<UserInfo>)EHCacheUtil.getValue("user_cache", "userInfoList");
			if(userInfoList==null){
				userInfoList=this.userService.findList(" from UserInfo u where u.isEnable > '0'");
				try {
					EHCacheUtil.setValue("user_cache", "userInfoList", userInfoList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			boolean registed_flag=false;
			for(UserInfo tuser:userInfoList){
				if(tuser.getUserName().equals(user.getUserName())){
					registed_flag=true;
					break;
				}
			}
			
			if(registed_flag==false){
				user.setIsBetter(0);
				user.setOnline(0L);
				user.setIsEnable(3);	//等于，表示状态在申请中
				user.setScore(0);
				user.setCreateTime(DateUtil.datetimeToDate());
				this.userService.save(user);
				register_flag="success";
				try {
					EHCacheUtil.removeElment("user_cache", "userInfoList");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		redirectAttrs.addAttribute("register_flag", register_flag);	//将参数放在session中，请求处理完毕
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		currentPage=currentPage.replace(this.getRoot(request),"");
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
			
		return "redirect:" + redirectUri.toUriString();
	}
	
	/**
	 * 退出登录
	 * @author wei.luo
	 * @createTime 2012-5-3
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user_exit")
	public String login(HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("cart");
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
}
