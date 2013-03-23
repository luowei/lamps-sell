package com.vvvv.module.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.vvvv.common.model.IdEntity;
import com.vvvv.lamps.model.Complany;
import com.vvvv.user.model.Role;

/**
 * @className:Menu.java
 * @classDescription:菜单对象
 * @author:xiayingjie
 * @createTime:2010-7-5
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends IdEntity  implements java.io.Serializable{
	
	private static final long serialVersionUID = -4522259504634124036L;
	
	@Column(name="menuName",length=50,nullable=false)
	private  String menuName;//显示名称
	@Column(name="parentId",nullable=false)
	private  Integer parentId=0;//父id,如果为顶级菜单则为0
	@Column(name="url",length=200,nullable=false)
	private  String url;//链接url
	@Column(name="imageUrl",length=200,nullable=false)
	private  String imageUrl;//image链接url
	@Column(name="dept")
	private Integer  dept;//层次
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime;//创建时间
	@Column(name="orders")
	private String orders ;//排序
	
	@Transient
	private List<Menu> childList = new ArrayList<Menu>();//子菜单
	
	//多对多
	@ManyToMany
	@JoinTable(name="rolemenu",inverseJoinColumns={@JoinColumn(name="roleid")},joinColumns={@JoinColumn(name="menuid")})
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Role> roleSet=new HashSet<Role>();
	
	
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	/**
	 * @return the childList
	 */
	public List<Menu> getChildList() {
		return childList;
	}
	/**
	 * @param childList the childList to set
	 */
	public void setChildList(List<Menu> childList) {
		this.childList = childList;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}
	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the dept
	 */
	public Integer getDept() {
		return dept;
	}
	/**
	 * @param dept the dept to set
	 */
	public void setDept(Integer dept) {
		this.dept = dept;
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
	 * @return the orders
	 */
	public String getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(String orders) {
		this.orders = orders;
	}

	

}
