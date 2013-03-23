package com.vvvv.common.tool.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLUtil {

	/**
	 * 读取网页中的源文件
	 * 
	 * @param path
	 *            网页文件路径+
	 * @return
	 */
	public static String readHtmlByUrl(String path) {
		String str = "";
		StringBuffer sb = new StringBuffer("");
		InputStream is = null;
		BufferedReader br = null;
		try {
			URL url = new URL(path);
			URLConnection urlcon = url.openConnection();
			urlcon.setConnectTimeout(1000);
			is = url.openStream();
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
				if (null != br) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * 在此添加方法说明
	 * 
	 * @author:wangjia
	 * @createTime:2011-6-30
	 * @param url
	 * @return
	 */
	public static InputStream readInputStreamByUrl(String url) {
		InputStream is = null;
		URLConnection con = null;
		try {
			URL urlObj = new URL(url);
			con = urlObj.openConnection();
			con.setReadTimeout(2000);
			is = con.getInputStream();
		} catch (Exception ex) {
			try {
				if (is != null) {
					is.close();
				}
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return is;
	}
}
