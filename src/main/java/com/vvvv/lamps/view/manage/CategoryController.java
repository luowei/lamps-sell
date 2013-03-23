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

import com.vvvv.common.service.IBaseService;
import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.DateUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.BaseModel;
import com.vvvv.lamps.model.Category;
import com.vvvv.lamps.service.ICategoryService;
import com.vvvv.lamps.view.head.HCategoryController;


/**
 * @className:CategoryController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:21:04 
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryController extends HCategoryController {
	
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
		try {
			EHCacheUtil.removeAllElment("category_cache");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/category/list.do");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);
		
		//子类别
		String subHql=" from Category where parentId > '1' ";
		String productHql=" from Product p where  p.category.id=";
		List<Category> subCategoryList = this.categoryService.getSubCategory(subHql,productHql);
		//父类别
		String phql=" from Category where parentId='1' and id!='1' ";
		List<Category>  pCategoryList = this.categoryService.getPCategory(subCategoryList,phql);

		//初始化page属性值--默认id排序
		Page<Category> page=new Page<Category>(pageSize);
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		String hql="from Category";
		page=this.findAll(categoryService,page,pfList,hql);
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
		return "manage/category/list";
	}
	
	/**
	 * 添加
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,HttpServletResponse response,
			Model model,Category category){
		if(category!=null ){
			category.setCreateTime(DateUtil.datetimeToDate());
			this.categoryService.save(category);
			removeCategoryCache();
		}
		return gotoCurrentPage(request, response);
	}

	/**
	 * @author wei.luo
	 * @createTime 2012-5-7    
	 */
	private void removeCategoryCache() {
		try {
			EHCacheUtil.removeEhcache("category_cache");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,HttpServletResponse response,
			Model model,Category category){
		if(category!=null ){
			this.categoryService.alter(category);
			removeCategoryCache();
		}
		return gotoCurrentPage(request, response);
	}
	
	/**
	 * 更新
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public String delete(HttpServletRequest request,HttpServletResponse response,/*String id*/@PathVariable int id) {
		this.categoryService.deleteById(id);
		removeCategoryCache();
		return gotoCurrentPage(request, response);
	}
	
	/**
	 * 初始添加
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public String toAdd(Model model){
		List<Category> bigCategoryList = this.categoryService.findBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		return "manage/category/add";
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
	public String edit(Model model,/*String id*/@PathVariable("id") int id){
		Category category = (Category)this.categoryService.findById(id);
		model.addAttribute("category", category);
		List<Category> bigCategoryList = this.categoryService.findBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		return "manage/category/alter";
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
		Category category=(Category)this.categoryService.findById(Integer.parseInt(id));
		int order=category.getOrders();
		if (null != type && type.equals("up")) {// 上升一名 即排名-1
			category.setOrders(this.categoryService.changeOrder(order - 1, order));
		} else {// 下降一名
			category.setOrders(this.categoryService.changeOrder(order + 1, order));
		}
		this.categoryService.alter(category);
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
		Category category = this.categoryService.findById(Integer.parseInt(id));
		if (null != category) {
			if (null != type && "true".equals(type)) {// 将状态修改为下线
				category.setStatus("下线");
				this.categoryService.alter(category);
				removeCategoryCache();
			}
			if (null != type && "false".equals(type)) {// 将状态修改为上线
				category.setStatus("上线");
				this.categoryService.alter(category);
				removeCategoryCache();
			}
		}
		return this.gotoCurrentPage(request, response);
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
	 * 批量删除
	 * @author wei.luo
	 * @createTime 2012-2-7
	 * @param request
	 * @param response
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
}
