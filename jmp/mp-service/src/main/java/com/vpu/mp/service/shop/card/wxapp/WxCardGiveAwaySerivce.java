package com.vpu.mp.service.shop.card.wxapp;

import static com.vpu.mp.db.shop.Tables.GIVE_CARD_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GiveCardRecordRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.builder.GiveCardRecordRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.base.UserCardConstant;
import com.vpu.mp.service.pojo.shop.member.exception.CardReceiveFailException;
import com.vpu.mp.service.pojo.shop.member.ucard.DefaultCardParam;
import com.vpu.mp.service.pojo.wxapp.card.vo.CardGiveVo;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.card.LimitCardOpt;


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
	
	@Autowired private LimitCardOpt limitCardOpt;
	@Autowired private QrCodeService qrCodeSvc;
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
	
	/**
	 * 处理判断转赠中的卡
	 * @param cardNo
	 */
	public void dealWithGivingCard(String cardNo) {
		GiveCardRecordRecord giveWayInfo = getNormalGiveCardRecordByCardNo(cardNo);
		if(giveWayInfo != null && giveWayInfo.getDeadline().before(DateUtil.getLocalDateTime())) {
			DefaultCardParam param = new DefaultCardParam();
			param.setCardNo(cardNo);
			quitLimitCardGiveAway(param);
		}
	}
	
	/**
	 * 	领取赠送的会员卡
	 */
	public void getGiveAwayCard(DefaultCardParam param) throws MpException {
		logger().info("领取赠送的会员卡");
		GiveCardRecordRecord giveWayInfo = getNormalGiveCardRecordByCardNo(param.getCardNo());
		if(giveWayInfo == null || giveWayInfo.getDeadline().before(DateUtil.getLocalDateTime())) {
			logger().info("链接已失效");
			throw new CardReceiveFailException(JsonResultCode.CODE_CARD_RECEIVE_VALIDLINK);
		}else if(giveWayInfo.getUserId().equals(param.getUserId())) {
			logger().info("此卡为自己赠送");
			throw new CardReceiveFailException(JsonResultCode.CODE_CARD_RECEIVE_BYSELF);
		}else {
			String cardNo = limitCardOpt.handleSendGiveAwayCard(param.getUserId(), param.getCardId(), param.getCardNo());
			if(StringUtils.isBlank(cardNo)) {
				logger().info("领卡失败");
				throw new CardReceiveFailException();
			}else {
				UserCardRecord userCardRecord = UserCardRecordBuilder.create()
					.flag(UserCardConstant.FLAG_GIVE_FINISHED)
					.giveAwayStatus(UserCardConstant.GIVE_WAY_SUCCESS)
					.build();
				
				GiveCardRecordRecord giveCardRecordRecord = GiveCardRecordRecordBuilder.create()
					.getUserId(param.getUserId())
					.getTime(DateUtil.getLocalDateTime())
					.getCardNo(cardNo)
					.flag(FLAG_SUCCESS)
					.build();
				
				Condition giveCardCondition = GIVE_CARD_RECORD.CARD_NO.eq(param.getCardNo())
						.and(GIVE_CARD_RECORD.FLAG.eq(FLAG_NORMAL));
				
				transaction(()->{
					db().executeUpdate(userCardRecord, USER_CARD.CARD_NO.eq(param.getCardNo()));
					db().executeUpdate(giveCardRecordRecord,giveCardCondition);
				});
			}
		}
	}
	
	/**
	 * 	获取正常转赠的记录
	 * 	@param cardNo
	 * 	@return  GiveCardRecordRecord || null 如果没有数据
	 */
	public GiveCardRecordRecord getNormalGiveCardRecordByCardNo(String cardNo) {
		
		Condition condition = GIVE_CARD_RECORD.FLAG.eq(FLAG_NORMAL)
			.and(GIVE_CARD_RECORD.CARD_NO.eq(cardNo));
		
		return db().fetchOne(GIVE_CARD_RECORD, condition);
	}
	
	/**
	 * 	获取转赠卡分享图片
	 * @param CardGiveVo 里面包括图片路径
	 */
	public CardGiveVo getCardGiveImg(MemberCardRecord card) {
		logger().info("获取转赠卡分享图片");
		CardGiveVo cardGiveVo = new CardGiveVo();
		try {
			String fullShareImg = qrCodeSvc.createCardGiveAwayImage(card);
			URL url = new URL(fullShareImg);
			
			cardGiveVo.setFullShareImg(fullShareImg);
			cardGiveVo.setShareImg(url.getPath());
		} catch (MalformedURLException e) {
			logger().info("错误： 获取转赠卡分享图片: "+"fullShareImg");
		}
		return cardGiveVo;
	}
	
}
