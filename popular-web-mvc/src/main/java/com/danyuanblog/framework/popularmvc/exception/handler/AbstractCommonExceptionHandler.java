/**  
* Title AbstractCommonExceptionHandler.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.exception.handler;

import com.danyuanblog.framework.popularmvc.aspect.ApiExceptionAspector;

public abstract class AbstractCommonExceptionHandler implements CommonExceptionHandler {

	public AbstractCommonExceptionHandler(){
		ApiExceptionAspector.addHandler(this);
	}
}
