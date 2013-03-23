package com.vvvv.lamps.model;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.vvvv.common.model.IdEntity;
import com.vvvv.common.tool.common.DateUtil;

/**
 * @className:complany.java
 * @classDescription:公司
 * @author:wei.luo
 * @createTime:2012-4-3
 */
@Entity
@Table(name = "complany")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Complany extends IdEntity {
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 4488844590918082940L;
	@Column(name="name",nullable=false)
	private String name;
	@Column(name="en_name",length=50)
	private String enName;
	@Column(name="short_name",length=10)
	private String shortName;
	@Column(name="type")
	private String type;
	@Column(name="logo_img",length=100)
	private String logo;
	@Column(name="img",length=100)
	private String img;
	@Column(name="business_mode")
	private String businessMode;
	@Column(name="primary_business",length=200)
	private String primaryBusiness;
	@Column(name="address",length=100)
	private String address;
	@Column(name="business_location",length=100)
	private String businessLocation;
	@Column(name="boss_name",length=20)
	private String bossName;
	@Column(name="detail",length=100)
	private String detail;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="orders")
	private Integer orders;
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="complany")
	@OrderBy("orders")
	private Set<Message> messages=new HashSet<Message>();
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="complany")
	@OrderBy("orders")
	private Set<News> newsSet=new HashSet<News>();
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="complany")
	@OrderBy("orders")
	private Set<Tradinfo> tradinfoSet=new HashSet<Tradinfo>();
	
	//一对一
	@OneToOne(mappedBy = "complany") 
	private Contact contact;
	
	//多对多
	@ManyToMany
	@JoinTable(name="complanyproduct",inverseJoinColumns={@JoinColumn(name="product_id")},joinColumns={@JoinColumn(name="complany_id")})
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Product> productSet=new HashSet<Product>();
	
	public Set<Product> getProductSet() {
		return productSet;
	}
	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}
	public Set<Tradinfo> getTradinfoSet() {
		return tradinfoSet;
	}
	public void setTradinfoSet(Set<Tradinfo> tradinfoSet) {
		this.tradinfoSet = tradinfoSet;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getBusinessMode() {
		return businessMode;
	}
	public void setBusinessMode(String businessMode) {
		this.businessMode = businessMode;
	}
	public String getPrimaryBusiness() {
		return primaryBusiness;
	}
	public void setPrimaryBusiness(String primaryBusiness) {
		this.primaryBusiness = primaryBusiness;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBusinessLocation() {
		return businessLocation;
	}
	public void setBusinessLocation(String businessLocation) {
		this.businessLocation = businessLocation;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
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
	public Complany() {
		super();
		this.createtime = DateUtil.datetimeToDate();
	}
	
}
