package com.vpu.mp.service.pojo.shop.member.userExp;

import java.util.Map;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelDynamicColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;

import lombok.Data;

/**
 * 
 * @author 黄壮壮
 *	用户 excel导出数据模型
 */
@Data
@ExcelSheet
public class UserExcelModel {
	// TODO 国际化
	@ExcelColumn(columnIndex=1,columnName="ID")
	private Integer userId;
	
	/**
	 *	 用户导出的动态选项
	 */
	@ExcelDynamicColumn
	private Map<String,Object> other;
}
