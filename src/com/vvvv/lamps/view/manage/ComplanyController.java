package com.vvvv.lamps.view.manage;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.common.FileUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.view.Global;
import com.vvvv.lamps.view.head.HComplanyController;


/**
 * @className:ComplanyController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:21:25 
 */
@Controller
@RequestMapping("/manage/complany")
public class ComplanyController extends HComplanyController{
	//@Autowired
	//protected IComplanyService complanyService;
	
	/**
	 * 公司列表
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
			Model model,QueryDTO queryDTO,String productId){
		
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
		fragmentList.add(new Condition("productId",productId,"",false));

		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/complany/list.do");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		//初始化page属性值--默认id排序
		Page<Complany> page=new Page<Complany>(pageSize);
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		String hql="from Complany";
		if(StringUtils.isNotBlank(productId)){
			List<Complany> complanyList=new ArrayList<Complany>();
			complanyList.addAll(this.productService.findById(Integer.parseInt(productId)).getComplanySet());
			page.setTotalCount(complanyList.size());
			page.setResult(complanyList);
		}else{
			page=this.findAll(complanyService,page,pfList,hql);
		}
		model.addAttribute("complanytList", page.getResult());
		
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
		return "manage/complany/list";
	}
	
	/**
	 * 添加
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param complany
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(DefaultMultipartHttpServletRequest request,HttpServletResponse response,
			Model model,Complany complany){
		if(complany!=null){
			MultipartFile logo=request.getFile("logoFile");
			MultipartFile img=request.getFile("imgFile");
			String logoPath=null;
			String imgPath=null;
			String root=this.getRoot(request);
			if(logo!=null && img!=null){
				String smallRealDir = this.getRealPath(request, Global.COMPLANY_LOGO_IMG);
				String bigRealDir = this.getRealPath(request, Global.COMPLANY_BIG_IMG);
				logoPath=FileUtil.upload(logo, smallRealDir, Global.COMPLANY_LOGO_IMG);
				imgPath=FileUtil.upload(img, bigRealDir, Global.COMPLANY_BIG_IMG);
			}
			if(StringUtils.isNotBlank(logoPath)){
				complany.setLogo(root+"/"+logoPath);
			}
			if(StringUtils.isNotBlank(imgPath)){
				complany.setImg(root+"/"+imgPath);
			}
			this.complanyService.save(complany);
			removeComplanyCache();
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
	 * @param complany
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(DefaultMultipartHttpServletRequest request,HttpServletResponse response,
			Model model,Complany complany){
		if(complany!=null){
			MultipartFile logo=request.getFile("logoFile");
			MultipartFile img=request.getFile("imgFile");
			String logoPath=null;
			String imgPath=null;
			String root=this.getRoot(request);
			if(logo!=null && img!=null){
				String smallRealDir = this.getRealPath(request, Global.COMPLANY_LOGO_IMG);
				String bigRealDir = this.getRealPath(request, Global.COMPLANY_BIG_IMG);
				logoPath=FileUtil.upload(logo, smallRealDir, Global.COMPLANY_LOGO_IMG);
				imgPath=FileUtil.upload(img, bigRealDir, Global.COMPLANY_BIG_IMG);
			}
			if(StringUtils.isNotBlank(logoPath)){
				complany.setLogo(root+"/"+logoPath);
			}
			if(StringUtils.isNotBlank(imgPath)){
				complany.setImg(root+"/"+imgPath);
			}
			this.complanyService.alter(complany);
			removeComplanyCache();
		}
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 删除
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response) {
		Complany complany=this.complanyService.findById(id);
		String root=this.getRoot(request);
		if(complany!=null){
			String logo=this.getRealPath(request, StringUtils.substringAfter(complany.getLogo(),root));
			String img=this.getRealPath(request, StringUtils.substringAfter(complany.getImg(),root));
			try {
				if(logo.contains(Global.COMPLANY_LOGO_IMG.replace("/", File.separator))){
					FileUtil.delete(logo);
				}
				if(img.contains(Global.COMPLANY_BIG_IMG.replace("/", File.separator))){
					FileUtil.delete(img);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.complanyService.deleteById(id);
		removeComplanyCache();
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);	
		return null;
	}

	/**
	 * @author wei.luo
	 * @createTime 2012-5-7    
	 */
	private void removeComplanyCache() {
		try {
			EHCacheUtil.removeEhcache("complany_cache");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		return "manage/complany/add";
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
		Complany complany = this.complanyService.findById(id);
		model.addAttribute("complany", complany);
		return "manage/complany/alter";
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
	public String detail(Model model,@PathVariable("id") int id ){
		Complany complany = this.complanyService.findById(id);
		model.addAttribute("complany", complany);
		return "manage/complany/detail";
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
		Complany complany=(Complany)this.complanyService.findById(Integer.parseInt(id));
		int order=complany.getOrders();
		if (null != type && type.equals("up")) {// 上升一名 即排名-1
			complany.setOrders(this.complanyService.changeOrder(order - 1, order));
		} else {// 下降一名
			complany.setOrders(this.complanyService.changeOrder(order + 1, order));
		}
		this.complanyService.alter(complany);
		return gotoCurrentPage(request, response);
	}
}
