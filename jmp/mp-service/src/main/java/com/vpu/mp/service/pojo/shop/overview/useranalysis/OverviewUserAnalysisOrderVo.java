package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import java.util.Date;

import lombok.Data;

/**
 * 成交用户分析
 * @author liangchen
 * @date 2019年7月19日
 */
@Data
public class OverviewUserAnalysisOrderVo {
	/** 成交客户数json  */
	private Date refDate;
	private Integer oldOrderUserData;
	private Integer newOrderUserData;
	/** 付款金额json */
	private Double oldPaidMoney;
	private Double newPaidMoney;
	/** 成交客户数total */
	private Integer orderUserDataTotal;
	private Integer oldOrderUserDataTotal;
	private Integer newOrderUserDataTotal;
	/** 付款金额total */
	private Integer totalPaidMoneyTotal;
	private Double oldPaidMoneyTotal;
	private Double newPaidMoneyTotal;
	/** 客户数占比total */
	private Double orderUserDataRate;
	private Double oldOrderUserDataRate;
	private Double newOrderUserDataRate;
	/** 客单价json */
	private Double oldPrice;
	private Double newPrice;
	/** 客单价total */
	private Double priceTotal;
	private Double oldPriceTotal;
	private Double newPriceTotal;
	/** 转化率json */
	private Integer loginData;
	private Double oldTransRate;
	private Double newTransRate;
	/** 转化率total */
	private Double transRateTotal;
	private Double oldTransRateTotal;
	private Double newTransRateTotal;
	/** 客户数涨幅 */
	private Double orderUserDataRange;
	private Double oldOrderUserDataRange;
	private Double newOrderUserDataRange;
	/** 付款金额涨幅 */
	private Double totalPaidMoneyRange;
	private Double oldPaidMoneyRange;
	private Double newPaidMoneyRange;
	/** 客户数占比涨幅 */
	private Double orderUserDataRateRange;
	private Double oldOrderUserDataRateRange;
	private Double newOrderUserDataRateRange;
	/** 客单价涨幅 */
	private Double priceRange;
	private Double oldPriceRange;
	private Double newPriceRange;
	/** 转化率total */
	private Double transRateRange;
	private Double oldTransRateRange;
	private Double newTransRateRange;
}
