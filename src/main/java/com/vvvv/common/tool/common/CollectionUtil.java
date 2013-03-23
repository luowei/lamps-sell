package com.vvvv.common.tool.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;


/**
 * @className:CollectionUtil.java
 * @classDescription:集合操作类
 */

public class CollectionUtil {


	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static List extractElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtil.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = extractElementPropertyToList(collection, propertyName);
		return StringUtils.join(list.toArray(), separator);
	}

}
