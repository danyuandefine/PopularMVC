/**  
* Title UniqueToken.java  
* Description  
* @author danyuan
* @date Nov 7, 2018
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.dto;

import java.io.Serializable;

public class UniqueToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	private boolean submit;
	public String getToken() {
		return token;
	}
	public UniqueToken setToken(String token) {
		this.token = token;
		return this;
	}
	public boolean isSubmit() {
		return submit;
	}
	public UniqueToken setSubmit(boolean submit) {
		this.submit = submit;
		return this;
	}
	
}
