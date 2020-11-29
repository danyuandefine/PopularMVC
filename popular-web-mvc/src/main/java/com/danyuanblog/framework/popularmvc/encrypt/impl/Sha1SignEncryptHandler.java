/**  
* Title Sha1SignEncryptHandler.java  
* Description  
* @author danyuan
* @date Nov 14, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.encrypt.impl;

import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.utils.EncryptUtils;

public class Sha1SignEncryptHandler implements SignEncryptHandler {

	/**
	 * @author danyuan
	 */
	@Override
	public String sign(String secrect, String body) {
		return EncryptUtils.encrytSHA1Hex(secrect, body);
	}

}
