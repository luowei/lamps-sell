package com.vvvv.common.tool.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;

/**
 * @className:FileZip.java
 * @classDescription: ZIP压缩
 * @author:wandonghai
 * @createTime:2011-6-22
 */
public class FileZip {
	
	/**
	* 压缩一个目录或者文件
	* @author wandonghai
	* @createTime 2011-6-22
	* @param filePath
	* @param zipFilePath
	 */
	public static void zipYasuo(String filePath, String zipFilePath) {
		try {
			File zipFile = new File(zipFilePath);
			zipFile.getParentFile().mkdirs();
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFilePath));
			zip(out, new File(filePath), "");			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	* 将文件写入压缩包
	* @author wandonghai
	* @createTime 2011-6-22
	 */
	private static void zip(ZipOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < files.length; i++) {
				zip(out, files[i], base + files[i].getName());
			}
		} else {
			//压缩不压缩zip文件
			if(base.contains(".zip")){
				return;
			}
			if (base == null || "".equals(base)) {
				base = f.getName();
			}
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	/**
	* 传入 File[] 进行压缩
	* @author wandonghai
	* @createTime 2011-6-22
	* @param files
	* @param zipFilePath
	 */
	public static void zip(File[] files, String zipFilePath) {
		File zipFile = new File(zipFilePath);
		zipFile.getParentFile().mkdirs();
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFilePath));
			out.putNextEntry(new ZipEntry("/"));
			for (int i = 0; i < files.length; i++) {
				if (files[i].exists()) {
					zip(out, files[i], files[i].getName());
				}
			}
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	* 对各个filePath进行压缩
	* @author wandonghai
	* @createTime 2011-6-22
	* @param filePathSet
	* @param zipFilePath
	 */
	public static void zip(Set<String> filePathSet, String zipFilePath) {
		try {
			File zipFile = new File(zipFilePath);
			zipFile.getParentFile().mkdirs();
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFilePath));

			for (String fileName : filePathSet) {
				zip(out, new File(fileName), "");
			}
			//System.out.println("zip done");
			out.close();
		} catch (Exception e) {
		}
	}

	public static void main(String args[]) {
		//System.out.println("asd");
		zipYasuo("c:\\androidhtml\\", "c:\\test1.zip");
	}
}
