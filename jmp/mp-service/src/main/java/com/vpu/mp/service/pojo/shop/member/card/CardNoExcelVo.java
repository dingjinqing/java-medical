package com.vpu.mp.service.pojo.shop.member.card;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;

import lombok.Data;

/**
 * 会员卡领取码
 * @author zhaojianqiang
 * @time   下午2:29:52
 */
@Data
@ExcelSheet
public class CardNoExcelVo {
	/** 领取码 */
	@ExcelColumn(columnIndex = 0, columnName = "cardReceive.import.cardNo")
	@ExcelColumnNotNull
	private String code;
}
