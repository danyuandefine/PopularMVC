/**  
* Title BusinessErrorDto.java  
* Description  
* @author danyuan
* @date Nov 28, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BusinessErrorDescription implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Integer code;
	
	private String desc;
}
