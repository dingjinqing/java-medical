package com.vpu.mp.service.pojo.shop.operation;
/**
 * @author 黄壮壮
 * 备注模块: 
 * 用户输入: 0,
 * 登录签到相关:1开头四位数，如1001,
 * 订单相关: 2开头四位数，如2001,
 * 会员卡相关:3开头四位数，如3001,
 * 营销相关: 4开头四位数，如4001,
 * 管理员操作相关：5开头四位数，如5001,
 * 依次类推)
 * 
 */
public enum RemarkScoreTemplate {
	/** 用户输入 */
	USER_INPUT_MSG(0,null),
	
	/**
	 * 登录
	 */
	
	/**
	 * 会员卡
	 */
	/** 开卡赠送 */
	CARD_OPEN_SEND(3001,RemarkScoreMessage.MSG_CARD_OPEN_SEND),
	
	
	/**
	 * 营销
	 */
	
	
	
	/** 
	 * 管理员
	 */
	/** 管理员操作 */
	ADMIN_OPERATION(6001,RemarkScoreMessage.MSG_ADMIN_OPERATION);
	
	
	
	/** 返回码 */
	public  int code;
	/** 返回信息 */
	private String message;
	
	private RemarkScoreTemplate(int code,String message) {
		this.code = code;
		this.message = message;
	}
}
