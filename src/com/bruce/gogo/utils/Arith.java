package com.bruce.gogo.utils;
/**
 * 2007-11-15
 * @author wangguan
 * ��ֵ����
 */
import java.math.BigDecimal;
import java.text.NumberFormat;
public class Arith {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
//	默认除法运算精度 
	private static final int def_div_scale = 10; 

//	这个类不能实例化 
	private Arith(){ 
	} 

	/** 
	* 提供精确的加法运算。 
	* @param v1 被加数 
	* @param v2 加数 
	* @return 两个参数的和 
	*/ 
	public static Double add(Double v1,Double v2){ 
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.add(b2).doubleValue(); 
	} 

	/** 
	* 提供精确的减法运算。 
	* @param v1 被减数 
	* @param v2 减数 
	* @return 两个参数的差 
	*/ 
	public static Double sub(Double v1,Double v2){ 
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.subtract(b2).doubleValue(); 
	} 

	/** 
	* 提供精确的乘法运算。 
	* @param v1 被乘数 
	* @param v2 乘数 
	* @return 两个参数的积 
	*/ 


	public static Double mul(Double v1,Double v2){ 
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.multiply(b2).doubleValue(); 
	} 

	/** 
	* 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
	* 小数点以后10位，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @return 两个参数的商 
	*/ 
	public static Double div(Double v1,Double v2){ 
	return div(v1,v2,def_div_scale); 
	} 

	/** 
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	* 定精度，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @param scale 表示表示需要精确到小数点以后几位。 
	* @return 两个参数的商 
	*/ 
	public static Double div(Double v1,Double v2,int scale){ 
	if(scale<0){ 
	throw new IllegalArgumentException( 
	"the scale must be a positive integer or zero"); 
	} 
	if(v2==0){
		return Double.valueOf(0);
	}
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
	} 

	/** 
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	* 定精度，以后的数字四舍五入。 
	* @param v1 除数 
	* @param v2 被除数 
	* @param scale 表示表示需要精确到小数点以后几位。 
	* @return 两个参数的商 
	*/ 
	public static String div(int v1,int v2,int scale){
	 // 除数
	  BigDecimal bd = new BigDecimal(v1);
	  // 被除数
	  BigDecimal bd2 = new BigDecimal(v2);
	  // 进行除法运算,保留200位小数,末位使用四舍五入方式,返回结果
	  BigDecimal result = bd.divide(bd2, scale, BigDecimal.ROUND_HALF_DOWN);
	  // 指定想要的小数位长度取值
	  NumberFormat nf = NumberFormat.getNumberInstance();
	  nf.setMaximumFractionDigits(scale);// 最大小数位
	  nf.setMinimumFractionDigits(scale);
	  String str = nf.format(result);
	  return str;
	}
	/** 
	* 提供精确的小数位四舍五入处理。 
	* @param v 需要四舍五入的数字 
	* @param scale 小数点后保留几位 
	* @return 四舍五入后的结果 
	*/ 
	public static Double round(Double v,int scale){ 
	if(scale<0){ 
	throw new IllegalArgumentException( 
	"the scale must be a positive integer or zero"); 
	} 
	BigDecimal b = new BigDecimal(Double.toString(v)); 
	BigDecimal one = new BigDecimal("1"); 
	return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
	} 
}
