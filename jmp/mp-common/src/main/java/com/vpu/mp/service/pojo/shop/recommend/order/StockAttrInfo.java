/**
  * Copyright 2019 bejson.com 
  */
package com.vpu.mp.service.pojo.shop.recommend.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2019-11-12 10:52:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class StockAttrInfo {

	@JsonProperty(value = "attr_name")
    private AttrName attr_name;
	
	@JsonProperty(value = "attr_value")
    private AttrValue attr_value;
}