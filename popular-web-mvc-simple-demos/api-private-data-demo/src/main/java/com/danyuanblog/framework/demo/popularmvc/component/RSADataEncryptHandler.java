/**  
* Title RSADataEncryptHandler.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danyuanblog.framework.demo.popularmvc.utils.RSAEncryptUtil;
import com.danyuanblog.framework.popularmvc.SecretManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.SecretInfo;
import com.danyuanblog.framework.popularmvc.encrypt.DataEncryptHandler;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
@Component
public class RSADataEncryptHandler implements DataEncryptHandler {

	@Autowired
	private SecretManager secretManager;
	/**
	 * @author danyuan
	 */
	@Override
	public String encrypt(String appId, String channelId, String userId,
			String content) throws Throwable {
		SecretInfo secret = secretManager.load(RequestContext.getContext().getChannelId(), 				
				RequestContext.getContext().getAppId(), 
				RequestContext.getContext().getSecretId(), 
				RequestContext.getContext().getUserId(), 
				RequestContext.getContext().getSessionId());
		if(secret != null){
			return RSAEncryptUtil.encrypt(content, secret.getKeyPair().getPublicSecret());
		}
		throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam("secret_key");
	}

	/**
	 * @author danyuan
	 */
	@Override
	public String decrypt(String appId, String channelId, String userId,
			String content) throws Throwable {
		SecretInfo secret = secretManager.load(RequestContext.getContext().getChannelId(), 				
				RequestContext.getContext().getAppId(), 
				RequestContext.getContext().getSecretId(), 
				RequestContext.getContext().getUserId(), 
				RequestContext.getContext().getSessionId());
		if(secret != null){
			return RSAEncryptUtil.decrypt(content, secret.getKeyPair().getPrivateSecret());
		}
		throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam("secret_key");
	}

}
