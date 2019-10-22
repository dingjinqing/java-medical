package com.vpu.mp.service.pojo.shop.member.card;
/**
* @author 黄壮壮
* @Date: 2019年10月22日
* @Description:
*/
public class CardMessage {
	// 门店支付
	public static final String STORE_PAYMEMBT = "member.card.charge.money.payment";
	//发卡原因： 管理员发卡 - 门店服务次数 
	public static final String SEND_CARD_REASON = "member.card.charge.money.reason";
	// 管理发卡
	public static final String ADMIN_SEND_CARD = "member.card.admin.send.card";
	// 开卡赠送
	public static final String OPEN_CARD_SEND = "member.card.open.send";
	// 系统检测升级
	public static final String SYSTEM_UPGRADE = "member.card.system.upgrade";
	// 后台管理员操作
	public static final String ADMIN_OPTION = "member.backend.admin.option";
	
}
