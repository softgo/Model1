package com.bruce.gogo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class PropUtil {
	public static Integer getPartnerId(String fileName, String key, Integer id) {
		String file = "";
		Integer pid = null;
		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (fis != null) {
			Properties prop = new Properties();
			file = Thread.currentThread().getContextClassLoader().getResource(fileName).getFile();
			try {
				fis = new FileInputStream(new File(file));
				prop.load(fis);
				String value = prop.getProperty(key);
				if (StringUtils.isBlank(value)) {
					pid = id;
					prop.put(key, id+"");
					OutputStream fos = new FileOutputStream(new File(file));
					prop.store(fos, null);
					fos.flush();
					fos.close();
				} else {
					pid = Integer.valueOf(value);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pid;
	}
	public static void main(String[] args) {
		int pid = getPartnerId("partner.properties", "中文", 13);
		pid = getPartnerId("partner.properties", "中文2", 13);
		System.out.println(pid);
	}
}
