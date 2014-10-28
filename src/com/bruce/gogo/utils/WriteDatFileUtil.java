package com.bruce.gogo.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WriteDatFileUtil {
	protected static Log logger = LogFactory.getLog(WriteDatFileUtil.class);
	public static String DAT_PATH = "/data01/chenbw/zs2x";
	public static String DEST_DIR = "/data01/chenbw/zs2x/dest1";
	
	static {
		try {
			File dir = new File(getDir("datFile.path", DAT_PATH));
			if (! dir.exists()) {
				dir.mkdirs();
			}
		} catch(Exception e) {
			logger.error(e.getClass().getSimpleName());
		}
	}
	/**
	 * 写dat文件
	 * @param content
	 */
	public static void writeToDatFile(byte[] content) {
		try {
			String filename = System.currentTimeMillis() + ".dat";
			File file = new File(DAT_PATH, filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			logger.info("File Path : " + file.getAbsolutePath());
			logger.info("Write File Begin : " + filename);
			OutputStream os = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(content);
			dos.flush();
			dos.close();
			logger.info("Write File End![ " + filename + " ]");
			//拷贝文件到指定目录
			copyFile(file);
		} catch(Exception e) {
			logger.error(e.getClass().getSimpleName());
		}
	}
	/**
	 * 写dat文件
	 * @param content
	 */
	public static void writeToDatFile(byte[] content, int port) {
		try {
			String filename = System.currentTimeMillis() + ".dat";
			File file = new File("/data01/chenbw/zs2x/"+port, filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			logger.info("File Path : " + file.getAbsolutePath());
			logger.info("Write File Begin : " + filename);
			OutputStream os = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(content);
			dos.flush();
			dos.close();
			logger.info("Write File End![ " + filename + " ]");
			//拷贝文件到指定目录
			copyFile(file);
		} catch(Exception e) {
			logger.error(e.getClass().getSimpleName());
		}
	}
	/**
	 * 拷贝文件到指定目录
	 * @param srcFile
	 */
	public static void copyFile(File srcFile) {
		try {
			String desDirStr = getDir("datFile.destDirs", DEST_DIR);
			String[] desDirs = desDirStr.split(",");
			for (String desDir : desDirs) {
				if (StringUtils.isNotBlank(desDir)) {
					File destDir = new File(desDir);
					if (!destDir.exists()) {
						destDir.mkdirs();
					}
					logger.info("拷贝文件到：" + desDir);
					FileUtils.copyFileToDirectory(srcFile, destDir);
					logger.info("拷贝文件到：[" + desDir + "]完成！");
				}
			}
			if (desDirs.length > 0) {
				srcFile.delete();
			}
		} catch(Exception e) {
			logger.error(e.getClass().getSimpleName());
		}
	}
	/**
	  * 读配置文件取接收端口
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	public static String getDir(String property,String defaultValue){
		Properties prop = new Properties();
		InputStream fis =  Thread.currentThread().getContextClassLoader().getResourceAsStream("destDirs.properties");
		try {
			prop.load(fis);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		String value = prop.getProperty(property,defaultValue).trim();
		logger.debug("Dest Dirs : " + value);
	    return value;
	}
}
