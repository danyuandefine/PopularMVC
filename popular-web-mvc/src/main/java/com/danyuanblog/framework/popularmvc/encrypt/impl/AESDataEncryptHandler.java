/**  
* Title AESDataEncryptHandler.java  
* Description  默认采用渠道对称秘钥进行AES128加解密
* @author danyuan
* @date Nov 15, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.encrypt.impl;

import java.util.Map;

import lombok.Setter;
import lombok.experimental.Accessors;

import org.springframework.util.StringUtils;

import com.danyuanblog.framework.popularmvc.dto.ApiPermissionDto;
import com.danyuanblog.framework.popularmvc.encrypt.DataEncryptHandler;
import com.danyuanblog.framework.popularmvc.properties.ChannelConfigProperties;
import com.danyuanblog.framework.popularmvc.utils.EncryptUtils;

@Setter
@Accessors(chain = true)
public class AESDataEncryptHandler implements DataEncryptHandler {

	private ChannelConfigProperties channelConfigProperties;
	/**
	 * @author danyuan
	 * @throws Exception 
	 */
	@Override
	public String encrypt(String appId, String channelId, String userId, String content) throws Throwable {
		Map<String, ApiPermissionDto> apps = channelConfigProperties.getChannels().get(channelId);
		if(apps != null){
			ApiPermissionDto permission = apps.get(appId);
			if(permission == null){
				//如果不存在单个应用的配置信息，则使用渠道默认配置
				permission = apps.get(ChannelConfigProperties.DEFAULT_APP);
			}
			if(permission != null && !StringUtils.isEmpty(permission.getSecret())){
				return EncryptUtils.encodeAES128(permission.getSecret(), content);
			}
		}		
		return content;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public String decrypt(String appId, String channelId, String userId, String content) throws Throwable {
		Map<String, ApiPermissionDto> apps = channelConfigProperties.getChannels().get(channelId);
		if(apps != null){
			ApiPermissionDto permission = apps.get(appId);
			if(permission == null){
				//如果不存在单个应用的配置信息，则使用渠道默认配置
				permission = apps.get(ChannelConfigProperties.DEFAULT_APP);
			}
			if(permission != null && !StringUtils.isEmpty(permission.getSecret())){
				return EncryptUtils.decodeAES128(permission.getSecret(), content);
			}
		}		
		return content;
	}

}
