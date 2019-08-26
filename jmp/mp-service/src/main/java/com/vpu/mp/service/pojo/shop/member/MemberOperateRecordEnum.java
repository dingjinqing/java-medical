package com.vpu.mp.service.pojo.shop.member;


/**
* @author 黄壮壮
* @Date: 2019年8月22日
* @Description: 会员操作记录国际化
*/
public enum MemberOperateRecordEnum {
	/** 管理员操作 */
	ADMIN_OPERATION("member.admin.operation"),
	/** - 兑换商品数量 */
	EXCHANGE_GOODS_NUM("member.exchange.goods.num"),
	/** - 门店服务次数 */
	STORE_SERVICE_TIMES("member.store.service.times"),
	/** - 会员卡余额 */
	MEMBER_CARD_ACCOUNT("member.card.account");
	
	/** 操作记录信息 */
	private String operationInfo;
	private MemberOperateRecordEnum(String operationInfo) {
		this.operationInfo = operationInfo;
	}
	
	public static void main(String... args) {
		MemberOperateRecordEnum adminOperation = MemberOperateRecordEnum.ADMIN_OPERATION;
		System.out.println(adminOperation.getValue());
	}
	
	public String getValue() {
		return this.operationInfo;
	}
	
}
