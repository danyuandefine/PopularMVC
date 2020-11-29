/**  
* Title ErrorController.java  
* Description  自定义系统未知错误处理接口响应
* @author danyuan
* @date Sep 10, 2019
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.controller;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.controller.response.ErrorResponse;

@Api(tags = "测试")
@RestController
public class CustomErrorController implements ErrorController{
	@Autowired
	private LanguageTranslateManager languageTranslateManager;
	
	@ApiOperation(value = "默认错误处理接口")
    @RequestMapping("/error")
    public DefaultResponseWrapper<ErrorResponse> error(HttpServletRequest request, HttpServletResponse response){
		
		DefaultResponseWrapper<ErrorResponse> resp = null;
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String originalUri =  request.getRequestURI();
        if(statusCode != null && (statusCode != 200)){//是否是存在错误地址重定向URL
        	if(RequestContext.getContext().getApiInfo() != null 
        			&& !StringUtils.isEmpty(RequestContext.getContext().getApiInfo().getApiUri())){
        		originalUri = RequestContext.getContext().getApiInfo().getApiUri();
        	}else{
        		originalUri = (String) request.getAttribute("javax.servlet.forward.servlet_path");
        	}        	
        }
        if(statusCode == HttpStatus.NOT_FOUND.value()){        	      	
        	String systemError = languageTranslateManager.get(
    				ErrorCodes.INVALID_METHOD.getMsgCode(), 
    				RequestContext.getContext().getLocale(),
    				originalUri);
    		resp = new DefaultResponseWrapper<>(ErrorCodes.INVALID_METHOD);
            resp.setMsg(systemError);
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }else{
        	//其他未知错误
        	String systemError = languageTranslateManager.get(
    				ErrorCodes.UNKOWN_ERROR.getMsgCode(), 
    				RequestContext.getContext().getLocale(),
    				originalUri);
    		resp = new DefaultResponseWrapper<>(ErrorCodes.UNKOWN_ERROR);
            resp.setMsg(systemError);
            response.setStatus(statusCode);
        }
		
        return resp;
    }

	/**
	 * @author danyuan
	 */
	@Override
	public String getErrorPath() {
		 return "/error";
	}
    
}
