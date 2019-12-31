package com.vpu.mp.service.pojo.shop.operation;
/**
 * @author 黄壮壮
 * 备注模块: 
 * 用户输入: 0, 代表的是用户自己输入
 * 登录签到相关:1开头四位数，如1001,
 * 订单相关: 2开头四位数，如2001,
 * 会员卡相关:3开头四位数，如3001,
 * 营销相关: 4开头四位数，如4001,
 * 管理员操作相关：5开头四位数，如5001,
 * 依次类推)
 * 
 */
public enum RemarkTemplate {
	/**	用户输入 */
	USER_INPUT_MSG(0,null),
	
	
	/**
	 * 登录
	 */
	/**	每日登录送积分 */
	LOGIN_EVERY_DAY_SEND(1001,RemarkMessage.MSG_LOGIN_EVERY_DAY_SEND),
	/**	连续签到{0}天，获得{1}积分 */
	SIGN_SOME_DAY_SEND(1002,RemarkMessage.MSG_SIGN_SOME_DAY_SEND),
	
	
	/**
	 * 订单
	 */
	/**	订单: {订单号}退余额 */
	ORDER_RETURN(2001,RemarkMessage.MSG_ORDER_RETURN),
	ORDER_CANCEL(2002,RemarkMessage.MSG_ORDER_CANCEL_RETURN),
	ORDER_CLOSE(2003,RemarkMessage.MSG_ORDER_CLOSE_RETURN),
	ORDER_CLOSE_SCORE_ACCOUNT(2004,RemarkMessage.MSG_ORDER_CLOSE_SC_AC),
	ORDER_MAKE(2005,RemarkMessage.MSG_ORDER_MAKE),
	ORDER_VIRTUAL_RETURN(2006,RemarkMessage.MSG_ORDER_VIRTUAL_RETURN),
	ORDER_VIRTUAL_RETURN_SCORE_ACCOUNT(2007,RemarkMessage.MSG_ORDER_VIRTUAL_RETURN_SC_AC),
	ORDER_CANCEL_SCORE_ACCOUNT(2008,RemarkMessage.MSG_ORDER_CANCEL_SCORE_ACCOUNT),
	ORDER_RETURN_SCORE_ACCOUNT(2009,RemarkMessage.MSG_ORDER_RETURN_SCORE_ACCOUNT),
	ORDER_STORE_SCORE(2010,RemarkMessage.MSG_ORDER_STORE_SCORE),
	
	
	/**
	 * 会员卡
	 */
	/**	领卡赠送 */
	CARD_RECEIVE_SEND(3001,RemarkMessage.MSG_CARD_RECEIVE_SEND),
	/**	卡升级赠送 */
	CARD_UPGRADE(3002,RemarkMessage.MSG_CARD_UPGRADE),
	
	/**
	 * 营销
	 */
	/**	支付有礼 */
	PAY_HAS_GIFT(4001,RemarkMessage.MSG_PAY_HAS_GIFT),
	/**	评价有理 */
	COMMENT_HAS_GIFT(4002,RemarkMessage.MSG_COMMENT_HAS_GIFT),
	/**	收藏有礼 */
	COLLECT_HAS_GIFT(4003,RemarkMessage.MSG_COLLECT_HAS_GIFT),
	/**	瓜分积分 */
	DIVIDE_SCORE(4004,RemarkMessage.MSG_DIVIDE_SCORE),
	/**	领取优惠券 */
	RECEIVE_COUPON(4005,RemarkMessage.MSG_RECEIVE_COUPON),
	/**	好友助力失败 */
	FRIENDS_HELP_FAIL(4006,RemarkMessage.MSG_FRIENDS_HELP_FAIL), 
	
	/** 
	 * 管理员
	 */
	/**	管理员操作 */
	ADMIN_OPERATION(6001,RemarkMessage.MSG_ADMIN_OPERATION),
	/**	管理员测试 */
	ADMIN_OPERATION_TEST(6002,RemarkMessage.MSG_ADMIN_OPERATION_TEST),
	/**	管理员操作- 兑换商品数量 */
	ADMIN_EXCHANGE_GOODS(6003,RemarkMessage.MSG_ADMIN_EXCHANGE_GOODS),
	/**	管理员操作 - 门店服务次数 */
	ADMIN_STORE_SERIVICE(6004,RemarkMessage.MSG_ADMIN_STORE_SERIVICE),
	/**	管理员操作 - 会员卡余额 */
	ADMIN_CARD_ACCOUNT(6005,RemarkMessage.MSG_ADMIN_CARD_ACCOUNT);
	
	/**	返回码 */
	public  Integer code;
	/**	返回信息 */
	private String message;
	
	private RemarkTemplate(Integer code,String message) {
		this.code = code;
		this.message = message;
	}
	
	public static String getMessageByCode(Integer code) {
		for(RemarkTemplate item: RemarkTemplate.values()) {
			if(code.equals(item.code)) {
				return item.message;
			}
		}
		return null;
	}
}
