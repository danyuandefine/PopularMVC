/**  
* Title NeedSign.java  
* Description  是否需要开启验签
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.danyuanblog.framework.popularmvc.consts.SignScope;
import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.impl.Sha1SignEncryptHandler;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredSign {
	boolean value() default true;
	SignScope scope() default SignScope.REQUEST;
	/**
	 * 手动指定加密处理器,默认采用AES加解密
	 * @author danyuan
	 */
	Class<? extends SignEncryptHandler> type() default Sha1SignEncryptHandler.class;
}
