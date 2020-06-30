package com.vpu.mp.service.pojo.shop.member.exception;

import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;

/**
* @author 黄壮壮
* @Date: 2019年11月4日
* @Description: 限次卡发放领取次数已达上限
*/
public class LimitCardAvailSendNoneException extends MpException {
	public LimitCardAvailSendNoneException() {
		super(JsonResultCode.CODE_LIMIT_CARD_AVAIL_SEND_NONE);
	}
	public LimitCardAvailSendNoneException(JsonResultCode errorCode) {
		super(errorCode);
	}
}
