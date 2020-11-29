/**  
* Title TestResponseInfoController.java  
* Description  测试响应结构自动包装和业务异常的使用
* @author danyuan
* @date Sep 10, 2019
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.demo.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;
import com.danyuanblog.framework.popularmvc.annotation.IgnoreResponseWrapper;
import com.danyuanblog.framework.popularmvc.controller.response.SuccessResponse;
import com.danyuanblog.framework.popularmvc.demo.controller.dto.LanguageInfoDto;
import com.danyuanblog.framework.popularmvc.demo.controller.dto.UserInfoDto;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;

@Api(tags = "测试响应结构自动包装和业务异常的使用")
@RestController
public class TestResponseInfoController {
	@Autowired
	private LanguageTranslateManager languageTranslateManager;
		
	@GetMapping(value="testResponseWrap",
			name="测试响应包装能力")
	@ApiOperation(value="测试响应包装能力", notes="测试响应包装能力")
	public SuccessResponse testResponseWrap(
			) {
		return new SuccessResponse();
	}
	
	@GetMapping(value="testGetLanguageInfo",
			name="测试信息国际化自动翻译功能")
	@ApiOperation(value="测试信息国际化自动翻译功能", notes="测试信息国际化自动翻译功能")
	public LanguageInfoDto testGetLanguageInfo(@RequestParam("info") String info){
		return new LanguageInfoDto(info,"danyuan",22);
	}
	
	@GetMapping(value="testBusinessException",
			name="测试抛出业务异常功能")
	@ApiOperation(value="测试抛出业务异常功能", notes="测试抛出业务异常功能")
	public void testBusinessException(String username){
		throw new BusinessException("USER_ACCOUNT_NOT_FOUND").setParam(username);
	}
	
	@GetMapping(value="testBusinessExceptionCode",
			name="测试抛出自定义错误码业务异常功能")
	@ApiOperation(value="测试抛出自定义错误码业务异常功能", notes="测试抛出自定义错误码业务异常功能")
	public void testBusinessExceptionCode(String account){
		throw new BusinessException("ACCOUNT_NOT_FOUND").setParam(account);
	}
	
	@GetMapping(value="testIgnoreResponseWrapper",
			name="测试跳过响应壳包装业务")
	@ApiOperation(value="测试跳过响应壳包装业务", notes="测试跳过响应壳包装业务")
	@IgnoreResponseWrapper
	public UserInfoDto testIgnoreResponseWrapper(@RequestParam("desc") String desc){
		UserInfoDto user = new UserInfoDto();
		user.setUsername("danyuan");
		user.setAge(18);
		user.setDesc(desc);		
		return user;
	}
	
}
