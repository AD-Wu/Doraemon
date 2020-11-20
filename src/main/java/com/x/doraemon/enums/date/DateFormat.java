package com.x.doraemon.enums.date;

import java.util.Locale;

/**
 * @Desc 日期样式枚举
 * @Date 2020/11/20 19:56
 * @Author AD
 */
public final class DateFormat extends BaseFormat {
    
    /**
     * yyyy-MM-dd
     */
    public static final DateFormat DEFAULT = new DateFormat("yyyy-MM-dd", Locale.US, Locale.CHINA);
    
    /**
     * yyyy/MM/dd
     */
    public static final DateFormat DATE_SLASH = new DateFormat("yyyy/MM/dd", Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日
     */
    public static final DateFormat DATE_CHINESE = new DateFormat("yyyy年MM月dd日", Locale.US, Locale.CHINA);
    
    /**
     * yyyyMMdd
     */
    public static final DateFormat DATE_NO_MARK = new DateFormat("yyyyMMdd", Locale.US, Locale.CHINA);
    
    /**
     * Wed, Jul 4, '01
     * 星期六, 九月 19, '20
     */
    public static final DateFormat WEEK = new DateFormat("EEE, MMM d, ''yy", Locale.US, Locale.CHINA);
    
    public DateFormat(String pattern, Locale... locales) {
        super(pattern, locales);
    }
    
    // private final String pattern;
    //
    // private final Locale[] locales;
    //
    // public DatePattern(String pattern, Locale... locales) {
    //     this.pattern = pattern;
    //     this.locales = locales;
    // }
    //
    // public String getPattern() {
    //     return this.pattern;
    // }
    //
    // public Locale[] getLocales() {
    //     return this.locales;
    // }
    
}
