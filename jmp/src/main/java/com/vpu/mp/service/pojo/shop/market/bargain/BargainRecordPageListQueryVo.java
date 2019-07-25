package com.vpu.mp.service.pojo.shop.market.bargain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Data
public class BargainRecordPageListQueryVo {

	/**
	 * 主键 
	 */
	private int id;
	
	private String goodsName;
	private String username;
	private String mobile;
	private Timestamp createTime;
	
	/**
	 * 已砍金额 
	 */
	private BigDecimal bargainMoney;
	
	/**
	 * 待砍金额 
	 */
	private BigDecimal surplusMoney;
	
	/**
	 * 参与砍价人数 
	 */
	private int userNumber;
	
	/**
	 *  状态 0砍价中，1成功，2失败
	 */
	private byte status;
}
