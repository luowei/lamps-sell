package com.vvvv.lamps.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.vvvv.common.model.IdEntity;

/**
 * @className:Tradinfo.java
 * @classDescription:求购信息
 * @author:wei.luo
 * @createTime:2012-4-3 
 */
@Entity
@Table(name = "tradinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tradinfo extends IdEntity{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -7428807355269625296L;
	@Column(name="name",length=20)
	private String name;
	@Column(name="product_name",length=50)
	private String productName;
	@Column(name="detail",length=4096)
	private String detail;
	@Column(name="product_img",length=100)
	private String productImg;
	@Column(name="info_type")
	private String infoType;
	@Column(name="validity")
	private Double validity;
	@Column(name="status")
	private String status;
	@Column(name="area",length=100)
	private String area;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="orders")
	private Integer orders;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "complany_id")
	private Complany complany;
	//一对一
	@OneToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)  
    @JoinColumn(name="product_id")
    private Product product;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public Double getValidity() {
		return validity;
	}
	public void setValidity(Double validity) {
		this.validity = validity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public Complany getComplany() {
		return complany;
	}
	public void setComplany(Complany complany) {
		this.complany = complany;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
