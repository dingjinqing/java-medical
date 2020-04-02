package com.vpu.mp.service.wechat.bean.open;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang 2020年4月2日下午4:52:22
 */
@Data
public class WxMaLiveRoomInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8430813682720980265L;
	private String name;
	private Integer roomid;
	@SerializedName("coverImg")
	private String cover_img;
	private Integer selected;

	@SerializedName("live_status")
	private Integer liveStatus;

	@SerializedName("start_time")
	private Long startTime;

	@SerializedName("end_time")
	private Long endTime;

	@SerializedName("anchor_name")
	private String anchorName;

	@SerializedName("anchor_img")
	private String anchorImg;

	private List<WxMaLiveRoomInfoGoods> goods;

	private Integer total;

}
