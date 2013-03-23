package com.vvvv.lamps.model;

import java.util.Date;

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
import com.vvvv.common.tool.common.DateUtil;
import com.vvvv.user.model.UserInfo;

/**
 * @className:Message.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-3
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Message extends IdEntity{
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -5092894034231775965L;
	@Column(name="type")
	private String type;
	@Column(name="detail")
	private String detail;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="status")
	private String status;
	@Column(name="orders")
	private Integer orders;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "user_id")
    private UserInfo user;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)  
    @JoinColumn(name="product_id")
    private Product product;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)  
    @JoinColumn(name="complany_id")
    private Complany complany;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Complany getComplany() {
		return complany;
	}

	public void setComplany(Complany complany) {
		this.complany = complany;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
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

	public Message() {
		super();
		this.createtime = DateUtil.datetimeToDate();
	}

}
