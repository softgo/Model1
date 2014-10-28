package com.bruce.gogo.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


public class DownLoadFile extends  ActionSupport
{
	private static final long serialVersionUID = 6329383258366253255L; 
    public String getDownloadFileName() { 
		String downFileName = ServletActionContext.getRequest().getParameter("filename"); 
		
		try { 
		downFileName = new String(downFileName.getBytes(), "ISO8859-1"); 
		} catch (UnsupportedEncodingException e) { 
		e.printStackTrace(); 

		} 
		return downFileName; 

		} 	
  /* 
    * @getDownloadFile 
    * 此方法对应的是struts.xml文件中的： 
    * <param name="inputName">downloadFile</param> 
    * 返回下载文件的流，可以参看struts2的源码 
    * */ 
   public InputStream getInputStream()
   { 
     
      String p=ServletActionContext.getRequest().getRealPath("/");
     
      String path =p+ServletActionContext.getRequest().getParameter("path");
     
      File file = new File(path);
      
      InputStream in = null;
      try {
       // 一次读一个字节
       in = new FileInputStream(file);
      }catch(Exception e){
    	  e.printStackTrace();
      }
      return in;
   } 

  @Override 
   public String execute() throws Exception { 
       return SUCCESS; 
  }
  public static void main(String[] args) throws Exception {
	  String p=ServletActionContext.getRequest().getRealPath("/");
	     
      String path =p+ServletActionContext.getRequest().getParameter("path");
      System.out.println("path==========="+path);
  }
  
}




