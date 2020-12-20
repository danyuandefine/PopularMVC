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
import com.danyuanblog.framework.popularmvc.consts.CacheExpireMode;
import com.danyuanblog.framework.popularmvc.dto.CacheManagerState;

public class DefaultCacheManagerImpl implements CacheManager {

	/**
	 * @author danyuan
	 */
	@Override
	public void set(String key, Object value, Long expireSeconds) {
		this.set(key, value, expireSeconds, CacheExpireMode.EXPIRE_AFTER_WRITE);	
	}

	@Override
	public void set(String key, Object value, Long expireSeconds, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			LocalWriteCacheContainer.set(key, value, expireSeconds);
		}else{
			LocalReadCacheContainer.set(key, value, expireSeconds);
		}		
	}

	@Override
	public void set(String key, Object value, Long expireSeconds, Long oldExpireSeconds, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			LocalWriteCacheContainer.set(key, value, expireSeconds, oldExpireSeconds);
		}else{
			LocalReadCacheContainer.set(key, value, expireSeconds, oldExpireSeconds);
		}		
	}
	/**
	 * @author danyuan
	 */
	@Override
	public boolean exists(String key) {
		return this.exists(key, CacheExpireMode.EXPIRE_AFTER_WRITE);		
	}
	
	@Override
	public boolean exists(String key, Long expireSeconds) {
		return this.exists(key, expireSeconds, CacheExpireMode.EXPIRE_AFTER_WRITE);		
	}
	
	@Override
	public boolean exists(String key, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			return LocalWriteCacheContainer.exists(key);
		}else{
			return LocalReadCacheContainer.exists(key);
		}
		
	}
	
	@Override
	public boolean exists(String key, Long expireSeconds, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			return LocalWriteCacheContainer.exists(key, expireSeconds);
		}else{
			return LocalReadCacheContainer.exists(key, expireSeconds);
		}
		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public <T> T get(String key, Class<T> type, Long expireSeconds) {
		return get(key, type, expireSeconds, CacheExpireMode.EXPIRE_AFTER_WRITE);
	}
	
	@Override
	public <T> T get(String key, Class<T> type) {
		return get(key, type, CacheExpireMode.EXPIRE_AFTER_WRITE);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> type, CacheExpireMode mode) {
		Object content = null;
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			content = LocalWriteCacheContainer.get(key);
		}else{
			content = LocalReadCacheContainer.get(key);
		}
		if(content != null && content.getClass().isAssignableFrom(type)){
			return (T)content;
		}
		return null;
	}
	
	@Override
	public Object get(String key, Long expireSeconds, CacheExpireMode mode){
		Object content = null;
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			content = LocalWriteCacheContainer.get(key, expireSeconds);
		}else{
			content = LocalReadCacheContainer.get(key, expireSeconds);
		}
		return content;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> type, Long expireSeconds, CacheExpireMode mode) {
		Object content = get(key, expireSeconds, mode);
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
		this.setExpire(key, expireSeconds, CacheExpireMode.EXPIRE_AFTER_WRITE);		
	}
	
	@Override
	public void setExpire(String key, Long expireSeconds, Long oldExpireSeconds) {
		this.setExpire(key, expireSeconds, oldExpireSeconds, CacheExpireMode.EXPIRE_AFTER_WRITE);		
	}
	
	@Override
	public void setExpire(String key, Long expireSeconds, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			LocalWriteCacheContainer.setExpire(key, expireSeconds);
		}else{
			LocalReadCacheContainer.setExpire(key, expireSeconds);
		}		
	}
	
	@Override
	public void setExpire(String key, Long expireSeconds, Long oldExpireSeconds, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			LocalWriteCacheContainer.setExpire(key, expireSeconds, oldExpireSeconds);
		}else{
			LocalReadCacheContainer.setExpire(key, expireSeconds, oldExpireSeconds);
		}		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void remove(String key) {
		this.remove(key, CacheExpireMode.EXPIRE_AFTER_WRITE);		
	}
	
	@Override
	public void remove(String key, Long expireSeconds) {
		this.remove(key, expireSeconds, CacheExpireMode.EXPIRE_AFTER_WRITE);		
	}
	
	@Override
	public void remove(String key, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			LocalWriteCacheContainer.remove(key);
		}else{
			LocalReadCacheContainer.remove(key);
		}
		
	}
	@Override
	public void remove(String key, Long expireSeconds, CacheExpireMode mode) {
		if(CacheExpireMode.EXPIRE_AFTER_WRITE.equals(mode)){
			LocalWriteCacheContainer.remove(key, expireSeconds);
		}else{
			LocalReadCacheContainer.remove(key, expireSeconds);
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

	@Override
	public void clear(Long expireSeconds) {
		LocalWriteCacheContainer.clear(expireSeconds);
		LocalReadCacheContainer.clear(expireSeconds);
	}
	
	/**
	 * @author danyuan
	 */
	@Override
	public List<CacheManagerState> stats() {
		return null;
	}

}
