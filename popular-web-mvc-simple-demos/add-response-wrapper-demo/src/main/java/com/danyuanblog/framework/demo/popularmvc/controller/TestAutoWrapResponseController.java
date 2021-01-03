/**  
 * Title TestAutoWrapResponseController.java  
 * Description  返回包装后的响应值
 * 
 * @author danyuan
 * @date Jan 3, 2021
 * @version 1.0.0
 * site: www.danyuanblog.com
 */
package com.danyuanblog.framework.demo.popularmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.demo.popularmvc.controller.dto.CustomResponse;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;

@Api(tags = "返回自动包装后的响应值接口列表")
@RestController
@RequestMapping("wrapped")
public class TestAutoWrapResponseController {

	@GetMapping("emptyResponse")
	@ApiOperation(value = "测试返回空原始值", notes = "测试返回空原始值")
	public void emptyResponse() {

	}

	@GetMapping("intResponse")
	@ApiOperation(value = "测试返回整型原始值", notes = "测试返回整型原始值")
	public int intResponse() {
		return 22;
	}

	@GetMapping("booleanResponse")
	@ApiOperation(value = "测试返回boolean型原始值", notes = "测试返回boolean型原始值")
	public boolean booleanResponse() {
		return true;
	}

	@GetMapping("stringResponse")
	@ApiOperation(value = "测试返回字符串型原始值", notes = "测试返回字符串型原始值")
	public String stringResponse() {
		return "this just a string response test !";
	}

	@GetMapping("mapResponse")
	@ApiOperation(value = "测试返回map型原始值", notes = "测试返回map型原始值")
	public Map<String, Object> mapResponse() {
		// mock数据
		Map<String, Object> userInfos = new HashMap<>();
		userInfos.put("username", "小明");
		userInfos.put("age", 23);
		userInfos.put("sex", "男");
		userInfos.put("desc", "阳光乐观外向，喜欢唱歌、打篮球！");

		// 返回数据
		return userInfos;
	}
	
	@GetMapping("listResponse")
	@ApiOperation(value = "测试返回list型原始值", notes = "测试返回list型原始值")
	public List<String> listResponse() {
		// mock数据
		List<String> list = new ArrayList<>();
		list.add("first");
		list.add("second");
		list.add("third");
		// 返回数据
		return list;
	}
	
	@GetMapping("customObjectResponse")
	@ApiOperation(value = "测试返回自定义对象类型原始值", notes = "测试返回自定义对象类型原始值")
	public CustomResponse customObjectResponse() {
		// mock数据
		CustomResponse resp = new CustomResponse();
		resp.setUsername("小明")
			.setAge(23)
			.setSex(true)
			.setDesc("阳光、帅气、活泼、积极、向上！");
		// 返回数据
		return resp;
	}
	
	@GetMapping("wrappedResponse")
	@ApiOperation(value = "测试返回已经被包装过的原始值", notes = "测试返回已经被包装过的原始值")
	public DefaultResponseWrapper<List<String>> wrappedResponse() {
		// mock数据
		List<String> list = new ArrayList<>();
		list.add("first");
		list.add("second");
		list.add("third");
		// 返回数据
		return new DefaultResponseWrapper<>(list);
	}
	
}
