/**  
 * Title PopularMvcManagerFactory.java  
 * Description  组装和生产管理器
 * @author danyuan
 * @date Oct 31, 2020
 * @version 1.0.0
 * site: www.danyuanblog.com
 */
package com.danyuanblog.framework.popularmvc.config;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.danyuanblog.framework.popularmvc.ApplicationManager;
import com.danyuanblog.framework.popularmvc.CacheManager;
import com.danyuanblog.framework.popularmvc.CheckRepeatManager;
import com.danyuanblog.framework.popularmvc.InvokeApiLogManager;
import com.danyuanblog.framework.popularmvc.InvokeTimesManager;
import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;
import com.danyuanblog.framework.popularmvc.SecretManager;
import com.danyuanblog.framework.popularmvc.SessionManager;
import com.danyuanblog.framework.popularmvc.SignManager;
import com.danyuanblog.framework.popularmvc.cache.LocalReadCacheContainer;
import com.danyuanblog.framework.popularmvc.cache.LocalWriteCacheContainer;
import com.danyuanblog.framework.popularmvc.encrypt.DataEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.impl.AESDataEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.impl.Sha1SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.impl.DefaultApplicationManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultCacheManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultCheckRepeatManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultInvokeApiLogManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultInvokeTimesManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultLanguageTranslateManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultSecretManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultSessionManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultSignManagerImpl;
import com.danyuanblog.framework.popularmvc.properties.ChannelConfigProperties;
import com.danyuanblog.framework.popularmvc.properties.PopularMvcConfig;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterRenameProperties;


@Configuration
@ConditionalOnProperty(name={"popularmvc.enable"},havingValue = "true")
@AutoConfigureAfter(PopularMvcWebConfigurer.class)
public class PopularMvcBeanConfigurer {

	@Bean(name="defaultApplicationManager")
	@ConditionalOnMissingBean(ApplicationManager.class)
	public ApplicationManager applicationManager() {
		return new DefaultApplicationManagerImpl();
	}

	@Bean(name="defaultInvokeTimesManager")
	@ConditionalOnMissingBean(InvokeTimesManager.class)
	public InvokeTimesManager invokeTimesManager(@Autowired ApplicationManager applicationManager, @Autowired InvokeApiLogManager invokeApiLogManager) {
		InvokeTimesManager manager = new DefaultInvokeTimesManagerImpl();
		//组装管理器
		manager.setApplicationManager(applicationManager);
		manager.setInvokeApiLogManager(invokeApiLogManager);
		return manager;
	}

	@Bean(name="defaultCheckRepeatManager")
	@ConditionalOnMissingBean(CheckRepeatManager.class)
	public CheckRepeatManager checkRepeatManager(@Autowired PopularMvcConfig popularMvcConfig, @Autowired CacheManager cacheManager) {
		DefaultCheckRepeatManagerImpl checkRepeatManager= new DefaultCheckRepeatManagerImpl();
		checkRepeatManager.setCacheManager(cacheManager);
		checkRepeatManager.setPopularMvcConfig(popularMvcConfig);
		return checkRepeatManager;
	}

	@Bean(name="defaultInvokeApiLogManager")
	@ConditionalOnMissingBean(InvokeApiLogManager.class)
	public InvokeApiLogManager invokeApiLogManager() {
		return new DefaultInvokeApiLogManagerImpl();
	}

	@Bean(name="defaultSessionManager")
	@ConditionalOnMissingBean(SessionManager.class)
	public SessionManager sessionManager(@Autowired CacheManager cacheManager, @Autowired PopularMvcConfig popularMvcConfig) {
		DefaultSessionManagerImpl sessionManager = new DefaultSessionManagerImpl();
		sessionManager.setCacheManager(cacheManager);
		sessionManager.setExpireSeconds(popularMvcConfig.getSessionExpireSeconds());
		return sessionManager;
	}
	
	@Bean(name="defaultSignManager")
	@ConditionalOnMissingBean(SignManager.class)
	public SignManager signManager(@Autowired SignEncryptHandler signEncryptHandler) {
		DefaultSignManagerImpl signManager = new DefaultSignManagerImpl();
		return signManager;
	}
	
	//默认国际化处理器
	@Bean(name="defaultLanguageTranslateManager")
	public LanguageTranslateManager defaultLanguageTranslateManager(@Autowired MessageSource messageSource, @Autowired PopularMvcConfig popularMvcConfig) {
		return new DefaultLanguageTranslateManagerImpl(messageSource, new Locale(popularMvcConfig.getLocale()));
	}
	
	@Bean(name="defaultSignEncryptHandler")
	public SignEncryptHandler signEncryptHandler() {
		return new Sha1SignEncryptHandler();
	}
	
	@Bean(name="defaultDataEncryptHandler")
	public DataEncryptHandler dataEncryptHandler(@Autowired SecretManager secretManager) {
		return new AESDataEncryptHandler().setSecretManager(secretManager);
	}
	
	@Bean(name="defaultSecretManager")
	@ConditionalOnMissingBean(SecretManager.class)
	public SecretManager secretManager(@Autowired ChannelConfigProperties channelConfigProperties, @Autowired SystemParameterRenameProperties systemParameterProperties) {
		DefaultSecretManagerImpl secretManager = new DefaultSecretManagerImpl(channelConfigProperties, systemParameterProperties);
		return secretManager;
	}
	
	@Bean(name="defaultCacheManager")
	public CacheManager cacheManager(@Autowired LocalReadCacheContainer localReadCacheContainer,
			@Autowired LocalWriteCacheContainer localWriteCacheContainer) {
		DefaultCacheManagerImpl cacheManager = new DefaultCacheManagerImpl();
		cacheManager.setLocalReadCacheContainer(localReadCacheContainer);
		cacheManager.setLocalWriteCacheContainer(localWriteCacheContainer);
		return cacheManager;
	}
	
}
