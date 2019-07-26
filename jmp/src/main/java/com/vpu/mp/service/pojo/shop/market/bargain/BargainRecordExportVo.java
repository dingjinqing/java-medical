package com.vpu.mp.service.pojo.shop.market.bargain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月26日
 */
@ExcelSheet
@Data
public class BargainRecordExportVo {

	/**
	 * 主键 
	 */
	@ExcelColumn(columnName = "id")
    @ExcelColumnNotNull
	private int id;
	
	@ExcelColumn(columnName = "goods_name")
    @ExcelColumnNotNull
	private String goodsName;
	
	@ExcelColumn(columnName = "username")
    @ExcelColumnNotNull
	private String username;
	
	@ExcelColumn(columnName = "mobile")
    @ExcelColumnNotNull
	private String mobile;
	
	@ExcelColumn(columnName = "create_time")
    @ExcelColumnNotNull
	private Timestamp createTime;
	
	/**
	 * 已砍金额 
	 */
	@ExcelColumn(columnName = "bargain_money")
    @ExcelColumnNotNull
	private BigDecimal bargainMoney;
	
	/**
	 * 待砍金额 
	 */
	@ExcelColumn(columnName = "surplus_money")
    @ExcelColumnNotNull
	private BigDecimal surplusMoney;
	
	/**
	 * 参与砍价人数 
	 */
	@ExcelColumn(columnName = "user_name")
    @ExcelColumnNotNull
	private int userNumber;
	
	/**
	 *  状态 0砍价中，1成功，2失败
	 */
	@ExcelColumn(columnName = "status")
    @ExcelColumnNotNull
	private byte status;
}
