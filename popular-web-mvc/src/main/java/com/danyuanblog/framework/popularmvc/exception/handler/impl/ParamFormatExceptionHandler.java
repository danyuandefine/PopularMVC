/**  
* Title ParamFormatExceptionHandler.java  
* Description  参数格式转换失败异常处理器
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.exception.handler.impl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.exception.handler.AbstractCommonExceptionHandler;

@Service
public class ParamFormatExceptionHandler extends AbstractCommonExceptionHandler {
	
	/**
	 * @author danyuan
	 */
	@Override
	public boolean support(Exception e) {
		if (e instanceof IllegalArgumentException || e instanceof MethodArgumentTypeMismatchException){
			return true;
		}
		return false;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public DefaultResponseWrapper<?> handle(HttpServletRequest request,
			HttpServletResponse response, Exception e) {		
		return new DefaultResponseWrapper<>(
				ErrorCodes.INVALID_PARAM.getCode(), e.getMessage());
	}

}
