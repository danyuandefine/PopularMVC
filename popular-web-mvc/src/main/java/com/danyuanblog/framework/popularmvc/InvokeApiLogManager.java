/**  
* Title InvokeApiLogManager.java  
* Description  接口调用日志管理器
* @author danyuan
* @date Oct 31, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc;

import java.sql.Date;

import com.danyuanblog.framework.popularmvc.dto.ApiInvokeLog;

public interface InvokeApiLogManager {
	/**
	 * 保存接口调用日志
	 * @author danyuan
	 */
	void save(ApiInvokeLog log);
	/**
	 * 统计接口调用次数
	 * @author danyuan
	 */
	long count(String apiName, String appId, String userId, String sessionId, String clientIp, Date startTime, Date endTime);
	
}
