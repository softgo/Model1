package com.bruce.gogo.socket.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.bruce.gogo.utils.DataConvert;
import com.bruce.gogo.utils.StringUtil;
import com.bruce.gogo.utils.TimeTools;

/**
 * @version: 1.0
 */
public class QueryReader {
	
	static Logger logger = Logger.getLogger(QueryReader.class.getName());
	
	/**
	 * 读取流信息
	 * 
	 * @param reader
	 * @param length
	 * @return
	 */
	public static byte[] getResXml(BufferedInputStream reader, int length){
		logger.debug("getResXml length===" + length);
		byte[] xmlArr = null;
		byte[] allArr = null;
		List<byte[]> alist = new ArrayList<byte[]>();
		int bytesRead = 0;
		int cur_read_len = 4096;
		try { //如果出现异常 退出循环
         while (true) {
        	if(length - bytesRead > 4096)
        	{
        		cur_read_len = 4096;
        	}
        	else {
        		cur_read_len = length - bytesRead;
        	}
        	xmlArr = new byte[cur_read_len];
            int bytesReadLength = 0;
			bytesReadLength = reader.read(xmlArr, 0, cur_read_len);
			
			if(bytesReadLength == -1)
				break;
			alist.add(xmlArr);
			bytesRead+=bytesReadLength;
			logger.debug("bytesRead==="+bytesRead);
			
			if (bytesRead >= length) {// end of InputStream
				break;
			}
			logger.debug("also in getResXml while(true)");
          }
           logger.debug("finish in getResXml while");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} 
		allArr = DataConvert.sysCopy(alist);
		
		return allArr;
	}
	
	/**
	 * 读取内容. modify 2014.7.1
	 * @param reader
	 * @param length
	 * @return
	 */
	public static byte[] getReturnXml(BufferedInputStream reader, int length){
		byte[] allArr = new byte[length];
		int bytesRead = 0;
		int cur_read_len = 204800;//每次200K数据
		try { //如果出现异常 退出循环
         while (true) {				
        	if(length - bytesRead > 204800)
        	{
        		cur_read_len = 204800;
        	}
        	else
        		cur_read_len = length - bytesRead;  //1---1583
        	
            int bytesReadLength = 0;
			bytesReadLength = reader.read(allArr, bytesRead, cur_read_len);
			
			if(bytesReadLength==-1)
				break;
			bytesRead+=bytesReadLength;
			
			if (bytesRead >= length) {// end of InputStream
				break;
			}
          }
		} catch (IOException e) {
			logger.info("读取流文件出错了,错误是："+e.getMessage());
		} 
		return allArr;
	}

