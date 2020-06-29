package com.vpu.jmd.service.shop.bo.audit;

/**
 * 操作类型
 *
 * @author zhaojianqiang
 * @date 2020年6月4日下午4:40:28
 */
public interface AuditActCont {
	/** 驳回申请 */
	public static final String ACT_REJECT = "reject";
	/** 忽略 */
	public static final String ACT_IGNORE = "ignore";
	/** 关闭审核单 */
	public static final String ACT_CLOSE = "close";
	/** 审核通过 */
	public static final String ACT_PASS = "pass";
	/** 提交代码 */
	public static final String ACT_SUB_CODE = "sub_code";
	/** 批量提交代码 */
	public static final String ACT_BATCH_SUB_CODE = "batch_sub_code";
	/** 提交审核失败 */
	public static final String ACT_SUB_FAIL = "sub_fail";
}
