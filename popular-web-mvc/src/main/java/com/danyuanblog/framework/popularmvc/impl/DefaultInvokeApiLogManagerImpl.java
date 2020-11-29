/**  
* Title DefaultInvokeApiLogManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import java.sql.Date;

import com.danyuanblog.framework.popularmvc.InvokeApiLogManager;
import com.danyuanblog.framework.popularmvc.dto.ApiInvokeLog;

public class DefaultInvokeApiLogManagerImpl implements InvokeApiLogManager {

	/**
	 * @author danyuan
	 */
	@Override
	public void save(ApiInvokeLog log) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public long count(String apiName, String appId, String userId,
			String sessionId, String clientIp, Date startTime, Date endTime) {
		return 0;
	}

}
