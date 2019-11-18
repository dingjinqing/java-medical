package com.vpu.mp.service.pojo.shop.operation.builder;

import java.math.BigDecimal;

import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;

/**
* @author 黄壮壮
* @Date: 2019年11月18日
* @Description: 
*/

public class TradeOptParamBuilder {
	private TradeOptParam tradeOpt;
	
	private TradeOptParamBuilder(){
		tradeOpt = new TradeOptParam();
	}
	private TradeOptParamBuilder(TradeOptParam tradeOpt) {
		this.tradeOpt = tradeOpt;
	}
	
	
	public static TradeOptParamBuilder create() {
		return new TradeOptParamBuilder();
	}
	
	
	public static TradeOptParamBuilder create(TradeOptParam tradeOpt) {
		return new TradeOptParamBuilder(tradeOpt);
	}

	public TradeOptParamBuilder adminUserId (Integer adminUserId) {
		tradeOpt.setAdminUserId(adminUserId);
		return this;
	}

	public TradeOptParamBuilder userId (Integer userId) {
		tradeOpt.setUserId(userId);
		return this;
	}

	public TradeOptParamBuilder tradeNum (BigDecimal tradeNum) {
		tradeOpt.setTradeNum(tradeNum);
		return this;
	}

	public TradeOptParamBuilder tradeContent (Byte tradeContent) {
		tradeOpt.setTradeContent(tradeContent);
		return this;
	}

	public TradeOptParamBuilder tradeType (Byte tradeType) {
		tradeOpt.setTradeType(tradeType);
		return this;
	}

	public TradeOptParamBuilder tradeFlow (Byte tradeFlow) {
		tradeOpt.setTradeFlow(tradeFlow);
		return this;
	}

	public TradeOptParamBuilder tradeStatus (Byte tradeStatus) {
		tradeOpt.setTradeStatus(tradeStatus);
		return this;
	}

	public TradeOptParamBuilder tradeSn (String tradeSn) {
		tradeOpt.setTradeSn(tradeSn);
		return this;
	}

	public TradeOptParam build() {
		return tradeOpt;
	}
}
