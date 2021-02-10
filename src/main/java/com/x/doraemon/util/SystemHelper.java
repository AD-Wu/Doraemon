package com.x.doraemon.util;

import java.util.Locale;

/**
 * @Desc
 * @Date 2021/2/7 19:36
 * @Author AD
 */
public class SystemHelper {
    
    private SystemHelper() {
    }
    
    /**
     * 获取操作系统
     *
     * @return
     */
    public static String getSystem() {
        return System.getProperty("os.name");
    }
    
    /**
     * 获取语言和城市key
     *
     * @return 如：zh_CN，en_US
     */
    public static String getLangKey() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        String language = locale.getLanguage();
        return language + "_" + country;
    }
    
}
