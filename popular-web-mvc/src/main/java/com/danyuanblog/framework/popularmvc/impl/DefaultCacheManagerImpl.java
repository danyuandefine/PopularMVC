/**  
* Title DefaultCacheManagerImpl.java  
* Description  
* @author danyuan
* @date Dec 17, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import java.util.List;

import com.danyuanblog.framework.popularmvc.CacheManager;
import com.danyuanblog.framework.popularmvc.cache.LocalCacheContainer;
import com.danyuanblog.framework.popularmvc.dto.CacheManagerState;

public class DefaultCacheManagerImpl implements CacheManager {

	/**
	 * @author danyuan
	 */
	@Override
	public void set(String key, Object value, Long expireSeconds) {
		LocalCacheContainer.set(key, value, expireSeconds);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public boolean exists(String key) {
		return LocalCacheContainer.exists(key);
	}

	/**
	 * @author danyuan
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> type) {
		Object content = LocalCacheContainer.get(key);
		if(content != null && content.getClass().isAssignableFrom(type)){
			return (T)content;
		}
		return null;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void setExpire(String key, Long expireSeconds) {
		LocalCacheContainer.setExpire(key, expireSeconds);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void remove(String key) {
		LocalCacheContainer.remove(key);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void clear() {
		LocalCacheContainer.clear(null);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public List<CacheManagerState> stats() {
		return null;
	}

}
