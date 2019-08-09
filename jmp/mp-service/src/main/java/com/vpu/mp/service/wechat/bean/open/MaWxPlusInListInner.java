package com.vpu.mp.service.wechat.bean.open;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.vpu.mp.service.foundation.util.Util;

import lombok.Data;

@Data
public class MaWxPlusInListInner implements Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("appid")
	private String appid;
	/**
	 * 插件状态（1：申请中，2：申请通过，3：被拒绝；4：已超时）
	 */
	@SerializedName("status")
	private String status;
	
	@SerializedName("nickname")
	private String nickName;
	
	@SerializedName("headimgurl")
	private String headimgurl;
	
	

	public static MaWxPlusInListInner fromJson(String json) {
		return Util.parseJson(json, MaWxPlusInListInner.class);
	}

	
	
}
