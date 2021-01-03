/**  
* Title TestThrowBusinessErrorController.java  
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.exception.BusinessException;


@Api(tags = "测试接口抛出业务异常信息相关接口列表")
@RestController
@Validated
public class TestThrowBusinessErrorController {
	
	@PostMapping("login")
	@ApiOperation(value="测试登录失败", notes="测试提示账户登录信息存在错误,这种方式适用于比较规范的错误码管理，便于调用者对不同的业务错误码做出对应的处理。")
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
	
	@GetMapping("promptSimpleTips")
	@ApiOperation(value="测试简单提示类业务异常", notes="这种业务异常不需要特定的业务异常码值，可以直接硬编码在代码里，推荐使用枚举统一规范一下。")
	public void promptSimpleTips(){
		throw new BusinessException("系统在本月10-15号进行维护，给您带来的不便，敬请理解，谢谢！");
	}
	
}
