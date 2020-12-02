/**  
 * Title CacheManagerState.java  
 * Description  
 * @author danyuan
 * @date Nov 13, 2020
 * @version 1.0.0
 * site: www.danyuanblog.com
 */
package com.danyuanblog.framework.popularmvc.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CacheManagerState implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String cacheName;
	private final long hitCount;
	private final long missCount;
	private final long loadSuccessCount;
	private final long loadTotalCount;
	private final long totalLoadTime;
	private final long evictionCount;
}
