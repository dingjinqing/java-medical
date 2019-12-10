package com.vpu.mp.service.shop.member.dao;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.builder.CardUpgradeRecordBuilder;

import static com.vpu.mp.db.shop.Tables.CARD_UPGRADE;
/**
* @author 黄壮壮
* @Date: 2019年10月31日
* @Description:
*/
@Service
public class CardUpgradeDao extends ShopBaseService {

	public void insert(Integer userId,MemberCardRecord oldCard, MemberCardRecord newCard,String option) {
		CardUpgradeRecordBuilder
			.create(db().newRecord(CARD_UPGRADE))
			.userId(userId)
			.oldCardId(oldCard.getId())
			.newCardId(newCard.getId())
			.oldGrade(oldCard.getGrade())
			.newGrade(newCard.getGrade())
			.oldCardName(oldCard.getCardName())
			.newCardName(newCard.getCardName())
			.gradeCondition(newCard.getGradeCondition())
			.operate(option)
			.build()
			.insert();
	}
}
