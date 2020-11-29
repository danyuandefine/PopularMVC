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

public class PopularMvcEnvironmentPostProcessor implements
		EnvironmentPostProcessor {

	private static final String DEFAULT_MSG_BASENAME = ",popularMvcMessages";

	private static final String SRPRING_MSG_BASENAME = "spring.messages.basename";
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment,
			SpringApplication application) {
        	
		System.out.println("#############################配置信息##############################");
		for (Iterator<?> it = ((AbstractEnvironment) environment)
				.getPropertySources().iterator(); it.hasNext();) {
			PropertySource<?> propertySource = (PropertySource<?>) it.next();
			if(propertySource.containsProperty(SRPRING_MSG_BASENAME)){
				updateStringProperty(environment, propertySource, 
						SRPRING_MSG_BASENAME, propertySource.getProperty(SRPRING_MSG_BASENAME).toString() + DEFAULT_MSG_BASENAME);
			}
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
		System.out.println("###############################################################");
	}
	private void updateStringProperty(ConfigurableEnvironment environment, PropertySource<?> source, String name, String value){
		Map<String,Object> params = new HashMap<>();
		params.put(name, value);
		updateStringProperty(environment, source, params);
	}
	private void updateStringProperty(ConfigurableEnvironment environment, PropertySource<?> source, Map<String, Object> params){
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
