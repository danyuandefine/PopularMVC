/**  
* Title TestValidateSystemParamsController.java  
* Description  
* @author danyuan
* @date Dec 27, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.annotation.RequiredCountryCode;
import com.danyuanblog.framework.popularmvc.annotation.RequiredCurrency;
import com.danyuanblog.framework.popularmvc.annotation.RequiredLocale;
import com.danyuanblog.framework.popularmvc.annotation.RequiredParam;
import com.danyuanblog.framework.popularmvc.annotation.RequiredTimeZone;
import com.danyuanblog.framework.popularmvc.annotation.RequiredTimestamp;
import com.danyuanblog.framework.popularmvc.annotation.RequiredVersion;

@RestController
@Validated
@Api(tags = "校验系统参数接口列表")
public class TestValidateSystemParamsController {
	
		@GetMapping("testSingleInnerSystemParams")
		@PostMapping("testSingleInnerSystemParams")
		@ApiOperation(value="测试校验系统参数(逐个校验)", notes="测试校验系统参数(逐个校验)")
		@RequiredLocale
		@RequiredCountryCode
		@RequiredCurrency
		@RequiredTimestamp
		@RequiredTimeZone
		@RequiredVersion
		public String testSingleInnerSystemParams(
				) {
			return "参数校验通过！";
		}
		
		@PostMapping("testMultiInnerSystemParams")
		@GetMapping("testMultiInnerSystemParams")
		@ApiOperation(value="测试校验系统参数(批量校验)", notes="测试校验系统参数(批量校验)")
		@RequiredParam({"locale","currency"})
		public String testMultiInnerSystemParams(
				) {
			return "参数校验通过！";
		}
}
