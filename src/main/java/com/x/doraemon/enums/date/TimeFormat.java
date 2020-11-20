package com.x.doraemon.enums.date;

import java.util.Locale;

/**
 * @Desc 时间样式枚举
 * @Date 2020/11/20 19:56
 * @Author AD
 */
public final class TimeFormat extends BaseFormat {
    
    /**
     * HH:mm:ss.SSS
     */
    public static final TimeFormat DEFAULT = new TimeFormat("HH:mm:ss.SSS", Locale.US, Locale.CHINA);
    
    /**
     * HH:mm:ss
     */
    public static final TimeFormat TIME_NO_MILL_SECONDS = new TimeFormat("HH:mm:ss", Locale.US, Locale.CHINA);
    
    // /**
    //  * HHmmssSSS(不使用这个样式，格式化会和年月日混淆)
    //  */
    // public static final TimeFormat TIME_NO_MARK = new TimeFormat("HHmmssSSS", Locale.US, Locale.CHINA);
    
    /**
     * HHmmss
     */
    public static final TimeFormat TIME_NO_MARK_MILL_SECONDS = new TimeFormat("HHmmss", Locale.US, Locale.CHINA);
    
    /**
     * HH时mm分ss秒SSS毫秒
     */
    public static final TimeFormat TIME_CHINESE = new TimeFormat("HH时mm分ss秒SSS毫秒", Locale.US, Locale.CHINA);
    
    /**
     * HH时mm分ss秒
     */
    public static final TimeFormat TIME_CHINESE_NO_MILL_SECONDS = new TimeFormat("HH时mm分ss秒", Locale.US, Locale.CHINA);
    
    /**
     * 12:08 PM
     * 12:08 下午
     */
    public static final TimeFormat A = new TimeFormat("h:mm a", Locale.US, Locale.CHINA);
    
    /**
     * 12 o'clock PM, Pacific Daylight Time
     * 12 o'clock 下午, 中国标准时间
     */
    public static final TimeFormat CLOCK = new TimeFormat("hh 'o''clock' a, zzzz", Locale.US, Locale.CHINA);
    
    /**
     * 0:08 PM, PDT
     */
    public static final TimeFormat ZONE = new TimeFormat("K:mm a, z", Locale.US, Locale.CHINA);
    
    public TimeFormat(String pattern, Locale... locales) {
        super(pattern, locales);
    }
    
}