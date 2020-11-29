/**  
* Title DefaultApplicationManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import com.danyuanblog.framework.popularmvc.ApplicationManager;
import com.danyuanblog.framework.popularmvc.dto.ApiPermissionDto;

public class DefaultApplicationManagerImpl implements ApplicationManager {

	/**
	 * @author danyuan
	 */
	@Override
	public void validate(String channelId, String appId, String clientId,
			String versionCode) {
	}

	/**
	 * @author danyuan
	 */
	@Override
	public ApiPermissionDto getChannelPermission(String channelId) {
		return null;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public ApiPermissionDto getAppPermission(String channelId) {
		return null;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public ApiPermissionDto getClientPermission(String channelId,
			String versionCode) {
		return null;
	}

}
