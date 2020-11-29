/**  
* Title SimoContext.java  
* Description  此对象用于存储一些用户会话相关的信息
* @author danyuan
* @date Dec 17, 2018
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.dto.ApiInfo;
import com.danyuanblog.framework.popularmvc.dto.ApiRestrictions;
import com.danyuanblog.framework.popularmvc.dto.Session;
import com.danyuanblog.framework.popularmvc.properties.SystemParameterProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class RequestContext {
	 // *) 定义了ThreadLocal对象
    private static final ThreadLocal<RequestContext> LOCAL = new ThreadLocal<RequestContext>() {
        protected RequestContext initialValue() {
            return new RequestContext();
        }
    };
    
    private Class<?> targetClass;
    /**
     * 当前执行的方法
     */
    private HandlerMethod handler;
    /**
     * 当前执行接口的请求
     */
    private HttpServletRequest request;
    /**
     * 当前执行接口的响应
     */
    private HttpServletResponse response;
    /**
     * API 限制条件
     */
    private ApiRestrictions apiRestrictions;
    /**
     * 当前接口信息
     */
    private ApiInfo apiInfo;
    /**
     * 当前会话信息
     */
    private Session session;
    /**
	 * 对响应信息进行数字签名的结果
	 */
	private DefaultResponseWrapper<?> responseWrapper;
	/**
	 * 防重复提交码的key
	 */
	private String repeatCodeKey;
	
    /**
     * 附带属性
     */
    private final Map<String, Object> attachments = new HashMap<>();
 
    public static RequestContext getContext() {
        return (RequestContext)LOCAL.get();
    }
 
    protected RequestContext() {
    }
 
    public String getAttachment(String key) {
        return (String)this.attachments.get(key);
    }
 
    public RequestContext setAttachment(String key, String value) {
        if(value == null) {
            this.attachments.remove(key);
        } else {
            this.attachments.put(key, value);
        } 
        return this;
    }
    
    public void clear() {
        this.attachments.clear(); 
        this.targetClass = null;
        this.apiRestrictions = null;
        this.apiInfo = null;
        this.session = null;
        this.responseWrapper = null;
        this.repeatCodeKey = null;
    }
    
    public String getLocale(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.LOCALE));
    }
    
    public String getTimeZone(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.TIME_ZONE));
    }
    
    public String getSessionId(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.SESSION_ID));
    }
    
    public String getUserId(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.USER_ID));
    }
    
    public String getAppId(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.APP_ID));
    }
    
    public String getChannelId(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.CHANNEL_ID));
    }
    
    public String getRepeatCode(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.REPEAT_CODE));
    }

    public String getSign(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.SIGN));
    }
    
    public String getTimestamp(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.TIMESTAMP));
    }
    
    public String getVersionCode(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.VERSION_CODE));
    }
    
    public String getCountryCode(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.COUNTRY_CODE));
    } 
    
    public String getCurrency(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.CURRENCY));
    } 
    
    public String getClientId(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.CLIENT_ID));
    } 
    
    public String getClientIp(){
    	return this.getAttachment(SystemParameterProperties.defaultParameterMap.get(SystemParameterProperties.CLIENT_IP));
    } 
}
