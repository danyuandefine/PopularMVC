/**  
* Title DefaultConfigPropertiesValue.java  
* Description  系统默认配置参数值
* @author danyuan
* @date Nov 30, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.consts;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum DefaultConfigPropertiesValue {
	SRPRING_MSG_BASENAME("spring.messages.basename","popularMvcMessages")
	,POPULARMVC_BASE_PACKAGES("popularmvc.basePackages","com.danyuanblog.framework.popularmvc")
	,POPULARMVC_ENABLE("popularmvc.enable",true)
	,POPULARMVC_ENABLE_SWAGGER("popularmvc.enableSwagger",true)
	,SRPRING_MSG_USE_CODE_AS_DEFAULT_MESSAGE("spring.messages.useCodeAsDefaultMessage",false)
	,SRPRING_MSG_FALLBACK_TO_SYSTEM_LOCALE("spring.messages.fallbackToSystemLocale",false)
	;
	private String key;
	
	private Object defaultValue;
	/**
	 * 是否添加到末尾,否则添加在前面
	 */
	private boolean append;
	/**
	 * 强制使用默认值
	 */
	private boolean forceUseDefaultValue;
	
	private DefaultConfigPropertiesValue(String key, Object defaultValue, boolean append, boolean forceUseDefaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
		this.append = append;
	}
	
	private DefaultConfigPropertiesValue(String key, Object defaultValue, boolean forceUseDefaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
		this.forceUseDefaultValue = forceUseDefaultValue;
	}
	
	private DefaultConfigPropertiesValue(String key, Object defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
		this.append = false; //默认添加到前面
		this.forceUseDefaultValue = false;
	}
	
	public Object getComposeValue(Object originalValue){
		if(this.forceUseDefaultValue){
			return this.defaultValue;
		}
		if(this.defaultValue.equals(originalValue)){
			return originalValue;
		}
		if(originalValue instanceof String){
			StringBuffer buffer = new StringBuffer();
			if(this.append){
				buffer.append(originalValue).append(",").append(this.getDefaultValue());
			}else{
				buffer.append(this.getDefaultValue()).append(",").append(originalValue);
			}
			return buffer.toString();
		}
		return originalValue;
	}
	
	public static Map<String,Object> getAllDefaultConfigs(){
		Map<String,Object> map = new HashMap<>();
		for(DefaultConfigPropertiesValue config : DefaultConfigPropertiesValue.values()){
			map.put(config.getKey(), config.getDefaultValue());
		}
		return map;
	}
}
