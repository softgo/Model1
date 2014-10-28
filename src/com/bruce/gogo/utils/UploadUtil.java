package com.bruce.gogo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * 上传工具类
 *
 * @author 段洋波
 * @version v1.0
 */
public class UploadUtil 
{
	/**
	 * 文件上传
	 * @param folder
	 * @param fileName
	 * @param srcFile
	 */
	public static void uploadFile(String folder,String fileName,File srcFile)
	{
		File rootDir = new File(folder);
        if(!rootDir.exists())
        {
            rootDir.mkdirs();
        }
		
		copy(srcFile,new File(fileName));
	}
	
	/**
	 * 复制文件
	 * @param src 源文件
	 * @param dst 目标文件
	 */
	private static void copy(File src, File dst)
	{     
		InputStream in = null;
        OutputStream out = null;
        try{                
        	in = new BufferedInputStream( new FileInputStream(src));
            out = new BufferedOutputStream( new FileOutputStream(dst)); 
           
            byte [] buffer = new byte [1024];    
            while(in.read(buffer) > 0)
            {
                out.write(buffer);     
            }
            in.close();
            out.close(); 
        }catch (Exception e) {    
            e.printStackTrace();    
        }       
    }
	
	/**
	 * 删除文件
	 * @param fullPath
	 */
	public static void deleteFile(String fullPath)
	{
		try
		{
			File file = new File(fullPath);
			if(file.exists())
			{
				file.delete();
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static String makeNewFileName(String srcFileName)
	{
		String fileEx = srcFileName.substring(srcFileName.indexOf("."), srcFileName.length());//文件的后缀
		Random r = new Random();
		String newFileName = System.currentTimeMillis() + r.nextInt() + fileEx;
		return newFileName;
	}
}
