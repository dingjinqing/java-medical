package com.vpu.mp.service.wechat.bean.open;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

@Data
public class WxOpenAuthorizerListResult implements Serializable {
	private static final long serialVersionUID = -5891226046698581644L;

	@SerializedName("total_count")
	private Integer totalCount;

	@SerializedName("list")
	private List<WxMpAppInfo> list = new ArrayList<>();

	static final class WxMpAppInfo {
		@SerializedName("authorizer_appid")
		private String authorizerAppId;

		@SerializedName("refresh_token")
		private String refreshToken;

		@SerializedName("auth_time")
		private Timestamp authTime;
	}

	public static WxOpenAuthorizerListResult fromJson(String json) {
		return WxGsonBuilder.create().fromJson(json, WxOpenAuthorizerListResult.class);
	}
}
