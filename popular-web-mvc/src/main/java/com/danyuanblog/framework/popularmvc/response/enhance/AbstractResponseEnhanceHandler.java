/**  
* Title AbstractResponseEnhanceHandler.java  
* Description  
* @author danyuan
* @date Nov 8, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.response.enhance;

import com.danyuanblog.framework.popularmvc.aspect.ApiResponseAspector;

public abstract class AbstractResponseEnhanceHandler implements ResponseEnhanceHandler {

	public AbstractResponseEnhanceHandler(){
		ApiResponseAspector.addResponseEnhanceHandler(this);
	}
}
