package com.vvvv.common.tool.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;


import freemarker.cache.MruCacheStorage;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @className:FreemarkerUtil.java
 * @classDescription:freemarker操作类
 * @author:xiayingjie
 * @createTime:2010-12-2
 */

public class FreemarkerUtil {
	private static String encoding = "UTF-8";
	private static String path = null;
	private static Configuration cfg = null;
	private static Template template = null;

	/**
	 * 初始化配置对象
	 * 
	 * @param dir
	 * @return
	 */
	public static Configuration initConfig() {
		if (cfg == null) {
			cfg = new Configuration();
			// 设置默认包装
			setObjectWrapper();
			// 设置缓存
			setCache(20, 250);
			// 设置cfg编码
			setCfgEncoding(encoding);
		}
		return cfg;
	}

	// ------------------设置模版 ，三种模式----------------
	/**
	 * 设置模板存在的目录(如果目录不存在则抛出异常)
	 * 
	 * @param dir
	 *            模板存在的目录
	 */
	public static void setTemplet(String dir) {
		try {
			cfg.setDirectoryForTemplateLoading(new File(dir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * class方式加载模板（比上一种要稳定和安全，你可以把资源文件或者图标打包到.jar文件中）
	 * 
	 * @param object
	 * @param prefix
	 */
	public static void setTemplet(Class object, String prefix) {
		cfg.setClassForTemplateLoading(object, prefix);
	}

	/**
	 * web应用上下文加载模板
	 * 
	 * @param servletContext
	 *            web应用上下文
	 * @param path
	 *            基路径（相对于WEB—INF目录）
	 */
	public static void setTemplet(Object servletContext, String path) {
		cfg.setServletContextForTemplateLoading(servletContext, path);
	}

	/**
	 * 从多个位置加载模板 例： FileTemplateLoader ftl=new FileTemplateLoader(new
	 * File("temp/template")); FileTemplateLoader ft2=new FileTemplateLoader(new
	 * File("uses/template")); ClassTemplateLoader ct1=new
	 * ClassTemplateLoader(getClass(),""); TemplteLoader[]loaders=new
	 * TemplateLoader{ft1,ft2,ct1}; 加载模板会先从ft1中查找，如果没有则ft2,其次ct1
	 * 
	 * @param loaders
	 *            多模板位置
	 */
	public static void SetMultiTemplet(TemplateLoader[] loaders) {
		MultiTemplateLoader multiTemplate = new MultiTemplateLoader(loaders);
		cfg.setTemplateLoader(multiTemplate);
	}

	// ---------------对config的设置--------------
	/**
	 * 设置对象包装模式，这里设置成默认包装
	 * 
	 */
	public static void setObjectWrapper() {
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	/**
	 * 设置freemarker缓存（建议设置成非0的数值，不然每次生成会导致重复加载） 如果一级缓存满的话，使用的少的文件将推送到二级缓存
	 * 
	 * @param strong
	 *            引用区域 (一级缓存)20
	 * @param soft
	 *            弱引用区域(二级缓存)250
	 */
	public static void setCache(int strong, int soft) {
		cfg.setCacheStorage(new MruCacheStorage(strong, soft));
		// cfg.setSetting(Configuration.CACHE_STORAGE_KEY,"strong:"+strong+",soft:"+soft);
	}

	/**
	 * 清除缓存
	 */
	public static void clearCache() {
		cfg.clearTemplateCache();
	}

	/**
	 * 设置Configuration默认编码
	 * 
	 * @param encoding
	 *            编码例如"UTF8","ISO8859-1"
	 */
	private static void setCfgEncoding(String encoding) {
		cfg.setDefaultEncoding(encoding);
	}

	// ----------template操作----------

	/**
	 * 获取模板(如果文件不存在则抛出异常)
	 * 
	 * @param fileName
	 *            模板名
	 * @return Temlplate 模板对象
	 */
	public static Template getTemplate(String fileName) {
		try {
			template = cfg.getTemplate(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

	/**
	 * 设置模板编码
	 * 
	 * @param template
	 *            模板对象
	 * @param encoding
	 *            编码
	 */
	private static void setTempEncoding(String encoding) {
		template.setEncoding(encoding);
	}

	/**
	 * 获取输出对象
	 * 
	 * @param outFile
	 *            输出的文件路径
	 * @return Writer
	 */
	public static void process(Object object, String outFilePath,
			String encoding) {
		try {
			File outFile = new File(outFilePath);
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), encoding));
			// 合并数据和模型
			template.process(object, out);

			// 清除缓存
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取输出对象
	 * 
	 * @param outFile
	 *            输出的文件路径
	 * @return Writer
	 */
	public static String process(Object object) {
		String outString = null;
		StringWriter sw = new StringWriter();
		try {
			Writer out = new BufferedWriter(sw);
			// 合并数据和模型
			template.process(object, out);
			// 清除缓存
			out.flush();
			out.close();

			outString = sw.getBuffer().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outString;
	}

	/**
	 * 根据模版生成文件 简化操作
	 * 
	 * @param directory
	 *            template文件目录
	 * @param fileName
	 *            文件名
	 * @param outFilePath
	 *            输出文件
	 * @param object
	 *            模版
	 * @param encoding
	 *            编码
	 * @return
	 */
	public static String makeTemplate2Str(String directory, String fileName,
			Object object) {
		String out = null;
		try {
			// 初始化config
			initConfig();
			// 设置模版路径
			setTemplet(directory);
			// 获取模板
			getTemplate(fileName);
			// 设置模版编码
			setTempEncoding(encoding);
			// 输出文件
			out = process(object);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	/**
	 * 根据模版生成文件 简化操作
	 * 
	 * @param directory
	 *            template文件目录
	 * @param fileName
	 *            文件名
	 * @param outFilePath
	 *            输出文件
	 * @param object
	 *            模版
	 * @param encoding
	 *            编码
	 * @return
	 */
	public static boolean makeTemplate(String directory, String fileName,
			String outFilePath, Object object) {
		boolean flag = false;
		try {
			// 初始化config
			initConfig();
			// 设置模版路径
			setTemplet(directory);
			// 获取模板
			getTemplate(fileName);
			// 设置模版编码
			setTempEncoding(encoding);
			// 输出文件
			process(object, outFilePath, encoding);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取模板的路径
	 * 
	 * @return 返回模板路径
	 */
	public static String getPath() {
		if (path == null) {
		}
		return path;
	}

	public static void main(String[] args) {

		// String
		// directory=System.getProperty("user.dir")+"/src/com/xyj/com/tool/template/";
		// System.out.println(directory);
		// String outPath="D:/aa.xml";
		// String fileName="search.xml";
		//		
		// List<Music> l=new ArrayList<Music>();
		//		
		// Music m1=new Music();
		// m1.setMusicName("一千个伤心的理由");
		// m1.setSinger("张学友");
		// m1.setUrl("http://yyc.vvvv.com/yyc/client-data!esdown.action?id=90823");
		//		
		// Music m2=new Music();
		// m2.setMusicName("一路上&有你");
		// m2.setSinger("张学友");
		// m2.setUrl("http://yyc.vvvv.com/yyc/client-data!esdown.action?id=92671");
		//	   
		// l.add(m1);
		// l.add(m2);
		//		
		//	    
		// // 创建根哈希表
		// Map root = new HashMap();
		// // 在根中放入字符串"user"
		// root.put("musicList", l);
		// // 为"latestProduct"创建哈希表
		//		
		// // FreemarkerUtil.makeTemplate(directory,fileName,outFilePath,root);
		// for(int i=0;i<100000;i++){
		// FreemarkerUtil.makeTemplate2Str(directory,fileName,root);
		// System.out.println(i);
		// }
		// System.out.println(s);
	}
}
/**
 * 页面格式： 集合： <#if 集合list??> <#list 集合list as 自定义对象object > <#if object.属性=="">
 * ${object.属性} </#if> <#if 10<object.属性?length> ${object.属性[0..9]} <#else>
 * ${object.属性} </#if> </#list> </#if>
 * 
 * 对象： <#if object.属性==""> ${object.属性} </#if> <#if 10<object.属性?length>
 * ${object.属性[0..9]} <#else> ${object.属性} </#if> hashMap：同上
 * 
 * 条件判断： <#if condition> ... <#elseif condition2> ... <#else> ... </#if> 集合：
 * <#list sequence as item> ... </#list> item_index：这是一个包含当前项在循环中的步进索引的数值。
 * item_has_next：来辨别当前项是否是序列的最后一项的布尔值。 include指令：<#include "/common/navbar.html"
 * parse=false encoding="UTF-8"> parse：true-表示解析导入文件语法 import指令：<#import
 * "/libs/mylib.ftl" as my> <@my.copyright date="1999-2002"/>
 * noparse指令：不会在这个指令之间进行解析 compress指令：删除多余的空格，一行的前后空格 escape指令：<#escape x as
 * x?html>First name: ${firstName}Last name: ${lastName}</#escape> 等同于 First
 * name: ${firstName?html}Last name: ${lastName?html}
 * assign指令：创建一个新的变量，或者替换一个已经存在的变量<#assign name=value>
 * global指令：在所有命名空间都可用<#global name=value> local指令 ：定义 root 中局部对象<#local
 * name=value> setting指令：格式化输出的值：<#setting
 * name=value>-locale:en，en_US，en_US_MAC;
 * number_format:number,computer，currency，percent;
 * boolean_format:是"true,false"date_format，time_format，datetime_format："short"，
 * "long_medium"， "MM/dd/yyyy" url_escaping_charset：用来 URL 转义（比如${foo?url}）的字符集;
 * 
 * 函数：后面加问号表示 string?cap_first cap_first:转换首字母大写 lower_case：转换成小写
 * upper_case：转换成大写 size：序列中元素的个数 trim:去掉字符 int:数字的整数部分 html: 字符串中所有的特殊 HTML
 * 字符都需要用实体引用来代替（比如<代替&lt;） 定义宏：<#macro copyright date> ${date}<#macro>
 */
