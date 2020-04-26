/*
 * 处理时间工具类
 */
package com.sefonsoft.oa.system.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 处理日期相关工具类
 *
 * @author Aron
 */
public class DateUtils {
  
  
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter DEFAULT_DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		public static final String YEAR = "yyyy";
		public static final String MONTH_OF_YEAR_PATTERN = "yyyy-MM";
		public static final String DATE_PATTERN = "yyyy-MM-dd";
		public static final String MONTH_FIRST_DAY_PATTERN = "yyyy-MM-01";
		public static final String YEAR_FIRST_DAY_PATTERN = "yyyy-01-01";

	/**
	 ************************************************************
	 * @Description:获取当前时间并返回形如 20171017的整形数据
	 * @return 比如 20171017
	 *
	 ************************************************************
	*/
	public static Integer getCourrentDateTimeKey(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.YYYYMMDD_TIME);
		String dateString = formatter.format(currentTime);
		Integer timeKey=Integer.valueOf(dateString);
		return timeKey;
	}

	public static Integer getCourrentDateTimeKey(Date currentTime){
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.YYYYMMDD_TIME);
		String dateString = formatter.format(currentTime);
		Integer timeKey=Integer.valueOf(dateString);
		return timeKey;
	}

	/**
	 *
	 ************************************************************
	 * @Description: 将时间字符串 转换为 date 格式
	 *
	 * @param time    时间字符串
	 * @param format  解析格式
	 * @return  时间date类型
	 * @throws ParseException
	 *
	 ************************************************************
	 */
	public static Date StringFormat(String time,String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
		if (format != null) {
			sdf = new SimpleDateFormat(format);
		}
		return sdf.parse(time);
	}

	/**
	 ************************************************************
	 * @Description:获取当前时间并返回形如 201710的整形数据
	 *
	 * @return 形如 201710
	 *
	 ************************************************************
	*/
	public static Integer getCourrentMouthTimeKey(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.YYYYMM_TIME);
		String dateString = formatter.format(currentTime);
		Integer timeKey=Integer.valueOf(dateString);
		return timeKey;
	}

	/**
	 ************************************************************
	 * @Description:获取今天的某个时间
	 *
	 * @param hour 时
	 * @param minute 分
	 * @param second 秒
	 * @return
	 *
	 ************************************************************
	*/
	public static Date getTodayTime(Integer hour, Integer minute, Integer second) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND,0);
		Date m = c.getTime();
		return m;
	}

	/**
	 ************************************************************
	 * @Description:获取今天的某个时间
	 *
	 * @param timeStr
	 * @return
	 *
	 ************************************************************
	*/
	public static Date getTodayTime(String timeStr){
		String[] timeArray = timeStr.split(":");
		if(timeArray.length != 3){
			return getTodayTime(0,0,0);
		}else{
			return getTodayTime(Integer.valueOf(timeArray[0]),Integer.valueOf(timeArray[1]),Integer.valueOf(timeArray[2]));
		}
	}

	/**
	 ************************************************************
	 * @Description获取固定某天的具体时间
	 * @param thatday
	 * @param hour
	 * @param minute
	 * @param second
	 * @Return java.util.Date
	 * @Exception
	 ************************************************************
	 */
	public static Date getTimeInThatday(Date thatday,Integer hour, Integer minute, Integer second){
		Calendar c = Calendar.getInstance();
		c.setTime(thatday);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		Date m = c.getTime();
		return m;
	}

	/**
	 ************************************************************
	 * @Description 获取固定某天的具体时间
	 * @param thatday
	 * @param timeStr
	 * @Return java.util.Date
	 * @Exception
	 ************************************************************
	 */
	public static Date getTimeInThatday(Date thatday,String timeStr){
		String[] timeArray = timeStr.split(":");
		if(timeArray.length != 3){
			return getTimeInThatday(thatday,0,0,0);
		}else{
			return getTimeInThatday(thatday,Integer.valueOf(timeArray[0]),Integer.valueOf(timeArray[1]),Integer.valueOf(timeArray[2]));
		}
	}

	/**
	 ************************************************************
	 * @Description:将String转Date
	 *
	 * @param date
	 * @param formateString
	 * @return
	 * @throws ParseException
	 *
	 ************************************************************
	*/
	public static Date DateValueOfString(String date,String formateString) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT);
		if(formateString!=null){
			sdf=new SimpleDateFormat(formateString);
		}
			return sdf.parse(date);
	}

	/**
	 ************************************************************
	 * @Description: 格式化时间
	 *
	 * @param date
	 * @param formateString
	 * @return
	 *
	 ************************************************************
	*/
	public static String formatDate(Date date,String formateString){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT);
		if(formateString!=null){
			sdf=new SimpleDateFormat(formateString);
		}
		return sdf.format(date);
	}
	/**
	 ************************************************************
	 * @Description: 格式化时间
	 *
	 * @param date
	 * @param formateString
	 * @return
	 *
	 ************************************************************
	 */
	public static String formatDateMin(Date date,String formateString){
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
		if(formateString!=null){
			sdf=new SimpleDateFormat(formateString);
		}
		return sdf.format(date);
	}

	/**
	 ************************************************************
	 * @Description:获取时间所代表的年份
	 *
	 * @param date
	 * @return
	 *
	 ************************************************************
	*/
	public static Integer getYear(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 ************************************************************
	 * @Description:获取时间所代表的月份
	 *
	 * @param date
	 * @return
	 *
	 ************************************************************
	*/
	public static Integer getMonth(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/**
	 ************************************************************
	 * @Description:获取时间所代表的日
	 *
	 * @param date
	 * @return
	 *
	 ************************************************************
	*/
	public static Integer getDate(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	/**
	 ************************************************************
	 * @Description:获取时间所代表的几点
	 *
	 * @param date
	 * @return
	 *
	 ************************************************************
	*/
	public static Integer getHour(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR);
	}

	/**
	 ************************************************************
	 * @Description:获取时间所代表的为一周的第几天
	 *
	 * @param date
	 * @return
	 *
	 ************************************************************
	*/
	public static Integer getDay(Date date){
		Calendar cal=Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 ************************************************************
	 * @Description:获取时间所代表的为一周的第几天
	 *
	 * @param date
	 * @return
	 *
	 ************************************************************
	*/
	public static String getDayString(Date date){
		Calendar cal=Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			return "日";
		case 2:
			return "一";
		case 3:
			return "二";
		case 4:
			return "三";
		case 5:
			return "四";
		case 6:
			return "五";
		case 7:
			return "六";
		default:
			return "";
		}
	}

	/**
	 ************************************************************
	 * @Description:获取月份的天数
	 *
	 * @return
	 *
	 ************************************************************
	 */
	public static Integer getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		int day = cal.getActualMaximum(Calendar.DATE);
		return day;
	}

	public static boolean isSaturdayOrSunday(Date date)throws ParseException{
		DateFormat format1 = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
		String bDate = formatDate(date,DateUtil.DEFAULT_FORMAT_DATE);
		Date bdate = format1.parse(bDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(bdate);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		{
			return true;
		}else{
			return false;
		}
	}
	/**
	 ************************************************************
	 * @Description:月份日期添加几天
	 *
	 * @param mouth 月份日期
	 * @param days 天数
	 * @return
	 *
	 ************************************************************
	*/
	public static Date mouthAddDays(Date mouth,Integer days){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(mouth);
		cal.add(Calendar.DATE, days-1);
		return cal.getTime();
	}

	/**
	 *
	 ************************************************************
	 * @Description:日期的月份变更
	 *
	 * @param time
	 * @param format
	 * @param n
	 * @return日期
	 * @throws ParseException
	 *
	 ************************************************************
	 */
	public static Date subtractMouth(String time,String format,int n) throws ParseException{
		Date stringFormat = StringFormat(time,format);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(stringFormat);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 *
	 ************************************************************
	 * @Description: 时间月分增减
	 *
	 * @param date 时间
	 * @param n 月
	 * @return 时间
	 * @throws ParseException
	 *
	 ************************************************************
	 */
	public static Date addMouth(Date date,int n) throws ParseException{
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 *
	 ************************************************************
	 * @Description:获取上月日期
	 *
	 * @return获取上月日期
	 *
	 ************************************************************
	 */
	public static Date lastMouth(){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	/**
	 *
	 ************************************************************
	 * @Description: 获取昨天
	 *
	 * @return
	 *
	 ************************************************************
	 */
	public static Date getYesterday(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		Date time=cal.getTime();
		return time;
	}

	/**
	 *
	 ************************************************************
	 * @Description: 获取昨天的下午六点
	 *
	 * @return
	 *
	 ************************************************************
	 */
	public static Date getYesterdayPMsix(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 18);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date time = cal.getTime();
		return time;
	}


	public static Date getDateByMMss(String hour,Date ymd) {
		String[] dateArray = hour.split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTime(ymd);
		cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(dateArray[0]));
		cal.set(Calendar.MINUTE, Integer.valueOf(dateArray[1]));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date time = cal.getTime();
		return time;
	}

	/**
	 *
	 ************************************************************
	 * @Description: 比较两个时间的先后
	 *
	 * @param beginDate
	 * @param endDate
	 * @return flase:开始时间大于结束时间 true: 开始时间小于结束时间
	 *
	 ************************************************************
	 */
	public static boolean subtractDate(Date beginDate,Date endDate){
		if(beginDate == null || endDate == null){
			return false;
		}
		if(beginDate.getTime()-endDate.getTime()>0){
			return false;
		}
		return true;
	}

	/**
	 ************************************************************
	 * @Description:判断两个时间是否是同一天
	 *
	 * @param date1
	 * @param date2
	 * @return
	 *
	 ************************************************************
	*/
	public static boolean isSameDay(Date date1, Date date2) {
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTime(date1);

		Calendar calDateB = Calendar.getInstance();
		calDateB.setTime(date2);

		return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
				&& calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
				&& calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
						.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 *
	 ************************************************************
	 * @Description: 获取第一个时间区间  在第二个时间区间的时间戳
	 *
	 * @param first 时间区间
	 * @param second 有效时间区间
	 * @return
	 *
	 ************************************************************
	 */
	public static long getEffectiveTime(long[] first,long[] second){
		long begin = 0;
		long end = 0;
		/* 判断请假时间有没在下午午有效的时间区间内*/
		if (first[1] < second[0] || first[0] > second[1]) {
		} else {
			/* 遍历请假时间，取出在下午有效时间内的时间 */
			for (int i = 0; i < first.length; i++) {
				if (i == 0) {
					if (second[i] > first[i]) {
						begin = second[i];
					} else {
						begin = first[i];
					}
				} else {
					if (second[i] > first[i]) {
						end = first[i];
					} else {
						end = second[i];
					}
				}
			}
		}
		return end - begin;
	}

	/**
	 * 获取两个date类型日期的天数差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getDateInterValDay(Date begin,Date end){
		return (int) ((end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 获取两个date类型日期的小时差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getDateInterValHour(Date begin,Date end){
		return (int) ((end.getTime() - begin.getTime()) / (60 * 60 * 1000));
	}

	/**
	 * 获取两个date类型日期的分钟差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getDateInterValSecond(Date begin,Date end){
		return (int) ((end.getTime() - begin.getTime()) / (60 * 1000));
	}

	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static Date getPastDate(int past,Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
		Date pastDay = calendar.getTime();
		return pastDay;
	}

	/**
	 * 给指定日期添加指定小时个数
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addDateHours(Date date, int hour){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);// 24小时制
		date = cal.getTime();
		return date;
	}

	/**
	 * 给指定日期减少指定小时个数
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date cutDateHours(Date date, int hour){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, -hour);// 24小时制
		date = cal.getTime();
		return date;
	}

	/**
	 * 当前日期 时分秒 00:00:00
	 * @return
	 */
	public static Date currentDate(){
		LocalDate localDate=new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return java.sql.Date.valueOf(localDate);
	}


	public static String parseDate(Calendar calendar) {

		return calendar.get(Calendar.YEAR) + "-"
				+ getMonth(calendar) + "-"
				+ getDay(calendar);
	}

	public static String getYearMonth(Calendar calendar){

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		String monthStr = month<10?("0"+month):String.valueOf(month);
		return year+"-"+monthStr;
	}

	private static String getMonth(Calendar calendar){
		int month = calendar.get(Calendar.MONTH)+1;
		if(month<10){
			return "0"+month;
		}
		return month+"";
	}

	private static String getDay(Calendar calendar){
		int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
		if(day<10){
			return "0"+day;
		}
		return day+"";
	}

  public static Date toDate(LocalDateTime ldt) {
    return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDateTime toLocalDateTime(Date scheduledFireTime) {
    return scheduledFireTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static java.sql.Date toSqlDate(Date date) {
    return new java.sql.Date(date.getTime());
  }

	/**
	 * 通过季来获得开始时间
	 * @return
	 */
	public static DateTimeFormatter getPatternOfQuarter(){

		int month = LocalDate.now().getMonthValue();
		//1
		if (month >= 1 && month <= 3) {
			return DateTimeFormatter.ofPattern("yyyy-01-01");
		}
		//2
		else if (month >= 4 && month <= 6) {
			return DateTimeFormatter.ofPattern("yyyy-04-01");
		}
		//3
		else if (month >= 7 && month <= 9) {
			return DateTimeFormatter.ofPattern("yyyy-07-01");
		}
		//4
		else {
			return DateTimeFormatter.ofPattern("yyyy-10-01");
		}
	}
}
