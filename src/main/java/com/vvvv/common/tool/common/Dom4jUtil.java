package com.vvvv.common.tool.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @className:Dom4jUtil.java
 * @classDescription:dom4j操作类
 * @author:xiayingjie
 * @createTime:2010-10-26
 */

public class Dom4jUtil {
	// -----------------写------------------------------
	/**
	 * 将doc转换成String（默认为不换行，utf-8编码）
	 * 
	 * @param doc
	 *            Document对象
	 * @return String
	 */
	public static String docToString(Document doc) {
		return docToString(doc,false,"UTF-8");
	}
	/**
	 * 将doc转换成String（默认是UTF-8编码）
	 * 
	 * @param doc
	 *            Document对象
	 * @param newLine
	 *             是否换行
	 * @return String
	 */
	public static String docToString(Document doc,boolean newLine){
		return docToString(doc,newLine,"UTF-8");
	}
	/**
	 * 将doc转换成String(默认不换行)
	 * 
	 * @param doc 
	 *           Document对象
	 * @param encoding
	 *            字符编码
	 * @return String
	 */
	public static String docToString(Document doc,String encoding){
		return docToString(doc,false,encoding);
	}

	/**
	 * 将doc转换成String
	 * 
	 * @param doc Document对象
	 * @param newLine 是否换行
	 * @param encoding 字符编码
	 * @return String
	 */
	public static String docToString(Document doc,boolean newLine,String encoding){
		String str = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码--false 换行
			OutputFormat format = new OutputFormat("", newLine, encoding);
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			str = out.toString(encoding);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}
	/**
	 * 将xml格式的字符串转换成Document对象
	 * 
	 * @param str
	 *            xml格式字符串(字符串编码必须为utf-8，否则会出现乱码)
	 * @return Document对象
	 */
	public static Document stringToDoc(String str) {
		Document doc = null;
		try {
			// 将xml格式化字符串转成Document对象
			doc = DocumentHelper.parseText(str);
		} catch (Exception ex) {
			System.out.println("-----------xml chucuo----------");
			System.out.println(DateUtil.datetime());
			System.out.println(str);
			ex.printStackTrace();
		}
		return doc;
	}

