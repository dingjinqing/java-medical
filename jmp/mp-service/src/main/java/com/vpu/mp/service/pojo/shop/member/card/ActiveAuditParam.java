package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月27日
* @Description: 激活审核数据-入参
*/
@Data
public class ActiveAuditParam {
	/** -每页总数 */
	public Integer pageRows;
	/** -当前页 */
	public Integer currentPage;
	// 会员卡id
	private Integer cardId;
	/** 审核状态 1审核中 2通过 3拒绝  {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.UNDER_REVIEW}*/
	private Byte status;
	// 真实姓名
	private String realName;
	// 手机号
	private String mobile;
	// 申请时间 - 开始
	private Timestamp firstTime;
	// 申请时间 - 结束
	private Timestamp secondTime;

}