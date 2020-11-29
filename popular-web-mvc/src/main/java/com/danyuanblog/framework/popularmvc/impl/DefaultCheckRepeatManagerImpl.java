/**  
* Title DefaultCheckRepeatManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import com.danyuanblog.framework.popularmvc.CheckRepeatManager;

public class DefaultCheckRepeatManagerImpl implements CheckRepeatManager {

	/**
	 * @author danyuan
	 */
	@Override
	public void setTokenTimeout(int second) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public String generateToken(String key) {
		return null;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void check(String key, String token) {
	}

}
