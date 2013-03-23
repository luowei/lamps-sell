package com.vvvv.module.view.manage;

import java.util.ArrayList;
import java.util.List;

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
import com.vvvv.module.service.IMenuManageService;

/**
 * @className:MenuManageController.java
 * @classDescription:菜单管理类
 * @author:xiayingjie
 * @createTime:2011-5-25
 */
@Controller
@RequestMapping("/manage/menu")
public class MenuManageController extends BaseController{
	
	@Autowired
	protected IMenuManageService menuManageService;
	/**
	 * 去新增菜单
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/toAdd/{id}")
	public String toAdd(@PathVariable int id,Model model){

		List<Menu>menuList=this.menuManageService.findAllMenus();
		model.addAttribute("menuList",menuList);		
		model.addAttribute("checkMenuId",id);		
		
		return "manage/menu/addMenu";
	}
	/**
	 * 新增菜单
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/new")
	public String _new(Menu menu,HttpServletRequest request,HttpServletResponse response){
		menu.setCreateTime(DateUtil.datetimeToDate());
		this.menuManageService.save(menu);		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		this.menuManageService.deleteById(id);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
	}

	/**
	 * 编辑菜单
	 * @param model
	 * @param menu
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,@PathVariable("id") int id ){
		Menu menu=this.menuManageService.findById(id);
		model.addAttribute("menuInfo", menu);
		//获取模块集合
		List<Menu>menuList=this.menuManageService.findAllMenus();
		model.addAttribute("menuList",menuList);	
		return "manage/menu/alterMenu";
	}
	/**
	 * 修改菜单
	 * @param model
	 * @param menu
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(Menu menu,HttpServletRequest request,HttpServletResponse response){
        Menu menu1=this.menuManageService.findById(menu.getId());
		
        menu1.setMenuName(menu.getMenuName());
		menu1.setUrl(menu.getUrl());
		menu1.setImageUrl(menu.getImageUrl());
		menu1.setParentId(menu.getParentId());
		menu1.setOrders(menu.getOrders());
		
		this.menuManageService.alter(menu1);
		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 显示所有的菜单
	 * @return
	 */
	@RequestMapping(value = { "/list", "" })
	public String list(QueryDTO menuDTO,Model model,HttpServletRequest request){
		
		//fileds
		String menuName=menuDTO.getKey();
		int pageNo=menuDTO.getPageNo();
		String order=menuDTO.getOrder();
		String startDate=menuDTO.getStartDate();
		String endDate=menuDTO.getEndDate();
		int pageSize=menuDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
		StringBuffer condition=new StringBuffer();
		
		//查询条件
		PropertyFilter pf=new PropertyFilter("menuName:LIKE_S",menuName);
		PropertyFilter startPf=new PropertyFilter("createTime:GT_D",startDate);
		PropertyFilter endPf=new PropertyFilter("createTime:LT_D",endDate);
		List<PropertyFilter>pfList=new ArrayList();
		pfList.add(pf);
		pfList.add(startPf);
		pfList.add(endPf);
		
		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/menu/list.do");
	

		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(menuName),"匹配'"+menuName+"'"));
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

		Page<Menu> page=new Page<Menu>(pageSize);
		//初始化page属性值--按时间排序
		if(null==order){
			page.setOrder("createTime:asc");
		}else{
			page.setOrder(order);
		}
	
		page.setPageNo(pageNo);
	
				
		//查询所有菜单，并放入会话
		page=this.menuManageService.findAllMenus(page,pfList);
		model.addAttribute("menuList", page.getResult());

		//生成分页标签
		page.setForwordName(forwordName.toString());
		String tag=PageUtil.getTag(page);
		model.addAttribute("tag", tag);
		//索引号
		model.addAttribute("index", page.getFirst());
		
		//设置页面搜索初始值
		model.addAttribute("menuName", menuName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		String currentPage=request.getRequestURI().toString()+forwarCondition+page.getPageNo();
		request.getSession().setAttribute("currentPage",currentPage );
	
		return "manage/menu/menuManage";
	}
}
