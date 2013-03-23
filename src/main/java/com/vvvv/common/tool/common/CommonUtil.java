package com.vvvv.common.tool.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @className:CommonUtil.java
 * @classDescription:公共工具类
 */
public class CommonUtil {
	/**
	 * 从list里取得count个随机值
	 * 
	 * @param list
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getRandomList(List list, int count) {
		Random r = new Random();
	//	System.out.println(list.size());
		List b = null;
		if (null != list) {
			if (count >= list.size()) {
				return list;
			}
			List a = new ArrayList();
			a.addAll(list);
			
			b = new ArrayList();
			int index;
			for (int i = 0; i < count; i++) {
				index = r.nextInt(a.size());
				b.add(a.get(index));
				a.remove(index);
			}
		}
		return b;
	}
	
	/**
	 * 随机生成 有n个字母组成的字符串	
	 * @param n
	 *        n个字母
	 * @return  String
	 * @throws Exception
	 */
	public static String getRandomString(int n) throws Exception{
		StringBuffer buffer = new StringBuffer();
		char [] ch = new char[n+1];		        
        for(int i=1; i<ch.length; i++){
             ch[i] = (char) ( Math.random()*26 + 97); // 'a'~'z' 97~122  ,'A'~'Z' 65~90	
             buffer.append(ch[i]);
       }     
	  return buffer.toString();
	}
	
	/**
	 * 将输入流转化成字符串	
	 * @param is  
	 *         输入流
	 * @return String
	 * @throws Exception
	 */
	public static String inputStream2String(InputStream is) throws Exception{
	    BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));
	    StringBuffer buffer = new StringBuffer();
	    String line = "";
	    while ((line = in.readLine()) != null){
	      buffer.append(line);
	    }		   
	    return buffer.toString();
	}
	
}
