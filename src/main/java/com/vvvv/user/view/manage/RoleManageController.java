package com.vvvv.user.view.manage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.vvvv.module.model.Module;
import com.vvvv.module.service.IMenuManageService;
import com.vvvv.module.service.IModuleManageService;
import com.vvvv.user.model.Action;
import com.vvvv.user.model.Role;
import com.vvvv.user.service.IRoleManageService;

/**
 * @className:RoleManageController.java
 * @classDescription:角色管理
 * @author:xiayingjie
 * @createTime:2011-5-25
 */
@Controller
@RequestMapping("/manage/role")
public class RoleManageController extends BaseController{
	
	@Autowired
	protected IRoleManageService roleManageService;
	@Autowired
	protected IModuleManageService moduleManageService;
	@Autowired
	protected IMenuManageService menuManageService;
	/**
	 * 新增角色
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/new")
	public String _new(Role role,HttpServletRequest request,HttpServletResponse response){
		role.setCreateTime(DateUtil.datetimeToDate());
		this.roleManageService.save(role);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		this.roleManageService.deleteById(id);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
	}

	/**
	 * 编辑角色
	 * @param model
	 * @param Role
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,@PathVariable("id") int id ){
		Role role=this.roleManageService.findById(id);
		model.addAttribute("role", role);
		return "manage/role/alterRole";
	}
	/**
	 * 修改角色
	 * @param model
	 * @param Role
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(Role role,HttpServletRequest request,HttpServletResponse response){
		 //修改角色
        Role role1=this.roleManageService.findById(role.getId());
		role1.setRoleName(role.getRoleName());
		role1.setRoleInfo(role.getRoleInfo());	
		this.roleManageService.alter(role1);
		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 显示所有的角色
	 * @return
	 */
	@RequestMapping(value = { "/list", "" })
	public String list(QueryDTO roleDTO,Model model,HttpServletRequest request){
		
		//fileds
		String roleName=roleDTO.getKey();
		int pageNo=roleDTO.getPageNo();
		String order=roleDTO.getOrder();
		String startDate=roleDTO.getStartDate();
		String endDate=roleDTO.getEndDate();
		int pageSize=roleDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
		StringBuffer condition=new StringBuffer();
		
		//查询条件
		PropertyFilter pf=new PropertyFilter("roleName:LIKE_S",roleName);
		PropertyFilter startPf=new PropertyFilter("createTime:GT_D",startDate);
		PropertyFilter endPf=new PropertyFilter("createTime:LT_D",endDate);
		List<PropertyFilter>pfList=new ArrayList();
		pfList.add(pf);
		pfList.add(startPf);
		pfList.add(endPf);
		
		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/role/list.do");
	
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(roleName),"匹配'"+roleName+"'"));
		fragmentList.add(new Condition("order",order,"排序",false));
		fragmentList.add(new Condition("startDate",startDate,"大于"+startDate));
		fragmentList.add(new Condition("endDate",endDate,"小于"+endDate));

		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		Page<Role> page=new Page<Role>(pageSize);
		//初始化page属性值--默认id排序
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
			page.setPageNo(pageNo);
				
		//查询所有权限，并放入会话
		page=this.roleManageService.findAllRoles(page,pfList);
		model.addAttribute("roleList", page.getResult());
		//生成分页标签
		page.setForwordName(forwordName.toString());
		String tag=PageUtil.getTag(page);
		model.addAttribute("tag", tag);
		//索引号
		model.addAttribute("index", page.getFirst());
		
		//设置页面搜索初始值
		model.addAttribute("key", roleName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		String currentPage=request.getRequestURI().toString()+forwarCondition+page.getPageNo();
		request.getSession().setAttribute("currentPage",currentPage );
		
		return "manage/role/roleManage";
	}
	
	
	/**
	 * 去为角色分配权限
	 * @return
	 */
	@RequestMapping(value="/editAction/{id}")
	public String editActionByRole(@PathVariable int id,Model model){
		//获取角色
		Role role=this.roleManageService.findById(id);
		//获取所有模块
		List<Module> moduleList=this.moduleManageService.findAllMoudles();
		model.addAttribute("role", role);
		model.addAttribute("moduleList", moduleList);
		return "manage/role/setAction";
	}
	
	/**
	 * 修改角色权限
	 */
	@RequestMapping(value="/updateAction")
	public String updateActionByRole(int id,String[]actions,HttpServletRequest request,HttpServletResponse response) {
		//获取角色
		Role role=this.roleManageService.findById(id);
		//获取所有权限
		Set actionSet=new HashSet();
		for(String actionId:actions){
		    Action action=new Action();
		    action.setId(Integer.parseInt(actionId));
		    actionSet.add(action);
		}
		// 保存角色
		role.setActions(actionSet);
		this.roleManageService.alter(role);

		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
	}
	/**
	 * 去为角色分配菜单
	 * @return
	 */
	@RequestMapping(value="/editMenu/{id}")
	public String editMenuByRole(@PathVariable int id,Model model){
		//获取角色
		Role role=this.roleManageService.findById(id);
		//获取所有菜单
		List<Menu> menuList=this.menuManageService.findAllMenus();
		
		model.addAttribute("role", role);
		model.addAttribute("menuList", menuList);
		
		return "manage/role/setMenu";
	}
	/**
	 * 为角色分配菜单
	 */
	@RequestMapping(value="/updateMenu")
	public String updateMenuByRole(int id,String[]menus,HttpServletRequest request,HttpServletResponse response) {
		//获取角色
		Role role=this.roleManageService.findById(id);
		//获取所有权限
		Set<Menu> menuSet=new HashSet();
		for(String menuId:menus){
			Menu menu=new Menu();
			menu.setId(Integer.parseInt(menuId));
		    menuSet.add(menu);
		}
		// 保存角色
		role.setMenus(menuSet);
		this.roleManageService.alter(role);
		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}

}
