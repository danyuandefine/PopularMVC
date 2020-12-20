/**  
* Title CacheExpireMode.java  
* Description  
* @author danyuan
* @date Dec 19, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.consts;

public enum CacheExpireMode {
	/**
	 * 写入缓存后超时时间后失效
	 */
	EXPIRE_AFTER_WRITE,
	/**
	 * 最后一次读取缓存超时时间后失效
	 */
	EXPIRE_AFTER_REDA;
}
