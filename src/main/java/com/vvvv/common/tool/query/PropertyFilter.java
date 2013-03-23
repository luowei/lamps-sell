package com.vvvv.common.tool.query;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @className:PropertyFilter.java
 * @classDescription:过滤条件封装类
 */
public class PropertyFilter {

	// 属性比较类型.依次分别是等于，like,小于，大于，小于等于，大于等于,不等,包含
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, NE, IN;
	}

	// 属性数据类型. 
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}
	private String propertyName=null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private MatchType matchType = null;

	public PropertyFilter() {
	}

	/**
	 * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表. 
	 *                   例如：userName:LIKE_S
	 * @param value 待比较的值.
	 */
	public PropertyFilter(final String filterName, final String value) {
		
		String matchTypeStr = StringUtils.substringAfter(filterName, ":");
		String matchTypeCode = StringUtils.substringBefore(matchTypeStr, "_");
		String propertyTypeCode = StringUtils.substringAfter(matchTypeStr, "_");

		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
		}

		propertyName = StringUtils.substringBefore(filterName, ":").trim();
		if(null==value){
			this.propertyValue="";
		}else{
			this.propertyValue=value.trim();
		}
		

	}

	/**
	 * 获取比较值.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}
	/**
	 * 设置propertyValue
	 */
	public void setPropertyValue(String propertyValue){
		this.propertyValue=propertyValue;
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * 获取比较方式类型.
	 */
	public MatchType getMatchType() {
		return matchType;
	}
	/**
	 * 获取属性名称.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
