/**  
* Title SkuCurrency.java  
* Description  
* @author danyuan
* @date Nov 14, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popular.mvc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.danyuanblog.framework.popularmvc.annotation.IgnoreSign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuCurrency implements Serializable{

	/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String currency;
	@IgnoreSign
	private BigDecimal price;
}
