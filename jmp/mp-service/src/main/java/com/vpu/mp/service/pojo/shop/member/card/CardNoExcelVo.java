package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
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
	/** - 会员ID */
	@ExcelColumn(columnIndex = 0, columnName = "usercard.import.userId")
	@ExcelColumnNotNull
	private Integer userId;
}
