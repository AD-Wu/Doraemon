package com.x.doraemon.util;

import com.x.doraemon.bean.New;
import com.x.doraemon.bean.SB;
import com.x.doraemon.interfaces.IConverter;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.x.doraemon.util.ArrayHelper.EMPTY_BYTE;
import static com.x.doraemon.util.StringHelper.ALL_HEX;
import static com.x.doraemon.util.StringHelper.HEX;

/**
 * @Desc 转换帮助类
 * @Date 2020/7/29 00:59
 * @Author AD
 */
public class ConvertHelper {

    // ------------------------ 变量定义 ------------------------

    // ------------------------ 构造方法 ------------------------
    private ConvertHelper() {
    }

    // ------------------------ 方法定义 ------------------------

    public static <FROM, TO> TO convert(IConverter<FROM, TO> converter, FROM from) throws Exception {
        return converter.convert(from);
    }

    public static <FROM, TO> FROM reconvert(IConverter<FROM, TO> converter, TO to) throws Exception {
        return converter.reconvert(to);
    }
    
    

    /**
     * int数组转为字符串
     *
     * @param ints int数组
     * @return 如：1, 2, 3
     */
    public static String arrayToString(int[] ints) {
        if (ArrayHelper.isEmpty(ints)) {
            return "";
        } else {
            SB sb = New.sb();
            for (int a : ints) {
                sb.append(a).append(",");
            }
            return sb.deleteLast().toString();
        }
    }

    /**
     * 将long型数组转为字符串
     *
     * @param longs long数组
     * @return 如：1, 2, 3
     */
    public static String arrayToString(long[] longs) {
        if (ArrayHelper.isEmpty(longs)) {
            return "";
        } else {
            SB sb = New.sb();
            for (long a : longs) {
                sb.append(a).append(",");
            }
            return sb.deleteLast().toString();
        }
    }

    /**
     * 将字节转为16进制字符串（大写）
     *
     * @param b 字节数据
     * @return 如：0A
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF).toUpperCase().trim();
        return hex.length() < 2 ? "0" + hex : hex;
    }

    /**
     * 将16进制字符串解析为byte值，字符串长度不能超过2
     *
     * @param hex 16进制字符串
     * @return
     */
    public static byte hexToByte(String hex) {
        if (hex.length() > 2) {
            hex = hex.substring(0, 2);
        }
        String fix = fixHex(hex);
        int i = Integer.parseInt(fix, 16);
        return (byte) i;
    }

