/**  
* Title CommonExceptionHandler.java  
* Description  异常处理器
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;

public interface CommonExceptionHandler {
	boolean support(Exception e);
	DefaultResponseWrapper<?> handle(HttpServletRequest request,
			HttpServletResponse response, Exception e);
}
