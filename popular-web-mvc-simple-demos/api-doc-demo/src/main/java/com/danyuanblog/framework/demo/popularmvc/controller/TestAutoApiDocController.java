/**  
* Title TestAutoApiDocController.java  
* Description  测试自动生成的接口文档
* @author danyuan
* @date Dec 27, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAutoApiDocController {
	
	@GetMapping("{age}/testNoDescApi")
	public String testNoDescApi( @RequestParam(required = false) String username
			, @PathVariable Integer age){
		return "测试无接口注释时自动生成的接口文档!";
	}
}
