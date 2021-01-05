/**  
* Title TestMethodResultCacheController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.UserDto;
import com.danyuanblog.framework.demo.popularmvc.controller.service.UserService;

@Api(tags = "测试方法结果查询缓存相关接口列表")
@RestController
public class TestMethodResultCacheController {

	@Autowired
	private UserService userService;
	
	@GetMapping("getUserInfo")
	@ApiOperation(value="测试查询用户信息", notes="测试查询用户信息")
	public UserDto getUserInfo(){
		return userService.getUserInfo();
	}
	
	@PostMapping("updateUserInfo")
	@ApiOperation(value="测试更新用户信息", notes="测试更新用户信息")
	public void updateUserInfo(@RequestBody UserDto user){
		userService.updateUserInfo(user);
	}
}
