/**  
* Title CheckRepeatManager.java  
* Description  防重复提交码
* 针对需要防止客户端误操作将需要更新的数据重复提交多次，导致服务器数据异常，特别是资金、账户相关的操作。
* 操作方式：客户端操作数据之前可以获取一个有时效的防重码，提交数据前先校验防重码是否被使用，如果未被使用，则成功执行更新操作，否则返回失败
* @author danyuan
* @date Nov 8, 2018
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc;

public interface CheckRepeatManager {
	/**
	 * 设置防重码过期时间,避免客户端申请了token结果又不使用，浪费内存资源
	 * @param second
	 */
	void setTokenTimeout(int second);
	/**
	 * 获取一个防重复提交码
	 * @param token 防重码键值,推荐用时间戳
	 * @return
	 * @throws BusinessException
	 */
	String generateToken(String key);
	/**
	 * 校验防重复提交码是否正确
	 * @param key 防重码键值, 时间戳或者sign值
	 * @param token 需要校验的防重码
	 * @throws BusinessException
	 */
	void check(String key,String token);
}
