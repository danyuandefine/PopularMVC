/**  
* Title TestDecorateApiDocController.java  
* Description  测试为接口添加描述信息
* @author danyuan
* @date Dec 27, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试为接口添加描述信息")
public class TestDecorateApiDocController {
	
	@GetMapping("testDescBaseTypeParamApi")
	@ApiOperation(value="测试为基础类型请求参数添加说明", notes="测试为基础类型请求参数添加说明")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", required = false, defaultValue = "danyuan"),
		@ApiImplicitParam(name = "age", value = "年龄", required = true, defaultValue = "23"),
		 })
	public String testDescBaseTypeParamApi( 
			@RequestParam(required = false) String username
			, @RequestParam Integer age){
		return "测试为基础类型请求参数添加说明!";
	}
	
	@PostMapping("testDescModelTypeParamApi")
	@ApiOperation(value="测试为域模型类型请求参数添加说明", notes="测试为域模型类型请求参数添加说明")
	public String testDescModelTypeParamApi(@RequestBody ModelDto req){
		return "测试为域模型类型请求参数添加说明！";
	}
}
