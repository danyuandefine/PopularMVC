/**  
* Title MapResponse.java  
* Description  
* @author danyuan
* @date Jul 1, 2020
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.popularmvc.controller.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString(callSuper=true)
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class MapResponse<T,U> extends SuccessResponse {/** 
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<T,U> map;
}
