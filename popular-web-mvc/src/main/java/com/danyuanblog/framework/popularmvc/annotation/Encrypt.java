/**  
* Title Encrypt.java  
* Description  
* @author danyuan
* @date Nov 15, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.danyuanblog.framework.popularmvc.encrypt.DataEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.impl.AESDataEncryptHandler;

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
	/**
	 * 是否启用加密
	 * @author danyuan
	 */
	boolean value() default true;
	/**
	 * 手动指定加密处理器,默认采用AES加解密
	 * @author danyuan
	 */
	Class<? extends DataEncryptHandler> type() default AESDataEncryptHandler.class;
}
