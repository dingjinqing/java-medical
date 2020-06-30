package com.vpu.mp.service.pojo.shop.member.exception;

import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;

/**
* @author 黄壮壮
* @Date: 2019年11月4日
* @Description: 会员卡不能重复发放
*/
public class CardSendRepeatException extends MpException {
	public CardSendRepeatException() {
		super(JsonResultCode.CODE_CARD_SEND_REPEAT);
	}
	public CardSendRepeatException(JsonResultCode errorCode) {
		super(errorCode);
	}
}
