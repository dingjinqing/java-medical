package com.vpu.jmd.service.shop.bo.audit;

/**
 * 基础类
 *
 * @author zhaojianqiang
 * @date 2020年6月3日上午11:24:29
 */

public interface AuditStatus {
	/** 运营待处理 */
	public static final byte AUDIT_STATUS_WAIT_DEAL = 0;
	/** 运营关闭 */
	public static final byte AUDIT_STATUS_CLOSE = 1;
	/** 运营驳回 */
	public static final byte AUDIT_STATUS_REFUSE = 2;
	/** 运营忽略 */
	public static final byte AUDIT_STATUS_IGNORE = 3;
	/** 运营审核通过 */
	public static final byte AUDIT_STATUS_AUDIT_PASS = 4;
	/** 微信审核中 */
	public static final byte AUDIT_STATUS_WECHAT_AUDIT = 5;
	/** 微信审核成功 */
	public static final byte AUDIT_STATUS_WECHAT_AUDIT_SUCCESS = 6;
	/** 微信审核失败 */
	public static final byte AUDIT_STATUS_WECHAT_AUDIT_FAIL = 7;
	/** 提交微信审核中 */
	public static final byte AUDIT_STATUS_SUBMIT_AUDITING = 8;

}
