package com.x.doraemon.util;

import com.x.doraemon.bean.New;
import com.x.doraemon.bean.SB;
import com.x.doraemon.enums.DisplayStyle;
import com.x.doraemon.enums.Regex;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @Desc 字符串工具类
 * @Date 2020/7/29 00:57
 * @Author AD
 */
public final class Strings {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 16进制字符串(大小写)
     */
    public static final String ALL_HEX = "0123456789ABCDEFabcdef";
    
    /**
     * 空字符串
     */
    public static final String NULL = "";
    
    /**
     * 16进制字符串(大写)
     */
    public static final String HEX = "0123456789ABCDEF";
    
    /**
     * 大小写字母、数字
     */
    public static final char[] LETTER_FIGURE =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    
    /**
     * 随机变量
     */
    private static final Random RANDOM = new Random();
    // ------------------------ 构造方法 ------------------------
    
    private Strings() {}
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 将对象转为字符串(禁止使用强制类型转换)
     *
     * @param o 转换对象
     *
     * @return
     */
    public static String of(Object o) {
        return String.valueOf(o);
    }
    
    /**
     * 将第一个字母改为大写
     *
     * @param s 需要修改的字符串
     *
     * @return
     */
    public static String firstToUpper(String s) {
        byte[] bs = s.getBytes();
        bs[0] = (byte) ((char) bs[0] - 97 + 65);
        return new String(bs);
    }
    
    /**
     * 将第一个字母改为小写
     *
     * @param s 需要修改的字符串
     *
     * @return
     */
    public static String firstToLower(String s) {
        byte[] bs = s.getBytes();
        bs[0] = (byte) ((char) bs[0] - 65 + 97);
        return new String(bs);
    }
    
    /**
     * 比较两个字符串是否相等
     *
     * @param one  第一个字符串
     * @param two  第二个字符串
     * @param trim 是否去除前后空格比较
     *
     * @return
     */
    public static boolean equals(String one, String two, boolean trim) {
        one = one == null ? "" : (trim ? one.trim() : one);
        two = two == null ? "" : (trim ? two.trim() : two);
        return one.equals(two);
    }
    
    /**
     * byte[] --> 字符串，指定编码方式
     *
     * @param bs      byte[]
     * @param charset
     *
     * @return String
     */
    public static String decode(byte[] bs, Charset charset) {
        return new String(bs, charset);
    }
    
    /**
     * toString方法，默认多行显示<br>
     * 如：
     * com.x.commons.collection.NameValue@27fa135a[   <br>
     * name=name                                      <br>
     * value=AD                                       <br>
     * ]
     *
     * @param o 需要重写toString方法的对象
     *
     * @return
     */
    public static String defaultToString(Object o) {
        return o == null ? "<null>" : reflectToString(o, DisplayStyle.MULTI_LINE);
    }
    
    /**
     * toString方法，简单类名+属性，单行显示<br>
     * 如：NameValue[name=name,value=AD]
     *
     * @param o 需要重写toString方法的对象
     *
     * @return
     */
    public static String simpleToString(Object o) {
        return o == null ? "<null>" : reflectToString(o, DisplayStyle.SHORT_PREFIX);
    }
    
    /**
     * 自定义风格的toString方法
     *
     * @param o     需要重写toString方法的对象
     * @param style 显示风格
     *
     * @return
     */
    public static String reflectToString(Object o, DisplayStyle style) {
        return ToStringBuilder.reflectionToString(o, style.get());
    }
    
    public static String toJsonString(Object o) {
        return o.getClass().getName() + "@" + o.hashCode()+"=" + Jsons.toJson(o);
    }
    
    /**
     * 用参数内容替换占位符
     *
     * @param template 如：{0}是中国人,来自{1},{2}岁
     * @param params   需要替换的参数
     *
     * @return
     */
    public static String replace(String template, Object... params) {
        return isNull(template) ? "" : MessageFormat.format(template, toStrings(params));
    }
    
    /**
     * 判断字符串是否为"","    "
     *
     * @param check 需检查的字符串,默认去除前后空白条
     *
     * @return boolean
     */
    public static boolean isNull(String check) {
        return isNull(check, true);
    }
    
    /**
     * 判断字符串是否不为"","  "
     *
     * @param check 需检查的字符串,默认去除前后空白条
     *
     * @return boolean
     */
    public static boolean isNotNull(String check) {
        
        return !isNull(check, true);
    }
    
