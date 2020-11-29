/**  
* Title ApiLogInterceptor.java  
* Description  会话信息校验拦截器
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor.impl;

import java.lang.reflect.Method;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danyuanblog.framework.popularmvc.SessionManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.dto.Session;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.interceptor.AbstractApiMethodInterceptor;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterProperties;

@Service
@Setter
@Slf4j
public class SessionInterceptor extends AbstractApiMethodInterceptor {
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private SystemParameterProperties systemParameterProperties;

	/**
	 * @author danyuan
	 */
	@Override
	public void preInvoke(List<ApiRequestParameter> requestParams, Method method,
			Class<?> targetClass) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("开始校验接口会话信息！");
		}
		if(RequestContext.getContext().getApiRestrictions().getSession()){
			//检查会话信息
			String sessionId = RequestContext.getContext().getSessionId();
			if(!sessionManager.exists(sessionId)){
				throw new BusinessException(ErrorCodes.INVALID_SESSION_ID).setParam(systemParameterProperties.getSessionId(), sessionId);
			}
			Session session = sessionManager.get(sessionId);
			RequestContext.getContext().setSession(session);
		}
	}

	/**
	 * @author danyuan
	 */
	@Override
	public Object afterInvoke(List<ApiRequestParameter> requestParams, Object resp,
			Method method, Class<?> targetClass) throws Throwable {
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
