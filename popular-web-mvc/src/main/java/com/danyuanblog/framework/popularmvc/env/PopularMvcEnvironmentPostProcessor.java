/**  
 * Title PopularMvcEnvironmentPostProcessor.java  
 * Description  
 * @author danyuan
 * @date Nov 29, 2020
 * @version 1.0.0
 * site: www.danyuanblog.com
 */
package com.danyuanblog.framework.popularmvc.env;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import com.danyuanblog.framework.popularmvc.consts.DefaultConfigPropertiesValue;

public class PopularMvcEnvironmentPostProcessor implements
		EnvironmentPostProcessor {

	private static final String OPEN_LOG_KEY="popularmvc.printAllProperties";
	
	private static final String DEFAULT_ENV_CONFIG = "defaultEnvConfig";
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment,
			SpringApplication application) {
		//先填充默认配置信息
		MapPropertySource defaultEnvConfig = new MapPropertySource(DEFAULT_ENV_CONFIG, DefaultConfigPropertiesValue.getAllDefaultConfigs());
		environment.getPropertySources().addLast(defaultEnvConfig);
		
        boolean open = false;
        String openLogValue = environment.getProperty(OPEN_LOG_KEY);
        if(!StringUtils.isEmpty(openLogValue)){
        	open = Boolean.valueOf(openLogValue);
        }
        if(open){
        	System.out.println("#############################配置信息##############################");
        }		
		for (Iterator<?> it = ((AbstractEnvironment) environment)
				.getPropertySources().iterator(); it.hasNext();) {
			PropertySource<?> propertySource = (PropertySource<?>) it.next();
			Map<String,Object> modifyMap = new HashMap<>();
			for(DefaultConfigPropertiesValue config : DefaultConfigPropertiesValue.values()){
				if(propertySource.containsProperty(config.getKey())){
					modifyMap.put(config.getKey(), config.getComposeValue(propertySource.getProperty(config.getKey())));
				}
			}
			//批量修改属性值
			updateStringPropertys(environment, propertySource, modifyMap);
			if(open){
				// 遍历每个配置来源中的配置项
				if (propertySource instanceof EnumerablePropertySource) {
					for (String name : ((EnumerablePropertySource<?>) propertySource)
							.getPropertyNames()) {
						/*
						 * 由于每个配置来源可能配置了同一个配置项，存在相互覆盖的情况，为了保证获取到的值与通过@Value获取到的值一致，
						 * 需要通过env.getProperty(name)获取配置项的值。
						 */
						System.out.println(name + " = "
								+ environment.getProperty(name));
						
					}
				}
	        }			
		}
		if(open){
			System.out.println("###############################################################");
        }		
	}

	private void updateStringPropertys(ConfigurableEnvironment environment, PropertySource<?> source, Map<String, Object> params){
        Map<String,Object> map = new HashMap<>();
        if(source instanceof EnumerablePropertySource){
        	for (String name : ((EnumerablePropertySource<?>) source).getPropertyNames()) {
    			map.put(name, environment.getProperty(name));			
    		}
            //动态修改配置
            params.forEach((k, v) -> {
                map.replace(k , v);
            });
            environment.getPropertySources().replace(source.getName(), new MapPropertySource(source.getName(), map));	
        }        
	}

}
