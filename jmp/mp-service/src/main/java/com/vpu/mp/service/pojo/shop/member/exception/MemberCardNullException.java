package com.vpu.mp.service.pojo.shop.member.exception;

import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;

/**
* @author 黄壮壮
* @Date: 2019年11月1日
* @Description: 会员卡为空
*/
public class MemberCardNullException extends MpException {
	public MemberCardNullException() {
		super(JsonResultCode.CODE_MEMBER_CARD_DELETE);
	}
	public MemberCardNullException(JsonResultCode errorCode) {
		super(errorCode);
	}
}
