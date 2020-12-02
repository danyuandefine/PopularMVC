/**  
* Title AutoAddReponseWrapperHandler.java  
* Description  响应内容检查、包装处理拦截器
* @author danyuan
* @date Oct 18, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.response.enhance;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danyuanblog.framework.popularmvc.consts.ErrorCodes;
import com.danyuanblog.framework.popularmvc.context.RequestContext;
import com.danyuanblog.framework.popularmvc.controller.response.DefaultResponseWrapper;
import com.danyuanblog.framework.popularmvc.properties.PopularMvcConfig;
import com.danyuanblog.framework.popularmvc.properties.ResponseSystemFieldRenameProperties;
import com.danyuanblog.framework.popularmvc.utils.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class AutoAddReponseWrapperHandler extends AbstractResponseEnhanceHandler {

	@Autowired
	private ResponseSystemFieldRenameProperties responseWrapperProperties;
	
	@Autowired
	private PopularMvcConfig popularMvcConfig;
	
	/**
	 * @author danyuan
	 */
	@Override
	public Object enhance(Object resp) {
		if(!popularMvcConfig.getForceAutoAddResponseWrapper()){
			if(log.isTraceEnabled()){
				log.trace("跳过响应包装壳处理流程！");
			}
			return resp;
		}
		if(log.isTraceEnabled()){
			log.trace("开始检查和修正响应包装内容！");
		}
		Map<String, Object> map = null;
		try{
			if(RequestContext.getContext().getApiRestrictions() != null 
					&& !RequestContext.getContext().getApiRestrictions().getIgnoreResponseWrapper()){
				if(resp != null){			
			    	//检查是否是包装类
					if(hasResponseWrapper(resp)){
						if(resp instanceof DefaultResponseWrapper){					
							DefaultResponseWrapper<?> wrapper = (DefaultResponseWrapper<?>)resp;
							map = new HashMap<>();
							map.put(responseWrapperProperties.getCode(), wrapper.getCode());
							map.put(responseWrapperProperties.getMsg(), wrapper.getMsg());
							map.put(responseWrapperProperties.getData(), wrapper.getData());
							if(RequestContext.getContext().getResponseWrapper() != null){//系统签名为
								map.put(responseWrapperProperties.getSign(), RequestContext.getContext().getResponseWrapper().getSign());
								map.put(responseWrapperProperties.getTimestamp(), RequestContext.getContext().getResponseWrapper().getTimestamp());
							}
							if(wrapper.getSign() != null){
								map.put(responseWrapperProperties.getSign(), wrapper.getSign());
							}
							if(wrapper.getTimestamp() != null){
								map.put(responseWrapperProperties.getTimestamp(), wrapper.getTimestamp());		
							}							
						} else {
							//说明存在响应包装
				    		map= baseResponseWrapper(resp);
						}	    			    			
			    	}else{
			    		map = successResponseWrapper();
			    		map.put(responseWrapperProperties.getData(), resp);
			    	}
				}else{//默认返回空的成功响应
					map = successResponseWrapper();
				}
			}	
		}catch(Exception e){
			if(log.isTraceEnabled()){
				log.trace(IOUtils.getThrowableInfo(e));
			}
		}	
		//去除空字段
		if(map != null){
			//添加其他系统响应参数
			map.putAll(RequestContext.getContext().getResonseSystemParams());
			for(Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext();){
				if(it.next().getValue() == null){
					it.remove();
				}
			}
			resp = map;
		}		
		return resp;
	}
	
	/**
	 * @author danyuan
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	private Map<String, Object> baseResponseWrapper(Object resp) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper(); 
		JsonNode json = mapper.readTree(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
		Map<String, Object> map = new HashMap<>();
		if(RequestContext.getContext().getResponseWrapper() != null){//系统签名为
			map.put(responseWrapperProperties.getSign(), RequestContext.getContext().getResponseWrapper().getSign());
			map.put(responseWrapperProperties.getTimestamp(), RequestContext.getContext().getResponseWrapper().getTimestamp());
		}	
		if(json.hasNonNull(responseWrapperProperties.getCode())){
			map.put(responseWrapperProperties.getCode(), json.get(responseWrapperProperties.getCode()).asInt());
		}	
		if(json.hasNonNull(responseWrapperProperties.getMsg())){
			map.put(responseWrapperProperties.getMsg(), json.get(responseWrapperProperties.getMsg()).asText());
		}	
		if(json.hasNonNull(responseWrapperProperties.getSign())){
			map.put(responseWrapperProperties.getSign(), json.get(responseWrapperProperties.getSign()).asText());
		}
		if(json.hasNonNull(responseWrapperProperties.getTimestamp())){
			map.put(responseWrapperProperties.getTimestamp(), json.get(responseWrapperProperties.getTimestamp()).asText());
		}
		if(json.hasNonNull(responseWrapperProperties.getData())){
			map.put(responseWrapperProperties.getData(), json.get(responseWrapperProperties.getData()));
		}			
		return map;
	}

	private boolean hasResponseWrapper(Object resp) throws JsonProcessingException, IOException{
		if(resp instanceof DefaultResponseWrapper){
			return true;
		}else{
			Map<String, Boolean> fieldStatusMap = new HashMap<>();
			fieldStatusMap.put(responseWrapperProperties.getCode(), false);
			fieldStatusMap.put(responseWrapperProperties.getData(), false);
			fieldStatusMap.put(responseWrapperProperties.getMsg(), false);
			fieldStatusMap.put(responseWrapperProperties.getSign(), false);
			fieldStatusMap.put(responseWrapperProperties.getTimestamp(), false);
			// 获取实体类的所有属性，返回Field数组
			Field[] fields = resp.getClass().getDeclaredFields();
			for(Field field : fields){
				if(fieldStatusMap.containsKey(field.getName())){
					fieldStatusMap.put(field.getName(), true);
				}
			}
			for(boolean status : fieldStatusMap.values()){
				if(!status){
					return false;
				}
			}
			return true;
		}		
	}

	
	private Map<String,Object> successResponseWrapper(){
		Map<String, Object> map = new HashMap<>();
		map.put(responseWrapperProperties.getCode(), ErrorCodes.SUCCESS.getCode());
		if(RequestContext.getContext().getResponseWrapper() != null){
			map.put(responseWrapperProperties.getSign(), RequestContext.getContext().getResponseWrapper().getSign());
			map.put(responseWrapperProperties.getTimestamp(), RequestContext.getContext().getResponseWrapper().getTimestamp());
		}		
		return map;
	}

	/**
	 * @author danyuan
	 */
	@Override
	public int order() {
		return -1;
	}


}
