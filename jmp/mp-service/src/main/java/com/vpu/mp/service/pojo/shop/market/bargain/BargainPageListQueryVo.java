package com.vpu.mp.service.pojo.shop.market.bargain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@Data
public class BargainPageListQueryVo {

	/**
	 * 砍价活动主键 
	 */
	private Integer id;
	
	private String bargainName;
	
	/**
	 * 砍价类型0定人1任意价
	 */
	private Byte bargainType;
    /**
     * 砍价底价
     */
	private BigDecimal expectationPrice;
	
	private Timestamp startTime;
	private Timestamp endTime;
	
	/**
	 * 停用启用的状态：1可用，0停用 
	 */
	private Byte status;

	private Integer goodsId;
	private String goodsName;
	/**
	 * 商品库存 
	 */
	private Integer goodsNumber;
    /**
     * 商品价格
     */
	private BigDecimal shopPrice;
	private String goodsImg;
    /**
     * 是否在售，1在售，0下架
     */
	private Byte isOnSale;

	/**
	 * 砍价活动库存 
	 */
	private Integer stock;
	/**
	 * 成功数量 
	 */
	private Integer successNumber;
	/**
	 * 发起砍价人数 
	 */
	private Integer bargainUserNumber;

    /**
     * 当前活动状态：1进行中，2未开始，3已结束，4已停用
     */
    private Byte currentState;
}
