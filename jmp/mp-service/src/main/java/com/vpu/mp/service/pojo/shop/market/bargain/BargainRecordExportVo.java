package com.vpu.mp.service.pojo.shop.market.bargain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
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
	@ExcelColumn(columnName = "bargain.record.list.id",columnIndex = 0)
	private Integer id;
	
	@ExcelColumn(columnName = "bargain.record.list.goods_name",columnIndex = 1)
	private String goodsName;
	
	@ExcelColumn(columnName = "bargain.record.list.username",columnIndex = 2)
	private String username;
	
	@ExcelColumn(columnName = "bargain.record.list.mobile",columnIndex = 3)
	private String mobile;
	
	@ExcelColumn(columnName = "bargain.record.list.create_time",columnIndex = 4)
	private Timestamp createTime;
	
	/**
	 * 已砍金额 
	 */
	@ExcelColumn(columnName = "bargain.record.list.bargain_money",columnIndex = 5)
	private BigDecimal bargainMoney;
	
	/**
	 * 待砍金额 
	 */
	@ExcelColumn(columnName = "bargain.record.list.surplus_money",columnIndex = 6)
	private BigDecimal surplusMoney;
	
	/**
	 * 参与砍价人数 
	 */
	@ExcelColumn(columnName = "bargain.record.list.user_number",columnIndex = 7)
	private Integer userNumber;
	
	/**
	 *  状态 0砍价中，1成功，2失败
	 */
	@ExcelColumn(columnName = "bargain.record.list.status",columnIndex = 8)
	private String statusName;
	
	/**
	 *  状态 0砍价中，1成功，2失败
	 */
	@ExcelIgnore
	private Byte status;
	
	/**
	 * 砍价类型0定人1任意价
	 */
	@ExcelIgnore
	private Byte bargainType;
	
	/**
	 *  任意低价
	 */
	@ExcelIgnore
	private BigDecimal floorPrice;
	
	@ExcelIgnore
	private BigDecimal goodsPrice;
	
	/**
	 * 固定人数模式， 预期砍价最低金额
	 */
	@ExcelIgnore
	private BigDecimal expectationPrice;
}
