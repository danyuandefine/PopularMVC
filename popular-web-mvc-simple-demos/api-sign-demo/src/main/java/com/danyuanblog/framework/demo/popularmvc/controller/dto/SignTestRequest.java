/**  
* Title SignTestRequest.java  
* Description  
* @author danyuan
* @date Nov 14, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignTestRequest implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String name;
	private boolean sex;
	private Integer age;
	@NotNull
	@Size(min=1,max=5)
	private Map<String,String> tags;
	private List<String> likes;
}