    /**
     * 判断字符串是否为null或者""（包含"null","{}"）
     *
     * @param check 需检查的字符串
     *
     * @return boolean
     */
    public static boolean isNullStr(String check) {
        return isNull(check, "null", "{}");
    }
    
    /**
     * 判断字符串是否为null或""
     *
     * @param check 需要检查的字符串
     * @param trim  是否去除前后空格
     *
     * @return
     */
    public static boolean isNull(String check, boolean trim) {
        if (trim) {
            return check == null || check.trim().length() == 0;
        } else {
            return check == null || check.length() == 0;
        }
    }
    
    /**
     * 判断字符串是否为null(或者被认为是null的字符串,不区分大小写)|""|"  "
     *
     * @param check 需检查的字符串
     * @param nulls 被认为是null的字符串(如：{},"null")
     *
     * @return boolean
     */
    public static boolean isNull(String check, String... nulls) {
        
        return isNull(check, false, nulls);
    }
    
    /**
     * 判断字符串是否为null(或者被认为是null的字符串,不区分大小写)|""|"  "
     *
     * @param check      需检查的字符串
     * @param ignoreCase 是否忽略大小写
     * @param nulls      被认为是null的字符串(如：{},"null")
     *
     * @return boolean
     */
    public static boolean isNull(String check, boolean ignoreCase, String... nulls) {
        if (ignoreCase) {
            return isNull(check) || Stream.of(nulls).anyMatch(n -> check.equalsIgnoreCase(n));
        }
        return isNull(check) || Stream.of(nulls).anyMatch(n -> check.equals(n));
    }
    
