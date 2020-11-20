package com.x.doraemon.interfaces;

import java.util.Locale;

/**
 * @Desc 日期、时间样式接口
 * @Date 2020/9/19 18:32
 * @Author AD
 */
public interface IDateTimePattern {
    
    String getPattern();
    
    Locale getLocale();
    
}
