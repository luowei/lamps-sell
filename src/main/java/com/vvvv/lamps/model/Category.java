package com.vvvv.lamps.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.vvvv.common.model.IdEntity;

/**
 * @className:Category.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-3
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category extends IdEntity{
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -2204625038384131614L;
	@Column(name="parent_id",nullable=false)
	private Integer parentId;
	@Column(name="name",length=20)
	private String name;
	@Column(name="status")
	private String status;
	@Column(name="product_count")
	private Integer productCount;;
	@Column(name="createtime")
	private Date createTime;
	@Column(name="orders")
	private Integer orders;
	
	@Transient
	private List<Category> childCategoryList=new ArrayList<Category>();	//子类
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="category")
	@OrderBy("orders")
	private Set<Product> productSet=new HashSet<Product>();
	
	
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
//		int count=0;
//		if(category.getId()!=1 && category.getParentId()==1){
//			for(Category cate:category.getChildCategoryList()){
//				count+=cate.getProductSet().size();
//			}
//		}
//		if(category.getParentId()>1){
//			count+=category.getProductSet().size();
//		}
//		if(childCategoryList==null){
			this.productCount = productCount;
//		}
//		if(childCategoryList!=null){
//			for(Category cate:childCategoryList){
//				count=count+cate.getProductCount();
//			}
//			this.productCount=count;
//		}
	}
	
	public Set<Product> getProductSet() {
		return productSet;
	}
	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}
	public List<Category> getChildCategoryList() {
		return childCategoryList;
	}
	public void setChildCategoryList(List<Category> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public Category(Integer id) {
		this.id=id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Category() {
		super();
		//this.createtime = DateUtil.datetimeToDate();
	}
}
