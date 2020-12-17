/**  
* Title DefaultSessionManagerImpl.java  
* Description  
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import java.util.Map;

import org.springframework.beans.BeanUtils;

import lombok.Setter;

import com.danyuanblog.framework.popularmvc.CacheManager;
import com.danyuanblog.framework.popularmvc.SessionManager;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.dto.Session;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;
import com.danyuanblog.framework.popularmvc.utils.UUIDGenerator;

@Setter
public class DefaultSessionManagerImpl implements SessionManager {

	private CacheManager cacheManager;
	
	private long expireSeconds;
	
	/**
	 * @author danyuan
	 */
	@Override
	public Session getCurrentSession(boolean createNew) {
		Session session = RequestContext.getContext().getSession();
		if(session == null && createNew){
			//创建会话
			String sessionId = UUIDGenerator.getUUID();
			session = new Session();
			RequestContext context = RequestContext.getContext();
			session.setSessionId(sessionId)
				.setAppId(context.getAppId())
				.setChannelId(context.getChannelId())
				.setClientId(context.getClientId())
				.setClientIp(context.getClientIp())
				.setCountryCode(context.getCountryCode())
				.setCurrency(context.getCurrency())
				.setLocale(context.getLocale())
				.setTimeZone(context.getTimeZone())
				.setUserId(context.getUserId())
				.setVersionCode(context.getVersionCode())
				;
			
			cacheManager.set(sessionId, session, expireSeconds, false);
			RequestContext.getContext().setSession(session);
			RequestContext.getContext().setAttachment(
					SystemParameterRenameProperties.DEFAULT_PARAM_MAP.get(SystemParameterRenameProperties.SESSION_ID), sessionId);
		}
		return session;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void persist(Session session) {
		//创建会话
		String sessionId = UUIDGenerator.getUUID();
		session.setSessionId(sessionId);
		cacheManager.set(sessionId, session, expireSeconds, false);
		RequestContext.getContext().setSession(session);
		RequestContext.getContext().setAttachment(
				SystemParameterRenameProperties.DEFAULT_PARAM_MAP.get(SystemParameterRenameProperties.SESSION_ID), sessionId);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public Session get(String sessionId) {
		Session session = cacheManager.get(sessionId, Session.class, false);
		return session;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public boolean exists(String sessionId) {
		return cacheManager.exists(sessionId, false);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void setExpireSeconds(long seconds) {
		this.expireSeconds = seconds;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void refreshAliveTime(String sessionId) {
		cacheManager.setExpire(sessionId, expireSeconds, false);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void remove(String sessionId) {
		cacheManager.remove(sessionId, false);
		RequestContext.getContext().setSession(null);
	}

}
