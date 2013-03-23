package com.vvvv.lamps.view.dto;

import java.io.Serializable;

/**
 * @className:Price.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-3
 */
public class Price implements Serializable{
	private int productId;
	private String name;
	private double price;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
