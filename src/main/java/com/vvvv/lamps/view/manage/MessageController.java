package com.vvvv.lamps.view.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.model.Contact;
import com.vvvv.lamps.model.Message;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.service.IMessageService;
import com.vvvv.lamps.view.head.HMessageController;


/**
 * @className:MessageController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:23:00 
 */
@Controller
@RequestMapping("/manage/message")
public class MessageController extends HMessageController {
	//@Autowired
	//protected IMessageService messageService;
	
	/**
	 * 留言列表
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
		pfList.add(new PropertyFilter("detail:LIKE_S",name));
		pfList.add(new PropertyFilter("createtime:GT_D",startDate));
		pfList.add(new PropertyFilter("createtime:LT_D",endDate));
		
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		fragmentList.add(new Condition("order",order,"排序",false));
		fragmentList.add(new Condition("startDate",startDate,"大于"+startDate));
		fragmentList.add(new Condition("endDate",endDate,"小于"+endDate));

		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/message/list.do");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		//初始化page属性值--默认id排序
		Page<Message> page=new Page<Message>(pageSize);
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		String hql="from Message";
		page=this.findAll(messageService,page,pfList,hql);
		model.addAttribute("messageList", page.getResult());
		
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
		return "manage/message/list";
	}
	
	/**
	 * 更新
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param message
	 * @param productId
	 * @param complanyId
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,HttpServletResponse response,
			Model model,Message message,String productId,String complanyId){
		if(StringUtils.isNotBlank(productId)){
			Product product=this.productService.findById(Integer.parseInt(productId));
			message.setProduct(product);
		}
		if(StringUtils.isNotBlank(complanyId)){
			Complany complany=this.complanyService.findById(Integer.parseInt(complanyId));
			message.setComplany(complany);
		}
		if(message!=null){
			this.messageService.alter(message);
			removeMessageCache();
		}
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 编辑
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public String edit(Model model,@PathVariable("id") int id ){
		Message message = this.messageService.findById(id);
		model.addAttribute("message", message);
		return "manage/message/alter";
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
		this.messageService.deleteById(id);
		removeMessageCache();
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
	 * 更改排序
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
		Message message=(Message)this.messageService.findById(Integer.parseInt(id));
		int order=message.getOrders();
		if (null != type && type.equals("up")) {// 上升一名 即排名-1
			message.setOrders(this.messageService.changeOrder(order - 1, order));
		} else {// 下降一名
			message.setOrders(this.messageService.changeOrder(order + 1, order));
		}
		this.messageService.alter(message);
		return gotoCurrentPage(request, response);
		
	}
	
	/**
	 * 更新状态
	 * @author wei.luo
	 * @createTime 2012-4-8
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateStatus")
	public String updateStatus(HttpServletRequest request,HttpServletResponse response) {
		String type = request.getParameter("type");
		if(StringUtils.isBlank(type)){
			type=String.valueOf(request.getAttribute("type"));
			if(StringUtils.isBlank(type)){
				return this.gotoCurrentPage(request, response);
			}
		}
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)){
			type=String.valueOf(request.getAttribute("id"));
			if(StringUtils.isBlank(id)){
				return this.gotoCurrentPage(request, response);
			}
		}
		Message message = this.messageService.findById(Integer.parseInt(id));
		if (null != message) {
			if (null != type && "true".equals(type)) {// 将状态修改为下线
				message.setStatus("下线");
				this.messageService.alter(message);
				removeMessageCache();
			}
			if (null != type && "false".equals(type)) {// 将状态修改为上线
				message.setStatus("上线");
				this.messageService.alter(message);
				removeMessageCache();
			}
		}
		return this.gotoCurrentPage(request, response);
	}

	/**
	 * @author wei.luo
	 * @createTime 2012-5-7    
	 */
	private void removeMessageCache() {
		try {
			EHCacheUtil.removeEhcache("message_cache");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量修改状态
	 * @author wei.luo
	 * @createTime 2012-2-7
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/mutiUpdateStatus")
	public String mutiUpdateStatus(HttpServletRequest request,HttpServletResponse response){
		String ids[]=request.getParameterValues("id");
		if(ids!=null ){
			for(String id:ids){
				request.setAttribute("id", id);
				this.updateStatus(request,response);
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
		Message message=this.messageService.findById(id);
		model.addAttribute("message", message);
		return "manage/message/detail";
	}
	
}
