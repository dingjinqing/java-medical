package com.vpu.mp.service.shop.card.wxapp;

import static com.vpu.mp.db.shop.Tables.GIVE_CARD_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;

import com.vpu.mp.db.shop.tables.records.UserCardRecord;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.builder.GiveCardRecordRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.ucard.DefaultCardParam;

import java.sql.Timestamp;
import org.springframework.stereotype.Service;


/**
 * 	会员卡转赠服务
 * @author 黄壮壮
 *
 */
@Service
public class WxCardGiveAwaySerivce extends ShopBaseService {
	
	/**
	 *	添加限次卡转赠记录
	 */
	public void addLimitCardGiveAwayRecord(DefaultCardParam param) {
		logger().info("添加限次卡转赠记录信息");
		Timestamp currentTime = DateUtil.getLocalDateTime();
		//	一天之后转赠失效
		Timestamp tomorrowTime = Timestamp.valueOf(currentTime.toLocalDateTime().plusDays(1));
		UserCardRecord userCardRecord = db().newRecord(USER_CARD);
		
		// 	插入数据库
		GiveCardRecordRecordBuilder.create(db().newRecord(GIVE_CARD_RECORD))
			.userId(param.getUserId())
			.cardNo(param.getCardNo())
			.createTime(currentTime)
			.deadline(tomorrowTime)
			.build()
			.insert();
		
		db().executeUpdate(record, condition)
		
			
	}
}
