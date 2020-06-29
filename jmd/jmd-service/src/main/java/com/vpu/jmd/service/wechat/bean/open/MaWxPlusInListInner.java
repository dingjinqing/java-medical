package com.vpu.jmd.service.wechat.bean.open;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

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

}
