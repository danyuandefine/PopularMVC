/**  
* Title AbstractApiMethodInterceptor.java  
* Description  
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor;

import com.danyuanblog.framework.popularmvc.aspect.ApiInvokeAspector;

public abstract class AbstractApiMethodInterceptor implements ApiMethodInterceptor {
	
	/**
	 * @author danyuan
	 */
	public AbstractApiMethodInterceptor() {
		ApiInvokeAspector.addInterceptor(this);
	}
}
