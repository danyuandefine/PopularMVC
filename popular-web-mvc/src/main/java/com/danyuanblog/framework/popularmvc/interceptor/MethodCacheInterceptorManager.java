/**  
* Title MethodCacheInterceptorManager.java  
* Description  
* @author danyuan
* @date Dec 17, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.danyuanblog.framework.popularmvc.CacheManager;
import com.danyuanblog.framework.popularmvc.annotation.CacheMethodResult;
import com.danyuanblog.framework.popularmvc.annotation.CacheMethodResultEvict;
import com.danyuanblog.framework.popularmvc.cache.MethodCacheManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.utils.SpringBeanUtil;
import com.danyuanblog.framework.popularmvc.utils.StringUtils;


@Component
@Slf4j
public class MethodCacheInterceptorManager implements MethodInterceptor {

	@Autowired
	@Lazy
	private MethodCacheManager methodCacheManager;
	
	private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
	
	/**
	 * @param invocation
	 * @return
	 * @throws Throwable
	 * @author danyuan
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("进入方法缓存处理器切面!");
		}
		Class<?> targetClass = invocation.getThis().getClass();	
		Object response = null;
		Object [] args=invocation.getArguments();		
		Method method = invocation.getMethod();
		String[] parameters = parameterNameDiscoverer.getParameterNames(method);    
	   
        //获取缓存注解信息
		CacheManager cacheManager = null;
		CacheMethodResult cacheMethodResult = AnnotationUtils.findAnnotation(method, CacheMethodResult.class);
		CacheMethodResultEvict cacheMethodResultEvict = AnnotationUtils.findAnnotation(method, CacheMethodResultEvict.class);
		if(cacheMethodResult != null){
			//缓存
			cacheManager = SpringBeanUtil.getBean(cacheMethodResult.cacheManager());
			if(cacheManager == null){
				throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam(cacheMethodResult.cacheManager().toString());
			}
			//获取缓存key
			String cacheKey = methodCacheManager.getCacheKey(cacheMethodResult.prefix(), cacheMethodResult.id(), parameters, args, targetClass, method);
			
			//检查是否存在方法结果缓存
			response = methodCacheManager.getResult(cacheKey, cacheManager, cacheMethodResult.expireSeconds(), cacheMethodResult.expireMode());
			if(response != null){
				//存在则直接返回缓存结果
				if(log.isTraceEnabled()){
					log.trace("存在方法结果缓存[{}]，直接返回缓存结果!", cacheKey);
				}
				return response;
			}	
			
			//否则直接执行正常业务逻辑
			if(log.isTraceEnabled()){
				log.trace("不存在方法结果缓存[{}]，重新执行业务流程!", cacheKey);
			}
			response = invocation.proceed();
			if(StringUtils.isNotEmpty(response)){
				//缓存方法返回的结果
				methodCacheManager.cacheResult(cacheKey, response, cacheManager, cacheMethodResult.expireSeconds(), cacheMethodResult.expireMode());
			}			
		}else if(cacheMethodResultEvict != null){
			//先执行业务流程
			response = invocation.proceed();
			//执行成功后删除前一次的缓存结果
			cacheManager = SpringBeanUtil.getBean(cacheMethodResultEvict.cacheManager());
			if(cacheManager == null){
				throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam(cacheMethodResultEvict.cacheManager().toString());
			}
			//获取缓存key
			String cacheKey = methodCacheManager.getCacheKey(cacheMethodResultEvict.prefix(), cacheMethodResultEvict.id(), parameters, args, targetClass, method);
			methodCacheManager.remove(cacheKey, cacheManager, cacheMethodResultEvict.expireSeconds(), cacheMethodResultEvict.expireMode());
		}else{
			log.warn("未找到缓存相关注解，请检查配置！");
			response = invocation.proceed();
		}
		
		return response;
	}

}
