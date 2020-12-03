/**  
* Title PopularMvcSwaggerConfigurer.java  
* Description  
* @author danyuan
* @date Nov 28, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.danyuanblog.framework.popularmvc.properties.PopularMvcSwaggerProperties;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterConfigProperties;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

@EnableSwagger2
@EnableKnife4j
@EnableConfigurationProperties({ 
	PopularMvcSwaggerProperties.class,
	SystemParameterConfigProperties.class
	})
@ConditionalOnProperty(name={"popularmvc.enable","popularmvc.enableSwagger"},havingValue = "true")
@AutoConfigureBefore(PopularMvcWebConfigurer.class)
@Configuration
public class PopularMvcSwaggerConfigurer {
	@Bean
	public Docket businessApi(@Autowired SystemParameterConfigProperties otherParams, @Autowired PopularMvcSwaggerProperties swagger,@Autowired SystemParameterRenameProperties systemParameterProperties) {
		Docket docket=new Docket(DocumentationType.SWAGGER_2)
				        .apiInfo(apiInfo(swagger))
				        //分组名称
				        .groupName(swagger.getTitle())
				        .globalOperationParameters(getGlobalOperationParameters(systemParameterProperties, otherParams, false))
				        .select()
				        //这里指定Controller扫描包路径
				        .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
				        .paths(PathSelectors.any())
				        .build();
		return docket;
	}
	
	private List<Parameter> getGlobalOperationParameters(SystemParameterRenameProperties systemParameterProperties, 
			SystemParameterConfigProperties otherParams, boolean execlud) {
		List<Parameter> pars = new ArrayList<>();
		
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        if(!execlud){
        	otherParams.toParamMap().forEach((key,value) -> {
            	parameterBuilder.name(value).description(key)
            	.modelRef(new ModelRef("string"))
            	.parameterType("query")
            	.required(false);
            	pars.add(parameterBuilder.build());
            });        
        }
        SystemParameterRenameProperties.DEFAULT_PARAM_MAP.forEach((key,value) -> {
        	if(!(execlud && otherParams.getOtherParams() !=null && otherParams.getOtherParams().contains(key))){
        		parameterBuilder.name(value).description(key)
            	.modelRef(new ModelRef("string"))
            	.parameterType("query")
            	.required(false);
            	pars.add(parameterBuilder.build());
        	}        	
        });        
		return pars;
	}

	@Bean(value = "defaultApi")		
    public Docket defaultApi(@Autowired SystemParameterRenameProperties systemParameterProperties, @Autowired SystemParameterConfigProperties otherParams) {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(systemApiInfo())
                //分组名称
                .groupName("PopularMvc内置API")
                .globalOperationParameters(getGlobalOperationParameters(systemParameterProperties, otherParams, true))
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.danyuanblog.framework.popularmvc.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo(PopularMvcSwaggerProperties swagger) {
		return new ApiInfoBuilder().title(swagger.getTitle())
				.description(swagger.getDescription())
				.termsOfServiceUrl(swagger.getTermsOfServiceUrl())
				.contact(swagger.getContact()).version(swagger.getVersion())
				.build();
	}
	
	@SuppressWarnings("deprecation")
	private ApiInfo systemApiInfo() {
		return new ApiInfoBuilder().title("PopularMvc内置API列表")
				.description("PopularMvc默认提供的一些公共的通用API")
				.termsOfServiceUrl("http://www.danyuanblog.com")
				.contact("admin@danyuanblog.com").version("v1.0.0")
				.build();
	}

}
