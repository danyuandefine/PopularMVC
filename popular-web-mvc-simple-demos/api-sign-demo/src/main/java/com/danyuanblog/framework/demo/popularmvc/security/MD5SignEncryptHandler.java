/**  
* Title MD5SignEncryptHandler.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.security;

import org.springframework.stereotype.Component;

import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.utils.EncryptUtils;

@Component
public class MD5SignEncryptHandler implements SignEncryptHandler {

	/**
	 * @author danyuan
	 */
	@Override
	public String sign(String secrect, String body) {
		return EncryptUtils.encryptMD5Hex(secrect, body);
	}

}
