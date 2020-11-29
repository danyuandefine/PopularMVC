/**  
* Title TestSecurityComponentsController.java  
* Description  测试接口安全组件功能
* @author danyuan
* @date Nov 29, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.annotation.Decrypt;
import com.danyuanblog.framework.popularmvc.annotation.Encrypt;
import com.danyuanblog.framework.popularmvc.annotation.RequiredSign;
import com.danyuanblog.framework.popularmvc.consts.SignScope;
import com.danyuanblog.framework.popularmvc.demo.controller.dto.AccountInfo;
import com.danyuanblog.framework.popularmvc.demo.controller.dto.SnakeNode;
import com.danyuanblog.framework.popularmvc.demo.controller.dto.UserInfoDto;
import com.danyuanblog.framework.popularmvc.demo.controller.request.SignTestRequest;
import com.danyuanblog.framework.popularmvc.demo.controller.response.SignTestResponse;

@Api(tags = "测试接口安全组件功能")
@RestController
public class TestSecurityComponentsController {

	@PostMapping(value="testSign",
			name="测试请求响应加签验签功能")
	@ApiOperation(value="测试请求响应加签验签功能", notes="测试请求响应加签验签功能")
	@RequiredSign(scope = SignScope.BOTH)
	public SignTestResponse testSign(
			@RequestBody SignTestRequest req, String note, BigDecimal amount, @RequestParam("mobilePhone") String phone
			) {
		SignTestResponse resp = new SignTestResponse();
		AccountInfo account = new AccountInfo();
		account.setAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
		account.setFreezeAmount(new BigDecimal(2856).setScale(2, BigDecimal.ROUND_HALF_UP));
		account.setNote(note);
		resp.setPhone(phone);
		resp.setAccount(account);
		resp.setDesc("test response !");
		resp.setTest("校验后响应也不能为空哦!");
		resp.setAge(26);
		return resp;
	}
	
	@GetMapping(value="testCircleDependentSign",
			name="测试循环依赖响应加签功能")
	@ApiOperation(value="测试循环依赖响应加签功能", notes="测试循环依赖响应加签功能")
	@RequiredSign(scope = SignScope.RESPONSE)
	public SnakeNode testCircleDependentSign() {
		SnakeNode node1 = new SnakeNode();
		node1.setColor("red");
		node1.setDesc("这是第一节身体!");
		node1.setIndex(1);
		node1.setLen(50);
		SnakeNode node2 = new SnakeNode();
		node2.setColor("blue");
		node2.setDesc("这是第二节身体!");
		node2.setIndex(2);
		node2.setLen(100);
		SnakeNode node3 = new SnakeNode();
		node3.setColor("black");
		node3.setDesc("这是第三节身体!");
		node3.setIndex(3);
		node3.setLen(10);
		node1.setNext(node2);
		node2.setNext(node3);
		return node1;
	}
	
	@GetMapping(value="testDecryptParamAndResponse",
			name="测试加解密普通参数和响应信息")
	@ApiOperation(value="测试加解密普通参数和响应信息", notes="测试加解密普通参数和响应信息")
	@Encrypt
	public UserInfoDto testDecryptParamAndResponse(@RequestParam("desc") @Decrypt String desc) {
		UserInfoDto user = new UserInfoDto();
		user.setUsername("danyuan");
		user.setAge(18);
		user.setDesc("this is a encrypt test .");		
		return user;
	}
}
