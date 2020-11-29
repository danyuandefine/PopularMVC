/**  
* Title UserInfoDto.java  
* Description  
* @author danyuan
* @date Nov 29, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.demo.controller.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserInfoDto implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private Integer age;
	
	private String desc;
	
	private List<UserInfoDto> friends;
}
