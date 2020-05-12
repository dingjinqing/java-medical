package com.vpu.mp.service.shop.order.trade;

import com.vpu.mp.db.shop.tables.TradesRecord;
import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vpu.mp.db.shop.tables.TradesRecord.TRADES_RECORD;

/**
 * table:b2c_trades_record
 * @author 王帅
 *
 */
@Service
public class TradesRecordService extends ShopBaseService{
	public final TradesRecord TABLE = TRADES_RECORD;
	/**交易类型-现金*/
	public final static byte TRADE_CONTENT_MONEY  = 0;
	/**交易类型-积分*/
	public final static byte TRADE_CONTENT_SCORE = 1;
	/**交易状态：0：已入账*/
	public final static byte TRADE_STATUS_ENTRY  = 0;
	/**交易状态：1：已到账*/
	public final static byte TRADE_STATUS_ARRIVAL = 1;
	
	/**
	 * 	添加交易记录
	 * @param money 金额
	 * @param orderSn 订单号
	 * @param userId 会员id
	 * @param tradeContent 交易类型：积分、现金
	 * @param tradeType 交易类型说明（type=RecordTradeEnum）
	 * @param tradeFlow 资金流向（type=RecordTradeEnum）
	 * @param tradeStatus 交易状态 0：已入账 1：已到账
	 */
	public void addRecord(BigDecimal money, String orderSn, Integer userId, byte tradeContent, byte tradeType,
			byte tradeFlow, byte tradeStatus) {
		if(BigDecimalUtil.compareTo(money, null) < 1) {
			return;
		}
		TradesRecordRecord record = db().newRecord(TABLE);
		record.setTradeNum(money);
		record.setTradeSn(orderSn);
		record.setUserId(userId);
		record.setTradeContent(tradeContent);
		record.setTradeType(tradeType);
		record.setTradeFlow(tradeFlow);
		record.setTradeStatus(tradeStatus);
		record.setTradeTime(DateUtil.getSqlTimestamp());
		record.insert();
	}
}
