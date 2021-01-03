/**  
* Title TestEncryptAndDecryptController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.UserInfoDto;
import com.danyuanblog.framework.popularmvc.annotation.Decrypt;
import com.danyuanblog.framework.popularmvc.annotation.Encrypt;

@Api(tags = "测试接口数据安全传输功能")
@RestController
public class TestEncryptAndDecryptController {

	@GetMapping(value="testDecryptParamAndResponse",
			name="测试加解密普通参数和响应信息")
	@ApiOperation(value="测试加解密普通参数和响应信息", notes="测试加解密普通参数和响应信息")
	@Encrypt
	public UserInfoDto testDecryptParamAndResponse(@RequestParam("desc") @Decrypt String desc) {
		UserInfoDto user = new UserInfoDto();
		user.setUsername("danyuan");
		user.setAge(18);
		user.setDesc("this is a encrypt test .");		
		return user;
	}
	
	@GetMapping(value="testEncryptListResponseData",
			name="测试加密列表响应信息")
	@ApiOperation(value="测试加密列表响应信息", notes="测试加密列表响应信息")
	public List<UserInfoDto> testEncryptListResponseData(){
		List<UserInfoDto> list = new ArrayList<>();
		UserInfoDto user1 = new UserInfoDto();
		user1.setUsername("danyuan");
		user1.setAge(18);
		user1.setDesc("this is a encrypt test 1 .");
		UserInfoDto user2 = new UserInfoDto();
		user2.setUsername("小明");
		user2.setAge(22);
		user2.setDesc("this is a encrypt test 2 .");
		list.add(user1);
		list.add(user2);
		return list;
		
	}
	
	@GetMapping(value="testEncryptStringResponse")
	@ApiOperation(value="测试加密字符串响应信息", notes="测试加密字符串响应信息")
	@Encrypt
	public String testEncryptStringResponse(){
		return "This just a string response encrypt test !";		
	}
}
