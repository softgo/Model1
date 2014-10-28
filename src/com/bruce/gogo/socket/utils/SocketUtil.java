package com.bruce.gogo.socket.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.bruce.gogo.utils.DataConvert;
import com.bruce.gogo.utils.StringUtil;

/**
 * 
 *
 */
public class SocketUtil {
	
	protected static Logger logger = Logger.getLogger(SocketUtil.class);
	
	private String ip;

	private int port;

	private String charset;

	private Socket socket;

	private InputStream in;

	private OutputStream out;

	private int socketWateServerTime = 30 * 60 * 1000; // socket超时时间
	public static final int COMMAND_ADD_PERSON_RETURN_ID = 2;//据人名创建人物id
	public static final int COMMAND_PID_LID_RETURN_INFO = 5;//返回pid对应的lid信息

	public SocketUtil(String ip, int port, String charset) {
		this.ip = ip;
		this.port = port;
		this.charset = charset;
	}

	/**
	 * 获得 socket 连接.
	 * @throws IOException
	 */
	public void connect() throws IOException {
		socket = new Socket(ip, port);
		logger.info("IP : " + ip + " PORT : [" + port + "]");
		socket.setSoTimeout(socketWateServerTime);
		
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}
	public void setKeepAlive(boolean alive) throws Exception {
		socket.setKeepAlive(alive);
	}
	/**
	 * 接收socket数据响应结果
	 * 
	 * @return
	 * @throws IOException
	 */
	ByteBuffer bb = new ByteBuffer();

	public String recvStr() throws IOException {
		byte[] g=new byte[1];
		g[0]=1;
		int len;
		BufferedInputStream bis = new BufferedInputStream(in, 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		byte[] buf = new byte[24]; // 保存标准头的数组
		byte[] result = null; // 保存标准头后面内容的数组
		String headerStr = "";
		if ((len = bis.read(buf)) > -1) { // 先读取24字节到buf，如果len==-1说明已经读到流结尾
			if (len == 24) {
				headerStr += new String(buf, charset).trim();
				String headStrEnd = headerStr.trim(); // 去掉末尾空格后的字符串
				int contentLen = Integer.parseInt(headStrEnd.substring(16,
						headStrEnd.length())); // 内容长度
				result = new byte[contentLen];
				bis.read(result); // 读取后面内容长度的字节到result
			} else {
				return "0";
			}
		}
		if (bis != null) {
			bis.close();
		}
		// 用文件随时记录
		FileOutputStream fos = new FileOutputStream(getClass().getClassLoader().getResource("SocketRes.dat").getPath());
		fos.write((headerStr + new String(result).trim()).getBytes());
		fos.write("\r\n\r\n".getBytes());
		fos.flush();
		fos.close();
		return new String(headerStr + new String(result).trim());
	}
	

	/**
	 * 循环读取接收到的数据
	 * 
	 * @param
	 * 
	 */
	public String readStr() throws IOException {
		int len;
		BufferedInputStream bis = new BufferedInputStream(in, 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		byte[] buf = new byte[24]; // 保存标准头的数组
		byte[] result = null; // 保存标准头后面内容的数组
		String headerStr = "";
		if ((len = bis.read(buf)) > -1) { // 先读取24字节到buf，如果len==-1说明已经读到流结尾
			if (len == 24) {
				// 已经读到数据，处理这些数据
				headerStr += new String(buf, charset).trim(); // BBSDP1.11001
				String headStrEnd = headerStr.trim(); // 去掉末尾空格后的字符串
				int contentLen = Integer.parseInt(headStrEnd.substring(16,
						headStrEnd.length())); // 内容长度
				result = new byte[contentLen];
				bis.read(result); // 读取后面内容长度的字节到result
			} else {
				return "0";

			}
		}
		if (bis != null) {
			bis.close();
		}
		return new String(headerStr + new String(result).trim());
	}


	public void send(String str) throws IOException {
		byte[] tmp = str.getBytes(charset);
		out.write(tmp, 0, tmp.length);
		out.flush();
	}
	
	public void sendByte(byte[] arry) throws IOException {
		out.write(arry, 0, arry.length);
		out.flush();
	}

	public void close() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();

			if (socket != null)
				socket.close();
		} catch (IOException e) {

		} finally {

			if (socket != null)
				socket = null;
		}
	}

	public SocketUtil(){};
	
	public static void main(String[] args) {
		SocketUtil sop = new SocketUtil();
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}
	
