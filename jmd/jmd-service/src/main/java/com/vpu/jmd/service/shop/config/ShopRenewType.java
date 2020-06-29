package com.vpu.jmd.service.shop.config;
/**
 *
 * @author zhaojianqiang
 * @date 2020年6月10日上午11:21:17
 */
public interface ShopRenewType {
	/** 续费 */
	public static final byte RENEWAL = 1;
	/** 试用 */
	public static final byte TRIAL = 2;
	/** 赠送 */
	public static final byte GIFT = 3;
	/** 退款 */
	public static final byte REFUND = 4;
}
