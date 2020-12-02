/**  
* Title SystemParameterProperties.java  
* Description  
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.experimental.Accessors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "popularmvc.api.request.parameter")
public class SystemParameterConfigProperties implements InitializingBean{
	
	/**
	 * 其他自定义全局参数
	 */
	private Set<String> otherParams;
	
	public Map<String,String> toParamMap(){
		Map<String,String> map = new HashMap<>();
		if(otherParams != null && otherParams.size() > 0){
			otherParams.forEach(key -> {
				map.put(key, key);
			});
		}
		return map;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {//允许自定义系统参数名
		SystemParameterRenameProperties.DEFAULT_PARAM_MAP.putAll(this.toParamMap());
	}
	
}
