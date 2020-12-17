/**  
* Title TestSessionController.java  
* Description  
* @author danyuan
* @date Dec 17, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.SessionManager;
import com.danyuanblog.framework.popularmvc.annotation.RequiredSession;
import com.danyuanblog.framework.popularmvc.dto.Session;

@Api(tags = "测试会话信息")
@RestController
public class TestSessionController {
	
	@Autowired
	private SessionManager sessionManager;
	
	@GetMapping(value="login",
			name="测试登录获取用户会话ID")
	@ApiOperation(value="测试登录获取用户会话ID", notes="测试登录获取用户会话ID")
	public Session login(String account, String password){
		Session session = sessionManager.getCurrentSession(true);
		Map<String,Object> map = new HashMap<>();
		map.put("account", account);
		session.setExpandParams(map);
		return session;
	}
	
	@GetMapping(value="queryAccountInfo",
			name="测试通过会话ID获取用户账户信息")
	@ApiOperation(value="测试通过会话ID获取用户账户信息", notes="测试通过会话ID获取用户账户信息")
	@RequiredSession
	public String queryAccountInfo(){
		Session session = sessionManager.getCurrentSession(false);
		return session.getExpandParams().get("account").toString();
	}
}
