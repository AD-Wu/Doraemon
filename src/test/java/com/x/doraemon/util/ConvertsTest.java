package com.x.doraemon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Desc TODO
 * @Date 2020/9/24 20:16
 * @Author AD
 */
class ConvertsTest {
    
    private static byte[] bs = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 45};
    String hex = "1234567890abcde";
    
    @Test
    void arrayToString() {
        
        String s = Converts.arrayToString(new int[]{1, 2, 3, 4, 5});
        System.out.println(s);
    }
    
    @Test
    void byteToHex() {
        String s = Converts.byteToHex((byte) 15);
        System.out.println(s);
    }
    
    @Test
    void bytesToHex() {
        String s = Converts.bytesToHex(bs);
        System.out.println(s);
    }
    
    @Test
    void bytesToHexWithDelimiter() {
        String s = Converts.bytesToHexWithDelimiter(bs, "哈哈");
        System.out.println(s);
    }
    
    @Test
    void hexToBytes() {
        byte[] bytes = Converts.hexToBytes(null);
        System.out.println(Converts.bytesToHexWithDelimiter(bytes,"|"));
    }
    
}