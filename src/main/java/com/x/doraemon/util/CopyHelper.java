package com.x.doraemon.util;

import com.google.gson.Gson;

import java.io.*;

public final class CopyHelper {
    
    // ------------------------ 变量定义 ------------------------
    
    // ------------------------ 构造方法 ------------------------
    private CopyHelper() {}
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 深度拷贝对象（和拷贝对象相关的对象都必须实现Serializable接口）
     *
     * @param serializable 拷贝对象（可序列化）
     *
     * @return
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T copy(T serializable) throws IOException, ClassNotFoundException {
        // 获取对象字节流
        byte[] bytes = serialize(serializable);
        // 反序列化拷贝
        return deserialize(bytes);
    }
    
    /**
     * 采用json的方式拷贝一个对象，效率慢，对象无需实现序列化接口
     *
     * @param clone
     * @param <T>
     *
     * @return
     */
    public static <T> T copy(T clone) {
        Gson gson = new Gson();
        return (T) gson.fromJson(gson.toJson(clone), clone.getClass());
        
    }
    
    // ------------------------ 私有方法 ------------------------
    
    private static byte[] serialize(Serializable serializable) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(serializable);
            oos.flush();
            return bos.toByteArray();
        }
    }
    
    private static <T extends Serializable> T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
             ObjectInputStream oin = new ObjectInputStream(bin);) {
            return (T) oin.readObject();
        }
    }
    
}