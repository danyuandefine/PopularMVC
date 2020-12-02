/**  
* Title CustomSystemErrorCodeHandler.java  
* Description  处理自定义系统错误码数值
* @author danyuan
* @date Nov 28, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.response.enhance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.properties.ErrorCodeProperties;
import com.danyuanblog.framework.popularmvc.properties.ResponseSystemFieldRenameProperties;

@Service
public class CustomSystemErrorCodeHandler extends AbstractResponseEnhanceHandler {
	
	@Autowired
	private ErrorCodeProperties errorCodeProperties;
	
	@Autowired
	private ResponseSystemFieldRenameProperties responseWrapperProperties;

	/**
	 * @author danyuan
	 */
	@Override
	public int order() {
		return 0;
	}

	/**
	 * @author danyuan
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object enhance(Object resp) {
		if(errorCodeProperties.getSystemErrorCodes() != null && errorCodeProperties.getSystemErrorCodes().size() > 0){
			if(resp instanceof Map){
				Map<String, Object> map = (Map<String, Object>)resp;
				Object str = map.get(responseWrapperProperties.getCode());
				if(!StringUtils.isEmpty(str)){
					Integer code = Integer.valueOf(str.toString());
					String codeName = ErrorCodes.getByCode(code).name();
					if(map.containsKey(codeName)){
						map.put(responseWrapperProperties.getCode(), errorCodeProperties.getSystemErrorCodes().get(codeName));
					}					
				}				
			}
		}		
		return resp;
	}

}
