package com.x.doraemon.util;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * @Desc TODO
 * @Date 2020/10/14 23:42
 * @Author AD
 */
public final class CacheHelper {
    
    public static void main(String[] args) throws Exception {
        // cacheTest();
        loaderCacheTest();
        Thread.currentThread().join();
    }
    
    private static void loaderCacheTest() throws Exception {
        LoadingCache<Integer, Integer> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(5)
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<Integer, Integer>() {
                    @Override
                    public void onRemoval(RemovalNotification<Integer, Integer> notification) {
                        Integer key = notification.getKey();
                        Integer value = notification.getValue();
                        RemovalCause cause = notification.getCause();
                        System.out.println(StringHelper.replace("缓存被移除，key={0},value={1},cause={2}", key, value, cause));
                    }
                })
                .build(new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer integer) throws Exception {
                        System.out.println(StringHelper.replace("加载缓存,key={0},value={1}", integer, 1));
                        return 2;
                    }
                });
        
        loadingCache.put(1, 1);
        for (; ; ) {
            Integer value = loadingCache.get(1);
            System.out.println(StringHelper.replace("当前缓存信息，key={0},value={1}", 1, value));
            TimeUnit.SECONDS.sleep(1);
            if (loadingCache.size() == 0) {
                break;
            }
        }
    }
    
    private static void cacheTest() throws Exception {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(5)
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build();
        cache.put(1, 1);
        cache.put(2, 2);
        
        Integer v;
        if ((v = cache.getIfPresent(2)) != null) {
            System.out.println("存在key=2的值：" + v);
        } else {
            System.out.println("不存在key=2的值");
        }
        if ((v = cache.getIfPresent(3)) != null) {
            System.out.println("存在key=3的值：" + v);
        } else {
            System.out.println("不存在key=3的值");
        }
        
        for (; ; ) {
            Integer one = cache.getIfPresent(1);
            Integer two = cache.getIfPresent(2);
            System.out.println(one + "|" + two + "|" + cache.size());
            TimeUnit.SECONDS.sleep(1);
            if (cache.size() == 0) {
                break;
            }
        }
    }
    
}
