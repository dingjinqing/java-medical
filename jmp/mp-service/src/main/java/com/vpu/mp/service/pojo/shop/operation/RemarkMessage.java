package com.vpu.mp.service.pojo.shop.operation;
/**
 * 
 * @author 黄壮壮
 * 	积分备注内容消息
 */
public class RemarkMessage {
	
	/**
	 * 登录
	 */
	/** 每日登录送积分 */
	public static final String MSG_LOGIN_EVERY_DAY_SEND = "login.every.day.send";
	public static final String MSG_SIGN_SOME_DAY_SEND = "sign.some.day.send";
	/**
	 * 订单
	 */
	public static final String MSG_ORDER_RETURN = "order.return";
	public static final String MSG_ORDER_CANCEL_RETURN = "order.cancel.return";
	public static final String MSG_ORDER_CLOSE_RETURN = "order.close.return";
	public static final String MSG_ORDER_CLOSE_SC_AC = "order.close.score.account";
	public static final String MSG_ORDER_MAKE = "order.make";
	public static final String MSG_ORDER_VIRTUAL_RETURN = "order.virtual.return";
	public static final String MSG_ORDER_VIRTUAL_RETURN_SC_AC = "order.virtual.return.score.account";
	public static final String MSG_ORDER_CANCEL_SCORE_ACCOUNT = "order.cancel.score.account";
	public static final String MSG_ORDER_RETURN_SCORE_ACCOUNT = "order.return.score.account";
	public static final String MSG_ORDER_STORE_SCORE = "order.store.score";
	public static final String MSG_ORDER_CANCEL_RETURN_CARD_ACCOUNT="order.cancel.return.card.account";
	public static final String MSG_ORDER_CLOSE_RETURN_CARD_ACCOUNT="order.close.return.card.account";
	public static final String MSG_ORDER_MAKE_CARD_ACCOUNT_PAY = "order.make.card.account.pay";
	public static final String MSG_ORDER_RETURN_CARD_ACCOUNT = "order.return.card.account";
	public static final String MSG_ORDER_VIRTUAL_RETURN_DEFAULT = "order.virtual.return.default";
	
	/**
	 * 会员卡
	 */
	/** 领卡赠送积分 */
	public static final String MSG_CARD_RECEIVE_SEND = "card.receive.send";
	/** 卡升级赠送积分 */
	public static final String MSG_CARD_UPGRADE = "card.upgrade.send";
	
	/**
	 * 登录
	 */
	
	
	
	/**
	 * 营销 
	 */
	/** 支付有礼 */
	public static final String MSG_PAY_HAS_GIFT = "pay.has.gift";
	/** 评价有礼送积分 */
	public static final String MSG_COMMENT_HAS_GIFT = "comment.has.gift";
	/** 收藏有礼 */
	public static final String MSG_COLLECT_HAS_GIFT = "collect.has.gift";
	/** 瓜分积分 */
	public static final String MSG_DIVIDE_SCORE = "divide.score";
	/** 领取优惠券 */
	public static final String MSG_RECEIVE_COUPON = "receive.coupon";
	/** 好友助力失败奖励 */
	public static final String MSG_FRIENDS_HELP_FAIL = "friends.help.fail";
	
	/**
	 * 管理员
	 */
	/**	管理员操作 */
	public static final String MSG_ADMIN_OPERATION = "admin.operation";
	public static final String MSG_ADMIN_OPERATION_TEST = "admin.operation.test";
	/**	管理员操作- 兑换商品数量 */
	public static final String MSG_ADMIN_EXCHANGE_GOODS = "admin.exchange.goods";
	/**	管理员操作 - 门店服务次数 */
	public static final String MSG_ADMIN_STORE_SERIVICE = "admin.store.serivice";
	/**	管理员操作 - 会员卡余额 */
	public static final String MSG_ADMIN_CARD_ACCOUNT = "admin.card.account";
	public static final String MSG_ADMIN_USER_IMPORT = "admin.user.import";
	
}
