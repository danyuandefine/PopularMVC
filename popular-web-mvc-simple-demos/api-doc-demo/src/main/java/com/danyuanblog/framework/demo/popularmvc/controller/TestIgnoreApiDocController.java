/**  
* Title TestIgnoreApiDocController.java  
* Description  测试不展示的接口文档，但是接口是可用的，
* 只是不接口文档中不显示而已，一些不希望暴露给使用者调用的接口可以使用此方式。
* @author danyuan
* @date Dec 27, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class TestIgnoreApiDocController {
	
	@GetMapping("testInnerApi")
	public String testInnerApi(){
		return "调用内部隐私接口成功!";
	}
}
