package com.x.doraemon.enums;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @Desc 日期时间样式枚举
 * @Date 2020/8/15 00:41
 * @Author AD
 */
public final class DateTimePattern {
    
    /**
     * Sat Sep 19 23:47:49 CST 2020
     * 星期六 九月 19 23:46:51 CST 2020
     */
    public static final DateTimePattern DATE_STRING = new DateTimePattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US, Locale.CHINA);
    
    /**
     * yyyy-MM-ddTHH:mm:ss.SSS
     */
    public static final DateTimePattern ISO = new DateTimePattern("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimePattern DATE_TIME = new DateTimePattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimePattern DATE_TIME_NO_MILL_SECONDS = new DateTimePattern("yyyy-MM-dd HH:mm:ss", Locale.US,
            Locale.CHINA);
    
    /**
     * yyyyMMddHHmmssSSS
     */
    public static final DateTimePattern DATE_TIME_NO_MARK = new DateTimePattern("yyyyMMddHHmmssSSS", Locale.US, Locale.CHINA);
    
    /**
     * yyyyMMddHHmmss
     */
    public static final DateTimePattern DATE_TIME_NO_MARK_MILL_SECONDS = new DateTimePattern("yyyyMMddHHmmss", Locale.US,
            Locale.CHINA);
    
    /**
     * yyyy/MM/dd HH:mm:ss.SSS
     */
    public static final DateTimePattern DATE_TIME_SLASH = new DateTimePattern("yyyy/MM/dd HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final DateTimePattern DATE_TIME_SLASH_NO_MILL_SECONDS = new DateTimePattern("yyyy/MM/dd HH:mm:ss",
            Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日 HH时mm分ss秒SSS毫秒
     */
    public static final DateTimePattern DATE_TIME_CHINESE = new DateTimePattern("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒", Locale.US,
            Locale.CHINA);
    
    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final DateTimePattern DATE_TIME_CHINESE_NO_MILL_SECONDS = new DateTimePattern("yyyy年MM月dd日 HH时mm分ss秒",
            Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日HH时mm分ss秒SSS毫秒
     */
    public static final DateTimePattern DATE_TIME_CHINESE_NO_SPACE = new DateTimePattern("yyyy年MM月dd日HH时mm分ss秒SSS毫秒",
            Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日HH时mm分ss秒
     */
    public static final DateTimePattern DATE_TIME_CHINESE_NO_SPACE_MILL_SECONDS = new DateTimePattern("yyyy年MM月dd日HH时mm分ss秒",
            Locale.US, Locale.CHINA);
    
    /**
     * yyyy-MM-dd
     */
    public static final DateTimePattern DATE = new DateTimePattern("yyyy-MM-dd", Locale.US, Locale.CHINA);
    
    /**
     * yyyy/MM/dd
     */
    public static final DateTimePattern DATE_SLASH = new DateTimePattern("yyyy/MM/dd", Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日
     */
    public static final DateTimePattern DATE_CHINESE = new DateTimePattern("yyyy年MM月dd日", Locale.US, Locale.CHINA);
    
    /**
     * yyyyMMdd
     */
    public static final DateTimePattern DATE_NO_MARK = new DateTimePattern("yyyyMMdd", Locale.US, Locale.CHINA);
    
    /**
     * HH:mm:ss.SSS
     */
    public static final DateTimePattern TIME = new DateTimePattern("HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * HH:mm:ss
     */
    public static final DateTimePattern TIME_NO_MILL_SECONDS = new DateTimePattern("HH:mm:ss", Locale.US, Locale.CHINA);
    
    /**
     * HHmmssSSS
     */
    public static final DateTimePattern TIME_NO_MARK = new DateTimePattern("HHmmssSSS", Locale.US, Locale.CHINA);
    
    /**
     * HHmmss
     */
    public static final DateTimePattern TIME_NO_MARK_MILL_SECONDS = new DateTimePattern("HHmmss", Locale.US, Locale.CHINA);
    
    /**
     * HH时mm分ss秒SSS毫秒
     */
    public static final DateTimePattern TIME_CHINESE = new DateTimePattern("HH时mm分ss秒SSS毫秒", Locale.US, Locale.CHINA);
    
    /**
     * HH时mm分ss秒
     */
    public static final DateTimePattern TIME_CHINESE_NO_MILL_SECONDS = new DateTimePattern("HH时mm分ss秒", Locale.US, Locale.CHINA);
    
    /**
     * "yyyy.MM.dd G 'at' HH:mm:ss z"
     * "yyyy.MM.dd 公元 'at' HH:mm:ss z"
     */
    public static final DateTimePattern A = new DateTimePattern("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.US, Locale.CHINA);
    
    /**
     * Wed, Jul 4, '01
     * 星期六, 九月 19, '20
     */
    public static final DateTimePattern B = new DateTimePattern("EEE, MMM d, ''yy", Locale.US, Locale.CHINA);
    
    /**
     * 12:08 PM
     * 12:08 下午
     */
    public static final DateTimePattern C = new DateTimePattern("h:mm a", Locale.US, Locale.CHINA);
    
    /**
     * 12 o'clock PM, Pacific Daylight Time
     * 12 o'clock 下午, 中国标准时间
     */
    public static final DateTimePattern D = new DateTimePattern("hh 'o''clock' a, zzzz", Locale.US, Locale.CHINA);
    
    /**
     * 0:08 PM, PDT
     */
    public static final DateTimePattern E = new DateTimePattern("K:mm a, z", Locale.US, Locale.CHINA);
    
    /**
     * 02001.July.04 AD 12:08 PM
     */
    public static final DateTimePattern F = new DateTimePattern("yyyyy.MMMMM.dd GGG hh:mm aaa", Locale.US, Locale.CHINA);
    
    /**
     * Wed, 4 Jul 2001 12:08:56 -0700
     * 星期六, 19 九月 2020 23:46:51 +0800
     */
    public static final DateTimePattern G = new DateTimePattern("EEE, d MMM yyyy HH:mm:ss Z", Locale.US, Locale.CHINA);
    
    /**
     * 20200919234651+0800
     */
    public static final DateTimePattern H = new DateTimePattern("yyyyMMddHHmmssZ", Locale.US, Locale.CHINA);
    
    /**
     * 2001-07-04T12:08:56.235+0800
     */
    public static final DateTimePattern I = new DateTimePattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US, Locale.CHINA);
    
    private final String pattern;
    
    private final Locale[] locales;
    
    public DateTimePattern(String pattern, Locale... locales) {
        this.pattern = pattern;
        this.locales = locales;
    }
    
    public String getPattern() {
        return this.pattern;
    }
    
    public Locale[] getLocales() {
        return this.locales;
    }
    
}
