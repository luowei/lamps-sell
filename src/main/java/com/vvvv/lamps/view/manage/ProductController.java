package com.vvvv.lamps.view.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.log4j.lf5.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.DateUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.common.FileUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Category;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.model.Stock;
import com.vvvv.lamps.service.ICategoryService;
import com.vvvv.lamps.service.IProductService;
import com.vvvv.lamps.view.Global;
import com.vvvv.lamps.view.head.HProductController;

/**
 * @className:ProductController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Controller
@RequestMapping("/manage/product")
public class ProductController extends HProductController{
	
//	@Autowired
//	protected IProductService productService;
//	@Autowired
//	protected ICategoryService categoryService;
	
	/**
	 * 产品列表
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param queryDTO
	 * @param categoryId
	 * @param categoryName
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO,String categoryId,String categoryName,
			String complanyId){
		
		String name=queryDTO.getKey();
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
		pfList.add(new PropertyFilter("name:LIKE_S",name));
		pfList.add(new PropertyFilter("createtime:GT_D",startDate));
		pfList.add(new PropertyFilter("createtime:LT_D",endDate));
		pfList.add(new PropertyFilter("category_id:EQ_I",categoryId));
		
//		if(StringUtils.isNotBlank(categoryId)){
//			String chql="(select Prodcut from Category c where c.id='"+categoryId+"')";
//			pfList.add(new PropertyFilter("Product.id:EQ_I",chql));
//		}
		
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		fragmentList.add(new Condition("categoryId",categoryId,"",false));
		fragmentList.add(new Condition("complanyId",complanyId,"",false));
		fragmentList.add(new Condition("order",order,"排序",false));
		fragmentList.add(new Condition("startDate",startDate,"大于"+startDate));
		fragmentList.add(new Condition("endDate",endDate,"小于"+endDate));

		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/product/list.do");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		//初始化page属性值--默认id排序
		Page<Product> page=new Page<Product>(pageSize);
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		String hql=" from Product ";
		if(StringUtils.isNotBlank(complanyId)){
			//page=this.productService.findByComplanyId(page,complanyId);
			List<Product> productList=new ArrayList<Product>();
			productList.addAll(
					this.complanyService.findById(Integer.parseInt(complanyId)).getProductSet());
			page.setTotalCount(productList.size());
			page.setResult(productList);
		}else{
			page=this.productService.findAll(page,pfList,hql);
		}
		model.addAttribute("productList", page.getResult());
		
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
		//model.addAttribute("categoryId", categoryId);
		//model.addAttribute("complanyId", complanyId);
		
		String currentPage = request.getRequestURI().toString()
			+ forwarCondition + page.getPageNo();
		request.getSession().setAttribute("currentPage", currentPage);
		return "manage/product/list";
	}
	
	/**
	 * 更新
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param product
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(DefaultMultipartHttpServletRequest request,HttpServletResponse response,
			Model model,Product product,String categoryId){
		//Product productOld=this.productService.findById(product.getId());
		if(product!=null && StringUtils.isNotBlank(categoryId)){
			MultipartFile smallImg=request.getFile("smallFile");
			MultipartFile bigImg=request.getFile("bigFile");
			String smallImgPath=null;
			String bigImgPath=null;
			String root=this.getRoot(request);
			if(bigImg!=null && smallImg!=null){
				String smallRealDir = this.getRealPath(request, Global.PRODUCT_SMALL_IMG);
				String bigRealDir = this.getRealPath(request, Global.PRODUCT_BIG_IMG);
				smallImgPath=FileUtil.upload(smallImg, smallRealDir, Global.PRODUCT_SMALL_IMG);
				bigImgPath=FileUtil.upload(bigImg, bigRealDir, Global.PRODUCT_BIG_IMG);
			}
			Category category=(Category)this.categoryService.findById(Integer.parseInt(categoryId));
			product.setCategory(category);
			//product.setSmallImg(productOld.getSmallImg());
			if(StringUtils.isNotBlank(smallImgPath)){
				product.setSmallImg(root+"/"+smallImgPath);
			}
			//product.setBigImg(productOld.getBigImg());
			if(StringUtils.isNotBlank(bigImgPath)){
				product.setBigImg(root+"/"+bigImgPath);
			}
			product.setCreatetime(DateUtil.datetimeToDate());
			this.productService.alter(product);
			removeProdcutCache();
		}
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 添加
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param product
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(DefaultMultipartHttpServletRequest request,HttpServletResponse response,
			Model model,Product product,String categoryId){
		if(product!=null && StringUtils.isNotBlank(categoryId)){
			MultipartFile smallImg=request.getFile("smallFile");
			MultipartFile bigImg=request.getFile("bigFile");
			String smallImgPath=null;
			String bigImgPath=null;
			String root=this.getRoot(request);
			if(bigImg!=null && smallImg!=null){
				String smallRealDir = this.getRealPath(request, Global.PRODUCT_SMALL_IMG);
				String bigRealDir = this.getRealPath(request, Global.PRODUCT_BIG_IMG);
				smallImgPath=FileUtil.upload(smallImg, smallRealDir, Global.PRODUCT_SMALL_IMG);
				bigImgPath=FileUtil.upload(bigImg, bigRealDir, Global.PRODUCT_BIG_IMG);
			}
			Category category=(Category)this.categoryService.findById(Integer.parseInt(categoryId));
			product.setCategory(category);
			if(StringUtils.isNotBlank(smallImgPath)){
				product.setSmallImg(root+"/"+smallImgPath);
			}
			if(StringUtils.isNotBlank(bigImgPath)){
				product.setBigImg(root+"/"+bigImgPath);
			}
			product.setCreatetime(DateUtil.datetimeToDate());
			this.productService.save(product);
			removeProdcutCache();
		}
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
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
	public String delete(HttpServletRequest request,HttpServletResponse response,
			@PathVariable int id) {
		Product product=this.productService.findById(id);
		String root=this.getRoot(request);
		if(product!=null){
			String smallImg=this.getRealPath(request, StringUtils.substringAfter(product.getSmallImg(),root));
			String bigImg=this.getRealPath(request, StringUtils.substringAfter(product.getBigImg(),root));
			try {
				if(smallImg.contains(Global.PRODUCT_SMALL_IMG.replace("/", File.separator))){
					FileUtil.delete(smallImg);
				}
				if(bigImg.contains(Global.PRODUCT_BIG_IMG.replace("/", File.separator))){
					FileUtil.delete(bigImg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.productService.deleteById(id);
		removeProdcutCache();
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
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
//		List<Category> bigCategoryList=this.categoryService.findList("from Category where parent_id = '1'");
//		model.addAttribute("bigCategoryList", bigCategoryList);
		List<Category> categoryList=this.categoryService.findList("from Category where parent_id <> '1'");
		model.addAttribute("categoryList", categoryList);
		return "manage/product/add";
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
		Product product=this.productService.findById(id);
		model.addAttribute("product", product);
		List<Category> categoryList=this.categoryService.findList("from Category");
		model.addAttribute("categoryList", categoryList);
		return "manage/product/alter";
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
		Product product=(Product)this.productService.findById(Integer.parseInt(id));
		int order=product.getOrders();
		if (null != type && type.equals("up")) {// 上升一名 即排名-1
			product.setOrders(this.productService.changeOrder(order - 1, order));
		} else {// 下降一名
			product.setOrders(this.productService.changeOrder(order + 1, order));
		}
		this.productService.alter(product);
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
		Product product = this.productService.findById(Integer.parseInt(id));
		if (null != product) {
			if (null != type && "true".equals(type)) {// 将状态修改为下线
				product.setStatus("下线");
				this.productService.alter(product);
				removeProdcutCache();
			}
			if (null != type && "false".equals(type)) {// 将状态修改为上线
				product.setStatus("上线");
				this.productService.alter(product);
				removeProdcutCache();
			}
		}
		return this.gotoCurrentPage(request, response);
	}

	/**
	 * @author wei.luo
	 * @createTime 2012-5-7    
	 */
	private void removeProdcutCache() {
		try {
			EHCacheUtil.removeEhcache("product_cache");
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
	
	/**
	 * 详细信息
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String detail(Model model,String id){
		Product product=this.productService.findById(Integer.parseInt(id));
		model.addAttribute("product", product);
		return "manage/product/detail";
	}
}