	/**
	 * 获取集成系统返回状态码
	 * @return
	 * @throws Exception
	 */
	public int recvCode()  throws Exception {
		int resCode = -1;
		BufferedInputStream bis = new BufferedInputStream(in, 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		byte[] byte_identifyCode = null;
		String resStr = "";
		String verifyCode = "";
		try {
			byte_identifyCode = new byte[8]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】
			int nresult = bis.read(byte_identifyCode, 0, 8);
			if (nresult < 0) {
				logger.error("读取标准头的前8字节时报错!!");
			}
			verifyCode = new String(byte_identifyCode); // 验证码
			logger.info("验证码：" + verifyCode);
			// 除验证码外包的长度 
			byte[] byte_alllen = new byte[4];//后续内容长度
			int nresult2 = bis.read(byte_alllen, 0, 4);
			int length = DataConvert.bytesToInt(byte_alllen);
			int dataLength = length -20;//真正数据长度
			logger.info("后续内容长度：" + length);
			logger.info("真正数据长度：" + dataLength);
			if (nresult2 < 0) {
				logger.error("image:对不起，读取标准头的后4个标识长度字节时报错！！");
			}
			byte[] protoTypeArr = new byte[2];
			bis.read(protoTypeArr, 0, 2);
			short protoType = DataConvert.bytesToShort(protoTypeArr);
			logger.info("协议类型：" + protoType);
			byte[] otherArr = new byte[12];//保留字段
			bis.read(otherArr, 0, 12);
			byte[] statusArr = new byte[4];//状态码
			bis.read(statusArr, 0, 4);
			int statusInt =  DataConvert.bytesToInt(statusArr);
			logger.info("状态码：" + statusInt);
			byte[] commandTypeArr = new byte[2];//命令类型
			bis.read(commandTypeArr, 0, 2);
			short commandType = DataConvert.bytesToShort(commandTypeArr);
			logger.info("命令类型：" + commandType);
			return statusInt;
		} catch (Exception e) {
			logger.error(e.getMessage());
			resStr = "协议解析发生异常：" + e.getMessage();
			return resCode;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	
	}
	/**
	 * 接收视频图片返回信息
	 * @return
	 * @throws IOException
	 */
	public String recvImageInfo() throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in, 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		byte[] byte_identifyCode = null;
		String resStr = "";
		String title = "";
		try {
			byte_identifyCode = new byte[8]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】，共占16字节
			int nresult = bis.read(byte_identifyCode, 0, 8);
			if (nresult < 0) {
				logger.error(("image 对不起，读取标准头的前8字节时报错!!").getBytes());
			}
			title = new String(byte_identifyCode); // 连接标准头的前8个字节
			logger.info("image: verification code："+title);
			// 除验证码外包的长度 
			byte[] byte_alllen = new byte[4];//后续内容长度
			int nresult2 = bis.read(byte_alllen, 0, 4);
			int length = DataConvert.bytesToInt(byte_alllen);
			int dataLength = length -20;//真正数据长度
			logger.info("image: all the video length==="+length);
			if (nresult2 < 0) {
				logger.error(("image:对不起，读取标准头的后4个标识长度字节时报错！！").getBytes());
			}
			byte[] otherArr = new byte[14];
			bis.read(otherArr, 0, 14);
			byte[] status = new byte[4];//状态码
			int statusRes =  bis.read(status, 0, 4);
			int statusInt =  DataConvert.bytesToInt(status);
			byte[] other = new byte[2];//命令类型
			bis.read(other, 0, 2);
			if(statusInt==0){//返回成功状态码
				int resCount = 0;
				byte[] byte_total = null;//每个视频xml的byte数组
					byte[] contentLength = new byte[4];//内容后续长度
					bis.read(contentLength, 0, 4);//或者从偏移量开始bis.read(contentLength, resCount, 4);
					int fLength = DataConvert.bytesToInt(contentLength);
					try {
						resStr = new String(QueryReader.getResXml(bis,fLength), "GBK").trim();
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage());
					}
			}else{
				return String.valueOf(statusInt);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			resStr = "协议解析发生异常："+e.getMessage();
		}finally{
			if (bis != null) {
				bis.close();
			}
		}
		return resStr;
	}
	

	/**
	 * 集成系统返回的状态码：0表示成功，<0失败
	 * @return
	 * @throws Exception
	 */
	public String recvStatusCodeOld() throws Exception {
		BufferedInputStream bis = new BufferedInputStream(in, 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		byte[] byte_identifyCode = null;
		String resStr = "";
		String verifyCode = "";
		try {
			byte_identifyCode = new byte[8]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】
			int nresult = bis.read(byte_identifyCode, 0, 8);
			if (nresult < 0) {
				logger.error("读取标准头的前8字节时报错!!");
			}
			verifyCode = new String(byte_identifyCode); // 验证码
			logger.info("验证码：" + verifyCode);
			
			// 除验证码外包的长度 
			byte[] byte_alllen = new byte[4];//后续内容长度
			int nresult2 = bis.read(byte_alllen, 0, 4);
			int length = DataConvert.bytesToInt(byte_alllen);
			
			//真正数据长度
			int dataLength = length -20;
			logger.info("后续内容长度：" + length);
			logger.info("真正数据长度：" + dataLength);
			if (nresult2 < 0) {
				logger.error("image:对不起，读取标准头的后4个标识长度字节时报错！！");
			}
			byte[] protoTypeArr = new byte[2];
			bis.read(protoTypeArr, 0, 2);
			short protoType = DataConvert.bytesToShort(protoTypeArr);
			logger.info("协议类型：" + protoType);
			//保留字段
			byte[] otherArr = new byte[12];
			bis.read(otherArr, 0, 12);
			
			//状态码
			byte[] statusArr = new byte[4];
			bis.read(statusArr, 0, 4);
			
			int statusInt =  DataConvert.bytesToInt(statusArr);
			logger.info("状态码：" + statusInt);
			byte[] commandTypeArr = new byte[2];//命令类型
			bis.read(commandTypeArr, 0, 2);
			short commandType = DataConvert.bytesToShort(commandTypeArr);
			logger.info("命令类型：" + commandType);
			return String.valueOf(statusInt);
		} catch (Exception e) {
			logger.error("协议解析发生异常：" + e.getMessage());
			resStr = null;
		}finally{
			if (bis != null) {
				bis.close();
			}
		}
		return resStr;
	}
	
	

	/**
	 * 集成系统返回的状态码：0表示成功，<0失败
	 * @return 2014-8-20
	 * @throws Exception
	 */
	public String recvStatusCode( short command_type) throws Exception {
		//BufferedInputStream bis = new BufferedInputStream(in, 1024); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		//2014-8-21
		BufferedInputStream bis = new BufferedInputStream(in,1024*1024*4); // 读取后的数据放进缓存：本机内存，然后从内存中读取，即使服务器端关闭也无影响
		
		byte[] byte_identifyCode = null;
		String resStr = "";
		String verifyCode = "";
		try {
			byte_identifyCode = new byte[8]; // 先取出标准头中内容长度8字节之前的内容【亦称验证码】
			int nresult = bis.read(byte_identifyCode, 0, 8);
			if (nresult < 0) {
				logger.error("读取标准头的前8字节时报错!!");
			}
			verifyCode = new String(byte_identifyCode); // 验证码
			logger.info("验证码：" + verifyCode);
			
			// 除验证码外包的长度 
			byte[] byte_alllen = new byte[4];//后续内容长度
			int nresult2 = bis.read(byte_alllen, 0, 4);
			int length = DataConvert.bytesToInt(byte_alllen);
			
			//真正数据长度
			int dataLength = length -20;
			logger.info("后续内容长度：" + length);
			logger.info("真正数据长度：" + dataLength);
			if (nresult2 < 0) {
				logger.error("image:对不起，读取标准头的后4个标识长度字节时报错！！");
			}
			
			//协议类型
			byte[] protoTypeArr = new byte[2];
			bis.read(protoTypeArr, 0, 2);
			short protoType = DataConvert.bytesToShort(protoTypeArr);
			logger.info("协议类型：" + protoType);
			
			byte[] otherArr = new byte[12];//保留字段
			bis.read(otherArr, 0, 12);
			
			byte[] statusArr = new byte[4];//状态码
			bis.read(statusArr, 0, 4);
			int statusInt =  DataConvert.bytesToInt(statusArr);
			logger.info("状态码：" + statusInt);
			
			byte[] commandTypeArr = new byte[2];//命令类型
			bis.read(commandTypeArr, 0, 2);
			short commandType = DataConvert.bytesToShort(commandTypeArr);
			logger.info("socket 返回命令类型：" + commandType+", 管理后台调用时候传递的命令类型是："+command_type);
			
			//对返回的结果做处理——返回有东西.
			/**
			 * 
			 * 
			 */
			return null;
			
		} catch (Exception e) {
			logger.error("协议解析发生异常：" + e.getMessage());
			resStr = null;
		}finally{
			if (bis != null) {
				bis.close();
			}
		}
		return resStr;
	}
	
	/**
	 * 添加新人物
	 * @param bis
	 */
	public String addNewPersonInfo(BufferedInputStream reader ){
		try {
			//pid
			byte[] pidArr = new byte[30];
			reader.read(pidArr, 0, 30);
			String pid = new String(pidArr).trim();
			
			byte[] dataContentLenArr = new byte[4];
			reader.read(dataContentLenArr, 0, 4);
			int xmlLen = DataConvert.bytesToInt(dataContentLenArr);
			logger.info("pid = "+pid+", xml长度xmlLen = "+xmlLen);
			//判断文件的长度来减少使用的时间.
			if (xmlLen==0) {
				logger.info("接收到的人物的PID是："+pid+",但是它却没有相应的xml文件发送过来,所以解析失败,返回-1");
				return null;
			}else {
				String dataContent = new String(QueryReader.getReturnXml(reader, xmlLen), "GBK").trim();
				reader.read(new byte[4], 0, 4);//扩展字段
				//添加数据逻辑--begin
				if (!StringUtil.isNotBlank(pid)) {
					logger.info("没有从字节文件中获取到人物PID,为错误数据,不能入库！");
					return null;
				}
				logger.info("pid:" + pid+" ,addNewPersonInfo() data:\n" + dataContent);
				//解析xml并入库的,判断是否保存成功!
				/**
				 * 
				 * 
				 * 
				 */
				return null;
			}		
		} catch (Exception e) {
			logger.info("从socket中读取添加新增娱乐人物信息出错!");
			return null;
		}
	}

}
