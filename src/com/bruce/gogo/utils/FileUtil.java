package com.bruce.gogo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 
 * @author chenbw
 *
 */
public class FileUtil {
	/**
	 * 复制文件
	 * @param src 源文件
	 * @param dst 目标文件
	 */
	public static boolean copy(File src, File dst)
	{     
		boolean flag = false;
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
            flag = true;
        }catch (Exception e) {    
            e.printStackTrace();   
            flag = false;
        }       
        return flag;
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
}
