package com.vvvv.common.tool.upload;
/**
 * @className:FileBean.java
 * @classDescription:上传文件后的文件大小
 * @author:xiayingjie
 * @createTime:2010-12-8
 */

public class FileBean {
	private String oldFileName;// 上传前的文件名

	private String newFileName;// 上传后的文件名

	private String fileExt;// 文件扩展名（FileExt）

	private String filePathName; // 文件的整个路径，相对于根目录

	private float size; // 文件大小(kb为单位)

	private String fieldName;// 表单字段名

	public String getOldFileName() {
		return oldFileName;
	}

	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFilePathName() {
		return filePathName;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}

	public float getSize() {
		return size / 1024;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
