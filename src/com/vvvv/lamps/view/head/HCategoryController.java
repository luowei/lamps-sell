package com.vvvv.lamps.view.head;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.BaseController;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Category;

/**
 * @className:HCategoryController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:32:35 
 */
@Controller
@RequestMapping("/head/category")
public class HCategoryController extends BaseController<Category>{
	/**
	 * 初始类别列表页
	 * @author wei.luo
	 * @createTime 2012-4-9
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
		int pageSize=queryDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		String order=queryDTO.getOrder();
		String startDate=queryDTO.getStartDate();
		String endDate=queryDTO.getEndDate();
		String parentId=request.getParameter("parentId");
		
		//查询条件
		List<PropertyFilter> pfList=new ArrayList<PropertyFilter>();
		pfList.add(new PropertyFilter("name:LIKE_S",name));
		pfList.add(new PropertyFilter("createtime:GT_D",startDate));
		pfList.add(new PropertyFilter("createtime:LT_D",endDate));
		pfList.add(new PropertyFilter("parentId:EQ_I",parentId));
		
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		fragmentList.add(new Condition("order",order,"排序",false));
		fragmentList.add(new Condition("startDate",startDate,"大于"+startDate));
		fragmentList.add(new Condition("endDate",endDate,"小于"+endDate));

		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/index");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		Page<Category> page=new Page<Category>(pageSize);
		//List<Category> pCategoryList=new LinkedList<Category>();
		//List<Category> subCategoryList=new LinkedList<Category>();
		List<Category> pCategoryList=(List<Category>)EHCacheUtil.getValue("category_cache", "pCategoryList");
		List<Category> subCategoryList=(List<Category>)EHCacheUtil.getValue("category_cache", "subCategoryList");
		List<Category> categoryList=(List<Category>)EHCacheUtil.getValue("category_cache", "categoryListInPage");
		
		if(pCategoryList==null || subCategoryList==null || categoryList==null){
			//初始化page属性值--默认id排序
			if(null==order){
				page.setOrder("id:asc");
			}else{
				page.setOrder(order);
			}
			page.setPageNo(pageNo);
			String hql="from Category";
			page=this.findAll(categoryService,page,pfList,hql);
			categoryList=this.categoryService.findList("from Category");
			
			pCategoryList=new LinkedList<Category>();
			subCategoryList=new LinkedList<Category>();
			for(Category cate:categoryList){
				if(cate.getParentId()==1 && cate.getId()!=1){
					pCategoryList.add(cate);
				}
				if(cate.getParentId()>1){
					subCategoryList.add(cate);
				}
			}
		}
		try {
			EHCacheUtil.setValue("category_cache", "pCategoryList", pCategoryList);
			EHCacheUtil.setValue("category_cache", "subCategoryList", subCategoryList);
			EHCacheUtil.setValue("category_cache", "categoryListInPage",page.getResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//page.setResult(categoryList);
		//pCategoryList.addAll();
		//subCategoryList.addAll();
		model.addAttribute("pCategoryList", pCategoryList);
		model.addAttribute("subCategoryList", subCategoryList);
		model.addAttribute("categorytList", page.getResult());
		
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
		return "/category";
	}
}
