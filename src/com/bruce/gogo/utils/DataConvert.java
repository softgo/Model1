package com.bruce.gogo.utils;

import java.util.ArrayList;
import java.util.List;

public class DataConvert 
{
	public List StringArrayToList(String[] strArray)
	{
		List list = new ArrayList();
		for(int i=0; i<strArray.length; i++)
		{
			list.add(new OptionsInt(i,strArray[i]));
		}
		return list;
	}
	
	/**将int转为低字节在前，高字节在后的byte数组 
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int num) {   
		
		byte a[] = new byte[4];
		for (int i = 0; i < 4; i++) {
			a[i] = (byte) ((num >> (i * 8)) & 0xff);
		}
		return a;
		
		 }
	/**将int转为高字节在前，低字节在后的byte数组 
	 * @param i
	 * @return
	 */
	public static byte[] convertIntTo(int i) {
	
		byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }
	
	public static byte[] longToByte(long number) { 
		long temp = number; 
        byte[] b =new byte[8]; 
        for(int i =0; i < b.length; i++){ 
            b[i]=new Long(temp &0xff).byteValue();// 
            temp = temp >>8;// 向右移8位 
        } 
        return b; 
    }
	public static byte[] long2bytes(long num) {
		   byte[] b = new byte[8];
		   for (int i = 0; i < 8; i++) {
		    b[i] = (byte) (num >>> (56 - i * 8));
		   }
		   return b;
		}
	 /** 
     * 将低字节数组转换为int 
     * @param b byte[] 
     * @return int 
     */  
   public static int lBytesToInt(byte[] b) {  
	   return    b[3] & 0xff 
       | (b[2] & 0xff) << 8 
       | (b[1] & 0xff) << 16
       | (b[0] & 0xff) << 24;
   }   
	/**
     * 
     *转为低字节在前，高字节在后的int
     * @param n int
     * @return byte[]
     */
  public static int bytesToInt(byte b[]) {
       
	  int s = 0;  
	     for (int i = 0; i < 3; i++) {  
	       if (b[3-i] >= 0) {  
	       s = s + b[3-i];  
	       } else {  
	       s = s + 256 + b[3-i];  
	       }  
	       s = s * 256;  
	     }  
	     if (b[0] >= 0) {  
	       s = s + b[0];  
	     } else {  
	       s = s + 256 + b[0];  
	     }  
	     return s;  
	 
    }
  /**
   * 转为低字节在前，高字节在后的int
   * @param b
   * @return l
   */
  public static long bytesToLong(byte[] b) {
	  long l = 0; 

	  l = b[0]; 

	  l |= ((long) b[1] << 8); 

	  l |= ((long) b[2] << 16); 

	  l |= ((long) b[3] << 24); 

	  l |= ((long) b[4] << 32); 

	  l |= ((long) b[5] << 40); 

	  l |= ((long) b[6] << 48); 

	  l |= ((long) b[7] << 56); 

	  return l;
  }
	  /**将short转为低字节在前，高字节在后的byte数组 
	 * @param n
	 * @return
	 */
  public static byte[] shortToBytes(short n) {
//	  byte[] b = new byte[2];
//	  b[1] = (byte) (n & 0xff);
//	  b[0] = (byte) ((n >> 8) & 0xff);
	  byte[] b = new byte[2];  
      b[0] = (byte) (n & 0xff);  
      b[1] = (byte) (n >> 8 & 0xff);  
	  
	  return b;
	  }
  
  /**
   * 低字节数组到short的转换 
 * @param b
 * @return
 */
  public static short bytesToShort(byte[] b) {
//	  return (short) (b[1] & 0xff
//	  | (b[0] & 0xff) << 8);
	  int s = 0;  
      if (b[1] >= 0) {  
        s = s + b[1];  
        } else {  
        s = s + 256 + b[1];  
        }  
        s = s * 256;  
      if (b[0] >= 0) {  
        s = s + b[0];  
      } else {  
        s = s + 256 + b[0];  
      }  
      short result = (short)s;  
      return result;  
  }
  
  /**
   * 系统提供的数组拷贝方法arraycopy
   * */
  public static byte[] sysCopy(List<byte[]> srcArrays) {
  
   int len = 0;
   for (byte[] srcArray:srcArrays) {
    len+= srcArray.length;
   }
      byte[] destArray = new byte[len];   
      int destLen = 0;
      for (byte[] srcArray:srcArrays) {
          System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);   
          destLen += srcArray.length;   
      }   
      return destArray;
  }  
  
  /** 
   * 浮点转换为字节 
   *  
   * @param f 
   * @return 
   */  
  public static byte[] float2byte(float f) {  
        
      // 把float转换为byte[]  
      int fbit = Float.floatToIntBits(f);  
        
      byte[] b = new byte[4];    
      for (int i = 0; i < 4; i++) {    
          b[i] = (byte) (fbit >> (24 - i * 8));    
      }   
        
      // 翻转数组  
      int len = b.length;  
      // 建立一个与源数组元素类型相同的数组  
      byte[] dest = new byte[len];  
      // 为了防止修改源数组，将源数组拷贝一份副本  
      System.arraycopy(b, 0, dest, 0, len);  
      byte temp;  
      // 将顺位第i个与倒数第i个交换  
      for (int i = 0; i < len / 2; ++i) {  
          temp = dest[i];  
          dest[i] = dest[len - i - 1];  
          dest[len - i - 1] = temp;  
      }  
        
      return dest;  
        
  }  
    
  /** 
   * 字节转换为浮点 
   *  
   * @param b 字节（至少4个字节） 
   * @param index 开始位置 
   * @return 
   */  
  public static float byte2float(byte[] b, int index) {    
      int l;                                             
      l = b[index + 0];                                  
      l &= 0xff;                                         
      l |= ((long) b[index + 1] << 8);                   
      l &= 0xffff;                                       
      l |= ((long) b[index + 2] << 16);                  
      l &= 0xffffff;                                     
      l |= ((long) b[index + 3] << 24);                  
      return Float.intBitsToFloat(l);                    
  } 
}
