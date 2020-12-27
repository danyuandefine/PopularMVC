/**  
* Title ClassOriginCheckUtil.java  
* Description  
* @author danyuan
* @date Nov 30, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.utils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

public class ClassOriginCheckUtil {
	
	public static String getDeafultBasePackage(){
		//查找启动类默认包名
		Map<String,Object> beanMap = SpringBeanUtil.getBeansWithAnnotation(SpringBootApplication.class);
		if(beanMap != null && beanMap.size() > 0){
			//自动扫描第一个启动类包名下的所有接口
			for(Object object : beanMap.values()){
				return object.getClass().getPackage().getName(); 
			}
		}
		return "";
	}

	public static boolean isBasePackagesChild(Class<?> targetClass, Set<String> pkgs){
		if(targetClass == null){
			return false;
		}
		for(String pkg : pkgs){
			if(targetClass.getName().startsWith(pkg)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNeedIntercept(Class<?> targetClass, Class<?> returnValueClass, Method method, Set<String> pkgs){
		if(targetClass == null){
			return false;
		}
		if(isBasePackagesChild(targetClass, pkgs)){
			//只处理json格式数据返回
			RestController restController =AnnotationUtils.findAnnotation(targetClass, RestController.class);
			if(restController != null){
				return true;
			}
			ResponseBody responseBody =AnnotationUtils.findAnnotation(method, ResponseBody.class);
			if(responseBody == null){
				responseBody =AnnotationUtils.findAnnotation(targetClass, ResponseBody.class);
			}
			if(responseBody != null){
				return true;
			}
		}
		return false;
	}
}
