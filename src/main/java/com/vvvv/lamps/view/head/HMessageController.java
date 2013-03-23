package com.vvvv.lamps.view.head;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.BaseController;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Message;
import com.vvvv.lamps.model.Product;
import com.vvvv.user.model.UserInfo;

/**
 * @className:HMessageController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:31:04 
 */
@Controller
@RequestMapping("/head")
public class HMessageController extends BaseController<Message>{
	
	@RequestMapping(value = "/message")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO,String productId,String complanyId,String id){
		String name=queryDTO.getKey();
		int pageNo=queryDTO.getPageNo();
		String order=queryDTO.getOrder();
		int pageSize=queryDTO.getPageSize();
		if(pageSize==0){
			pageSize=20;
		}
		Page<Message> page=new Page<Message>(pageSize);
		page.setPageNo(pageNo);
		
		//查询条件
		List<PropertyFilter> pfList=new ArrayList<PropertyFilter>();
		pfList.add(new PropertyFilter("status:EQ_S","上线"));
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		//fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		//fragmentList.add(new Condition("id",id,"",false));
		fragmentList.add(new Condition("order",order,"排序",false));
		
		//留言
		List<Message> messagePageList=(List<Message>)EHCacheUtil.getValue("message_cache", "messagePageList");
		Integer prodId=(Integer)EHCacheUtil.getValue("final_cache", "productId");
		Integer InPageNo=(Integer)EHCacheUtil.getValue("final_cache", "pageNo");
		String keyInCache=(String)EHCacheUtil.getValue("final_cache", "key");
		
		Long totalCount=null;
		if(messagePageList==null ||!String.valueOf(productId).equals(String.valueOf(prodId)) || 
				!Integer.valueOf(page.getPageNo()).equals(InPageNo) || 
				!String.valueOf(keyInCache).equals(String.valueOf(name))){
			if(null==order){
				page.setOrder("createtime:desc");
			}else{
				page.setOrder(order);
			}
			String hql=" from Message  ";
			if(StringUtils.isNotBlank(productId)){
				int first=(page.getPageNo()-1)*page.getPageSize();
				int max=page.getPageSize();
				hql=" from Message m where m.status='上线' and m.product.id="+ productId+" order by m.createtime desc";
				List<Message> messageList=this.messageService.findListWithLimt(hql,first,max);
				totalCount=(Long)EHCacheUtil.getValue("final_cache", "product_message_totalCount");
				if(totalCount==null){
					totalCount=Long.valueOf((long)this.messageService.findCountBySql(hql));
				} 
				if(messageList!=null && totalCount!=null){
					page.setTotalCount(totalCount);
					page.setResult(messageList);
				}
				try {
					EHCacheUtil.setValue("final_cache", "productId",Integer.valueOf(productId) );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				page=this.messageService.findAll(page,pfList,hql);
			}
			try {
				messagePageList=page.getResult();
				EHCacheUtil.setValue("final_cache", "pageNo",page.getPageNo());
				EHCacheUtil.setValue("final_cache", "key",name);
				EHCacheUtil.removeElment("final_cache", "categoryId");
				EHCacheUtil.setValue("message_cache", "messagePageList",messagePageList );
				EHCacheUtil.setValue("final_cache", "product_message_totalCount",page.getTotalCount() );
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			if(totalCount!=null){
				page.setTotalCount(totalCount);
			}
			page.setResult(messagePageList);
		}
		model.addAttribute("messageList", messagePageList);	
		
		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/head/message.php");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		// 生成分页标签
		page.setForwordName(forwordName.toString());
		String frontTag = PageUtil.getFrontTag(page);
		model.addAttribute("frontTag", frontTag);
		
		String currentPage = request.getRequestURI().toString()+forwarCondition+page.getPageNo();
		request.getSession().setAttribute("currentPage", currentPage);
		return "message";
	}
	
	
	
	/**
	 * 用户添加留言
	 * @author wei.luo
	 * @createTime 2012-5-6
	 * @param request
	 * @param response
	 * @param model
	 * @param msg
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/message_add")
	public String add(HttpServletRequest request,HttpServletResponse response,
			Model model,Message msg,String productId,String userId,
			RedirectAttributes redirectAttrs){
		String addMsg_flag="faild";
		if(msg!=null && StringUtils.isNotBlank(productId)
				&& StringUtils.isNotBlank(userId)){
			Product product=this.productService.findById(Integer.parseInt(productId));
			UserInfo user=this.userService.findById(Integer.parseInt(userId));
			msg.setProduct(product);
			msg.setUser(user);
			msg.setStatus("上线");
			this.messageService.save(msg);
			try {
				EHCacheUtil.removeElment("message_cache", "messagePageList");
				addMsg_flag="success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		redirectAttrs.addFlashAttribute("addMsg_flag", addMsg_flag);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		currentPage=currentPage.replace(this.getRoot(request),"");
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
	
	/**
	 * 修改留言
	 * @author wei.luo
	 * @createTime 2012-5-6
	 * @param request
	 * @param response
	 * @param model
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/message_alter")
	public String alter(HttpServletRequest request,HttpServletResponse response,
			Model model,Message msg,String productId,String userId,
			RedirectAttributes redirectAttrs){
		String alterMsg_flag="faild";
		if(msg!=null && StringUtils.isNotBlank(userId)){
			Product product=null;
			UserInfo user=null;
			if(StringUtils.isNotBlank(productId)){
				product = this.productService.findById(Integer.parseInt(productId));
				user=this.userService.findById(Integer.parseInt(userId));
				msg.setProduct(product);
				msg.setUser(user);
			}else{
				Message tmsg=this.messageService.findById(msg.getId());
				tmsg.setDetail(msg.getDetail());
				msg=tmsg;
			}
			msg.setStatus("上线");
			this.messageService.merge(msg);
			try {
				EHCacheUtil.removeElment("message_cache", "messagePageList");
				alterMsg_flag="success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//redirectAttrs.addFlashAttribute("alterMsg_flag", alterMsg_flag);
		redirectAttrs.addAttribute("alterMsg_flag", alterMsg_flag);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		currentPage=currentPage.replace(this.getRoot(request),"");
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
	
	@RequestMapping(value = "/message_del")
	public String delete(HttpServletRequest request,HttpServletResponse response,
			Model model,String id,RedirectAttributes redirectAttrs){
		String delMsg_flag="faild";
		if(StringUtils.isNotBlank(id)){
			this.messageService.deleteById(Integer.parseInt(id));
			try {
				EHCacheUtil.removeElment("message_cache", "messagePageList");
				delMsg_flag="success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		redirectAttrs.addFlashAttribute("delMsg_flag", delMsg_flag);
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		currentPage=currentPage.replace(this.getRoot(request),"");
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
	
	@RequestMapping(value = "/message_detail")
	public String detail(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO,String id){
		
		String currentPage=(String) this.getSessionAttribute(request,"currentPage");
		currentPage=currentPage.replace(this.getRoot(request),"");
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
}
