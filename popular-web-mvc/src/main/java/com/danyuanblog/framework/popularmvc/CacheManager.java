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

import java.util.List;

import com.danyuanblog.framework.popularmvc.dto.CacheManagerState;

public interface CacheManager {
	/**
	 * 保存一个缓存，并设置过期时间,默认为写后超时模式
	 * @author danyuan
	 */
	void set(String key, Object value, Long expireSeconds);
	/**
	 * 保存一个缓存，并设置过期时间
	 * @author danyuan
	 */
	void set(String key, Object value, Long expireSeconds, boolean refreshAfterWrite);
	/**
	 * 是否存在该缓存,默认为写后超时模式
	 * @author danyuan
	 */
	boolean exists(String key);
	/**
	 * 是否存在该缓存
	 * @author danyuan
	 */
	boolean exists(String key, boolean refreshAfterWrite);
	/**
	 * 获取一个缓存,默认为写后超时模式
	 * @author danyuan
	 */
	<T> T get(String key, Class<T> type);
	/**
	 * 获取一个缓存
	 * @author danyuan
	 */
	<T> T get(String key, Class<T> type, boolean refreshAfterWrite);
	/**
	 * 重新设置一个缓存的超时时间,默认为写后超时模式
	 * @author danyuan
	 */
	void setExpire(String key, Long expireSeconds);
	/**
	 * 重新设置一个缓存的超时时间
	 * @author danyuan
	 */
	void setExpire(String key, Long expireSeconds, boolean refreshAfterWrite);
	/**
	 * 删除缓存,默认为写后超时模式
	 * @author danyuan
	 */
	void remove(String key);
	/**
	 * 删除缓存
	 * @author danyuan
	 */
	void remove(String key, boolean refreshAfterWrite);
	/**
	 * 清空所有缓存
	 * @author danyuan
	 */
	void clear();
	/**
	 * 缓存管理器统计数据
	 * @author danyuan
	 */
	List<CacheManagerState> stats();
	
}
