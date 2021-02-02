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
        String rootFolder = FileHelper.fixPath(new File("").getAbsolutePath(), true);
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
    void copyFile() {
        FileHelper.createFile(filePath, "这是一个拷贝测试");
        String folder = FileHelper.getAppPath(false);
        boolean b = FileHelper.copyFile(filePath, folder);
        System.out.println("【" + filePath + "】拷贝文件至【" + folder + "】成功?" + b);
    }

    @Test
    void getFiles() {
        String appPath = FileHelper.getAppPath(false);
        String[] files = FileHelper.getFiles(appPath);
        for (String file : files) {
            System.out.println(file);
        }
    }

    @Test
    void copyFolder() {
        String appPath = FileHelper.getAppPath(false);
        String targetFolder = "E:\\";
        boolean b = FileHelper.copyFolder(appPath, targetFolder);
        System.out.println(b);
    }

}