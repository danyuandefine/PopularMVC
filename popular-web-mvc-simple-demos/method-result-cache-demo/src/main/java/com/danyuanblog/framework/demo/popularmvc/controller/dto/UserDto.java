/**  
* Title UserDto.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String email;
	
	private String phone;
	
	private Boolean sex;
	
	private Integer age;
	
	private String desc;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loadTime;
}
