package com.vvvv.lamps.view.dto;

import java.io.Serializable;

/**
 * @className:PreOrder.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-5-9
 */
public class PreOrder implements Serializable {
	
	private String consignee;
	private String contact;
	private String zip;
	private String shippingaddr;
	private String deliverymode;
	private String payMode;
	
	
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getShippingaddr() {
		return shippingaddr;
	}
	public void setShippingaddr(String shippingaddr) {
		this.shippingaddr = shippingaddr;
	}
	public String getDeliverymode() {
		return deliverymode;
	}
	public void setDeliverymode(String deliverymode) {
		this.deliverymode = deliverymode;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	
	
	public PreOrder() {
		super();
	}
	
	
	
}
