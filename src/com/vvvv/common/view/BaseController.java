package com.vvvv.common.view;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.vvvv.common.service.IBaseService;
import com.vvvv.common.service.ICommonService;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.tool.query.QueryUtil;
import com.vvvv.lamps.service.IAdsService;
import com.vvvv.lamps.service.ICategoryService;
import com.vvvv.lamps.service.IComplanyService;
import com.vvvv.lamps.service.IContactService;
import com.vvvv.lamps.service.IMessageService;
import com.vvvv.lamps.service.INewsService;
import com.vvvv.lamps.service.IOrderRowService;
import com.vvvv.lamps.service.IOrdersService;
import com.vvvv.lamps.service.IProductService;
import com.vvvv.lamps.service.IStockService;
import com.vvvv.lamps.service.ITradinfoService;
import com.vvvv.module.service.IMenuManageService;
import com.vvvv.module.service.IModuleManageService;
import com.vvvv.user.service.IActionManageService;
import com.vvvv.user.service.IRoleManageService;
import com.vvvv.user.service.IUserManageService;

/**
 * @className:BaseController.java
 * @classDescription:
 * @author:wei.luo
 * @param <IUserManageService>
 * @createTime:2011-5-3
 */

public class BaseController<T>/* extends BaseService<T>*/{	

	//@Autowired
	//protected IBaseService<T> baseService;
	//@Autowired
	//protected ICommonService commonService;
	@Autowired
	protected IUserManageService userService;
	@Autowired
	protected IActionManageService actionService;
	@Autowired
	protected IRoleManageService roleService;
	@Autowired
	protected IMenuManageService menuService;
	@Autowired
	protected IModuleManageService moduleService;
	@Autowired
	protected IAdsService adsService;
	@Autowired
	protected ICategoryService categoryService;
	@Autowired
	protected IComplanyService complanyService;
	@Autowired
	protected IContactService contactService;
	@Autowired
	protected IMessageService messageService;
	@Autowired
	protected INewsService newsService;
	@Autowired
	protected IOrdersService ordersService;
	@Autowired
	protected IProductService productService;
	@Autowired
	protected IStockService stockService;
	@Autowired
	protected ITradinfoService tradinfoService;
	@Autowired
	protected IOrderRowService orderRowService;
	
	
	/**
	 * 验证表单
	 * @param result
	 * @param model
	 * @return
	 */
	public boolean validate(BindingResult result, Model model) {
		boolean flag=true;
		if (result.hasErrors()) {
			flag= false;
			for (FieldError fe : result.getFieldErrors()) {
				model.addAttribute(fe.getField(), fe.getDefaultMessage());
			}
		}
		return flag;
	}

	
	/**
	 * 添加日期绑定参数
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//初始化日期
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		//初始化基本类型
		binder.registerCustomEditor(Short.class, new CustomNumberEditor(Short.class, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
		
	}
	
	/**
	 *  获取根目录地址,即获取应用的ContextPath
	 * @author wei.luo
	 * @createTime 2012-3-29
	 * @param request
	 * @return
	 */
	public String getRoot(HttpServletRequest request){
		return request.getSession().getServletContext().getContextPath();
		
	}
	
	/**
	 * 获得http的应用Root的路径部分，
	 * 例如http://ayyc.ttpod.com/manage/main.jsp --> http://ayyc.ttpod.com
	 * http://192.168.5.28:8080/ayyc/manage/main.jsp --> http://192.168.5.28.8080/ayyc
	 * @author wei.luo
	 * @createTime 2012-3-29
	 * @param request	request请求参数
	 * @return	http的应用Root的路径部分
	 */
	public String getHttpRoot(HttpServletRequest request){
		String root=null;
		if(request.getServerPort()==80){
			root = "http://" + request.getServerName() + this.getRoot(request);
		}else{
			root = "http://" + request.getServerName() + ":" + request.getServerPort() + this.getRoot(request);
		}
		return root;
	}
	
