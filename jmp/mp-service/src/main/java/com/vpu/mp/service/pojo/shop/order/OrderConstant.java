package com.vpu.mp.service.pojo.shop.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单状态order_status;订单类型goods_type常量类
 *
 * @author wangshuai
 *
 */
public class OrderConstant {
	/** 订单状态order_status */
	/** 待付款 可进行操作：关闭订单 */
	public final static byte ORDER_WAIT_PAY = 0;
	/** 客户已取消 可进行操作：无 */
	public final static byte ORDER_CANCELLED = 1;
	/** 卖家关闭 */
	public final static byte ORDER_CLOSED = 2;
	/** 待发货 可进行操作：发货，卖家关闭（关闭原因） */
	public final static byte ORDER_WAIT_DELIVERY = 3;
	/** 已发货 可进行操作：已收货（需检查已超时未收货时才可操作） */
	public final static byte ORDER_SHIPPED = 4;
	/** 已收货 可进行操作：完成 */
	public final static byte ORDER_RECEIVED = 5;
	/** 已完成 可进行操作：无 */
	public final static byte ORDER_FINISHED = 6;
	/** 目前没用-退货中 可进行操作：完成退货 已完成 */
	@Deprecated
	public final static byte ORDER_RETURNING = 7;
	/** 完成退货 可进行操作：无 */
	public final static byte ORDER_RETURN_FINISHED = 8;
	/** 目前没用-退款中 可进行操作：无 */
	@Deprecated
	public final static byte ORDER_REFUNDING = 9;
	/** 退款成功 可进行操作：无 */
	public final static byte ORDER_REFUND_FINISHED = 10;
	/** 拼团已支付拼团中状态,这时需等待拼团是否成功.拼团成功后,如果为定金订单则变为待补款,否则为待发货;拼团失败则变为退款 */
	public final static byte ORDER_PIN_PAYED_GROUPING = 11;
	/** 已成团 */
	public final static byte ORDER_PIN_SUCCESSS = 12;
	/** 礼单(主订单)环节已完成 */
	public final static byte ORDER_GIVE_GIFT_FINISHED = 13;

	/** 订单类型goods_type常量 */
	/** 普通商品订单 返利 */
	public final static byte GOODS_TYPE_GENERAL = 0;
	/** 拼团商品订单 */
	public final static byte GOODS_TYPE_PIN_GROUP = 1;
	/** 返利商品 */
	public final static byte GOODS_TYPE_REBATE = 2;
	/** 砍价商品 */
	public final static byte GOODS_TYPE_BARGAIN = 3;
	/** 积分兑换商品 */
	public final static byte GOODS_TYPE_INTEGRAL = 4;
	/** 秒杀商品 */
	public final static byte GOODS_TYPE_SECKILL = 5;
	/** 限时降价 返利 */
	public final static byte GOODS_TYPE_REDUCE_PRICE = 6;
	/** 加价购 返利 */
	public final static byte GOODS_TYPE_PURCHASE_PRICE = 7;
	/** 拼团抽奖 */
	public final static byte GOODS_TYPE_GROUP_DRAW = 8;
	/** 一口价 */
	public final static byte GOODS_TYPE_PACKAGE_SALE = 9;
	/** 定金膨胀 */
	public final static byte GOODS_TYPE_PRE_SALE = 10;
	/** 赠品 */
	public final static byte GOODS_TYPE_GIFT = 11;
	/** 幸运大抽奖 */
	public final static byte GOODS_TYPE_LOTTERY_PRESENT = 12;
	/** 限次卡兑换 */
	public final static byte GOODS_TYPE_EXCHANG_ORDER = 13;
	/** 好友助力 */
	public final static byte GOODS_TYPE_PROMOTE_ORDER = 14;
	/** 满包邮 */
	public final static byte GOODS_TYPE_FREESHIP_ORDER = 15;
	/** 测评 */
	public final static byte GOODS_TYPE_ASSESS_ORDER = 16;
	/** 送礼 */
	public final static byte GOODS_TYPE_GIVE_GIFT = 17;
	/** 首单特惠 */
	public final static byte GOODS_TYPE_FIRST_SPECIAL = 18;

	/**退货状态*/
	/**审核中,退款且退货申请*/
	public final static byte  REFUND_STATUS_AUDITING = 1;
	/**审核通过,更新卖家的收货地址信息*/
	public final static byte  REFUND_STATUS_AUDIT_PASS = 2;
	/**未通过审核,继续走正常订单流程*/
	public final static byte  REFUND_STATUS_AUDIT_NOT_PASS = 3;
	/**买家提交物流 或 仅退款时,买家申请退款,(仅退运费/手动退款)*/
	public final static byte  REFUND_STATUS_APPLY_REFUND_OR_SHIPPING = 4;
	/**退款/退货完成*/
	public final static byte  REFUND_STATUS_FINISH = 5;
	/**拒绝退款/退货*/
	public final static byte  REFUND_STATUS_REFUSE = 6;
	/**退款/退货 撤销*/
	public final static byte  REFUND_STATUS_CLOSE = 7;
	/** 退货的默认状态 */
	public final static byte REFUND_DEFAULT_STATUS = 0;
	/**
	 * 	支付code
	 */
	/**微信*/
	public final static String PAY_CODE_WX_PAY = "wxpay";
	/**积分*/
	@Deprecated
	public final static String PAY_CODE_SCORE_PAY = "score";
	/**余额*/
	public final static String PAY_CODE_BALANCE_PAY = "balance";
	/**货到付款*/
	public final static String PAY_CODE_COD = "cod";
	/**会员卡*/
	@Deprecated
	public final static String PAY_CODE_MEMBER_CARD = "member_card";
	/**ali_mini*/
	@Deprecated
	public final static String PAY_CODE_ALI_MINI_PAY = "ali_mini_pay";

