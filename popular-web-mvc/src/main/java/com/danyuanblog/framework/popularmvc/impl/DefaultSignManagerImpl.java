/**  
* Title DefaultSignManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import com.danyuanblog.framework.popularmvc.SignManager;
import com.danyuanblog.framework.popularmvc.annotation.IgnoreSign;
import com.danyuanblog.framework.popularmvc.annotation.RequiredSign;
import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.exception.BusinessException;
import com.danyuanblog.framework.popularmvc.utils.BeanPropertyUtil;
import com.danyuanblog.framework.popularmvc.utils.EncryptUtils;
import com.danyuanblog.framework.popularmvc.utils.SpringBeanUtil;

@Slf4j
public class DefaultSignManagerImpl implements SignManager{

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
		Method method = RequestContext.getContext().getMethod();
		RequiredSign requiredSign =method.getAnnotation(RequiredSign.class);
		SignEncryptHandler signEncryptHandler = SpringBeanUtil.getBean(requiredSign.type());
		if(signEncryptHandler != null){
			//进行数字签名
			return signEncryptHandler.sign(secret, data);
		}else{
			log.warn("数字签名生成器[{}]实例未找到,请先配置!", requiredSign.type().toString());
			throw new BusinessException(ErrorCodes.NOT_FOUND_CONFIG).setParam(requiredSign.type().toString());
		} 
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
