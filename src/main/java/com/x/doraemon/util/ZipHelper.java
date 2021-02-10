package com.x.doraemon.util;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * @Desc 压缩工具类
 * @Date 2021/2/10 18:56
 * @Author AD
 */
public class ZipHelper {
    
    // ------------------------ 成员变量 ------------------------
    private static final int BUFFER_SIZE = 1024;
    // ------------------------ 构造方法 ------------------------
    
    private ZipHelper() {}
    
    // ------------------------ 成员方法 ------------------------
    
    /**
     * 压缩字节
     *
     * @param data 被压缩数据
     *
     * @return byte[] 被压缩之后的数据
     *
     * @throws Exception
     */
    public static byte[] compressBytes(byte[] data) throws Exception {
        byte[] result = new byte[0];
        if (ArrayHelper.isEmpty(data)) {
            return data;
        }
        
        Deflater deflater = new Deflater();
        deflater.reset();
        deflater.setInput(data);
        deflater.finish();
        
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(data.length)) {
            byte[] buf = new byte[BUFFER_SIZE];
            while (true) {
                if (!deflater.finished()) {
                    int len = deflater.deflate(buf);
                    if (len > 0) {
                        out.write(buf, 0, len);
                        continue;
                    }
                }
                result = out.toByteArray();
                return result;
            }
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    /**
     * 解压缩
     *
     * @param data 被解压数据
     *
     * @return 解压之后的数据
     *
     * @throws Exception
     */
    public static byte[] decompressBytes(byte[] data) throws Exception {
        byte[] result = new byte[0];
        if (ArrayHelper.isEmpty(data)) {
            return data;
        }
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);) {
            byte[] buf = new byte[BUFFER_SIZE];
            
            while (true) {
                if (!inflater.finished()) {
                    int len = inflater.inflate(buf);
                    if (len > 0) {
                        out.write(buf, 0, len);
                        continue;
                    }
                }
                result = out.toByteArray();
                return result;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 将文件压缩至同级目标，并以zip结尾命名
     *
     * @param source 源文件路径，如：/Users/sunday/IdeaProjects/Doraemon/target
     *
     * @return
     *
     * @throws Exception
     */
    public static boolean zip(String source) throws Exception {
        if (Strings.isNull(source)) {
            return false;
        }
        return zip(source, source + ".zip");
    }
    
    /**
     * 将文件压缩至指定目标
     *
     * @param source 源文件路径，如：/Users/sunday/IdeaProjects/Doraemon/target
     * @param target 目标文件名，如：/Users/sunday/IdeaProjects/Doraemon/target.zip
     *
     * @return
     *
     * @throws Exception
     */
    public static boolean zip(String source, String target) throws Exception {
        // 创建源文件对象
        File sourceFile = new File(source);
        // 判断源文件是否存在
        if (!sourceFile.exists()) {
            return false;
        }
        // 创建目标文件对象
        File targetFile = new File(target);
        // 判断目标压缩文件是否存在
        if (targetFile.exists()) {
            throw new Exception("目标压缩文件已存在");
        }
        // 创建压缩文件输出流
        try (FileOutputStream fileOut = new FileOutputStream(target);
             ZipOutputStream zipOut = new ZipOutputStream(fileOut)) {
            // 判断源文件是否是目标
            if (sourceFile.isDirectory()) {
                zipFolder(sourceFile, zipOut, sourceFile.getName());
            } else {
                zipFile(sourceFile, zipOut, sourceFile.getName());
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static boolean unZip(String zipPath, String target) throws Exception {
        // 修正目标路径
        target = FileHelper.fixPath(target, true);
        // 创建缓冲区
        byte[] buf = new byte[BUFFER_SIZE];
        // 创建文件对象
        File zip = new File(zipPath);
        // 判断压缩文件是否存在
        if (!zip.exists()) {
            return false;
        } else {
            // 创建解压缩的目标文件夹
            FileHelper.createFolder(target);
            try {
                // 创建文件压缩对象
                ZipFile zipFile = new ZipFile(zip);
                Enumeration<? extends ZipEntry> enums = zipFile.entries();
                ZipEntry entry = null;
                while (true) {
                    while (enums.hasMoreElements()) {
                        entry = enums.nextElement();
                        File file = new File(target + entry.getName());
                        if (entry.isDirectory()) {
                            file.mkdirs();
                        } else {
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            FileOutputStream out = new FileOutputStream(file);
                            InputStream in = zipFile.getInputStream(entry);
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                            out.flush();
                            out.close();
                        }
                    }
                    zipFile.close();
                    return true;
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
    
    /**
     * 压缩文件夹嵌套的数据
     *
     * @param file
     * @param zipOut
     * @param supName
     *
     * @throws Exception
     */
    private static void zipFolder(File file, ZipOutputStream zipOut, String supName) throws Exception {
        ZipEntry entry = new ZipEntry(supName + "/");
        zipOut.putNextEntry(entry);
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0, L = files.length; i < L; ++i) {
                File sub = files[i];
                String name = supName + "/" + sub.getName();
                if (sub.isDirectory()) {
                    zipFolder(sub, zipOut, name);
                } else {
                    zipFile(sub, zipOut, name);
                }
            }
        }
        
    }
    
    /**
     * 压缩单个文件
     *
     * @param file
     * @param zipOut
     * @param name
     *
     * @throws Exception
     */
    private static void zipFile(File file, ZipOutputStream zipOut, String name) throws Exception {
        // 创建文件输入流
        try (FileInputStream in = new FileInputStream(file)) {
            // 创建一个zip节点
            ZipEntry entry = new ZipEntry(name);
            // 往zip流里写入该节点
            zipOut.putNextEntry(entry);
            byte[] buf = new byte[5120];
            int len;
            while ((len = in.read(buf)) != -1) {
                zipOut.write(buf, 0, len);
            }
            zipOut.flush();
        } catch (FileNotFoundException e) {
            throw e;
        }
    }
    
}
