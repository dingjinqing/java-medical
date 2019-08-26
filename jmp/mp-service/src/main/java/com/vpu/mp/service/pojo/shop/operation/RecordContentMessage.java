package com.vpu.mp.service.pojo.shop.operation;

/**
 *
 * @author: 卢光耀
 * @date: 2019-07-16 10:57
 *
*/
public class RecordContentMessage {

    public static final String GOODS_CONTENT_ADD = "goods.content.add";
    
    /**
     * 	订单
     */
    /**发货*/
    public static final String ORDER_SHIP = "order.ship";
    /**优惠券礼包订单退款*/
    public static final String ORDER_COUPON_PACK_ORDER_REFUND = "order.coupon.pack.order.refund";
    /**会员卡订单退款*/
    public static final String ORDER_MEMBER_CARD_ORDER_REFUND = "order.member.card.order.refund";
    
    /**
     * 	会员
     */
    /** 会员发放会员卡 */
    public static final String MSG_MEMBER_CARD_SEND = "发放给'ID：%d；昵称：%s'的会员卡:  %s";
    /** 会员余额更新变动 */
	public static final String MSG_MEMBER_ACCOUNT = "修改\"ID: %d  昵称: %s \"的余额 %s";
    

}
