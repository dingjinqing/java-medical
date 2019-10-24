package com.vpu.mp.service.shop.operation.dao;

import static com.vpu.mp.db.shop.Tables.TRADES_RECORD;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.EXCHANGE_SCORE;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.POWER_MEMBER_CARD_ACCOUNT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_CONTENT_BY_CASH;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_STATUS_ENTRY_ACCOUNT;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;

/**
* @author 黄壮壮
* @Date: 2019年10月24日
* @Description: 对交易记录的操作
*/
@Service
public class TradesRecordDaoService extends ShopBaseService{
	
	/**
	 * 插入交易记录
	 * @param data
	 * @param tradeType
	 * @param tradeFlow
	 */
	public void insertTradesRecord(UserCardConsumeBean data, Byte tradeType, Byte tradeFlow) {
		TradesRecordRecord tradesRecord = db().newRecord(TRADES_RECORD);
		//交易额
		tradesRecord.setTradeNum(tradeType==POWER_MEMBER_CARD_ACCOUNT.getValue()?data.getMoney():data.getMoney().abs());
		// 交易单号
		tradesRecord.setTradeSn(StringUtils.isBlank(data.getOrderSn())?"":data.getOrderSn());
		// 交易用户id
		tradesRecord.setUserId(data.getUserId());
		// 交易内容：0：现金，1：积分,此处为现金
		tradesRecord.setTradeContent(TRADE_CONTENT_BY_CASH.getValue());
		// 交易类型说明
		tradesRecord.setTradeType(tradeType);
		// 资金流向：0：收入，1：支出，2：待确认收入
		tradesRecord.setTradeFlow(tradeFlow);
		// 交易状态：0：已入账，1：已到账
		tradesRecord.setTradeStatus(tradeFlow==EXCHANGE_SCORE.getValue()?TRADE_STATUS_ENTRY_ACCOUNT.getValue():tradeFlow);
		// 交易时间
		tradesRecord.setTradeTime(DateUtil.getLocalTimeDate());
		tradesRecord.insert();
	}
}
