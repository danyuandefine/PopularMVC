/**  
* Title InvalidateExceptionHandler.java  
* Description  参数校验失败异常处理器
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.exception.handler.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.controller.response.ErrorInfo;
import com.danyuanblog.framework.popularmvc.controller.response.ErrorResponse;
import com.danyuanblog.framework.popularmvc.exception.handler.AbstractCommonExceptionHandler;

@Service
public class InvalidationExceptionHandler extends AbstractCommonExceptionHandler {

	@Autowired
	private LanguageTranslateManager languageTranslateManager;
	
	/**
	 * @author danyuan
	 */
	@Override
	public boolean support(Exception e) {
		if (e instanceof BindException || e instanceof MethodArgumentNotValidException){
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
		String systemError = null;			
		String locale = RequestContext.getContext().getLocale();
		List<FieldError> errors = null;
		if(e instanceof BindException){
			BindException ex=(BindException)e;
			errors = ex.getFieldErrors();
		}else{
			MethodArgumentNotValidException ex = (MethodArgumentNotValidException)e;
			errors = ex.getBindingResult().getFieldErrors();
		}
		ErrorResponse errorResp=null;
		systemError=languageTranslateManager.get(
				ErrorCodes.INVALID_PARAM.getMsgCode(), locale,"",""
				);
		errorResp=new ErrorResponse().setSubErrors(new ArrayList<ErrorInfo>());
		for(FieldError error :errors){
			String filed=error.getField();
			String errorType=error.getCode();
			Character first=errorType.charAt(0);
			errorType="system."+errorType.replaceFirst(String.valueOf(first), first.toString().toLowerCase());
			String code=languageTranslateManager.get(errorType,locale);
			if(code.equals(errorType)){//默认为参数非法
				code=error.getDefaultMessage();
			}
			String subErrMsg=languageTranslateManager.get(
					ErrorCodes.INVALID_PARAM.getMsgCode(), locale,filed,code
					);
			ErrorInfo info=new ErrorInfo();
			info.setError(errorType)
				.setMsg(subErrMsg);
			errorResp.getSubErrors().add(info);
		}
					
		DefaultResponseWrapper<ErrorResponse> responseWrapper = new DefaultResponseWrapper<>(
				ErrorCodes.INVALID_PARAM.getCode(), systemError, errorResp);

		return responseWrapper;
	}

}
