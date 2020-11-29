/**  
* Title DenyRepeatSubmitType.java  
* Description  
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.consts;

public enum DenyRepeatSubmitType {
	/**
	 * 使用从服务器获取防重复提交码模式
	 */
	GENERATE_TOKEN,
	/**
	 * 使用数字签名作为防重码模式
	 */
	USE_SIGN
}
