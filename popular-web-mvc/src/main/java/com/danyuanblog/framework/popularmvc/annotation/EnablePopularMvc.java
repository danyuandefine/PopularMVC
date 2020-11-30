/**  
* Title EnableMongoPlus.java  
* Description  
* @author danyuan
* @date Mar 13, 2019
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.annotation;

import org.springframework.context.annotation.Import;

import com.danyuanblog.framework.popularmvc.config.PopularMvcBeanConfigurer;
import com.danyuanblog.framework.popularmvc.config.PopularMvcSwaggerConfigurer;
import com.danyuanblog.framework.popularmvc.config.PopularMvcWebConfigurer;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({PopularMvcWebConfigurer.class,PopularMvcSwaggerConfigurer.class,PopularMvcBeanConfigurer.class})
public @interface EnablePopularMvc {
    
}
