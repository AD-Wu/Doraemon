package com.x.doraemon.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Desc
 * @Date 2021/2/1 15:04
 * @Author AD
 */
class SBTest {

    @Test
    void deleteLast() {
        String s = "1,2,3,,,";
        SB sb = New.sb(s);
        sb = sb.deleteLast(3);
        String fix = sb.toString();
        System.out.println(fix);
    }

}