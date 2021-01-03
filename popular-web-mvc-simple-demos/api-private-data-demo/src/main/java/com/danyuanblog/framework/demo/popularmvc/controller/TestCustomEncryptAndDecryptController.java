/**  
* Title TestCustomEncryptAndDecryptController.java  
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.component.RSADataEncryptHandler;
import com.danyuanblog.framework.demo.popularmvc.controller.dto.UserInfoDto;
import com.danyuanblog.framework.popularmvc.annotation.Decrypt;
import com.danyuanblog.framework.popularmvc.annotation.Encrypt;

@Api(tags = "测试自定义加解密算法的接口数据安全传输功能")
@RestController
@RequestMapping("custom")
public class TestCustomEncryptAndDecryptController {

	@GetMapping(value="testDecryptParamAndResponse",
			name="测试加解密普通参数和响应信息")
	@ApiOperation(value="测试加解密普通参数和响应信息", notes="测试加解密普通参数和响应信息")
	@Encrypt(type = RSADataEncryptHandler.class)
	public UserInfoDto testDecryptParamAndResponse(@RequestParam("desc") @Decrypt(type = RSADataEncryptHandler.class) String desc) {
		UserInfoDto user = new UserInfoDto();
		user.setUsername("danyuan");
		user.setAge(18);
		user.setDesc("this is a encrypt test .");		
		return user;
	}
	
	@GetMapping(value="testEncryptStringResponse")
	@ApiOperation(value="测试加密字符串响应信息", notes="测试加密字符串响应信息")
	@Encrypt(type = RSADataEncryptHandler.class)
	public String testEncryptStringResponse(){
		return "This just a string response encrypt test !";		
	}
}
