package com.x.doraemon.util;

import com.x.doraemon.bean.New;
import com.x.doraemon.enums.date.BaseFormat;
import com.x.doraemon.enums.date.DateFormat;
import com.x.doraemon.enums.date.DateTimeFormat;
import com.x.doraemon.enums.date.TimeFormat;
import com.x.doraemon.interfaces.IDateTimePattern;

import java.lang.reflect.Field;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Desc 日期时间工具类
 * @Date 2020/9/17 21:08
 * @Author AD
 */
public final class DateTimes {
    
    // ------------------------ 变量定义 ------------------------
    
    private static final BaseFormat[] FORMATS;
    
    private static final DateTimeFormat[] DATE_TIME_FORMATS;
    
    private static final DateFormat[] DATE_FORMATS;
    
    private static final TimeFormat[] TIME_FORMATS;
    
    private static final DateTimeFormatter[] DATE_TIME_FORMATTERS;
    
    // ------------------------ 构造方法 ------------------------
    private DateTimes() {}
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 自动将对象转为日期时间类（包括日期和时间，在DateTimeFormat里找对应的样式）
     *
     * @param dateTime 字符串、Date、LocalDateTime、int、long对象
     *
     * @return LocalDateTime对象
     *
     * @throws Exception 没有样式异常
     */
    public static LocalDateTime toLocalDateTime(Object dateTime) throws Exception {
        // 将对象转换为LocalDateTime对象
        LocalDateTime local = toLocalDateTime(dateTime, null);
        // 判断有效性
        if (local == null) {
            String patter = "Can not parse the dateTime={0},implements {1} and add the annotation:@AutoService({2})";
            String name = IDateTimePattern.class.getName();
            String msg = Strings.replace(patter, dateTime, name, name);
            throw new RuntimeException(msg);
        }
        // 返回结果
        return local;
    }
    
    /**
     * 自动将对象转为日期时间类（包括日期和时间，在DateTimePattern里找对应的样式），转换异常时返回默认值
     *
     * @param dateTime     字符串、Date、LocalDateTime、int、long对象
     * @param defaultValue 默认值
     *
     * @return LocalDateTime对象
     *
     * @throws Exception
     */
    public static LocalDateTime toLocalDateTime(Object dateTime, LocalDateTime defaultValue) throws Exception {
        // 判断有效性
        if (dateTime == null) {
            return defaultValue;
        }
        // 如果是LocalDateTime对象，直接返回
        else if (dateTime instanceof LocalDateTime) {
            return (LocalDateTime) dateTime;
        }
        // 如果是Date对象，进行转换
        else if (dateTime instanceof Date) {
            return dateToLocalDateTime((Date) dateTime);
        }
        // 不是数int或long，则转成String对象
        else if (!(dateTime instanceof Long) && !(dateTime instanceof Integer)) {
            // 将字符串解析为LocalDateTime对象
            LocalDateTime localDateTime = parseStringToLocalDateTime(dateTime.toString(), 0);
            // 判断解析结果是否有效
            if (localDateTime == null) {
                // 将字符串解析为Date对象
                Date date = parseStringToDate(dateTime.toString(), 0);
                // 无效返回默认值
                if (date == null) {
                    return defaultValue;
                }
                // 有效则将Date转为LocalDateTime对象
                return dateToLocalDateTime(date);
            }
            return localDateTime;
        }
        // 是int或long类型，则先转为Date，再转为LocalDateTime对象
        return dateToLocalDateTime(new Date((Long) dateTime));
    }
    
    /**
     * 自动将对象转为Date，不推荐使用Date，推荐使用LocalDateTime
     *
     * @param date 对象，如：String、int、long、Date、LocalDateTime
     *
     * @return Date 日期对象
     *
     * @throws Exception
     */
    public static Date toDate(Object date) throws Exception {
        return toDate(date, null);
    }
    
