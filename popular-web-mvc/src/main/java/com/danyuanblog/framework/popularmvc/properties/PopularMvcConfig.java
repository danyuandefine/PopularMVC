/**  
* Title SimoConfig.java  
* Description  
* @author danyuan
* @date Nov 7, 2018
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.properties;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "popularmvc")
public class PopularMvcConfig implements Serializable {

	/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否启用popularMVC api框架
	 */
	private Boolean enable;
	/**
	 * 是否开启swagger
	 */
	private Boolean enableSwagger;
	/**
	 * 默认语言
	 */
	private String locale;
	/**
	 * 当未找到该语言的翻译时，是否使用默认翻译内容
	 */
	private Boolean alwaysUserDefaultLocale;
	/**
	 * 是否强制对所有api响应添加响应壳
	 */
	private Boolean forceAutoAddResponseWrapper;
	/**
	 * 根包名
	 */
	private String basePackage;
	
	public PopularMvcConfig(){
		this.enable=true;
		this.locale=Locale.CHINA.toString();
		this.alwaysUserDefaultLocale=true;
		this.forceAutoAddResponseWrapper=true;
		this.basePackage="com.danyuanblog.framework.popularmvc.controller";
	}
	
}
