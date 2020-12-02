/**  
* Title InjectSystemParamFieldDataFormatHandler.java  
* Description  请求字段自动注入系统参数
* @author danyuan
* @date Nov 16, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.dataformat;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.danyuanblog.framework.popularmvc.annotation.InjectSystemParam;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;

@Component
public class InjectSystemParamFieldDataFormatHandler extends AbstractFieldDataFormatHandler{

	/**
	 * @author danyuan
	 */
	@Override
	public int order() {
		return 0;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public Object handle(String fieldName, Object data, Map<Class<?>, Annotation> annotations)
			throws Throwable {
		if(!SystemParameterRenameProperties.DEFAULT_PARAM_MAP.keySet().contains(fieldName)){
			//非系统参数直接返回原值
			return data;
		}
		Annotation anno = annotations.get(InjectSystemParam.class);
		if(anno != null){			
			Set<String> needKeys = new HashSet<>();
			InjectSystemParam injectAnno = (InjectSystemParam) anno;
			String[] needParams = injectAnno.value();
			String[] execludeParams = injectAnno.execludes();
			if(needParams.length == 0){
				needKeys.addAll(SystemParameterRenameProperties.DEFAULT_PARAM_MAP.keySet());
			}else{
				for(String key : needParams){					
					needKeys.add(key);
				}
			}
			//排除字段
			for(String key : execludeParams){	
				if(needKeys.contains(key)){
					needKeys.remove(key);
				}				
			}
			if(needKeys.contains(fieldName)){
				//设置系统参数值
				return RequestContext.getContext().getAttachment(SystemParameterRenameProperties.DEFAULT_PARAM_MAP.get(fieldName));
			}
		}
		return data;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public boolean handleRequest() {
		return true;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public boolean handleResponse() {
		return false;
	}

}
