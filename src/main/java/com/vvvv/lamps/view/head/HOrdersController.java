package com.vvvv.lamps.view.head;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.vvvv.common.tool.cache.EHCacheUtil;
import com.vvvv.common.tool.common.DateUtil;
import com.vvvv.common.view.BaseController;
import com.vvvv.lamps.model.OrderRow;
import com.vvvv.lamps.model.Orders;
import com.vvvv.lamps.model.Product;
import com.vvvv.lamps.view.dto.Cart;
import com.vvvv.lamps.view.dto.CartRowValue;
import com.vvvv.lamps.view.dto.PreOrder;
import com.vvvv.user.model.UserInfo;

/**
 * @className:HOrdersController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Controller
@RequestMapping("/orders")
public class HOrdersController extends BaseController<Orders>{
	
	/**
	 * 预览订单
	 * @author wei.luo
	 * @createTime 2012-5-9
	 * @param request
	 * @param response
	 * @param model
	 * @param preOrder
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/preview")
	public String preview(HttpServletRequest request,HttpServletResponse response,
			Model model,PreOrder preOrder,RedirectAttributes redirectAttrs){
		UserInfo user=(UserInfo)request.getSession().getAttribute("user");
		if(user==null){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		Map<String,CartRowValue> cartRowMap=((Cart)request.getSession().getAttribute("cart")).getCartRowMap();
		List<CartRowValue> cartRowList = new ArrayList<CartRowValue>();
		cartRowList.addAll(cartRowMap.values());
		model.addAttribute("cartRowList", cartRowList);
		return "preorder";
	}
	
	/**
	 * 提交订单
	 * @author wei.luo
	 * @createTime 2012-5-9
	 * @param request
	 * @param response
	 * @param model
	 * @param preOrder
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String confirm(HttpServletRequest request,HttpServletResponse response,
			Model model,PreOrder preOrder,RedirectAttributes redirectAttrs){
		
		UserInfo user=(UserInfo)request.getSession().getAttribute("user");
		if(user==null){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		Map<String,CartRowValue> cartRowMap=cart.getCartRowMap();
		if(user!=null && cartRowMap!=null){
			List<CartRowValue> cartRowList=new ArrayList<CartRowValue>();
			cartRowList.addAll((Collection<CartRowValue>)cartRowMap.values());
			Date date = DateUtil.datetimeToDate();
			Orders orders = new Orders(preOrder.getPayMode(),"未付款",date,preOrder.getConsignee(),preOrder.getContact(),
					preOrder.getZip(),preOrder.getShippingaddr(),preOrder.getDeliverymode(),user,cart.getTotalPrice());
			Set<OrderRow> orderRowSet=new HashSet<OrderRow>();
			for(CartRowValue cartRow:cartRowList){
				OrderRow orderRow=new OrderRow(cartRow.getProduct(),cartRow.getNum(),cartRow.getVipPrice(),cartRow.getSubPrice());
				orderRow.setOrders(orders);
				this.orderRowService.save(orderRow);
				orderRowSet.add(orderRow);
				Product product = orderRow.getProduct();
				product.setNum(product.getNum()-cartRow.getNum());
				Integer oldValume=cartRow.getProduct().getSalesValume();
				product.setSalesValume(oldValume+cartRow.getNum());
				this.productService.alter(product);
			}
			orders.setOrderRowSet(orderRowSet);
			cart.getCartRowMap().clear();
			cart.setCartRowMap(cart.getCartRowMap());
			request.getSession().setAttribute("cart",cart);
			removeOrdersListCache();
			this.ordersService.save(orders);
			//this.ordersService.flush();
			try {
				EHCacheUtil.removeElment("product_cache", "productList");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		redirectAttrs.addFlashAttribute("preOrder", preOrder);
		String currentPage="/orders/list.php";
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}
	
	
	/**
	 * 订单列表
	 * @author wei.luo
	 * @createTime 2012-5-9
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,HttpServletResponse response,
			Model model,PreOrder preOrder,String orderListIndex,RedirectAttributes redirectAttrs){
		
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		Map<Integer,UserInfo> userInfoMap=(Map<Integer,UserInfo>)EHCacheUtil.getValue("user_cache", "userInfoMap");
		if(user==null || userInfoMap==null || !userInfoMap.containsKey(user.getId())){
			this.sendRedirect(response, this.getHttpRoot(request)+"/head/index.php");
			return null;
		}
		List<Orders> ordersList = (List<Orders>)EHCacheUtil.getValue("orders_cache", "ordersList");
		Set<OrderRow> orderRowSet =new HashSet<OrderRow>();
		Orders orders = null;
		if(ordersList==null){
			ordersList = new ArrayList<Orders>();
			ordersList.addAll(this.ordersService.findList(" from Orders o where o.user.id = '"+user.getId()+"' order by o.orderDate desc "));
			orders = ordersList.get(0);
			orderRowSet = orders.getOrderRowSet();
			try {
				EHCacheUtil.setValue("orders_cache", "ordersList", ordersList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			if(StringUtils.isNotBlank(orderListIndex)){
				Integer ordersId = ordersList.get(Integer.parseInt(orderListIndex)).getId();
				orders = this.ordersService.findById(ordersId);
				orderRowSet.addAll(orders.getOrderRowSet());
			}else{
				orders = this.ordersService.findById(ordersList.get(0).getId());
				orderRowSet.addAll(orders.getOrderRowSet());
			}
		}
		if(StringUtils.isBlank(orderListIndex)){
			orderListIndex="0";
		}
		model.addAttribute("preOrder", preOrder);
		model.addAttribute("orders", orders);
		model.addAttribute("orderRowSet", orderRowSet);
		model.addAttribute("orderListIndex",orderListIndex);
		model.addAttribute("ordersList", ordersList);
		String currentPage =this.getRoot(request)+"/orders/list.php";
		request.getSession().setAttribute("currentPage", currentPage);
		return "orders";
	}
	
	/**
	 * 删除订单
	 * @author wei.luo
	 * @createTime 2012-5-9
	 * @param request
	 * @param response
	 * @param model
	 * @param ordersId
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request,HttpServletResponse response,
			Model model,String ordersId,RedirectAttributes redirectAttrs){
		if(StringUtils.isNotBlank(ordersId)){
			this.ordersService.deleteById(Integer.parseInt(ordersId));
			this.ordersService.flush();
			removeOrdersListCache();
		}
		
		String currentPage="/orders/list.php";
		UriComponents redirectUri = UriComponentsBuilder.fromUriString(currentPage).build().encode();
		return "redirect:" + redirectUri.toUriString();
	}

	/**
	 * 删除ordersList缓存
	 * @author wei.luo
	 * @createTime 2012-5-9    
	 */
	private void removeOrdersListCache() {
		try {
			EHCacheUtil.removeElment("orders_cache", "ordersList");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
