/**  
* Title MethdInvokeUtil.java  
* Description  
* @author danyuan
* @date Dec 19, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.utils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.danyuanblog.framework.popularmvc.annotation.Decrypt;
import com.danyuanblog.framework.popularmvc.annotation.IgnoreSign;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;

public class MethdInvokeUtil {
	public static List<ApiRequestParameter> parseParameters(String[] params, Object [] args, 
			Annotation[][] annotations, Annotation[] methodAnnos){
		List<ApiRequestParameter> parameters = new ArrayList<>();
		//提取业务请求参数
		ApiRequestParameter param = null;
	    for(int i=0; i<args.length; i++){
	    	if(args[i] != null && (BeanPropertyUtil.isBaseType(args[i]) 
	    			|| (args[i] instanceof Serializable))){
	    		param = new ApiRequestParameter();
	    		if(BeanPropertyUtil.isBaseType(args[i])){
	    			String paramName = getBaseTypeParamName(annotations[i]);
	    			if(StringUtils.isEmpty(paramName)){
	    				param.setParamName(params[i]);
	    			}else{
	    				param.setParamName(paramName);
	    			}    			
	    		}else{
	    			param.setParamName(params[i]);
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
	
	public static boolean checkIsAnno(Annotation[] annotations, Class<?> annoClass){
		for(Annotation anno : annotations){
			if(anno.annotationType().equals(annoClass)){
				return true;
			}
		}
		return false;
	}
	
	public static String getBaseTypeParamName(Annotation[] annotations){
		for(Annotation anno : annotations){
			if(anno.annotationType().equals(RequestParam.class)){
				RequestParam reqAnno = (RequestParam) anno;
				return reqAnno.value();				
			}
		}
		return null;
	}

}
