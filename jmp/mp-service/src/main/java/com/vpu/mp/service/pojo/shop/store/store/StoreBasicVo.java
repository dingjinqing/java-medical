package com.vpu.mp.service.pojo.shop.store.store;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
* @author 黄壮壮
* @Date: 2019年9月3日
* @Description: 获取门店弹窗出参
*/
@Getter
@Setter
public class StoreBasicVo {
	@JsonProperty("value")
	private Integer storeId;
	@JsonProperty("label")
	private String storeName;
}
