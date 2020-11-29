/**  
* Title ChannelConfigProperties.java  
* Description  
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.properties;

import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.danyuanblog.framework.popularmvc.dto.ApiPermissionDto;

@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "popularmvc.api")
public class ChannelConfigProperties {
	
	public static final String DEFAULT_APP = "default";
	/**
	 * 渠道ID<br>
	 *   应用标识:<br>
	 *     参数名：参数值
	 */
	private Map<String,  Map<String, ApiPermissionDto>> channels;
	
}
