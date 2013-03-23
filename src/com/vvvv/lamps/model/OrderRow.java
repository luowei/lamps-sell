package com.vvvv.lamps.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.vvvv.common.model.IdEntity;

/**
 * @className:OrderRow.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-5-9
 */
@Entity
@Table(name = "OrderRow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderRow extends IdEntity  implements Serializable {
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 7642051507557954228L;

	@Column(name="product_num")
	private Integer productNum;
	
	@Column(name="vip_price")
	private Double vipPrice;
	
	@Column(name="sub_price")
	private Double subprice;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "orders_id")     
	private Orders orders;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "product_id")
	private Product product;

	
	public Double getSubprice() {
		return subprice;
	}

	public void setSubprice(Double subprice) {
		this.subprice = subprice;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	
	public Double getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Double getSubPrice(){
		return this.getVipPrice()*this.productNum;
	}

	
	public OrderRow() {
		super();
	}

	public OrderRow(Product product,Integer productNum,Double vipPrice,Double subPrice) {
		super();
		this.productNum = productNum;
		this.product = product;
		this.vipPrice = vipPrice;
		this.subprice = subPrice;
	}
	
	
}
