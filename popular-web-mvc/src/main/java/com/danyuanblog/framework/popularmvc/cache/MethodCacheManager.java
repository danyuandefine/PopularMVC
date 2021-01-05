/**  
* Title MethodCacheManager.java  
* Description  
* @author danyuan
* @date Dec 19, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.danyuanblog.framework.popularmvc.CacheManager;
import com.danyuanblog.framework.popularmvc.consts.CacheExpireMode;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.utils.BeanPropertyUtil;
import com.danyuanblog.framework.popularmvc.utils.MethdInvokeUtil;
import com.danyuanblog.framework.popularmvc.utils.StringUtils;

@Service
@Slf4j
public class MethodCacheManager {
	
	public String getCacheKey(String prefix, String keyId, String[] parameters, Object [] args, Class<?> targetClass, Method method){
		//先提取缓存前缀
		if(StringUtils.isBlank(prefix)){
			//自动生成缓存前缀
			StringBuffer buffer = new StringBuffer(targetClass.getSimpleName());
			buffer.append(":")
			.append(method.getName());
			prefix = buffer.toString();
		}
		//参数化key
		if(StringUtils.isBlank(keyId)){
			//方法级缓存，直接返回前缀即可
			return prefix;
		}
		//提取参数信息
		Map<String, String> nameValueMap = new HashMap<>();
		int i = 0;
		String paramName = "";
		for(Object param : args){
			paramName = MethdInvokeUtil.getBaseTypeParamName(method.getParameterAnnotations()[i]);
			if(StringUtils.isBlank(paramName)){
				if(parameters != null){
					paramName = parameters[i];
				}else{
					paramName = "p"+i;
				}
			}
			nameValueMap.putAll(BeanPropertyUtil.objToStringMap(param, paramName));		
			i++;
		}
		String id = nameValueMap.get(keyId);
		if(StringUtils.isBlank(id)){
			log.warn("缓存参数配置错误，未找到参数信息:[{}]", keyId);
			throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam("参数化缓存配置错误:"+keyId);
		}
		StringBuffer key = new StringBuffer(prefix).append(":").append(id);
		return key.toString();
	}
	
	public Object getResult(String key, CacheManager cacheManager, Long expireSeconds, CacheExpireMode expireMode){		
		
		return cacheManager.get(key, expireSeconds, expireMode);
	}
	
	public void cacheResult(String key ,Object result, CacheManager cacheManager, Long expireSeconds, CacheExpireMode expireMode){
		
		cacheManager.set(key, result, expireSeconds, expireSeconds, expireMode);
	}
	
	public void remove(String key , CacheManager cacheManager, Long expireSeconds, CacheExpireMode expireMode){
		
		cacheManager.remove(key, expireSeconds, expireMode);
	}
	
}
