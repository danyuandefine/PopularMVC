/**  
* Title Startup.java  
* Description  
* @author danyuan
* @date Oct 31, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.danyuanblog.framework.popularmvc.annotation.EnablePopularMvc;

@SpringBootApplication
@EnablePopularMvc
@ComponentScan("com.danyuanblog.framework.demo.popularmvc")
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}
}
