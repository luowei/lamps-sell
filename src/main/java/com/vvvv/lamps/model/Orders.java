package com.vvvv.lamps.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.vvvv.common.model.IdEntity;
import com.vvvv.common.tool.common.FileUtil;
import com.vvvv.user.model.UserInfo;

/**
 * @className:Orders.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-3 
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Orders extends IdEntity implements Serializable{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 4263110994277000397L;
	@Column(name="number")			//订单编号
	private String number;
	@Column(name="pay_mode")
	private String payMode;
	@Column(name="pay_status")
	private String payStatus;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="orders_date")
	private Date orderDate;
	@Column(name="consignee")		//收货人
	private String consignee;
	@Column(name="phone")			//联系电话
	private String phone;
	@Column(name="zip")				//邮编
	private String zip;
	@Column(name="shippingaddr")	//收货地址
	private String shippingaddr;
	@Column(name="deliverymode")	//配送方式
	private String deliverymode;
	@Column(name="total_price")
	private Double totalPrice;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "user_id")
    private UserInfo user;

	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="orders")
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<OrderRow> orderRowSet=new HashSet<OrderRow>();
	//private Set<Product> productSet=new HashSet<Product>();
	
	
	public String getPayMode() {
		return payMode;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Set<OrderRow> getOrderRowSet() {
		return orderRowSet;
	}

	public void setOrderRowSet(Set<OrderRow> orderRowSet) {
		this.orderRowSet = orderRowSet;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
//	public Double getTotalPrice(Set<OrderRow> orderRowSet) {
//		Double totalPrice=0D;
//		for(OrderRow orderRow:orderRowSet){
//			totalPrice=totalPrice+orderRow.getSubPrice();
//		}
//		return totalPrice;
//	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Orders(){
		this.number = FileUtil.getNewId();
	}
	
	public Orders(String payMode, String payStatus, Date orderDate,
			UserInfo user, Set<OrderRow> orderRowSet) {
		super();
		this.payMode = payMode;
		this.payStatus = payStatus;
		this.orderDate = orderDate;
		this.user = user;
		this.orderRowSet = orderRowSet;
		this.number = FileUtil.getNewId();
	}
	public Orders(String payMode, String payStatus, Date orderDate,
			UserInfo user) {
		super();
		this.payMode = payMode;
		this.payStatus = payStatus;
		this.orderDate = orderDate;
		this.user = user;
		this.number = FileUtil.getNewId();
	}

	public Orders( String payMode, String payStatus,
			Date orderDate, String consignee,String phone,String zip, 
			String shippingaddr,String deliverymode, UserInfo user,Double totalPrice) {
		super();
		this.payMode = payMode;
		this.payStatus = payStatus;
		this.orderDate = orderDate;
		this.consignee = consignee;
		this.phone = phone;
		this.zip = zip;
		this.shippingaddr = shippingaddr;
		this.deliverymode = deliverymode;
		this.user = user;
		this.number = FileUtil.getNewId();
		this.totalPrice = totalPrice;
	}

	
	
}
