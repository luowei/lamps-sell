package com.vvvv.user.view.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvvv.common.tool.common.DateUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.BaseController;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.module.model.Menu;
import com.vvvv.user.model.Action;
import com.vvvv.user.model.Role;
import com.vvvv.user.model.UserInfo;
import com.vvvv.user.service.IRoleManageService;
import com.vvvv.user.service.IUserManageService;

/**
 * @className:UserController.java
 * @classDescription:用户管理
 * @author:xiayingjie
 * @createTime:2011-5-3
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController extends BaseController {

	@Autowired
	protected IUserManageService userManageService;
	@Autowired
	protected IRoleManageService roleManageService;

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login")
	public String login(String userName, String password,
			HttpServletRequest request) {

		UserInfo user = this.userManageService.login(userName, password);
		if (user != null) {
			//将用户拥有的菜单显示出来
			Map menuMap = new HashMap();
			for (Role role : user.getRoles()) {	
				for(Action action:role.getActions()){
					action.getActionName();
				}
				
				for (Menu menu : role.getMenus()) {
					if (!menuMap.containsKey(menu.getId())) {
						menuMap.put(menu.getId(), menu);
					}
				}
			}
			request.getSession().setAttribute("userAdmin", user);
			request.getSession().setAttribute("userMenuMap", menuMap);

			return "manage/index/index";
		} else {
			request.setAttribute("message", "用户名或者密码错误");
			return "manage/login";
		}
	}
	/**
	 * 登出
	 */
	@RequestMapping(value = "/loginOut")
	public String loginOut(HttpServletRequest request)throws Exception{
		request.getSession().invalidate();
		return "manage/login";
	}

	/**
	 * 新增用户
	 * 
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/new")
	public String _new(UserInfo userInfo, HttpServletRequest request,
			HttpServletResponse response) {
		userInfo.setCreateTime(DateUtil.datetimeToDate());
		this.userManageService.save(userInfo);
		String currentPage = (String) this.getSessionAttribute(request,
				"currentPage");
		this.sendRedirect(response, currentPage);
		return null;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		this.userManageService.deleteById(id);
		String currentPage = (String) this.getSessionAttribute(request,
				"currentPage");
		this.sendRedirect(response, currentPage);
		return null;
	}

	/**
	 * 编辑用户
	 * 
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id) {
		UserInfo user = this.userManageService.findById(id);
		model.addAttribute("userInfo", user);
		return "manage/user/alterUser";
	}

	/**
	 * 修改用户
	 * 
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(UserInfo userInfo, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = this.userManageService.findById(userInfo.getId());

		user.setUserPassword(userInfo.getUserPassword());
		user.setEmail(userInfo.getEmail());
		user.setQq(userInfo.getQq());
		user.setContent(userInfo.getContent());
		user.setSex(userInfo.getSex());

		this.userManageService.alter(user);

		String currentPage = (String) this.getSessionAttribute(request,
				"currentPage");
		System.out.println(currentPage);
		this.sendRedirect(response, currentPage);
		return null;
	}

	/**
	 * 显示所有的用户
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/list", "" })
	public String list(QueryDTO userDTO, Model model, HttpServletRequest request) {

		// fileds
		String userName = userDTO.getKey();
		int pageNo = userDTO.getPageNo();
		String order = userDTO.getOrder();
		String startDate = userDTO.getStartDate();
		String endDate = userDTO.getEndDate();
		int pageSize=userDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
		//StringBuffer condition = new StringBuffer();

		// 查询条件
		PropertyFilter pf = new PropertyFilter("userName:LIKE_S", userName);
		PropertyFilter startPf = new PropertyFilter("createTime:GT_D",
				startDate);
		PropertyFilter endPf = new PropertyFilter("createTime:LT_D", endDate);
		List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
		pfList.add(pf);
		pfList.add(startPf);
		pfList.add(endPf);

		// 设置跳转页面
		StringBuffer forwordName = new StringBuffer(this.getRoot(request)
				+ "/manage/user/list.do");

		// 获取分页跳转页面
		List<Condition> fragmentList = new ArrayList<Condition>();
	
		fragmentList.add(new Condition("key", EncodeUtil.urlEncode(userName), "匹配'" + userName
				+ "'"));
		fragmentList.add(new Condition("order", order, "排序", false));
		fragmentList
				.add(new Condition("startDate", startDate, "大于" + startDate));
		fragmentList.add(new Condition("endDate", endDate, "小于" + endDate));

		String forwarCondition = PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		// 获取排序跳转页面
		String orderCondition = PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);

		// 获取过滤查询集合
		List<Condition> filterList = PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		Page<UserInfo> page = new Page<UserInfo>(pageSize);
		// 初始化page属性值--默认id排序
		if (null == order) {
			page.setOrder("id:asc");
		} else {
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		// 查询所有用户，并放入会话
		page = this.userManageService.findAllUsers(page, pfList);
		model.addAttribute("userList", page.getResult());
		// 生成分页标签
		page.setForwordName(forwordName.toString());
		String tag = PageUtil.getTag(page);
		model.addAttribute("tag", tag);
		// 索引号
		model.addAttribute("index", page.getFirst());

		// 设置页面搜索初始值
		model.addAttribute("key", userName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		String currentPage = request.getRequestURI().toString()
				+ forwarCondition + page.getPageNo();
		
		request.getSession().setAttribute("currentPage", currentPage);
		return "manage/user/userManage";
	}

	/**
	 * 检查用户名是否存在
	 */
	@RequestMapping("/checkUserName")
	public String checkUserName(String userName, HttpServletResponse respnose) {

		boolean flag = this.userManageService.checkUserName(userName);
		try {
			if (flag) {
				respnose.getWriter().write("1");
			} else {
				respnose.getWriter().write("0");
			}
		} catch (Exception e) {

		}
		// 因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}

	/**
	 * 根据用户编辑角色
	 * 
	 * @return
	 */
	@RequestMapping("/editRole/{id}")
	public String editRoleByUser(@PathVariable int id, Model model) {
		// 获取用户
		UserInfo user = this.userManageService.findById(id);
		// 获取所有角色
		List<Role> roleList = this.roleManageService.findAllRoles();

		model.addAttribute("user", user);
		model.addAttribute("roleList", roleList);

		return "manage/user/setRole";
	}

	/**
	 * 为用户分配角色
	 */
	@RequestMapping("/updateRole")
	public String updateRoleByUser(int id, String[] roles,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取用户
		UserInfo user = this.userManageService.findById(id);
		// 获取所有角色
		Set<Role> roleSet = new HashSet<Role>();
		for (String roleId : roles) {
			Role role = new Role();
			role.setId(Integer.parseInt(roleId));
			roleSet.add(role);
		}
		// 保存角色
		user.setRoles(roleSet);
		this.userManageService.alter(user);

		String currentPage = (String) this.getSessionAttribute(request,
				"currentPage");
		this.sendRedirect(response, currentPage);
		return null;
	}

}
