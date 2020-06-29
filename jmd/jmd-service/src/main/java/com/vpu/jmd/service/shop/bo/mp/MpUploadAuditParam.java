package com.vpu.jmd.service.shop.bo.mp;

import lombok.Data;

/**
 * 上传代码提交审核的入参
 *
 * @author zhaojianqiang
 * @date 2020年6月3日下午3:02:48
 */
@Data
public class MpUploadAuditParam {
	/** true为是直播页面提交的 */
	private Boolean isLiveCommit;
	/** 默认1 */
	private Byte source;
}
