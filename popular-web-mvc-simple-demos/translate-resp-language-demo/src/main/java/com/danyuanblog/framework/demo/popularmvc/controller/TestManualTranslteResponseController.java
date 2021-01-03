/**  
* Title TestManualTranslteResponseController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;

@Api(tags = "测试手动翻译响应内容接口列表")
@RestController
public class TestManualTranslteResponseController {

	@Autowired
	@Lazy
	private LanguageTranslateManager languageTranslateManager;
	
	@GetMapping("getUserEnDesc")
	@ApiOperation(value="测试获取用户英文描述信息", notes="测试获取用户英文描述信息")
	public String getUserEnDesc(@RequestParam(name = "infoDict", required = false) String infoDict){
		if(StringUtils.isEmpty(infoDict)){
			infoDict = "danyuan.info";
		}
		return languageTranslateManager.get(infoDict, "en_US");
	}
}
