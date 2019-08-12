package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

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
