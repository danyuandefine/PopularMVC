/**  
* Title ApiInvokeMethodInterceptorManager.java  
* Description  
* @author danyuan
* @date Dec 17, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;

import com.danyuanblog.framework.popularmvc.annotation.Decrypt;
import com.danyuanblog.framework.popularmvc.annotation.IgnoreSign;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.ApiInfo;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.interceptor.ApiMethodInterceptor;
import com.danyuanblog.framework.popularmvc.interceptor.impl.ApiLogInterceptor;
import com.danyuanblog.framework.popularmvc.interceptor.impl.NoRepeatSubmitInterceptor;
import com.danyuanblog.framework.popularmvc.properties.PopularMvcConfig;
import com.danyuanblog.framework.popularmvc.utils.BeanPropertyUtil;
import com.danyuanblog.framework.popularmvc.utils.ClassOriginCheckUtil;

@Component
@Slf4j
public class ApiInvokeMethodInterceptorManager implements MethodInterceptor {

	private static String DEFAULT_API_VERSION = "1.0";
	private static String API_NAME = "%s::%s";
	private static Pattern PATTERN = Pattern.compile("[0-9\\.]*");
	
	@Autowired(required = false)
	private PopularMvcConfig config;
	
	@Autowired
	private ApiLogInterceptor apiLogInterceptor;
	
	@Autowired
	private ApiExceptionAspector apiExceptionAspector;
	
	@Autowired
	private NoRepeatSubmitInterceptor noRepeatSubmitInterceptor;
	
	private static List<ApiMethodInterceptor> interceptors = new ArrayList<>();
	/**
	 * 添加接口拦截器
	 * @author danyuan
	 */
	public static void addInterceptor(ApiMethodInterceptor interceptor){
		interceptors.add(interceptor);
		//添加成功后重新进行排序,使拦截器按特定顺序进行拦截处理
		interceptors.sort(new Comparator<ApiMethodInterceptor>() {

			@Override
			public int compare(ApiMethodInterceptor o1, ApiMethodInterceptor o2) {
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
	
	/**
	 * @param invocation
	 * @return
	 * @throws Throwable
	 * @author danyuan
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if(log.isTraceEnabled()){
			log.trace("进入API接口调用切面!");
		}
		
		Class<?> targetClass = invocation.getThis().getClass();	
		RequestContext.getContext().setTargetClass(targetClass);
		Object response = null;
		Object [] args=invocation.getArguments();		
		Method method = invocation.getMethod();
		RequestContext.getContext().setMethod(method);
		Parameter[] parameters = invocation.getMethod().getParameters();
	    
	    //方法注解
	    Annotation[] methodAnnos = method.getAnnotations();
	    //参数注解，1维是参数，2维是注解
        Annotation[][] annotations = method.getParameterAnnotations();
        //获取参数
		List<ApiRequestParameter> params = parseParameters(parameters, args, annotations, methodAnnos);
	    
	    //获取接口信息
	    HttpServletRequest request = RequestContext.getContext().getRequest();
	    HandlerMethod handler = RequestContext.getContext().getHandler();
	    String controllerName = handler.getBeanType().getName();
	    controllerName = controllerName.substring(controllerName.lastIndexOf(".") + 1);
        String requestMethodName = handler.getMethod().getName();
        
        ApiInfo apiInfo = new ApiInfo();
	    apiInfo.setApiMethod(request.getMethod())
	    	.setApiName(String.format(API_NAME,controllerName, requestMethodName));
	    	
        String url = request.getRequestURI();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode != null && (statusCode != 200)){//是否是存在错误地址重定向URL
        	url = (String) request.getAttribute("javax.servlet.forward.servlet_path");
        }else{
        	//尝试从url中提取版本号
            String version = DEFAULT_API_VERSION;
            String str = url.substring(1);
            if(str.indexOf("/") > 0){
            	String first = str.substring(0, str.indexOf("/"));
            	if(PATTERN.matcher(first).matches()){
            		version = first;
            	}
            }
            apiInfo.setApiVersion(version);
        }	
        if(!StringUtils.isEmpty(request.getQueryString())){
        	url = url + "?" + request.getQueryString();
        }            
        apiInfo.setApiUri(url);
	    RequestContext.getContext().setApiInfo(apiInfo);
	    try{
	    	//接口业务执行前拦截处理
		    try{
		    	for(ApiMethodInterceptor interceptor : interceptors){
		    		interceptor.preInvoke(params, method, targetClass);
			    }	
		    }finally{	    	
		    	apiLogInterceptor.preInvoke(params, method, targetClass);
		    }		    
		    apiInfo.setReqParams(params);
		    //执行业务方法
		    response = invocation.proceed();
		    //接口执行完成后拦截处理
		    for(ApiMethodInterceptor interceptor : interceptors){
		    	response = interceptor.afterInvoke(params, response, method, targetClass);
		    }	
		    apiInfo.setResponse(response);	
	    }catch(Exception e){
	    	//释放防重复提交码
	    	noRepeatSubmitInterceptor.afterInvoke(params, response, method, targetClass);
	    	throw e;
	    }		
		return response;
	}
	
	private List<ApiRequestParameter> parseParameters(Parameter[] params, Object [] args, 
			Annotation[][] annotations, Annotation[] methodAnnos){
		List<ApiRequestParameter> parameters = new ArrayList<>();
		//提取业务请求参数
		ApiRequestParameter param = null;
	    for(int i=0; i<args.length; i++){
	    	if(args[i] != null && (BeanPropertyUtil.isBaseType(args[i]) 
	    			|| ClassOriginCheckUtil.isBasePackagesChild(args[i].getClass(), config.getAllBasePackages()))){
	    		param = new ApiRequestParameter();
	    		if(BeanPropertyUtil.isBaseType(args[i])){
	    			String paramName = getBaseTypeParamName(annotations[i]);
	    			if(StringUtils.isEmpty(paramName)){
	    				param.setParamName(params[i].getName());
	    			}else{
	    				param.setParamName(paramName);
	    			}    			
	    		}else{
	    			param.setParamName(params[i].getName());
	    		}	    		
	    		if(checkIsAnno(annotations[i], IgnoreSign.class)){
	    			param.setNeedSign(false);
	    		}else{
	    			param.setNeedSign(true);
	    		}
	    		if(checkIsAnno(annotations[i], Decrypt.class)){
	    			param.setDecrpt(true);
	    		}else{
	    			param.setDecrpt(false);
	    		}
	    		param.setParam(args[i]);
	    		
  		
	    		Annotation[] allAnnos = null;
	    		if(methodAnnos != null && (annotations[i] != null)){
	    			allAnnos = Arrays.copyOf(methodAnnos, methodAnnos.length + annotations[i].length);//数组扩容
	    			System.arraycopy(annotations[i], 0, allAnnos, methodAnnos.length, annotations[i].length);
	    		}else if(methodAnnos != null){
	    			allAnnos = methodAnnos;
	    		}else if(annotations[i] != null){
	    			allAnnos = annotations[i];
	    		}	    		
	    		param.setAnnotations(allAnnos);
	    		param.setIndex(i);
	    		param.setArgs(args);
	    		parameters.add(param);
	    	}
	    }		
		return parameters;
	}
	
	private boolean checkIsAnno(Annotation[] annotations, Class<?> annoClass){
		for(Annotation anno : annotations){
			if(anno.annotationType().equals(annoClass)){
				return true;
			}
		}
		return false;
	}
	
	private String getBaseTypeParamName(Annotation[] annotations){
		for(Annotation anno : annotations){
			if(anno.annotationType().equals(RequestParam.class)){
				RequestParam reqAnno = (RequestParam) anno;
				return reqAnno.value();				
			}
		}
		return null;
	}

}
