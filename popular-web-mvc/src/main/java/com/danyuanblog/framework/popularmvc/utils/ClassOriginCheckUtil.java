/**  
* Title ClassOriginCheckUtil.java  
* Description  
* @author danyuan
* @date Nov 30, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.utils;

import java.util.Set;

public class ClassOriginCheckUtil {

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
}
