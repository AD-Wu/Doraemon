package com.x.doraemon.util;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.x.doraemon.bean.New;
import com.x.doraemon.bean.SB;
import com.x.doraemon.enums.Charsets;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * 以UTF-8的方式读取xxx.txt文本
     *
     * @param txtPath
     * @return
     * @throws IOException
     */
    public static String readTxt(String txtPath) throws IOException {
        // 以UTF-8的形式读取txt内容
        return readTxt(txtPath, getFileCharset(txtPath));
    }

    /**
     * 读取xxx.txt文本
     *
     * @param txtPath  文本路径
     * @param encoding 字符编码
     * @return
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

    public static String getFileCharset(String filePath) {
        String encoding = null;

        try {
            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);
            CharsetDetector detector = new CharsetDetector();
            detector.setText(data);
            CharsetMatch match = detector.detect();
            encoding = match.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoding;
    }

    /**
     * 获取唯一字符读取器
     *
     * @param in 输入流
     * @return
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
     * @return
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

    public static String fixFolderPath(String path) {
        return fixPath(path, true);
    }

    public static String fixFilePath(String path) {
        return fixPath(path, false);
    }

    /**
     * 根据文件系统分隔符修正路径，并以分隔符结尾
     *
     * @param path 路径
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

}
