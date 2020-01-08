package com.vpu.mp.service.pojo.shop.market.bargain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Data
public class BargainUpdateParam {

	/**
	 * 活动的主键 
	 */
	@NotNull
	@Min(1)
	private Integer id;
	
	/**
	 *  状态：1可用，0停用
	 */
	@Min(0)
    @Max(1)
	private Byte status;
	
	private String bargainName;
    private Byte       bargainType;

    private Timestamp startTime;
    private Timestamp  endTime;
    private Double     bargainMin;
    private Double     bargainMax;
    private Integer    stock;
    private String     mrkingVoucherId;
    private String     rewardCouponId;
    private Byte       bargainMoneyType;
    private BigDecimal bargainFixedMoney;
    private BigDecimal bargainMinMoney;
    private BigDecimal bargainMaxMoney;
    private Byte       freeFreight;

    private BigDecimal expectationPrice;
	
	private PictorialShareConfig shareConfig;
}
