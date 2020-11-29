/**  
* Title ApplicationManager.java  
* Description  渠道、应用信息管理器
* @author danyuan
* @date Nov 7, 2018
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc;

import com.danyuanblog.framework.popularmvc.dto.ApiPermissionDto;

public interface ApplicationManager {	
	/**
	 * 校验渠道、应用信息、客户端类型、版本是否合法
	 * @author danyuan
	 */
	void validate(String channelId, String appId, String clientId, String versionCode);
	/**
	 * 获取渠道访问权限配置信息
	 * @author danyuan
	 */
	ApiPermissionDto getChannelPermission(String channelId);
	/**
	 * 获取应用访问权限配置信息
	 * @author danyuan
	 */
	ApiPermissionDto getAppPermission(String channelId);	
	/**
	 * 获取某版本客户端类型访问权限配置信息
	 * @author danyuan
	 */
	ApiPermissionDto getClientPermission(String channelId, String versionCode);
	
}
