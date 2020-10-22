package com.x.doraemon.bean;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.*;

/**
 * 静态工厂，对象创建者
 *
 * @Date 2018-12-19 20:50
 * @Author AD
 */
public final class New {
    
    private New() {}
    
    /**
     * HashMap-非线程安全
     */
    public static <K, V> Map<K, V> map() {
        return new HashMap<K, V>();
    }
    
    public static <K, V> Map<K, V> map(int size) {
        return new HashMap<K, V>(size);
    }
    
    public static <K, V> Map<K, V> linkedMap() {
        return new LinkedHashMap<K, V>();
    }
    
    /**
     * key有序，基于红黑树，非线程安全
     */
    public static <K, V> Map<K, V> treeMap() {
        return new TreeMap<>();
    }
    
    /**
     * 并发Map
     */
    public static <K, V> Map<K, V> concurrentMap() {
        return new ConcurrentHashMap<>();
    }
    
    /**
     * key有序，基于跳表，线程安全
     */
    public static <K, V> Map<K, V> skipListMap() {
        return new ConcurrentSkipListMap<>();
    }
    
    public static <T> Set<T> set() {
        return new HashSet<>();
    }
    
    public static <T> Set<T> set(Set<T> a) {
        return new HashSet<>(a);
    }
    
    public static <T> Set<T> set(int size) {
        return new HashSet<>(size);
    }
    
    /**
     * 并发版-Set
     */
    public static <T> Set<T> syncSet() {
        return new CopyOnWriteArraySet<>();
    }
    
    /**
     * 并发版-Set，基于跳表
     */
    public static <T> Set<T> concurrentSet() {
        return new ConcurrentSkipListSet<>();
    }
    
    public static <T> Set<T> treeSet() {
        return new TreeSet<>();
    }
    
    public static <T> List<T> list() {
        return new ArrayList<>();
    }
    
    public static <T> List<T> list(int size) {
        return new ArrayList<>(size);
    }
    
    /**
     * ArrayList-并发版
     */
    public static <T> List<T> syncList() {
        return new CopyOnWriteArrayList<>();
    }
    
    public static <T> LinkedList<T> linkedList() {
        return new LinkedList<>();
    }
    
    /**
     * LinkedList-普通队列
     */
    public static <T> Queue<T> queue() {
        return new LinkedList<>();
    }
    
    /**
     * 并发队列-基于链表
     */
    public static <T> Queue<T> syncQueue() {
        return new ConcurrentLinkedQueue<>();
    }
    
    /**
     * 并发队列-基于双向链表
     */
    public static <T> Queue<T> syncDeque() {
        return new ConcurrentLinkedDeque<>();
    }
    
    /**
     * 并发-阻塞线程队列-基于数组
     */
    public static <T> ArrayBlockingQueue<T> arrayBlockingQueue(int capacity) {
        return new ArrayBlockingQueue<T>(capacity);
    }
    
    /**
     * 并发-阻塞线程队列-基于链表
     */
    public static <T> LinkedBlockingQueue<T> linkedBlockingQueue(int capacity) {
        return new LinkedBlockingQueue<T>(capacity);
    }
    
    /**
     * 并发-阻塞线程队列-基于双向链表
     */
    public static <T> LinkedBlockingDeque<T> linkedBlockingDeque(int capacity) {
        return new LinkedBlockingDeque<T>(capacity);
    }
    
    /**
     * 线程安全的优先级队列，放进去的元素会被排序，消费时按排序后的顺序消费
     */
    public static <T> PriorityBlockingQueue<T> priorityBlockingQueue() {
        return new PriorityBlockingQueue<T>();
    }
    
    /**
     * 并发-读写成对的队列
     */
    public static <T> SynchronousQueue<T> SynchronousQueue() {
        return new SynchronousQueue<T>();
    }
    
    /**
     * 并发-基于链表的数据交换队列
     */
    public static <T> LinkedTransferQueue<T> linkedTransferQueue() {
        return new LinkedTransferQueue<T>();
    }
    
    /**
     * 延时队列
     */
    public static DelayQueue delayQueue() {
        return new DelayQueue();
    }
    
    public static ByteBuffer buffer(int capacity) {
        return ByteBuffer.allocate(capacity);
    }
    
    public static ByteBuffer buffer(byte[] bs) {
        return ByteBuffer.wrap(bs);
    }
    
    public static ExecutorService fixedThreadPool(int n) {
        return Executors.newFixedThreadPool(n);
    }
    
    public static ExecutorService singleThreadPool() {
        return Executors.newSingleThreadExecutor();
    }
    
    public static ThreadPoolExecutor threadPool() {
        return threadPool(5);
    }
    
    public static ThreadPoolExecutor threadPool(int maxPoolSize) {
        if (maxPoolSize <= 0 || maxPoolSize > 100) {
            maxPoolSize = 100;
        }
        return new ThreadPoolExecutor(0, maxPoolSize, 1, TimeUnit.MINUTES,
                new SynchronousQueue<>(),
                new ThreadPoolExecutor.AbortPolicy());
    }
    
    public static SB sb() {
        return new SB();
    }
    
    public static SB sb(String s) {
        return new SB(s);
    }
    
    public static SB sb(String... ss) {return new SB(ss);}
    
}
