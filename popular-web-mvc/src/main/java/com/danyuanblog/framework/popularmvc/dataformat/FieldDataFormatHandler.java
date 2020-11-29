/**  
* Title FieldDataFormatHandler.java  
* Description  对象字段值加工处理器
* @author danyuan
* @date Nov 16, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.dataformat;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface FieldDataFormatHandler {

	/**
	 * @author danyuan
	 */
	int order();
	
	Object handle(Object data, Map<Class<?>, Annotation> annotations) throws Throwable;
	
}
