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
import com.danyuanblog.framework.popularmvc.CheckRepeatManager;
import com.danyuanblog.framework.popularmvc.InvokeApiLogManager;
import com.danyuanblog.framework.popularmvc.InvokeTimesManager;
import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;
import com.danyuanblog.framework.popularmvc.SecretManager;
import com.danyuanblog.framework.popularmvc.SessionManager;
import com.danyuanblog.framework.popularmvc.SignManager;
import com.danyuanblog.framework.popularmvc.encrypt.DataEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.impl.AESDataEncryptHandler;
import com.danyuanblog.framework.popularmvc.encrypt.impl.Sha1SignEncryptHandler;
import com.danyuanblog.framework.popularmvc.impl.CacheableLanguageTranslateManagerImpl;
import com.danyuanblog.framework.popularmvc.impl.DefaultApplicationManagerImpl;
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

	@Bean
	@ConditionalOnMissingBean(ApplicationManager.class)
	public ApplicationManager applicationManager() {
		return new DefaultApplicationManagerImpl();
	}

	@Bean
	@ConditionalOnMissingBean(InvokeTimesManager.class)
	public InvokeTimesManager invokeTimesManager(@Autowired ApplicationManager applicationManager, @Autowired InvokeApiLogManager invokeApiLogManager) {
		InvokeTimesManager manager = new DefaultInvokeTimesManagerImpl();
		//组装管理器
		manager.setApplicationManager(applicationManager);
		manager.setInvokeApiLogManager(invokeApiLogManager);
		return manager;
	}

	@Bean
	@ConditionalOnMissingBean(CheckRepeatManager.class)
	public CheckRepeatManager checkRepeatManager() {
		return new DefaultCheckRepeatManagerImpl();
	}

	@Bean
	@ConditionalOnMissingBean(InvokeApiLogManager.class)
	public InvokeApiLogManager invokeApiLogManager() {
		return new DefaultInvokeApiLogManagerImpl();
	}

	@Bean
	@ConditionalOnMissingBean(SessionManager.class)
	public SessionManager sessionManager() {
		return new DefaultSessionManagerImpl();
	}
	
	@Bean
	@ConditionalOnMissingBean(SignManager.class)
	public SignManager signManager(@Autowired SignEncryptHandler signEncryptHandler) {
		SignManager signManager = new DefaultSignManagerImpl();
		signManager.setSignEncryptHandler(signEncryptHandler);
		return signManager;
	}
	
	//自定义国际化处理器,如果没有定义，就使用spring i18n
	@Bean
	@ConditionalOnMissingBean(value = LanguageTranslateManager.class, ignored = CacheableLanguageTranslateManagerImpl.class)
	public LanguageTranslateManager languageTranslateManager(@Autowired MessageSource messageSource, @Autowired PopularMvcConfig popularMvcConfig) {
		return new DefaultLanguageTranslateManagerImpl(messageSource, new Locale(popularMvcConfig.getLocale()));
	}
	
	//默认国际化处理器
	@Bean
	public LanguageTranslateManager defaultLanguageTranslateManager(@Autowired MessageSource messageSource, @Autowired PopularMvcConfig popularMvcConfig) {
		return new DefaultLanguageTranslateManagerImpl(messageSource, new Locale(popularMvcConfig.getLocale()));
	}
	
	@Bean
	@ConditionalOnMissingBean(SignEncryptHandler.class)
	public SignEncryptHandler signEncryptHandler() {
		return new Sha1SignEncryptHandler();
	}
	
	@Bean
	@ConditionalOnMissingBean(DataEncryptHandler.class)
	public DataEncryptHandler dataEncryptHandler(@Autowired SecretManager secretManager) {
		return new AESDataEncryptHandler().setSecretManager(secretManager);
	}
	
	@Bean
	@ConditionalOnMissingBean(SecretManager.class)
	public SecretManager secretManager(@Autowired ChannelConfigProperties channelConfigProperties, @Autowired SystemParameterRenameProperties systemParameterProperties) {
		DefaultSecretManagerImpl secretManager = new DefaultSecretManagerImpl(channelConfigProperties, systemParameterProperties);
		return secretManager;
	}
	
}
