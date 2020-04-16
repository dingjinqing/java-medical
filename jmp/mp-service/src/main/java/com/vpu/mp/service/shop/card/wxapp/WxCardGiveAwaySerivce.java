package com.vpu.mp.service.shop.card.wxapp;

import static com.vpu.mp.db.shop.Tables.GIVE_CARD_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;

import com.vpu.mp.db.shop.tables.records.GiveCardRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;


import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.builder.GiveCardRecordRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.base.UserCardConstant;
import com.vpu.mp.service.pojo.shop.member.ucard.DefaultCardParam;

import java.sql.Timestamp;

import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;


/**
 * 	会员卡转赠服务
 * @author 黄壮壮
 *
 */
@Service
public class WxCardGiveAwaySerivce extends ShopBaseService {
	/**
	 * 	转赠记录正常 
	 */
	public final static Byte FLAG_NORMAL = 0;
	/**
	 * 	转赠记录取消
	 */
	public final static Byte FLAG_QUIT = 1;
	/**
	 * 转赠记录成功
	 */
	public final static Byte FLAG_SUCCESS = 2;
	
	/**
	 *	添加限次卡转赠记录
	 */
	public void addLimitCardGiveAwayRecord(DefaultCardParam param) {
		logger().info("添加限次卡转赠记录信息");
		Timestamp currentTime = DateUtil.getLocalDateTime();
		//	一天之后转赠失效
		Timestamp tomorrowTime = Timestamp.valueOf(currentTime.toLocalDateTime().plusDays(1));
		
		UserCardRecord userCardRecord = UserCardRecordBuilder.create()
			.flag(UserCardConstant.FLAG_CANNOT_USE)
			.giveAwayStatus(UserCardConstant.GIVE_AWAY_ING)
			.build();
		
		GiveCardRecordRecord giveCardRecordRecord = GiveCardRecordRecordBuilder.create(db().newRecord(GIVE_CARD_RECORD))
			.userId(param.getUserId())
			.cardNo(param.getCardNo())
			.createTime(currentTime)
			.deadline(tomorrowTime)
			.build();
		
		this.transaction(()->{
			//	记录转赠记录
			giveCardRecordRecord.insert();
			// 	更新用户卡转赠数据
			db().executeUpdate(userCardRecord, USER_CARD.CARD_NO.eq(param.getCardNo()));
		});
	}
	
	
	/**
	 * 	取消限次卡转赠
	 */
	public void quitLimitCardGiveAway(DefaultCardParam param) {
		logger().info("取消限次卡转赠");
		
		Condition giveCardCondition = GIVE_CARD_RECORD.CARD_NO.eq(param.getCardNo())
								.and(GIVE_CARD_RECORD.FLAG.eq(FLAG_NORMAL));
		GiveCardRecordRecord giveCardRecordRecord = new GiveCardRecordRecord();
		giveCardRecordRecord.setFlag(FLAG_QUIT);
		
		UserCardRecord userCardRecord = UserCardRecordBuilder.create()
				.flag(UserCardConstant.FLAG_NORMAL)
				.giveAwayStatus(UserCardConstant.GIVE_AWAY_NORMAL)
				.build();
		Condition userCardCondition = USER_CARD.CARD_NO.eq(param.getCardNo());
		
		this.transaction(()->{
			db().executeUpdate(giveCardRecordRecord, giveCardCondition);
			db().executeUpdate(userCardRecord, userCardCondition);
		});
	}
	
	
	
}
