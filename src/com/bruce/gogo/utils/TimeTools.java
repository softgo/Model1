package com.bruce.gogo.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;


//import com.hysh.util.Debuger;

/**
 * <p>
 * Title: TimeTools.java
 * </p>
 * <p>
 * Description: 提供所有对日期，时间操作的方法
 * </p>
 */

public class TimeTools {
	public static void main(String[] args) {
		try
		{
//			System.out.println(calcHMS(12388));
//			System.out.println(TimeTools.dateToString(new Date(), "yyyy-mm-dd"));
//			System.out.println(TimeTools.getDate());
//			System.out.println(TimeTools.getCurrentDate() );
//		    System.out.println(dateAdd(TimeTools.getDate(),-6));
//		    System.out.println("----------" + monthAdd(TimeTools.getDate(), -1) );
//		    java.util.Random r=new java.util.Random();
//
//		    SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
//		    System.out.println(fd.format(new Date())+r.nextInt());
//		    System.out.println(fd.format(new Date())+r.nextInt());
			System.out.println(TimeTools.getDateByLong(Long.parseLong("000")));
			System.out.println(getStringDate());
		} 
		catch (Exception ex) 
		{
		} 
	}

	private static GregorianCalendar c = new GregorianCalendar();

	private static String year;

	private static String month;

	private static String day;

	private static String hour;
	/**
	 * 初始化当前时间的日期(包括年,月,日,小时)
	 */
	static {
		init();
	}

	public TimeTools() {
	}
	
