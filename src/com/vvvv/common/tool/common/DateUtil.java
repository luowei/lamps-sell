package com.vvvv.common.tool.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;


/**
 * @className:DateUtil.java
 * @classDescription:日期处理类
 * @author:xiayingjie
 * @createTime:2010-12-9
 */

public class DateUtil extends DateUtils{
	
	//常量
	 public static final String DEFAULT_Calendar_ALL = "yyyy-MM-dd HH:mm:ss";  
	 public static final String DEFAULT_Calendar_DATE = "yyyy-MM-dd"; 
	 public static final String DEFAULT_Calendar_TIME = "HH:mm:ss";
	 
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();;

	/**
	 * 获取当前时间（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String datetime(){
		dateFormat.applyPattern(DEFAULT_Calendar_ALL);
		return dateFormat.format(Calendar.getInstance().getTime());
	}
	/**
	 * 获取当前时间（日期对象 yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static Date datetimeToDate(){
		return Timestamp.valueOf(datetime());
	}
	/**
	 *  获取当前日期（yyyy-MM-dd）
	 * @return
	 */
	public static String date(){
		dateFormat.applyPattern(DEFAULT_Calendar_DATE);
		return dateFormat.format(Calendar.getInstance().getTime());
	}
	/**
	 * 获取当前时间（HH:mm:ss）
	 * @return
	 */
	public static String time(){
		dateFormat.applyPattern(DEFAULT_Calendar_TIME);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	public static void main(String[]arg){
	//	System.out.print(StringUtils.indexOfAny("abcdefg", new String[]{"f","g"}));
		System.out.println(datetimeToDate());
	}



	

}