	/**
	 * 将xml字符串保存为xml文件
	 * 
	 * @param xmlStr
	 *            xml格式的字符串
	 * @param fileName
	 *            保存的文件名
	 * @return true:保存成功 flase:失败
	 */
	public static boolean stringToXmlFile(String xmlStr, String fileName) {
		boolean flag = true;
		try {
			flag = docToXmlFile(stringToDoc(xmlStr), fileName);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}
	/**
	 * 将doc写入xml文件(默认为Utf-8编码)
	 * 
	 * @param doc document对象
	 * @param fileName 文件的全路径
	 * @return
	 */
	public static boolean docToXmlFile(Document doc, String fileName){
		return docToXmlFile(doc,fileName,"UTF-8");
	}
	/**
	 * 将doc写入xml文件
	 * 
	 * @param doc document对象
	 * @param fileName 文件的全路径
	 * @param encoding 编码
	 * @return
	 */
	public static boolean docToXmlFile(Document doc, String fileName,String encoding) {
		boolean flag = true;

		try {
			// 创建格式化类
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 设置编码格式
			format.setEncoding(encoding);
			// 创建输出流
			OutputStreamWriter osw = new OutputStreamWriter(
					new FileOutputStream(fileName), encoding);
			FileWriter fw = new FileWriter(new File(fileName));
			// 创建xml输出流
			XMLWriter writer = new XMLWriter(osw, format);
			// 生成xml文件
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;

	}

	/**
	 * 创建document
	 * 
	 * @return Document
	 */
	public static Document createDocument() {
		// 创建文档
		return DocumentHelper.createDocument();
	}
	//---------------------------读（sax）---------------------
	/**
	 * 使用sax加载xml文档（速度快-大文件）
	 * @param filename 绝对文件路径
	 * @return 成功返回Document对象，失败返回null
	 */
	public static Document loadForSax(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	//---------------------读（dom）--------------------
	/**
	 * 使用dom加载xml（速度快-小文件 适用于频繁操作xml）
	 * @param fileName
	 * @return
	 */
	public static Document loadForDom(String fileName){
		Document doc=null;
		try{
			//创建DoucmentBuilder工厂类(使用jaxp解析)
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			//读取doc(首先将其转换成w3c的Document,然后在转成Dom4j的Document)
			org.w3c.dom.Document domDocument = db.parse(new File(fileName));
			DOMReader reader = new DOMReader();
			doc = reader.read(domDocument);
		}catch(Exception e){
			e.printStackTrace();
		}
		return doc; 

	}
	/**-------------------xpath查询语法------------
bookstore	选取 bookstore 元素的所有子节点
/bookstore	选取根元素 bookstore 注释：假如路径起始于正斜杠( / )，则此路径始终代表到某元素的绝对路径！
bookstore/book	选取所有属于 bookstore 的子元素的 book 元素。
//book	选取所有 book 子元素，而不管它们在文档中的位置。
bookstore//book	选择所有属于 bookstore 元素的后代的 book 元素，而不管它们位于 bookstore 之下的什么位置。
//@lang	选取所有名为 lang 的属性。
/bookstore[@lang='我和你'] 选取bookstore lang属性等于'你和我'的所有元素
/bookstore[@lang>2] 选取bookstore lang属性大于2的所有元素  如果不加@,那么就是元素比较
/bookstore/book[price>35.00]/title
/bookstore/*	选取 bookstore 元素的所有子节点
//*	选取文档中的所有元素
//title[@*]	选取所有带有属性的 title 元素。
 “|”表示或  
*/	
	/**
	 * 根据xpath语法查询匹配的结果集（Element或者Attribute）
	 * @param doc Document对象
	 * @param xpathExp xpath查询语法，官方推荐
	 * @return List
	 */
	public static List<Node> findList(Document doc,String xpathExp){
		return doc.selectNodes(xpathExp);
		
	}
	
	
	
//	/**
//	 * 查询结果
//	 * @param doc
//	 * @param xpathExp
//	 * @return
//	 */
//	public static Node findSingleNode(Document doc,String xpathExp){
//		return doc.selectSingleNode(xpathExp);
//	}
//	/**
//	 * 根据xpath语法查询匹配的结果集（Element或者Attribute）
//	 * @param Element Element对象
//	 * @param xpathExp xpath查询语法，官方推荐
//	 * @return List
//	 */
//	public static List findList(Element el,String xpathExp){
//		return el.selectNodes(xpathExp);	
//	}
	
	public static void main(String[] args) {
		String s=MD5.getInstance().createMD5("asdgdfhrthrthrthtr");
		System.out.println(s);
		
//		Dom4jUtil.docToString(loadForDom("F://msn.xml"));
		
//		Document doc =Dom4jUtil.loadForDom("D://b.xml");
//		
//		List list=Dom4jUtil.findList(doc,"//skills/skill/@name");
//		for (Iterator it = list.iterator(); it.hasNext();) {
//			
//			Attribute attr = (Attribute) it.next();
//			Element el = (Element) it.next();
//		
//			System.out.println(attr.getValue());
//			//TODO
//		}
//--------增删改查都放到具体的操作中。
	
		// String str = "<skills><!--第一个技能--><skill name=\"独孤九剑\">"
		// + "<info>为独孤求败所创，变化万千，凌厉无比。其传人主要有风清扬、令狐冲。</info> </skill>"
		// +
		// "<skill name='葵花宝典'>    <info>宦官所创，博大精深，而且凶险至极。练宝典功夫时，首先要自宫净身。</info>"
		// +
		// "  </skill>  <skill name='北冥神功'>    <info>나는 당신을 사랑합니다 一，能吸人内力转化为自己所有，威力无穷。</info>"
		// + "  </skill></skills>";
		//
		// System.out.println(docToString(stringToDoc(str)));
//		Document doc = Dom4jUtil.createDocument();
//		Element e = doc.addElement("skills");
//
//		Element e1 = e.addElement("skill");
//		e1.addComment("第一个技能");
//		e1.addAttribute("name", "独孤九剑");
//		e1.addElement("info").addText("为独孤求败所创，变化万千，凌厉无比。其传人主要有风清扬、令狐冲");
//
//		Element e2 = e.addElement("skill");
//		e2.addComment("第二个技能");
//		e2.addAttribute("name", "葵花宝典");
//		e2.addElement("info").addText("宦官所创，博大精深，而且凶险至极。练宝典功夫时，首先要自宫净身。");
//		
//		String str=Dom4jUtil.docToString(doc,true);
//		System.out.println(str);
		

//		Dom4jUtil.docToXmlFile(doc, "D://b.xml");
		for (int i = 1; i < 1000; i++) {
		String url="http://mp3.easou.com/scoop.e?id=5091321&ver=4&cid=cbtmv2.7.0.2011101312&esid=FBJ9Iajm8bL5UxS&im=868714007764753";
	//	url="http://www.baidu.com";
		String musicXML=HttpClientUtil.get(url,null);
		System.out.println(musicXML);
		Document doc=Dom4jUtil.stringToDoc(musicXML);
		List<Node> countList=Dom4jUtil.findList(doc, "/results/count");
		if(countList.size()>0){
			//return null;
			System.out.println("存在");
		}
		
		List<Node> list=Dom4jUtil.findList(doc, "/results/result");
		//歌曲名称
		String songName=doc.selectSingleNode("/results/song").getText();
		//歌手
		String songer=doc.selectSingleNode("/results/songer").getText();
		//来源
		String source=doc.selectSingleNode("/results/source").getText();
		System.out.println(songName+","+songer+","+source);
		//遍历所有的结果，有小于等于两种结果，type为common(普通),gaozhi(高质)
	    for(Node e:list){
	       System.out.println(((Element)e).attributeValue("type"));
	       //type类型
	       String type=((Element)e).attributeValue("type");
	       List<Node> nodeList=e.selectNodes("item/filetype");
	       //去掉压缩mp3，如果结果只有一个的时候，那么就不去掉压缩
	       if(nodeList.size()>1){
		       for(Node ni:nodeList){
		    	   //文件类型：压缩，高质，普通
		    	   String fileType=ni.getText();
		    	   
		    	   if(fileType.indexOf("压缩")==-1){
		    		   Node np=ni.getParent();
		    		   //宜搜id
		    		   String filedid=np.selectSingleNode("fileid").getText();
		    		   //下载地址
		    		   String downurl=np.selectSingleNode("downurl").getText();
		    		   System.out.println(fileType+","+filedid+","+downurl);
		    	   }
		       }
	       }else{
	    	   Node ni=nodeList.get(0);
	    	   String fileType=ni.getText();
	    	   Node np=ni.getParent();
	    	   //宜搜id
    		   String filedid=np.selectSingleNode("fileid").getText();
    		   //下载地址
    		   String downurl=np.selectSingleNode("downurl").getText();
    		   System.out.println(fileType+","+filedid+","+downurl);
	       }
	    }
	   
	    }
	    /*
		System.out.println("普通版："+Dom4jUtil.findList(doc, "/results/result[@type='common']/item/filetype").get(0).getText());
		System.out.println("普通版："+Dom4jUtil.findList(doc, "/results/result[@type='common']/item/fileid").get(0).getText());
		System.out.println("普通版："+Dom4jUtil.findList(doc, "/results/result[@type='common']/item/downurl").get(0).getText());
		System.out.println("高潮版："+Dom4jUtil.findList(doc, "/results/result[@type='gaochao']/item/filetype").get(0).getText());
		System.out.println("高潮版："+Dom4jUtil.findList(doc, "/results/result[@type='gaochao']/item/fileid").get(0).getText());
		System.out.println("高潮版："+Dom4jUtil.findList(doc, "/results/result[@type='gaochao']/item/downurl").get(0).getText());
		*/
//		items.setFileid(el.elementText("fileid"));
//		items.setDownurl(el.elementText("downurl");
//		System.out.println("完整版："+Dom4jUtil.findList(doc, "/results/result/[@type=common]/").get(0).getText());
		
		
		

	}
}
