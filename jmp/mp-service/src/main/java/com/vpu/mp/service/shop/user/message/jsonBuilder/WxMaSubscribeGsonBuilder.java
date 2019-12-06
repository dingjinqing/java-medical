package com.vpu.mp.service.shop.user.message.jsonBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vpu.mp.service.shop.user.message.maConfig.WxMaSubscribeMessage;

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
