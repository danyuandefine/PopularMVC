/**  
* Title DefaultSessionManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import com.danyuanblog.framework.popularmvc.SessionManager;
import com.danyuanblog.framework.popularmvc.dto.Session;

public class DefaultSessionManagerImpl implements SessionManager {

	/**
	 * @author danyuan
	 */
	@Override
	public Session getCurrentSession(boolean createNew) {
		return null;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void persist(Session session) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public Session get(String sessionId) {
		return null;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public boolean exists(String sessionId) {
		return false;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void setExpireSeconds(long seconds) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void refreshAliveTime(String sessionId) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void remove(String sessionId) {
	}

}