    /**
     * 自动将对象转为Date，不推荐使用Date，推荐使用LocalDateTime
     *
     * @param date        对象，如：String、int、long、Date、LocalDateTime
     * @param defaultDate 默认值
     *
     * @return Date 日期对象
     *
     * @throws Exception
     */
    public static Date toDate(Object date, Date defaultDate) throws Exception {
        LocalDateTime defaultValue = null;
        // 判断默认值是否有效
        if (defaultDate != null) {
            // 将默认值Date转为LocalDateTime对象（同时进行修正）
            defaultValue = dateToLocalDateTime(defaultDate);
        }
        // 转换：Object->LocalDateTime->Date
        return localDateTimeToDate(toLocalDateTime(date, defaultValue));
    }
    
    /**
     * 日期格式化，结果样式：yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param dateTime 格式化对象
     *
     * @return
     *
     * @throws Exception
     */
    public static String format(Object dateTime) throws Exception {
        return format(dateTime, DateTimeFormat.DEFAULT.getFormat());
    }
    
    /**
     * 日期格式化
     *
     * @param dateTime 格式化对象
     * @param format   格式化样式对象
     *
     * @return
     *
     * @throws Exception
     */
    public static String format(Object dateTime, BaseFormat format) throws Exception {
        return format(dateTime, format.getFormat());
    }
    
    /**
     * 日期格式化
     *
     * @param dateTime 格式化对象
     * @param pattern  格式化样式
     *
     * @return
     *
     * @throws Exception
     */
    public static String format(Object dateTime, String pattern) throws Exception {
        // 将对象转为Date对象
        Date date = toDate(dateTime);
        try {
            // 进行格式化（自定义样式的建议使用SimpleDateFormat）
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            LocalDateTime localDateTime = toLocalDateTime(dateTime);
            return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
        }
    }
    
    /**
     * 是否闰年
     *
     * @param year 年份
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 != 0) {
            if (year % 4 == 0) {
                return true;
            }
        } else if (year % 400 == 0) {
            return true;
        }
        return false;
    }
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 将字符串解析城Date对象
     *
     * @param dateTime
     * @param index
     *
     * @return
     */
    private static Date parseStringToDate(String dateTime, int index) {
        // 判断是否还存在DateTime的样式
        if (index < FORMATS.length) {
            // 获取当前样式
            BaseFormat format = FORMATS[index];
            // 获取所有的语言
            Locale[] locales = format.getLocales();
            Date parse = null;
            for (Locale locale : locales) {
                try {
                    // 解析结果
                    parse = new SimpleDateFormat(format.getFormat(), locale).parse(dateTime,
                            new ParsePosition(0));
                    // 判断有效性
                    if (parse == null) {
                        continue;
                    }
                    return parse;
                } catch (Exception e) {
                    continue;
                }
            }
            // 当前样式无法解析，则递归解析
            if (parse == null) {
                return parseStringToDate(dateTime, ++index);
            }
        }
        return null;
    }
    
    /**
     * 将字符串解析城LocalDateTime对象
     *
     * @param dateTime
     * @param index
     *
     * @return
     */
    private static LocalDateTime parseStringToLocalDateTime(String dateTime, int index) {
        // 判断是否还有日期时间格式化对象
        if (index < DATE_TIME_FORMATTERS.length) {
            try {
                // 解析
                return LocalDateTime.from(DATE_TIME_FORMATTERS[index].parse(dateTime));
            } catch (Exception e) {
                // 解析异常则递归解析
                return parseStringToLocalDateTime(dateTime, ++index);
            }
        }
        // 无法解析返回空
        return null;
    }
    
