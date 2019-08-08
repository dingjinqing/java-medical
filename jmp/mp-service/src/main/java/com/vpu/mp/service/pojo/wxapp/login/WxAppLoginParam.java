package com.vpu.mp.service.pojo.wxapp.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxAppLoginParam  extends WxAppCommonParam{
	String code;
	String avatar;
	@JsonProperty(value = "path_query")
	String pathQuery;
}
