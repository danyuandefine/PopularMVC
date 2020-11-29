package com.danyuanblog.framework.popularmvc.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.springframework.util.StringUtils;

import java.util.Map;

public class CacheUtil {
    private static final long TIME_OUT = 10;
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(TIME_OUT, java.util.concurrent.TimeUnit.MINUTES)
            .build();

    public static void save(Map<String,String> map){
        map.forEach((key,value)->{
            if(StringUtils.isEmpty(value)){
                cache.put(key,"");
            }else {
                cache.put(key,value);
            }
        });
    }
    
    public static void save(String key, String value){
    	cache.put(key,value);
    }
    
    public static String get(String key){
        return cache.getIfPresent(key);
    }
    
    public static boolean isPresent(String key){
    	return get(key) != null;
    }
}
