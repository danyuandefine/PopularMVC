/**  
* Title TestValidateResonseParamsController.java  
* Description  
* @author danyuan
* @date Dec 27, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.ParamsCheckDto;

@RestController
@Api(tags = "校验接口响应参数接口列表")
public class TestValidateResonseParamsController {
		
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
