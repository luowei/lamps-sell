package com.vvvv.lamps.view.manage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.model.Contact;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.service.IComplanyService;
import com.vvvv.lamps.service.IContactService;
import com.vvvv.lamps.view.head.HContactController;


/**
 * @className:ContactController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:22:06 
 */
@Controller
@RequestMapping("/manage/contact")
public class ContactController extends HContactController{
	//@Autowired
	//protected IContactService contactService;
	//@Autowired
	//protected IComplanyService complanyService;
	
	/**
	 * 联系列表
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param queryDTO
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO){
		
		String name=queryDTO.getKey();
		int pageNo=queryDTO.getPageNo();
		String order=queryDTO.getOrder();
		String startDate=queryDTO.getStartDate();
		String endDate=queryDTO.getEndDate();
		int pageSize=queryDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
		//查询条件
		List<PropertyFilter> pfList=new ArrayList<PropertyFilter>();
		pfList.add(new PropertyFilter("name:LIKE_S",name));
		pfList.add(new PropertyFilter("createtime:GT_D",startDate));
		pfList.add(new PropertyFilter("createtime:LT_D",endDate));
		
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		fragmentList.add(new Condition("order",order,"排序",false));
		fragmentList.add(new Condition("startDate",startDate,"大于"+startDate));
		fragmentList.add(new Condition("endDate",endDate,"小于"+endDate));

		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/contact/list.do");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		//初始化page属性值--默认id排序
		Page<Contact> page=new Page<Contact>(pageSize);
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		String hql="from Contact";
		page=this.findAll(contactService,page,pfList,hql);
		model.addAttribute("contactList", page.getResult());
		
		// 生成分页标签
		page.setForwordName(forwordName.toString());
		String tag = PageUtil.getTag(page);
		model.addAttribute("tag", tag);
		// 索引号
		model.addAttribute("index", page.getFirst());

		// 设置页面搜索初始值
		model.addAttribute("key", name);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		String currentPage = request.getRequestURI().toString()
			+ forwarCondition + page.getPageNo();
		request.getSession().setAttribute("currentPage", currentPage);
		return "manage/contact/list";
	}
	
	/**
	 * 添加
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param contact
	 * @param complanyId
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,HttpServletResponse response,
			Model model,Contact contact,String complanyId){
		if(contact!=null && StringUtils.isNotBlank(complanyId)){
			Complany complany=this.complanyService.findById(Integer.parseInt(complanyId));
			contact.setComplany(complany);
			this.contactService.save(contact);
		}
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 更新
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param contact
	 * @param complanyId
	 * @param qq
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,HttpServletResponse response,
			Model model,Contact contact,String complanyId,String qq){
		if(contact!=null){
			//Complany complany=this.complanyService.findById(Integer.parseInt(complanyId));
			//contact.setComplany(complany);
			//this.contactService.deleteById(contact.getId());
			//this.contactService.save(contact);
			this.contactService.alter(contact);
			this.contactService.flush();
		}
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 初始添加
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAdd")
	public String toAdd(HttpServletRequest request,HttpServletResponse response,
			Model model){
		List<Complany> complanyList = new LinkedList<Complany>();
		complanyList = this.complanyService.findList("select cpl from Complany cpl where cpl not in(" +
				"select c.complany from Contact c where c.complany.name is not null)");
		model.addAttribute("complanyList", complanyList);
		return "manage/contact/add";
	}
	
	/**
	 * 初始编辑
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,@PathVariable("id") int id ){
		Contact contact = this.contactService.findById(id);
		List<Complany> complanyList = new LinkedList<Complany>();
		//complanyList = this.complanyService.findList("from Complany cpl where cpl.contact is not null");
		//model.addAttribute("complanyList", complanyList);
		model.addAttribute("contact", contact);
		return "manage/contact/alter";
	}
	
	/**
	 * 修改排序
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/changeOrder")
	public String changeOrder(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		String type = request.getParameter("type");
		Contact contact=(Contact)this.contactService.findById(Integer.parseInt(id));
		int order=contact.getOrders();
		if (null != type && type.equals("up")) {// 上升一名 即排名-1
			contact.setOrders(this.contactService.changeOrder(order - 1, order));
		} else {// 下降一名
			contact.setOrders(this.contactService.changeOrder(order + 1, order));
		}
		this.contactService.alter(contact);
		return gotoCurrentPage(request, response);
		
	}
	
	/**
	 * 删除
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public String delete(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) {
		this.contactService.deleteById(id);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 批量删除
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/mutiDelete", ""})
	public String mutiDelete(HttpServletRequest request,HttpServletResponse response){
		String ids[]=request.getParameterValues("id");
		if(ids!=null ){
			for(String id:ids){
				this.delete(request,response,Integer.parseInt(id));
			}
		}
		return this.gotoCurrentPage(request, response);
	}
	
	/**
	 * 详细信息
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail/{id}")
	public String detail(Model model,@PathVariable int id){
		Contact contact=this.contactService.findById(id);
		model.addAttribute("contact", contact);
		return "manage/contact/detail";
	}
}
