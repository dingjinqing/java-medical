package com.vpu.mp.service.shop.member.card;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;

/**
 * 	等级卡操作
 * @author 黄壮壮
 *
 */
@Service
public class GradeCardOpt extends CardOpt {

	@Autowired
	private UserCardService uCardSvc;
	@Autowired
	private MemberCardService mCardSvc;

	
	public GradeCardOpt() {
		super(CardConstant.MCARD_TP_GRADE);
	}

	@Override
	protected String sendCard(Integer userId, Integer cardId, boolean isActivate) {
		//	是否拥有会员卡
		if(uCardSvc.isHasAvailableGradeCard(userId)) {
			//	设置卡等级
			logger().info("更换等级卡卡号");
			MemberCardRecord oldCard = uCardSvc.getUserGradeCard(userId);
			MemberCardRecord newCard = mCardSvc.getCardById(cardId);
			uCardSvc.changeUserGradeCard(userId, oldCard, newCard, "");
			return uCardSvc.getCardNoByUserAndCardId(userId,cardId);
		}else {
			//	直接发卡
			logger().info("直接发卡");
			MemberCardRecord card = mCardSvc.getCardById(cardId);
			
			UserCardRecord newCard = UserCardRecordBuilder.create()
				.userId(userId)
				.cardId(cardId)
				.cardNo(uCardSvc.getRandomCardNo(cardId))
				.createTime(DateUtil.getLocalDateTime())
				.expireTime(uCardSvc.calcCardExpireTime(card))
				.build();
			if(isActivate || !CardUtil.isNeedActive(card.getActivation())) {
				newCard.setActivationTime(DateUtil.getLocalDateTime());
			}
			
			Integer result = uCardSvc.insertRow(newCard);
			logger().info(String.format("成功向ID为%d的用户，发送了%d张等级会员卡：%s", userId, result,card.getCardName()));
			
			//	开卡赠送积分
			uCardSvc.addUserCardScore(userId,card);
			
			return newCard.getCardNo();
		}
	}

	@Override
	public boolean canSendCard(Integer userId, Integer cardId) {
		// TODO 等待产品规划发放等级卡业务
		return true;
	}
}
