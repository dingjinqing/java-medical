package com.vpu.mp.service.wechat.bean.open;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.vpu.mp.service.foundation.util.Util;

import lombok.Data;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import me.chanjar.weixin.open.util.json.WxOpenGsonBuilder;

@Data
public class MaWxPlusInResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("errcode")
	private String errcode;

	@SerializedName("errmsg")
	private String errmsg;

	@SerializedName("plugin_list")
	private List<MaWxPlusInListInner> pluginList;

	public static MaWxPlusInResult fromJson(String json) {
		return WxOpenGsonBuilder.create().fromJson(json, new TypeToken<MaWxPlusInResult>() {
		}.getType());
	}

}
