package com.vpu.mp.service.shop.card;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUTTON_ON;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_CRASH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_SCORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CHECKED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_BGT_COLOR;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_BGT_IMG;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_CTP_COUPON;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_CTP_PACKAGE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DIS_ALL;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DIS_PART;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_DURING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_FIX;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_FOREVER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_FLAG_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ISE_ALL;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ISE_NON;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ISE_PART;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ISP_BUY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ISP_CODE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ISP_DEFAULT;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_REA_CODE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_REA_PWD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_STP_ALL;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_STP_BAN;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_STP_PART;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_SUSE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_SUSE_OK;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.builder.MemberCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomAction;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomRights;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomRights.RightSwitch;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRenew;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRight;
import com.vpu.mp.service.shop.member.CardReceiveCodeService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
/**
 * 创建会员卡
 * @author 黄壮壮
 *
 */
@Service
public class CardCreateService extends ShopBaseService{
	@Autowired
	private CardDaoService cardDao;
	@Autowired
	private MemberCardService memberCardSvc;
	@Autowired
	private CardReceiveCodeService cardReceiveCode;
	
	/**
	 * 添加会员卡
	 */
	public void createMemberCard(CardParam card) {
		logger().info("添加会员卡");
		MemberCardRecord mCard = initMembercardRecordByCfgData(card);
		this.transaction(() -> {
			int cardId = addMemberCardAndGetCardId(mCard);
			card.setId(cardId);
			initOwncfg(card);
			initCardBatchCfg(card);
		});
	}

