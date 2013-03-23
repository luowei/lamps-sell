package com.vvvv.lamps.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.vvvv.common.model.IdEntity;

/**
 * @className:BaseModel.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-9上午12:34:00 
 */
@MappedSuperclass
public class BaseModel extends IdEntity{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -2515672458562080291L;
	@Column(name="status")
	protected String status;
	@Column(name="createtime")
	protected Date createTime;
	@Column(name="orders")
	protected Integer orders;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	
}
