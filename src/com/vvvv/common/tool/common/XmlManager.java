package com.vvvv.common.tool.common;

import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA Date: 2011-1-11 15:53:51 Note Dom4j遍历解析XML
 */
public class XmlManager {
	/**
	 * 获取指定xml文档的Document对象
	 * 
	 * @param xmlFilePath
	 *            xml文件路径
	 * @return Document对象
	 */
	public static Document parse2Document(InputStream is) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * 获取指定xml文档的Document对象
	 * 
	 * @param xmlFilePath
	 *            xml文件路径
	 * @return Document对象
	 */
	public static Document parse2Document(URL url) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
}