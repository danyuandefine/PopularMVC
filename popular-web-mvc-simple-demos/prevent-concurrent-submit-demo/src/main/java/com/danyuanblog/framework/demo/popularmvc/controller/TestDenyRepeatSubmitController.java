/**  
* Title TestDenyRepeatSubmitController.java  
* Description  
* @author danyuan
* @date Jan 5, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.UserInfoDto;
import com.danyuanblog.framework.popularmvc.annotation.RequiredNoRepeatSubmit;
import com.danyuanblog.framework.popularmvc.annotation.RequiredSign;
import com.danyuanblog.framework.popularmvc.consts.DenyRepeatSubmitType;
import com.danyuanblog.framework.popularmvc.utils.IOUtils;

@Api(tags = "测试接口防重复提交相关接口列表")
@RestController
@Slf4j
public class TestDenyRepeatSubmitController {
	
	@PostMapping(value="testUserAccountRegistNoRepeatSubmit",
			name="测试注册用户账号接口防重复提交功能")
	@ApiOperation(value="测试注册用户账号接口防重复提交功能", notes="防重复提交码由客户端生成，防止同一用户重复注册!")
	@RequiredNoRepeatSubmit(mode = DenyRepeatSubmitType.GENERATE_TOKEN)
	public void testUserAccountRegistNoRepeatSubmit(@RequestBody UserInfoDto userInfo){
		try {
			//模拟业务执行需要的耗时
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log.error(IOUtils.getThrowableInfo(e));
		}
		log.info("保存用户信息成功[{}]!",userInfo.toString());		
	}
	
	@PostMapping(value="testUserAccountRegistNoRepeatSubmitBySign",
			name="测试注册用户账号接口防重复提交功能数字签名模式")
	@ApiOperation(value="测试注册用户账号接口防重复提交功能数字签名模式", notes="使用数字签名，防止同一用户重复注册!")
	@RequiredNoRepeatSubmit(mode = DenyRepeatSubmitType.USE_SIGN)
	@RequiredSign
	public void testUserAccountRegistNoRepeatSubmitBySign(@RequestBody UserInfoDto userInfo){
		try {
			//模拟业务执行需要的耗时
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log.error(IOUtils.getThrowableInfo(e));
		}
		log.info("保存用户信息成功[{}]!",userInfo.toString());
	}
}
