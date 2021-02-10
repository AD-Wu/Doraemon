package com.x.doraemon.util;

import com.x.doraemon.bean.New;
import com.x.doraemon.bean.SB;
import com.x.doraemon.enums.Charsets;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;

/**
 * @Desc 文件帮助类
 * @Date 2021/1/29 14:11
 * @Author AD
 */
public class FileHelper {
    
    // ------------------------ 成员变量 ------------------------
    /**
     * Windows：
     * <p>
     * 　　“/”是表示参数，“\”是表示本地路径。
     * <p>
     * Linux和Unix：
     * <p>
     * 　　“/”表示路径，“\”表示转义，“-”和“--”表示参数。
     * <p>
     * 网络：
     * <p>
     * 　　由于网络使用Unix标准，所以网络路径用“/”。
     */
    public static final String SP = File.separator;
    /**
     * 类加载器
     */
    private static final ClassLoader LOADER = Thread.currentThread().getContextClassLoader();
    /**
     * 当前应用路径
     */
    private static String APP_PATH;
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 构造方法
     */
    private FileHelper() {
    }
    
    // ------------------------ 成员方法 ------------------------
    
    /**
     * 创建文件（通过文件名绝对路径创建空白文件）
     *
     * @param fileAbsPath 完整文件名，即绝对路径，如：/home/xxx.txt
     *
     * @return File 文件对象
     */
    public static File createFile(String fileAbsPath) throws Exception {
        // 判断路径有效性
        Objects.requireNonNull(fileAbsPath, "file path can't be null");
        // 修正文件路径
        String fixPath = fixPath(fileAbsPath);
        // 创建文件对象
        File file = new File(fixPath);
        // 文件已存在
        if (file.exists()) {
            // 返回该文件对象
            return file;
        }
        // 判断文件所在文件夹是否已存在
        if (!file.getParentFile().exists()) {
            // 创建文件夹和文件
            if (file.getParentFile().mkdirs() && file.createNewFile()) {
                // 返回文件对象
                return file;
            } else {
                // 创建异常
                throw new RuntimeException("Create File Exception");
            }
        } else {
            // 创建文件
            if (file.createNewFile()) {
                // 返回文件对象
                return file;
            } else {
                // 创建文件异常
                throw new RuntimeException("Create File Exception");
            }
        }
    }
    
    /**
     * 默认以UTF-8的编码创建文件,content会覆盖原有文件内容
     *
     * @param fileAbsPath 文件名
     * @param content     文件内容
     *
     * @return true:成功 <p> false:失败
     */
    public static boolean createFile(String fileAbsPath, String content) {
        return createFile(fileAbsPath, content, Charsets.UTF8.name());
    }
    
