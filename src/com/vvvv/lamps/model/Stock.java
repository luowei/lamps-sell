package com.vvvv.lamps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.vvvv.common.model.IdEntity;

/**
 * @className:Stock.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-3
 */
@Entity
@Table(name = "Stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stock extends IdEntity{
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -6023158981208803389L;
	@Column(name="product_count")
	private Integer productCount;
	@Column(name="category_id")
	private Integer categoryId;
	@Column(name="min_num")
	private Integer minNum;
	@Column(name="order")
	private Integer order;
	
//	//一对一
//	@OneToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)  
//    @JoinColumn(name="product_id")
//    private Product product;

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Stock() {
		super();
	}
	public Stock(Integer id) {
		this.id=id;
	}
	
}
