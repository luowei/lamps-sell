package com.vvvv.common.tool.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * @className:PropertiesOperate.java
 * @classDescription:属性文件操作实现
 * @author:xiayingjie
 * @createTime:Apr 2, 2008
 */
public class PropertiesOperate{

	/**
	 * 根据文件路径和key得出value
	 * 
	 * @param filePath
	 *            文件路径
	 * @param key
	 *            关键字
	 * @return String
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String readValue(String filePath, String key) {
		// 定义properties对象
		Properties props = new Properties();
		String value = "";
		// 加载properties文件
		try {
			props.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 根据key取出value
		value = props.getProperty(key);
		return value;
	}

	/**
	 * 读取properties的全部信息
	 * 
	 * @param filePath
	 */
	public HashMap readProperties(String filePath) {
		HashMap map = new HashMap();
		// 定义Properties对象
		Properties props = new Properties();
		try {
			// 创建文件流对象，然后将其放入Buffer中
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			// 将流加载
			props.load(in);
			// 获取所有的key，放入枚举类型中
			Enumeration en = props.propertyNames();
			// 遍历枚举，根据key取出value
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String property = props.getProperty(key);
				// 将值放入key中
				map.put(key, property);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 写入properties信息
	 * 
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 */
	public void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties props = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			props.load(fis);
			fis.close();
			// 调用Hashtable的方法put。使用getProperty方法提供并行性
			// 强制要求为属性的键和值使用字符串。返回值是Hashtable调用put的结果
			OutputStream fos = new FileOutputStream(filePath);
			props.setProperty(parameterName, parameterValue);
			// 以适合使用load方法加载到Properties表中的格式，将此Properties表中的属性列表（键和元素对）写入输出流
			// props.store(fos, "Update '" + parameterName + "' value");
			props.store(fos, "");
			// fos.close();------------------------------------important
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String filePath = "c://ff.properties";
		PropertiesOperate p = new PropertiesOperate();
		filePath = "E://vvvv//song//song//src//init.properties";
		filePath = "C://Documents%20and%20Settings//Administrator//My Documents//init.properties";
		int b = (int) Math.ceil(Math.random() * 3) + 8;// 9~11
		int d = (int) Math.ceil(Math.random() * 3) + 12;// 13~15
		int e = (int) Math.ceil(Math.random() * 3) + 16;// 17~19
	}
}
