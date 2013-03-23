package com.vvvv.user.view.manage;

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
import com.vvvv.module.model.Module;
import com.vvvv.module.service.IModuleManageService;
import com.vvvv.user.model.Action;
import com.vvvv.user.service.IActionManageService;

/**
 * @className:ActionManageController.java
 * @classDescription:权限操作类
 * @author:xiayingjie
 * @createTime:2011-5-25
 */
@Controller
@RequestMapping("/manage/action")
public class ActionManageController extends BaseController{
	
	@Autowired
	protected IActionManageService actionManageService;
	@Autowired
	protected IModuleManageService moduleManageService;
	
	/**
	 * 去新增权限
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public String toAdd(Model model){
		//获取模块集合
		List<Module> moduleList=this.moduleManageService.findAllMoudles();
		model.addAttribute("moduleList",moduleList);	
		
		return "manage/action/addAction";
	}
	/**
	 * 新增权限
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/new")
	public String _new(Action action,int moduleId,HttpServletRequest request,HttpServletResponse response){
		action.setCreateTime(DateUtil.datetimeToDate());
		Module module=new Module();
		module.setId(moduleId);
		action.setModule(module);
		this.actionManageService.save(action);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 删除权限
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		this.actionManageService.deleteById(id);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
	}

	/**
	 * 编辑权限
	 * @param model
	 * @param action
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,@PathVariable("id") int id ){
		Action action=this.actionManageService.findById(id);
		model.addAttribute("action", action);
		//获取模块集合
		List<Module> moduleList=this.moduleManageService.findAllMoudles();
		model.addAttribute("moduleList",moduleList);
		return "manage/action/alterAction";
	}
	/**
	 * 修改权限
	 * @param model
	 * @param action
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(Action action,int moduleId,HttpServletRequest request,HttpServletResponse response){
        Action action1=this.actionManageService.findById(action.getId());
		
        action1.setActionName(action.getActionName());
        action1.setPath(action.getPath());
		
		Module module=new Module();
		module.setId(moduleId);
		action1.setModule(module);
		action1.setOrders(action.getOrders());
		
		this.actionManageService.alter(action1);
		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 显示所有的权限
	 * @return
	 */
	@RequestMapping(value = { "/list", "" })
	public String list(QueryDTO actionDTO,String moduleId,Model model,HttpServletRequest request){
		
		//fileds
		String actionName=actionDTO.getKey();
		int pageNo=actionDTO.getPageNo();
		String order=actionDTO.getOrder();
		String startDate=actionDTO.getStartDate();
		String endDate=actionDTO.getEndDate();
		int pageSize=actionDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
		StringBuffer condition=new StringBuffer();
		
		//查询条件
		PropertyFilter pf=new PropertyFilter("actionName:LIKE_S",actionName);
		PropertyFilter modulePf=new PropertyFilter("moudleid:EQ_I",moduleId);
		PropertyFilter startPf=new PropertyFilter("createTime:GT_D",startDate);
		PropertyFilter endPf=new PropertyFilter("createTime:LT_D",endDate);
		List<PropertyFilter>pfList=new ArrayList();
		pfList.add(pf);
		pfList.add(modulePf);
		pfList.add(startPf);
		pfList.add(endPf);
		
		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/action/list.do");
	
		String moduleName=null;
		if(null!=moduleId && !"".equals(moduleId)){
			moduleName=this.moduleManageService.findById(Integer.parseInt(moduleId)).getModuleName();
		}
		
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(actionName),"匹配'"+actionName+"'"));
		fragmentList.add(new Condition("moudleid",moduleId,"'"+moduleName+"'"));
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

		Page<Action> page=new Page<Action>(pageSize);
		//初始化page属性值--按时间排序
		if(null==order){
			page.setOrder("createTime:asc");
		}else{
			page.setOrder(order);
		}
	
			page.setPageNo(pageNo);
	
				
		//查询所有权限，并放入会话
		page=this.actionManageService.findAllActions(page,pfList);
		model.addAttribute("actionList", page.getResult());
		List<Module>moduleList=this.moduleManageService.findAllMoudles();
		model.addAttribute("moduleList", moduleList);
		//生成分页标签
		page.setForwordName(forwordName.toString());
		String tag=PageUtil.getTag(page);
		model.addAttribute("tag", tag);
		//索引号
		model.addAttribute("index", page.getFirst());
		
		//设置页面搜索初始值
		model.addAttribute("moduleId", moduleId);
		model.addAttribute("actionName", actionName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		String currentPage=request.getRequestURI().toString()+forwarCondition+page.getPageNo();
		request.getSession().setAttribute("currentPage",currentPage );
	
		return "manage/action/actionManage";
	}


}
