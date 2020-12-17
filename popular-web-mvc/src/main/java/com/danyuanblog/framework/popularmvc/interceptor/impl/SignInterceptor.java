/**  
* Title SignInterceptor.java  
* Description  校验接口请求数字签名拦截器
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danyuanblog.framework.popularmvc.SecretManager;
import com.danyuanblog.framework.popularmvc.SignManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.dto.SecretInfo;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.interceptor.AbstractApiMethodInterceptor;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;

@Service
@Slf4j
public class SignInterceptor extends AbstractApiMethodInterceptor {

	@Autowired
	private SignManager signManager;
	
	@Autowired
	private SecretManager secretManager;
	
	@Autowired
	private SystemParameterRenameProperties systemParameterProperties;
	/**
	 * @author danyuan
	 */
	@Override
	public void preInvoke(List<ApiRequestParameter> requestParams, Method method,
			Class<?> targetClass) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("开始校验接口数字签名信息！");
		}
		SecretInfo secret = secretManager.load(RequestContext.getContext().getChannelId(), 				
				RequestContext.getContext().getAppId(), 
				RequestContext.getContext().getSecretId(), 
				RequestContext.getContext().getUserId(), 
				RequestContext.getContext().getSessionId());
		if(RequestContext.getContext().getApiRestrictions().getRequestSign() 
				&& secret != null){
			
			//检查接口数字签名
			String sign = RequestContext.getContext().getSign();			
			String timestamp = RequestContext.getContext().getTimestamp();
			if(!signManager.checkSign(sign, secret.getSecret(), timestamp, requestParams)){
				throw new BusinessException(ErrorCodes.ERROR_SIGN).setParam(systemParameterProperties.getSign(), sign);
			}
		}else if(RequestContext.getContext().getApiRestrictions().getRequestSign()){
			log.warn("渠道和应用的验签秘钥配置均为空，无法进行进行验签，请先配置!");
			throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam("secret_key");
		}
	}

	/**
	 * @author danyuan
	 */
	@Override
	public Object afterInvoke(List<ApiRequestParameter> requestParams, Object resp,
			Method method, Class<?> targetClass) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("开始检查并生成返回响应数据的数字签名信息！");
		}
		SecretInfo secret = secretManager.load(RequestContext.getContext().getChannelId(), 				
				RequestContext.getContext().getAppId(), 
				RequestContext.getContext().getSecretId(), 
				RequestContext.getContext().getUserId(), 
				RequestContext.getContext().getSessionId());
		if(RequestContext.getContext().getApiRestrictions().getResponseSign()
				&& secret != null){
			Long timestamp = System.currentTimeMillis();
			ApiRequestParameter param = new ApiRequestParameter();
			param.setParam(resp);
			String sign = signManager.sign(secret.getSecret(), String.valueOf(timestamp), Arrays.asList(param));
			DefaultResponseWrapper<?> responseWrapper = RequestContext.getContext().getResponseWrapper();
			if(responseWrapper == null){
				responseWrapper = new DefaultResponseWrapper<>();
			}
			responseWrapper.setSign(sign).setTimestamp(timestamp);
			RequestContext.getContext().setResponseWrapper(responseWrapper);
		}else if(RequestContext.getContext().getApiRestrictions().getResponseSign()){
			log.warn("渠道和应用的验签秘钥配置均为空，无法进行进行验签，请先配置!");
			throw new BusinessException(ErrorCodes.UNKOWN_ERROR).setParam("渠道和应用的验签秘钥配置均为空，无法进行进行验签，请先配置!");
		}
		return resp;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public int order() {
		return 0;
	}

}
