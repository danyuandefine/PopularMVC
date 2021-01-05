/**  
* Title TestManualManageCacheController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.CacheManager;
import com.danyuanblog.framework.popularmvc.consts.CacheExpireMode;
import com.danyuanblog.framework.popularmvc.utils.StringUtils;

@Api(tags = "测试手动管理方法结果缓存相关接口列表")
@RestController
@RequestMapping("manual")
public class TestManualManageCacheController {
	
	@Autowired
	private CacheManager cacheManager;
	
	@GetMapping("getCachedProductBySkuId")
	@ApiOperation(value="测试通过skuId手动查询商品信息缓存", notes="测试通过skuId手动查询商品信息缓存")
	public Object getCachedProductBySkuId(String skuId){
		//手动查询接口缓存结果
		Object result = cacheManager.get("TestMethodResultParamCacheController:getProductBySkuId"+":"+skuId, 10L, CacheExpireMode.EXPIRE_AFTER_REDA);
		if(StringUtils.isBlank(result)){
			result = "未找到skuId:"+skuId+"的商品缓存信息";
		}
		return result;
	}
	

	@PostMapping("evictProductBySkuInfoCache")
	@ApiOperation(value="测试手动删除方法结果缓存", notes="测试手动删除方法结果缓存")
	public void evictProductBySkuInfoCache(@RequestBody Map<String,Object> sku){
		//手动删除方法结果缓存
		cacheManager.remove("TestMethodResultParamCacheController:getProductBySkuInfo"+":"+sku.get("id"), 30L, CacheExpireMode.EXPIRE_AFTER_REDA);
	}
}
