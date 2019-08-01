package com.vpu.mp.service.shop.member;

import org.springframework.stereotype.Service;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.pojo.shop.member.card.PowerCardJson;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.*;
/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月30日
 * @Description:
 */
@Service
public class MemberCardService extends ShopBaseService {
	public JsonResultCode addMemberCard(CardParam card) {
		MemberCardRecord cardRecord = new MemberCardRecord();

		/** 设置会员卡的公共属性 */
		setCommonCardAttr(card, cardRecord);

		Byte cardType = card.getCardType();

		/** 判断卡类型 */
		if (NORMAL_TYPE.equals(cardType) || LIMIT_NUM_TYPE.equals(cardType)) {

			/** 会员有效期 */
			Byte expiredType = card.getExpiredType();
			if (FIX_DATETIME.equals(expiredType)) {
				cardRecord.setExpireType(FIX_DATETIME);
				cardRecord.setStartTime(card.getStartTime());
				cardRecord.setEndTime(card.getEndTime());
				// String endTime = card.getEndTime().substring(0, 10).concat(FINAL_TIME);
			} else if (DURING_TIME.equals(expiredType)) {
				cardRecord.setExpireType(DURING_TIME);
				cardRecord.setReceiveDay(card.getReceiveDay());
				cardRecord.setDateType(card.getDateType());
			} else if (FOREVER.equals(expiredType)) {
				cardRecord.setExpireType(FOREVER);
			}

			/** 使用门店 */
			String storeListType = card.getStoreListType();
			if (ALL_SHOP.equals(storeListType)) {
				cardRecord.setStoreList(ALL_SHOP);
			} else if (PART_SHOP.equals(storeListType)) {
				if (card.getStoreList() != null) {
					String storeList = String.join(",", card.getStoreList());
					cardRecord.setStoreList(storeList);
				} else {
					cardRecord.setStoreList(PROHIBITED);
				}
			} else if (PROHIBITED.equals(storeListType)) {
				cardRecord.setStoreList(PROHIBITED);
			}

			/** 领取设置 */
			/** 购买设置 */
			Byte isPay = card.getIsPay();
			if (GET_DIRECTLY.equals(isPay)) {
				cardRecord.setIsPay(GET_DIRECTLY);
			} else if (NEED_BUY.equals(isPay)) {
				cardRecord.setIsPay(NEED_BUY);
				if (BUY_BY_CRASH.equals(card.getPayType())) {
					cardRecord.setPayFee(card.getPayMoney());
				} else if (BUY_BY_SCORE.equals(card.getPayType())) {
					cardRecord.setPayFee(card.getPayScore());
				}
			} else if (NEED_CODE.equals(isPay)) {
				// todo 领取码
				cardRecord.setIsPay(NEED_CODE);
			}
		}

		/** 设置限次与等级会员卡的公共属性 */
		JsonResultCode result = setLimitAndRankCard(card, cardRecord);
		if (result.getCode() != JsonResultCode.CODE_SUCCESS.getCode()) {
			return result;
		}

		/** 处理普通会员卡 */
		if (NORMAL_TYPE.equals(cardType)) {
			return dealWithNormalCard(card, cardRecord);
		} else if (LIMIT_NUM_TYPE.equals(cardType)) {
			/** 处理限次会员卡 */
			return dealWithLimitNumCard(card, cardRecord);
		} else if (RANK_TYPE.equals(cardType)) {
			return dealWithRankCard(card, cardRecord);
		}

		// TODO 没有此类会员卡
		return JsonResultCode.CODE_SUCCESS;

	}

	/**
	 * 设置限次与等级会员卡的公共属性
	 * 
	 * @param card
	 * @param cardRecord
	 */
	private JsonResultCode setLimitAndRankCard(CardParam card, MemberCardRecord cardRecord) {
		Byte cardType = card.getCardType();
		Byte payOwnGood;
		if (NORMAL_TYPE.equals(cardType) || RANK_TYPE.equals(cardType)) {
			// TODO专享商品
			payOwnGood = (byte) (BUTTON_ON.equals(card.getPowerPayOwnGood()) ? 1 : 0);

			boolean flag = false;
			/** 1. 会员折扣 */
			if (CHECKED.equals(card.getPowerCount())) {
				flag = true;
				cardRecord.setDiscount(card.getDisCount());
			} else {
				/** 没有勾选值设置为null */
				cardRecord.setDiscount(null);
			}
			/** 折扣部分商品还是全部商品 */
			if (DISCOUNT_ALL_GOODS.equals(card.getDiscountIsAll())) {
				cardRecord.setDiscountIsAll(DISCOUNT_ALL_GOODS);
			} else if (DISCOUNT_PART_GOODS.equals(card.getDiscountIsAll())) {
				// TODO 处理需要折扣的部分商品
				cardRecord.setDiscountIsAll(DISCOUNT_PART_GOODS);
			}
			/** 2. 会员专享商品 */
			if (BUTTON_ON.equals(card.getPowerPayOwnGood())) {
				// TODO 处理允许会员专享的商家，分类，平台等
				flag = true;
			}
			/** 3. 积分获取 */
			if (CHECKED.equals(card.getPowerScore())) {
				flag = true;
				/** 积分 */
				cardRecord.setSorce(card.getScore());
				
				/** 购物送积分策略json数据 */
				ScoreJson scoreJson = card.getScoreJson();
				cardRecord.setBuyScore(Util.toJson(scoreJson));
			}

			if (!flag && RANK_TYPE.equals(cardType)) {
				/** 必须选择一项会员权益 针对等级卡 */
				return JsonResultCode.CODE_MEMBER_CARD_RIGHTS_EMPTY;
			}

		}
		return JsonResultCode.CODE_SUCCESS;
	}

