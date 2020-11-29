/**  
* Title ApiMethodInterceptor.java  
* Description  接口拦截处理器
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;

public interface ApiMethodInterceptor {
	
	int order();
	
	void preInvoke(List<ApiRequestParameter> requestParams, Method method, Class<?> targetClass) throws Throwable;
	
	Object afterInvoke(List<ApiRequestParameter> requestParams, Object resp, Method method, Class<?> targetClass) throws Throwable;
}
