/**  
* Title TestController.java  
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.exception.BusinessException;

@RestController
@Validated
public class TestController {
	
	/**
	 * 参数自动校验、响应内容自动包装功能示例
	 * @author danyuan
	 */
	@GetMapping("queryUserInfo")	
	public Map<String,Object> queryUserInfo(@Valid @Size(max=20, min=6) @NotBlank @RequestParam String username){
		
		System.out.println("查询用户["+username+"]的信息!");
		
		//TODO: 执行业务逻辑
		
		//mock数据
		Map<String,Object> userInfos = new HashMap<>();
		userInfos.put("username", username);
		userInfos.put("age", 23);
		userInfos.put("sex", "男");
		userInfos.put("desc", "阳光乐观外向，喜欢唱歌、打篮球！");
		
		//返回数据
		return userInfos;
	}
	
	/**
	 * 业务异常的使用示例
	 * @author danyuan
	 */
	@PostMapping("user/regist")	
	public void userRegist(@Valid @NotEmpty String username, 
			@Valid @NotEmpty String password){
		//TODO: 执行业务逻辑，发现账号已存在，直接通过抛出异常的方式提示用户
		throw new BusinessException("用户名[{0}]已存在，请更换!").setParam(username);
	}
}
