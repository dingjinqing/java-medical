package com.vpu.mp.service.pojo.shop.member;


/**
* @author 黄壮壮
* @Date: 2019年8月22日
* @Description: 会员操作记录国际化
*/
public enum MemberOperateRecordEnum {
	/** 管理员操作 */
	ADMIN_OPERATION("member.admin.operation");
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
