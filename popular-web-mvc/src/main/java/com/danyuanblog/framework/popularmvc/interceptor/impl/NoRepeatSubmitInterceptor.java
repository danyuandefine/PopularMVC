/**  
* Title NoRepeatSubmitInterceptor.java  
* Description  检查接口防重复提交拦截器
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor.impl;

import java.lang.reflect.Method;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danyuanblog.framework.popularmvc.CheckRepeatManager;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.interceptor.AbstractApiMethodInterceptor;

@Service
@Slf4j
public class NoRepeatSubmitInterceptor extends AbstractApiMethodInterceptor {

	@Autowired
	private CheckRepeatManager checkRepeatManager;
	
	/**
	 * @author danyuan
	 */
	@Override
	public void preInvoke(List<ApiRequestParameter> requestParams, Method method,
			Class<?> targetClass) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("开始校验接口防重复提交！");
		}
		if(RequestContext.getContext().getApiRestrictions().getUniqueSubmit()){
			//检查接口防重复提交
			checkRepeatManager.check(RequestContext.getContext().getRepeatCode());
		}
		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public Object afterInvoke(List<ApiRequestParameter> requestParams, Object resp,
			Method method, Class<?> targetClass) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("释放接口防重复提交码！");
		}
		if(RequestContext.getContext().getApiRestrictions().getUniqueSubmit()){
			//标记接口防重复提交成功
			checkRepeatManager.used(RequestContext.getContext().getRepeatCode());
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
