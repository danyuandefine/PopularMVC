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
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.danyuanblog.framework.popularmvc.SignManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.dto.ApiPermissionDto;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.interceptor.AbstractApiMethodInterceptor;
import com.danyuanblog.framework.popularmvc.properties.ChannelConfigProperties;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;

@Service
@Slf4j
public class SignInterceptor extends AbstractApiMethodInterceptor {

	@Autowired
	private SignManager signManager;
	
	@Autowired
	private ChannelConfigProperties channelConfigProperties;
	
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
		String channelId = RequestContext.getContext().getChannelId();
		if(RequestContext.getContext().getApiRestrictions().getRequestSign() 
				&& (!StringUtils.isEmpty(channelId)) 
				&& (channelConfigProperties.getChannels() != null)
				){
			if(!channelConfigProperties.getChannels().containsKey(channelId)){
				throw new BusinessException(ErrorCodes.INVALID_PARAM).setParam(systemParameterProperties.getChannelId(), channelId);
			}
			//检查接口数字签名
			String sign = RequestContext.getContext().getSign();			
			String timestamp = RequestContext.getContext().getTimestamp();
			Map<String, ApiPermissionDto> apps = channelConfigProperties.getChannels().get(channelId);
			
			ApiPermissionDto permission = apps.get(RequestContext.getContext().getAppId());
			if(permission == null){
				//如果不存在单个应用的配置信息，则使用渠道默认配置
				permission = apps.get(ChannelConfigProperties.DEFAULT_APP);
			}
			if(permission != null && !StringUtils.isEmpty(permission.getSecret())){
				if(!signManager.checkSign(sign, permission.getSecret(), timestamp, requestParams)){
					throw new BusinessException(ErrorCodes.ERROR_SIGN).setParam(systemParameterProperties.getSign(), sign);
				}
			}else{
				log.warn("渠道[{}]验签配置为空，无法进行进行验签，请先配置!", channelId);
				throw new BusinessException(ErrorCodes.UNKOWN_ERROR).setParam("渠道验签配置为空，无法进行进行验签，请先配置!");
			}
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
		String channelId = RequestContext.getContext().getChannelId();
		if(RequestContext.getContext().getApiRestrictions().getResponseSign()
				&& (!StringUtils.isEmpty(channelId)) 
				&& (channelConfigProperties.getChannels() != null)
				&& (channelConfigProperties.getChannels().containsKey(channelId))){
			//检查接口数字签名			
			Map<String, ApiPermissionDto> apps = channelConfigProperties.getChannels().get(channelId);
			
			ApiPermissionDto permission = apps.get(RequestContext.getContext().getAppId());
			if(permission == null){
				//如果不存在单个应用的配置信息，则使用渠道默认配置
				permission = apps.get(ChannelConfigProperties.DEFAULT_APP);
			}
			if(permission != null && !StringUtils.isEmpty(permission.getSecret())){
				Long timestamp = System.currentTimeMillis();
				ApiRequestParameter param = new ApiRequestParameter();
				param.setParam(resp);
				String sign = signManager.sign(permission.getSecret(), String.valueOf(timestamp), Arrays.asList(param));
				DefaultResponseWrapper<?> responseWrapper = RequestContext.getContext().getResponseWrapper();
				if(responseWrapper == null){
					responseWrapper = new DefaultResponseWrapper<>();
				}
				responseWrapper.setSign(sign).setTimestamp(timestamp);
				RequestContext.getContext().setResponseWrapper(responseWrapper);
			}else{
				log.warn("渠道[{}]验签配置为空，无法进行进行验签，请先配置!", channelId);
				throw new BusinessException(ErrorCodes.UNKOWN_ERROR).setParam("渠道验签配置为空，无法进行进行验签，请先配置!");
			}
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
