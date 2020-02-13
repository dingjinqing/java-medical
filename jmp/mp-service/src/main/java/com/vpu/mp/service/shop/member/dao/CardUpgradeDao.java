package com.vpu.mp.service.shop.member.dao;

import org.jooq.Record6;
import org.jooq.SelectSeekStep1;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.builder.CardUpgradeRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.wxapp.card.CardUpgradeVo;

import static com.vpu.mp.db.shop.Tables.CARD_UPGRADE;

import java.sql.Timestamp;
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

	public PageResult<CardUpgradeVo> getGradeList(SearchCardParam param) {
		SelectSeekStep1<Record6<String, Timestamp, String, String, Integer, Integer>, Integer> select = db().select(CARD_UPGRADE.NEW_CARD_NAME,CARD_UPGRADE.CREATE_TIME,CARD_UPGRADE.NEW_GRADE,CARD_UPGRADE.OLD_GRADE,CARD_UPGRADE.OLD_CARD_ID,CARD_UPGRADE.NEW_CARD_ID)
			.from(CARD_UPGRADE)
			.where(CARD_UPGRADE.USER_ID.eq(param.getUserId()))
			.orderBy(CARD_UPGRADE.ID.desc());
		
		return getPageResult(select, param.getCurrentPage(),param.getPageRows(), CardUpgradeVo.class);
		
	}
}
