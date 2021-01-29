package com.x.doraemon.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @Desc
 * @Date 2021/1/29 14:47
 * @Author AD
 */
class FileHelperTest {

    private static String filePath;

    @BeforeAll
    static void before() {
        String rootFolder = FileHelper.fixFolderPath(new File("").getAbsolutePath());
        filePath = rootFolder + "temp" + FileHelper.SP + "test" + FileHelper.SP + "xxx.txt";
    }

    @Test
    void createFile() {
        try {
            File file = FileHelper.createFile(filePath);
            System.out.println(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateFile() {
        boolean success = FileHelper.createFile(filePath, "这是一个测试内容");
        System.out.println(filePath);
        System.out.println(success);
    }

    @Test
    void readTxt() {
        try {
            String txt = FileHelper.readTxt(filePath);
            System.out.println(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getFileCharset() {
        String charset = FileHelper.getFileCharset(filePath);
        System.out.println(charset);
    }

}