    /**
     * 将字节数组转为16进制字符串（大写）
     *
     * @param bs 字节数组
     * @return 如：0A1B
     */
    public static String bytesToHex(byte[] bs) {
        SB sb = New.sb();
        for (byte b : bs) {
            sb.append(byteToHex(b));
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转为字节数组
     *
     * @param hex 16进制字符串
     * @return byte[]字节数组
     */
    public static byte[] hexToBytes(String hex) {
        if (StringHelper.isNull(hex)) {
            return EMPTY_BYTE;
        }
        char[] cs = hex.toUpperCase().toCharArray();
        byte[] bs = new byte[cs.length / 2];
        for (int i = 0, k = 0, L = bs.length; i < L; ++i, k += 2) {
            bs[i] = (byte) (HEX.indexOf(cs[k]) << 4 | HEX.indexOf(cs[k + 1]));
        }
        return bs;
    }

    /**
     * 将字节数组转为16进制字符串（大写），并用分割符分割
     *
     * @param bs        字节数组
     * @param delimiter 分割符
     * @return 如：0A,1B,2C
     */
    public static String bytesToHexWithDelimiter(byte[] bs, String delimiter) {
        if (ArrayHelper.isEmpty(bs)) {
            return "";
        }
        SB sb = New.sb();
        for (byte b : bs) {
            sb.append(byteToHex(b)).append(delimiter);
        }
        return sb.deleteLast(delimiter.length()).toString();
    }

    /**
     * 将带分割符的16进制字符串解析成字节数组
     *
     * @param hex       带分隔符的16进制字符串，如：0A,1B,2C
     * @param delimiter 分割符，如：“,” 或 “|” ……
     * @return
     */
    public static byte[] hexWithDelimiterToBytes(String hex, String delimiter) {
        // 判断字符串有效性
        if (StringHelper.isNull(hex)) {
            return EMPTY_BYTE;
        }
        // 判断分割符是否为空
        if (StringHelper.isNotNull(delimiter)) {
            // 判断分隔符是否需要转义
            if ("|".equals(delimiter)) {
                delimiter = "\\|";
            }
            String[] hexes = hex.split(delimiter);
            byte[] bytes = new byte[hexes.length];
            for (int i = 0; i < bytes.length; i++) {
                if (onlyHex(hexes[i])) {
                    bytes[i] = hexToByte(hexes[i]);
                }
            }
            return bytes;
        } else {
            // 没有分隔符则直接解析
            return hexToBytes(hex);
        }

    }

    /**
     * true，y，yes，1字符串值为true，其它为false
     *
     * @param value 需转换的值
     * @return
     */
    public static boolean toBoolean(Object value) {
        return toBoolean(value, false);
    }

    /**
     * true，y，yes，1字符串值为true，其它为false
     *
     * @param value        需转换的值
     * @param defaultValue 默认值
     * @return
     */
    public static boolean toBoolean(Object value, boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String t = StringHelper.toUpperCase(value);
        return "TRUE".equals(t) || "Y".equals(t) || "YES".equals(t) || ("1".equals(t)) || defaultValue;
    }

    /**
     * 将对象转为10进制int数值，转换出错时默认返回0
     *
     * @param value 转换对象
     * @return
     */
    public static int toInt(Object value) {
        return toInt(value, 0, 10);
    }

    /**
     * 将对象转为10进制int数值，转换出错时默认返回defaultValue
     *
     * @param value        转换对象
     * @param defaultValue 默认值
     * @return
     */
    public static int toInt(Object value, int defaultValue) {
        return toInt(value, defaultValue, 10);
    }

    /**
     * 将对象转为指定进制int数值，转换出错时默认返回defaultValue
     *
     * @param value        转换对象
     * @param defaultValue 默认值
     * @param radix        进制
     * @return
     */
    public static int toInt(Object value, int defaultValue, int radix) {
        // 判断有效性
        if (value == null) {
            // 为空返回默认值
            return defaultValue;
        }
        // 非int类型
        if (!value.getClass().equals(Integer.TYPE) && !(value instanceof Integer)) {
            // 非double类型
            if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
                // 转为字符串
                String s = value.toString();
                // 判断有效性
                if (StringHelper.isNull(s)) {
                    // 返回默认值
                    return defaultValue;
                } else {
                    try {
                        // 解析成对应进制的int值
                        return Integer.parseInt(s, radix);
                    } catch (Exception e) {
                        // 异常返回默认值
                        return defaultValue;
                    }
                }
            } else {
                return ((Double) value).intValue();
            }
        } else {
            return (Integer) value;
        }

    }

    public static byte toByte(Object value) {
        return toByte(value, (byte) 0, 10);
    }

    public static byte toByte(Object value, byte defaultValue) {
        return toByte(value, defaultValue, 10);
    }

    public static byte toByte(Object value, byte defaultValue, int radix) {
        if (value == null) {
            return defaultValue;
        }
        if (!(value instanceof Byte) && !(value instanceof Integer)) {
            String s = value.toString();
            if (StringHelper.isNull(s)) {
                return defaultValue;
            } else {
                try {
                    byte b = (byte) Integer.parseInt(s, radix);
                    return b;
                } catch (Exception e) {
                    return defaultValue;
                }
            }
        } else {
            return Byte.valueOf(value.toString());
        }

    }

    public static short toShort(Object value) {
        return toShort(value, (short) 0, 10);
    }

    public static short toShort(Object value, short defaultValue) {
        return toShort(value, defaultValue, 10);
    }

    public static short toShort(Object value, short defaultValue, int radix) {
        if (value == null) {
            return defaultValue;
        }
        if (!value.getClass().equals(Short.TYPE) && !(value instanceof Short)) {
            if (!value.getClass().equals(Integer.TYPE) && !(value instanceof Integer)) {
                if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
                    String s = value.toString();
                    if (StringHelper.isNull(s)) {
                        return defaultValue;
                    } else {
                        try {
                            return Short.valueOf(s, radix);
                        } catch (Exception e) {
                            return defaultValue;
                        }
                    }
                } else {
                    return ((Double) value).shortValue();
                }
            } else {
                return ((Integer) value).shortValue();
            }
        } else {
            return (Short) value;
        }
    }

    public static long toLong(Object value) {
        return toLong(value, 0L);
    }

    public static long toLong(Object value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (!value.getClass().equals(Long.TYPE) && !(value instanceof Long)) {
            String s = value.toString();
            if (StringHelper.isNull(s)) {
                return defaultValue;
            } else {
                try {
                    return Double.valueOf(s).longValue();
                } catch (Exception var5) {
                    return defaultValue;
                }
            }
        } else {
            return (Long) value;
        }
    }

    public static float toFloat(Object value) {
        return toFloat(value, 0.0F);
    }

