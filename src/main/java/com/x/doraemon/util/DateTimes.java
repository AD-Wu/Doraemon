package com.x.doraemon.util;

import com.x.doraemon.bean.New;
import com.x.doraemon.enums.DateTimePattern;
import com.x.doraemon.interfaces.IDateTimePattern;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * @Desc 日期时间工具类
 * @Date 2020/9/17 21:08
 * @Author AD
 */
public final class DateTimes {
    
    // ------------------------ 变量定义 ------------------------
    
    private static final List<DateTimePattern> patterns = New.list();
    
    private static final List<DateTimeFormatter> formatters = New.list();
    
    private static final int pattersCount;
    
    private static final int formatterCount;
    
    // ------------------------ 构造方法 ------------------------
    private DateTimes() {}
    // ------------------------ 方法定义 ------------------------
    
    public static LocalDateTime toLocalDateTime(Object dateTime) throws Exception {
        LocalDateTime local = toLocalDateTime(dateTime, null);
        if (local == null) {
            String patter = "Can not parse the dateTime={0},implements {1} and add the annotation:@AutoService({2})";
            String name = IDateTimePattern.class.getName();
            String msg = Strings.replace(patter, dateTime, name, name);
            throw new RuntimeException(msg);
        }
        return local;
    }
    
    public static LocalDateTime toLocalDateTime(Object dateTime, LocalDateTime defaultValue) throws Exception {
        if (dateTime == null) {
            return defaultValue;
        } else if (dateTime instanceof LocalDateTime) {
            return (LocalDateTime) dateTime;
        } else if (dateTime instanceof Date) {
            return dateToLocalDateTime((Date) dateTime);
        } else if (!(dateTime instanceof Long) && !(dateTime instanceof Integer)) {
            LocalDateTime localDateTime = parseStringToLocalDateTime(dateTime.toString(), 0);
            if (localDateTime == null) {
                Date date = parseStringToDate(dateTime.toString(), 0);
                if (date == null) {
                    return defaultValue;
                }
                return dateToLocalDateTime(date);
            }
            return localDateTime;
        }
        return dateToLocalDateTime(new Date((Long) dateTime));
    }
    
    public static Date toDate(Object date) throws Exception {
        return toDate(date, null);
    }
    
    public static Date toDate(Object date, Date defaultDate) throws Exception {
        if (defaultDate == null) {
            return localDateTimeToDate(toLocalDateTime(date));
        }
        LocalDateTime defaultValue = fixLocalDateTime(defaultDate, LocalDateTime.now());
        return localDateTimeToDate(toLocalDateTime(date, defaultValue));
    }
    
    public static String format(Object dateTime) throws Exception {
        return format(dateTime, DateTimePattern.DATE_TIME.getPattern());
    }
    
    public static String format(Object dateTime, DateTimePattern pattern) throws Exception {
        return format(dateTime, pattern.getPattern());
    }
    
