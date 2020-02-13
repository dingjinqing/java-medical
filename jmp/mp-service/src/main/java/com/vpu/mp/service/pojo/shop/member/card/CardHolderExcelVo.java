package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;

import lombok.Data;

/**
 * 持卡会员excel-出参
 * 
 * @author zhaojianqiang
 * @time 下午2:46:43
 */
@Data
public class CardHolderExcelVo {
	/** - 会员ID */
	@ExcelColumn(columnIndex = 0, columnName = "user.import.name")
	private Integer userId;
	
	/** - 昵称 */
	@ExcelColumn(columnIndex = 1, columnName = "user.import.name")
	private String username;
	
	@ExcelColumn(columnIndex = 2, columnName = "user.import.name")
	/** - 手机号 */
	private String mobile;
	
	@ExcelColumn(columnIndex = 3, columnName = "user.import.name")
	/** - 邀请人 */
	private String invitedName;
	
	@ExcelColumn(columnIndex = 4, columnName = "user.import.name")
	/** -领卡时间 */
	private Timestamp createTime;
	
	@ExcelColumn(columnIndex = 5, columnName = "user.import.name")
	/** - 会员卡号 */
	private String cardNo;
	
	@ExcelColumn(columnIndex = 6, columnName = "user.import.name")
	private String nflag;
	
	/** -过期时间 */
	private Timestamp expireTime;
}
