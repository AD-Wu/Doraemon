package com.x.doraemon.collection;

/**
 * @Desc 数组游标，迭代器模式
 * @Date 2020/11/14 18:01
 * @Author AD
 */
public class ArrayCursor<T> {

    private T[] array;

    private int index;

    private final int count;

    public ArrayCursor(T[] array) {
        this(array, 0);
    }

    public ArrayCursor(T[] array, int index) {
        this.array = array;
        this.index = index;
        this.count = array.length;
    }

    public boolean hasNext() {
        return array != null && index < count;
    }

    public synchronized T next() {
        return hasNext() ? array[index++] : null;
    }



}
