package com.vpu.mp.service.wechat.bean.open;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.foundation.util.Util;

import lombok.Data;

@Data
public class WxOpenAuthorizerListResult {

	@JsonProperty("total_count")
	private Integer totalCount;

	@JsonProperty("list")
	private List<WxMpAppInfo> list = new ArrayList<>();

	static final class WxMpAppInfo{

		@JsonProperty("authorizer_appid")
		private String authorizerAppId;

		@JsonProperty("refresh_token")
		private String refreshToken;

		@JsonProperty("auth_time")
		private Integer authTime;
	}

	public static WxOpenAuthorizerListResult fromJson(String json) {
		return Util.parseJson(json, WxOpenAuthorizerListResult.class);
	}
}
