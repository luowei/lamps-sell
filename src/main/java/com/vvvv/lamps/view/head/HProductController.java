package com.vvvv.lamps.view.head;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import com.vvvv.common.tool.query.PropertyFilter;
import com.vvvv.common.view.BaseController;
import com.vvvv.common.view.dto.QueryDTO;
import com.vvvv.lamps.model.Category;
import com.vvvv.lamps.model.Complany;
import com.vvvv.lamps.model.Message;
import com.vvvv.lamps.model.Product;

/**
 * @className:HProductController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:30:28 
 */
@Controller
@RequestMapping("/head")
public class HProductController extends BaseController<Product>{
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
	@RequestMapping(value = "/product")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO,String categoryId,String categoryName,
			String complanyId){
		
		String name=queryDTO.getKey();
		int pageNo=queryDTO.getPageNo();
		String order=queryDTO.getOrder();
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
		pfList.add(new PropertyFilter("status:EQ_S","上线"));
		
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

		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/head/product.php");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		//获取排序跳转页面
		String orderCondition=PageUtil.getOrderCondition(fragmentList);
		model.addAttribute("order", orderCondition);
		
		//获取过滤查询集合
		List<Condition> filterList=PageUtil.getFilterConditions(fragmentList);
		model.addAttribute("filterList", filterList);

		//类别
		List<Category> pCategoryList=(List<Category>)EHCacheUtil.getValue("category_cache", "pCategoryList");
		List<Category> subCategoryList=(List<Category>)EHCacheUtil.getValue("category_cache", "subCategoryList");
		if(pCategoryList==null ||subCategoryList==null){
			//子类别
			String subHql=" from Category where parentId > '1' and status='上线' ";
			String productHql=" from Product p where  p.status='上线' and p.category.id=";
			subCategoryList = this.categoryService.getSubCategory(subHql,productHql);
			//父类别
			String hql=" from Category where parentId='1' and id!='1' and status='上线' ";
			pCategoryList = this.categoryService.getPCategory(subCategoryList,hql);
			try {
				EHCacheUtil.setValue("category_cache", "pCategoryList", pCategoryList);
				EHCacheUtil.setValue("category_cache", "subCategoryList", subCategoryList);
				EHCacheUtil.removeElment("product_cache", "productList");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("pCategoryList", pCategoryList);
		model.addAttribute("subCategoryList", subCategoryList);
		
		//产品
		List<Product> hotProductList=(List<Product>)EHCacheUtil.getValue("product_cache", "hotProductList");
		if(hotProductList==null){
			hotProductList=this.productService.findList(" from Product p where  p.status='上线' order by createtime  ",1,20);
			try {
				EHCacheUtil.setValue("product_cache", "hotProductList", hotProductList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("hotProductList", hotProductList);
		
		List<Product> productPageList=(List<Product>)EHCacheUtil.getValue("product_cache", "productList");
		String cateId=(String)EHCacheUtil.getValue("final_cache", "categoryId");
		String compId=(String)EHCacheUtil.getValue("final_cache", "complanyId");
		Integer InPageNo=(Integer)EHCacheUtil.getValue("final_cache", "pageNo");
		String keyInCache=(String)EHCacheUtil.getValue("final_cache", "key");
		Page<Product> page=new Page<Product>(pageSize);
		page.setPageNo(pageNo);
		
		//注意：还要对key值做判断
		productPageList = this.obtainProductList(categoryId, complanyId, name,
				order, pfList, productPageList, cateId,compId, InPageNo, keyInCache,
				page);
		model.addAttribute("productList", productPageList);
		
		// 生成分页标签
		page.setForwordName(forwordName.toString());
		String frontTag = PageUtil.getFrontTag(page);
		model.addAttribute("frontTag", frontTag);
		// 索引号
		model.addAttribute("index", page.getFirst());

		// 设置页面搜索初始值
		model.addAttribute("key", name);
		//model.addAttribute("categoryId", categoryId);
		//model.addAttribute("complanyId", complanyId);
		
		String currentPage = request.getRequestURI().toString()
			+ forwarCondition + page.getPageNo();
		request.getSession().setAttribute("currentPage", currentPage);
		return "product";
	}

	/**
	 * 获得产品列表
	 * @author wei.luo
	 * @createTime 2012-5-5
	 * @param categoryId
	 * @param complanyId
	 * @param name
	 * @param order
	 * @param pfList
	 * @param productPageList
	 * @param cateId
	 * @param InPageNo
	 * @param keyInCache
	 * @param page
	 * @return
	 */
	private List<Product> obtainProductList(String categoryId,
			String complanyId, String name, String order,
			List<PropertyFilter> pfList, List<Product> productPageList,
			String cateId,String compId, Integer InPageNo, String keyInCache,
			Page<Product> page) {
		if(productPageList==null ||!String.valueOf(categoryId).equals(String.valueOf(cateId)) || 
				!Integer.valueOf(page.getPageNo()).equals(InPageNo) || 
				!String.valueOf(keyInCache).equals(String.valueOf(name)) || 
				!String.valueOf(complanyId).equals(String.valueOf(compId))){
			if(null==order){
				page.setOrder("id:asc");
			}else{
				page.setOrder(order);
			}
			String hql=" from Product ";
			if(StringUtils.isNotBlank(complanyId)){
				//page=this.productService.findByComplanyId(page,complanyId);
				List<Product> productList=new ArrayList<Product>();
				productList.addAll(
						this.complanyService.findById(Integer.parseInt(complanyId)).getProductSet());
				
				//Integer pageCount=productList.size()/page.getPageSize();
				Integer fromIndex=Integer.valueOf(0);
				if(page.getPageNo() > 0){
					fromIndex=(page.getPageNo()-1)*page.getPageSize();
				}
				Integer toIndex=page.getPageNo()*page.getPageSize();
				List<Product> newList=new ArrayList<Product>();
				if(toIndex < productList.size()){
					newList=productList.subList(fromIndex, toIndex);
				}else{
					newList=productList.subList(fromIndex, productList.size());
				}
				page.setTotalCount(productList.size());
				page.setResult(newList);
				try {
					EHCacheUtil.setValue("final_cache", "complanyId",complanyId );
					//EHCacheUtil.setValue("final_cache", "pageCurrent",complanyId );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(StringUtils.isNotBlank(categoryId)){
				try {
					EHCacheUtil.setValue("final_cache", "categoryId",categoryId );
				} catch (Exception e) {
					e.printStackTrace();
				}
				int first=(page.getPageNo()-1)*page.getPageSize();
				int max=page.getPageSize();
				hql=" from Product p where p.status='上线' and (p.category.id='"+categoryId+"' or p.category.parentId='"+categoryId+"')";
				List<Product> productList=this.productService.findListWithLimt(hql,first,max);
				String tsql=" product where status='上线' and (category_id ='"+categoryId+"' or category_id in(select id from category where parent_id='"+categoryId+"')) ";
				Integer totalCount=this.categoryService.findTotalCountBySql(tsql);
				if(productList!=null && totalCount!=null){
					page.setTotalCount(totalCount);
					page.setResult(productList);
				}
			}else{
				page=this.productService.findAll(page,pfList,hql);
			}
			try {
				productPageList=page.getResult();
				EHCacheUtil.setValue("product_cache", "productList",productPageList );
				EHCacheUtil.setValue("final_cache", "totalCount",page.getTotalCount() );
				EHCacheUtil.setValue("final_cache", "pageNo",page.getPageNo());
				EHCacheUtil.setValue("final_cache", "key",name);
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Long totalCount=(Long)EHCacheUtil.getValue("final_cache", "totalCount");
			if(totalCount!=null){
				page.setTotalCount(totalCount);
			}
			page.setResult(productPageList);
		}
		return productPageList;
	}

	/**
	 * 详细信息
	 * @author wei.luo
	 * @createTime 2012-5-5
	 * @param request
	 * @param response
	 * @param model
	 * @param queryDTO
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/product_detail")
	public String detail(HttpServletRequest request,HttpServletResponse response,
			Model model,QueryDTO queryDTO,String id){
		String name=queryDTO.getKey();
		int pageNo=queryDTO.getPageNo();
		String order=queryDTO.getOrder();
		int pageSize=queryDTO.getPageSize();
		if(pageSize==0){
			pageSize=10;
		}
		
		//查询条件
		List<PropertyFilter> pfList=new ArrayList<PropertyFilter>();
		pfList.add(new PropertyFilter("name:LIKE_S",name));
		pfList.add(new PropertyFilter("status:EQ_S","上线"));
		
		//获取分页跳转页面
		List<Condition> fragmentList=new ArrayList<Condition>();
		fragmentList.add(new Condition("key",EncodeUtil.urlEncode(name),"匹配'"+name+"'"));
		fragmentList.add(new Condition("id",id,"",false));
		fragmentList.add(new Condition("order",order,"排序",false));
		
		//产品、类别
		List<Product> productList=(List<Product>)EHCacheUtil.getValue("product_cache", "productList");
		productList = obtainProductList(productList);
		
		Integer cCategoryId=(Integer)EHCacheUtil.getValue("final_cache", "cCategoryId");
		Product product=(Product)EHCacheUtil.getValue("product_cache", "product");;
		product = obtainProduct(id, productList, product,cCategoryId);
		
		Category cCategory=(Category)EHCacheUtil.getValue("prod_category_cache", "cCategory");
		Integer pCategoryId=(Integer)EHCacheUtil.getValue("final_cache", "pCategoryId");
		cCategoryId=(Integer)EHCacheUtil.getValue("final_cache", "cCategoryId");
		cCategory = obtainChildCategory(cCategory,cCategoryId,pCategoryId);
		
		Category pCategory=(Category)EHCacheUtil.getValue("prod_category_cache", "pCategory");
		pCategoryId=(Integer)EHCacheUtil.getValue("final_cache", "pCategoryId");
		pCategory = obtainParentCategory(pCategory, pCategoryId);
		
		model.addAttribute("product", product);
		model.addAttribute("pCategory", pCategory);
		model.addAttribute("cCategory", cCategory);
		
		//企业
		List<Complany> complanyList=(List<Complany>)EHCacheUtil.getValue("complany_cache", "complanyList");
		if(complanyList==null){
			complanyList=new ArrayList<Complany>();
			complanyList.addAll(this.productService.findById(Integer.parseInt(id)).getComplanySet());
			try {
				//if(!complanyList.isEmpty()){
					EHCacheUtil.setValue("complany_cache", "complanyList", complanyList);
				//}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(!complanyList.isEmpty()){
			Complany complany=complanyList.get(0);
			model.addAttribute("complany", complany);
		}
		
		//留言
		List<Message> messagePageList=(List<Message>)EHCacheUtil.getValue("message_cache", "messagePageList");
		Integer prodId=(Integer)EHCacheUtil.getValue("final_cache", "productId");
		Integer InPageNo=(Integer)EHCacheUtil.getValue("final_cache", "pageNo");
		String keyInCache=(String)EHCacheUtil.getValue("final_cache", "key");
		Page<Message> page=new Page<Message>(pageSize);
		page.setPageNo(pageNo);
		
		messagePageList = this.obtainMessage(id, name, order, pfList,
				messagePageList, prodId, InPageNo, keyInCache, page);
		model.addAttribute("messageList", messagePageList);	
		
		//设置跳转页面
		StringBuffer forwordName=new StringBuffer(this.getRoot(request)+"/head/product_detail.php");
		String forwarCondition=PageUtil.getForwardCondition(fragmentList);
		forwordName.append(forwarCondition);
		
		// 生成分页标签
		page.setForwordName(forwordName.toString());
		String frontTag = PageUtil.getFrontTag(page);
		model.addAttribute("frontTag", frontTag);
		
		String currentPage = request.getRequestURI().toString()+forwarCondition+page.getPageNo();
		request.getSession().setAttribute("currentPage", currentPage);
		return "pdetail";
		
	}

	/**
	 * @author wei.luo
	 * @createTime 2012-5-6
	 * @param id
	 * @param productList
	 * @param product
	 * @return    
	 */
	private Product obtainProduct(String id, List<Product> productList,
			Product product,Integer cCategoryId) {
		if(product==null || cCategoryId==null || !id.equals(product.getId())){
			for(Product prod:productList){
				if(prod.getId().equals(Integer.valueOf(id))){
					product=this.productService.findById(Integer.parseInt(id));
					break;
				}
			}
			try {
				EHCacheUtil.setValue("product_cache", "product", product);
				EHCacheUtil.setValue("final_cache", "cCategoryId", product.getCategory().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return product;
	}
	
	/**
	 * @author wei.luo
	 * @createTime 2012-5-6
	 * @param product
	 * @param cCategory
	 * @return    
	 */
	private Category obtainChildCategory(Category cCategory,Integer cCategoryId,Integer pCategoryId) {
		if(cCategory==null ||pCategoryId==null){
			//cCategory=product.getCategory();
			cCategory=this.categoryService.findById(cCategoryId);
			try {
				EHCacheUtil.setValue("prod_category_cache", "cCategory",cCategory );
				EHCacheUtil.setValue("final_cache", "pCategoryId",cCategory.getParentId() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cCategory;
	}

	/**
	 * @author wei.luo
	 * @createTime 2012-5-6
	 * @param pCategory
	 * @param cCategory
	 * @return    
	 */
	private Category obtainParentCategory(Category pCategory, Integer pCategoryId) {
		if(pCategory==null){
			pCategory=this.categoryService.findById(pCategoryId);
			try {
				EHCacheUtil.setValue("prod_category_cache", "pCategory", pCategory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pCategory;
	}
	

	/**
	 * @author wei.luo
	 * @createTime 2012-5-6
	 * @param productList
	 * @return    
	 */
	private List<Product> obtainProductList(List<Product> productList) {
		if(productList==null){
			productList=this.productService.findList(" from Product where status='上线' ");
			try {
				EHCacheUtil.setValue("product_cache", "productList", productList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return productList;
	}


	/**
	 * 根据产品id获得留言列表
	 * @author wei.luo
	 * @createTime 2012-5-5
	 * @param productId
	 * @param name
	 * @param order
	 * @param pfList
	 * @param messagePageList
	 * @param prodId
	 * @param InPageNo
	 * @param keyInCache
	 * @param page
	 * @return
	 */
	private List<Message> obtainMessage(String productId, String name,
			String order, List<PropertyFilter> pfList,
			List<Message> messagePageList, Integer prodId, Integer InPageNo,
			String keyInCache, Page<Message> page) {
		//Long totalCount=(Long)EHCacheUtil.getValue("final_cache", "product_message_totalCount");
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
				try {
					EHCacheUtil.setValue("final_cache", "productId",Integer.valueOf(productId) );
					//if(totalCount==null){
						totalCount=Long.valueOf((long)this.messageService.findCountBySql(hql));
						//EHCacheUtil.setValue("final_cache", "product_message_totalCount", totalCount);
					//} 
				}catch (Exception e) {
					e.printStackTrace();
				}
				if(messageList!=null && totalCount!=null){
					page.setTotalCount(totalCount);
					page.setResult(messageList);
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
		return messagePageList;
	}
	
}
