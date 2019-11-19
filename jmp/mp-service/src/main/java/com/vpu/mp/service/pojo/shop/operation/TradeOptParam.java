package com.vpu.mp.service.pojo.shop.operation;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/**
 * @author 黄壮壮
 * @Date: 2019年11月18日
 * @Description: 交易操作的属性
 *               默认定义值{@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
 */
@Builder
@Data
public class TradeOptParam {
	// 操作的系统管理员id,默认值为0
	private Integer adminUserId = 0;
	// 交易用户id
	private Integer userId;
	// 交易额
	private BigDecimal tradeNum;
	// 交易内容
	private Byte tradeContent;
	// 交易类型
	private Byte tradeType;
	// 交易资金流向
	private Byte tradeFlow;
	// 交易状态
	private Byte tradeStatus;
	// 交易单号
	private String tradeSn;
	/** 交易是否写入库,默认为true*/
	private boolean tradeFlag = true;
	
	
}
