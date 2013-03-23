package com.vvvv.lamps.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.vvvv.common.model.IdEntity;

/**
 * @className:Contact.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-3
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contact extends IdEntity{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 3971370255219632195L;
	@Column(name="name",length=20)
	private String name;
	@Column(name="tel",length=12)
	private String tel;
	@Column(name="phone",length=11)
	private String phone;
	@Column(name="qq",length=12)
	private String qq;
	@Column(name="email",length=30)
	private String email;
	@Column(name="http",length= 100)
	private String http;
	@Column(name="fax",length=20)
	private String fax;
	@Column(name="zip",length=6)
	private String zip;
	@Column(name="addr",length=50)
	private String addr;
	@Column(name="orders")
	private Integer orders;
	
	//一对一
	@OneToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)  
    @JoinColumn(name="complany_id")
	private Complany complany;
    
	public Complany getComplany() {
		return complany;
	}
	public void setComplany(Complany complany) {
		this.complany = complany;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public String getHttp() {
		return http;
	}
	public void setHttp(String http) {
		this.http = http;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Contact() {
		super();
	}
	
}
