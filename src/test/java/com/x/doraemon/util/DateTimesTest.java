package com.x.doraemon.util;

import com.x.doraemon.enums.date.BaseFormat;
import com.x.doraemon.enums.date.DateFormat;
import com.x.doraemon.enums.date.DateTimeFormat;
import com.x.doraemon.enums.date.TimeFormat;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @Desc
 * @Date 2020/11/20 22:56
 * @Author AD
 */
class DateTimesTest {
    
    @Test
    public void test() throws Exception {
        format(DateTimeFormat.class);
        System.out.println("============");
        format(DateFormat.class);
        System.out.println("============");
        format(TimeFormat.class);
    }
    
    private void format(Class<? extends BaseFormat> clazz) throws Exception {
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            BaseFormat o = (BaseFormat) field.get(null);
            String format = DateHelper.format(LocalDateTime.now(), o);
            System.out.println(format + "   " + DateHelper.toLocalDateTime(format));
        }
    }
    
}