    public static float toFloat(Object value, float defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (!value.getClass().equals(Float.TYPE) && !(value instanceof Float)) {
            if (value instanceof Double) {
                return ((Double) value).floatValue();
            } else {
                String s = value.toString();
                if (StringHelper.isNull(s)) {
                    return defaultValue;
                } else {
                    try {
                        return Double.valueOf(s).floatValue();
                    } catch (Exception var4) {
                        return defaultValue;
                    }
                }
            }
        } else {
            return (Float) value;
        }
    }

    public static double toDouble(Object value) {
        return toDouble(value, 0.0D);
    }

    public static double toDouble(Object value, double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
            String s = value.toString();
            if (StringHelper.isNull(s)) {
                return defaultValue;
            } else {
                try {
                    return Double.valueOf(s);
                } catch (Exception var5) {
                    return defaultValue;
                }
            }
        } else {
            return (Double) value;
        }
    }
    
    public static <T> String toString(List<T> list) {
        return toString(list, ",");
    }
    
    public static <T> String toString(List<T> list, String split) {
        if (!ArrayHelper.isEmpty(list)) {
            SB sb = New.sb();
            if (list != null) {
                for (int i = 0, L = list.size(); i < L; ++i) {
                    if (i > 0) {
                        sb.append(split);
                    }
                    sb.append(list.get(i));
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    public static BigDecimal toBigDecimal(Object value) {
        return toBigDecimal(value, (BigDecimal) null);
    }

    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        return value == null ? defaultValue : new BigDecimal(value.toString());
    }

    /**
     * 将可序列化对象转为字节数组
     *
     * @param serial 可序列化对象
     * @return
     */
    public static byte[] objectToBytes(Serializable serial) {
        if (serial == null) {
            return EMPTY_BYTE;
        } else {
            try (ByteArrayOutputStream bsOut = new ByteArrayOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(bsOut)) {
                out.writeObject(serial);
                out.flush();
                return bsOut.toByteArray();
            } catch (IOException e) {
                LogHelper.getLogger(ConvertHelper.class).error(StringHelper.getExceptionTrace(e));
                return EMPTY_BYTE;
            }
        }
    }

    /**
     * 将字节数组转为对象
     *
     * @param bytes 字节数组
     * @param <T>   结果对象
     * @return
     */
    public static <T> T bytesToObject(byte[] bytes, Class<T> clazz) {
        if (ArrayHelper.isEmpty(bytes)) {
            return null;
        }
        try (ByteArrayInputStream bsIn = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bsIn)) {
            Object o = in.readObject();
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将两个数组进行复制整合
     *
     * @param first
     * @param second
     * @param <T>
     *
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        } else {
            T[] ts = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, ts, first.length, second.length);
            return ts;
        }
        
    }
    
    public static int[] concat(int[] first, int[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        } else {
            int[] ts = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, ts, first.length, second.length);
            return ts;
        }
    }
    
    public static <T> T[] concatAll(T[] first, T[]... ts) {
        if (ts == null) {
            return first;
        }
        int firstLen = first == null ? 0 : first.length;
        int allLen = 0;
        allLen += firstLen;
        for (T[] t : ts) {
            if (t != null) {
                if (first == null) {
                    first = t;
                }
                allLen += t.length;
            }
        }
        if (allLen == 0) return null;
        T[] all = Arrays.copyOf(first, allLen);
        int L = first.length;
        for (T[] t : ts) {
            if (t != null && t.length > 0) {
                System.arraycopy(t, 0, all, L, t.length);
                L += t.length;
            }
            
        }
        return all;
    }

    // ------------------------ 私有方法 ------------------------

    /**
     * 判断是否只包含16进制字符串
     *
     * @param check 需检查字符串
     * @return boolean
     * @author AD
     * @date 2018-12-22 18:37
     */
    private static boolean onlyHex(String check) {
        for (char c : check.toCharArray()) {
            if (ALL_HEX.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 修正16进制字符串长度，如：A --> 0A
     *
     * @param hex 需修正的16进制字符串
     * @return
     */
    private static String fixHex(String hex) {
        return hex.length() % 2 == 0 ? hex : "0" + hex;
    }

    /**
     * 将修正长度(奇数长度前面补0)后的16进制字符串转为字节数组
     *
     * @param hex 16进制字符串
     * @return byte[]
     */
    private static byte[] getBytes(String hex) {

        final char[] cs = hex.toUpperCase().toCharArray();
        byte[] bs = new byte[cs.length / 2];

        for (int i = 0, k = 0, L = bs.length; i < L; ++i, k += 2) {
            bs[i] = (byte) (HEX.indexOf(cs[k]) << 4 | HEX.indexOf(cs[k + 1]));
        }
        return bs;
    }

}
