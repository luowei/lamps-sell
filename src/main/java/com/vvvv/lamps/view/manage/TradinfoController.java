package com.vvvv.lamps.view.manage;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.common.FileUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.model.Message;
import com.vvvv.lamps.model.News;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.model.Tradinfo;
import com.vvvv.lamps.service.IComplanyService;
import com.vvvv.lamps.service.ITradinfoService;
import com.vvvv.lamps.view.Global;
import com.vvvv.lamps.view.head.HTradinfoController;
import com.vvvv.user.model.UserInfo;


/**
 * @className:TradinfoController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:26:00 
 */
@Controller
@RequestMapping("/manage/tradinfo")
public class TradinfoController extends HTradinfoController{
//	@Autowired
//	protected ITradinfoService tradinfoService;
//	@Autowired
//	protected IComplanyService complanyService;
	
	/**
	 * 供求信息列表
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
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/manage/tradinfo/list.do");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		//初始化page属性值--默认id排序
		Page<Tradinfo> page=new Page<Tradinfo>(pageSize);
		if(null==order){
			page.setOrder("id:asc");
		}else{
			page.setOrder(order);
		}
		page.setPageNo(pageNo);
		String hql="from Tradinfo";
		page=this.findAll(tradinfoService,page,pfList,hql);
		model.addAttribute("tradinfoList", page.getResult());
		
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
		return "manage/tradinfo/list";
	}
	
	/**
	 * 添加
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param tradinfo
	 * @param complanyId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(DefaultMultipartHttpServletRequest request,HttpServletResponse response,
			Model model,Tradinfo tradinfo,String complanyId,String productId){
		if(StringUtils.isBlank(productId)&&StringUtils.isBlank(complanyId)){
			String currentPage=(String) this.getSessionAttribute(request,"currentPage");
			this.sendRedirect(response,currentPage);
			return null;
		}
		Complany complany=this.complanyService.findById(Integer.parseInt(complanyId));
		Product product=this.productService.findById(Integer.parseInt(productId.split("@")[0].trim()));
		if(complany!=null&&product!=null){
			tradinfo.setComplany(complany);
			tradinfo.setProduct(product);
			MultipartFile productImg=request.getFile("imgFile");
			String imgPath=null;
			if(productImg!=null){
				String imgRealDir=this.getRealPath(request, Global.TRADINFO_PRODUCT_IMG);
				imgPath=FileUtil.upload(productImg,imgRealDir,Global.TRADINFO_PRODUCT_IMG);
			}
			if(StringUtils.isNotBlank(imgPath)){
				tradinfo.setProductImg(imgPath);
			}
			if(tradinfo!=null){
				this.tradinfoService.save(tradinfo);
			}
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
	 * @param tradinfo
	 * @param productId
	 * @param complanyId
	 * @return
	 */
	@RequestMapping(value = "/update")
	public String update(DefaultMultipartHttpServletRequest request,HttpServletResponse response,
			Model model,Tradinfo tradinfo,String productId,String complanyId){
		if(StringUtils.isBlank(complanyId) && StringUtils.isBlank(productId)){
			String currentPage=(String) this.getSessionAttribute(request,"currentPage");
			this.sendRedirect(response,currentPage);
			return null;
		}
		Product product=this.productService.findById(Integer.parseInt(productId.split("@")[0].trim()));
		Complany complany=this.complanyService.findById(Integer.parseInt(complanyId));
		if(complany!=null&&product!=null){
			tradinfo.setComplany(complany);
			tradinfo.setProduct(product);
			MultipartFile productImg=request.getFile("imgFile");
			String imgPath=null;
			if(productImg!=null){
				String imgRealDir=this.getRealPath(request, Global.TRADINFO_PRODUCT_IMG);
				imgPath=FileUtil.upload(productImg,imgRealDir,Global.TRADINFO_PRODUCT_IMG);
			}
			if(StringUtils.isNotBlank(imgPath)){
				tradinfo.setProductImg(imgPath);
			}
			if(tradinfo!=null){
				//this.tradinfoService.flush();
				this.tradinfoService.merge(tradinfo);
			}
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
		//List<Tradinfo> tradInfoList=this.tradinfoService.findList("from Tradinfo t where t.product is null and t.complany is null");
		List<Complany> comlanyList = this.complanyService.findList("from Complany c where not exists (" +
				"select t.complany from Tradinfo t where t.complany is not null and t.complany=c)");
		List<Product> productList =this.productService.findList("from Product p where not exists(" +
				"select t.product from Tradinfo t where t.product is not null and t.product=p)");
		//model.addAttribute("tradInfoList", tradInfoList);
		model.addAttribute("comlanyList", comlanyList);
		model.addAttribute("productList", productList);
		return "manage/tradinfo/add";
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
		List<Complany> comlanyList = this.complanyService.findList("from Complany c where not exists (" +
				"select t.complany from Tradinfo t where t.complany is not null and t.complany=c)");
		List<Product> productList =this.productService.findList("from Product p where not exists(" +
				"select t.product from Tradinfo t where t.product is not null and t.product=p)");
		Tradinfo tradinfo = this.tradinfoService.findById(id);
		model.addAttribute("productList", productList);
		model.addAttribute("comlanyList", comlanyList);
		model.addAttribute("tradinfo", tradinfo);
		return "manage/tradinfo/alter";
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
		Tradinfo tradinfo=this.tradinfoService.findById(id);
		String root=this.getRoot(request);
		if(tradinfo!=null){
			String productImg=this.getRealPath(request, StringUtils.substringAfter(tradinfo.getProductImg(),root));
			try {
				if(productImg.contains(Global.TRADINFO_PRODUCT_IMG.replace("/", File.separator))){
					FileUtil.delete(productImg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.tradinfoService.deleteById(id);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response,currentPage);
		return null;
	}
	
	/**
	 * 根据公司id获得产品
	 * @author wei.luo
	 * @createTime 2012-4-29
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 */
	@RequestMapping(value="/getProductByComplanyId")
	public void getProductByComplanyId(HttpServletRequest request,HttpServletResponse response,
			Model model,String id){
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		Set<Product> productSet=this.complanyService.findById(id).getProductSet();
		this.printOutJson(request, response, productSet);
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
		Tradinfo tradinfo = this.tradinfoService.findById(Integer.parseInt(id));
		if (null != tradinfo) {
			if (null != type && "true".equals(type)) {// 将状态修改为下线
				tradinfo.setStatus("下线");
				this.tradinfoService.alter(tradinfo);
			}
			if (null != type && "false".equals(type)) {// 将状态修改为上线
				tradinfo.setStatus("上线");
				this.tradinfoService.alter(tradinfo);
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
		Tradinfo tradinfo=(Tradinfo)this.tradinfoService.findById(Integer.parseInt(id));
		int order=0;
		if(tradinfo.getOrders()!=null){
			order=tradinfo.getOrders();
		}
		if (null != type && type.equals("up")) {// 上升一名 即排名-1
			tradinfo.setOrders(this.tradinfoService.changeOrder(order - 1, order));
		} else {// 下降一名
			tradinfo.setOrders(this.tradinfoService.changeOrder(order + 1, order));
		}
		this.tradinfoService.alter(tradinfo);
		return gotoCurrentPage(request, response);
		
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
		Tradinfo tradinfo=this.tradinfoService.findById(id);
		model.addAttribute("tradinfo", tradinfo);
		return "manage/tradinfo/detail";
	}
}