	/**
	 * 获取相对于应用目录的绝对路径
	 * @author wei.luo
	 * @createTime 2012-3-29
	 * @param request	request请求参数
	 * @param relativePath	相对路径
	 * @return	相对路径
	 */
	public String getRealPath(HttpServletRequest request,String relativePath){
		return request.getSession().getServletContext().getRealPath(relativePath);
		
	}
	
	/**
	 * 获取会话中的数据
	 * @param request	request请求参数
	 * @param key	键
	 * @return 返回属性	
	 */
	public Object getSessionAttribute(HttpServletRequest request,String key){
		return request.getSession().getAttribute(key);
	}
	
	/**
	 * 发送地址 
	 * @param response
	 * @param forwardName
	 */
	public void sendRedirect(HttpServletResponse response,String forwardName){
		try {
			response.setCharacterEncoding("utf-8");
			response.sendRedirect(forwardName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * forward跳转
	 * @author wei.luo
	 * @createTime 2012-4-9
	 * @param request
	 * @param response
	 * @param forwardPath
	 * @throws ServletException
	 * @throws IOException
	 */
	public void forward(HttpServletRequest request,HttpServletResponse response,String forwardPath){
		try {
			request.getRequestDispatcher(forwardPath).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 转到currentPage
	 * @author wei.luo
	 * @createTime 2012-4-8
	 * @param request
	 * @param response
	 * @return
	 */
	public String gotoCurrentPage(HttpServletRequest request,HttpServletResponse response) {
		String currentPage = (String) this.getSessionAttribute(request,"currentPage");
		this.sendRedirect(response, currentPage);
		return null;
	}
	
	/**
	 * 将以json格式输出到页面
	 * @author wei.luo
	 * @createTime 2012-3-26
	 * @param request request请求
	 * @param response response 响应
	 * @param obj 输出对象
	 */
	public void printOutJson(HttpServletRequest request,HttpServletResponse response,Object obj){
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSONObject.toJSONString(obj));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("输出数据异常...");
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
	
	/**
	 * 查询所有
	 * @author wei.luo
	 * @createTime 2012-4-8
	 * @param baseService
	 * @param page
	 * @param pfList
	 * @param hql
	 * @return Page对象
	 */
	public Page<T> findAll(IBaseService<T> baseService,Page<T> page, 
			List<PropertyFilter> pfList,String hql) {
		//初始化hql
		StringBuffer thql=new StringBuffer(hql);
		//设置查询条件
		String condition= QueryUtil.toSqlString(pfList, true);
		int totalCount= 0;
		//查找所有用户的总条数
		thql.append(condition);
		totalCount=baseService.findCountBySql(thql.toString());
		page.setTotalCount(totalCount);
		
		//是否存在排序
		if(page.isOrderBySetted()){
			thql.append(page.getOrder());
		}
		List<T> list= baseService.findList(thql.toString(), page.getPageNo(), page.getPageSize());
		//查出结果集
		page.setResult(list);
		return page;
	}

	/**
	 * 修改状态
	 * @author wei.luo
	 * @createTime 2012-4-9
	 * @param baseService
	 * @param request
	 * @param response
	 * @return
	 */
//	public String updateStatus(IBaseService<BaseModel> baseService,HttpServletRequest request,
//			HttpServletResponse response) {
//		String type = request.getParameter("type");
//		if(StringUtils.isBlank(type)){
//			type=String.valueOf(request.getAttribute("type"));
//			if(StringUtils.isBlank(type)){
//				return this.gotoCurrentPage(request, response);
//			}
//		}
//		String id = request.getParameter("id");
//		if(StringUtils.isBlank(id)){
//			type=String.valueOf(request.getAttribute("id"));
//			if(StringUtils.isBlank(id)){
//				return this.gotoCurrentPage(request, response);
//			}
//		}
//		BaseModel baseModel = baseService.findById(id);
//		if (null != baseModel) {
//			if (null != type && "true".equals(type)) {// 将状态修改为下线
//				baseModel.setStatus("下线");
//				baseService.alter(baseModel);
//			}
//			if (null != type && "false".equals(type)) {// 将状态修改为上线
//				baseModel.setStatus("上线");
//				baseService.alter(baseModel);
//			}
//		}
//		return this.gotoCurrentPage(request, response);
//	}
	
}
