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
import com.vvvv.common.tool.common.EncodeUtil;
import com.vvvv.common.tool.page.Condition;
import com.vvvv.common.tool.page.Page;
import com.vvvv.common.tool.page.PageUtil;
import com.vvvv.common.view.BaseController;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Category;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.model.Message;
import com.vvvv.lamps.model.News;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.model.Tradinfo;

/**
 * @className:IndexController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-3-30
 */
@Controller
@RequestMapping("/head")
public class IndexController  extends BaseController<Category>{
	
	@RequestMapping(value = "/index")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO){
		
		/**
		 * 类别
		 */
		List<Category> pCategoryList=(List<Category>)EHCacheUtil.getValue("category_cache", "pCategoryList");
		List<Category> subCategoryList=(List<Category>)EHCacheUtil.getValue("category_cache", "subCategoryList");
		/**
		 * 灯具
		 */
		List<Product> roomProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "roomProductList");
		List<Product> outdoorsProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "outdoorsProductList");
		List<Product> colorProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "colorProductList");
		List<Product> e_sourceProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "e_sourceProductList");
		List<Product> partsProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "partsProductList");
		List<Product> otherProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "otherProductList");
		List<Product> hotProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "hotProductList");
		/**
		 * 公司
		 */
		List<Complany> lastComplanyList=(List<Complany>)EHCacheUtil.getValue("complany_cache", "lastComplanyList");
		/**
		 * 供求信息
		 */
		List<Tradinfo> lastBuyTradinfoList=(List<Tradinfo>)EHCacheUtil.getValue("tradinfo_cache", "lastBuyTradinfoList");
		List<Tradinfo> lastsupplyTradinfoList=(List<Tradinfo>)EHCacheUtil.getValue("tradinfo_cache", "lastsupplyTradinfoList");
		List<Tradinfo> lastCoopTradinfoList=(List<Tradinfo>)EHCacheUtil.getValue("tradinfo_cache", "lastCoopTradinfoList");
		List<Tradinfo> lastProxyTradinfoList=(List<Tradinfo>)EHCacheUtil.getValue("tradinfo_cache", "lastProxyTradinfoList");
		/**
		 * 新闻
		 */
		List<News> lastIndustryNewsList=(List<News>)EHCacheUtil.getValue("news_cache", "lastIndustryNewsList");
		List<News> lastComplanyNewsList=(List<News>)EHCacheUtil.getValue("news_cache", "lastComplanyNewsList");
		/**
		 * 留言
		 */
		List<Message> lastMessageList=(List<Message>)EHCacheUtil.getValue("message_cache", "lastMessageList");
		
		//---------------------类别-------------------------
		//子类别
		if(subCategoryList==null){
			String subHql=" from Category where parentId > '1' and status='上线' ";
			String productHql=" from Product p where  p.status='上线' and p.category.id=";
			subCategoryList = this.categoryService.getSubCategory(subHql,productHql);
		}
		//父类别
		if(pCategoryList==null){
			String hql=" from Category where parentId='1' and id!='1' and status='上线' ";
			pCategoryList = this.categoryService.getPCategory(subCategoryList,hql);
		}
		
		//---------------------产品-------------------------
		if(roomProductList==null){
			roomProductList=this.productService.findList(" from Product p where p.category.parentId ='2' and p.status='上线' ");
		}
		if(outdoorsProductList==null){
			outdoorsProductList=this.productService.findList(" from Product p where p.category.parentId ='3'  and p.status='上线' ");
		}
		if(colorProductList==null){
			colorProductList=this.productService.findList(" from Product p where p.category.parentId ='4'  and p.status='上线' ");
		}
		if(e_sourceProductList==null){
			e_sourceProductList=this.productService.findList(" from Product p where p.category.parentId ='5'  and p.status='上线' ");
		}
		if(partsProductList==null){
			partsProductList=this.productService.findList(" from Product p where p.category.parentId ='6'  and p.status='上线' ");
		}
		if(otherProductList==null){
			otherProductList=this.productService.findList(" from Product p where p.category.parentId ='7'  and p.status='上线' ");
		}
		if(hotProductList==null){
			hotProductList=this.productService.findList(" from Product p where  p.status='上线' order by createtime  ",1,20);
		}
		//---------------------公司-------------------------
		if(lastComplanyList==null){
			lastComplanyList=this.complanyService.findList(" from Complany  order by createtime ",1,20);
		}
		//---------------------新闻-------------------------
		if(lastIndustryNewsList==null){
			lastIndustryNewsList=this.newsService.findList(" from News where type='行业新闻' and status='上线' order by createtime ",1,20);
		}
		if(lastComplanyNewsList==null){
			lastComplanyNewsList=this.newsService.findList(" from News where type='企业新闻' and status='上线' order by createtime ",1,20);
		}
		//---------------------留言-------------------------
		if(lastMessageList==null){
			lastMessageList=this.messageService.findList(" from Message where status='上线' order by createtime ",1,20);
		}
		//---------------------供求信息-------------------------
		if(lastBuyTradinfoList==null){
			lastBuyTradinfoList=this.tradinfoService.findList(" from Tradinfo where info_type='求购' and status='上线' order by createtime ",1,20);
		}
		if(lastsupplyTradinfoList==null){
			lastsupplyTradinfoList=this.tradinfoService.findList(" from Tradinfo where info_type='供应' and status='上线' order by createtime ",1,20);
		}
		if(lastCoopTradinfoList==null){
			lastCoopTradinfoList=this.tradinfoService.findList(" from Tradinfo where info_type='合作' and status='上线' order by createtime ",1,20);
		}
		if(lastProxyTradinfoList==null){
			lastProxyTradinfoList=this.tradinfoService.findList(" from Tradinfo where info_type='代理' and status='上线' order by createtime ",1,20);
		}
		
		try {
			/**
			 * 类别
			 */
			EHCacheUtil.setValue("category_cache", "pCategoryList", pCategoryList);
			EHCacheUtil.setValue("category_cache", "subCategoryList", subCategoryList);
			/**
			 * 产品
			 */
			EHCacheUtil.setValue("product_cache", "roomProductList", roomProductList);
			EHCacheUtil.setValue("product_cache", "outdoorsProductList", outdoorsProductList);
			EHCacheUtil.setValue("product_cache", "colorProductList", colorProductList);
			EHCacheUtil.setValue("product_cache", "e_sourceProductList", e_sourceProductList);
			EHCacheUtil.setValue("product_cache", "partsProductList", partsProductList);
			EHCacheUtil.setValue("product_cache", "otherProductList", otherProductList);
			EHCacheUtil.setValue("product_cache", "hotProductList", hotProductList);
			/**
			 * 公司
			 */
			EHCacheUtil.setValue("complany_cache", "lastComplanyList", lastComplanyList);
			/**
			 * 新闻
			 */
			EHCacheUtil.setValue("news_cache", "lastIndustryNewsList", lastIndustryNewsList);
			EHCacheUtil.setValue("news_cache", "lastComplanyNewsList", lastComplanyNewsList);
			/**
			 * 留言
			 */
			EHCacheUtil.setValue("message_cache", "lastMessageList", lastMessageList);
			/**
			 * 供求信息
			 */
			EHCacheUtil.setValue("tradinfo_cache", "lastBuyTradinfoList", lastBuyTradinfoList);
			EHCacheUtil.setValue("tradinfo_cache", "lastsupplyTradinfoList", lastsupplyTradinfoList);
			EHCacheUtil.setValue("tradinfo_cache", "lastCoopTradinfoList", lastCoopTradinfoList);
			EHCacheUtil.setValue("tradinfo_cache", "lastProxyTradinfoList", lastProxyTradinfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * 类别
		 */
		model.addAttribute("pCategoryList", pCategoryList);
		model.addAttribute("subCategoryList", subCategoryList);
		/**
		 * 产品
		 */
		model.addAttribute("roomProductList", roomProductList);
		model.addAttribute("outdoorsProductList", outdoorsProductList);
		model.addAttribute("colorProductList", colorProductList);
		model.addAttribute("e_sourceProductList", e_sourceProductList);
		model.addAttribute("partsProductList", partsProductList);
		model.addAttribute("otherProductList", otherProductList);
		model.addAttribute("hotProductList", hotProductList);
		/**
		 * 公司
		 */
		model.addAttribute("lastComplanyList", lastComplanyList);
		/**
		 * 新闻
		 */
		model.addAttribute("lastIndustryNewsList", lastIndustryNewsList);
		model.addAttribute("lastComplanyNewsList", lastComplanyNewsList);
		/**
		 * 留言
		 */
		model.addAttribute("lastMessageList", lastMessageList);
		/**
		 * 供求信息
		 */
		model.addAttribute("lastBuyTradinfoList", lastBuyTradinfoList);
		model.addAttribute("lastsupplyTradinfoList", lastsupplyTradinfoList);
		model.addAttribute("lastCoopTradinfoList", lastCoopTradinfoList);
		model.addAttribute("lastProxyTradinfoList", lastProxyTradinfoList);
		
		//设置跳转页面
		String name=queryDTO.getKey();		
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/head/index.php");
		if(StringUtils.isNotBlank(name)){
			forwordName.append("?key="+name);
		}
		String currentPage =forwordName.toString();
		request.getSession().setAttribute("currentPage", currentPage);
		return "index";
	}
	
}
