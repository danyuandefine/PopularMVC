/**  
* Title CustomResponse.java  
* Description  
* @author danyuan
* @date Jan 3, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomResponse implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private Integer age;
	
	private Boolean sex;
	
	private String desc;
}