	/**
	 * 更新会员卡
	 */
	public void updateMemberCard(CardParam card) {
		if(isNull(card.getId())) {
			logger().info("会员卡id不能为空");
			return;
		}
		logger().info("更新会员卡");
		MemberCardRecord cardRecord = initMembercardRecordByCfgData(card);
		transaction(() -> {
			updateMemberCardById(cardRecord, card.getId());
			initOwncfg(card);
			initCardBatchCfg(card);
		});
	}
	
	
	/**
	 * 用配置信息初始化MemberCardRecord
	 */
	private MemberCardRecord initMembercardRecordByCfgData(CardParam param) {
		logger().info("开始初始化会员卡");
		MemberCardRecordBuilder cardBuilder = MemberCardRecordBuilder.create();
		if (CardUtil.isNormalCard(param.getCardType())) {
			initNormalCardCfg(param, cardBuilder);
		} else if (CardUtil.isLimitCard(param.getCardType())) {
			initLimitCardCfg(param, cardBuilder);
		} else if (CardUtil.isGradeCard(param.getCardType())) {
			initGradeCardCfg(param, cardBuilder);
		}
		return cardBuilder.build();
	}
	
	
	/**
	 * 初始化普通会员卡配置信息
	 */
	private void initNormalCardCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化普通会员卡");
		initCommonCardCfg(param, cardBuilder);
		initCardDiscountCfg(param, cardBuilder);
		initIsAllowPayOwnGoods(param, cardBuilder);
		initCardScoreCfg(param, cardBuilder);
		initCardChargeCfg(param, cardBuilder);
		initCardCouponCfg(param, cardBuilder);
		initCardValidityPeriod(param, cardBuilder);
		initCardStoreList(param, cardBuilder);
		initReceiveCardCfg(param, cardBuilder);
		initFreeshipCfg(param,cardBuilder);
		initRenewCardCfg(param,cardBuilder);
		initCustonRights(param,cardBuilder);
		initCustomActions(param,cardBuilder);
	}
	
	
	/**
	 * 初始化限次会员卡配置信息
	 */
	private void initLimitCardCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化限次会员卡配置信息");
		initCommonCardCfg(param, cardBuilder);
		initCardValidityPeriod(param, cardBuilder);
		initCardApplicableGoodsCfg(param, cardBuilder);
		initCardStoreList(param, cardBuilder);
		initReceiveCardCfg(param, cardBuilder);
	}

	/**
	 * 初始化等级会员卡配置信息
	 */
	private void initGradeCardCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化等级会员卡配置信息");
		initCommonCardCfg(param, cardBuilder);
		initCardDiscountCfg(param, cardBuilder);
		initCardScoreCfg(param, cardBuilder);
		initIsAllowPayOwnGoods(param, cardBuilder);
		initGradeBasicCfg(param, cardBuilder);
		initReceiveCardCfg(param, cardBuilder);
	}
	
	
	/**
	 * 初始化会员卡的公共属性
	 */
	private void initCommonCardCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化会员卡的公共属性");
		cardBuilder.cardType(param.getCardType()).cardName(param.getCardName()).desc(param.getDesc())
				.mobile(param.getMobile());

		initCardBackground(param, cardBuilder);

	}
	
	/**
	 * 初始化会员卡的背景
	 */
	private void initCardBackground(CardParam card, MemberCardRecordBuilder cardBuilder) {
		if (MCARD_BGT_COLOR.equals(card.getBgType())) {
			cardBuilder.bgType(card.getBgType()).bgColor(card.getBgColor());

		} else if (MCARD_BGT_IMG.equals(card.getBgType())) {
			cardBuilder.bgType(card.getBgType()).bgImg(card.getBgImg());
		}
	}
	
	
	/**
	 * 初始化等级卡的基本配置
	 */
	private void initGradeBasicCfg(CardParam card, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化等级卡的升级条件，等级，是否启用等信息");
		cardBuilder.flag(card.getFlag() != null ? card.getFlag() : MCARD_FLAG_USING).expireType(MCARD_ET_FOREVER)
				.gradeCondition(Util.toJson(card.getGradeConditionJson())).grade(card.getGrade());
	}

	

	/**
	 * 初始化会员卡折扣配置
	 */
	private void initCardDiscountCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化会员卡折扣配置");
		// 1. 会员折扣
		if (CHECKED.equals(param.getPowerCount())) {
			cardBuilder.discount(param.getDisCount());
		} else {
			cardBuilder.discount(null);
		}

		// 2. 折扣部分商品还是全部商品
		if (MCARD_DIS_ALL.equals(param.getDiscountIsAll())) {
			cardBuilder.discountIsAll(param.getDiscountIsAll());
		} else if (MCARD_DIS_PART.equals(param.getDiscountIsAll())) {
			cardBuilder.discountIsAll(param.getDiscountIsAll());
			initDiscountPartGoods(param, cardBuilder);
		}
		
		// 3. 不可与优惠券公用
		cardBuilder.cannotUseCoupon(param.getCannotUseCoupon());
	}
	
	/**
	 * 初始化会员卡有效期
	 */
	private void initCardValidityPeriod(CardParam card, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化会员卡有效期");
		Byte expiredType = card.getExpiredType();
		if (MCARD_ET_FIX.equals(expiredType)) {
			cardBuilder.expireType(expiredType).startTime(card.getStartTime()).endTime(card.getEndTime());

		} else if (MCARD_ET_DURING.equals(expiredType)) {
			cardBuilder.expireType(expiredType).receiveDay(card.getReceiveDay()).dateType(card.getDateType());

		} else if (MCARD_ET_FOREVER.equals(expiredType)) {
			cardBuilder.expireType(expiredType);
		}
	}

	
	/**
	 * 初始化积分配置
	 */
	private void initCardScoreCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化积分配置");
		if (CHECKED.equals(param.getPowerScore())) {
			ScoreJson scoreJson = param.getScoreJson();
			cardBuilder.sorce(param.getScore()).buyScore(Util.toJson(scoreJson));
		} else {
			cardBuilder.sorce(null).buyScore(null);
		}
	}
	

	/**
	 * 初始化卡充值配置
	 */
	private void initCardChargeCfg(CardParam card, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化卡充值配置");
		if (CHECKED.equals(card.getPowerCard())) {

			cardBuilder.sendMoney(card.getSendMoney()).chargeMoney(Util.toJson(card.getPowerCardJson()));
		} else {
			cardBuilder.sendMoney(null).chargeMoney(null);
		}
	}

	/**
	 * 初始化会员卡优惠券配置
	 */
	private void initCardCouponCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化会员卡优惠券配置");
		boolean flag = BUTTON_ON.equals(param.getSendCoupon());
		cardBuilder.sendCouponSwitch(flag ? BYTE_ONE : BYTE_ZERO);
		if (flag) {
			if (MCARD_CTP_COUPON.equals(param.getCouponType())) {
				cardBuilder.sendCouponType(param.getCouponType())
						.sendCouponIds(Util.listToString(param.getCouponIds()));
			} else if (MCARD_CTP_PACKAGE.equals(param.getCouponType())) {
				cardBuilder.sendCouponType(param.getCouponType());
				if(param.getCouponPackage()!=null) {
					cardBuilder.sendCouponIds(String.valueOf(param.getCouponPackage()));
				}		
			}
		}
	}

	
	/**
	 * 	初始化包邮信息
	 */
	private void initFreeshipCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化包邮信息");
		CardFreeship freeship = param.getFreeship();
		if(freeship!=null) {
			if(freeship.getType()!=null) {
				cardBuilder.freeshipLimit(freeship.getType());
				if(freeship.getNum()!=null && freeship.getNum()>= 0) {
					cardBuilder.freeshipNum(freeship.getNum());
				}
			}
			
		}
	}
	
	/**
	 * 初始化续费信息
	 */
	private void initRenewCardCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化续费信息");
		CardRenew cardRenew = param.getCardRenew();
		if(cardRenew == null) {
			return;
		}
		cardBuilder
			.renewMemberCard(cardRenew.getRenewMemberCard()) // 是否可续费
			.renewType(cardRenew.getRenewType()) // 续费类型
			.renewNum(cardRenew.getRenewNum())	// 续费数值
			.renewTime(cardRenew.getRenewTime()); // 续费时长
		// 续费单位
		CardRenew.DateType dateType = cardRenew.getRenewDateType();
		if(dateType != null) {
			cardBuilder.renewDateType((byte)dateType.ordinal());
		}
	}
	
	
	/**
	 * 初始化会员卡门店列表
	 */
	private void initCardStoreList(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化会员卡门店列表");
		Byte storeListType = param.getStoreListType();
		assert isAllStore(storeListType) || isPartStore(storeListType) || isNonStore(storeListType) : "选择使用的门店范围类型错误";

		String fmt = "[%s]", storeList = null;
		Byte storeUseSwitch = null;

		if (isAllStore(storeListType)) {
			storeList = String.format(fmt, storeListType);
			storeUseSwitch = MCARD_SUSE_OK;

		} else if (isPartStore(storeListType)) {
			if (isNotNull(param.getStoreList()) && param.getStoreList().size() > 0) {
				storeList = String.format(fmt, Util.listToString(param.getStoreList()));
				storeUseSwitch = MCARD_SUSE_OK;
			} else {
				storeList = String.format(fmt, MCARD_STP_BAN);
				storeUseSwitch = MCARD_SUSE_NO;
			}
		} else if (isNonStore(storeListType)) {
			storeList = String.format(fmt, storeListType);
			storeUseSwitch = MCARD_SUSE_NO;
		}
		cardBuilder.storeList(storeList).storeUseSwitch(storeUseSwitch);

		if (CardUtil.isLimitCard(param.getCardType())) {

			if (isAllStore(storeListType) || isPartStore(storeListType)) {
				// 1. 使用时间 2.卡总次数
				cardBuilder.useTime(param.getUseTime()).count(param.getCount());
			}
		}
	}
	
	/**
	 * 	初始化自定义权益信息
	 */
	private void initCustonRights(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化自定义权益信息");
		CardCustomRights customRights = param.getCustomRights();
		Byte flag = null;
		String rightsJson = null;
		
		if(customRights != null) {
			RightSwitch rightsFlag = customRights.getCustomRightsFlag();
			if(rightsFlag != null) {
				flag = (byte)rightsFlag.ordinal();
			}
			
			List<CardRight> customRightsAll = customRights.getCustomRightsAll();
			
			// 将图片的URL处理成相对路径
			if(customRightsAll != null) {
				for(CardRight right: customRightsAll) {
					String imgUrl = right.getCrightImage();
					if(!StringUtils.isBlank(imgUrl)) {
						try {
							URL url = new URL(imgUrl);
							imgUrl = url.getPath();
						} catch (MalformedURLException e) {
							imgUrl = null;
						}
					}
					right.setCrightImage(imgUrl);
				}
			}

			
			if(customRightsAll != null && customRightsAll.size()>0) {
				rightsJson = Util.toJson(customRightsAll);
			}
		}
		
		cardBuilder
			.customRightsFlag(flag)
			.customRights(rightsJson);
	}
	
	/**
	 * 初始化自定义激活项
	 */
	private void initCustomActions(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化自定义激活项");
		List<CardCustomAction> customAction = param.getCustomAction();
		if(customAction != null) {
			String customOptions = Util.toJsonNotNull(customAction);
			cardBuilder.customOptions(customOptions);
		}
	}
	
	
	/**
	 * 初始化领取设置配置
	 */
	private void initReceiveCardCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("正在初始化领取需要的条件");

		if (CardUtil.isNormalCard(param.getCardType()) || 
				CardUtil.isLimitCard(param.getCardType())) {
			initReceiveCardCondition(param, cardBuilder);
		}

		if (CardUtil.isLimitCard(param.getCardType())) {
			initReceiveAndSendNumLimitCfg(param, cardBuilder);
		}

		initActivationAndExamine(param, cardBuilder);
	}
	
	
	/**
	 * 初始化领取需要的条件
	 */
	private void initReceiveCardCondition(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("设置是否会员卡需要购买： 直接领取；需要购买；领取码");
		Byte isPay = param.getIsPay();
		assert MCARD_ISP_DEFAULT.equals(isPay) || MCARD_ISP_BUY.equals(isPay)
				|| MCARD_ISP_CODE.equals(isPay) : "领取设置类型参数错误";

		cardBuilder.isPay(isPay);
		if (MCARD_ISP_BUY.equals(isPay)) {
			if (BUY_BY_CRASH.equals(param.getPayType())) {
				BigDecimal payMoney = param.getPayMoney() != null ? param.getPayMoney() : BigDecimal.ZERO;
				cardBuilder.payType(param.getPayType()).payFee(payMoney);
			} else if (BUY_BY_SCORE.equals(param.getPayType())) {
				BigDecimal payScore = param.getPayScore() != null ? param.getPayScore() : BigDecimal.ZERO;
				cardBuilder.payType(param.getPayType()).payFee(payScore);
			}
		} else if (MCARD_ISP_CODE.equals(isPay)) {
			if (MCARD_REA_CODE.equals(param.getReceiveAction()) || MCARD_REA_PWD.equals(param.getReceiveAction())) {
				cardBuilder.receiveAction(param.getReceiveAction());
				// return card.getBatchIdList; -> 根据id-更新card_batch表
			} else {
				cardBuilder.receiveAction(BYTE_ZERO);
			}
		}
	}
	

	/**
	 * 会员卡发放总量和领取设置
	 */
	private void initReceiveAndSendNumLimitCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		// 1. 会员卡数量
		cardBuilder.stock(param.getStock());
		// 2.每人领取次数
		cardBuilder.limit(param.getLimits());
	}

	
	/**
	 * 初始化会员卡领取批次
	 */
	private void initCardBatchCfg(CardParam card) {
		logger().info("初始化会员领取批次");
		cardReceiveCode.updateCardBatch(card.getId(), card.getBatchIdList());
	}
	
	/**
	 * 初始化会员卡激活设置
	 */
	private void initActivationAndExamine(CardParam card, MemberCardRecordBuilder cardBuilder) {
		/** 激活设置 */
		if (MCARD_ACT_NO.equals(card.getActivation())) {
			cardBuilder.activation(card.getActivation());

		} else if (MCARD_ACT_YES.equals(card.getActivation())) {
			cardBuilder.activation(card.getActivation()).activationCfg(Util.listToString(card.getActivationCfgBox()))
					.examine(card.getExamine());
		}
	}

	


	
	/**
	 * 会员专享的商品，商家，平台,品牌等
	 */
	private void initOwncfg(CardParam card) {

		if (CardUtil.isNormalOrGradeCard(card.getCardType()) && 
				isAllowPayOwnGoods(card)) {
			logger().info("正在处理会员卡的专享商品");

			if (isNotNull(card.getOwnGoodsId())) {
				cfgOwnGoods(card.getOwnGoodsId(), card.getId());
			}
			if (isNotNull(card.getOwnStoreCategoryIds())) {
				cfgOwnStoreCategory(card.getOwnStoreCategoryIds(), card.getId());
			}

			if (isNotNull(card.getOwnPlatFormCategoryIds())) {
				cfgOwnPlatformCategory(card.getOwnPlatFormCategoryIds(), card.getId());
			}

			if (isNotNull(card.getOwnBrandId())) {
				cfgOwnBrandId(card.getOwnBrandId(), card.getId());
			}
		}
	}
	
	/**
	 * 更新折扣指定商品信息： 商品，商家，平台
	 */
	private void initDiscountPartGoods(CardParam param, MemberCardRecordBuilder cardBuilder) {
		logger().info("更新折扣指定商品信息： 商品，商家，平台,品牌");

		// 1.商品
		if (isNotNull(param.getGoodsId())) {
			String discountGoodsIds = Util.listToString(param.getGoodsId());
			logger().info("折扣商品id: " + discountGoodsIds);
			cardBuilder.discountGoodsId(discountGoodsIds);
		}

		// 2.商家
		if (isNotNull(param.getShopCategoryIds())) {
			String discountSortIds = Util.listToString(param.getShopCategoryIds());
			logger().info("折扣商家分类id " + discountSortIds);
			cardBuilder.discountSortId(discountSortIds);
		}

		// 3. 平台
		if (isNotNull(param.getPlatformCategoryIds())) {
			String discountCatId = Util.listToString(param.getPlatformCategoryIds());
			logger().info("折扣平台分类id " + discountCatId);
			cardBuilder.discountCatId(discountCatId);
		}

		// 4.品牌
		if (isNotNull(param.getDiscountBrandId())) {
			String brandId = Util.listToString(param.getDiscountBrandId());
			logger().info("折扣品牌id " + brandId);
			cardBuilder.discountBrandId(brandId);
		}
	}
	
	/**
	 * 初始化是否允许购买专属商品
	 */
	private void initIsAllowPayOwnGoods(CardParam param, MemberCardRecordBuilder cardBuilder) {
		cardBuilder.payOwnGood(isAllowPayOwnGoods(param) ? BYTE_ONE : BYTE_ZERO);
	}
	
	/**
	 * 配置专享商家
	 */
	private void cfgOwnStoreCategory(List<Integer> storeIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		memberCardSvc.batchUpdateStoreCategory(storeIdList, cardList);
	}

	/**
	 * 配置专享平台
	 */
	private void cfgOwnPlatformCategory(List<Integer> platformIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		memberCardSvc.batchUpdatePlatformCategory(platformIdList, cardList);
	}

	/**
	 * 配置专享品牌
	 */
	private void cfgOwnBrandId(List<Integer> brandIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		memberCardSvc.batchUpdateBrandId(brandIdList, cardList);
	}
	
	
	/**
	 * 初始化适用商品配置
	 */
	private void initCardApplicableGoodsCfg(CardParam card, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化适用商品配置");
		Byte isExchange = card.getIsExchange();
		assert (isExchangNonGoods(isExchange) || isExchangPartGoods(isExchange)
				|| isExchangAllGoods(isExchange)) : "适用商品类型参数";

		if (isExchangNonGoods(isExchange)) {
			cardBuilder.isExchang(isExchange);
		} else if (isExchangPartGoods(isExchange) && isNotNull(card.getExchangGoods())) {

			if (card.getExchangGoods().size() > 0) {
				// 1.兑换次数2.运费策略 3. 商品id
				cardBuilder
				.isExchang(isExchange)
				.exchangGoods(Util.listToString(card.getExchangGoods()));
			} else {
				cardBuilder.isExchang(MCARD_ISE_NON).exchangGoods(null);
			}
		} else if (isExchangAllGoods(isExchange)) {
			cardBuilder.isExchang(isExchange).exchangGoods(null);
		}
		
		if(CardUtil.isLimitCard(card.getCardType())) {
			cardBuilder.exchangCount(card.getExchangCount())
			.exchangFreight(card.getExchangFreight());
		}
	}
	
	
	/**
	 * 配置专享商品
	 */
	private void cfgOwnGoods(List<Integer> goodsIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		memberCardSvc.batchUpdateGoods(goodsIdList, cardList);

	}
	

	/**
	 * 通过会员卡id,更新折扣指定商品信息： 商品，商家，平台等信息
	 *
	 * @param card
	 */
	public void updateDiscountPartGoodsByCardId(CardParam card) {
		logger().info("更新会员折扣指定商品");
		MemberCardRecordBuilder cardBuilder = MemberCardRecordBuilder.create();
		Integer id = card.getId();

		/** 准备更新数据 */
		initDiscountPartGoods(card, cardBuilder);

		/** 更新到数据库 */
		updateMemberCardById(cardBuilder.build(), id);
	}





	

	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 将数据放入到数据库
	 */
	private int addMemberCardAndGetCardId(MemberCardRecord cardRecord) {
		return cardDao.addMemberCardAndGetCardId(cardRecord);
	}
	
	
	/**
	 * 会员卡数据更新
	 */
	private void updateMemberCardById(MemberCardRecord cardRecord, Integer cardId) {
		if(isNull(cardId)) {
			return;
		}
		cardDao.updateMemberCardById(cardRecord, cardId);
	}
	
	


	
	private boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	private boolean isNull(Object obj) {
		return obj == null;
	}
	
	private boolean isNonStore(Byte type) {
		return MCARD_STP_BAN.equals(type);
	}

	private boolean isPartStore(Byte type) {
		return MCARD_STP_PART.equals(type);
	}

	private boolean isAllStore(Byte type) {
		return MCARD_STP_ALL.equals(type);
	}
	
	private boolean isExchangNonGoods(Byte isExchange) {
		return MCARD_ISE_NON.equals(isExchange);
	}

	private boolean isExchangPartGoods(Byte isExchange) {
		return MCARD_ISE_PART.equals(isExchange);
	}

	private boolean isExchangAllGoods(Byte isExchange) {
		return MCARD_ISE_ALL.equals(isExchange);
	}
	
	private boolean isAllowPayOwnGoods(CardParam param) {
		return BUTTON_ON.equals(param.getPowerPayOwnGood());
	}

}
