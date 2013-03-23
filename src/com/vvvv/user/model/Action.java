package com.vvvv.user.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.vvvv.common.model.IdEntity;
import com.vvvv.module.model.Module;

/**
 * @className:Action.java
 * @classDescription:权限类
 * @author:xiayingjie
 * @createTime:2010-7-5
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Action extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 5567728827821074636L;
	
	
	@Column(name="actionName",length=50)
	private String actionName;//动作名称（例如增加，删除）
	@Column(name="path",length=100,nullable=false)
	private String path;//相对于主目录的路径
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime;//创建时间
	@Column(name="orders",nullable=false)
	private Integer orders;//排序
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "moudleid")     
	private Module module ;//模块

	/**
	 * @return the orders
	 */
	public Integer getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(Integer orders) {
		this.orders = orders;
	}



	
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}


	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
	}

	
 

    


    

}