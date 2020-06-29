package com.vpu.jmd.service.wechat.bean.ma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMaSubscribeMessage implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -7104511825090456523L;

	private String touser;

	private String templateId;

	private String page;

	private List<WxMaSubscribeMessageData> data;

	public String toJson() {
		return WxMaSubscribeGsonBuilder.create().toJson(this);
	}

}
