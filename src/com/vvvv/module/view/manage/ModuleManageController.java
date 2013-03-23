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
import com.vvvv.module.model.Module;
import com.vvvv.module.service.IModuleManageService;
import com.vvvv.user.model.Action;
import com.vvvv.user.service.IActionManageService;

/**
 * @className:ModuleManageController.java
 * @classDescription:
 * @author:xiayingjie
 * @createTime:2011-5-25
 */
@Controller
@RequestMapping("/manage/module")
public class ModuleManageController extends BaseController{
	
	@Autowired
	protected IModuleManageService moduleManageService;
	
	
	
	/**
	 * 新增模块
	 * @param model
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/new")
	public String _new(Module module,HttpServletRequest request,HttpServletResponse response){

		module.setCreateTime(DateUtil.datetimeToDate());
		this.moduleManageService.save(module);
		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 删除模块
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		this.moduleManageService.deleteById(id);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
	}

	/**
	 * 编辑模块
	 * @param model
	 * @param module
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,@PathVariable("id") int id ){
		Module module=this.moduleManageService.findById(id);
		model.addAttribute("module", module);
		//获取模块集合
		List<Module> moduleList=this.moduleManageService.findAllMoudles();
		model.addAttribute("moduleList",moduleList);
		return "manage/module/alterModule";
	}

	/**
	*  修改模块
	 * @param module
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(Module module,HttpServletRequest request,HttpServletResponse response){
        Module module1=this.moduleManageService.findById(module.getId());
		
        //更新模块
		module1.setModuleName(module.getModuleName());
		module1.setModuleInfo(module.getModuleInfo());
		
		this.moduleManageService.alter(module1);
		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 显示所有的模块
	 * @return
	 */
	@RequestMapping(value = { "/list", "" })
	public String list(QueryDTO moduleDTO,Model model,HttpServletRequest request){
		
		//fileds
		String moduleName=moduleDTO.getKey();
		int pageNo=moduleDTO.getPageNo();
		String order=moduleDTO.getOrder();
		String startDate=moduleDTO.getStartDate();
		String endDate=moduleDTO.getEndDate();
		int pageSize=moduleDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
		
		StringBuffer condition=new StringBuffer();
		
		//查询条件
		PropertyFilter pf=new PropertyFilter("moduleName:LIKE_S",moduleName);
		PropertyFilter startPf=new PropertyFilter("createTime:GT_D",startDate);
		PropertyFilter endPf=new PropertyFilter("createTime:LT_D",endDate);
		List<PropertyFilter>pfList=new ArrayList();
		pfList.add(pf);
		pfList.add(startPf);
		pfList.add(endPf);
		
		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/module/list.do");
	
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(moduleName),"匹配'"+moduleName+"'"));
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

		Page<Module> page=new Page<Module>(pageSize);
		//初始化page属性值--默认id排序
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
	
			page.setPageNo(pageNo);

				
		//查询所有模块，并放入会话
		page=this.moduleManageService.findAllModules(page,pfList);
		model.addAttribute("moduleList", page.getResult());
		//生成分页标签
		page.setForwordName(forwordName.toString());
		String tag=PageUtil.getTag(page);
		model.addAttribute("tag", tag);
		//索引号
		model.addAttribute("index", page.getFirst());
		
		//设置页面搜索初始值
		model.addAttribute("moduleName", moduleName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		String currentPage=request.getRequestURI().toString()+forwarCondition+page.getPageNo();
		request.getSession().setAttribute("currentPage",currentPage );
	
		return "manage/module/moduleManage";
	}
	

}
