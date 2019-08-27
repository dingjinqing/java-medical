package com.vpu.mp.service.pojo.shop.operation;
/**
* @author 黄壮壮
* @Date: 2019年8月22日
* @Description: 交易类型，交易内容.资金流向，交易状态
*/
public enum RecordTradeEnum {
	
	/** 交易状态-已入账 */
	TRADE_STATUS_ENTRY_ACCOUNT(0),
	/** 交易状态-已到账 */
	TRADE_STATUS_ARRIVE_ACCOUNT(1),
	
	
	/** 资金流量-收入  */
	TRADE_FLOW_INCOME(0),
	/** 资金流量-支出  */
	TRADE_FLOW_OUTCOME(1),
	/** 资金流量-待确认收入  */
	TRADE_FLOW_TO_BE_CONFIRMED(2),
	
	/** 交易内容 - 现金*/
	TRADE_CONTENT_BY_CASH(0),
	/** 交易内容 - 积分*/
	TRADE_CONTENT_BY_SCORE(1),
	

		
	
	/**----------------------------------------
	 * 以下为交易内容为现金所对应的交易类型说明
	 ----------------------------------------*/
	/** 余额变动默认值 */
	ACCOUNT_DEFAULT(0),
	/** 微信支付 */
	WX_PAY(1),
	/** 余额支付 */
	ACCOUNT_PAY(2),
	/**会员卡支付 */
	MEMBER_CARD_PAY(3),
	/** 现金退款 */
	CASH_REFUND(4),
	/** 用户余额退款 */
	MEMBER_ACCOUNT_REFUND(5),
	/** 用户会员卡余额退款 */
	MEMBER_CARD_ACCOUNT_REFUND(6),
	/** 返利 */
	REBATE(7),
	/** 抽奖获得余额 */
	GET_ACCOUNT_BY_LOTTERY(8),
	/** 用户余额充值 */
	POWER_MEMBER_ACCOUNT(9),
	/** 用户会员卡余额充值 */
	POWER_MEMBER_CARD_ACCOUNT(10),
	
	/**----------------------------------------
	 * 以下为交易内容为积分所对应的交易类型说明
	 ----------------------------------------*/
	
	/** 积分支付 */
	SCORE_PAY(1),
	/** 积分兑换 */
	EXCHANGE_SCORE(2),
	/** 幸运大抽奖消耗积分 */
	CONSUMP_SCORE_BY_LUCKY_LOTTERY(3),
	/** 积分充值 */
	POWER_SCORE(4),
	/** 用户登录送积分 */
	SEND_SCORE_BY_LOGIN(5),
	/** 用户签到送积分 */
	SEND_SCORE_BY_SIGN_IN(6),
	/** 开卡赠送积分 */
	SEND_SCORE_BY_CREATE_CARD(7),
	/** 买单送积分 */
	SEND_SCORE_BY_PAY_BILL(8),
	/** 交易退货退积分 */
	RETURN_SCORE_BY_REFUND(9),
	/** 组团瓜分积分 */
	GET_SCORE_BY_GROUP_DIVIDING(10),
	/** 抽奖获得积分 */
	GET_SCORE_BY_LOTTERY(11),
	
	/**--------------------*/
	/** 充值 */
	RECHARGE(0),
	/** 消费 */
	CONSUMPTION(1),
	
	/** 门店兑换类型 */
	STORE_EXCHANGE_TYPE(1),
	/** 消费次数类型 */
	CONSUMPTION_TIMES_TYPE(0),
	
	/** 按规则 */
	CHARGE_TYPE_BY_RULE(0),
	/** 自定义 */
	CHARGE_TYPE_BY_CUSTOMIZE(1),
	
	/** -金额积分比例 100积分比一元 */
	SCALE_HUNDRED(100),
	
	/** 积分变得是否来自退款 */
	IS_FROM_REFUND_Y(1),
	IS_FROM_REFUND_N(0);
	private byte val;
	RecordTradeEnum(int value) {
		this.val = (byte)value;
	}
	
	public byte getValue() {
		return this.val;
	}
}
