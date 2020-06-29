package com.vpu.jmd.service.shop.bo.audit;
/**
 * operation
 * @author zhaojianqiang
 * @date 2020年6月4日下午4:08:46
 */
public interface OperationStatus {
	/** 待处理 */
	public static final byte PENDING = 0;
	/** 1：关闭 */
	public static final byte CLOSE = 1;
	/** 2：拒绝 */
	public static final byte REJECT = 2;
	/** 3：忽略 */
	public static final byte IGNORE = 3;
	/** 4：运营审核通过 */
	public static final byte OPERATION_REVIEW_OK = 4;
	/** 5：微信审核中 */
	public static final byte IN_PROGRESS = 5;
	/** 6：微信审核成功 */
	public static final byte REVIEW_SUCCEEDED = 6;
	/** 7：微信审核失败 */
	public static final byte REVIEW_FAILED = 7;
	/** 备注 */
	public static final byte REMARKS = 8;
}
