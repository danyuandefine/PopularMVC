/**  
* Title SignManager.java  
* Description  渠道账号秘钥验签、鉴权管理器
* @author danyuan
* @date Nov 7, 2018
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc;

import java.util.List;

import com.danyuanblog.framework.popularmvc.dto.ApiRequestParameter;
import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;

public interface SignManager {
	
	void setSignEncryptHandler(SignEncryptHandler signEncryptHandler);
	
	/**
	 * 生成数字签名
	 * @author danyuan
	 */
	String sign(String secret, String token, List<ApiRequestParameter> params);
	/**
	 * 验签操作
	 * @author danyuan
	 */
	boolean checkSign(String sign, String secret, String token, List<ApiRequestParameter> params);
}
