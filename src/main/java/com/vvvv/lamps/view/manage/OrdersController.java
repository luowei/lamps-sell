package com.vvvv.lamps.view.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.common.FileUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Orders;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.view.Global;
import com.vvvv.lamps.view.head.HOrdersController;


/**
 * @className:OrdersController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:24:23 
 */
@Controller
@RequestMapping("/manage/orders")
public class OrdersController extends HOrdersController {
	
	@RequestMapping(value = "/olist")
	public String olist(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO,String userId){
		
		//String name=queryDTO.getKey();
		int pageNo=queryDTO.getPageNo();
		String order=queryDTO.getOrder();
		String startDate=queryDTO.getStartDate();
		String endDate=queryDTO.getEndDate();
		int pageSize=queryDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
//		if(StringUtils.isNotBlank(categoryId)){
//			category = this.categoryService.findById(Integer.parseInt(categoryId));
//		}
		
		//查询条件
		List<PropertyFilter> pfList=new ArrayList<PropertyFilter>();
		//pfList.add(new PropertyFilter("name:LIKE_S",name));
		pfList.add(new PropertyFilter("createtime:GT_D",startDate));
		pfList.add(new PropertyFilter("createtime:LT_D",endDate));
		pfList.add(new PropertyFilter("user_id:EQ_I",userId));
		
//		if(StringUtils.isNotBlank(categoryId)){
//			String chql="(select Prodcut from Category c where c.id='"+categoryId+"')";
//			pfList.add(new PropertyFilter("Product.id:EQ_I",chql));
//		}
		
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		//fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		fragmentList.add(new Condition("categoryId",userId,"",false));
		fragmentList.add(new Condition("order",order,"排序",false));
		fragmentList.add(new Condition("startDate",startDate,"大于"+startDate));
		fragmentList.add(new Condition("endDate",endDate,"小于"+endDate));

		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/orders/olist.do");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		//初始化page属性值--默认id排序
		Page<Orders> page=new Page<Orders>(pageSize);
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		String hql=" from Orders ";
		if(StringUtils.isNotBlank(userId)){
			//page=this.productService.findByComplanyId(page,complanyId);
			List<Orders> ordersList=new ArrayList<Orders>();
			ordersList.addAll(
					this.userService.findById(Integer.parseInt(userId)).getOrdersSet());
			page.setTotalCount(ordersList.size());
			page.setResult(ordersList);
		}else{
			page=this.ordersService.findAll(page,pfList,hql);
		}
		model.addAttribute("ordersList", page.getResult());
		
		// 生成分页标签
		page.setForwordName(forwordName.toString());
		String tag = PageUtil.getTag(page);
		model.addAttribute("tag", tag);
		// 索引号
		model.addAttribute("index", page.getFirst());

		// 设置页面搜索初始值
		//model.addAttribute("key", name);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		//model.addAttribute("categoryId", categoryId);
		//model.addAttribute("complanyId", complanyId);
		
		String currentPage = request.getRequestURI().toString()
			+ forwarCondition + page.getPageNo();
		request.getSession().setAttribute("currentPage", currentPage);
		return "manage/orders/list";
	}
	
	
	@RequestMapping(value="/delete/{id}")
	public String delete(HttpServletRequest request,HttpServletResponse response,
			@PathVariable int id) {
		Orders orders=this.ordersService.findById(id);
		this.ordersService.deleteById(id);
		try {
			EHCacheUtil.removeElment("orders_cache", "ordersList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
	}
	
	@RequestMapping(value="/detail")
	public String detail(Model model,String id){
		Orders orders=this.ordersService.findById(Integer.parseInt(id));
		model.addAttribute("orders", orders);
		return "manage/orders/detail";
	}
}
