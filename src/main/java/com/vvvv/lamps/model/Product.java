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
import javax.persistence.ManyToOne;
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
 * @className:product.java
 * @classDescription:产品
 * @author:wei.luo
 * @createTime:2012-4-3
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends IdEntity{

	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -8678901684525887828L;
	@Column(name="name",length=50,nullable=false)
	private String name;
	@Column(name="type",length=20)
	private String type;
	@Column(name="status")
	private String status;
	@Column(name ="sales_valume",length=11)
	private Integer salesValume;
	@Column(name="produce_area",length=100)
	private String produceArea;
	@Column(name="small_img",length=100)
	private String smallImg;
	@Column(name="big_img",length=100)
	private String bigImg;
	@Column(name="detail",length=2048)
	private String detail;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="price")
	private double price;
	@Column(name="num")
	private Integer num;
	@Column(name="min_num")
	private Integer minNum;
	@Column(name="orders")
	private Integer orders;
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "category_id")     
	private Category category ;

	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="product")
	@OrderBy("orders")
	private Set<Message> messages=new HashSet<Message>();
	
	//一对多
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="product")
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<OrderRow> orderRowSet=new HashSet<OrderRow>();
	
	//一对一
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "product") 
	private Tradinfo tradinfo;
	
	//多对多
	@ManyToMany
	@JoinTable(name="complanyproduct",inverseJoinColumns={@JoinColumn(name="complany_id")},joinColumns={@JoinColumn(name="product_id")})
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Complany> complanySet=new HashSet<Complany>();
	
	
	public Set<Complany> getComplanySet() {
		return complanySet;
	}

	public void setComplanySet(Set<Complany> complanySet) {
		this.complanySet = complanySet;
	}

	public Set<OrderRow> getOrderRowSet() {
		return orderRowSet;
	}

	public void setOrderRowSet(Set<OrderRow> orderRowSet) {
		this.orderRowSet = orderRowSet;
	}

	public Tradinfo getTradinfo() {
		return tradinfo;
	}

	public void setTradinfo(Tradinfo tradinfo) {
		this.tradinfo = tradinfo;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSalesValume() {
		return salesValume;
	}

	public void setSalesValume(Integer salesValume) {
		this.salesValume = salesValume;
	}

	public String getProduceArea() {
		return produceArea;
	}

	public void setProduceArea(String produceArea) {
		this.produceArea = produceArea;
	}

	public String getSmallImg() {
		return smallImg;
	}

	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}

	public String getBigImg() {
		return bigImg;
	}

	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
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
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setFileds(Product product){
		//this.name = product.getName();
		this.type = product.getType();
		this.status = product.getStatus();
		this.produceArea = product.getProduceArea();
		this.smallImg = product.getSmallImg();
		this.bigImg = product.getBigImg();
		this.detail = product.getDetail();
		this.createtime = DateUtil.datetimeToDate();
		this.price = product.getPrice();
		this.orders = product.getOrders();
		this.category = product.getCategory();
		this.messages = product.getMessages();
		this.tradinfo = product.getTradinfo();
		this.num += product.getNum();
		this.minNum = product.getMinNum();
	}

	public Product() {
		super();
		this.createtime = DateUtil.datetimeToDate();
	}
	
	
}
