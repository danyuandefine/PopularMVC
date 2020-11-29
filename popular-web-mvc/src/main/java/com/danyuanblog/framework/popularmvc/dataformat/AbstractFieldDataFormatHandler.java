/**  
* Title AbstractFieldDataFormatHandler.java  
* Description  
* @author danyuan
* @date Nov 16, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.dataformat;

import com.danyuanblog.framework.popularmvc.interceptor.impl.ApiDataFormatInterceptor;

public abstract class AbstractFieldDataFormatHandler implements FieldDataFormatHandler {

	public AbstractFieldDataFormatHandler(){
		ApiDataFormatInterceptor.addDataFormatHandler(this);
	}
	
}
