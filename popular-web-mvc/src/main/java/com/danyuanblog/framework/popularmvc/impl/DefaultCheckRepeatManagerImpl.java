/**  
* Title DefaultCheckRepeatManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import lombok.Setter;

import com.danyuanblog.framework.popularmvc.CacheManager;
import com.danyuanblog.framework.popularmvc.CheckRepeatManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.dto.UniqueToken;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.properties.PopularMvcConfig;

@Setter
public class DefaultCheckRepeatManagerImpl implements CheckRepeatManager {

	private PopularMvcConfig popularMvcConfig;
	
	private CacheManager cacheManager;

	/**
	 * @author danyuan
	 */
	@Override
	public void check(String key) {
		UniqueToken token = cacheManager.get(key, UniqueToken.class);
		if(token != null){
			if(token.isSubmit()){
				//数据已提交过，请勿重复提交
				throw new BusinessException(ErrorCodes.UNIQUE_TOKEN_USED);
			}else{
				//数据正在处理中，请勿重复提交
				throw new BusinessException(ErrorCodes.UNIQUE_TOKEN_USING);
			}
		}else{
			//保存token
			token = new UniqueToken();
			token.setCrtTime(System.currentTimeMillis());
			token.setSubmit(false);
			cacheManager.set(key, token, popularMvcConfig.getNoSubmitRepeatTimeoutSeconds());
		}
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void release(String key) {
		cacheManager.remove(key);	
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void used(String key) {
		UniqueToken token = cacheManager.get(key, UniqueToken.class);
		if(token != null && !token.isSubmit()){
			token.setSubmit(true);
			cacheManager.set(key, token, popularMvcConfig.getNoSubmitRepeatTimeoutSeconds());
		}	
	}

}
