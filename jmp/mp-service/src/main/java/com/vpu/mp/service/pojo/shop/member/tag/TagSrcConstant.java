package com.vpu.mp.service.pojo.shop.member.tag;
/**
 * 	用户打标签来源
 */
public class TagSrcConstant {
	/** 	后台admin操作	*/
	private final static Byte ADMIN_BACKEND = 0;
	/**		会员卡	*/
	private final static Byte MEMBER_CARD = 1;
	/**		优惠券	*/
	private final static Byte COUPON = 2;
    /** 	限时降价 */
    public final static Byte REDUCE_PRICE = 6;
    /** 	加价购 */
    public final static Byte PURCHASE_PRICE = 7;
}
