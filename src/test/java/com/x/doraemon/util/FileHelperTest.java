package com.x.doraemon.util;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @Desc
 * @Date 2021/1/29 14:47
 * @Author AD
 */
class FileHelperTest {

    @Test
    void createFile() {
        try {
            String rootFolder = FileHelper.fixFolderPath(new File("").getAbsolutePath());
            rootFolder = rootFolder + "temp" + FileHelper.SP + "test" + FileHelper.SP;
            File newFile = FileHelper.createFile(rootFolder + "xxx.txt");
            System.out.println(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}