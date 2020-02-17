package com.vpu.mp.service.pojo.shop.member.card;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;

import lombok.Data;

/**
 * 会员卡+密码领取码
 * @author zhaojianqiang
 * @time   下午2:29:52
 */
@Data
@ExcelSheet
public class CardNoPwdExcelVo {
	/** 卡号 */
	@ExcelColumn(columnIndex = 0, columnName = "cardReceive.import.codeNo")
	@ExcelColumnNotNull
	private String codeNo;
	
	
	/** 密码 */
	@ExcelColumn(columnIndex = 1, columnName = "cardReceive.import.cardPwd")
	@ExcelColumnNotNull
	private String cardPwd;
}
