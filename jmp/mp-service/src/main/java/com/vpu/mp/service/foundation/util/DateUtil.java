package com.vpu.mp.service.foundation.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Date工具
 *
 * @author: 卢光耀
 * @date: 2019-07-26 10:51
 *
 */
public final class DateUtil {

	protected static ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = ThreadLocal
			.withInitial(() -> new HashMap<String, SimpleDateFormat>());

	public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";
	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_FULL_NO_UNDERLINE = "yyyyMMddHHmmss";

	private static final Integer MILLI_SECOND = 1000;

	/**
	 * 转换日期格式输出
	 * @param format
	 * @param date
	 * @return
	 */
	public static String dateFormat(String format, Date date) {
		LocalDateTime localDateTime = convertLocalDate(date);
		return  localDateTime.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Date 转为 LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime convertLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return  LocalDateTime.ofInstant(instant, zone);
	}

	/**
	 * 转为LocalDate类型
	 * @param format 日期格式
	 * @param date 日期
	 * @return
	 */
	public static LocalDate localDate(String format,String date) {
		return LocalDate.parse(date,DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 获取本地日期
	 * @return
	 */
	public static LocalDate getLocalDate() {
		return LocalDate.now();
	}


	/**
	 * 转为LocalDateTime类型
	 * @param format 日期时间格式
	 * @param dateTime 时间
	 * @return
	 */
	public static LocalDateTime localDateTime(String format,String dateTime) {
		return LocalDateTime.parse(dateTime,DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 当前时间文本输出
	 * @param format
	 * @return
	 */
	public static String dateFormat(String format) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}


	/**
	 * 获取本地的时间
	 */
	public static Timestamp getLocalDateTime() {
		return Timestamp.valueOf(dateFormat(DATE_FORMAT_FULL));
	}
    /**
     * 获取延后（秒）的时间
     */
    public static Timestamp getDalyedDateTime(Integer second) {
        return new Timestamp(getLocalDateTime().getTime()+second*MILLI_SECOND);
    }

	/**
	 * 	获取当前时间戳（可以直接用此值放入数据库）
	 */
	public static Timestamp getSqlTimestamp() {
		return Timestamp.from(Instant.now());
	}

	/**
	 * 获取当前/指定月份第一天
	 */
	public static Timestamp currentMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date != null ? date : new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new Timestamp(calendar.getTime().getTime());
	}
	/**
	 * 获取当前月份第一天
	 */
	public static Timestamp currentMonthFirstDay() {
		return currentMonthFirstDay(null);
	}
	/**
	 * 获取当前月份最后一天
	 */
	public static Timestamp currentMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Timestamp(calendar.getTime().getTime());
	}
	/**
	 * 获取指定月份的下一个月的第一天.
	 */
	public static Timestamp nextMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * 转换为时间戳
	 */
	public static Timestamp convertToTimestamp(String dateTime) {
		return Timestamp.valueOf(dateTime);
	}

    /**
     * 获取两个日期之间的所有日期
     * @param startDate 开始
     * @param endDate  截止
     * @return LocalDate集合
     */
	public static List<LocalDate> getAllDatesBetweenTwoDates(LocalDate startDate,LocalDate endDate){
        long numberOfDaysBetween = ChronoUnit.DAYS.between(startDate,endDate);
        return IntStream.iterate(0,i->i+1)
            .limit(numberOfDaysBetween)
            .mapToObj(startDate::plusDays)
            .collect(Collectors.toList());
    }
	
	
	/**
	 * 获取本地的时间
	 */
	public static Timestamp getLocalTimeDate() {
		return Timestamp.valueOf(dateFormat(DATE_FORMAT_SIMPLE));
	}

	
	/**
	 * 获取本地的时间
	 */
	public static Timestamp getLocalTimeDateBySelf(String format) {
		return Timestamp.valueOf(dateFormat(format));
	}
	
	/**
	 * 时间戳是否为今日
	 * @param timestamp
	 * @return
	 */
	public static Boolean TimestampIsNowDay(Timestamp timestamp) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT_SIMPLE);
		LocalDateTime localDateTime=timestamp.toLocalDateTime();
		String formate1 = df.format(localDateTime);
		LocalDateTime localDateTime2=LocalDateTime.now();
		String formate2 = df.format(localDateTime2);
		return formate1.equals(formate2);
	}
	
}
