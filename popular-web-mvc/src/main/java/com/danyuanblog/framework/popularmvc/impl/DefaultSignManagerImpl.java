/**  
* Title DefaultSignManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.danyuanblog.framework.popularmvc.SignManager;
import com.danyuanblog.framework.popularmvc.annotation.IgnoreSign;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.utils.BeanPropertyUtil;
import com.danyuanblog.framework.popularmvc.utils.EncryptUtils;

@Slf4j
@Setter
public class DefaultSignManagerImpl implements SignManager{

	private SignEncryptHandler signEncryptHandler;
	/**
	 * @author danyuan
	 */
	@Override
	public String sign(String secret, String token, List<ApiRequestParameter> params) {
		Map<String, String> nameValueMap = new HashMap<>();
		for(ApiRequestParameter param : params){
			if(param.isNeedSign()){
				nameValueMap.putAll(BeanPropertyUtil.objToStringMap(param.getParam(), param.getParamName(), IgnoreSign.class));
			}			
		}
		//按key进行字符串自然序排序后，进行拼接
		String content = EncryptUtils.sortAndMontage(nameValueMap);
		if(log.isTraceEnabled()){
			log.trace("secret:[{}],token:[{}],params to string sort result:[{}]", secret, token, content);
		}
		//拼接随机token码
		String data = token + content + token;
		//进行数字签名
		return signEncryptHandler.sign(secret, data);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public boolean checkSign(String sign, String secret, String token,
			List<ApiRequestParameter> params) {
		return sign.equals(this.sign(secret, token, params));
	}

}
