package com.vvvv.common.tool.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * @className:EncodeUtil.java
 * @classDescription:编码操作类
 * @author:xiayingjie
 * @createTime:2010-12-9
 */

public class EncodeUtil {
	
	private static final String DEFAULT_ENCODING = "UTF-8";//设置默认编码为utf-8
	
	/**
	 * Hex编码
	 * @param input
	 * @return
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}
	/**
	 * Hex解码
	 * @param input
	 * @return
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex 解码出错", e);
		}
	}

	/**
	 * Base64编码
	 * @param input
	 * @return
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 *  Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符).
	 * @param input
	 * @return
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码
	 * @param input
	 * @return
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * URL 编码 默认设置为utf-8
	 * @param input
	 * @return
	 */
	public static String urlEncode(String input) {
		try {
			if(null==input||"".equals(input)){
				return "";
			}
			return URLEncoder.encode(input, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("urlEncode转换utf-8出错", e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8
	 * @param input
	 * @return
	 */
	public static String urlDecode(String input) {
		try {
			return URLDecoder.decode(input, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("URLEncode解码出错", e);
		}
	}

	/**
	 * Html 转码(将“<”转换成&lt;)
	 * @param html
	 * @return
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * Html 解码
	 * @param htmlEscaped
	 * @return
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}

	/**
	 * Xml 转码
	 * @param xml
	 * @return
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码
	 * @param xmlEscaped
	 * @return
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
	
	public static void main(String[]args){
		System.out.print(EncodeUtil.xmlUnescape("&lt;a&gt;&#25105;&amp;B&lt;/a&gt;"));
		
	}

}