	/**
	 * 获取昨天向前，前七天日志记录 如，今天7.9统计的是昨天7.8的下载量，故获取2号~8号的记录
	 * 
	 * @param 输入参数
	 *            1或7
	 */
	public static String getHistory(int input) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int d = c.get(Calendar.DAY_OF_MONTH);
		d = d - input;
		c.set(Calendar.DAY_OF_MONTH, d);
		// System.out.println(df.format(c.getTime()));
		return df.format(c.getTime());
	}
	/**
	 * 得格式如20121105的日期
	 * @param input
	 * @return
	 */
	public static String getHistoryByEntra(int input) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		int d = c.get(Calendar.DAY_OF_MONTH);
		d = d - input;
		c.set(Calendar.DAY_OF_MONTH, d);
		return df.format(c.getTime());
	}
	
	public static String getHistory(String str,int input) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date da=null;
		try
		{
			da=df.parse(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(da);
        c.add(Calendar.DAY_OF_MONTH, -input);
		// System.out.println(df.format(c.getTime()));
		return df.format(c.getTime());
	}
	
	public static String getDateByLong(long time){
		
		Date date = new Date(time);
	  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		return sdf.format(date); 
	}
	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 * @return boolean
	 */
	public static boolean isLeapYear(int year)
	{
		boolean ret = false;
		
		if(year%4 != 0)
			ret = false;
		else if(year%100 != 0)
			ret = true;
		else if(year%400 != 0)
			ret = false;
		else
			ret = true;
		
		return ret;	
	}

	
	/**
	 * 根据 年 月 取得当年那个月的天数
	 * 
	 * @param year
	 * @param mouth
	 * @return int
	 */
	public static int getMouthDate(int year, int mouth)
	{
		int count = 0;
		switch(mouth)
		{
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				count = 31;
				break;
			case 2:
				if(isLeapYear(year))
					count = 29;
				else
					count = 28;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				count = 30;
				break;
		}
		return count;
	}

	
	
	/**
	 * 取得输入日期那个月的第一天
	 * 
	 * @param token
	 * @return String
	 */
	public static String getMonthStart(String token, String date)
	{
		StringTokenizer st = new StringTokenizer(date,token);
		String year = st.nextToken();
		String month = st.nextToken();
		
		return (TimeTools.getTransMonth(new Integer(year).intValue(), new Integer(month).intValue()) + token + "01");
	}
	
	/**
	 * 取得输入日期那个月的最后一天
	 * 
	 * @param token
	 * @return String
	 */
	public static String getMonthEnd(String token, String date)
	{
		StringTokenizer st = new StringTokenizer(date,token);
		String year = st.nextToken();
		String month = st.nextToken();

		return (TimeTools.getTransMonth(new Integer(year).intValue(), new Integer(month).intValue()) + token + TimeTools.getMouthDate(new Integer(year).intValue(), new Integer(month).intValue()) + " 23:59:59");
	}
	
	
	
	/**
	 * 获得当前时间的日期(包括年,月,日,小时)
	 * 
	 * @return String 日期：xxxx.xx.xx
	 */
	public static String getTime() {
		String tmp = "";

		tmp = year + "-" + month + "-" + day + " " + hour;

		return tmp;
	}

	/**
	 * 获得当前时间的日期(包括年,月,日)
	 * 
	 * @return String 日期：xxxx.xx.xx
	 */
	public static String getDate() {
		String tmp = "";

		tmp = year + "-" + month + "-" + day;

		return tmp;
	}

	/**
	 * 获得当前时间的日期(包括年,月,日)
	 * @return 
	 */
	public static java.sql.Date getCurrentDate() 
	{
		//System.out.println(getDate()); 
		return getSqlDateFromString(getDate());
	}

	/**
	 * 获得当前时间的日期(包括年,月,日,小时)
	 * 
	 * @param c
	 *            GregorianCalendar 类型
	 * @return String 日期：xxxx.xx.xx
	 */
	public static String getTime(GregorianCalendar c) {
		String tmp = "";

		tmp = year + "-" + month + "-" + day + " " + hour;

		return tmp;
	}

	/**
	 * 获得当前时间的年份月份
	 * 
	 * @return String 返回当前时间的年份月份
	 */
	public static String getYM() {
		String tmp = "";

		tmp = year + "-" + month;

		return tmp;
	}

	/**
	 * 获得当前时间的年份
	 * 
	 * @return int 返回当前时间的年份
	 */
	public static int getYear() {
		int year = c.get(Calendar.YEAR);

		return year;
	}

	/**
	 * 获得当前时间的月份
	 * 
	 * @return int 返回当前时间的月份
	 */
	public static int getMonth() {
		int month = c.get(Calendar.MONTH) + 1;

		return month;
	}

	/**
	 * 获得当前时间的日
	 * 
	 * @return int 返回当前时间的日
	 */
	public static int getDay() {

		int day = c.get(Calendar.DATE);

		return day;
	}

	// 获得当前时间的小时
	public static int getHour() {
		int hour = c.get(Calendar.HOUR);

		return hour;
	}

	// 获得当前时间的分钟
	public static int getMinute() {
		int minute = c.get(Calendar.MINUTE);

		return minute;

	}

	// //获得当前时间的秒
	public static int getSecond() {
		int second = c.get(Calendar.SECOND);

		return second;
	}

	/**
	 * 获得当前周的周日的日期
	 * 
	 * @return String 日期：xxxx.xx.xx
	 */
	public static String getWeekday(int index) {
		int n = c.get(Calendar.DAY_OF_WEEK) - 1;

		c.add(Calendar.DAY_OF_YEAR, -1 * n);
		c.add(Calendar.DAY_OF_YEAR, 7 * index);

		String result = year + "-" + month + "-" + day;

		return result;
	}

	/**
	 * 获得当前日期的描述信息
	 * 
	 * @return String 日期：xxxx.xx.xx
	 */
	public static String getDateInfo() {
		String date = year + "年" + month + "月" + day + "日";

		return date;
	}

	private static void init() {
		year = String.valueOf(c.get(Calendar.YEAR)).trim();

		month = String.valueOf(c.get(Calendar.MONTH) + 1).trim();

		day = String.valueOf(c.get(Calendar.DATE)).trim();

		hour = String.valueOf(c.get(Calendar.HOUR)).trim();

		if (hour.length() < 2) {
			hour = "0" + hour;
		}
		if (day.length() < 2) {
			day = "0" + day;
		}
		if (month.length() < 2) {
			month = "0" + month;
		}
	}

	/**
	 * 将不合理的年月转换成合理的年月
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return String 年月
	 */
	public static String getTransMonth(int year, int month) {
		// int iOver = month - 12 ;
		if (month < 0) {
			year = year - 1;
			month = 12 + month;
		} else if (month > 12) {
			year = year + 1;
			month = month - 12;
		}

		String sMonth = String.valueOf(month).trim();

		if (sMonth.length() < 2) {
			sMonth = "0" + sMonth;
		}
		String ym = year + "-" + sMonth;

		return ym;
	}

	/**
	 * 将不合理的年月转换成合理的日期
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return String 年月
	 */
	public static String getTransMonth(String year, String month, String day) {
		// int iOver = month - 12 ;
		int iYear = getYear();
		int iMonth = getMonth();
		int iDay = getDay();

		if (year != null && year.trim().length() > 0) {
			iYear = Integer.parseInt(year);
		}
		if (month != null && month.trim().length() > 0) {
			iMonth = Integer.parseInt(month);
		}
		if (day != null && day.trim().length() > 0) {
			iDay = Integer.parseInt(day);
		}

		if (iMonth < 0) {
			iYear = iYear - 1;
			iMonth = 12 + iMonth;
		} else if (iMonth > 12) {
			int iLen = new Integer(iMonth / 12).intValue();

			iYear = iYear + iLen;
			iMonth = iMonth % 12;
		}

		String sMonth = String.valueOf(iMonth).trim();

		String sDay = String.valueOf(iDay).trim();

		if (sMonth.length() < 2) {
			sMonth = "0" + sMonth;
		}
		if (sDay.length() < 2) {
			sDay = "0" + sDay;
		}
		String ym = iYear + "-" + sMonth + "-" + sDay;

		return ym;
	}

	/**
	 * 按指定格式返回日期类型
	 * 
	 * @param cal
	 *            java.util.Calendar
	 * @param format
	 *            要返回的格式 如："yyyy-mm-dd hh:mm:ss"
	 * @return String 按指定格式返回日期类型
	 */
	static public final String getFormatDate(String format) {
		String sDate = null;
		int len = format.length();
		int i = 0;
		int year = getYear();
		int month = getMonth();
		int day = getDay();
		int hour = getHour();
		int minute = getMinute();
		int second = getSecond();
		//String sYear, sMonth, sDay, sHour, sMinute, sSecond;
		for (; i < len; i++) {
			char ch = format.charAt(i);
			if (Character.isSpaceChar(ch))
				break;
		}
		if (i < len) {
			if (i == 8) {
				sDate = ""
						+ year
						+ (month < 10 ? "0" : "")
						+ month
						+ (day < 10 ? "0" : "")
						+ day
						+ " "
						+ hour
						+ (minute < 10 ? "" + format.charAt(11) + "0" : ""
								+ format.charAt(11))
						+ minute
						+ (second < 10 ? "" + format.charAt(14) + "0" : ""
								+ format.charAt(14)) + second;
			}
			if (i == 10) {
				sDate = ""
						+ year
						+ format.charAt(4)
						+ (month < 10 ? "0" : "")
						+ month
						+ format.charAt(7)
						+ (day < 10 ? "0" : "")
						+ day
						+ " "
						+ hour
						+ (minute < 10 ? "" + format.charAt(13) + "0" : ""
								+ format.charAt(13))
						+ minute
						+ (second < 10 ? "" + format.charAt(16) + "0" : ""
								+ format.charAt(16)) + second;
			}
			if (i > 10) {

			}
		}
		if (i == len) {
			if (i == 8) {
				sDate = "" + year + (month < 10 ? "0" : "") + month
						+ (day < 10 ? "0" : "") + day;
			}
			if (i == 10) {
				sDate = "" + year + format.charAt(4) + (month < 10 ? "0" : "")
						+ month + format.charAt(7) + (day < 10 ? "0" : "")
						+ day;
			}
			if (i > 10) {

			}
		}
		return sDate;
	}

	/**
	 * Description ： 格式化显示日期型数据
	 * 
	 * @param Date
	 *            dt_in ：日期型数据 boolean bShowTimePart_in ： 是否显示时间部分
	 * @return String 格式化后的日期格式
	 */

	public static String FormatDate2String(java.util.Date dt_in, boolean ShowTimePart) {
		if (ShowTimePart)
			return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(dt_in);
		else
			return (new SimpleDateFormat("yyyy-MM-dd")).format(dt_in);
	}

	/**
	 * 两个时间相减的天数
	 * 
	 * dt,date1,date2格式必须为"yyyymmdd"
	 * 
	 */
	static public int diffDate(String date1, String date2) {
		int arry1[] = new int[3];
		arry1 = datetoInt(date1);
		int arry2[] = new int[3];
		arry2 = datetoInt(date2);
		long result = (toLongTime(arry1[0], arry1[1], arry1[2]) - toLongTime(
				arry2[0], arry2[1], arry2[2])) / (24 * 60 * 60 * 1000);
		return (int) result;
	}

	/**
	 * 相加后的时间.
	 * 
	 * @param d
	 *            被加的时间,yyyy-mm-dd
	 * @param times
	 *            要加的年数,
	 * @return flag,"y":表示年，"d":表示天
	 */
	static public final String getAddDate(String d, int times, String flag) {
		String sValue = null;
		if (flag.equals("y")) {
			String y = d.substring(0, 4);
			int n = Integer.parseInt(y) + times;
			sValue = "" + n + d.substring(4);
		}
		if (flag.equals("d")) {
			String one = cancelDelimiter(d);
			int[] arry = datetoInt(one);
			long l = toLongTime(arry[0], arry[1], arry[2]);
			long sum = l + (long) times * 24 * 60 * 60 * 1000;
			Calendar cal = getStaticCalendars(sum);
			String str = calToString(cal);
			sValue = addDelimiter(str, '-');
		}
		return sValue;
	}

	public static java.util.Date toDate(int year, int month, int day) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-mm-dd");

		if (staticCal == null)
			staticCal = new GregorianCalendar();
		staticCal.clear();
		staticCal.set(Calendar.YEAR, year);
		staticCal.set(Calendar.MONTH, month - 1);
		staticCal.set(Calendar.DAY_OF_MONTH, day); // day-1??
		try {
			return bartDateFormat.parse(bartDateFormat.format(staticCal
					.getTime()));
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return null;
		}// .getTime();

	}

	public static long toLongTime(int year, int month, int day) {
		return toDate(year, month, day).getTime();
	}

	/**
	 * dt,date1,date2格式必须为"yyyymmdd"
	 */
	static public int[] datetoInt(String dt) {
		int arry[] = new int[3];
		int y = Integer.parseInt(dt.substring(0, 4));
		int m = Integer.parseInt(dt.charAt(4) == '0' ? "" + dt.charAt(5) : dt
				.substring(4, 6));
		int d = Integer.parseInt(dt.charAt(6) == '0' ? "" + dt.charAt(7) : dt
				.substring(6));
		arry[0] = y;
		arry[1] = m;
		arry[2] = d;
		return arry;
	}

	static public final String calToString(Calendar cal) {
		int year = cal.get(Calendar.YEAR);// ((java.sql.Timestamp)value).getYear()
											// + 1900;
		int month = cal.get(Calendar.MONTH) + 1;// ((java.sql.Timestamp)value).getMonth()
												// + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);// ((java.sql.Timestamp)value).getDate();
		return "" + year + (month < 10 ? "0" : "") + month
				+ (day < 10 ? "0" : "") + day;
	}

	static public final String addDelimiter(String sdate, char delimiter) {
		String hdate = sdate.substring(0, 4) + delimiter
				+ sdate.substring(4, 6) + delimiter + sdate.substring(6);
		return hdate;
	}

	static public final String cancelDelimiter(String sdate) {
		String hdate = sdate.substring(0, 4) + sdate.substring(5, 7)
				+ sdate.substring(8);
		return hdate;
	}

	private static Calendar staticCal;

	public static Calendar getStaticCalendars(java.util.Date date) {
		if (staticCal == null)
			staticCal = new GregorianCalendar();
		if (date != null)
			staticCal.setTime(date);
		return staticCal;
		// utcCal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		// defaultCenturyStart = staticCal.get(Calendar.YEAR) - 80;
	}

	/*
	 * public static Calendar getStaticCalendars(java.util.Date date) {
	 * if(staticCal==null) staticCal = new GregorianCalendar(); if( date!=null )
	 * staticCal.setTime(date); return staticCal; //utcCal = new
	 * GregorianCalendar(TimeZone.getTimeZone("GMT")); //defaultCenturyStart =
	 * staticCal.get(Calendar.YEAR) - 80; }
	 */
	public static Calendar getStaticCalendars(long time) {
		Calendar cal = getStaticCalendars(null);
		if (cal != null)
			cal.setTime(new java.util.Date(time));
		return cal;
	}

	/**
	 * 把一个时间转化为字符串.
	 * 
	 * @param day
	 *            被转化的时间,如果day=null表示当前时间
	 * @param format
	 *            转化的格式,例如format="yyyy-mm-dd hh:mm:ss"
	 * @return 转化后的字符串
	 */
	static public final String dateToString(java.util.Date date, String format) {
		String sDate = null;
		int len = format.length();
		int i = 0;
		
		if (date == null)
			date = new Date();
			
		Calendar cal = getStaticCalendars(date);// (java.sql.Timestamp)value;
		int year = cal.get(Calendar.YEAR);// ((java.sql.Timestamp)value).getYear()
											// + 1900;
		int month = cal.get(Calendar.MONTH) + 1;// ((java.sql.Timestamp)value).getMonth()
												// + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);// ((java.sql.Timestamp)value).getDate();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		//String sYear, sMonth, sDay, sHour, sMinute, sSecond;
		for (; i < len; i++) {
			char ch = format.charAt(i);
			if (Character.isSpaceChar(ch))
				break;
		}
		if (i < len) {
			if (i == 8) {
				sDate = ""
						+ year
						+ (month < 10 ? "0" : "")
						+ month
						+ (day < 10 ? "0" : "")
						+ day
						+ " "
						+ hour
						+ (minute < 10 ? "" + format.charAt(11) + "0" : ""
								+ format.charAt(11))
						+ minute
						+ (second < 10 ? "" + format.charAt(14) + "0" : ""
								+ format.charAt(14)) + second;
			}
			if (i == 10) {
				sDate = ""
						+ year
						+ format.charAt(4)
						+ (month < 10 ? "0" : "")
						+ month
						+ format.charAt(7)
						+ (day < 10 ? "0" : "")
						+ day
						+ " "
						+ hour
						+ (minute < 10 ? "" + format.charAt(13) + "0" : ""
								+ format.charAt(13))
						+ minute
						+ (second < 10 ? "" + format.charAt(16) + "0" : ""
								+ format.charAt(16)) + second;
			}
			if (i > 10) {

			}
		}
		if (i == len) {
			if (i == 8) {
				sDate = "" + year + (month < 10 ? "0" : "") + month
						+ (day < 10 ? "0" : "") + day;
			}
			if (i == 10) {
				sDate = "" + year + format.charAt(4) + (month < 10 ? "0" : "")
						+ month + format.charAt(7) + (day < 10 ? "0" : "")
						+ day;
			}
			if (i > 10) {

			}
		}
		return sDate;
	}

	/**
	 * 根据日期计算星期0:星期日1:星期一,依此类推
	 * 
	 * @param String
	 *            日期格式yyyy-mm-dd
	 * @return int 格式转换出错时返回码为1000
	 */
	public static int dayofweek(String date) {
		String[] a1 = date.trim().split(" ");
		String[] b1 = a1[0].trim().split("-");
		int year, month, day;
		try {
			year = Integer.parseInt(b1[0]);
			month = Integer.parseInt(b1[1]);
			day = Integer.parseInt(b1[2]);
		} catch (Exception e) {
			return 1000;
		}

		int a = (14 - month) / 12;
		int y = year - a;
		int m = month + 12 * a - 2;
		return (day + y + y / 4 - y / 100 + y / 400 + (31 * m) / 12) % 7;
	}

	/**
	 * 字符串转换成日期类型,格式为"yyyy-mm-dd"
	 * 
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date) {
		String[] a = date.split(" ");
		String[] d = a[0].split("-");
		int year = Integer.parseInt(d[0]);
		int month = Integer.parseInt(d[1]);
		int day = Integer.parseInt(d[2]);
		if (a.length == 2) {
			String[] e = a[1].split(":");
			int hour = Integer.parseInt(e[0]);
			int minute = Integer.parseInt(e[1]);
			int second = Integer.parseInt(e[2]);
			return toDate(year, month, day, hour, minute, second);
		}
		return toDate(year, month, day);
	}

	/**
	 * 格式为"yyyy-mm-dd"字符串转换成日期类型,格式为"yyyy-mm-dd",
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDateFromString(String dateStr)

	{
		return java.sql.Date.valueOf(dateStr);
	}

	public static Date toDate(int year, int month, int day, int hour,
			int minute, int second) {
		if (staticCal == null)
			staticCal = new GregorianCalendar();
		staticCal.clear();
		staticCal.set(Calendar.YEAR, year);
		staticCal.set(Calendar.MONTH, month - 1);
		staticCal.set(Calendar.DAY_OF_MONTH, day);
		staticCal.set(Calendar.HOUR_OF_DAY, hour);
		staticCal.set(Calendar.MINUTE, minute);
		staticCal.set(Calendar.SECOND, second);
		return staticCal.getTime();
	}

	/**
	 * 两个日期相减的年限
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getYear(String date1, String date2) {
		int year1 = Integer.parseInt(date1.substring(0, 4));
		int year2 = Integer.parseInt(date2.substring(0, 4));
		int year = year1 - year2;
		if (year > 0) {
			return year;
		}
		return (-1) * year;
	}
	
	/** 将当前日期加减n天数。 
	* 如传入字符型"-5" 意为将当前日期减去5天的日期 
	* 如传入字符型"5" 意为将当前日期加上5天后的日期 
	* 返回字串 例(1999-02-03) 
	*/ 
	public static String dateAdd(String date, int to) 
	{ 
		//	日期处理模块 (将日期加上某些天或减去天数)返回字符串 
		int strTo = to; 
		
		Calendar strDate=Calendar.getInstance();
		strDate.setTime(getDateFromString(date, "-"));

		strDate.add(strDate.DATE, strTo); //日期减 如果不够减会将月变动 
		
		String meStrDate = strDate.get(strDate.YEAR) + "-" + 
		String.valueOf(strDate.get(strDate.MONTH)+1) + "-" + strDate.get(strDate.DATE); 

		
		return (TimeTools.dateToString(getDateFromString(meStrDate, "-"), "yyyy-mm-dd")); 
	}
    
	
	/**
	 *  日期中月的加减
	 * @param String date
	 * @param int to  加上某些月或减去月数 正数为加，负数为减
	 * @return String
	 */
	public static String monthAdd(String date, int to) 
	{ 
		//	日期处理模块 (将日期加上某些月或减去月数)返回字符串 
		int strTo = to; 
		
		Calendar strDate=Calendar.getInstance();
		strDate.setTime(getDateFromString(date, "-"));

		strDate.add(strDate.MONTH, strTo); //日期减 如果不够减会将月变动 
		
		String meStrDate = strDate.get(strDate.YEAR) + "-" + 
		String.valueOf(strDate.get(strDate.MONTH)+1) + "-" + strDate.get(strDate.DATE); 

		
		return (TimeTools.dateToString(getDateFromString(meStrDate, "-"), "yyyy-mm-dd")); 
	}
	/**
	 *  日期中分钟的加减
	 * @param String date
	 * @param int to  分钟数 正数为加，负数为减
	 * @return String
	 */
	public static String minuteAdd(Date date, int to) 
	{ 
		Calendar   strDate=Calendar.getInstance();   
		strDate.setTime(date);
		  strDate.add(strDate.MINUTE,to);   
		  Format   s   =   new   SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");   
		  return s.format(strDate.getTime());  
	}