    public static String format(Object dateTime, String pattern) throws Exception {
        Date date = toDate(dateTime);
        try {
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
     * @param year
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
    private static Date parseStringToDate(String dateTime, int index) {
        if (index < pattersCount) {
            DateTimePattern pattern = patterns.get(index);
            Locale[] locales = pattern.getLocales();
            Date parse = null;
            for (Locale locale : locales) {
                try {
                    parse = new SimpleDateFormat(pattern.getPattern(), locale).parse(dateTime,
                            new ParsePosition(0));
                    if (parse == null) {
                        continue;
                    }
                    return parse;
                } catch (Exception e) {
                    continue;
                }
            }
            if (parse == null) {
                return parseStringToDate(dateTime, ++index);
            }
        }
        return null;
    }
    
    private static LocalDateTime parseStringToLocalDateTime(String dateTime, int index) {
        if (index < formatterCount) {
            DateTimeFormatter formatter = formatters.get(index);
            try {
                return LocalDateTime.from(formatter.parse(dateTime));
            } catch (Exception e) {
                return parseStringToLocalDateTime(dateTime, ++index);
            }
        }
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
        // LocalDateTime使用正数，如：1100=1100;Date：1100=1100-1900
        int localYear = localDateTime.getYear();
        int year = localYear - 1900;
        date.setYear(year);
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
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime before = date.toInstant().atZone(zoneId).toLocalDateTime();
        /*
         * 修正,如：
         * date=1700-3-2 1:2:3.234 => localDateTime=1700-03-02T01:07:46
         * date=1100-3-2 1:2:3.234 => localDateTime=1100-03-09T01:07:46.234
         */
        return fixLocalDateTime(date, before);
    }
    
    /**
     * 修正时间转换误差,如：
     * date=1700-3-2 1:2:3.234 => localDateTime=1700-03-02T01:07:46
     * date=1100-3-2 1:2:3.234 => localDateTime=1100-03-09T01:07:46.234
     *
     * @param date  原始值
     * @param local 需修正的值
     *
     * @return
     */
    private static LocalDateTime fixLocalDateTime(Date date, LocalDateTime local) {
        if (local == null) {LocalDateTime.now();}
        // 年
        int dateYear = date.getYear();
        int localYear = local.getYear();
        // 月,Date：0=一月
        int dateMonth = date.getMonth() + 1;
        Month localMonth = local.getMonth();
        // 日
        int dateDay = date.getDate();
        int localDay = local.getDayOfMonth();
        // 时
        int dateHours = date.getHours();
        int localHours = local.getHour();
        // 分
        int dateMinutes = date.getMinutes();
        int localMinutes = local.getMinute();
        // 秒
        int dateSeconds = date.getSeconds();
        int localSeconds = local.getSecond();
        // 不相等才设置，否则会出现莫名其妙的错误
        if (dateYear != localYear) {
            if (dateMonth != localMonth.getValue()) {
                if (dateDay != localDay) {
                    if (dateHours != localHours) {
                        if (dateMinutes != localMinutes) {
                            if (dateSeconds != localSeconds) {
                                LocalDateTime result = local
                                        .withYear(dateYear)
                                        .withMonth(dateMonth)
                                        .withDayOfMonth(dateDay)
                                        .withHour(dateHours)
                                        .withMinute(dateMinutes)
                                        .withSecond(dateSeconds)
                                        .withNano(local.getNano());
                                return result;
                            } else {
                                LocalDateTime result = local
                                        .withNano(local.getNano());
                                return result;
                            }
                        } else {
                            LocalDateTime result = local
                                    .withSecond(dateSeconds)
                                    .withNano(local.getNano());
                            return result;
                        }
                    } else {
                        LocalDateTime result = local
                                .withMinute(dateMinutes)
                                .withSecond(dateSeconds)
                                .withNano(local.getNano());
                        return result;
                    }
                } else {
                    LocalDateTime result = local
                            .withHour(dateHours)
                            .withMinute(dateMinutes)
                            .withSecond(dateSeconds)
                            .withNano(local.getNano());
                    return result;
                }
            } else {
                LocalDateTime result = local
                        .withDayOfMonth(dateDay)
                        .withHour(dateHours)
                        .withMinute(dateMinutes)
                        .withSecond(dateSeconds)
                        .withNano(local.getNano());
                return result;
            }
        } else {
            LocalDateTime result = local
                    .withMonth(dateMonth)
                    .withDayOfMonth(dateDay)
                    .withHour(dateHours)
                    .withMinute(dateMinutes)
                    .withSecond(dateSeconds)
                    .withNano(local.getNano());
            return result;
        }
    }
    
    // ------------------------ 静态内部类 ------------------------
    
    static {
        Field[] patternFields = DateTimePattern.class.getFields();
        for (Field field : patternFields) {
            field.setAccessible(true);
            if (field.getType().equals(DateTimePattern.class)) {
                try {
                    DateTimePattern pattern = (DateTimePattern) field.get(null);
                    patterns.add(pattern);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        ServiceLoader<IDateTimePattern> load = ServiceLoader.load(IDateTimePattern.class);
        Iterator<IDateTimePattern> it = load.iterator();
        while (it.hasNext()) {
            IDateTimePattern next = it.next();
            String pattern = next.getPattern();
            Locale locale = next.getLocale();
            patterns.add(new DateTimePattern(pattern, locale));
        }
        
        Field[] fields = DateTimeFormatter.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (fieldType.equals(DateTimeFormatter.class)) {
                try {
                    DateTimeFormatter formatter = (DateTimeFormatter) field.get(null);
                    formatters.add(formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        pattersCount = patterns.size();
        formatterCount = formatters.size();
    }
    
    
}
