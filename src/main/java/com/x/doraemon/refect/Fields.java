package com.x.doraemon.refect;

import com.x.doraemon.util.ArrayHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Desc 成员属性工具类
 * @Date 2020/12/5 12:15
 * @Author AD
 */
public class Fields {
    
    private Fields() {}
    
    /**
     * 获取类所有的Field，包括父类
     *
     * @param clazz
     *
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        return getFields(clazz, new ArrayList<>());
    }
    
    private static List<Field> getFields(Class<?> clazz, List<Field> fieldContainer) {
        if (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            fieldContainer.addAll(Arrays.asList(fields));
            getFields(clazz.getSuperclass(), fieldContainer);
        }
        return fieldContainer;
    }
    
    public static void main(String[] args) {
        List<Field> fields = getAllFields(ArrayHelper.class);
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}

