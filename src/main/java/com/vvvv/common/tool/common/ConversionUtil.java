package com.vvvv.common.tool.common;

import org.apache.commons.lang.StringUtils;

/**
 * @className:ConversionUtil.java
 * @classDescription:通用转换类
 */
public class ConversionUtil {
	/**
	 * 
	 * 数字型字符串转换成int型,如果不是数字型字符串则返回0
	 * @param s
	 * @return
	 */
	public static  Integer StringToInteger(String s){
		int result = 0;
		if(null != s && !"".equals(s) && StringUtils.isNumeric(s)){
			result = Integer.parseInt(s);
		}
		return result;
	}
}

