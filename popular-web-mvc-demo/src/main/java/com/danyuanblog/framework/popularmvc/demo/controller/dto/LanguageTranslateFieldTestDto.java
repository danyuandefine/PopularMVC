/**  
* Title LanguageTranslateFieldTestDto.java  
* Description  
* @author danyuan
* @date Nov 30, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.demo.controller.dto;

import java.io.Serializable;

import com.danyuanblog.framework.popularmvc.annotation.LanguageTranslate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageTranslateFieldTestDto implements Serializable{/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@LanguageTranslate
	private String info;//只对此字段做国际化翻译,使用当前上下文方言
	
	private String name;
		
	@LanguageTranslate(locale = "zh_CN")
	private String tags;//标签， 翻译成指定语言
}
