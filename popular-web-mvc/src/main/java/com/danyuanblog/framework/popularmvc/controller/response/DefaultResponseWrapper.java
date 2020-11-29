/**  
* Title BaseResponse.java  
* Description  响应包装类
* 兼容之前老接口
* @author danyuan
* @date Nov 6, 2018
* @version 1.0.0
* blog www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.controller.response;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@ToString(callSuper=true)
@Accessors(chain=true)
public class DefaultResponseWrapper<T> implements Serializable {

	/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String msg;
	private T data;
	private String sign;
	private Long timestamp;
	/**
	 * 预留出来的可拓展系统参数字段
	 */
	private Map<String,Object> otherParams;
	
	
	public DefaultResponseWrapper(T data){
		this.code=ErrorCodes.SUCCESS.getCode();
		this.data=data;
	}
	public DefaultResponseWrapper(Integer code,String msg){
		this.code=code;
		this.msg=msg;
	}
	public DefaultResponseWrapper(Integer code,String msg,T body){
		this.code=code;
		this.msg=msg;
		this.data=body;
	}
	public DefaultResponseWrapper(){
		this.code=ErrorCodes.SUCCESS.getCode();
	}
	public DefaultResponseWrapper(ErrorCodes error){
		this.code=error.getCode();
		this.msg=error.getMsgCode();
	}
	public DefaultResponseWrapper(BusinessException e){
		this.code=e.getCode();
		this.msg=e.getMsg();
	}
	
}
