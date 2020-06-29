package com.vpu.jmd.service.wechat.bean.ma;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxMaSubscribeGsonBuilder {
	private static final GsonBuilder INSTANCE = new GsonBuilder();
	static {
		INSTANCE.disableHtmlEscaping();
	    INSTANCE.registerTypeAdapter(WxMaSubscribeMessage.class, new WxMaSubscribeMessageGsonAdapter());
	}

	public static Gson create() {
		return INSTANCE.create();
	}
}
