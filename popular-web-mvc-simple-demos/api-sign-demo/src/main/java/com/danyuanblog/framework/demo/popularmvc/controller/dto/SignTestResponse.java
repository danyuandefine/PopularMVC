/**  
* Title SignTestResponse.java  
* Description  
* @author danyuan
* @date Nov 14, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.AccountInfo;

import lombok.Data;

@Data
public class SignTestResponse implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String desc;
	private String phone;
	private AccountInfo account;
	@NotEmpty
	private String test;
	@NotNull
	private Integer age;
}
