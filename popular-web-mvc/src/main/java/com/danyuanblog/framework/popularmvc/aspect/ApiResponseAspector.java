/**  
 * Title DefaultResponseListener.java  
 * Description  响应内容处理器
 * @author danyuan
 * @date Nov 6, 2018
 * @version 1.0.0
 * site: www.danyuanblog.com
 */
package com.danyuanblog.framework.popularmvc.aspect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.interceptor.impl.ApiLogInterceptor;
import com.danyuanblog.framework.popularmvc.properties.PopularMvcConfig;
import com.danyuanblog.framework.popularmvc.response.enhance.ResponseEnhanceHandler;
import com.danyuanblog.framework.popularmvc.utils.IOUtils;

@SuppressWarnings("rawtypes")
@RestControllerAdvice
@ControllerAdvice
@Service
@Slf4j
public class ApiResponseAspector implements ResponseBodyAdvice {
	
	private static List<ResponseEnhanceHandler> handlers = new ArrayList<>();

	@Autowired(required = false)
	private PopularMvcConfig config;
	
	@Autowired
	private ApiLogInterceptor apiLogInterceptor;
	
	public static void addResponseEnhanceHandler(ResponseEnhanceHandler handler){
		if(!handlers.contains(handler)){
			handlers.add(handler);
			//添加成功后重新进行排序,使处理器按特定顺序进行增强处理
			handlers.sort(new Comparator<ResponseEnhanceHandler>() {

				@Override
				public int compare(ResponseEnhanceHandler o1, ResponseEnhanceHandler o2) {
					if(o1.order() > o2.order()){
						return 1;
					}else if(o1.order() < o2.order()){
						return -1;
					}else{
						return 0;
					}
				}
			});
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
	 * #supports(org.springframework.core.MethodParameter, java.lang.Class)
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// 只需要拦截我们配置的包名下的接口即可
		Class<?> targetClass = RequestContext.getContext().getTargetClass();
		
		if(targetClass != null && targetClass.getName().startsWith(config.getBasePackage())){
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
	 * #beforeBodyWrite(java.lang.Object,
	 * org.springframework.core.MethodParameter,
	 * org.springframework.http.MediaType, java.lang.Class,
	 * org.springframework.http.server.ServerHttpRequest,
	 * org.springframework.http.server.ServerHttpResponse)
	 */
	@Override
	@ResponseBody
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
			MediaType selectedContentType, Class selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		if(log.isTraceEnabled()){
			log.trace("开始处理响应数据：{}", body);
		}
		Object resp = body;
		for(ResponseEnhanceHandler handler : handlers){
			resp = handler.enhance(resp);
		}	
		
		try {
			apiLogInterceptor.outputResonseLog(RequestContext.getContext().getApiInfo().getReqParams(),
					resp, RequestContext.getContext().getTargetClass());
		} catch (Throwable e) {
			log.warn(IOUtils.getThrowableInfo(e));
		}
		return resp;
	}

}
