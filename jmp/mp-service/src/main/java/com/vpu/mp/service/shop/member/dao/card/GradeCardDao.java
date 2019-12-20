package com.vpu.mp.service.shop.member.dao.card;

import org.jooq.Condition;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
/**
 * 
 * @author 黄壮壮
 * 等级卡数据交互层
 */
@Service
public class GradeCardDao extends ShopBaseService {
	
	/**
	 * 获取所有没有被删除的等级卡
	 * @return 
	 */
	public Result<MemberCardRecord> getAllNoDeleteCard() {
		Condition condition = DSL.noCondition();
		condition = condition
					.and(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_GRADE))
					.and(MEMBER_CARD.DEL_FLAG.eq(CardConstant.MCARD_DF_NO));
		return getCardList(condition);
		
	}

	public Result<MemberCardRecord> getCardList(Condition condition) {
		return db().selectFrom(MEMBER_CARD).where(condition).orderBy(MEMBER_CARD.GRADE.asc()).fetchInto(MEMBER_CARD);
	}

}
