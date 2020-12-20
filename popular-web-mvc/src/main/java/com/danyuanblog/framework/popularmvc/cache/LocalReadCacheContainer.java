/**  
* Title LocalReadCacheContainer.java  
* Description  本地缓存管理器，以失效时间为key进行管理
* @author danyuan
* @date Dec 17, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.cache;

import org.springframework.stereotype.Component;

import com.danyuanblog.framework.popularmvc.consts.CacheExpireMode;

@Component
public class LocalReadCacheContainer extends LocalCacheContainer{

	/**
	 * @author danyuan
	 */
	public LocalReadCacheContainer() {
		super(CacheExpireMode.EXPIRE_AFTER_REDA);
	}	
	
}
