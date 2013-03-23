package com.vvvv.common.tool.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @className:PDFUtil.java
 * @classDescription:
 * @author:xiayingjie
 * @createTime:Jun 25, 2008
 */

public class PDFUtil {
	/**
	 * 向文件写入数据(会覆盖以前的内容)
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param data
	 *            需要写入的数据
	 * @return
	 */
	public static boolean writeFile(String filePath, String data) {
		boolean isflag = false;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(filePath);
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);
			bw.write(data);
			isflag = true;
		} catch (Exception ex) {
			System.out.println("写数据到文件时出错");
		}
		finally{
			try {
				if(null != bw){
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isflag;
	}

	/**
	 * 向文件写入数据(不覆盖以前的内容)
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param data
	 *            需要写入的数据 
	 * @return
	 */   
	public static boolean writeFileNotclear(String filePath, String data) {
		boolean isflag = false;
		FileOutputStream fos = null;
		OutputStreamWriter osw  = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(filePath,true);
			osw = new OutputStreamWriter(fos,"utf-8");
			bw = new BufferedWriter(osw);
			bw.write(data);
			bw.newLine();
			isflag = true;
		} catch (Exception ex) {
			ex.printStackTrace(); 
		}
		finally{
			try {
				if(null != bw){
					bw.flush();
					bw.close();
				}
				if(null != osw){
					osw.close();
				}
				if(null != fos){
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isflag;
	}
	public static String readFile(String filePath) {
		String result = "";
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			InputStreamReader isr1 = new InputStreamReader(fis1);
			BufferedReader br1 = new BufferedReader(isr1);
			result = br1.readLine();
			br1.close();
		} catch (Exception ex) {
			System.out.println("读文件时出错");
		}
		return result;
	}

	public static void main(String[] ages) {
	}
}
