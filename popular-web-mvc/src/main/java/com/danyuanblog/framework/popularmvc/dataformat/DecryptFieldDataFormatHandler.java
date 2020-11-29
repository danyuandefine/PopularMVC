/**  
* Title DecryptFieldDataFormatHandler.java  
* Description  
* @author danyuan
* @date Nov 16, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.dataformat;

import java.lang.annotation.Annotation;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.danyuanblog.framework.popularmvc.annotation.Decrypt;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.encrypt.DataEncryptHandler;
import com.danyuanblog.framework.popularmvc.utils.SpringBeanUtil;

@Component
@Slf4j
public class DecryptFieldDataFormatHandler extends AbstractFieldDataFormatHandler{

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
	@Override
	public Object handle(Object data, Map<Class<?>, Annotation> annotations)
			throws Throwable {
		Annotation anno = annotations.get(Decrypt.class);
		if(anno != null){
			Decrypt decrypt = (Decrypt) anno;
			if(decrypt.value()){
				DataEncryptHandler dataEncryptHandler = SpringBeanUtil.getBean(decrypt.type());
				if(dataEncryptHandler != null){
					if(data.getClass().equals(String.class)){
						return dataEncryptHandler.decrypt(RequestContext.getContext().getAppId(), 
								RequestContext.getContext().getChannelId(), 
								RequestContext.getContext().getUserId(), 
								data.toString());
					}else{
						if(log.isTraceEnabled()){
							log.trace("@Decrypt注解只能使用在String类型字段上,当前类型:{}!", data.getClass().getName());
						}										
					}									
				}else{
					log.warn("spring 容器中未找到 此类{}的实例对象!", decrypt.type());
				}
			}
		}
		return data;
	}

}
