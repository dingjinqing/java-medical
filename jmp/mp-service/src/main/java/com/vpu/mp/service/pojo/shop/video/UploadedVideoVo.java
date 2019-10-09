package com.vpu.mp.service.pojo.shop.video;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UploadedVideoVo {
	private Integer videoId;
	private String videoType;
	private Integer videoSize;
	private String videoName;
	private String videoUrl;
	private Integer videoCatId;
	private Integer videoWidth;
	private Integer videoHeight;
	private Integer duration;
	private Timestamp createTime;
	private String videoSnapPath;
	private String snapshotUrl;
	private String formatDuration;
}