    /**
     * 判断字符串是否与字符串数组都不相等(不去除空格比较)
     *
     * @param check  需要判断的字符串
     * @param values 对比的字符串数组
     *
     * @return true:不相等；false:相等
     */
    public static boolean isNotEquals(String check, String... values) {
        if (Strings.isNull(check, false)) {
            if (ArrayHelper.isEmpty(values)) {
                return false;
            } else {
                for (String value : values) {
                    if (Strings.isNull(value, false)) {
                        return false;
                    }
                }
            }
        } else {
            if (ArrayHelper.isEmpty(values)) {
                return true;
            } else {
                for (String value : values) {
                    if (check.equals(value)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 判断是否是本地IP
     *
     * @param ip          需要判断的IP
     * @param removeSpace 是否移除所有空格
     *
     * @return
     */
    public static boolean isLocalhost(String ip, boolean removeSpace) {
        ip = removeSpace ? removeSpaces(ip) : ip;
        if (!isNull(ip)) {
            return ip.replaceAll("[0\\.]", NULL).length() == 0 ||
                   "127.0.0.1".equals(ip) ||
                   "localhost".equals(ip);
        }
        return true;
    }
    
    /**
     * 是否URL格式
     *
     * @param check 需检验的字符串
     *
     * @return
     */
    public static boolean isURLFormat(String check) {
        return match(check, Regex.URL);
    }
    
    /**
     * 是否邮箱格式
     *
     * @param check
     *
     * @return
     */
    public static boolean isEmailFormat(String check) {
        return match(check, Regex.EMAIL);
    }
    
    /**
     * 是否英文
     *
     * @param check
     *
     * @return
     */
    public static boolean isOnlyEnglish(String check) {
        return match(check, Regex.ONLY_ENGLISH);
    }
    
    /**
     * 是否整数
     *
     * @param check
     *
     * @return
     */
    public static boolean isLong(String check) {
        return match(check, Regex.LONG);
    }
    
    /**
     * 是否数字，包括小数
     *
     * @param check
     *
     * @return
     */
    public static boolean isNumeric(String check) {
        return match(check, Regex.NUMERIC);
    }
    
    /**
     * 是否无符号数
     *
     * @param check
     *
     * @return
     */
    public static boolean isUnsignedNumeric(String check) {
        return match(check, Regex.UNSIGNED_NUMERIC);
    }
    
    /**
     * 是否浮点数，包括float，不包括整数
     *
     * @param check
     *
     * @return
     */
    public static boolean isDouble(String check) {
        return match(check, Regex.DOUBLE) && !isLong(check);
    }
    
    /**
     * 是否时间
     *
     * @param check
     *
     * @return
     */
    public static boolean isTime(String check) {
        return match(check, Regex.TIME);
    }
    
    /**
     * 是否日期
     *
     * @param check
     *
     * @return
     */
    public static boolean isDate(String check) {
        return match(check, Regex.DATE);
    }
    
    /**
     * 是否日期时间
     *
     * @param check
     *
     * @return
     */
    public static boolean isDateTime(String check) {
        return match(check, Regex.DATE_TIME);
    }
    
    public static boolean isDateOrTime(String check) {
        return match(check, Regex.DATE_OR_TIME);
    }
    
    /**
     * 是否只包含中文，不能包含数字、标点
     *
     * @param check
     *
     * @return
     */
    public static boolean isOnlyChinese(String check) {
        return match(check, Regex.ONLY_CHINESE);
    }
    
    /**
     * 是否存在中文
     *
     * @param check
     *
     * @return
     */
    public static boolean isExistChinese(String check) {
        return match(check, Regex.CHINESE);
    }
    
    /**
     * 修正字符串长度为12，不足时以前缀"0"填充，否则截取
     *
     * @param fix 需修正的字符串
     *
     * @return
     */
    public static String fix(String fix) {
        return fix(fix, 12, "0");
    }
    
    /**
     * 修正字符串至固定长度，不足时以前缀"0"填充，否则截取
     *
     * @param fix         需修正的字符串
     * @param totalLength 修正后总长度
     *
     * @return
     */
    public static String fix(String fix, int totalLength) {
        return fix(fix, totalLength, "0");
    }
    
    /**
     * 修正字符串至固定长度，不足以前缀填充，一般是0
     *
     * @param fix         需修正的字符串
     * @param totalLength 修正后总长度
     * @param prefix      长度不足时以前缀填充，默认是0
     *
     * @return
     */
    public static String fix(String fix, int totalLength, String prefix) {
        if (totalLength <= 0) {
            return "";
        }
        SB sb = New.sb(fix);
        int len = sb.length();
        if (len < totalLength) {
            int prefixLen = prefix.length();
            while (len < totalLength) {
                sb.preAppend(prefix);
                len = len + prefixLen;
            }
        }
        return sb.sub(len - totalLength);
    }
    
    /**
     * 获取异常信息
     *
     * @param throwable 可抛出的异常对象
     *
     * @return
     */
    public static String getExceptionTrace(Throwable throwable) {
        if (throwable == null) {
            return "none";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
    
    /**
     * 获取指定长度的随机字符串，包含数字、大小写字母
     *
     * @param length     指定长度
     * @param onlyFigure 是否只包含数字
     *
     * @return
     */
    public static String getRandom(int length, boolean onlyFigure) {
        SB sb = New.sb();
        int L = onlyFigure ? 10 : LETTER_FIGURE.length;
        if (onlyFigure) {
            for (int i = 0; i < length; ++i) {
                sb.append(RANDOM.nextInt(L));
            }
        } else {
            for (int i = 0; i < length; ++i) {
                sb.append(LETTER_FIGURE[RANDOM.nextInt(L)]);
            }
        }
        return sb.get();
    }
    
    /**
     * 移除字符串的所有空格
     *
     * @param fix 需修正的字符串
     */
    public static String removeSpaces(String fix) {
        if (isNull(fix)) {
            return "";
        }
        SB sb = New.sb();
        for (char c : fix.toCharArray()) {
            if (!Character.isSpaceChar(c)) sb.append(c);
        }
        return sb.get();
    }
    
    /**
     * 转换成大写
     *
     * @param convert
     *
     * @return
     */
    public static String toUpperCase(Object convert) {
        return convert == null ? "" : String.valueOf(convert).toUpperCase();
    }
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 正则表达式匹配
     *
     * @param check 需要检验的字符串
     * @param regex 正则表达式
     *
     * @return
     */
    private static boolean match(String check, String regex) {
        if (!isNull(check)) {
            String fix = check.trim();
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(fix);
            return m.find();
        }
        return false;
    }
    
    /**
     * 将对象数组转为字符串
     *
     * @param os 对象数组
     *
     * @return String[] 字符串数组
     */
    private static String[] toStrings(Object... os) {
        if (ArrayHelper.isEmpty(os)) {
            return new String[0];
        }
        String[] ss = new String[os.length];
        for (int i = 0, L = os.length; i < L; i++) {
            ss[i] = String.valueOf(os[i]);
        }
        return ss;
    }
    
    // ------------------------ 变量定义 ------------------------
    // ------------------------ 构造方法 ------------------------
    // ------------------------ 方法定义 ------------------------
    // ------------------------ 私有方法 ------------------------
    private void autoCreateMethod() {}
    
}
