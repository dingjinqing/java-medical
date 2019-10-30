package com.vpu.mp.service.shop.member.dao;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyResultVo;

import static com.vpu.mp.db.shop.Tables.CARD_EXAMINE;
/**
* @author 黄壮壮
* @Date: 2019年10月30日
* @Description:
*/
@Service
public class CardVerifyDaoService extends ShopBaseService {
	/**
	 * 获取卡的审核结果
	 * @param cardNo
	 */
	public CardVerifyResultVo getCardVerifyResult(String cardNo){
		return db().select(CARD_EXAMINE.ID,CARD_EXAMINE.STATUS,CARD_EXAMINE.REFUSE_DESC,CARD_EXAMINE.REFUSE_TIME,CARD_EXAMINE.PASS_TIME)
					.from(CARD_EXAMINE)
					.where(CARD_EXAMINE.CARD_NO.eq(cardNo))
					.orderBy(CARD_EXAMINE.ID.desc())
					.fetchAnyInto(CardVerifyResultVo.class);
	}
}
