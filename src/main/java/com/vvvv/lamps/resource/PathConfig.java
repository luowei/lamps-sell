package com.vvvv.lamps.resource;

import java.util.Properties;

import com.vvvv.common.tool.common.PropertiesUtil;

/**
 * @className:PathConfig.java
 * @classDescription:从属性文件中获取各种路径
 * @author:wei.luo
 * @createTime:2012-3-23下午05:46:16 
 */
public class PathConfig {

	/**
	 * 初始给properties文件的路径赋值
	 */
	//public static String PROPFILE_PATH = "../webapps/ayyc/WEB-INF/classes/config/properties/pathConfig.properties";
	public static String PROPFILE_PATH = "WEB-INF/classes/config/properties/pathConfig.properties";
	/**
	 * 存放pathConfig.properties中的属性
	 */
	private Properties props=null;
	
	/**
	 * 试听下载地址
	 */
	public static String LIS_INTERFACE=null;
	
	/**
	 * 默认试听服务器ok
	 */
	public static String LISTENOK = "true" ;
	
	/**
	 * 分离的文件的父目录的绝对地址
	 */
	public static String PARENT_PATH=null;
	
	/**
	 * 备份的分离的文件的父目录的绝对地址
	 */
	public static String BAK_PARENT_PATH=null;
	
	/**
	 * upload文件夹
	 */
	public static String UPLOAD_CHILD_PATH=null;
	
	/**
	 * 频道内置图片的的存放路径
	 */
	public static String CHANNEL_PIC_CHILD_PATH=null;
	/**
	 * 频道小图片的存放路径
	 */
	public static String CHANNEL_NEWPIC_CHILD_PATH=null;
	/**
	 * 频道大图片的存放路径
	 */
	public static String CHANNEL_BIGPIC_CHILD_PATH=null;
	/**
	 * 歌曲图片的存放路径
	 */
	public static String MUSIC_PIC_CHILD_PATH=null;
	/**
	 * zip文件的存放路径
	 */
	public static String ZIP_CHILD_PATH=null;
	
	/**
	 * zip文件的发布地址
	 */
	public static String ZIP_PUBLISH_CHILD_PATH=null;
	
	/**
	 * PathConfig单对象
	 */
	private static PathConfig pathconfig=null;
	
	/**
	 * 把构造方法私有化
	 * @param path	properties文件的物理路径
	 */
	private PathConfig(String path){
		props=PropertiesUtil.getProperties(path, "utf-8");
		if(props!=null){
			PROPFILE_PATH=props.getProperty("property.path");
			LISTENOK=props.getProperty("listenok");
			LIS_INTERFACE=props.getProperty("lis.interface");
			PARENT_PATH=props.getProperty("parent.path");
			BAK_PARENT_PATH=props.getProperty("bak.parent.path");
			UPLOAD_CHILD_PATH=props.getProperty("upload.child.path");
			CHANNEL_PIC_CHILD_PATH=props.getProperty("channel.pic.child.path");
			CHANNEL_NEWPIC_CHILD_PATH=props.getProperty("channel.newpic.child.path");
			CHANNEL_BIGPIC_CHILD_PATH=props.getProperty("channel.bigpic.child.path");
			MUSIC_PIC_CHILD_PATH=props.getProperty("music.pic.child.path");
			ZIP_CHILD_PATH=props.getProperty("zip.child.path");
			ZIP_PUBLISH_CHILD_PATH=props.getProperty("zip.publish.child.path");
		}
	}
	
	/**
	 * 从属性文件中读取key、value，初始化PathConfig
	 * @author wei.luo
	 * @createTime 2012-3-24
	 * @param path  properties文件的物理路径
	 */
	public static synchronized void initPathConfig(String path){
		if(pathconfig==null){
			pathconfig = new PathConfig(path);
		}
	}
	/**
	 * 销毁pathconfig对象
	 * @author wei.luo
	 * @createTime 2012-3-31
	 */
	public static void destroy(){
		if(pathconfig!=null){
			pathconfig=null;
		}
	}
	
}
