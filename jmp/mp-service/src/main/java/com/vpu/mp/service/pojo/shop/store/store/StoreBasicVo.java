package com.vpu.mp.service.pojo.shop.store.store;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
* @author 黄壮壮
* @Date: 2019年9月3日
* @Description: 获取门店弹窗出参
*/
@Getter
@Setter
public class StoreBasicVo {
	@JsonProperty("value")
    @JsonAlias({"storeId", "store_id"})
    @NotBlank(groups = AppletStoreInfo.class)
	private Integer storeId;
	@JsonProperty("label")
	private String storeName;
}