	/**
	 * 后台系统推送协议解析
	 * @param reader
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public static void parseByteReader(BufferedInputStream reader,Socket client) {
		//"fasdf";
		byte[] byte_identifyCode = new byte[8]; // 8字节【验证码】PEOPLE10
		int nresult = 0;
		try {
			nresult = reader.read(byte_identifyCode, 0, 8);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (nresult < 0) {
			logger.error(("对不起，读取标准头的前8字节时报错!!").getBytes());
			logger.info("ERROR:the package title error;");
		}
		String identifyCode  = new String(byte_identifyCode); // 
		logger.info("验证码：" + identifyCode);
		
		byte[] byte_alllen = new byte[4];// 随后的数据包长度
		int nresult2 = 0;
		try {
			nresult2 = reader.read(byte_alllen, 0, 4);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (nresult2 < 0) {
			logger.error(("对不起，读取标准头的后4个标识长度字节时报错！！").getBytes());
		}
		int length = DataConvert.bytesToInt(byte_alllen);//随后的数据包长度
		logger.info("随后的数据包长度：" + length);
		
		byte[] protoArr = new byte[2];//协议类型
		try {
			reader.read(protoArr, 0, 2);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		short protoType = DataConvert.bytesToShort(protoArr);//协议类型
		logger.info("协议类型：" + protoType);
		try {
			reader.read(new byte[16], 0, 16);
			byte[] commandTypeArr = new byte[2];
			reader.read(commandTypeArr, 0, 2);
			short commandType = DataConvert.bytesToShort(commandTypeArr);
			
			//继续往下读取，根据协议来操作...
			
		} catch(Exception e) {
			logger.error("后台系统推送协议解析发生了错误："+e.getMessage());
		}
		
	}
	/**
	 * 处理报警信息入库
	 * @param reader
	 * @param client
	 * @throws Exception
	 */
	private static void dealWarnnnigInfo(BufferedInputStream reader,
			Socket client) throws Exception {
		try {
			byte[] warnTypeArr = new byte[2];
			reader.read(warnTypeArr, 0, 2);
			short warnType = DataConvert.bytesToShort(warnTypeArr);
			logger.info("报警类型：" + warnType);
			
			byte[] dataIdArr = new byte[30];
			reader.read(dataIdArr, 0 , 30);
			String dataId = new String(dataIdArr);
			logger.info("报警数据相关id: " + dataId);
			
			byte[] nameLenArr = new byte[4];
			reader.read(nameLenArr, 0, 4);
			int nameLen = DataConvert.bytesToInt(nameLenArr);
			logger.info("名称/标题长度："+nameLen);
			
			byte[] nameArr = new byte[nameLen];
			reader.read(nameArr, 0, nameLen);
			String name = new String(nameArr);
			logger.info("名称/标题：" + name);
			
			byte[] issueLenArr = new byte[4];
			reader.read(issueLenArr,0,4);  //2014.7.8
			int issueLen = DataConvert.bytesToInt(issueLenArr);
			logger.info("待解决问题长度:"+issueLen);
			
			byte[] issueArr = new byte[issueLen];
			reader.read(issueArr, 0, issueLen);
			String issue = new String(issueArr);
			logger.info("待解决问题问题：" + issue);
			
			byte[] xmlLenArr = new byte[4];
			reader.read(xmlLenArr, 0, 4);
			int xmlLength = DataConvert.bytesToInt(xmlLenArr);
			logger.info("原数据长度："+xmlLength);
			
			String xmlData = new String(QueryReader.getResXml(reader, xmlLength), "GBK").trim();
			logger.info("原方法：xml数据部分：" + xmlData);
			//String xmlData = new String(QueryRead.getReturnXml(reader, xmlLength), "GBK").trim();
			
			reader.read(new byte[4], 0, 4);//读扩展字段长度
			
			//返回操作字符串.
			//2014.7.8
			byte[] proto = assemblyReturnProtocol(0);
			resBack(proto, client);
			
		} catch(Exception e) {
			logger.error("解析数据出现了错误："+e.getMessage()+",时间："+TimeTools.getStringDate());
			//2014.7.8
			byte[] proto = assemblyReturnProtocol(-1);
			resBack(proto, client);
		}
		
	}



	/**
	 * 返回给集成系统协议串
	 * @param statusCode 0：成功 非0:错误码(小于0)
	 * @return
	 */
	public static byte[] assemblyReturnProtocol(int statusCode) {
		List<byte[]> protoList = new ArrayList<byte[]>();
		
		byte[] verifyCodeByte ="world".getBytes();
		int total_data_len = 20;
		byte[] totalDataLenByte = DataConvert.intToByteArray(total_data_len);
		short proto_type = 4;
		byte[] protoTypeByte = DataConvert.shortToBytes(proto_type);
		byte[] retain_field_12 = new byte[12];
		byte[] statusCodeByte = DataConvert.intToByteArray(statusCode);
		
		protoList.add(verifyCodeByte);//状态码
		protoList.add(protoTypeByte);//协议类型4
		protoList.add(retain_field_12);//12位保留字段
		protoList.add(statusCodeByte);//状态码：0  成功 非0：错误码
		protoList.add(totalDataLenByte);//后续数据总长
		
		logger.info("状态码:"+new String(verifyCodeByte)+",后续数据总长:"+new String(totalDataLenByte)+
				",协议类型:"+new String(protoTypeByte)+",位保留字段:"+new String(retain_field_12)+"验证码:"+
				new String(statusCodeByte)+"最终发送的结果是："+ new String( DataConvert.sysCopy(protoList))+" ,len= "+DataConvert.sysCopy(protoList).length);
		
		return DataConvert.sysCopy(protoList);
	}

	/**
	 * @param proto
	 * @param client
	 */
	private static void resBack(byte[] proto, Socket client) {
		try {
			BufferedOutputStream bfOut =  new BufferedOutputStream(client.getOutputStream()); // 拼接发送给下载的请求信息,这个地方注意对应协议
			logger.info("将要发给客户端的数据是："+new String(proto));
			bfOut.write(proto);
			bfOut.flush();
			bfOut.close();
			if (client != null && !client.isClosed()) {
				client.close();
				client = null;
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 将输入的值转换
	 * @param value
	 * @return
	 */
	public static int getIntVal(String value) {
		if (!StringUtil.isNotBlank(value)) {
			return 0;
		}
		int retValeu = 0;
		try {
			if ("未知".equals(value.trim())) {
				return 0;
			}
			retValeu = Integer.parseInt(value);
		} catch (Exception e) {
			retValeu = 0;
			return retValeu ;
		}
		return retValeu ;
	}
}