/**  
* Title AbstractApiMethodInterceptor.java  
* Description  
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.interceptor;


public abstract class AbstractApiMethodInterceptor implements ApiMethodInterceptor {
	
	/**
	 * @author danyuan
	 */
	public AbstractApiMethodInterceptor() {
		ApiInvokeMethodInterceptorManager.addInterceptor(this);
	}
}
