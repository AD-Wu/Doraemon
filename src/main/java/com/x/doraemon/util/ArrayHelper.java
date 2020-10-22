package com.x.doraemon.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Desc TODO
 * @Date 2020/7/29 23:48
 * @Author AD
 */
public final class ArrayHelper {
    
    // ------------------------ 变量定义 ------------------------
    
    /**
     * 空 byte[] 数组
     */
    public static final byte[] EMPTY_BYTE = new byte[0];
    
    /**
     * 空 short[] 数组
     */
    public static final short[] EMPTY_SHORT = new short[0];
    
    /**
     * 空 int[] 数组
     */
    public static final int[] EMPTY_INT = new int[0];
    
    /**
     * 空 long[] 数组
     */
    public static final long[] EMPTY_LONG = new long[0];
    
    /**
     * 空 float[] 数组
     */
    public static final float[] EMPTY_FLOAT = new float[0];
    
    /**
     * 空 double[] 数组
     */
    public static final double[] EMPTY_DOUBLE = new double[0];
    
    /**
     * 空 String[] 数组
     */
    public static final String[] EMPTY_STRING = new String[0];
    
    /**
     * 空 File[] 数组
     */
    public static final File[] EMPTY_FILE = new File[0];
    
    /**
     * 空 Object[] 数组
     */
    public static final Object[] EMPTY = new Object[0];
    
    // ------------------------ 构造方法 ------------------------
    
    /**
     * 构造方法
     */
    private ArrayHelper() {}
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 拷贝字节数组
     *
     * @param data 被拷贝对象
     *
     * @return byte[] 拷贝数据
     */
    public static byte[] copy(byte[] data) {
        if (!isEmpty(data)) {
            byte[] copy = new byte[data.length];
            System.arraycopy(data, 0, copy, 0, copy.length);
            return copy;
        }
        return EMPTY_BYTE;
    }
    
    /**
     * 判断两个数组是否都不为空且长度相等
     *
     * @param os1 数组1
     * @param os2 数组2
     *
     * @return 都不为空且长度相等时为true，否则false
     */
    public static boolean isValid(Object[] os1, Object[] os2) {
        if (!isEmpty(os1) && !isEmpty(os2) && os1.length == os2.length) {
            return true;
        }
        return false;
    }
    
    /**
     * 将两个对象数组进行复制整合
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
    
    /**
     * 将两个int型数组进行复制整合
     * @param first
     * @param second
     * @return
     */
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
    
    /**
     * 将多个对象型数组进行复制整合
     * @param first
     * @param ts
     * @param <T>
     * @return
     */
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
    
    public static <T> boolean isEmpty(Set<T> set) {
        return set == null || set.size() == 0;
    }
    
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }
    
    public static <T> boolean isEmpty(T[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(int[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(byte[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(short[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(long[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(float[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(double[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static boolean isEmpty(char[] arrays) {
        return arrays == null || arrays.length == 0;
    }
    
    public static <T> Set<T> toSet(T[] ts) {
        return Stream.of(ts).collect(Collectors.toSet());
    }
    
    // ------------------------ 私有方法 ------------------------
    
}
