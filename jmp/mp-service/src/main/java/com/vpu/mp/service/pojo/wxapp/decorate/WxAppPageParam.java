package com.vpu.mp.service.pojo.wxapp.decorate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.wxapp.login.WxAppCommonParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxAppPageParam extends WxAppCommonParam {

	/**
	 * 装修页面Id，为0时代表首页
	 */
	@JsonProperty(value = "page")
	Integer pageId = 0;
	
	/**
	 * 场景值Id
	 */
	@JsonProperty(value = "scene")
	Integer sceneId;
	
}
