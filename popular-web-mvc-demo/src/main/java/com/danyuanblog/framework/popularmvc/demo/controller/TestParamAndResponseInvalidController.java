/**  
* Title TestParamAndResponseInvalidController.java  
* Description  
* @author danyuan
* @date Nov 29, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.demo.controller;

import java.util.Arrays;

import javax.validation.constraints.Email;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.annotation.RequiredCountryCode;
import com.danyuanblog.framework.popularmvc.annotation.RequiredCurrency;
import com.danyuanblog.framework.popularmvc.annotation.RequiredLocale;
import com.danyuanblog.framework.popularmvc.annotation.RequiredNoRepeatSubmit;
import com.danyuanblog.framework.popularmvc.annotation.RequiredSession;
import com.danyuanblog.framework.popularmvc.annotation.RequiredSign;
import com.danyuanblog.framework.popularmvc.annotation.RequiredTimeZone;
import com.danyuanblog.framework.popularmvc.annotation.RequiredTimestamp;
import com.danyuanblog.framework.popularmvc.annotation.RequiredVersion;
import com.danyuanblog.framework.popularmvc.consts.DenyRepeatSubmitType;
import com.danyuanblog.framework.popularmvc.demo.controller.dto.ParamsCheckDto;

@Api(tags = "测试api请求参数或响应内容的校验")
@RestController
public class TestParamAndResponseInvalidController {

	@GetMapping(value="testSystemParams",
			name="测试校验系统参数")
	@ApiOperation(value="测试校验系统参数", notes="测试校验系统参数")
	@RequiredLocale
	@RequiredSign
	@RequiredCountryCode
	@RequiredCurrency
	@RequiredNoRepeatSubmit(mode = DenyRepeatSubmitType.GENERATE_TOKEN)
	@RequiredSession
	@RequiredTimestamp
	@RequiredTimeZone
	@RequiredVersion
	public void testSystemParams(
			) {
	}
	
	@PostMapping(value="testRequestParamCheck",
			name="测试校验请求参数")
	@ApiOperation(value="测试校验请求参数", notes="测试校验请求参数")
	public void testRequestParamCheck(@RequestBody ParamsCheckDto req, @RequestParam @Email String adminEmail){
		
	}
	
	@GetMapping(value="testResponseFieldCheck",
			name="测试校验响应参数")
	@ApiOperation(value="测试校验响应参数", notes="测试校验响应参数")
	public ParamsCheckDto testResponseFieldCheck(
			) {
		ParamsCheckDto dto = new ParamsCheckDto();
		dto.setAccount("111222");
		dto.setAge(25);
		dto.setEmail("xxx");
		dto.setLikes(Arrays.asList("football","watch movie","swiming","book"));
		return dto;
	}
}
