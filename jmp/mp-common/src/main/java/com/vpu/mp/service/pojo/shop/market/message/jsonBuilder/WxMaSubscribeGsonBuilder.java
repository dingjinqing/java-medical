package com.vpu.mp.service.pojo.shop.market.message.jsonBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vpu.mp.service.pojo.shop.market.message.maConfig.WxMaSubscribeMessage;

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
