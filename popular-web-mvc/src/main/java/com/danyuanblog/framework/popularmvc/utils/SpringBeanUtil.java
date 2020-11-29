/**  
* Title SpringBeanUtil.java  
* Description  
* @author danyuan
* @date Mar 13, 2019
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;
    
    private static DefaultListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
            beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过Bean名字获取Bean
     *
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    /**
     * 通过Bean类型获取Bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass) {
        return getApplicationContext().getBean(beanClass);
    }

    /**
     * 通过Bean名字和Bean类型获取Bean
     *
     * @param beanName
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return getApplicationContext().getBean(beanName, beanClass);
    }
    /**
     * 添加手动初始化的bean到容器，进行统一管理
     * @author danyuan
     */
    public static void addExistSingletonBean(Object obj){
    	addExistSingletonBean(null, obj);
    }
    
    /**
     * 添加手动初始化的bean到容器，进行统一管理
     * @author danyuan
     */
    public static void addExistSingletonBean(String beanName, Object obj){
    	if(StringUtils.isEmpty(beanName)){
    		beanName = obj.getClass().getName();
    	}
    	beanFactory.registerSingleton(beanName, obj);
    }
}
