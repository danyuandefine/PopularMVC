/**  
* Title IgnoreRequestFiledSignRequest.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.danyuanblog.framework.popularmvc.annotation.IgnoreSign;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class IgnoreRequestFiledSignRequest implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String desc;
	private String phone;
	@IgnoreSign
	@NotNull
	private AccountInfo account;
	@NotEmpty
	private String test;
	@NotNull
	private Integer age;
}
