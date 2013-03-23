package com.vvvv.lamps.view.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.vvvv.common.tool.others.RoundTool;

/**
 * @className:Cart.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-5-10下午08:21:35 
 */
public class Cart {

	//public Double totalPrice;
	//String(key)存放key，
	public Map<String,CartRowValue> cartRowMap;
	
	public Map<String, CartRowValue> getCartRowMap() {
		return cartRowMap;
	}
	public void setCartRowMap(Map<String, CartRowValue> cartRowMap) {
		this.cartRowMap = cartRowMap;
	}
	
	public Double getTotalPrice() {
		Double totalPrice= new Double(0D);
		Collection<CartRowValue> cartRowSet = this.cartRowMap.values();
		for(CartRowValue cartRow:cartRowSet){
			totalPrice = totalPrice + cartRow.getSubPrice();
		}
		return RoundTool.round(totalPrice,2,BigDecimal.ROUND_HALF_DOWN);
	}
	public Cart(Map<String, CartRowValue> cartRowMap) {
		super();
		this.cartRowMap = cartRowMap;
	}
	public Cart() {
		super();
	}
	
}
