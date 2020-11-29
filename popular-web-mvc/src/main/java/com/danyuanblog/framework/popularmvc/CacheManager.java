/**  
* Title CacheManager.java  
* Description  缓存管理器
* 用于服务内数据缓存的存取
* @author danyuan
* @date Nov 13, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc;

import com.danyuanblog.framework.popularmvc.dto.CacheManagerState;

public interface CacheManager {
	void set(String key, Object value, Integer expireSeconds);
	
	<T> T get(String key, Class<T> type);
	
	void setExpire(String key, Integer expireSeconds);
	
	void remove(String key);
	/**
	 * 缓存管理器统计数据
	 * @author danyuan
	 */
	CacheManagerState stats();
	
}
