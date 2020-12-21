/**  
* Title TestResourceController.java  
* Description  测试获取静态资源接口
* @author danyuan
* @date Dec 20, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import io.swagger.annotations.Api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.danyuanblog.framework.popularmvc.utils.StringUtils;


@Controller
@Api(tags="测试获取静态资源")
public class TestResourceController {
	
	@GetMapping("testGetDocument")
	public ModelAndView testGetDocument(String resourcePath){
		if(StringUtils.isBlank(resourcePath)){
			resourcePath = "doc.html";
		}
		ModelAndView view = new ModelAndView();
		//view.setViewName("redirect:"+resourcePath);
		view.setViewName(resourcePath);
		return view;
	}
	
}
