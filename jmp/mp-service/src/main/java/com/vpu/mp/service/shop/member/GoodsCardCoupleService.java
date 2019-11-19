package com.vpu.mp.service.shop.member;

import com.vpu.mp.db.shop.tables.GoodsCardCouple;
import com.vpu.mp.db.shop.tables.records.GoodsCardCoupleRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.shop.member.dao.GoodsCardCoupleDao;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_STORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_PLAT;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_BRAND;

/**
 * 会员卡商品(分类,商家分类...)关联类
 *
 * @author 孔德成
 * @date 2019/10/30 15:31
 */
@Service
public class GoodsCardCoupleService extends ShopBaseService {

	@Autowired
	public UserCardDaoService userCardDao;
	@Autowired
	public GoodsCardCoupleDao goodsCardDao;

	/**
	 *
	 * 根据会员卡等级获取会员卡关联表
	 *
	 * @param grade
	 * @return
	 */
	public Map<Byte, List<Integer>> getGradeCardCoupleGoodsList(String grade) {
		return db().select(GOODS_CARD_COUPLE.GCTA_ID, GOODS_CARD_COUPLE.TYPE).from(GOODS_CARD_COUPLE)
				.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(GOODS_CARD_COUPLE.CARD_ID))
				.where(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_GRADE))
				.and(MEMBER_CARD.FLAG.eq(CardConstant.MCARD_FLAG_USING)).and(MEMBER_CARD.GRADE.le(grade))
				.and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(MEMBER_CARD.PAY_OWN_GOOD.eq(CardConstant.PAY_OWN_GOOD_YES)).fetch()
				.intoGroups(GOODS_CARD_COUPLE.TYPE, GOODS_CARD_COUPLE.GCTA_ID);
	}

	/**
	 * 获取普通会员卡关联表
	 *
	 * @param userId
	 * @return type为key, id为value
	 */
	public Map<Byte, List<Integer>> getGeneralCardCoupleGoodsList(Integer userId) {
		return db().select(GOODS_CARD_COUPLE.GCTA_ID, GOODS_CARD_COUPLE.TYPE).from(GOODS_CARD_COUPLE)
				.leftJoin(USER_CARD).on(GOODS_CARD_COUPLE.CARD_ID.eq(USER_CARD.CARD_ID)).leftJoin(MEMBER_CARD)
				.on(MEMBER_CARD.ID.eq(GOODS_CARD_COUPLE.CARD_ID))
				.where(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_NORMAL))
				.and(USER_CARD.FLAG.eq(CardConstant.UCARD_FG_USING)).and(USER_CARD.USER_ID.eq(userId))
				.and(USER_CARD.EXPIRE_TIME.isNull().or(USER_CARD.EXPIRE_TIME.gt(DateUtil.getLocalDateTime())))
				.and(MEMBER_CARD.ACTIVATION.eq(CardConstant.MCARD_ACT_NO)
						.or(MEMBER_CARD.ACTIVATION.eq(CardConstant.MCARD_ACT_YES)
								.and(USER_CARD.ACTIVATION_TIME.isNotNull())))
				.fetch().intoGroups(GOODS_CARD_COUPLE.TYPE, GOODS_CARD_COUPLE.GCTA_ID);
	}

	/**
	 * 获取用户的会员关联商品表数据
	 *
	 * @param userId
	 * @return k 商品,分类,商家分类,品牌  v id
	 */
	public Map<Byte, List<Integer>> getGoodsCardCouple(Integer userId) {
		// 获取会员等级
		String userCardGrade = userCardDao.getUserCardGrade(userId);
		Map<Byte, List<Integer>> gradeCoupleGoods = new HashMap<>(16);
		if (userCardGrade != null) {
			gradeCoupleGoods = getGradeCardCoupleGoodsList(userCardGrade);
		}
		// 获取普通会员卡
		Map<Byte, List<Integer>> cardCoupleGoods = getGeneralCardCoupleGoodsList(userId);
		gradeCoupleGoods.forEach((key, value) -> {
			if (cardCoupleGoods.containsKey(key)) {
				value.addAll(cardCoupleGoods.get(key));
				cardCoupleGoods.put(key, value);
			} else {
				cardCoupleGoods.put(key, value);
			}
		});
		return cardCoupleGoods;
	}


	/**
	 * 获取专享商品
	 */
	public List<GoodsCardCoupleRecord> getOwnGoods(Integer cardId) {
		return getGoodsCardCoupleRecord(cardId,COUPLE_TP_GOODS);
	}

	/**
	 * 获取专享商家
	 */
	public List<GoodsCardCoupleRecord> getOwnStoreCategory(Integer cardId) {
		return getGoodsCardCoupleRecord(cardId,COUPLE_TP_STORE);
	}

	/**
	 * 获取专享平台
	 */
	public List<GoodsCardCoupleRecord> getOwnPlatformCategory(Integer cardId) {
		return getGoodsCardCoupleRecord(cardId,COUPLE_TP_PLAT);
	}

	/**
	 * 获取专享品牌
	 * @return
	 */
	public List<GoodsCardCoupleRecord> getOwnBrandId(Integer cardId) {
		return getGoodsCardCoupleRecord(cardId,COUPLE_TP_BRAND);
	}

	private List<GoodsCardCoupleRecord> getGoodsCardCoupleRecord(Integer cardId, Byte type) {
		 return goodsCardDao.selectGoodsCardCouple(cardId, type);
	}


}