    /**
     * 将LocalDateTime类转换成类Date，不推荐使用Date
     *
     * @param localDateTime
     *
     * @return
     */
    @SuppressWarnings("all")
    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Date date = new Date();
        /*
            - LocalDateTime使用正数，如：1100=1100
            - Date使用1900做为基准，1910:1910-1900=10，1100=1100-1900=-800
         */
        date.setYear(localDateTime.getYear() - 1900);
        date.setMonth(localDateTime.getMonthValue() - 1);
        date.setDate(localDateTime.getDayOfMonth());
        date.setHours(localDateTime.getHour());
        date.setMinutes(localDateTime.getMinute());
        date.setSeconds(localDateTime.getSecond());
        return date;
        /**
         * 不使用这种方式，会有错误，如：
         * LocalDateTime = 1100-03-02T01:02:03.234 =>
         * Date = Fri Feb 24 00:56:20 CST 1100
         */
        // String zoneID = TimeZone.getDefault().getID();// 时区偏移
        // ZonedDateTime toLocalDataTime = ZonedDateTime.toLocalDataTime(localDateTime, ZoneId.toLocalDataTime(zoneID));
        // Instant instant = toLocalDataTime.toInstant();
        // Date from = Date.from(instant);
        // return from;
    }
    
    private static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        // 获取当前时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 先转成LocalDateTime对象
        LocalDateTime local = date.toInstant().atZone(zoneId).toLocalDateTime();
        /*
         * 进行修正,不然会有时间错误。如：
         * date=1700-3-2 1:2:3.234 => localDateTime=1700-03-02T01:07:46
         * date=1100-3-2 1:2:3.234 => localDateTime=1100-03-09T01:07:46.234
         */
        if (date.getYear() != local.getYear()) {
            // Date的年是以1900为基准的，如：1910年为10，1700年为-200
            local = local.withYear(date.getYear() + 1900);
        }
        if (date.getMonth() + 1 != local.getMonthValue()) {
            local = local.withMonth(date.getMonth() + 1);
        }
        if (date.getDate() != local.getDayOfMonth()) {
            local = local.withDayOfMonth(date.getDate());
        }
        if (date.getHours() != local.getHour()) {
            local = local.withHour(date.getHours());
        }
        if (date.getMinutes() != local.getMinute()) {
            local = local.withMinute(date.getMinutes());
        }
        if (date.getSeconds() != local.getSecond()) {
            local = local.withSecond(date.getSeconds());
        }
        return local;
    }
    
    // ------------------------ 静态内部类 ------------------------
    
    static {
        List<BaseFormat> formats = New.list();
        // 初始化日期时间样式
        List<DateTimeFormat> dateTimes = New.list();
        for (Field field : DateTimeFormat.class.getFields()) {
            dateTimes.add(getFieldValue(field, DateTimeFormat.class));
        }
        
        Iterator<IDateTimePattern> it = ServiceLoader.load(IDateTimePattern.class).iterator();
        while (it.hasNext()) {
            IDateTimePattern next = it.next();
            String pattern = next.getPattern();
            Locale locale = next.getLocale();
            dateTimes.add(new DateTimeFormat(pattern, locale));
        }
        DATE_TIME_FORMATS = dateTimes.toArray(new DateTimeFormat[0]);
        
        // 初始化日期样式
        List<DateFormat> dates = New.list();
        for (Field field : DateFormat.class.getFields()) {
            dates.add(getFieldValue(field, DateFormat.class));
        }
        DATE_FORMATS = dates.toArray(new DateFormat[0]);
        
        // 初始化时间样式
        List<TimeFormat> times = New.list();
        for (Field field : TimeFormat.class.getFields()) {
            times.add(getFieldValue(field, TimeFormat.class));
        }
        TIME_FORMATS = times.toArray(new TimeFormat[0]);
        
        formats.addAll(dateTimes);
        formats.addAll(dates);
        formats.addAll(times);
        FORMATS = formats.toArray(new BaseFormat[0]);
        
        // 初始化格式化对象
        List<DateTimeFormatter> formatters = New.list();
        for (Field field : DateTimeFormatter.class.getDeclaredFields()) {
            formatters.add(getFieldValue(field, DateTimeFormatter.class));
        }
        DATE_TIME_FORMATTERS = formatters.toArray(new DateTimeFormatter[0]);
    }
    
    private static <T> T getFieldValue(Field field, Class<T> clazz) {
        if (field != null) {
            field.setAccessible(true);
            if (field.getType().equals(clazz)) {
                try {
                    return (T) field.get(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
    
}
