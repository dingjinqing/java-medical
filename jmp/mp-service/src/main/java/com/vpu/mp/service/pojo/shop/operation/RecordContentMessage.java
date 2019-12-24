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
    /**完成*/
    public static final String ORDER_FINISH = "order.finish";
    /**退款退货*/
    public static final String ORDER_RETURN = "order.return";
    /**关闭*/
    public static final String ORDER_CLOSE = "order.close";
    /**核销*/
    public static final String ORDER_VERIFY = "order.verify";
    /**收货*/
    public static final String ORDER_RECEIVE = "order.receive";
    /**提醒*/
    public static final String ORDER_REMIND = "order.remind";
    /**延长收货*/
    public static final String ORDER_EXTEND_RECEIVE = "order.extend.receive";
    /**删除*/
    public static final String ORDER_DELETE = "order.delete";
    
    /**
     * 	会员
     */
    /** 会员发放会员卡 */
    public static final String MSG_MEMBER_CARD_SEND = "member.content.card.send";
    /** 会员余额更新变动 */

    public static final String MSG_MEMBER_ACCOUNT = "member.content.account";
    /** 会员积分更新变动*/
    public static final String MSG_MEMBER_INTEGRALT = "member.content.integral";


    /**
     * 	营销
     */
    /** 秒杀活动添加 */
    public static final String MARKET_SECKILL_ADD = "market.seckill.add";


}
