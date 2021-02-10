package com.x.doraemon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Desc
 * @Date 2021/2/7 19:40
 * @Author AD
 */
class SystemHelperTest {
    
    @Test
    void getSystem() {
    
        String system = SystemHelper.getSystem();
        System.out.println(system);
    }
    
    @Test
    void getLangKey() {
        String langKey = SystemHelper.getLangKey();
        System.out.println(langKey);
    }
    
}