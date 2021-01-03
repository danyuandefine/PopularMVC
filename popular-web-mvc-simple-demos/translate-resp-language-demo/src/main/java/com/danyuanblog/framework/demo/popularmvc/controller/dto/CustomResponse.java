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

import com.danyuanblog.framework.popularmvc.annotation.LanguageTranslate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@LanguageTranslate
	private String name;
	
	private Integer age;
	
	private Boolean sex;
	
	@LanguageTranslate
	private String info;
	
	@LanguageTranslate
	private String tags;
}
