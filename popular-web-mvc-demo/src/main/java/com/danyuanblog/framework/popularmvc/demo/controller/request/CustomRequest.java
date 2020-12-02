/**  
* Title CustomRequest.java  
* Description  
* @author danyuan
* @date Dec 2, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.demo.controller.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomRequest implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String clientId;
	
	private String locale;
	
	private String appName;
	
	private String countryCode;
}
