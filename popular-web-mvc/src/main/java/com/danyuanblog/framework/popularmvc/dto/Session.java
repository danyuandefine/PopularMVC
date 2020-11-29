/**  
* Title Sesssion.java  
* Description  会话信息
* @author danyuan
* @date Oct 19, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Session implements Serializable{
	/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 应用ID，可以按应用维度控制api的访问权限
	 */
	private String appId;
	/**
	 * 渠道ID，如：安卓、iOS、微信、官网等等，可以按访问渠道控制api的访问权限
	 */
	private String channelId;
	
	/**
	 * 会话ID，用于便于用户认证后在一定时间段内进行有状态的访问后续接口
	 */
	private String sessionId;
	/**
	 * 用户访问唯一标识
	 */
	private String userId;
	/**
	 * 客户端通过秘钥+参数进行验签后生成的数字签名
	 */
	private String sign;
	/**
	 * 客户端ID,用于区分是哪个APP或者其他客户端的不同分支
	 */
	private String clientId;
	/**
	 * 客户端版本号
	 */
	private String versionCode;
	/**
	 * 防重复提交码,用来控制接口防重复提交
	 */
	private String repeatCode;
	/**
	 * 时间戳
	 */
	private Long timestamp;
	/**
	 * 客户端IP地址
	 */
	private String clientIp;
	
	/****************国际化相关的系统参数****************/	
	/**
	 * 语言，如： en_US、zh_CN等等
	 */
	private String locale;
	/**
	 * 货币类型,如：USD、CNY等等
	 */
	private String currency;
	/**
	 * 国家码，如： US、CN等等
	 */
	private String countryCode;
	/**
	 * 时区,如： GTM+8   北京时间所在时区
	 */
	private String timeZone;
	
	/**
	 * 其他可自定义配置的额外参数
	 */
	private Map<String, Object> expandParams;
}
