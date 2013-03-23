package com.vvvv.user.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.vvvv.common.model.IdEntity;
import com.vvvv.lamps.model.Message;
import com.vvvv.lamps.model.News;
import com.vvvv.lamps.model.Orders;
import com.vvvv.lamps.model.Product;

/**
 * @className:Userinfo.java
 * @classDescription:用户类
 */
@Entity
@Table(name = "userinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfo  extends IdEntity implements java.io.Serializable{

	private static final long serialVersionUID = 6749326735019568778L;
	
	@Column(name="userName",length=50,nullable=false)
	private String userName;//用户名
	@Column(name="password",length=50,nullable=false)
	private String userPassword;//用户密码
	@Column(name="sex",length=2)
	private String sex;//性别 1为男，2为女
	@Column(name="email",length=100)
	private String email;//email
	@Column(name="qq",length=12,nullable=false)
	private String qq;//qq
	@Column(name="isEnable")
	private Integer isEnable;//是否可用
	@Column(name="online")
	private Long online;//在线时长
	@Column(name="score")
	private Integer score;//积分
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime;//注册时间
	@Column(name="content",length=1000)
	private String content;//个性签名  
	@Column(name="isBetter")
    private Integer isBetter=0;//是否为高级用户
    
	//多对多
	@ManyToMany
	@JoinTable(name="userrole",inverseJoinColumns={@JoinColumn(name="roleId")},joinColumns={@JoinColumn(name="userId")})
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("id")
	//集合中对象id的缓存
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Role> roles=new HashSet<Role>(0);
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
	@OrderBy("orders")
	private Set<Message> messages = new HashSet<Message>(0);
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
	@OrderBy("orders")
	private  Set<News> newsSet=new HashSet<News>();
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
	@OrderBy("id")
	private Set<Orders> ordersSet=new HashSet<Orders>();
	
	//多对多
	@ManyToMany
	@JoinTable(name="userproduct",inverseJoinColumns={@JoinColumn(name="product_id")},joinColumns={@JoinColumn(name="user_id")})
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Product> productSet=new HashSet<Product>();
	
	
	public Set<Orders> getOrdersSet() {
		return ordersSet;
	}

	public void setOrdersSet(Set<Orders> ordersSet) {
		this.ordersSet = ordersSet;
	}

	public Set<News> getNewsSet() {
		return newsSet;
	}

	public void setNewsSet(Set<News> newsSet) {
		this.newsSet = newsSet;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the isEnable
	 */
	public Integer getIsEnable() {
		return isEnable;
	}

	/**
	 * @param isEnable the isEnable to set
	 */
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	/**
	 * @return the online
	 */
	public Long getOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(Long online) {
		this.online = online;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}





	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the isBetter
	 */
	public Integer getIsBetter() {
		return isBetter;
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
	 * @param isBetter the isBetter to set
	 */
	public void setIsBetter(Integer isBetter) {
		this.isBetter = isBetter;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


}