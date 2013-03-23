package com.vvvv.common.tool.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className:FileInfo.java
 * @classDescription:上传文件属性设置
 * @author:xiayingjie
 * @createTime:2010-12-8
 */

public class FileInfo {
	
	private int sizeThreshold=100*1024;//设置缓冲值 ，这里默认为100Kb写入一次文件;系统 默认为10KB
	
    private File tempFolder=null;//临时文件目录,默认是new File(System.getProperty("java.io.tmpdir"))
    
    private long size= -1;//设置单个文件的上传大小,默认为-1表示不限制 以M为单位
    
    private long sizeMax = 100*1024*1024;//设置总上传文件的大小,设置为100M.默认为-1,表示无限制(单位为字节)
    
    private String encoding="utf-8";//设置编码为utf-8
    
	private String root;// 文件的根目录
	
	private String path;// 相对根目录的目录(格式：upload)不需要分隔符
	
	private int type=0;//保存文件的路径，默认为原文件路径。否则则是按照年月日来生成文件
    
    private Map<String,FileBean>fileMap=new  HashMap<String,FileBean>();//设置bean
    

	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}
	
	public File getTempFolder() {
		return tempFolder;
	}

	public void setTempFolder(File tempFolder) {
		this.tempFolder = tempFolder;
	}
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}

	public int getSizeThreshold() {
		return sizeThreshold;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Map<String, FileBean> getFileMap() {
		return fileMap;
	}


	public void setFileMap(Map<String, FileBean> fileMap) {
		this.fileMap = fileMap;
	}


	public String getRoot() {
		return root;
	}


	public void setRoot(String root) {
		this.root = root;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	











}
