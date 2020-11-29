/**  
* Title SignHandlerInterceptor.java  
* Description  
* @author danyuan
* @date Nov 7, 2018
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor.impl;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterProperties;
import com.danyuanblog.framework.popularmvc.utils.IOUtils;
import com.danyuanblog.framework.popularmvc.utils.request.BodyReaderHttpServletRequestWrapper;
import com.danyuanblog.framework.popularmvc.utils.request.HttpHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("singleton")
@Slf4j
public final class PopularMvcHandlerInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			//只拦截API接口
			RequestContext.getContext().setHandler((HandlerMethod)handler);
			RequestContext.getContext().setRequest(request);
			RequestContext.getContext().setResponse(response);
			//解析系统公共参数
			parseSystemParams(request);
			String path=request.getServletPath();
			if(path.replaceFirst("/", "").trim().equals("error")){//处理默认的错误结果页
				//throw new ApiBusinessException(ErrorCodes.INVALID_METHOD).setParam(request.getRequestURI());
			}		
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	private void parseSystemParams(HttpServletRequest request){
		// 请求方法
        String method = request.getMethod();
        HttpServletRequest requestWrapper = request;
        //先通过URL获取参数
        SystemParameterProperties.defaultParameterMap.keySet().stream().forEach(key -> {
        	String paramName = SystemParameterProperties.defaultParameterMap.get(key);
        	String paramValue = request.getParameter(paramName);
        	if(!StringUtils.isEmpty(paramValue)){
        		RequestContext.getContext().setAttachment(paramName, paramValue);
        	}        	
        });
        //再通过请求体获取系统参数
        if(RequestMethod.POST.name().equals(method) || RequestMethod.PUT.name().equals(method)){
        	String body = null;
        	//判断action
        	String action=request.getContentType();
        	if(action!=null && action.contains(MediaType.APPLICATION_JSON_VALUE)){
        		try {
        			 if(!(request instanceof BodyReaderHttpServletRequestWrapper)){
        				 requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        			 }
                    
                    body = HttpHelper.getBodyString(requestWrapper);
                    if (!StringUtils.isEmpty(body)) {
                    	if(log.isDebugEnabled()){
                        	log.debug("request body:{}", body);
                        }
                    	try{
                        	// 解析json                
                        	ObjectMapper mapper = new ObjectMapper(); 
                        	JsonNode json = mapper.readTree(body);
                        	SystemParameterProperties.defaultParameterMap.keySet().stream().forEach(key -> {
                            	String paramName = SystemParameterProperties.defaultParameterMap.get(key);
                            	if(json.has(paramName)){
                            		String paramValue = json.get(paramName).asText();
                                	if(!StringUtils.isEmpty(paramValue)){
                                		RequestContext.getContext().setAttachment(paramName, paramValue);
                                	} 
                            	}                            	       	
                            });
                        }catch(Exception e){
                        	log.warn(IOUtils.getThrowableInfo(e));
                        }  
                    }
				} catch (IOException e) {
					if(log.isDebugEnabled()){
						log.error(e.getMessage());
						log.error(IOUtils.getThrowableInfo(e));
					}					
				}   
        	}               
        }
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
