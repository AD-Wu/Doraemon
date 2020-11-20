package com.x.doraemon.enums.date;

import java.util.Locale;

/**
 * @Desc
 * @Date 2020/11/20 22:41
 * @Author AD
 */
public class BaseFormat {
    
    private final String format;
    
    private final Locale[] locales;
    
    public BaseFormat(String format, Locale... locales) {
        this.format = format;
        this.locales = locales;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public Locale[] getLocales() {
        return this.locales;
    }
    
}
