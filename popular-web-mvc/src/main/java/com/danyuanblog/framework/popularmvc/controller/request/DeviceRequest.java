/**  
* Title BaseDeviceRequest.java  
* Description  
* @author danyuan
* @date Nov 6, 2018
* @version 1.0.0
* blog www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain=true)
public class DeviceRequest extends BaseRequest{

	/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 手机系统版本
	 */
	private String phoneVersion;
	
}
