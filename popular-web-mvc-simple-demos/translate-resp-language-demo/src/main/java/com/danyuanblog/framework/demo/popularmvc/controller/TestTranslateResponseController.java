/**  
* Title TestTranslateResponseController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.CustomResponse;
import com.danyuanblog.framework.demo.popularmvc.controller.dto.LanguageInfoDto;
import com.danyuanblog.framework.demo.popularmvc.controller.dto.LicenseDto;

@Api(tags = "测试自动翻译响应信息相关接口列表")
@RestController
public class TestTranslateResponseController {

	@GetMapping("getLanguageInfo")
	@ApiOperation(value="测试信息国际化自动翻译整个对象", notes="测试信息国际化自动翻译整个对象")
	public LanguageInfoDto getLanguageObjectInfo(@RequestParam(name = "info", required = false) String info){
		return new LanguageInfoDto(info,"danyuan.name");
	}
	
	@GetMapping("getLanguageFieldInfo")
	@ApiOperation(value="测试信息国际化自动翻译某些字段", notes="测试信息国际化自动翻译某些字段")
	public CustomResponse getLanguageFieldInfo(){
		return new CustomResponse("danyuan.name", 22, true, "danyuan.info", "danyuan.tags");
	}
	
	@GetMapping("getEnglishLicence")
	@ApiOperation(value="测试获取指定语言的版权信息", notes="测试获取指定语言的版权信息")
	public LicenseDto getEnglishLicence(){
		return new LicenseDto("LICENSE");
	}
}