    /**
     * 创建文件,如果文件已存在,会覆盖原有内容
     *
     * @param fileAbsPath 文件名
     * @param content     文件内容
     * @param charset     文件字符编码
     *
     * @return true:成功 <p> false:失败
     */
    public static boolean createFile(String fileAbsPath, String content, String charset) {
        try {
            File file = createFile(fileAbsPath);
            try (PrintWriter writer = new PrintWriter(file, charset);) {
                writer.print(content);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     *
     * @return true：文件夹已存在或创建成功  false：文件夹创建失败
     */
    public static boolean createFolder(String path) {
        if (Strings.isNull(path)) {
            return false;
        }
        try {
            File file = new File(path);
            return file.exists() || file.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 在当前app目录下创建多个文件夹
     *
     * @param paths
     */
    public static void createFolders(String[] paths) {
        String appPath = getAppPath(true);
        for (int i = 0, L = paths.length; i < L; ++i) {
            createFolder(appPath + paths[i]);
        }
    }
    
    /**
     * 拷贝文件至指定文件夹。如果文件已存在，则不拷贝
     *
     * @param filePath   文件路径
     * @param folderPath 文件夹路径
     *
     * @return
     */
    public static boolean copyFile(String filePath, String folderPath) {
        // 判断文件和文件夹路径的有效性
        if (Strings.isNull(filePath) || Strings.isNull(folderPath)) {
            return false;
        }
        // 创建文件对象
        File file = new File(filePath);
        // 创建文件夹对象
        File folder = new File(fixPath(folderPath));
        // 判断文件有效性
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        // 目标文件夹不存在
        if (!folder.exists()) {
            // 创建文件夹
            folder.mkdirs();
        }
        // 在目标文件夹下创建新文件对象
        File newFile = new File(fixPath(folder.getAbsolutePath(), true) + file.getName());
        // 判断文件是否已存在
        if (newFile.exists()) {
            return false;
        }
        // 读取文件，同时写出文件
        try (FileInputStream in = new FileInputStream(file);
             FileOutputStream out = new FileOutputStream(newFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 将源文件夹下的所有文件拷贝至目标文件夹（包括srcFolder）
     *
     * @param srcFolder    需拷贝的文件夹路径
     * @param targetFolder 目的地文件夹
     */
    public static boolean copyFolder(String srcFolder, String targetFolder) {
        // 判断源文件夹和目的地文件夹路径的有效性
        if (Strings.isNull(srcFolder) || Strings.isNull(targetFolder)) {
            return false;
        }
        // 创建源文件夹对象
        File src = new File(srcFolder);
        // 创建目的地文件夹对象
        File target = new File(targetFolder);
        // 判断源文件夹是否存在
        if (!src.exists()) {
            return false;
        }
        // 判断目的地文件夹是否存在
        if (!target.exists()) {
            // 创建目的地文件夹
            if (!createFolder(targetFolder)) {
                return false;
            }
        }
        // 判断源文件是否非目录
        if (src.isFile()) {
            // 非目录直接拷贝
            return copyFile(srcFolder, targetFolder);
        } else {
            // 往目的地文件夹创建源文件夹
            String subFolder = fixPath(targetFolder, true) + src.getName();
            if (!createFolder(subFolder)) {
                return false;
            }
            // 获取源文件夹下所有文件
            File[] files = src.listFiles();
            // 遍历所有文件
            for (File file : files) {
                // 如果目标文件夹和源文件夹是同级目录，要避免无限递归
                if (file.getPath().equals(targetFolder)) {
                    continue;
                }
                // 递归拷贝
                if (!copyFolder(file.getPath(), subFolder)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    /**
     * 删除文件或空文件夹
     *
     * @param filePath 文件路径
     *
     * @return true:删除成功  false:删除不成功
     */
    public static boolean deleteFile(String filePath) {
        // 判断路径有效性
        if (Strings.isNull(filePath)) {
            return false;
        }
        // 创建文件对象
        File file = new File(filePath);
        // 如果是目录
        if (file.isDirectory()) {
            // 非空目录不删除
            if (file.listFiles().length > 0) {
                return false;
            }
            // 空目录则删除
            return file.delete();
        }
        // 文件不存在表示删除成功
        if (!file.exists()) {
            return true;
        }
        // 返回删除结果
        return file.delete();
    }
    
    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹路径
     *
     * @return
     */
    public static boolean deleteFolder(String folderPath) {
        // 判断路径有效性
        if (Strings.isNull(folderPath)) {
            return false;
        }
        // 创建文件对象
        File folder = new File(folderPath);
        // 判断是否是文件
        if (folder.isFile()) {
            // 文件直接删除
            return deleteFile(folder.getPath());
        }
        // 获取文件夹下所有文件
        File[] files = folder.listFiles();
        // 判断是否是空文件夹
        if (files.length == 0) {
            // 删除空文件夹
            return folder.delete();
        }
        // 非空文件夹，便利所有文件对象
        for (File file : files) {
            // 遍历删除
            deleteFolder(file.getPath());
        }
        // 最后删除根文件夹
        return folder.delete();
        
    }
    
    /**
     * 以UTF-8的方式读取xxx.txt文本
     *
     * @param txtPath
     *
     * @return
     *
     * @throws IOException
     */
    public static String readTxt(String txtPath) throws IOException {
        // 以UTF-8的形式读取txt内容
        return readTxt(txtPath, "UTF-8");
    }
    
    /**
     * 读取xxx.txt文本
     *
     * @param txtPath  文本路径
     * @param encoding 字符编码
     *
     * @return
     *
     * @throws IOException
     */
    public static String readTxt(String txtPath, String encoding) throws IOException {
        SB sb = New.sb();
        String result = "";
        InputStream in = null;
        try {
            // 加载txt文件流
            in = LOADER.getResourceAsStream(txtPath);
            // 判断文件流是否有效
            if (in == null) {
                // 通过绝对路径创建文件对象
                File file = new File(txtPath);
                // 判断文件是否存在
                if (!file.exists()) {
                    System.out.println("========== Files.readTxt() appPath:" + APP_PATH);
                    // 在app根目录下查找该文件
                    in = new FileInputStream(getAppPath(true) + txtPath);
                } else {
                    // 文件存在，直接读入文件流
                    in = new FileInputStream(txtPath);
                }
            }
            // 获取文件编码读取器
            try (InputStreamReader reader = getUnicodeReader(in, encoding);
                 BufferedReader bufReader = new BufferedReader(reader);) {
                String content = "";
                while ((content = bufReader.readLine()) != null) {
                    /*
                     * \r是回车,\n是换行.不通的平台是要\r\n才能达到换行，如:
                     * - windows: \r\n
                     * - mac: \r
                     * - unix/linux: \n
                     */
                    sb.append(content + "\r\n");
                }
            }
            result = sb.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            result = "";
        } finally {
            try {
                if (in != null) {
                    ((InputStream) in).close();
                }
            } catch (Exception e) {
            }
            
        }
        return result;
    }
    
    /**
     * 获取唯一字符读取器
     *
     * @param in 输入流
     *
     * @return
     *
     * @throws IOException
     */
    public static InputStreamReader getUnicodeReader(InputStream in) throws IOException {
        return getUnicodeReader(in, Charsets.UTF8.name());
    }
    
    /**
     * 获取唯一字符读取器
     *
     * @param in       输入流
     * @param encoding 字符编码
     *
     * @return
     *
     * @throws IOException
     */
    public static InputStreamReader getUnicodeReader(InputStream in, String encoding) throws IOException {
        if (in == null) {
            return null;
        } else {
            byte[] buf = new byte[4];
            PushbackInputStream back = new PushbackInputStream(in, 4);
            int byteNums = back.read(buf, 0, buf.length);
            int unread;
            if (buf[0] == 0 && buf[1] == 0 && buf[2] == -2 && buf[3] == -1) {
                encoding = "UTF-32BE";
                unread = byteNums - 4;
            } else if (buf[0] == -1 && buf[1] == -2 && buf[2] == 0 && buf[3] == 0) {
                encoding = "UTF-32LE";
                unread = byteNums - 4;
            } else if (buf[0] == -17 && buf[1] == -69 && buf[2] == -65) {
                encoding = "UTF-8";
                unread = byteNums - 3;
            } else if (buf[0] == -2 && buf[1] == -1) {
                encoding = "UTF-16BE";
                unread = byteNums - 2;
            } else if (buf[0] == -1 && buf[1] == -2) {
                encoding = "UTF-16LE";
                unread = byteNums - 2;
            } else {
                if (encoding == null || encoding.equals("")) {
                    encoding = Charset.defaultCharset().name();
                }
                
                unread = byteNums;
            }
            if (unread > 0) {
                back.unread(buf, byteNums - unread, unread);
            }
            
            return new InputStreamReader(back, encoding);
        }
    }
    
    /**
     * 获取当前应用所在的路径
     *
     * @param endWithSP 返回的路径是否要以系统文件分隔符结束
     *
     * @return
     */
    public static String getAppPath(boolean endWithSP) {
        // 判断当前app路径是否有效
        if (Strings.isNull(APP_PATH)) {
            // 获取当前app路径
            String absPath = new File("").getAbsolutePath();
            // 判断是否以文件分隔符结尾
            if (absPath.endsWith(SP)) {
                // 判断是否是根目录
                if (absPath.length() > 1) {
                    // 非根目录
                    absPath = absPath.substring(0, absPath.length() - 1);
                } else {
                    // 根目录
                    absPath = "";
                }
            }
            // 初始化app路径
            APP_PATH = absPath;
        }
        // 判断是否以文件分隔符结尾，并返回结果
        return endWithSP ? APP_PATH + SP : APP_PATH;
    }
    
    /**
     * 获取文件夹下的所有非目录文件路径
     *
     * @param folderPath 文件夹路径
     *
     * @return String[] 非目录文件路径数组
     */
    public static String[] getFiles(String folderPath) {
        // 判断文件夹路径有效性
        if (Strings.isNull(folderPath)) {
            // 返回空数组
            return ArrayHelper.EMPTY_STRING;
        }
        // 创建容器
        List<String> filePaths = New.list();
        // 递归获取文件
        getFiles(filePaths, folderPath);
        // 返回转换结果
        return filePaths.toArray(ArrayHelper.EMPTY_STRING);
        
    }
    
    /**
     * 获取文件对象MD5值
     *
     * @param file 文件对象
     *
     * @return 文件对象的MD5值（大写）
     */
    public static String getMD5(File file) {
        // 判断文件有效性
        if (file == null) {
            return "";
        }
        // 创建文件输入流
        try (FileInputStream fin = new FileInputStream(file)) {
            // 读取文件字节缓冲数据
            MappedByteBuffer buf = fin.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            // 获取MD5算法对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 计算MD5值
            md5.update(buf);
            // 创建大数对象
            BigInteger bi = new BigInteger(1, md5.digest());
            // 转换为16进制
            return bi.toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 根据文件系统分隔符修正路径，并以分隔符结尾
     *
     * @param path 路径
     *
     * @return
     */
    public static String fixPath(String path) {
        return fixPath(path, false);
    }
    
    /**
     * 根据文件系统分隔符修正路径
     *
     * @param path      路径
     * @param endWithSP 是否以文件系统的分隔符结尾
     *
     * @return
     */
    public static String fixPath(String path, boolean endWithSP) {
        return fixPath(path, SP, endWithSP);
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static String fixPath(String path, String fileSeparator, boolean endWithSP) {
        // 判断需要修正的路径是否有效
        if (Strings.isNull(path)) {
            return "";
        }
        String fixPath;
        // window系统
        if ("\\".equals(fileSeparator)) {
            // 将“/”全部修改为：“\”
            fixPath = path.replaceAll("/", "\\\\");
            
        }
        // linux系统
        else if ("/".equals(fileSeparator)) {
            // 将“\”全部修改为：“/”
            fixPath = path.replaceAll("\\\\", "/");
        } else {
            fixPath = path;
        }
        // 判断是否以文件分隔符结尾
        if (endWithSP) {
            // 没有以分隔符结尾
            if (!fixPath.endsWith(fileSeparator)) {
                // 追加分隔符
                fixPath = fixPath + fileSeparator;
            }
        } else {
            // 以分隔符结尾
            if (fixPath.endsWith(fileSeparator)) {
                // 去掉分隔符
                fixPath = fixPath.substring(0, fixPath.length() - 1);
            }
        }
        // 返回修正路径
        return fixPath;
        
    }
    
    /**
     * 递归获取文件夹下的所有非目录文件路径
     *
     * @param filePaths  路径容器
     * @param folderPath 文件夹路径
     */
    private static void getFiles(List<String> filePaths, String folderPath) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                getFiles(filePaths, file.getPath());
            }
        } else {
            filePaths.add(folder.getPath());
        }
    }
    
}
