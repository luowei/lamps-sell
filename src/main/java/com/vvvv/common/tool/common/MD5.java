package com.vvvv.common.tool.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	private MessageDigest md;
	private static MD5 md5;

	private MD5() {
		try {
			md = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("没有这种算法");
		}
	}

	// 产生一个MD5实例
	public static MD5 getInstance() {
		if (null != md5)
			return md5;
		else {
			makeInstance();
			return md5;
		}
	}

	// 保证同一时间只有一个线程在使用MD5加密
	private static synchronized void makeInstance() {
		if (null == md5)
			md5 = new MD5();
	}

	public String createMD5(String pass) {
		md.update(pass.getBytes());
		byte[] b = md.digest();
		return byteToHexString(b);
	}

	private String byteToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String temp = "";
		for (int i = 0; i < b.length; i++) {
			temp = Integer.toHexString(b[i] & 0Xff);
			if (temp.length() == 1)
				temp = "0" + temp;
			sb.append(temp);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * md5加密取中间6位字符
	 * @author:wangjia
	 * @createTime:2011-6-30
	 * @param beforeStr
	 * @return
	 */
	public static String get6BitMD5EncStr(String beforeStr){
		if(null == beforeStr || "".equals(beforeStr)){
			return beforeStr;
		}
		String afterStr = null;
		try{
			afterStr = MD5.getInstance().createMD5(beforeStr);
		}catch(ArrayIndexOutOfBoundsException ex){
			afterStr = "";
		}
		if(afterStr.length()==32){
			afterStr = afterStr.substring(13,19);
		}
		return afterStr;
	}

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		MD5 md5 = MD5.getInstance();
		String string2=md5.get6BitMD5EncStr("高兴");
		System.out.println(string2);
		System.out.println(md5.createMD5(string2));
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - beginTime);
	}
}
