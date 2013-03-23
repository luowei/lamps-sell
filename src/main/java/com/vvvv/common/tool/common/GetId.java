package com.vvvv.common.tool.common;

import java.util.Calendar;
import java.util.Random;

public final class GetId 
{
	
	private GetId()
	{
	}
	/**
	 * 根据当前时间得到编号
	 * @return String
	 */
	public static String getNewId(){
		Calendar calendar = Calendar.getInstance();
		StringBuffer sbf = new StringBuffer();
		sbf.append(calendar.get(Calendar.YEAR));   
		sbf.append(calendar.get(Calendar.MONTH));   
		sbf.append(calendar.get(Calendar.DAY_OF_MONTH)); 
		sbf.append(calendar.get(Calendar.HOUR_OF_DAY));
		sbf.append(calendar.get(Calendar.MINUTE));
		sbf.append(calendar.get(Calendar.MILLISECOND));
		
		Random rand = new Random();         
        for (int i=0;i<4;i++) {            
            sbf.append(rand.nextInt(10));
        }
		return sbf.toString();
	}
	
	public static void main(String[] args) {
		for(int i = 0 ; i < 10 ; i ++){
			System.out.println(getNewId());
		}
	}
}