	/**
	 * 处理等级会员卡的信
	 * 
	 * @param card
	 * @param cardRecord
	 * @return
	 */
	private JsonResultCode dealWithRankCard(CardParam card, MemberCardRecord cardRecord) {

		/** 启用 */
		cardRecord.setFlag(card.getFlag());

		/** 有效时间 */
		cardRecord.setExpireType(FOREVER);

		/** 升级条件 */
		GradeConditionJson conditon = card.getGradeConditionJson();
		cardRecord.setGradeCondition(Util.toJson(conditon));

		/** 等级 */
		cardRecord.setGrade(card.getGrade());

		insertIntoMemberCard(cardRecord);
		return JsonResultCode.CODE_SUCCESS;
	}

	/**
	 * 设置会员卡的公共属性
	 * 
	 * @param card
	 * @param cardRecord
	 * @return
	 */
	private void setCommonCardAttr(CardParam card, MemberCardRecord cardRecord) {
		/** 会员卡名称 */
		cardRecord.setCardName(card.getCardName());
		/** 背景类型 */
		Byte bgType = card.getBgType();
		if (BG_COLOR_TYPE.equals(bgType)) {
			/** 背景色 */
			cardRecord.setBgType(BG_COLOR_TYPE);
			cardRecord.setBgColor(card.getBgColor());
		} else if (BG_IMG_TYPE.equals(bgType)) {
			cardRecord.setBgType(BG_IMG_TYPE);
			cardRecord.setBgImg(card.getBgImg());
		}

		/** 使用须知 */
		cardRecord.setDesc(card.getDesc());
		/** 电话号码 */
		cardRecord.setMobile(card.getMobile());

		/** 设置会员卡类型 */
		Byte cardType = card.getCardType();
		cardRecord.setCardType(cardType);

		/** 激活设置 */
		if (ACTIVE_NO.equals(card.getActivation())) {
			cardRecord.setActivation(ACTIVE_NO);
		} else if (ACTIVE_YES.equals(card.getActivation())) {
			cardRecord.setActivation(ACTIVE_YES);
			String activationCfg = String.join(",", card.getActivationCfgBox());
			cardRecord.setActivationCfg(activationCfg);
			cardRecord.setExamine(card.getExamine());
		}
	}

	/**
	 * 处理创建限次会员卡的信息
	 * 
	 * @param card
	 * @param cardRecord
	 * @return
	 */
	private JsonResultCode dealWithLimitNumCard(CardParam card, MemberCardRecord cardRecord) {

		/** 适用商品 */
		Byte isExchange = card.getIsExchange();
		if (NONE_GOODS.equals(isExchange)) {
			cardRecord.setIsExchang(NONE_GOODS);
		} else if (PART_GOODS.equals(isExchange) || ALL_GOODS.equals(isExchange)) {
			cardRecord.setIsExchang(isExchange);
			/** 设置允许兑换次数 */
			cardRecord.setExchangCount(card.getExchangCount());
			/** 运费策略 */
			cardRecord.setExchangFreight(card.getExchangFreight());

			if (PART_GOODS.equals(isExchange)) {
				/** 处理允许的店铺 */
				String exchangGoods = String.join(",", card.getExchangGoods());
				cardRecord.setExchangGoods(exchangGoods);
			}
		}

		/** 处理使用门店 */
		if (!PROHIBITED.equals(card.getStoreListType())) {
			/** 处理工作日，工作日 */
			cardRecord.setUseTime(card.getUseTime());
			/** 使用次数 */
			cardRecord.setCount(card.getCount());
		}

		/** 会员卡数量 */
		cardRecord.setStock(card.getStock());

		/** 每人领取次数 */
		cardRecord.setLimits(card.getLimits());

		/** insert in to member_card table */
		insertIntoMemberCard(cardRecord);
		return JsonResultCode.CODE_SUCCESS;
	}

	/**
	 * 创建普通会员卡
	 * 
	 * @param card
	 * @param cardRecord
	 * @return
	 */
	private JsonResultCode dealWithNormalCard(CardParam card, MemberCardRecord cardRecord) {

		/** 处理会员权益 */
		boolean flag = false;

		/** 4. 卡充值 */
		if (CHECKED.equals(card.getPowerCard())) {
			flag = true;
			/** 设置金额 */
			cardRecord.setSendMoney(card.getSendMoney());
			/** 设置开卡策略 */
			PowerCardJson powerCardJson = new PowerCardJson();
			FieldsUtil.assignNotNull(card, powerCardJson);
			cardRecord.setChargeMoney(Util.toJson(powerCardJson));
		}
		if (!flag) {
			/** 必须选择一项会员权益 */
			return JsonResultCode.CODE_MEMBER_CARD_RIGHTS_EMPTY;
		}

		/** insert in to member_card table */
		insertIntoMemberCard(cardRecord);
		return JsonResultCode.CODE_SUCCESS;
	}

	/**
	 * 将数据放入到数据库
	 * 
	 * @param cardRecord
	 */
	private int insertIntoMemberCard(MemberCardRecord cardRecord) {
		return db().executeInsert(cardRecord);
	}
}