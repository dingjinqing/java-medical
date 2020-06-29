package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * system查询审核列表入参
 *
 * @author zhaojianqiang
 * @date 2020年6月3日上午9:31:35
 */
@Data
public class SystemAuditListParam {
	/** 店铺名称或者店铺id */
	private String keywords;
	/** 店铺标签 */
	private Integer shopTagId;
	/** 审核人id */
	private Integer accountId;
	/** 店铺版本 */
	private String shopType;
	/** 审核状态 */
	private List<Byte> auditStatus;
	/** 店铺状态 */
	private Byte shopStatus;
	/** 小程序版本 */
	private Byte packageVersion;
	/** 运营审核时间 - 开始 */
	private Timestamp auditStartTime;
	/** 运营审核时间 - 结束 */
	private Timestamp auditEndTime;
	/** 微信审核时间 - 开始 */
	private Timestamp wxAuditStartTime;
	/** 微信审核时间 - 结束 */
	private Timestamp wxAuditEndTime;
	/** 审核次数查询 - 开始 */
	private Timestamp countStartTime;
	/** 审核次数查询 - 结束 */
	private Timestamp countEndTime;
	/** 售后负责人 */
	private Integer afterSaleId;

	private List<Integer> batchAuditId;

	private Integer currentPage;
	private Integer pageRows;
}
