/**  
* Title LanguageInfoDto.java  
* Description  
* @author danyuan
* @date Nov 16, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller.dto;

import java.io.Serializable;

import com.danyuanblog.framework.popularmvc.annotation.LanguageTranslate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@LanguageTranslate
@AllArgsConstructor
@NoArgsConstructor
public class LanguageInfoDto implements Serializable{

	/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String info;
	
	private String name;
	
	private int age;
}
