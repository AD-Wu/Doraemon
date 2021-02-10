package com.x.doraemon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Desc TODO
 * @Date 2021/2/10 21:29
 * @Author AD
 */
class ZipHelperTest {
    
    @Test
    void zip() throws Exception {
        String source = "/Users/sunday/IdeaProjects/Doraemon/target";
        String target = "/Users/sunday/IdeaProjects/Doraemon/target.zip";
        boolean zip = ZipHelper.zip(source, target);
        System.out.println(zip);
    }
    
}