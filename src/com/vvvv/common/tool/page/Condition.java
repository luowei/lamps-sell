package com.vvvv.common.tool.page;
/**
 * @className:Condition.java
 * @classDescription:条件对象
 * @author:xiayingjie
 * @createTime:2010-7-14
 */
public class Condition {
	private String name;//属性名称
	private String value;//属性值
	private String url;//url
	private String displayName;//显示名称
	private boolean flag=true;//过滤条件时是否显示
	
	public Condition(){}
	public Condition(String name,String value,String displayName){
		this.name=name;
		this.value=value;
		this.displayName=displayName;
	}
	public Condition(String name,String value,String displayName,boolean flag){
		this.name=name;
		this.value=value;
		this.displayName=displayName;
		this.flag=flag; 
	}
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
