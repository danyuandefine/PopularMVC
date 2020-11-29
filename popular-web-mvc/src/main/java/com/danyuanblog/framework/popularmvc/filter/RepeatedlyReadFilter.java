/**  
* Title RepeatedlyReadFilter.java  
* Description  
* @author danyuan
* @date Jul 31, 2019
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.danyuanblog.framework.popularmvc.utils.request.BodyReaderHttpServletRequestWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepeatedlyReadFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(log.isTraceEnabled()){
			log.trace("构建可重复读取的request.getInputStream流");
		}		
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
            return ;
        }
        chain.doFilter(request, response);
	}

}
