package com.vpu.mp.service.pojo.shop.member.exception;

import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
/**
* @author 黄壮壮
* @Description: 会员卡领取失败
*/
public class CardReceiveFailException extends MpException {
	private static final long serialVersionUID = 1L;
	public CardReceiveFailException() {
		super(JsonResultCode.CODE_CARD_RECEIVE_FAIL);
	}
	public CardReceiveFailException(JsonResultCode errorCode) {
		super(errorCode);
	}
}
