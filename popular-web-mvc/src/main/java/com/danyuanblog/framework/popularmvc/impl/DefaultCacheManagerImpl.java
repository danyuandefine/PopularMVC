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
import com.danyuanblog.framework.popularmvc.cache.LocalReadCacheContainer;
import com.danyuanblog.framework.popularmvc.cache.LocalWriteCacheContainer;
import com.danyuanblog.framework.popularmvc.dto.CacheManagerState;

public class DefaultCacheManagerImpl implements CacheManager {

	/**
	 * @author danyuan
	 */
	@Override
	public void set(String key, Object value, Long expireSeconds) {
		this.set(key, value, expireSeconds, true);	
	}

	@Override
	public void set(String key, Object value, Long expireSeconds, boolean refreshAfterWrite) {
		if(refreshAfterWrite){
			LocalWriteCacheContainer.set(key, value, expireSeconds);
		}else{
			LocalReadCacheContainer.set(key, value, expireSeconds);
		}		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public boolean exists(String key) {
		return this.exists(key, true);		
	}
	
	@Override
	public boolean exists(String key, boolean refreshAfterWrite) {
		if(refreshAfterWrite){
			return LocalWriteCacheContainer.exists(key);
		}else{
			return LocalReadCacheContainer.exists(key);
		}
		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public <T> T get(String key, Class<T> type) {
		return get(key, type, true);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> type, boolean refreshAfterWrite) {
		Object content = null;
		if(refreshAfterWrite){
			content = LocalWriteCacheContainer.get(key);
		}else{
			content = LocalReadCacheContainer.get(key);
		}
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
		this.setExpire(key, expireSeconds, true);		
	}
	
	@Override
	public void setExpire(String key, Long expireSeconds, boolean refreshAfterWrite) {
		if(refreshAfterWrite){
			LocalWriteCacheContainer.setExpire(key, expireSeconds);
		}else{
			LocalReadCacheContainer.setExpire(key, expireSeconds);
		}		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void remove(String key) {
		this.remove(key, true);		
	}
	
	@Override
	public void remove(String key, boolean refreshAfterWrite) {
		if(refreshAfterWrite){
			LocalWriteCacheContainer.remove(key);
		}else{
			LocalReadCacheContainer.remove(key);
		}
		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void clear() {
		LocalWriteCacheContainer.clear(null);
		LocalReadCacheContainer.clear(null);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public List<CacheManagerState> stats() {
		return null;
	}

}
