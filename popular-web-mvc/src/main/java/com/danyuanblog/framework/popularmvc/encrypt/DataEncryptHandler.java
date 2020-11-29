/**  
* Title DataEncryptHandler.java  
* Description  数据加解密处理器
* @author danyuan
* @date Nov 15, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.encrypt;

public interface DataEncryptHandler {
	String encrypt(String appId, String channelId, String userId, String content) throws Throwable ;
	String decrypt(String appId, String channelId, String userId, String content) throws Throwable ;
}
