package com.vvvv.lamps.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.vvvv.common.model.IdEntity;

/**
 * @className:Ads.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-3
 */
@Entity
@Table(name = "ads")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ads extends IdEntity{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 7445907515031959298L;
	@Column(name="name",length=100)
	private String name;
	@Column(name="img",length=100)
	private String img;
	@Column(name="detail",length=500)
	private String detail;
	@Column(name="status")
	private String status;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="orders")
	private Integer orders;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
}
