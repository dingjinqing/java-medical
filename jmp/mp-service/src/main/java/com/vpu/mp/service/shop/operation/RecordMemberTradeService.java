package com.vpu.mp.service.shop.operation;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;

/**
* @author 黄壮壮
* @Date: 2019年8月22日
* @Description: 交易记录
*/
@Service
public class RecordMemberTradeService extends ShopBaseService{
	public void insertRecord(TradesRecordRecord oldRecord) {
		logger().info("开始插入trades_record表");
		TradesRecordRecord newRecord = new TradesRecordRecord();
		/** 确保null 不被执行 */
		FieldsUtil.assignNotNull(oldRecord,newRecord);
		newRecord.insert();
		
	}
}
