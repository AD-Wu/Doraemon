package com.x.doraemon.util;

import com.google.gson.*;
import com.x.doraemon.enums.DisplayStyle;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Desc Json工具类
 * @Date 2020/8/14 23:59
 * @Author AD
 */
public final class Jsons {
    
    // ------------------------ 变量定义 ------------------------
    
    // 序列化
    private static final JsonSerializer<LocalDateTime> serializerLocalDateTime = (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    private static final JsonSerializer<LocalDate> serializerLocalDate = (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    private static final JsonSerializer<LocalDate> serializerLocalTime = (localTime, type, jsonSerializationContext) -> new JsonPrimitive(localTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
    // 反序列化
    private static final JsonDeserializer<LocalDateTime> deserializerLocalDateTime = (jsonElement, type, jsonDeserializationContext) -> LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    private static final JsonDeserializer<LocalDate> deserializerLocalDate = (jsonElement, type, jsonDeserializationContext) -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
    private static final JsonDeserializer<LocalDate> deserializerLocalTime = (jsonElement, type, jsonDeserializationContext) -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_TIME);
    // ------------------------ 构造方法 ------------------------
    private Jsons() {}
    // ------------------------ 方法定义 ------------------------
    
   
    
    /**
     * 将对象解析成JSON字符串
     *
     * @param src
     *
     * @return
     */
    public static String toJson(Object src) {
        return toJson(src, "yyyy-MM-dd HH:mm:ss.SSS");
    }
    
    /**
     * 将对象解析成JSON字符串
     *
     * @param src             解析对象
     * @param dateTimePattern 日期格式，如：yyyy-MM-dd HH:mm:ss，只支持Date，不支持LocalDateTime
     *
     * @return
     */
    public static String toJson(Object src, String dateTimePattern) {
        return gson(dateTimePattern).toJson(src);
    }
    
    /**
     * 将JSON字符串解析成对应的对象
     *
     * @param json
     * @param clazz
     *
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json, clazz, "yyyy-MM-dd HH:mm:ss.SSS");
    }
    
    /**
     * 将JSON字符串解析成对应的对象
     *
     * @param json
     * @param clazz
     * @param dateTimePattern
     *
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz, String dateTimePattern) {
        return gson(dateTimePattern).fromJson(json, clazz);
    }
    
    /**
     * 判断是否是有效的json字符串
     *
     * @param check
     *
     * @return
     */
    public static boolean isValid(String check) {
        try {
            fromJson(check, Object.class, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------ 私有方法 ------------------------
    public static Gson gson(String dateTimePattern) {
        return new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat(dateTimePattern)
                .registerTypeAdapter(LocalDateTime.class,serializerLocalDateTime)
                .registerTypeAdapter(LocalDate.class,serializerLocalDate)
                .registerTypeAdapter(LocalTime.class,serializerLocalTime)
                .registerTypeAdapter(LocalDateTime.class,deserializerLocalDateTime)
                .registerTypeAdapter(LocalDate.class,deserializerLocalDate)
                .registerTypeAdapter(LocalTime.class,deserializerLocalTime)
                .create();
    }
    
   
    
    private static class A {
        
        private String name;
        
        private int age;
        
        private Date birthday;
        private LocalDateTime localBirthday;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public int getAge() {
            return age;
        }
    
        public void setAge(int age) {
            this.age = age;
        }
    
        public Date getBirthday() {
            return birthday;
        }
    
        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    
       
        public LocalDateTime getLocalBirthday() {
            return this.localBirthday;
        }
        
        public void setLocalBirthday(LocalDateTime localBirthday) {
            this.localBirthday = localBirthday;
        }
    
        @Override
        public String toString() {
            return Strings.toJsonString(this);
        }
    
    }
   
    public static void main(String[] args) {
        
        A a = new A();
        a.setName("ad");
        a.setAge(1);
        a.setLocalBirthday(LocalDateTime.now());
        a.setBirthday(new Date());
        String s = toJson(a);
        System.out.println(s);
        A re = fromJson(s, A.class, "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(re);
    }
    
}
