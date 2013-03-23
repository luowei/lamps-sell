package com.vvvv.common.tool.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @className:FileUploadUtil.java
 * @classDescription:fileupload上传处理类
 * @author:xiayingjie
 * @createTime:2010-12-7
 */

public class FileUploadUtil {

	private static DiskFileItemFactory factory = null;

	private static Map<String, String> paramMap = null;

	private static ServletFileUpload fileUpload = null;

	/**
	 * 初始化FileItemFactory对象
	 * 
	 * @return
	 */
	private static DiskFileItemFactory initItemFactory() {
		if (factory == null) {
			factory = new DiskFileItemFactory();
		}
		return factory;
	}

	public static void upload(HttpServletRequest request, FileInfo fileInfo) {
		try {
			// 判断是否是文件上传
			boolean flag = ServletFileUpload.isMultipartContent(request);
			if (!flag) {
				throw new IllegalArgumentException(
						"非上传表单，请确认form中是否加上:method=\"post\" enctype=\"multipart/form-data\" ");
			}
			//设置编码
			request.setCharacterEncoding(fileInfo.getEncoding());
			// 初始化上传对象
			initItemFactory();
			// 设置写入硬盘的缓冲大小
			factory.setSizeThreshold(fileInfo.getSizeThreshold());
			// 设置文件的临时目录
			factory.setRepository(fileInfo.getTempFolder());
			// 初始化上传对象
			fileUpload = new ServletFileUpload(factory);
			// 设置单个文件上传的大小
			fileUpload.setFileSizeMax(fileInfo.getSize());
			// 设置总文件的大小
			fileUpload.setSizeMax(fileInfo.getSizeMax());
			// 设置编码
			fileUpload.setHeaderEncoding(fileInfo.getEncoding());
			// 获取所有表单
			List<FileItem> fileItemList = fileUpload.parseRequest(request);
			paramMap = new HashMap();
			// 遍历表单集合
			for (FileItem file : fileItemList) {		
				// 判断表单是否是文件类型
				if (!file.isFormField()) {
					FileBean fileBean = new FileBean();
					//获取系统分隔符
					String   fs   =   System.getProperties().getProperty( "file.separator");
					// 获取文件名
					String fileName =file.getName();
				    fileName = fileName.substring(fileName.lastIndexOf(fs) + 1, fileName.length());
				
					fileBean.setOldFileName(fileName);

					// 获取上传文件的类型
					String fileExt = fileName.substring(fileName
							.lastIndexOf(".") + 1, fileName.length());
					fileBean.setFileExt(fileExt);

					// 获取文件大小(以兆为单位)
					float fileSize = file.getSize();
					fileBean.setSize(fileSize);

					// 获取字段名
					fileBean.setFieldName(file.getFieldName());

					// 新文件名
					String newFileName;
					// 文件保存路径
					StringBuffer path = new StringBuffer("");
					path.append(fileInfo.getRoot()+ fileInfo.getPath()+fs);	

					// 判断已经上传的文件
					if (!file.getName().trim().equals("")) {
						File temp = null;
						// 上传文件保持原文件名
						if (fileInfo.getType() == 0) {	
							newFileName = fileName;
						} else {
							Calendar calendar = Calendar.getInstance();
							int YY = calendar.get(Calendar.YEAR);
							int MM = calendar.get(Calendar.MONTH) + 1;
							int DD = calendar.get(Calendar.DATE);
							int HH = calendar.get(Calendar.HOUR);
							int NN = calendar.get(Calendar.MINUTE);
							int SS = calendar.get(Calendar.SECOND);
							
							path.append(YY + fs + MM
									+ fs + DD+fs);
							// 这里定义一个随机数，便于上传文件不同名
							Random r = new Random();
							String date = new SimpleDateFormat(
									"yyyyMMddhhmmssSSS").format(calendar.getTime());
							newFileName = date + r.nextInt(100)+"." + fileExt;
						}
						// 如果上传文件目录不存在则创建
						File uploadDir = new File(path.toString());
						
						if (!uploadDir.exists()) {
							uploadDir.mkdirs();
						}
						// 设置新文件的名称
						fileBean.setNewFileName(newFileName);
						// 获取新文件
						fileBean.setFilePathName(fileInfo.getPath()+fs
								+ newFileName);
						
						// 文件另存为
						temp = new File(path + newFileName);
						// 将数据写入文件
						file.write(temp);

						fileInfo.getFileMap().put(fileBean.getFieldName(),fileBean);
					}

				} else {
					// 获取表单项名和表单项值 放入map中
					paramMap.put(file.getFieldName(), file.getString(fileInfo.getEncoding()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter传入参数
	 * @return String
	 */
	public static String getParameter(String parameter) {
		if (null == paramMap) {
			throw new IllegalArgumentException(
					"调用此方法前请先调用FileUploadUitl.upload()方法 ");
		}
		String value = "";
		if (paramMap.containsKey(parameter)) {
			value = paramMap.get(parameter);
		}
		return value;
	}

}
