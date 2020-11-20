package com.x.doraemon.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @Desc 日志帮助类
 * @Date 2020/9/24 00:50
 * @Author AD
 */
public final class Logs {
    
    // ------------------------ 变量定义 ------------------------
    
    // ------------------------ 构造方法 ------------------------
    private Logs() {}
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取日志对象
     *
     * @param o null表示获取root日志对象；如果o=this表示获取当前类名的日志记录对象
     *
     * @return
     */
    public static Logger getLogger(Object o) {
        if (o == null) {
            return LogManager.getRootLogger();
        } else {
            if (o instanceof Class) {
                return LogManager.getLogger((Class) o);
            } else if (o instanceof String) {
                return LogManager.getLogger((String) o);
            }
            return LogManager.getLogger(o.getClass().getName());
        }
    }
    
    /**
     * 获取根日志对象
     *
     * @return
     */
    public static Logger getRootLogger() {
        return getLogger(null);
    }
    
}
