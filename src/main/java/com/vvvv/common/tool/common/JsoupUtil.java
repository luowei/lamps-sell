package com.vvvv.common.tool.common;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @className:JsoupUtil.java
 * @classDescription:网页解析工具
 * @author:wangjia
 * @createTime:2011-6-27
 */
public class JsoupUtil {
	
	/**
	 * 
	 * 根据url获取jsoup的Document对象
	 * @author:wangjia
	 * @createTime:2011-6-27
	 * @param url
	 * @return
	 */
	public static Document getDocument(String url){
		Document doc = null;
		try {
			 doc = Jsoup.connect(url).timeout(3000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 根据url获取jsoup的Document对象
	 * 前提条件为先用其它工具获取网页内容
	 * @author:wangjia
	 * @createTime:2011-6-27
	 * @param url
	 * @return
	 */
	public static Document parseDocument(String url){
		Document doc = null;
		String html = URLUtil.readHtmlByUrl(url);
		try{
			if(!"".equals(html)){
				doc = Jsoup.parse(html);
			}
		}catch(Exception ex){
		}
		return doc;
	}
	
}

