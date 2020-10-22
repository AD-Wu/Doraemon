package com.x.doraemon.util;

import com.x.doraemon.bean.New;
import com.x.doraemon.bean.SB;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.x.doraemon.util.ArrayHelper.EMPTY_BYTE;
import static com.x.doraemon.util.Strings.ALL_HEX;
import static com.x.doraemon.util.Strings.HEX;

/**
 * @Desc 转换帮助类
 * @Date 2020/7/29 00:59
 * @Author AD
 */
public class Converts {
    
    // ------------------------ 变量定义 ------------------------
    
    // ------------------------ 构造方法 ------------------------
    public Converts() {}
    
    // ------------------------ 方法定义 ------------------------
    
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
    
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 255).toUpperCase().trim();
        return hex.length() < 2 ? "0" + hex : hex;
    }
    
    public static String bytesToHex(byte[] bs) {
        SB sb = New.sb();
        for (byte b : bs) {
            sb.append(byteToHex(b));
        }
        return sb.toString();
    }
    
    public static String bytesToHexWithDelimiter(byte[] bs, String delimiter) {
        if (ArrayHelper.isEmpty(bs)) {
            return "";
        }
        SB sb = New.sb();
        for (byte b : bs) {
            sb.append(byteToHex(b)).append(delimiter);
        }
        return sb.sub(0, sb.length() - delimiter.length());
    }
    
    public static byte[] hexToBytes(String hex) {
        if (Strings.isNull(hex)) {
            return EMPTY_BYTE;
        }
        char[] cs = hex.toUpperCase().toCharArray();
        byte[] bs = new byte[cs.length / 2];
        for (int i = 0, k = 0, L = bs.length; i < L; ++i, k += 2) {
            bs[i] = (byte) (HEX.indexOf(cs[k]) << 4 | HEX.indexOf(cs[k + 1]));
        }
        return bs;
    }
    
    public static boolean toBoolean(Object value) {
        if (value == null) {return false;}
        String t = Strings.toUpperCase(value);
        return "TRUE".equals(t) || "Y".equals(t) || "YES".equals(t) || ("1".equals(t));
    }
    
    public static boolean toBoolean(Object value, boolean defaultValue) {
        if (value == null) {return defaultValue;}
        String t = Strings.toUpperCase(value);
        return "TRUE".equals(t) || "Y".equals(t) || "YES".equals(t) || ("1".equals(t)) || defaultValue;
    }
    
    public static boolean toBoolean(byte[] bs) {
        return bs[0] != 0;
    }
    /**
     * 将对象转为10进制int数值，转换出错时默认返回0
     *
     * @param value 转换对象
     *
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
     *
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
     *
     * @return
     */
    public static int toInt(Object value, int defaultValue, int radix) {
        if (value == null) {
            return defaultValue;
        } else if (!value.getClass().equals(Integer.TYPE) && !(value instanceof Integer)) {
            if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
                String s = value.toString();
                if (Strings.isNull(s)) {
                    return defaultValue;
                } else {
                    try {
                        return Integer.parseInt(s, radix);
                    } catch (Exception e) {
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
        } else if (!(value instanceof Byte) && !(value instanceof Integer)) {
            String s = value.toString();
            if (Strings.isNull(s)) {
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
        } else if (!value.getClass().equals(Short.TYPE) && !(value instanceof Short)) {
            if (!value.getClass().equals(Integer.TYPE) && !(value instanceof Integer)) {
                if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
                    String s = value.toString();
                    if (Strings.isNull(s)) {
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
        } else if (!value.getClass().equals(Long.TYPE) && !(value instanceof Long)) {
            String s = value.toString();
            if (Strings.isNull(s)) {
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
        } else if (!value.getClass().equals(Float.TYPE) && !(value instanceof Float)) {
            if (value instanceof Double) {
                return ((Double) value).floatValue();
            } else {
                String s = value.toString();
                if (Strings.isNull(s)) {
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
        } else if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
            String s = value.toString();
            if (Strings.isNull(s)) {
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
    
    public static BigDecimal toBigDecimal(Object value) {
        return toBigDecimal(value, (BigDecimal) null);
    }
    
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        return value == null ? defaultValue : new BigDecimal(value.toString());
    }
    
    // /**
    //  * 16进制字符串转为 byte[],如果包含其它字符(空格除外)，将返回空数组
    //  *
    //  * @param hex 16进制字符串
    //  *
    //  * @return byte[]
    //  */
    // public static byte[] hexToBytes(String hex) {
    //     String s = Strings.removeSpaces(hex);
    //     return onlyHex(s) ? getBytes(fixHex(s)) : EMPTY_BYTE;
    // }
    
    public static byte[] objectToBytes(Serializable serial) {
        if (serial == null) {
            return EMPTY_BYTE;
        } else {
            try (ByteArrayOutputStream bsOut = new ByteArrayOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(bsOut)) {
                out.writeObject(serial);
                out.flush();
                out.close();
                return bsOut.toByteArray();
            } catch (IOException e) {
                Logs.getLogger(Converts.class).error(Strings.getExceptionTrace(e));
                return EMPTY_BYTE;
            }
        }
    }
    
    // ------------------------ 私有方法 ------------------------
    
    /**
     * 判断是否只包含16进制字符串
     *
     * @param check 需检查字符串
     *
     * @return boolean
     *
     * @author AD
     * @date 2018-12-22 18:37
     */
    private static boolean onlyHex(String check) {
        for (char c : check.toCharArray()) {
            if (ALL_HEX.indexOf(c) == -1) return false;
        }
        return true;
    }
    
    /**
     * 修正16进制字符串长度，如：A --> 0A
     *
     * @param hex 需修正的16进制字符串
     *
     * @return
     */
    private static String fixHex(String hex) {
        return hex.length() % 2 == 0 ? hex : "0" + hex;
    }
    
    /**
     * 将修正长度(奇数长度前面补0)后的16进制字符串转为字节数组
     *
     * @param hex 16进制字符串
     *
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
