/**  
 * Title WebConfig.java  
 * Description  spring mvc 框架的一些配置信息
 * @author danyuan
 * @date Nov 7, 2018
 * @version 1.0.0
 * site: www.danyuanblog.com
 */
package com.danyuanblog.framework.popularmvc.config;


import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.danyuanblog.framework.popularmvc.filter.RepeatedlyReadFilter;
import com.danyuanblog.framework.popularmvc.interceptor.PopularMvcHandlerInterceptor;
import com.danyuanblog.framework.popularmvc.properties.ChannelConfigProperties;
import com.danyuanblog.framework.popularmvc.properties.ErrorCodeProperties;
import com.danyuanblog.framework.popularmvc.properties.PopularMvcConfig;
import com.danyuanblog.framework.popularmvc.properties.ResponseSystemFieldRenameProperties;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterConfigProperties;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;

@Configuration
@Data
@Slf4j
@EnableConfigurationProperties({ PopularMvcConfig.class, 
	ChannelConfigProperties.class,
	ResponseSystemFieldRenameProperties.class,
	SystemParameterRenameProperties.class,
	SystemParameterConfigProperties.class,
	ErrorCodeProperties.class
	})
@ComponentScan("com.danyuanblog.framework.popularmvc")
@ConditionalOnProperty(name={"popularmvc.enable"},havingValue = "true")
public class PopularMvcWebConfigurer implements WebMvcConfigurer {

	@Autowired
	private PopularMvcHandlerInterceptor popularMvcHandlerInterceptor;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		WebMvcConfigurer.super.addFormatters(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(popularMvcHandlerInterceptor);
		if(log.isTraceEnabled()){
			log.trace("Add PopularMvcHandlerInterceptor success !");
		}		
		WebMvcConfigurer.super.addInterceptors(registry);
	}	

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		// 添加默认转换器
		converters.addAll(new WebMvcConfigurationSupport() {

			public List<HttpMessageConverter<?>> defaultMessageConverters() {
				return super.getMessageConverters();
			}

		}.defaultMessageConverters());			
		converters.removeIf(httpMessageConverter -> httpMessageConverter.getClass() == StringHttpMessageConverter.class);
		
	}	

	@Override
	public Validator getValidator() {
		return WebMvcConfigurer.super.getValidator();
	}
	
	@Bean
    public FilterRegistrationBean<RepeatedlyReadFilter> repeatedlyReadFilter() {
        FilterRegistrationBean<RepeatedlyReadFilter> registration = new FilterRegistrationBean<RepeatedlyReadFilter>();
        RepeatedlyReadFilter repeatedlyReadFilter = new RepeatedlyReadFilter();
        registration.setFilter(repeatedlyReadFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }


}
