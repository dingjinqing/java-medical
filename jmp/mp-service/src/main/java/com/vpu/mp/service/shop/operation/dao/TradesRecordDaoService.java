package com.vpu.mp.service.shop.operation.dao;

import static com.vpu.mp.db.shop.Tables.TRADES_RECORD;



import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.operation.builder.TradesRecordRecordBuilder;

/**
* @author 黄壮壮
* @Date: 2019年10月24日
* @Description: 对交易记录的操作
*/
@Service
public class TradesRecordDaoService extends ShopBaseService{
	
	/**
	 * 插入交易记录
	 */
	public void insertTradesRecord(TradeOptParam tradeOpt) {
		
		int res = TradesRecordRecordBuilder
			.create(db().newRecord(TRADES_RECORD))
			.tradeNum(tradeOpt.getTradeNum())
			.tradeSn(tradeOpt.getTradeSn())
			.userId(tradeOpt.getUserId())
			.tradeContent(tradeOpt.getTradeContent())
			.tradeType(tradeOpt.getTradeType())
			.tradeFlow(tradeOpt.getTradeFlow())
			.tradeStatus(tradeOpt.getTradeStatus())
			.tradeTime(DateUtil.getLocalDateTime())
			.build()
			.insert();
		logger().info(String.format("成功插入%d条交易记录", res));
	}
}
