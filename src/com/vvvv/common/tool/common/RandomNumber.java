package com.vvvv.common.tool.common;

import java.util.Calendar;
import java.util.Random;

/**
 * 获取随机ID
 * @author fang.huang
 * @date 2010-12-28
 */
public class RandomNumber {
	public static String getRowId(){
		Calendar calendar = Calendar.getInstance();
		StringBuffer sbf = new StringBuffer();
		sbf.append(calendar.get(Calendar.YEAR));   
		sbf.append(calendar.get(Calendar.MONTH)+1);   
		sbf.append(calendar.get(Calendar.DAY_OF_MONTH)); 
		sbf.append(calendar.get(Calendar.HOUR_OF_DAY));
		sbf.append(calendar.get(Calendar.MINUTE));
		String mini = ""+calendar.get(Calendar.MILLISECOND);
		if(mini.length() < 2){
			mini = "00" + mini;
		}else if(mini.length() < 3){
			mini = "0" + mini;
		}
		sbf.append(mini);
		Random rand = new Random();         
        for (int i=0;i<4;i++) {            
            sbf.append(rand.nextInt(10));
        }
		return sbf.toString();
	}
	
	public static void main(String[] args) {
		for(int i = 0 ; i < 1000 ; i ++){
			System.out.println(getRowId().length());
			for(int j = 0 ; j < 100000000 ; j ++){
			}
		}
	}
}
