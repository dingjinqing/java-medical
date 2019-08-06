package com.vpu.mp.service.pojo.wxapp.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WxAppLoginParam {
	@JsonProperty(value = "shop_id")
	Integer shopId;
	String code;
	String avatar;
	@JsonProperty(value = "path_query")
	String pathQuery;
}