/**
 *  日期中月的加减
 * @param Date date
 * @param int to  加上某些月或减去月数 正数为加，负数为减
 * @return Date
 */
	public static Date monthAdd(Date date, int to) 
	{ 
		//	日期处理模块 (将日期加上某些月或减去月数)返回字符串 
		int strTo = to; 
		
		Calendar strDate=Calendar.getInstance();
		strDate.setTime(date);

		strDate.add(strDate.MONTH, strTo); //日期减 如果不够减会将月变动 
		
		String meStrDate = strDate.get(strDate.YEAR) + "-" + 
		String.valueOf(strDate.get(strDate.MONTH)+1) + "-" + strDate.get(strDate.DATE); 

		
		return getDateFromString(meStrDate, "-"); 
	}
	//日期中年的加减
	public static String yearAdd(String date, int to) 
	{ 
		//	日期处理模块 (将日期加上某些天或减去天数)返回字符串 
		int strTo = to; 
		
		Calendar strDate=Calendar.getInstance();
		strDate.setTime(getDateFromString(date, "-"));

		strDate.add(strDate.YEAR, strTo); //日期减 如果不够减会将月变动 
		
		String meStrDate = strDate.get(strDate.YEAR) + "-" + 
		String.valueOf(strDate.get(strDate.MONTH)+1) + "-" + strDate.get(strDate.DATE); 

		
		return (TimeTools.dateToString(getDateFromString(meStrDate, "-"), "yyyy-mm-dd")); 
	}

	
	
	
	/*
	 * 取得输入月份上个月的第一天
	 */
	public static String getLastMonthStart(String base)
	{
		String result = null;
		String temp = null;
		int year = 0;
		int month = 0;		
		int date = 0;
		StringTokenizer st = new StringTokenizer(base, "-");
		
		if(st.hasMoreTokens())
		{
			temp = st.nextToken();
			year = Integer.parseInt(temp);
			
		}
		if(st.hasMoreTokens())
		{
			temp = st.nextToken();
			month = Integer.parseInt(temp);
			
		}
		if(st.hasMoreTokens())
		{
			temp = st.nextToken();
			date = Integer.parseInt(temp);
		}
		
		
		month = month - 1;
		while(month == 0)
		{
			year--;
			month = month + 12;
		}
		
		if(month >= 10 && date >= 10)
			result = year + "-" + month + "-" + "01";
		else if(month >= 10 && date < 10 )
			result = year + "-"  + month + "-" + "01";
		else if(month < 10 && date >= 10 )
			result = year + "-" + "0" + month + "-" + "01";
		else 
			result = year + "-" + "0" + month + "-" + "01";
			
		return result;
	}
	
	
	
	/*
	 * 取得输入月份上个月的最后一天
	 */
	public static String getLastMonthEnd(String base)
	{
		String end = getLastMonthStart(base);
		end = TimeTools.getMonthEnd("-", end);
		return end;
	}
	
	 public static boolean isInteger(String initStr){
	        try{
	          new Integer(initStr);	
	        }catch(Exception e){
	          return false;	
	        }
	        
	        return true;
	    }
		
		
		
		
		//Transform string to date.
		//If dateStr is invalid,return today.
		public static Date getDateFromString(String dateStr,String token){
			if(token == null || "".equals(token))
				token = "/";
			
			if("".equals(dateStr)) return new Date();
			if(dateStr.indexOf(token) == -1){
			  if(!isInteger(dateStr)) return new Date();	
			}
			
			int year = 0;
			int month = 0;
			int date = 0;
			StringTokenizer st = new StringTokenizer(dateStr,token,false);
			String temp = st.nextToken().trim();
			if(isInteger(temp))
				year = Integer.parseInt(temp);
			
			if(st.hasMoreTokens()){
			    temp = st.nextToken().trim();	
			    if(isInteger(temp))
				    month = Integer.parseInt(temp) - 1;
			}
			
			if(st.hasMoreTokens()){
			    temp = st.nextToken().trim();	
			    if(isInteger(temp))
				    date = Integer.parseInt(temp);
			}
			
			try{
				Calendar c = Calendar.getInstance();
		    	c.set(year,month,date,0,0,0);
		    	return c.getTime();
			}catch(Exception e){
			    e.printStackTrace();	
			}
			
			return new Date();
		}
		
		
		//Transform date to string.
		//If date is null,return today.
		public static String getStringFromDate(Date date,String token){
			String result = "";
			if(date == null)
				return "";
			
			if(token == null || "".equals(token))
				token = "/";
			
			return result + (date.getYear() + 1900) + token + 
			       (date.getMonth() + 1) + token + 
				   date.getDate();
		}
		
		public static String getStringFromDateAndTime(Date date,String token){
			String result = "";
			if(date == null)
				date = new Date();
			
			if(token == null || "".equals(token))
				token = "/";
			
			return result + (date.getYear() + 1900) + token + 
			       (date.getMonth() + 1) + token + 
				   date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		}
		/**
		 * 计算时分秒
		 * @param timeInSeconds
		 */
		   public static String calcHMS(int timeInSeconds) {
			      int hours, minutes, seconds;
			      hours = timeInSeconds / 3600;
			      timeInSeconds = timeInSeconds - (hours * 3600);
			      minutes = timeInSeconds / 60;
			      timeInSeconds = timeInSeconds - (minutes * 60);
			      seconds = timeInSeconds;
//			      System.out.println(hours + " hour(s) " + minutes + " minute(s) " + seconds + " second(s)");
			      String str ="";
			      String h="";
			      String m="";
			      String s="";
			      if(hours < 10){
			    	  h += "0"+hours;
			      }
			      else{
			    	  h += hours;
			      }
			      if(minutes < 10){
			    	  m += "0"+minutes;
			      }
			      else{
			    	  m += minutes;
			      }
			      if(seconds < 10){
			    	  s += "0"+seconds;
			      }
			      else{
			    	  s += seconds;
			      }
			      return h+":"+m+":"+s;
			   }
	public static Date strToDate(String str) {
		Date date = null;
		try {
			date = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA)
					.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
			date = null;
		}

		return date;

	}
	
	
	public static String getStringDate() {
		String date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date=format.format(new Date());
		} catch (Exception e) {
			date = null;
		}
		return date;
	}
}