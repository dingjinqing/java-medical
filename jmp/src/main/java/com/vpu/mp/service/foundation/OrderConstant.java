package com.vpu.mp.service.foundation;

/**
 * 订单状态order_status;订单类型goods_type常量类
 * 
 * @author wangshuai
 *
 */
public class OrderConstant {
	/** 订单状态order_status */
	/** 待付款 可进行操作：关闭订单 */
	public static int ORDER_WAIT_PAY = 0;
	/** 客户已取消 可进行操作：无 */
	public static int ORDER_CANCELLED = 1;
	/** 卖家关闭 */
	public static int ORDER_CLOSED = 2;
	/** 待发货 可进行操作：发货，卖家关闭（关闭原因） */
	public static int ORDER_WAIT_DELIVERY = 3;
	/** 已发货 可进行操作：已收货（需检查已超时未收货时才可操作） */
	public static int ORDER_SHIPPED = 4;
	/** 已收货 可进行操作：完成 */
	public static int ORDER_RECEIVED = 5;
	/** 已完成 可进行操作：无 */
	public static int ORDER_FINISHED = 6;
	/** 退货中 可进行操作：完成退货 已完成 */
	public static int ORDER_RETURNING = 7;
	/** 完成退货 可进行操作：无 */
	public static int ORDER_RETURN_FINISHED = 8;
	/** 退款中 可进行操作：无 */
	public static int ORDER_REFUNDING = 9;
	/** 退款成功 可进行操作：无 */
	public static int ORDER_REFUND_FINISHED = 10;
	/** 拼团已支付拼团中状态,这时需等待拼团是否成功。拼团成功后，如果为定金订单则变为待补款，否则为待发货；拼团失败则变为退款 */
	public static int ORDER_PIN_PAYED_GROUPING = 11;
	/** 已成团 */
	public static int ORDER_PIN_SUCCESSS = 12;
	/** 礼单(主订单)环节已完成 */
	public static int ORDER_GIVE_GIFT_FINISHED = 13;

	/** 订单类型goods_type常量 */
	/** 普通商品订单 返利 */
	public static int GOODS_TYPE_GENERAL = 0;
	/** 拼团商品订单 */
	public static int GOODS_TYPE_PIN_GROUP = 1;
	/** 返利商品 */
	public static int GOODS_TYPE_REBATE = 2;
	/** 砍价商品 */
	public static int GOODS_TYPE_BARGAIN = 3;
	/** 积分兑换商品 */
	public static int GOODS_TYPE_INTEGRAL = 4;
	/** 秒杀商品 */
	public static int GOODS_TYPE_SECKILL = 5;
	/** 限时降价 返利 */
	public static int GOODS_TYPE_REDUCE_PRICE = 6;
	/** 加价购 返利 */
	public static int GOODS_TYPE_PURCHASE_PRICE = 7;
	/** 拼团抽奖 */
	public static int GOODS_TYPE_GROUP_DRAW = 8;
	/** 一口价 */
	public static int GOODS_TYPE_PACKAGE_SALE = 9;
	/** 定金膨胀 */
	public static int GOODS_TYPE_PRE_SALE = 10;
	/** 赠品 */
	public static int GOODS_TYPE_GIFT = 11;
	/** 幸运大抽奖 */
	public static int GOODS_TYPE_LOTTERY_PRESENT = 12;
	/** 限次卡兑换 */
	public static int GOODS_TYPE_EXCHANG_ORDER = 13;
	/** 好友助力 */
	public static int GOODS_TYPE_PROMOTE_ORDER = 14;
	/** 满包邮 */
	public static int GOODS_TYPE_FREESHIP_ORDER = 15;
	/** 测评 */
	public static int GOODS_TYPE_ASSESS_ORDER = 16;
	/** 送礼 */
	public static int GOODS_TYPE_GIVE_GIFT = 17;
	/** 首单特惠 */
	public static int GOODS_TYPE_FIRST_SPECIAL = 18;
	
	/**
	 * 配送方式
	 * 
	 */
	/**快递*/
	public static int DELIVER_TYPE_COURIER = 0;
	/**自提*/
	public static int DELIVER_TYPE_SELF = 1;
	

	private String[] orderStatus = {
			// ORDER_WAIT_PAY
			"待付款",
			// ORDER_CANCELLED
			"订单取消",
			// ORDER_CLOSED
			"订单关闭",
			// ORDER_WAIT_DELIVERY
			"待发货/待核销",
			// ORDER_SHIPPED
			"已发货",
			// ORDER_RECEIVED
			"已收货/已自提",
			// ORDER_FINISHED
			"订单完成",
			// ORDER_RETURNING
			"退货中",
			// ORDER_RETURN_FINISHED
			"退货完成",
			// ORDER_REFUNDING
			"退款中",
			// ORDER_REFUND_FINISHED
			"退款完成",
			// ORDER_PIN_PAYED_GROUPING
			"拼团中",
			// ORDER_PIN_SUCCESSS
			"拼团成功",
			// ORDER_PIN_SUCCESSS
			"已成团",
			// ORDER_GIVE_GIFT_FINISHED
			"送礼已完成"
	};
	
	private String[] goodsType = {
			// GOODS_TYPE_GENERAL
			"普通订单",
			// GOODS_TYPE_PIN_GROUP
			"拼团订单",
			// GOODS_TYPE_REBATE
			"返利订单",
			// GOODS_TYPE_BARGAIN
			"砍价订单",
			// GOODS_TYPE_INTEGRAL
			"积分兑换订单",
			// GOODS_TYPE_SECKILL
			"秒杀订单",
			// GOODS_TYPE_REDUCE_PRICE
			"限时降价订单",
			// GOODS_TYPE_FIRST_SPECIAL
			"首单特惠订单",
			// GOODS_TYPE_PURCHASE_PRICE
			"加价购订单",
			// GOODS_TYPE_GROUP_DRAW
			"拼团抽奖订单",
			// GOODS_TYPE_PACKAGE_SALE
			"一口价订单",
			// GOODS_TYPE_PRE_SALE
			"定金膨胀订单",
			// GOODS_TYPE_GIFT
			"赠品订单",
			// GOODS_TYPE_LOTTERY_PRESENT
			"幸运抽奖订单",
			// GOODS_TYPE_EXCHANG_ORDER
			"限次卡兑换订单",
			// GOODS_TYPE_PROMOTE_ORDER
			"好友助力订单",
			// GOODS_TYPE_FREESHIP_ORDER
			"满包邮",
			// GOODS_TYPE_ASSESS_ORDER
			"测评订单",
			// GOODS_TYPE_GIVE_GIFT
			"送礼订单"
	};
	
	public String getOrderStatus(int orderStatusCode) {
		return orderStatus[orderStatusCode];
	}
	
	public String getGoodsType(int goodsTypeCode) {
		return goodsType[goodsTypeCode];
	}
}
