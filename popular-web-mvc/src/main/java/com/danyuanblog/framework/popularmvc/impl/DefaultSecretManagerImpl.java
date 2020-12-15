/**  
* Title DefaultSecretManagerImpl.java  
* Description  通过读取配置文件信息进行渠道应用安全控制,暂时支持渠道、应用级别安全控制
* @author danyuan
* @date Dec 15, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.util.StringUtils;

import com.danyuanblog.framework.popularmvc.SecretManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.ApiPermissionDto;
import com.danyuanblog.framework.popularmvc.dto.SecretInfo;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.properties.ChannelConfigProperties;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultSecretManagerImpl implements SecretManager {

	private ChannelConfigProperties channelConfigProperties;
	
	private SystemParameterRenameProperties systemParameterProperties;

	/**
	 * @author danyuan
	 */
	@Override
	public SecretInfo load(String channelId, String appId, String secretId,
			String userId, String sessionId) {
		if(!StringUtils.isEmpty(channelId)
				&& (channelConfigProperties.getChannels() != null)
				){//渠道配置存在
			if(!channelConfigProperties.getChannels().containsKey(channelId)){
				throw new BusinessException(ErrorCodes.INVALID_PARAM).setParam(systemParameterProperties.getChannelId(), channelId);
			}
			Map<String, ApiPermissionDto> apps = channelConfigProperties.getChannels().get(channelId);
			
			ApiPermissionDto permission = apps.get(RequestContext.getContext().getAppId());
			if(permission == null){
				//如果不存在单个应用的配置信息，则使用渠道默认配置
				permission = apps.get(ChannelConfigProperties.DEFAULT_APP);
			}
			if(permission != null){
				SecretInfo secret = new SecretInfo();
				secret.setKeyPair(permission.getKeyPair());
				secret.setSecret(permission.getSecret());
				return secret;
			}			
		}
		return null;
	}
	

}
