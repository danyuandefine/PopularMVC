/**  
* Title ErrorController.java  
* Description  自定义系统未知错误处理接口响应
* @author danyuan
* @date Sep 10, 2019
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.controller;



import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;
import com.danyuanblog.framework.popularmvc.component.BussinessErrorCodeInterpreter;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.controller.response.ErrorCodeListResponse;
import com.danyuanblog.framework.popularmvc.dto.ErrorCodeDto;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;

@Api(tags = "系统错误码管理")
@RestController
public class ErrorCodeController {
	@Autowired
	private LanguageTranslateManager languageTranslateManager;
	
	@Autowired
	private BussinessErrorCodeInterpreter bussinessErrorCodeInterpreter;
	
	@GetMapping(value="1.0.1/system/errorList",
			name="查询错误码列表")
	@ApiOperation(value="查询错误码列表", notes="查询错误码列表")
	public DefaultResponseWrapper<ErrorCodeListResponse> errorList(
			) throws BusinessException{
		List<ErrorCodeDto> systemCodes = BussinessErrorCodeInterpreter.getSystemCodes();
		for(ErrorCodeDto error : systemCodes){
			error.setMsg(languageTranslateManager.get(error.getError(),"zh_CN"));			
		}
		return new DefaultResponseWrapper<>(new ErrorCodeListResponse().setBussinessErrors(bussinessErrorCodeInterpreter.getBussinessCodes())
			.setSystemErrors(systemCodes));
	}
}
