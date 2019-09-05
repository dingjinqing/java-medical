package com.vpu.mp.service.pojo.shop.operation;


import lombok.Getter;

/**
 * 操作模块(装修:1001、商品:2001、订单:3001、会员:4001、营销,5001)
 * @author: 卢光耀
 * @date: 2019-07-16 10:57
 *
*/
@Getter
public enum   RecordContentTemplate {

    GOODS_CONTENT_ADD(2001,RecordContentMessage.GOODS_CONTENT_ADD),
	
	/**订单*/
	ORDER_SHIP(3001,RecordContentMessage.ORDER_SHIP),
    ORDER_COUPON_PACK_ORDER_REFUND(3002,RecordContentMessage.ORDER_COUPON_PACK_ORDER_REFUND),
    ORDER_MEMBER_CARD_ORDER_REFUND(3003,RecordContentMessage.ORDER_MEMBER_CARD_ORDER_REFUND),
	ORDER_FINISH(3004,RecordContentMessage.ORDER_FINISH),
	ORDER_RETURN(3005,RecordContentMessage.ORDER_RETURN),
	ORDER_CLOSE(3006,RecordContentMessage.ORDER_CLOSE),

    
    /** 会员-会员卡 */
	MEMBER_CARD_SEND(4001,RecordContentMessage.MSG_MEMBER_CARD_SEND),
	/** 会员-余额*/
	MEMBER_ACCOUNT(4002,RecordContentMessage.MSG_MEMBER_ACCOUNT);
    /**
     * 得到返回码
	 */
    public int code;

    /**
     * 返回信息
     */
    private String message;

     RecordContentTemplate(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
