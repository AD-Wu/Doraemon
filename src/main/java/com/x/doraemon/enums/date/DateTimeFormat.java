package com.x.doraemon.enums.date;

import java.util.Locale;

/**
 * @Desc 日期时间样式枚举
 * @Date 2020/8/15 00:41
 * @Author AD
 */
public final class DateTimeFormat extends BaseFormat {
    
    /**
     * 默认样式：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormat DEFAULT = new DateTimeFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormat DATE_TIME = new DateTimeFormat("yyyy-MM-dd HH:mm:ss", Locale.US,
            Locale.CHINA);
    
    /**
     * yyyy-MM-ddTHH:mm:ss.SSS
     */
    public static final DateTimeFormat ISO = new DateTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * Sat Sep 19 23:47:49 CST 2020
     * 星期六 九月 19 23:46:51 CST 2020
     */
    public static final DateTimeFormat DATE_STRING = new DateTimeFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US, Locale.CHINA);
    
    /**
     * yyyyMMddHHmmssSSS
     */
    public static final DateTimeFormat DATE_TIME_NO_MARK = new DateTimeFormat("yyyyMMddHHmmssSSS", Locale.US, Locale.CHINA);
    
    /**
     * yyyyMMddHHmmss
     */
    public static final DateTimeFormat DATE_TIME_NO_MARK_MILL_SECONDS = new DateTimeFormat("yyyyMMddHHmmss", Locale.US,
            Locale.CHINA);
    
    /**
     * yyyy/MM/dd HH:mm:ss.SSS
     */
    public static final DateTimeFormat DATE_TIME_SLASH = new DateTimeFormat("yyyy/MM/dd HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final DateTimeFormat DATE_TIME_SLASH_NO_MILL_SECONDS = new DateTimeFormat("yyyy/MM/dd HH:mm:ss",
            Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日 HH时mm分ss秒SSS毫秒
     */
    public static final DateTimeFormat DATE_TIME_CHINESE = new DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒", Locale.US,
            Locale.CHINA);
    
    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final DateTimeFormat DATE_TIME_CHINESE_NO_MILL_SECONDS = new DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒",
            Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日HH时mm分ss秒SSS毫秒
     */
    public static final DateTimeFormat DATE_TIME_CHINESE_NO_SPACE = new DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒",
            Locale.US, Locale.CHINA);
    
    /**
     * yyyy年MM月dd日HH时mm分ss秒
     */
    public static final DateTimeFormat DATE_TIME_CHINESE_NO_SPACE_MILL_SECONDS = new DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒",
            Locale.US, Locale.CHINA);
    
    /**
     * "yyyy.MM.dd G 'at' HH:mm:ss z"
     * "yyyy.MM.dd 公元 'at' HH:mm:ss z"
     */
    public static final DateTimeFormat A = new DateTimeFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.US, Locale.CHINA);
    
    /**
     * 02001.July.04 AD 12:08 PM
     */
    public static final DateTimeFormat B = new DateTimeFormat("yyyyy.MMMMM.dd GGG hh:mm aaa", Locale.US, Locale.CHINA);
    
    /**
     * Wed, 4 Jul 2001 12:08:56 -0700
     * 星期六, 19 九月 2020 23:46:51 +0800
     */
    public static final DateTimeFormat C = new DateTimeFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US, Locale.CHINA);
    
    /**
     * 20200919234651+0800
     */
    public static final DateTimeFormat D = new DateTimeFormat("yyyyMMddHHmmssZ", Locale.US, Locale.CHINA);
    
    /**
     * 2001-07-04T12:08:56.235+0800
     */
    public static final DateTimeFormat E = new DateTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US, Locale.CHINA);
    
    public DateTimeFormat(String pattern, Locale... locales) {
        super(pattern, locales);
    }
    
}
