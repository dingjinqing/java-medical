package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 商品返利明细列表出参
 * @author 常乐
 * 2019年8月12日
 */
@Data
public class RebateGoodsDetailVo {
	private String goodsName;
	private BigDecimal rebateGoodsMoney;
	private String orderSn;
	private String mobile;
	private String username;
	private String distributorMobile;
	private String distributorName;
	private Integer rebatePrecent;
	private BigDecimal canRebateMoney;
	private Byte rebateStatus;
	private Timestamp finishTime;
}
