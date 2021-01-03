/**  
* Title TestTranslateBusinessErrorController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.exception.BusinessException;


@Api(tags = "测试自动翻译业务异常信息相关接口列表")
@RestController
@Validated
public class TestTranslateBusinessErrorController {
	
	@PostMapping("login")
	@ApiOperation(value="测试登录失败", notes="测试提示账户存在错误")
	public void login(@Valid @NotEmpty String account, @Valid @NotEmpty String password){
		//TODO: 执行业务逻辑
		
		//TODO：当发现账户信息不存在，直接抛出异常即可
		if(!"admin".equals(account)){
			throw new BusinessException("USER_ACCOUNT_NOT_FOUND").setParam(account);
		}
		
		//TODO：当发现密码错误时，直接抛出异常
		if(!"123456".equals(password)){
			throw new BusinessException("USER_PASSWORD_ERROR");
		}
		
		//TODO：组装响应信息，然后返回
		
	}
}
