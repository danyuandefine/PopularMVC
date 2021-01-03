/**  
* Title TestSignController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.AccountInfo;
import com.danyuanblog.framework.demo.popularmvc.controller.dto.IgnoreRequestFiledSignRequest;
import com.danyuanblog.framework.demo.popularmvc.controller.dto.SignTestRequest;
import com.danyuanblog.framework.demo.popularmvc.controller.dto.SignTestResponse;
import com.danyuanblog.framework.popularmvc.annotation.IgnoreSign;
import com.danyuanblog.framework.popularmvc.annotation.RequiredSign;
import com.danyuanblog.framework.popularmvc.consts.SignScope;

@Api(tags = "测试接口验签加签功能")
@RestController
@Slf4j
public class TestSignController {

	@PostMapping("testRequestCheckSign")
	@ApiOperation(value="测试请求验签功能", notes="测试请求验签功能")
	@RequiredSign(scope = SignScope.REQUEST)
	public String testRequestCheckSign(
			@RequestBody SignTestRequest req,@RequestParam String note, BigDecimal amount, @RequestParam("mobilePhone") String phone
			) {
		log.info("phone:{},amount:{}", phone, amount);
		return "SUCCESS";
	}
	
	@GetMapping("testResponseSign")
	@ApiOperation(value="测试响应加签功能", notes="测试响应加签功能")
	@RequiredSign(scope = SignScope.RESPONSE)
	public SignTestResponse testResponseSign() {
		BigDecimal amount = new BigDecimal(100);
		SignTestResponse resp = new SignTestResponse();
		AccountInfo account = new AccountInfo();
		account.setAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
		account.setFreezeAmount(new BigDecimal(2856).setScale(2, BigDecimal.ROUND_HALF_UP));
		account.setNote("this just a response sign test !");
		resp.setPhone("13823117861");
		resp.setAccount(account);
		resp.setDesc("test response !");
		resp.setTest("校验后响应也不能为空哦!");
		resp.setAge(26);
		return resp;
	}
	
	@PostMapping("testIgnoreRequestFiledSign")
	@ApiOperation(value="测试请求响应验签加签忽略字段等功能", notes="测试请求响应验签加签忽略字段等功能")
	@RequiredSign(scope = SignScope.BOTH)
	public String testIgnoreRequestFiledSign(@RequestBody IgnoreRequestFiledSignRequest req, 
			@IgnoreSign @RequestBody SignTestRequest testReq, @RequestParam String email){
		log.info("phone:{},account:{}", req.getPhone(), req.getAccount());
		return "SUCCESS";
	}
}
