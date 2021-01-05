/**  
* Title UserService.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller.service;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.UserDto;
import com.danyuanblog.framework.popularmvc.annotation.CacheMethodResult;
import com.danyuanblog.framework.popularmvc.annotation.CacheMethodResultEvict;

@Service
@Slf4j
public class UserService {
	
	private UserDto user = new UserDto()
		.setUsername("小明")
		.setAge(28)
		.setEmail("16721368817@163.com")
		.setPhone("16721368817")
		.setSex(true)
		.setDesc("阳光乐观快乐的小伙子!");

	/**
	 * 查询用户信息
	 * @author danyuan
	 */
	@CacheMethodResult(prefix="UserService:user", expireSeconds=600)
	public UserDto getUserInfo(){
		
		log.info("query user info from database !");
		return user.setLoadTime(new Date());
	}
	
	/**
	 * 更新用户信息
	 * @author danyuan
	 */
	@CacheMethodResultEvict(prefix="UserService:user", expireSeconds=600)
	public void updateUserInfo(UserDto user){
		this.user = user;
	} 
}
