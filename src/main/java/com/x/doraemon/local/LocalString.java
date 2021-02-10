package com.x.doraemon.local;

import com.x.doraemon.util.FileHelper;
import com.x.doraemon.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Desc 语言信息
 * @Date 2021/2/7 18:16
 * @Author AD
 */
public class LocalString {
    
    private Properties prop;
    
    /**
     * 加载语言配置文件信息
     *
     * @param path 配置文件路径，如：com/x/commons/local/zh_CN.properties
     *
     * @return true:加载成功 false:加载失败
     */
    public boolean load(String path) {
        // 判断路径有效性
        if (Strings.isNull(path)) {
            return false;
        }
        // 获取类加载起
        ClassLoader loader = this.getClass().getClassLoader();
        // 获取文件输入流
        try (InputStream in = loader.getResourceAsStream(path)) {
            // 判断输入流是否有效
            if (in == null) {
                return false;
            }
            // 获取统一编码读取器
            try (InputStreamReader reader = FileHelper.getUnicodeReader(in)) {
                // 加载配置文件信息
                this.prop.load(reader);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取文本信息
     *
     * @param key    xxx.properties里的key
     * @param params key所对应内容所需要填充的参数
     *
     * @return
     */
    public String text(String key, Object... params) {
        return Strings.replace(this.prop.getProperty(key), params);
    }
    
}
