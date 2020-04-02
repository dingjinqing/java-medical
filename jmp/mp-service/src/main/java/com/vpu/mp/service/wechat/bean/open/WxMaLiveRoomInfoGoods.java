package com.vpu.mp.service.wechat.bean.open;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang 2020年4月2日下午5:02:57
 */
@Data
public class WxMaLiveRoomInfoGoods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7081843931712826266L;

	@SerializedName("cover_img")
	private String coverImg;

	@SerializedName("start_time")
	private String url;

	private BigDecimal price;

	private String name;

}
