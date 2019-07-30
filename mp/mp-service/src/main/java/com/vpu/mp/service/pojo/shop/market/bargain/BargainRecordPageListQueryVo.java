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
	private BigDecimal goodsPrice;
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
	
	/**
	 * 砍价类型0定人1任意价 
	 */
	private byte bargainType;
	
	/**
	 * 任意金额结算模式的结算金额底价  
	 */
	private BigDecimal floorPrice;
	
	/**
	 *固定人数模式， 预期砍价最低金额
	 */
	private BigDecimal expectationPrice;
	
}
