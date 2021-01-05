/**  
* Title TestMethodResultParamCacheController.java  
* Description  
* @author danyuan
* @date Jan 4, 2021
* @version 1.0.0
* site: www.danyuanblog.com
*/ 
package com.danyuanblog.framework.demo.popularmvc.controller;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.danyuanblog.framework.popularmvc.annotation.CacheMethodResult;

@Api(tags = "测试方法结果查询参数化缓存相关接口列表")
@RestController
public class TestMethodResultParamCacheController {
	
	@GetMapping("getProductBySkuId")
	@ApiOperation(value="测试通过skuId查询商品信息", notes="测试通过skuId查询商品信息")
	@CacheMethodResult(id="skuId", expireSeconds=10)
	public Map<String,Object> getProductBySkuId(String skuId){
		//Mock 商品信息
		Map<String,Object> map = new HashMap<>();
		map.put("skuId", skuId);
		map.put("productName", "大宗商品"+skuId);
		map.put("loadTime", System.currentTimeMillis());
		return map;
	}
	

	@PostMapping("getProductBySkuInfo")
	@ApiOperation(value="测试通过sku信息查询商品信息", notes="测试通过sku信息查询商品信息")
	@CacheMethodResult(id="sku[id]", expireSeconds=30)
	public Map<String,Object> getProductBySkuInfo(@RequestBody Map<String,Object> sku){
		//Mock 商品信息
		Map<String,Object> map = new HashMap<>();
		map.put("skuId", sku.get("id"));
		map.put("productName", "大宗商品"+sku.get("id"));
		map.put("loadTime", System.currentTimeMillis());
		return map;
	}
}
