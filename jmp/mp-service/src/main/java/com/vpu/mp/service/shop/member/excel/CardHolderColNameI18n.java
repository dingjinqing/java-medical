package com.vpu.mp.service.shop.member.excel;

import java.util.HashMap;
import java.util.Map;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.AbstractExcelDisposer;
import com.vpu.mp.service.foundation.excel.util.IDymicColNameI18n;
import com.vpu.mp.service.foundation.util.Util;
/**
 * 	持卡会员列表excel动态字段国际化
 * @author 黄壮壮
 *
 */
public class CardHolderColNameI18n implements IDymicColNameI18n {
	//	是否提交激活申请
	public static final String IS_SUBMIT = "isSubmit";
	//	审核状态
	public static final String STATUS = "examineStatus"; 
	//	卡余额
	public static final String CARD_BALANCE = "balance";
	//	消费次数
	public static final String CONSUME_TIMES = "consumeTimes";
	//	充值次数
	public static final String CHARGE_TIMES = "chargeTimes";

	private Map<String,String> map=null;
	public CardHolderColNameI18n() {
		super();
		map = new HashMap<>();
		map.put(IS_SUBMIT, JsonResultMessage.CARD_EXAMINE_SUBMIT_TITLE);
		map.put(STATUS, JsonResultMessage.CARD_EXAMINE_STATUS_TITLE);
		map.put(CARD_BALANCE, JsonResultMessage.CARD_EXAMINE_MONEY_TITLE);
		map.put(CONSUME_TIMES, JsonResultMessage.CARD_EXAMINE_CONSUME_TITLE);
		map.put(CHARGE_TIMES, JsonResultMessage.CARD_EXAMINE_CHARGE_TITLE);
	}
	@Override
	public String i18nName(String name,String language) {
		String message = map.get(name);
		return Util.translateMessage(language, message, AbstractExcelDisposer.LANGUAGE_TYPE_EXCEL);
	}
}