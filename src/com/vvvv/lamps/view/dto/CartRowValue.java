package com.vvvv.lamps.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.vvvv.common.tool.others.RoundTool;
import com.vvvv.lamps.model.Product;
import com.vvvv.user.model.UserInfo;

/**
 * @className:Cart.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-5-2
 */
public class CartRowValue implements Serializable{

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	private Product product;
	private UserInfo user;
	private Integer num;
	private Double subPrice;
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public Double getSubPrice() {
		if(product != null && num != null && user !=null){
			double sprice=this.getVipPrice()*num;//*(100-user.getIsBetter())/100;
			return RoundTool.round(sprice,2,BigDecimal.ROUND_HALF_DOWN);
		}else{
			return 0D;
		}
	}
	public Double getVipPrice() {
		if(product != null && num != null && user !=null){
			double vprice=this.product.getPrice()*(100-user.getIsBetter())/100;
			return RoundTool.round(vprice,2,BigDecimal.ROUND_HALF_DOWN);
		}else{
			return this.product.getPrice();
		}
	}
//	public void setSubPrice(Double subPrice) {
//		this.subPrice = subPrice;
//	}
	
	public CartRowValue(Product product, Integer num,UserInfo user) {
		super();
		this.product = product;
		this.num = num;
		this.user = user;
		//this.subPrice=this.product.getPrice()*num;
	}
	public CartRowValue(Product product, Integer num, String payMode,UserInfo user) {
		super();
		this.product = product;
		this.num = num;
		this.user = user;
	}
	public CartRowValue() {
		super();
	}
	
}
