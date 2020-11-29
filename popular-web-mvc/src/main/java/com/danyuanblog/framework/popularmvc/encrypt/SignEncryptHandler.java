/**  
* Title SignEncryptHandler.java  
* Description  
* @author danyuan
* @date Nov 14, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.encrypt;

public interface SignEncryptHandler {
	
	String sign(String secrect, String body);
}
