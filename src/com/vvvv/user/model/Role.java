package com.vvvv.user.model;

import java.util.Date;
import java.util.HashSet;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.vvvv.common.model.IdEntity;
import com.vvvv.module.model.Menu;



/**
 * @className:Role.java
 * @classDescription:角色类
 * @author:xiayingjie
 * @createTime:2010-7-5
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -1973396954874955071L;
	
	@Column(name="roleName",length=50,nullable=false)
	private String roleName;//角色名称
	@Column(name="roleInfo",length=1000)
	private String roleInfo;//角色介绍
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime;//创建时间

	@ManyToMany
	@JoinTable(name="roleaction",inverseJoinColumns={@JoinColumn(name="actionId")},joinColumns={@JoinColumn(name="roleId")})
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("id")
	//集合中对象id的缓存
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Action> actions = new HashSet<Action>(0);
	
	@ManyToMany
	@JoinTable(name="rolemenu",inverseJoinColumns={@JoinColumn(name="menuId")},joinColumns={@JoinColumn(name="roleId")})
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("id")
	//集合中对象id的缓存
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Menu> menus=new HashSet<Menu>(0);

	//多对多
	@ManyToMany
	@JoinTable(name="rolemenu",inverseJoinColumns={@JoinColumn(name="menuid")},joinColumns={@JoinColumn(name="roleid")})
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Menu> menuSet=new HashSet<Menu>();
	
	public Set<Menu> getMenuSet() {
		return menuSet;
	}

	public void setMenuSet(Set<Menu> menuSet) {
		this.menuSet = menuSet;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleInfo
	 */
	public String getRoleInfo() {
		return roleInfo;
	}

	/**
	 * @param roleInfo the roleInfo to set
	 */
	public void setRoleInfo(String roleInfo) {
		this.roleInfo = roleInfo;
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
	 * @return the actions
	 */
	public Set<Action> getActions() {
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}


	/**
	 * @return the menus
	 */
	public Set<Menu> getMenus() {
		return menus;
	}

	/**
	 * @param menus the menus to set
	 */
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

}