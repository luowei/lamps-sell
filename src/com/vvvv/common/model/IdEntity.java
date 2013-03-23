package com.vvvv.common.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @className:IdEntity.java
 * @classDescription:统一定义id的entity基类
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -8856284847374280436L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//@GeneratedValue(generator = "system-uuid")
	//@GenericGenerator(name = "system-uuid", strategy = "uuid")
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



}
