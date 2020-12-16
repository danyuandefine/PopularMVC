/**  
* Title LocalCacheContainer.java  
* Description  本地缓存管理器，以失效时间为key进行管理
* @author danyuan
* @date Dec 17, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class LocalCacheContainer {
	/**
	 * 单个缓存管理器最多能缓存多少key
	 */
	private static final Long MAX_CACHE_SIZE = 100000L;
	/**
	 * 最多允许开启多少个缓存
	 */
	private static final Long MAX_CACHE_NUM = 20L;
	/**
	 * 默认缓存管理器超时时间,3分钟
	 */
	private static final Long DEFAULT_CACHE_EXPIRE_SECONDS = 180L;
	
	private final static ConcurrentHashMap<String, Cache<String, Object>> cacheMap = new ConcurrentHashMap<>();
	private static Lock lock = new ReentrantLock();
	
	static {//初始化默认缓存管理器
		Cache<String, Object> cacheContainer = CacheBuilder.newBuilder()
                .maximumSize(MAX_CACHE_SIZE)
                .expireAfterWrite(DEFAULT_CACHE_EXPIRE_SECONDS, TimeUnit.SECONDS)//最后一次写入后的一段时间移出
                .recordStats()//开启统计功能
                .build();
		cacheMap.put(String.valueOf(DEFAULT_CACHE_EXPIRE_SECONDS), cacheContainer);
	};
	
	private static Cache<String, Object> getCache(Long expireSeconds){
		Cache<String, Object> cacheContainer = null;
        if (expireSeconds == null) {
            return cacheContainer;
        }

        String mapKey = String.valueOf(expireSeconds);

        if (cacheMap.containsKey(mapKey) == true) {
            cacheContainer = cacheMap.get(mapKey);
            return cacheContainer;
        }

        if(cacheMap.size() >= MAX_CACHE_NUM){
        	//缓存管理器已经大于上限值，返回默认时效的缓存管理器
        	return cacheMap.get(String.valueOf(DEFAULT_CACHE_EXPIRE_SECONDS));
        }
        
        try {
            lock.lock();            
            cacheContainer = CacheBuilder.newBuilder()
                    .maximumSize(MAX_CACHE_SIZE)
                    .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)//最后一次写入后的一段时间移出
                    //.expireAfterAccess(AppConst.CACHE_MINUTE, TimeUnit.MILLISECONDS) //最后一次访问后的一段时间移出
                    .recordStats()//开启统计功能
                    .build();

            cacheMap.put(mapKey, cacheContainer);

        } finally {
            lock.unlock();
        }

        return cacheContainer;
	}
	
	public static void set(String key, Object content, Long seconds){
		try {
            lock.lock(); 
            //删除旧key
            remove(key);
            //保存新key
    		Cache<String, Object> cacheContainer = getCache(seconds);
    		cacheContainer.put(key, content);
		} finally {
            lock.unlock();
        }		
	}
	
	public static void setExpire(String key, Long seconds){
		Object content = get(key);
		if(content == null){
			return ;
		}
		try {
            lock.lock();             
            //删除旧key
            remove(key);
            //保存新key
    		Cache<String, Object> cacheContainer = getCache(seconds);
    		cacheContainer.put(key, content);
		} finally {
            lock.unlock();
        }		
	}
	
	public static Object get(String key){
		for(Cache<String,Object> cache : cacheMap.values()){
			Object content = cache.getIfPresent(key);
			return content;
		}
		return null;
	}
	
	public static void remove(String key){
		try {
            lock.lock(); 
			String existsLocate = locate(key);
			if(existsLocate != null){
				Cache<String, Object> cacheContainer = getCache(Long.valueOf(existsLocate));
				cacheContainer.invalidate(key);
			}
		} finally {
            lock.unlock();
        }		
	}
	
	public static void clear(Long expireSeconds){
		try {
            lock.lock(); 
            if(expireSeconds == null){
    			//清空所有缓存
    			cacheMap.forEach((seconds, cache) -> {
    				cache.invalidateAll();
    			});
    		}else{
    			Cache<String, Object> cacheContainer = getCache(expireSeconds);
    			cacheContainer.invalidateAll();
    		}
		} finally {
            lock.unlock();
        }	
		
	}
	
	public static boolean exists(String key){
		return get(key) != null;
	}
	
	private static String locate(String key){
		for(Map.Entry<String, Cache<String,Object>> entry : cacheMap.entrySet()){
			Object content = entry.getValue().getIfPresent(key);
			if(content != null){
				return entry.getKey();
			}
		}
		return null;
	}
}