	/**
	 *	 配送方式
	 *
	 */
	/**快递*/
	public final static byte DELIVER_TYPE_COURIER = 0;
	/**自提*/
	public final static byte DELIVER_TYPE_SELF = 1;

	/**
	 *	 是否部分发货
	 */
	/**否*/
	public final static byte NO_PART_SHIP = 0;
	/**是*/
	public final static byte PART_SHIP = 1;

	/**
	 *  return_order_goods表success字段
	 */
	/**0拒绝*/
	public final static byte SUCCESS_REFUSE = 0;
	/**退货、款中1*/
	public final static byte SUCCESS_RETURNING = 1;
	/**2完成*/
	public final static byte SUCCESS_COMPLETE = 2;
	/**3撤销*/
	public final static byte SUCCESS_REVOKE = 3;

	/**
	 * mp端是否支持退款、退货,orderInfo表字段
	 */
	public final static byte CFG_RETURN_TYPE_Y = 1;
	public final static byte CFG_RETURN_TYPE_N = 2;
	/**
	 * mp端是否支持退款、退货,orderGoods表字段
	 */
	public final static byte IS_CAN_RETURN_Y = 1;
	public final static byte IS_CAN_RETURN_N = 0;
	/**
	 * 0当前订单可以退款
	 * 1当前订单可以退款、退货
	 */
	/**退款支持状态:待发货 已发货 已收货*/
	public final static List<Byte> IS_RETURNMONEY = new ArrayList<Byte>(Arrays.asList(ORDER_WAIT_DELIVERY, ORDER_SHIPPED, ORDER_RECEIVED));
	/**退货支持状态:已收货 已完成*/
	public final static List<Byte> IS_RETURNGOODS = new ArrayList<Byte>(Arrays.asList(ORDER_RECEIVED, ORDER_FINISHED));
	
	/** 
	 * return_order return_type 
	 * 退-退款类型:0仅退款 1:退货退款 2:仅退运费 3:手动退款
	 */
	public final static byte RT_ONLY_MONEY = 0;
	public final static byte RT_GOODS = 1;
	public final static byte RT_ONLY_SHIPPING_FEE = 2;
	public final static byte RT_MANUAL = 3;
	
	public final static String[] RETURN_TYPE_CN = {
			"仅退款",
			"退货退款",
			"仅退运费",
			"手动退款"	
	};
	
	/**
	 * 返利类型，0：普通订单，1：分销返利订单，2：返利会员返利订单
	 */
	public final static byte FANLI_TYPE_DEFAULT = 0;
	public final static byte FANLI_TYPE_DISTRIBUTION_ORDER = 1;
	public final static byte FANLI_TYPE_MEMBER_ORDER = 2;
	
	/**
	 * 	settlement_flag 结算标志：0：未结算，1：已结算，2：不返利
	 */
	
	public final static byte SETTLEMENT_WAIT = 0;
	public final static byte SETTLEMENT_FINISH = 1;
	public final static byte SETTLEMENT_NOT = 2;
	
	/**元转化为分的比例*/
	public final static byte TUAN_TO_FEN = 100;

	/**一分*/
	public final static BigDecimal CENT = new BigDecimal("0.01");
	
	/**商家 0 、买家1*/
	public final static byte IS_MP_Y = 1;
	public final static byte IS_MP_N = 0;
	
	/**
	 * 订单退款退货流程操作
	 */
	/**买家提交物流信息*/
	public final static byte RETURN_OPERATE_MP_SUBMIT_SHIPPING = 0;
	/**买家撤销退款退货*/
	public final static byte RETURN_OPERATE_MP_REVOKE = 1;
	/**商家拒绝退款退货（退货为提交物流后的拒绝）*/
	public final static byte RETURN_OPERATE_ADMIN_REFUSE = 2;
	/**商家同意退货申请*/
	public final static byte RETURN_OPERATE_ADMIN_AGREE_RETURN = 3;
	/**商家拒绝退货申请*/
	public final static byte RETURN_OPERATE_ADMIN_REFUSE_RETURN_GOODS_APPLY = 4;
	/**操作对应的中文*/
	public final static String[] RETURN_OPERATE = {
			"买家提交物流信息",
			"买家撤销退款退货",
			"商家拒绝退款退货",
			"商家同意退货申请",
			"商家拒绝退货申请"
	};
	
	/**
	 * 订单简单操作
	 */
	/**商家关闭订单*/
	public final static byte SIMPLE_OPERATE_CLOSE = 0;
	/**买家取消订单*/
	public final static byte SIMPLE_OPERATE_CANCEL = 1;
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

	/** 全款 **/
    public static final byte PAY_WAY_FULL = 0;
    /** 定金 **/
    public static final byte PAY_WAY_BARGIAN = 1;
    /** 好友代付 **/
    public static final byte PAY_WAY_FRIEND_PAYMENT = 2;

	public String getOrderStatus(byte orderStatusCode) {
		return orderStatus[orderStatusCode];
	}

	public String getGoodsType(byte goodsTypeCode) {
		return goodsType[goodsTypeCode];
	}
	
	/** 是否删除 0： 否； 1： 是 */
	public static final Byte DELETE_NO = 0;
	public static final Byte DELETE_YES = 1;
	
}
