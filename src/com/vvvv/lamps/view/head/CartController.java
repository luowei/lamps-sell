package com.vvvv.lamps.view.head;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.vvvv.common.view.BaseController;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.view.dto.Cart;
import com.vvvv.lamps.view.dto.CartRowValue;
import com.vvvv.user.model.UserInfo;

/**
 * @className:CartController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-5-7下午07:58:39 
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{
	
	/**
	 * 向购物车中添加商品
	 * @author wei.luo
	 * @createTime 2012-5-8
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @param num
	 * @param userId
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,HttpServletResponse response,
			Model model,String productId,String num,String userId){
		if(StringUtils.isBlank(userId) || this.userService.findById(Integer.parseInt(userId))==null 
				|| !StringUtils.isNumeric(userId) || !StringUtils.isNumeric(productId) || !StringUtils.isNumeric(num)){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		UserInfo user = this.userService.findById(Integer.parseInt(userId));
		if(StringUtils.isNotBlank(num) && StringUtils.isNotBlank(productId) && user!=null){
			Product product = this.productService.findById(Integer.parseInt(productId));
			CartRowValue cartRow = new CartRowValue(product,Integer.parseInt(num),user);
			Map<String,CartRowValue> cartRowMap=((Cart)request.getSession().getAttribute("cart")).getCartRowMap();
			if(cartRowMap !=null && cartRowMap.containsKey(productId)){
				Integer newNum=cartRowMap.get(productId).getNum()+Integer.parseInt(num);
				cartRowMap.get(productId).setNum(newNum);
			}else{
				if(cartRowMap==null){
					cartRowMap=new HashMap<String, CartRowValue>();
				}
				cartRowMap.put(productId,cartRow);
			}
			Cart cart = new Cart(cartRowMap);
			request.getSession().setAttribute("cart", cart);
		}
		//this.gotoCurrentPage(request, response);
		
		String currentPage="/cart/list.php";
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
		
		//return list(request, response, model);
	}
	
	/**
	 * 修改购物车中商品的数量
	 * @author wei.luo
	 * @createTime 2012-5-8
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @param num
	 * @param userId
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/alter")
	public String alter(HttpServletRequest request,HttpServletResponse response,
			Model model,String productId,String num,String payMode,String userId){
		if(StringUtils.isBlank(userId) || this.userService.findById(Integer.parseInt(userId))==null 
				|| !StringUtils.isNumeric(userId) || !StringUtils.isNumeric(productId) || !StringUtils.isNumeric(num)){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		UserInfo user = this.userService.findById(Integer.parseInt(userId));
		if(StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(num) && user!=null){
			Product product = this.productService.findById(Integer.parseInt(productId));
			CartRowValue cartRow = new CartRowValue(product,Integer.parseInt(num),payMode,user);
//			if(StringUtils.isNotBlank(payMode)){
//				request.getSession().setAttribute("payMode", payMode);
//			}
			Map<String,CartRowValue> cartRowMap=((Cart)request.getSession().getAttribute("cart")).getCartRowMap();
			//cart.put(productId,cartRow);
			if(cartRowMap !=null && cartRowMap.containsKey(productId)){
				//Integer newNum=cart.get(productId).getNum()+Integer.parseInt(num);
				Integer newNum=Integer.parseInt(num);
				cartRowMap.get(productId).setNum(newNum);
			}
			Cart cart = new Cart(cartRowMap);
			request.getSession().setAttribute("cart", cart);
		}
		String currentPage="/cart/list.php";
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
	
	/**
	 * 删除购物车中的商品
	 * @author wei.luo
	 * @createTime 2012-5-8
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @param userId
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request,HttpServletResponse response,
			Model model,String productId,String userId){
		if(StringUtils.isBlank(userId) || this.userService.findById(Integer.parseInt(userId))==null 
				|| !StringUtils.isNumeric(userId) || !StringUtils.isNumeric(productId)){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		if(StringUtils.isNotBlank(productId)){
			Map<String,CartRowValue> cartRowMap=((Cart)request.getSession().getAttribute("cart")).getCartRowMap();
			//if(!cart.isEmpty()){
				cartRowMap.remove(productId);
			//}
			Cart cart = new Cart(cartRowMap);
			request.getSession().setAttribute("cart", cart);
		}
		String currentPage="/cart/list.php";
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
	
	/**
	 * 购买商品
	 * @author wei.luo
	 * @createTime 2012-5-8
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @param num
	 * @param userId
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/buy")
	public String buy(HttpServletRequest request,HttpServletResponse response,
			Model model,String productId,String num,String payMode,String userId){
		if(StringUtils.isBlank(userId) || this.userService.findById(Integer.parseInt(userId))==null 
				|| !StringUtils.isNumeric(userId) || !StringUtils.isNumeric(productId) || !StringUtils.isNumeric(num)){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		UserInfo user = this.userService.findById(Integer.parseInt(userId));
		if(StringUtils.isNotBlank(num) && StringUtils.isNotBlank(productId) && user!=null){
			Product product = this.productService.findById(Integer.parseInt(productId));
			CartRowValue cartRow = new CartRowValue(product,Integer.parseInt(num),user);
			Map<String,CartRowValue> cartRowMap=((Cart)request.getSession().getAttribute("cart")).getCartRowMap();
			if(cartRowMap !=null && cartRowMap.containsKey(productId)){
				Integer newNum=cartRowMap.get(productId).getNum()+Integer.parseInt(num);
				cartRowMap.get(productId).setNum(newNum);
			}else{
				if(cartRowMap==null){
					cartRowMap=new HashMap<String, CartRowValue>();
				}
				cartRowMap.put(productId,cartRow);
				
			}
			Cart cart=new Cart(cartRowMap);
			request.getSession().setAttribute("cart", cart);
		}
		String currentPage="/cart/list.php";
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
	
	/**
	 * 查看购物车列表
	 * @author wei.luo
	 * @createTime 2012-5-8
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model){
		if(request.getSession().getAttribute("user")==null){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		Map<String,CartRowValue> cartRowMap=((Cart)request.getSession().getAttribute("cart")).getCartRowMap();
		List<CartRowValue> cartRowList=new ArrayList<CartRowValue>();
		cartRowList.addAll((Collection<CartRowValue>)cartRowMap.values());
		Cart cart = new Cart(cartRowMap); 
		model.addAttribute("cartRowList", cartRowList);
		model.addAttribute("cart", cart);
		
		String currentPage = this.getRoot(request)+"/cart/list.php";
		request.getSession().setAttribute("currentPage", currentPage);
		return "cart";
	}
	
	
	
}
