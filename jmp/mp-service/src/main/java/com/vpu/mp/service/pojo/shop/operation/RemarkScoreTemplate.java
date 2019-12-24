package com.vpu.mp.service.pojo.shop.operation;
/**
 * 备注模块(用户输入: 0,登录:1001,订单2001,会员卡:3001,营销: 5001,管理员：6001，依次类推)
 * @author 黄壮壮
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
