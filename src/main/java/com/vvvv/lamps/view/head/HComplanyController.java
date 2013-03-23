package com.vvvv.lamps.view.head;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.BaseController;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.model.Message;

/**
 * @className:HComplanyController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:29:17 
 */
@Controller
@RequestMapping("/head")
public class HComplanyController extends BaseController<Complany>{
	
	/**
	 * 企业列表
	 * @author wei.luo
	 * @createTime 2012-5-6
	 * @param request
	 * @param response
	 * @param model
	 * @param queryDTO
	 * @param productId
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/complany")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO,String productId,String id){
		String name=queryDTO.getKey();
		int pageNo=queryDTO.getPageNo();
		String order=queryDTO.getOrder();
		int pageSize=queryDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		Page<Complany> page=new Page<Complany>(pageSize);
		page.setPageNo(pageNo);
		
		//查询条件
		List<PropertyFilter> pfList=new ArrayList<PropertyFilter>();
		pfList.add(new PropertyFilter("name:LIKE_S",name));
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		//fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		//fragmentList.add(new Condition("id",id,"",false));
		fragmentList.add(new Condition("order",order,"排序",false));
		
		List<Complany> complanyPageList=(List<Complany>)EHCacheUtil.getValue("complany_cache", "complanyPageList");
		Integer compId=(Integer)EHCacheUtil.getValue("final_cache", "id");
		Integer InPageNo=(Integer)EHCacheUtil.getValue("final_cache", "pageNo");
		String keyInCache=(String)EHCacheUtil.getValue("final_cache", "key");
		Long totalPages=(Long)EHCacheUtil.getValue("final_cache", "totalPages");
		
		if(complanyPageList==null ||!String.valueOf(id).equals(String.valueOf(compId)) || 
				!Integer.valueOf(page.getPageNo()).equals(InPageNo) || 
				!String.valueOf(keyInCache).equals(String.valueOf(name)) ||
				totalPages ==null){
			if(null==order){
				page.setOrder("id:asc");
			}else{
				page.setOrder(order);
			}
			String hql=" from Complany  ";
			page=this.complanyService.findAll(page,pfList,hql);
			try {
				complanyPageList=page.getResult();
				EHCacheUtil.setValue("final_cache", "pageNo",page.getPageNo());
				EHCacheUtil.setValue("final_cache", "key",name);
				EHCacheUtil.removeElment("final_cache", "categoryId");
				EHCacheUtil.setValue("complany_cache", "complanyPageList",complanyPageList );
				EHCacheUtil.removeElment("final_cache", "pageCount");
				EHCacheUtil.setValue("final_cache", "totalPages",Long.valueOf(page.getTotalPages()) );
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("complanyList", complanyPageList);
		
		Complany complany=null;
		if(StringUtils.isNotBlank(id)){
			complany=this.complanyService.findById(Integer.parseInt(id));
		}
		model.addAttribute("rComplany", complany);
		
		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/head/complany.php");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		// 生成分页标签
		page.setForwordName(forwordName.toString());
		String frontTag = PageUtil.getFrontTag(page);
		model.addAttribute("frontTag", frontTag);
		
		String currentPage = request.getRequestURI().toString()+forwarCondition+page.getPageNo();
		request.getSession().setAttribute("currentPage", currentPage);
		return "complany";
	}
}
