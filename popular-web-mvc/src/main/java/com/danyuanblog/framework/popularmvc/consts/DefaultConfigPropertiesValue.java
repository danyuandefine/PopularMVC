/**  
* Title DefaultConfigPropertiesValue.java  
* Description  系统默认配置参数值
* @author danyuan
* @date Nov 30, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.consts;

import lombok.Getter;

@Getter
public enum DefaultConfigPropertiesValue {
	SRPRING_MSG_BASENAME("spring.messages.basename","popularMvcMessages"),
	POPULARMVC_BASE_PACKAGES("popularmvc.basePackages","com.danyuanblog.framework.popularmvc")
	;
	private String key;
	
	private String defaultValue;
	/**
	 * 是否添加到末尾,否则添加在前面
	 */
	private boolean append;
	
	private DefaultConfigPropertiesValue(String key, String defaultValue, boolean append){
		this.key = key;
		this.defaultValue = defaultValue;
		this.append = append;
	}
	
	private DefaultConfigPropertiesValue(String key, String defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
		this.append = false; //默认添加到前面
	}
	
	public String getComposeValue(String originalValue){
		StringBuffer buffer = new StringBuffer();
		if(this.append){
			buffer.append(originalValue).append(",").append(this.getDefaultValue());
		}else{
			buffer.append(this.getDefaultValue()).append(",").append(originalValue);
		}
		return buffer.toString();
	}
}
