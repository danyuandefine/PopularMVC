/**  
* Title DefaultInvokeTimesManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import com.danyuanblog.framework.popularmvc.ApplicationManager;
import com.danyuanblog.framework.popularmvc.InvokeApiLogManager;
import com.danyuanblog.framework.popularmvc.InvokeTimesManager;

public class DefaultInvokeTimesManagerImpl implements InvokeTimesManager {

	/**
	 * @author danyuan
	 */
	@Override
	public void setInvokeApiLogManager(InvokeApiLogManager invokeApiLogManager) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void setApplicationManager(ApplicationManager applicationManager) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkClientIpInvokeTimes(String clientIp) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppTotalInvokeTimes(String appId) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppUserTotalInvokeTimes(String appId, String userId) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppUserSessionTotalInvokeTimes(String appId,
			String sessionId) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppInvokeTimes(String appId, String apiName) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppUserInvokeTimes(String appId, String userId,
			String apiName) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppUserSessionInvokeTimes(String appId, String sessionId,
			String apiName) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppTimeSectionInvokeTimes(String appId, String apiName) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkAppUserTimeSectionInvokeTimes(String appId, String userId,
			String apiName) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void checkClientIpTimeSectionInvokeTimes(String clientIp) {
	}

}
