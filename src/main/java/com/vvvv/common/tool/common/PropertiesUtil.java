package com.vvvv.common.tool.common;
 
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.Reader;  
import java.io.Writer;  
import java.text.MessageFormat;
import java.util.Enumeration;  
import java.util.HashMap;  
import java.util.Properties;  
  
/** 
 * @className:PropertiesUtil.java 
 * @classDescription:资源操作类 
 * @author:xiayingjie 
 * @createTime:2010-11-10 
 */  
public class PropertiesUtil {  
  
    /** 
     * 获取Properties对象(默认为utf-8字符编码) 
     *  
     * @param filePath 
     *            文件路径 
     * @return 
     */  
    public static Properties getProperties(String filePath) {  
        return getProperties(filePath, "utf-8");  
    }  
  
    /**
     * 获取Properties对象(默认为utf-8字符编码) 
     * @author wei.luo
     * @createTime 2012-3-28
     * @param filePath
     * @return
     */
    public static Properties getProperties(InputStream in) {  
    	if(in == null){
    		return null;
    	}
    	// 定义properties对象  
        Properties props = new Properties();
    	// 加载properties文件  
        try {
			props.load(new InputStreamReader(in,"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return props; 
    }
    
    /** 
     * 获取Properties对象 
     *  
     * @param filePath 
     *            文件路径 
     * @param encoding 
     *            字符编码 
     * @return 
     */  
    public static Properties getProperties(String filePath, String encoding) {  
        // 定义properties对象  
        Properties props = new Properties();  
  
        try {  
            // 判断文件是否存在，如果不存在则创建文件  
            File file = new File(filePath);  
  
            File dirFile = new File(file.getParent());  
            if (!dirFile.exists()) {  
                dirFile.mkdirs();  
            }  
            if (!file.exists()) {  
                file.createNewFile();  
  
            }  
            Reader rd = new InputStreamReader(new FileInputStream(file),  
                    encoding);  
            // 加载properties文件  
            props.load(rd);  
            rd.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return props;  
    }  
    /** 
     * 获取key对应的value值 默认为utf-8 
     * @param filePath 
     * @param name 
     * @return 
     */  
    public static String readValue(String filePath,String name){  
        return readValue(filePath,"utf-8", name);  
    }  
    /** 
     * 获取key对应的value值 
     * @param filePath 
     * @param encoding 
     * @param name 
     * @return 
     */  
    public static String readValue(String filePath,String encoding,String name){  
        Properties props=getProperties(filePath,encoding);  
        return props.getProperty(name);  
    }  
  
    /** 
     * 写资源文件(默认为utf-8编码) 
     *  
     * @param filePath 
     *            文件路径 
     * @param name 
     *            名称 
     * @param value 
     *            值 
     */  
    public static void write(String filePath, String name, String value) {  
        write(filePath, "utf-8", name, value);  
    }  
  
    /** 
     * 写资源文件 
     *  
     * @param filePath 
     *            文件路径 
     * @param encoding 
     *            文件编码 
     * @param name 
     *            名称 
     * @param value 
     *            值 
     */  
    public static void write(String filePath, String encoding, String name,  
            String value) {  
        try {  
            Properties props = getProperties(filePath, encoding);  
            props.setProperty(name, value);  
          
            Writer osw = new OutputStreamWriter(new FileOutputStream(filePath),  
                    encoding);  
            // 以适合使用load方法加载到Properties表中的格式，将此Properties表中的属性列表（键和元素对）写入输出流  
            // props.store(fos, "Update '" + parameterName + "' value");  
  
            props.store(osw, "");  
            osw.close();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    }  
  
    /** 
     * 读取properties的全部信息 
     *  
     * @param filePath 
     */  
    public static HashMap readAll(String filePath) {  
        return readAll(filePath, "utf-8");  
    }  
  
    /** 
     * 读取properties的全部信息 
     *  
     * @param filePath 
     *            文件路径 
     * @param encoding 
     *            字符编码 
     */  
    public static HashMap readAll(String filePath, String encoding) {  
        HashMap map = new HashMap();  
        Properties props = getProperties(filePath, encoding);  
  
        // 获取所有的key，放入枚举类型中  
        Enumeration en = props.propertyNames();  
        // 遍历枚举，根据key取出value  
        while (en.hasMoreElements()) {  
            String key = (String) en.nextElement();  
            String property = props.getProperty(key);  
            // 将值放入key中  
            map.put(key, property);  
        }  
  
        return map;  
    }  
  
    public static void main(String[] args) {  
        String filePath = "D://xia/ff.properties";  
        String name = "mfy1";  
        String parameterValue = "我和你456";  
        Object []obs={"ee","ff"};

       // write(filePath, name, parameterValue);  
        System.out.println(MessageFormat.format   (readValue(filePath,name),   obs));  
    }  
  
}  
