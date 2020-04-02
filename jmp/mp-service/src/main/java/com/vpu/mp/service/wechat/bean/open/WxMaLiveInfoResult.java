package com.vpu.mp.service.wechat.bean.open;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 获取直播房间列表 返回值
 * 
 * @author zhaojianqiang 2020年4月2日下午4:47:55
 */
@Getter
@Setter
@ToString
public class WxMaLiveInfoResult extends WxOpenResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4400409481898888644L;

	@SerializedName("room_info")
	private List<WxMaLiveRoomInfo> roomInfo;

	public static WxMaLiveInfoResult fromJson(String json) {
		return WxGsonBuilder.create().fromJson(json, WxMaLiveInfoResult.class);
	}
}
