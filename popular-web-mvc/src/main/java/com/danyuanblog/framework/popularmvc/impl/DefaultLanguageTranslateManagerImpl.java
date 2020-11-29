/**  
* Title DefaultLanguageTranslateManagerImpl.java  
* Description  默认字典翻译器，默认使用spring容器i18n字典翻译器
* @author danyuan
* @date Nov 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.impl;

import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Setter;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import com.danyuanblog.framework.popularmvc.LanguageTranslateManager;

@Setter
@AllArgsConstructor
public class DefaultLanguageTranslateManagerImpl implements LanguageTranslateManager{

	private MessageSource messageSource;
	
	private Locale locale;
	
	/**
	 * @author danyuan
	 */
	@Override
	public String get(String key, String localeName, Object... params) {
		Locale locale = new Locale(localeName);
		try{
			return messageSource.getMessage(key, params, locale);
		}catch(NoSuchMessageException e){
			return key;
		}		
	}

	/**
	 * @author danyuan
	 */
	@Override
	public String get(String key, Object... params) {
		Locale locale = new Locale(this.getDefaultLocale());
		try{
			return messageSource.getMessage(key, params, locale);
		}catch(NoSuchMessageException e){
			return key;
		}
	}

	/**
	 * @author danyuan
	 */
	@Override
	public void setDefaultLocale(String locale) {
		this.locale = new Locale(locale);
	}

	/**
	 * @author danyuan
	 */
	@Override
	public String getDefaultLocale() {
		return locale.toString();
	}

}
