/**  
* Title AESDataEncryptHandler.java  
* Description  默认采用渠道对称秘钥进行AES128加解密
* @author danyuan
* @date Nov 15, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.encrypt.impl;

import lombok.Setter;
import lombok.experimental.Accessors;

import com.danyuanblog.framework.popularmvc.SecretManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.SecretInfo;
import com.danyuanblog.framework.popularmvc.encrypt.DataEncryptHandler;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.utils.EncryptUtils;

@Setter
@Accessors(chain = true)
public class AESDataEncryptHandler implements DataEncryptHandler {

	private SecretManager secretManager;
	/**
	 * @author danyuan
	 * @throws Exception 
	 */
	@Override
	public String encrypt(String appId, String channelId, String userId, String content) throws Throwable {
		SecretInfo secret = secretManager.load(RequestContext.getContext().getChannelId(), 				
				RequestContext.getContext().getAppId(), 
				RequestContext.getContext().getSecretId(), 
				RequestContext.getContext().getUserId(), 
				RequestContext.getContext().getSessionId());
		if(secret != null){
			return EncryptUtils.encodeAES128(secret.getSecret(), content);
		}
		throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam("secret_key");
	}

	/**
	 * @author danyuan
	 */
	@Override
	public String decrypt(String appId, String channelId, String userId, String content) throws Throwable {
		SecretInfo secret = secretManager.load(RequestContext.getContext().getChannelId(), 				
				RequestContext.getContext().getAppId(), 
				RequestContext.getContext().getSecretId(), 
				RequestContext.getContext().getUserId(), 
				RequestContext.getContext().getSessionId());
		if(secret != null){
			return EncryptUtils.decodeAES128(secret.getSecret(), content);
		}		
		throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam("secret_key");
	}

}
