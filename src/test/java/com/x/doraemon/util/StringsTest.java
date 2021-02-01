package com.x.doraemon.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Desc
 * @Date 2021/2/1 14:50
 * @Author AD
 */
class StringsTest {

    private static String s;

    @BeforeAll
    static void before() {
        s = "   呵呵  哈哈  嘻嘻         嘎嘎        ";
    }

    @Test
    void removeSpaces() {
        String fix = Strings.removeSpaces(s);
        System.out.println(s);
        System.out.println(fix);
        System.out.println(s.trim());
    }

}