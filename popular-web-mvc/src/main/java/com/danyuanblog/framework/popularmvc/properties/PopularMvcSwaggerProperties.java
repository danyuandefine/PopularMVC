/**  
* Title SwaggerProperties.java  
* Description  
* @author danyuan
* @date Mar 14, 2019
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "popularmvc.api.swagger")
public class PopularMvcSwaggerProperties {
	private String basePackage;
	private String title;
	private String description;
	private String termsOfServiceUrl;
	private String contact;
	private String version;
	public PopularMvcSwaggerProperties(){
		basePackage="com.danyuanblog.framework.popularmvc.controller";
		title="PopularMVC 接口文档清单";
		description="接口文档随着接口动态同步更新！";
		termsOfServiceUrl="http://www.danyuanblog.com";
		contact="淡远文摘";
		version="1.0";
	}
}
