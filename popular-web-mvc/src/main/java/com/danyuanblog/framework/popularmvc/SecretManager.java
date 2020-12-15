/**  
* Title SecretManager.java  
* Description  秘钥管理器
* @author danyuan
* @date Dec 15, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc;

import com.danyuanblog.framework.popularmvc.dto.SecretInfo;

public interface SecretManager {

	/**
	 * 加载秘钥信息
	 * @author danyuan
	 */
	SecretInfo load(String channelId, String appId, String secretId, String userId, String sessionId);
	
}
