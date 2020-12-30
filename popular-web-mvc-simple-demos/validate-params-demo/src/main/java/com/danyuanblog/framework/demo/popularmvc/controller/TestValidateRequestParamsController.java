/**  
* Title TestValidateRequestParamsController.java  
* Description  
* @author danyuan
* @date Dec 27, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.ParamsCheckDto;

@RestController
@Validated
@Api(tags = "校验请求参数接口列表")
public class TestValidateRequestParamsController {
				
		@PostMapping("testModelParamsCheck")
		@ApiOperation(value="测试校验域模型请求参数", notes="测试校验域模型请求参数")
		public String testModelParamsCheck(@RequestBody ParamsCheckDto req){
			return "参数校验成功！";
		}
		
		@GetMapping("testBaseTypeParamsCheck")	
		@ApiOperation(value="测试校验基础类型请求参数", notes="测试校验基础类型请求参数")
		public Map<String, Object> testBaseTypeParamsCheck(
				@Valid @Size(min=6, max=20) @NotBlank @RequestParam String username, 
				@Valid @NotBlank @Size(min=6, max=32) @RequestParam String password, 
				@Valid @Range(max=200, min=1, message = "年龄只能在1-200岁之间!") @RequestParam Integer age){
			
			Map<String,Object> userInfos = new HashMap<>();
			userInfos.put("username", username);
			userInfos.put("age", age);
			userInfos.put("password", password);
			
			//返回数据
			return userInfos;
		}

}
