/**  
* Title ApiLogInterceptor.java  
* Description  打印接口调用日志拦截器
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danyuanblog.framework.popularmvc.InvokeApiLogManager;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.ApiInfo;
import com.danyuanblog.framework.popularmvc.dto.ApiInvokeLog;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.interceptor.ApiMethodInterceptor;
import com.danyuanblog.framework.popularmvc.utils.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class ApiLogInterceptor implements ApiMethodInterceptor {
	/**
	 * 最大允许打印的响应日志长度
	 */
	private static final int MAX_RESP_LOG_SIZE = 1024;
	private static final String RESPONSE_TAIL = "\n//省略部分内容......\n}";
	private static String INVOKE_REQUEST_LOG = "接口请求：{}({}【{}】),版本号:{},请求参数：[{}]";
	private static String INVOKE_RESPONSE_LOG = "接口响应：{}({}【{}】),版本号:{},请求参数：[{}],响应参数：{}";
	@Autowired
	private InvokeApiLogManager invokeApiLogManager;
	/**
	 * @author danyuan
	 */
	@Override
	public void preInvoke(List<ApiRequestParameter> requestParams, Method method,
			Class<?> targetClass) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("开始打印调用接口入参！");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> systemParams = RequestContext.getContext().getAttachments();
		String paramValues = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(systemParams);		
		ApiInvokeLog apiLog = mapper.readValue(paramValues, ApiInvokeLog.class);
		BeanUtils.copyProperties(RequestContext.getContext().getApiInfo(), apiLog);	
		invokeApiLogManager.save(apiLog);
		ApiInfo info = RequestContext.getContext().getApiInfo();
		log.info(INVOKE_REQUEST_LOG, info.getApiName(), info.getApiUri(), info.getApiMethod(), info.getApiVersion(), objectArrToJSONString(requestParams));
	}

	/**
	 * @author danyuan
	 */
	@Override
	public Object afterInvoke(List<ApiRequestParameter> requestParams, Object resp,
			Method method, Class<?> targetClass) throws Throwable {
		//outputResonseLog(requestParams, resp, targetClass);
		return resp;
	}
	
	public void outputResonseLog(List<ApiRequestParameter> requestParams, Object resp,
			Class<?> targetClass) throws JsonProcessingException{
		if(log.isTraceEnabled()){
			log.trace("开始打印调用接口返回信息！");
		}
		ApiInfo info = RequestContext.getContext().getApiInfo();
		ObjectMapper mapper = new ObjectMapper();
		// 序列化 
	    String respStr = null;
	    if(resp != null){	    	
	    	respStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp);
	    	if(respStr.length() > MAX_RESP_LOG_SIZE){
	    		respStr = respStr.substring(0, MAX_RESP_LOG_SIZE) + RESPONSE_TAIL;
	    	}
	    }    
		log.info(INVOKE_RESPONSE_LOG, info.getApiName(), info.getApiUri(), info.getApiMethod(), info.getApiVersion(), objectArrToJSONString(requestParams), respStr);
	}

	public static String objectArrToJSONString(List<ApiRequestParameter> arr){
		if(arr != null && arr.size() > 0){
			ObjectMapper mapper = new ObjectMapper();
			StringBuffer buffer = new StringBuffer();
			for(ApiRequestParameter param : arr){
				try {
					buffer.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(param.getParam())).append(",");
				} catch (JsonProcessingException e) {
					if(log.isTraceEnabled()){
						log.trace(IOUtils.getThrowableInfo(e));
					}
				}
			}
			return buffer.substring(0, buffer.length() - 1);
		}
		return null;
	}
	/**
	 * @author danyuan
	 */
	@Override
	public int order() {
		return 999;//最后才调用这个拦截器，确保所有参数信息都能获取到
	}

}
