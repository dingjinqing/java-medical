package com.vpu.mp.service.shop.member;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.*;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponSrcConstant;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleCard;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.gift.UserAction;
import com.vpu.mp.service.pojo.shop.member.MemberEducationEnum;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.account.AddMemberCardParam;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;
import com.vpu.mp.service.pojo.shop.member.account.MemberCardVo;
import com.vpu.mp.service.pojo.shop.member.builder.CardBatchVoBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.MemberCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.*;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomRights;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomRights.RightSwitch;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRenew;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRenew.DateType;
import com.vpu.mp.service.pojo.shop.member.card.show.CardUseStats;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRight;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.shop.store.service.order.ServiceOrderDetailVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.wxapp.member.card.MemberCardPageDecorationVo;
import com.vpu.mp.service.shop.card.CardFreeShipService;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.card.*;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.member.excel.UserImExcelWrongHandler;
import com.vpu.mp.service.shop.operation.RecordTradeService;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.store.StoreService;
import jodd.util.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Condition;
import org.jooq.InsertValuesStep3;
import org.jooq.Result;
import org.jooq.SelectSeekStep1;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.net.MalformedURLException;
import java.net.URL;
import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.MemberCard.MEMBER_CARD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.*;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.*;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;
import static org.jooq.impl.DSL.count;


/**
 *
 * @author 黄壮壮
 * @Date: 2019年7月30日
 * @Description: 会员卡服务..
 */
@Service
public class MemberCardService extends ShopBaseService {
	@Autowired
	private RecordTradeService tradeService;
	@Autowired
	private CardDaoService cardDao;
	@Autowired
	private OrderGoodsService orderGoodsDao;
	@Autowired
	private ServiceOrderService serviceOrderDao;
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private CardVerifyService cardVerifyService;
	@Autowired
	private CardReceiveCodeService cardReceiveCode;
	@Autowired
	private GoodsCardCoupleService goodsCardCoupleService;
	@Autowired
	private CouponGiveService couponGiveService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private QrCodeService qrCodeService;
	@Autowired
	private GradeCardService gradeCardService;
    @Autowired
    protected DomainConfig domainConfig;
    @Autowired
    protected ImageService imageService;
	@Autowired 
	private NormalCardOpt normalCardOpt;
	@Autowired
	private LimitCardOpt limitCardOpt;
	@Autowired
	private GradeCardOpt gradeCardOpt;
	@Autowired
	private CardFreeShipService freeShipSvc;

    
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
	 * 初始化会员卡领取批次
	 */
	private void initCardBatchCfg(CardParam card) {
		logger().info("初始化会员领取批次");
		cardReceiveCode.updateCardBatch(card.getId(), card.getBatchIdList());
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
	 * 初始化是否允许购买专属商品
	 */
	private void initIsAllowPayOwnGoods(CardParam param, MemberCardRecordBuilder cardBuilder) {
		cardBuilder.payOwnGood(isAllowPayOwnGoods(param) ? BYTE_ONE : BYTE_ZERO);
	}

	private boolean isAllowPayOwnGoods(CardParam param) {
		return BUTTON_ON.equals(param.getPowerPayOwnGood());
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
	 * 会员卡发放总量和领取设置
	 */
	private void initReceiveAndSendNumLimitCfg(CardParam param, MemberCardRecordBuilder cardBuilder) {
		// 1. 会员卡数量
		cardBuilder.stock(param.getStock());
		// 2.每人领取次数
		cardBuilder.limit(param.getLimits());
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

	private boolean isNonStore(Byte type) {
		return MCARD_STP_BAN.equals(type);
	}

	private boolean isPartStore(Byte type) {
		return MCARD_STP_PART.equals(type);
	}

	private boolean isAllStore(Byte type) {
		return MCARD_STP_ALL.equals(type);
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
	 * 为单张会员卡设置专享商品 | 商家分类 | 平台分类
	 * 
	 * @param cardId 会员卡id
	 * @param ownId  专享商品id列表 | 商家分类id列表 | 平台分类id列表
	 * @param type   标签关联类型 如：
	 *               {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.GOODS_TYPE }
	 */
	public void addGoodsCardCouple(Integer cardId, List<Integer> ownId, Byte type) {
		this.batchUpdateGoods(ownId, Arrays.asList(cardId), type);
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
	 * 配置专享商品
	 */
	private void cfgOwnGoods(List<Integer> goodsIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		batchUpdateGoods(goodsIdList, cardList);

	}

	/**
	 * 配置专享商家
	 */
	private void cfgOwnStoreCategory(List<Integer> storeIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		batchUpdateStoreCategory(storeIdList, cardList);
	}

	/**
	 * 配置专享平台
	 */
	private void cfgOwnPlatformCategory(List<Integer> platformIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		batchUpdatePlatformCategory(platformIdList, cardList);
	}

	/**
	 * 配置专享品牌
	 */
	private void cfgOwnBrandId(List<Integer> brandIdList, Integer cardId) {
		List<Integer> cardList = new ArrayList<>(Arrays.asList(cardId));
		batchUpdateBrandId(brandIdList, cardList);
	}

	/**
	 * 根据会员卡id批量更新会员专享商品
	 */
	public void batchUpdateGoods(List<Integer> goodsIdList, List<Integer> cardIdList) {
		batchUpdateGoods(goodsIdList, cardIdList, COUPLE_TP_GOODS);
	}

	/**
	 * 根据会员卡id批量更新会员专享商品-商家分类
	 */
	public void batchUpdateStoreCategory(List<Integer> storeIdList, List<Integer> cardIdList) {
		batchUpdateGoods(storeIdList, cardIdList, COUPLE_TP_STORE);
	}

	/**
	 * 根据会员卡id批量更新会员专享商品-平台分类
	 */
	public void batchUpdatePlatformCategory(List<Integer> platformIdList, List<Integer> cardIdList) {
		batchUpdateGoods(platformIdList, cardIdList, COUPLE_TP_PLAT);
	}

	/**
	 * 根据会员卡id批量更新会员专享商品-品牌分类
	 */
	public void batchUpdateBrandId(List<Integer> brandIdList, List<Integer> cardIdList) {
		batchUpdateGoods(brandIdList, cardIdList, COUPLE_TP_BRAND);
	}

	/**
	 * 根据会员id以及标签关联类型，批量更新会员专享商品： 商品，平台分类，商家分类
	 * 
	 * @param goodsIdList
	 * @param cardIdList
	 * @param type
	 */
	public void batchUpdateGoods(List<Integer> goodsIdList, List<Integer> cardIdList, Byte type) {
		logger().info("根据会员卡id批量更新会员专享商品");
		this.transaction(() -> {

			/** 删除会员专享商品记录 */
			int deleteNum = deleteOwnEnjoyGoods(cardIdList, type);
			logger().info("成功删除：" + deleteNum);

			InsertValuesStep3<GoodsCardCoupleRecord, Integer, Integer, Byte> insert = db().insertInto(GOODS_CARD_COUPLE)
					.columns(GOODS_CARD_COUPLE.CARD_ID, GOODS_CARD_COUPLE.GCTA_ID, GOODS_CARD_COUPLE.TYPE);

			for (Integer cardId : cardIdList) {
				for (Integer goodsId : goodsIdList) {
					insert.values(cardId, goodsId, type);
				}
			}

			int execute = insert.execute();
			logger().info("成功更新会员专享商品： " + execute + "行");
		});
	}

	/**
	 * 根据商品或分类id获取相应的会员卡id
	 * 
	 * @author 李晓冰
	 * @param gctaId 商品或分类id
	 * @param type   类型
	 * @return 会员卡ids
	 */
	public List<Integer> selectOwnEnjoyCardByGcta(Integer gctaId, Byte type) {
		List<Integer> cardIds = db().select(GOODS_CARD_COUPLE.CARD_ID).from(GOODS_CARD_COUPLE)
				.where(GOODS_CARD_COUPLE.GCTA_ID.eq(gctaId)).and(GOODS_CARD_COUPLE.TYPE.eq(type))
				.fetchInto(Integer.class);

		return cardIds;
	}

	/**
	 * 删除商品会员卡的专属信息
	 * 
	 * @author 李晓冰
	 * @param gctaId 商品或分类id
	 * @param type   类型
	 */
	public void deleteOwnEnjoyGoodsByGcta(List<Integer> gctaId, Byte type) {
		db().deleteFrom(GOODS_CARD_COUPLE).where(GOODS_CARD_COUPLE.GCTA_ID.in(gctaId))
				.and(GOODS_CARD_COUPLE.TYPE.eq(type)).execute();
	}

	/**
	 * 根据会员卡id,标签关联类型进行删除
	 * 
	 * @param cardIdList
	 * @param type
	 * @return
	 */
	private int deleteOwnEnjoyGoods(List<Integer> cardIdList, Byte type) {
		return db().deleteFrom(GOODS_CARD_COUPLE).where(GOODS_CARD_COUPLE.CARD_ID.in(cardIdList))
				.and(GOODS_CARD_COUPLE.TYPE.eq(type)).execute();
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
	 * 初始化等级卡的基本配置
	 */
	private void initGradeBasicCfg(CardParam card, MemberCardRecordBuilder cardBuilder) {
		logger().info("初始化等级卡的升级条件，等级，是否启用等信息");
		cardBuilder.flag(card.getFlag() != null ? card.getFlag() : MCARD_FLAG_USING).expireType(MCARD_ET_FOREVER)
				.gradeCondition(Util.toJson(card.getGradeConditionJson())).grade(card.getGrade());
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

	private boolean isExchangNonGoods(Byte isExchange) {
		return MCARD_ISE_NON.equals(isExchange);
	}

	private boolean isExchangPartGoods(Byte isExchange) {
		return MCARD_ISE_PART.equals(isExchange);
	}

	private boolean isExchangAllGoods(Byte isExchange) {
		return MCARD_ISE_ALL.equals(isExchange);
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

	/**
	 * 查询等级会员卡,按照等级降序
	 */
	public PageResult<RankCardVo> getRankCardList(SearchCardParam param) {
		logger().info("正在分页查询等级会员卡");
		PageResult<MemberCardRecord> pageResult = cardDao.selectCardList(param);
		PageResult<RankCardVo> res = new PageResult<>();
		List<RankCardVo> dataList = new ArrayList<>();
		/** 执行转换 */
		for(MemberCardRecord card: pageResult.dataList) {
			RankCardVo vo = card.into(RankCardVo.class);
			vo.changeJsonCfg();
			// 会员卡使用数据
			vo.setCardUseStats(getCardUseStatistic(card));
			dataList.add(vo);
		}
		res.setDataList(dataList);
		return res;
	}

	/**
	 * 分页查询限次会员卡
	 */
	public PageResult<LimitNumCardVo> getLimitCardList(SearchCardParam param) {

		logger().info("正在分页查询限次会员卡");
		PageResult<MemberCardRecord> pageResult = cardDao.selectCardList(param);
		PageResult<LimitNumCardVo> res = new PageResult<LimitNumCardVo>();
		res.setPage(pageResult.getPage());
		List<LimitNumCardVo> dataList = new ArrayList<>();
		// 查询领取次数
		Map<Integer, Integer> intoMap = db().select(USER_CARD.CARD_ID, count()).from(USER_CARD)
				.groupBy(USER_CARD.CARD_ID).fetch().intoMap(USER_CARD.CARD_ID, count());
		
		for (MemberCardRecord card : pageResult.dataList) {
			LimitNumCardVo vo = card.into(LimitNumCardVo.class);
			// 设置未领取值
			vo.setHasSend(intoMap.get(vo.getId()) == null ? 0 : intoMap.get(vo.getId()));
			vo.changeJsonCfg();
			// 会员卡使用数据
			vo.setCardUseStats(getCardUseStatistic(card));
			dataList.add(vo);
		}
		res.setDataList(dataList);
		return res;
	}

	/**
	 * 分页查询普通会员卡
	 *
	 * @param param
	 */
	public PageResult<NormalCardVo> getNormalCardList(SearchCardParam param) {
		logger().info("正在分页查询普通会员卡");
		PageResult<MemberCardRecord> pageResult = cardDao.selectCardList(param);
		PageResult<NormalCardVo> res = new PageResult<NormalCardVo>();
		res.setPage(pageResult.getPage());
		List<NormalCardVo> dataList = new ArrayList<>();
		/** 将json配置文件转化成合适的数据给前端 */
		for (MemberCardRecord rec : pageResult.dataList) {
			NormalCardVo vo = rec.into(NormalCardVo.class);
			vo.changeJsonCfg();
			if(rec.getSendMoney()!= null && !StringUtils.isBlank(rec.getChargeMoney())) {
				// 是否展示充值明细
				Integer count = db().selectCount().from(CHARGE_MONEY).fetchOne(0, int.class);
				if(count != 0) {
					vo.setShowCharge(NumberUtils.BYTE_ONE);
				}
			}
			
			// TODO 退款记录
			
			// TODO 续费记录
			
			// 会员卡使用统计数据
			vo.setCardUseStats(getCardUseStatistic(rec));
			dataList.add(vo);
		}
		res.setDataList(dataList);
		return res;
	}

	/**
	 * 获取会员卡列表
	 *
	 * @param param
	 * @return
	 */
	public PageResult<? extends BaseCardVo> getCardList(SearchCardParam param) {

		Byte cardType = param.getCardType();
		PageResult<? extends BaseCardVo> result = null;
		if (CardUtil.isNormalCard(cardType)) {
			result = getNormalCardList(param);
		} else if (CardUtil.isLimitCard(cardType)) {
			result = getLimitCardList(param);
		} else if (CardUtil.isGradeCard(cardType)) {
			result = getRankCardList(param);
		}

		if (result != null) {
			String avatar = saas().shop.getShopAvatarById(this.getShopId());
			result.dataList.stream().forEach(item -> {
				// 获取头像
				item.setAvatar(avatar);
				// 背景图片全路径
				if(!StringUtils.isBlank(item.getBgImg())) {
					item.setBgImg(domainConfig.imageUrl(item.getBgImg()));
				}
			});
		}
		return result;
	}

	/**
	 * 设置会员卡启动或禁止状态
	 *
	 * @param param
	 */
	public void powerCard(PowerCardParam param) {
		/**
		 * UPDATE b2c_member_card set flag = 51 where id=825;
		 */
		/** SQL语句执行 */
		int result = db().update(MEMBER_CARD).set(MEMBER_CARD.FLAG, param.getFlag())
				.where(MEMBER_CARD.ID.eq(param.getId())).execute();
		logger().info("设置会员卡状态成功，受影响行： " + result);
	}

	/**
	 * 删除会员卡
	 *
	 * @param
	 */
	public void deleteCard(@Valid CardIdParam param) {
		/**
		 * update `b2c_member_card` set `is_delete` = '1' where `id` = '819'
		 */
		/** 假删除会员卡 */
		int result = db().update(MEMBER_CARD).set(MEMBER_CARD.DEL_FLAG, MCARD_DF_YES)
				.where(MEMBER_CARD.ID.eq(param.getId())).execute();
		logger().info("删除会员卡成功，受影响行： " + result);
	}

	/**
	 * 获取会员卡基本信息
	 */
	public MemberCardRecord getCardById(Integer cardId) {
		logger().info("获取会员卡基本信息");
		MemberCardRecord card = cardDao.getCardById(cardId);
		return card;
	}
	
	
	

	/**
	 * 根据ID获取该会员卡的详细信息
	 */
	public BaseCardVo getCardDetailById(CardIdParam param) {
		MemberCardRecord card = getCardById(param.getId());

		if (CardUtil.isNormalCard(card.getCardType())) {
			return changeToNormalCardDetail(card);
		} else if (CardUtil.isLimitCard(card.getCardType())) {
			return changeToLimitCardDetail(card);
		} else if (CardUtil.isGradeCard(card.getCardType())) {
			return changeToGradeCardDetail(card);
		}
		return null;
	}

	/**
	 * 装配会员卡批次信息
	 * 
	 * @param card
	 */
	private void assignCardBatch(BaseCardVo card) {
		if (CardUtil.isNormalCard(card.getCardType()) || 
				CardUtil.isLimitCard(card.getCardType())) {
			logger().info("正在获取批次信息");
			List<CardBatchRecord> batchList = cardReceiveCode.getAvailableCardBatchByCardId(card.getId());
			List<CardBatchVo> res = new ArrayList<>();
			boolean pwdBatch = CardUtil.isReceiveByPwd(card.getReceiveAction());
			for (CardBatchRecord b : batchList) {
				res.add(CardBatchVoBuilder.create()
							.id(b.getId())
							.name(b.getName())
							.pwdBatch(pwdBatch)
							.action(b.getAction())
							.build());
			}
			card.setBatchList(res);
		}
	}

	/**
	 * 装配专享商品
	 */
	private void assignPayOwnGoods(BaseCardVo card) {
		if (CardUtil.isNormalCard(card.getCardType()) ||
				CardUtil.isGradeCard(card.getCardType())) {
			logger().info("正在获取专享商品");
			card.setOwnGoodsId(getOwnGoodsId(card.getId()));
			card.setOwnStoreCategoryIds(getOwnStoreCategory(card.getId()));
			card.setOwnPlatFormCategoryIds(getOwnPlatformCategory(card.getId()));
			card.setOwnBrandId(getOwnBrandId(card.getId()));
		}
	}

	/**
	 * 获取专享商品id
	 */
	public List<Integer> getOwnGoodsId(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnGoods(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	/**
	 * 获取专享平家id
	 */
	public List<Integer> getOwnStoreCategory(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnStoreCategory(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	/**
	 * 获取专享平台id
	 */
	public List<Integer> getOwnPlatformCategory(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnPlatformCategory(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	/**
	 * 获取专享品牌id
	 */
	public List<Integer> getOwnBrandId(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnBrandId(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	private NormalCardToVo changeToNormalCardDetail(MemberCardRecord card) {
		logger().info("正在获取普通会员卡");
		NormalCardToVo normalCard = card.into(NormalCardToVo.class);
		assignPayOwnGoods(normalCard);
		assignCardBatch(normalCard);
		changeCardJsonCfgToDetailType(normalCard);
		assignCoupon(normalCard);
		// 包邮信息
		normalCard.setFreeship(getFreeshipData(card));
		// 续费信息
		normalCard.setCardRenew(getRenewData(card));
		// 自定义权益信息
		normalCard.setCardCustomRights(getCustomRights(card));
		return normalCard;
	}
	




	/**
	 * 	获取卡的包邮信息
	 */
	private CardFreeship getFreeshipData(MemberCardRecord card) {
		return freeShipSvc.getFreeshipData(card,null);
	}
	
	/**
	 * 获取卡的续费信息
	 */
	private CardRenew getRenewData(MemberCardRecord card) {
		DateType dateType = null;
		if(card.getRenewDateType() != null) {
			dateType = CardRenew.DateType.values()[card.getRenewDateType()];
		}
		return CardRenew.builder()
				 .renewMemberCard(card.getRenewMemberCard())
				 .renewType(card.getRenewType())
				 .renewNum(card.getRenewNum())
				 .renewTime(card.getRenewTime())
				 .renewDateType(dateType)
				 .build();
	}
	
	/**
	 * 	获取卡的自定义权益信息
	 */
	public CardCustomRights getCustomRights(MemberCardRecord card) {
		logger().info("获取卡的自定义权益信息");
		List<CardRight> customRightsAll = null;
		CardCustomRights.RightSwitch flag = CardCustomRights.RightSwitch.off;
		
		if(!StringUtils.isBlank(card.getCustomRights())) {
			customRightsAll = Util.json2Object(card.getCustomRights(), new TypeReference<List<CardRight>>() {
	        }, false);
		}
		
		if(null != card.getCustomRightsFlag() ) {
			flag = CardCustomRights.RightSwitch.values()[card.getCustomRightsFlag()];
		}
		// 处理图片路径
		if(customRightsAll != null) {
			for(CardRight right: customRightsAll) {
				String imgUrl = right.getCrightImage();
				if(!StringUtils.isBlank(imgUrl)) {
					imgUrl = domainConfig.imageUrl(imgUrl);
				}
				right.setCrightImage(imgUrl);
			}
		}
		
		return CardCustomRights.builder()
					.customRightsAll(customRightsAll)
					.customRightsFlag(flag)
					.build();
	}

	private void changeCardJsonCfgToDetailType(BaseCardVo card) {
			card.changeJsonCfg();
			assignCardStoreIdAndName(card);
	}
	
	private void assignCardStoreIdAndName(BaseCardVo card) {
		List<StoreBasicVo> allStore = storeService.getAllStore();
		
		if(allStore!=null) {
			for(Iterator<StoreBasicVo> it = allStore.iterator();it.hasNext();) {
				StoreBasicVo vo = it.next();
				if(!card.getStoreIdList().contains(vo.getStoreId())) {
					it.remove();
				}
			}
			System.out.println(allStore.size());
			card.setStoreDataList(allStore);
		}
	}
	
	private void assignCoupon(NormalCardToVo card) {
		logger().info("处理优惠券信息");
		List<CouponGivePopVo> couponList = couponGiveService.popWindows(new CouponGivePopParam());
		List<Integer> couponIds = card.getCouponIds();
		if (isListAvailable(couponList) && isListAvailable(couponIds)) {
			List<CouponGivePopVo> res = couponList.stream().filter(coupon -> couponIds.contains(coupon.getId()))
					.collect(Collectors.toList());
			card.setCouponList(res);
		}
	}

	private <T> boolean isListAvailable(List<T> list) {
		return list != null && list.size() > 0;
	}

	private LimitNumCardToVo changeToLimitCardDetail(MemberCardRecord card) {
		logger().info("正在获取限次会员卡");
		LimitNumCardToVo limitCard = card.into(LimitNumCardToVo.class);
		assignCardBatch(limitCard);
		int numOfSendCard = getNumSendCardById(limitCard.getId());
		limitCard.setHasSend(numOfSendCard);
		changeCardJsonCfgToDetailType(limitCard);
		return limitCard;
	}

	public RankCardToVo changeToGradeCardDetail(MemberCardRecord card) {
		logger().info("获取等级会员卡");
		RankCardToVo gradeCard = card.into(RankCardToVo.class);
		assignPayOwnGoods(gradeCard);
		gradeCard.changeJsonCfg();
		return gradeCard;
	}

	/**
	 * 获取已经发放的卡的数量
	 */
	public int getNumSendCardById(Integer cardId) {
		return userCardService.getNumCardsWithSameId(cardId);
	}

	/**
	 * 根据会员卡ID列表取会员卡信息列表
	 */
	public List<SimpleMemberCardVo> getMemberCardByCardIds(List<Integer> cardIds) {
		return db().select(MEMBER_CARD.ID, MEMBER_CARD.CARD_NAME,MEMBER_CARD.CARD_TYPE).from(MEMBER_CARD)
				.where(MEMBER_CARD.ID.in(cardIds))
				.and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchInto(SimpleMemberCardVo.class);
	}

	/**
	 * 获取可用的所有会员卡弹窗 返回的对象信息为会员卡的id与名称
	 */
	public List<CardBasicVo> getAllUserCard() {
		// 等级降序
		logger().info("正在获取所有的可用会员卡，按等级排序");
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		List<CardBasicVo> cardList = db().select(MEMBER_CARD.ID, MEMBER_CARD.CARD_NAME).from(MEMBER_CARD)
				.where(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING)).and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX).and(MEMBER_CARD.END_TIME.ge(localDateTime)))
						.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING, MCARD_ET_FOREVER)))
				.orderBy(MEMBER_CARD.CARD_TYPE.asc(), MEMBER_CARD.GRADE.asc(), MEMBER_CARD.ID.asc()).fetchInto(CardBasicVo.class);

		return cardList;
	}

	/**
	 * 获取专属会员卡
	 * 
	 * @return List<CardBasicVo>
	 */
	public List<CardBasicVo> getCardExclusive() {
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		List<CardBasicVo> cardList = db().select(MEMBER_CARD.ID, MEMBER_CARD.CARD_NAME).from(MEMBER_CARD)
				.where(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING))
				.and((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX).and(MEMBER_CARD.END_TIME.ge(localDateTime)))
						.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING, MCARD_ET_FOREVER)))
				.and(MEMBER_CARD.PAY_OWN_GOOD.eq(PAY_OWN_GOOD_YES))
				.and(MEMBER_CARD.CARD_TYPE.in(MCARD_TP_NORMAL, MCARD_TP_GRADE))
				.orderBy(MEMBER_CARD.GRADE.asc(), MEMBER_CARD.ID.asc()).fetch().into(CardBasicVo.class);

		return cardList;
	}
	/**
	 * 获取可用会员卡列表
	 */
	public List<UserAction> getUsableMemberCardList() {
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		return db().select(MEMBER_CARD.ID, MEMBER_CARD.CARD_NAME.as("name")).from(MEMBER_CARD)
				.where(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING))
				.and((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX).and(MEMBER_CARD.END_TIME.ge(localDateTime)))
						.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING, MCARD_ET_FOREVER)))
				.fetchInto(UserAction.class);
	}
	/**
	 * 会员卡列表
	 *
	 * @return
	 */
	public MemberCardVo getAllCardList(CardParam param) {
		Result<MemberCardRecord> cardRecords = selectAllMemberCard(param);
		MemberCardVo vo = new MemberCardVo();
		logger().info("正在分类处理");
		for (MemberCardRecord card : cardRecords) {
			Byte cardType = card.getCardType();
			List<String> legal = new ArrayList<>();
			List<String> exchangCountLegal = new ArrayList<>();
			Integer legalFlag = 0;
			legalFlag = dealWithCardRights(card, cardType, legal, exchangCountLegal, legalFlag);
			MemberCard cardVo = card.into(MemberCard.class);
			/** 执行策略 */
			cardVo.changeJsonCfg();
			cardVo.setLegal(legal);
			cardVo.setExchangCountLegal(exchangCountLegal);
			cardVo.setLegalFlag(legalFlag);
			if(!StringUtils.isBlank(cardVo.getBgImg())) {
				String imageUrl = saas.getShopApp(getShopId()).image.imageUrl(cardVo.getBgImg());
				cardVo.setBgImg(imageUrl);
			}
			if (MCARD_TP_NORMAL.equals(cardType)) {
				vo.getNormalCard().add(cardVo);
			} else if (MCARD_TP_LIMIT.equals(cardType)) {
				vo.getLimitNumCard().add(cardVo);
			} else if (MCARD_TP_GRADE.equals(cardType)) {
				vo.getRankCard().add(cardVo);
			}
		}
		return vo;
	}
	
	/**
	 * 	处理会员卡的拥有的权益
	 * @return
	 */
	private Integer dealWithCardRights(MemberCardRecord card, Byte cardType, List<String> legal,
			List<String> exchangCountLegal, Integer legalFlag) {
		final Byte two = Byte.valueOf("2");
		if(CardUtil.isNormalCard(cardType) || CardUtil.isGradeCard(cardType)) {
			if(isNotNull(card.getDiscount())) {
				logger().info("积分打折");
				legal.add(""+card.getDiscount());
				legalFlag = 1;
			}else if(isNotNull(card.getSorce())) {
				logger().info("开卡赠送积分");
				legal.add(""+card.getSorce());
				legalFlag = 2;
			}else if(isNotBlank(card.getBuyScore())) {
				ScoreJson buyScore = Util.parseJson(card.getBuyScore(), ScoreJson.class);
				Byte offset = buyScore.getOffset();
				if(!Byte.valueOf((byte)-1).equals(offset)) {
					if(Byte.valueOf((byte)0).equals(offset) && buyScore.getGoodsMoney().size()>0) {
						logger().info("购买满多少赠送多少积分");
						legal.add(""+buyScore.getGoodsMoney().get(0));
						legal.add(" "+buyScore.getGetScores().get(0));
						legalFlag = 3;
					}else {
						logger().info("购买每满多少赠送多少积分");
						legal.add(""+buyScore.getPerGoodsMoney());
						legal.add(""+buyScore.getPerGetScores());
						legalFlag = 4;
					}
				}
			}else if(isNotNull(card.getSendMoney())){
				logger().info("开卡赠送多少");
				legal.add(""+ card.getSendMoney());
				legalFlag = 5;
			}else if(isNotBlank(card.getChargeMoney())) {
				PowerCardJson powerCardJson = Util.parseJson(card.getChargeMoney(), PowerCardJson.class);
				Byte offsetMoney = powerCardJson.getOffsetMoney();
				if(Byte.valueOf((byte)0).equals(offsetMoney) && powerCardJson.getMoney().length>0) {
					logger().info("充值满多少赠送多少");
					legal.add(""+powerCardJson.getMoney()[0]);
					legal.add(""+powerCardJson.getGetMoney()[0]);
					legalFlag = 6;
				}else if(Byte.valueOf((byte)1).equals(offsetMoney)){
					logger().info("充值每满多少赠多少");
					legal.add(""+powerCardJson.getPerMoney());
					legal.add(""+powerCardJson.getPerGetMoney());
					legalFlag = 7;
				}else if(two.equals(offsetMoney)) {
					logger().info("仅充值");
					legalFlag = 8;
				}
			}
		}else if(CardUtil.isLimitCard(cardType)) {
			if(isNotNull(card.getCount())) {
				logger().info("开卡赠送门店服务机会");
				legal.add(""+card.getCount());
				legalFlag = 9;
			}else {
				logger().info("开卡赠送兑换商品机会");
				exchangCountLegal.add(""+card.getExchangCount());
				legalFlag = 10;
			}
		}
		return legalFlag;
	}

	private boolean isNotBlank(String buyScore) {
		return !StringUtils.isBlank(buyScore);
	}

	/**
	 * 查询所有的会员卡
	 */
	private Result<MemberCardRecord> selectAllMemberCard(CardParam param) {
		logger().info("查询所有会员卡");
		Result<MemberCardRecord> cardRecords = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.DEL_FLAG.equal(MCARD_DF_NO))
				.and(getCondition(param))
				.fetch();
		return cardRecords;
	}

	private Condition getCondition(CardParam param) {
		Condition condition = DSL.noCondition();

		//	有效时间
		
		condition = condition.and(MEMBER_CARD.EXPIRE_TYPE.eq(CardConstant.MCARD_ET_DURING)
				.or(MEMBER_CARD.EXPIRE_TYPE.eq(CardConstant.MCARD_ET_FOREVER))
				.or(MEMBER_CARD.EXPIRE_TYPE.eq(CardConstant.MCARD_ET_FIX)
						.and(MEMBER_CARD.END_TIME.gt(DateUtil.getLocalDateTime()))));
		
		condition = condition.and(MEMBER_CARD.DEL_FLAG.equal(MCARD_DF_NO));
		condition = condition.and(MEMBER_CARD.FLAG.eq(CardConstant.MCARD_FLAG_USING));
		
		if(null != param.getCardType()) {
			condition = condition.and(MEMBER_CARD.CARD_TYPE.eq(param.getCardType()));
		}
		if(isNotBlank(param.getCardName())) {
			condition = condition.and(MEMBER_CARD.CARD_NAME.like(likeValue(param.getCardName())));
		}
		return condition;
	}

	/**
	 * 为会员分配会员卡
	 */
	public void addCardForMember(AddMemberCardParam param) {

		/** 准备数据 */
		List<Integer> cardIdList = param.getCardIdList();
		List<Integer> userIdList = param.getUserIdList();
		
		for(Integer cardId: cardIdList) {
			MemberCardRecord card = this.getCardById(cardId);
			if(card != null) {
				CardOpt cardOpt = getCardOpt(card.getCardType());
				for(Integer userId: userIdList) {
					cardOpt.handleSendCard(userId, cardId, true);
				}
			}else {
				logger().info("该卡: "+cardId+" 不存在");
			}
		}
	}
	
	public CardOpt getCardOpt(Byte type) {
		if(CardUtil.isNormalCard(type)) {
			return normalCardOpt;
		}else if(CardUtil.isLimitCard(type)) {
			return limitCardOpt;
		}else {
			return gradeCardOpt;
		}
	}

	/**
	 * 生成会员卡号
	 */
	public String generateCardNo(int cardId) {
		StringBuilder cardNo = new StringBuilder();

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			/** 会员卡号 = 店铺id+两位随机数+四位会员卡id+四位随机数 */
			cardNo.append(getShopId());
			cardNo.append(Util.randomInteger(10, 100));
			cardNo.append(String.format("%04d", cardId), 0, 4);
			cardNo.append(Util.randomInteger(1000, 10000));

			/** 确保数据库会员卡号具有唯一性 */
			int count = db().fetchCount(USER_CARD, USER_CARD.CARD_NO.eq(cardNo.toString()));
			if (count == 0) {
				break;
			}
			/** clear string buffer */
			cardNo.setLength(0);
		}

		logger().info("cardNo: " + cardNo.toString());
		return cardNo.toString();
	}

	/**
	 * 更新用户会员卡余额
	 * 
	 * @param data 用户卡相关数据
	 * @throws MpException
	 */
	public void updateMemberCardAccount(CardConsumpData data, TradeOptParam tradeOpt)
			throws MpException {

		/** 1.-获取数据库中的存储的信息 */
		UserCardRecord userCard = getUserCardInfoByCardNo(data.getCardNo());
		if(userCard == null) {
			return;
		}
		data.setCardId(userCard.getCardId());
		/** 2-判断会员卡余额是属于充值还是消费 */
		if (isConsump(data)) {
			/** 2.1-如果消费余额超出用户会员卡现有余额，则抛出异常 */
			if ((data.getMoney().add(userCard.getMoney())).compareTo(BigDecimal.ZERO) == -1) {
				throw new MpException(JsonResultCode.CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
			}
			/** -消费会员卡余额 */
			consumpUserCard(data, RemarkTemplate.ADMIN_CARD_ACCOUNT.code);
		} else {
			/** 2.2 充值会员卡余额 */
			chargeUserCard(data, RemarkTemplate.ADMIN_CARD_ACCOUNT.code);
		}
		/** 3-更新user_card用户会员卡的余额 */
		updateUserCard(data, userCard, RemarkTemplate.ADMIN_CARD_ACCOUNT.code);

		insertCardAccountTradesRecord(data, tradeOpt);
		// TODO模板消息

	}

	private boolean isConsump(CardConsumpData data) {
		if (data.getMoney() == null) {
			return false;
		}
		return BigDecimal.ZERO.compareTo(data.getMoney()) > 0;
	}

	/**
	 * 更新用户卡且为限次会员卡的消费次数（门店）
	 * 
	 * @param data 用户卡相关数据
	 * @throws MpException
	 */
	public void updateMemberCardSurplus(CardConsumpData data, TradeOptParam tradeOpt, String language)
			throws MpException {
		/** 1-判断是不是限次会员卡则结束 */
		if (!data.getType().equals(MCARD_TP_LIMIT)) {
			throw new MpException(JsonResultCode.CODE_PARAM_ERROR);
		}

		/** 2.-获取数据库中的存储的信息 */
		UserCardRecord userCard = getUserCardInfoByCardNo(data.getCardNo());

		/** 3-判断是使用还是增加用户卡的卡剩余次数 */
		if (data.getCount() < 0) {
			/** 3.1-减少（使用）卡剩余次数 */
			/** -检查卡剩余次数是否够用 */
			if ((data.getCount() + userCard.getSurplus()) < 0) {
				throw new MpException(JsonResultCode.CODE_MEMBER_CARD_SURPLUS_UPDATE_FAIL);
			}
			/** -消费会员卡剩余次数 */
			consumpUserCard(data, RemarkTemplate.ADMIN_STORE_SERIVICE.code);
		} else {
			/** 3.2-增加(充值)卡剩余次数 */
			chargeUserCard(data, RemarkTemplate.ADMIN_STORE_SERIVICE.code);
		}

		/** 4-更新user_card用户会员卡的消费次数 */
		updateUserCard(data, userCard, RemarkTemplate.ADMIN_STORE_SERIVICE.code);
		// TODO模板消息
		/**
		 * 5-记录交易明细
		 */
		insertCardAccountTradesRecord(data, tradeOpt);
	}

	/**
	 * 更新用户卡且为限次会员卡的卡剩余兑换次数
	 * 
	 * @param data 用户卡相关数据
	 * @throws MpException
	 */
	public void updateMemberCardExchangeSurplus(CardConsumpData data, TradeOptParam tradeOpt, String language)
			throws MpException {
		/** 1-判断是不是限次会员卡则结束 */
		if (!MCARD_TP_LIMIT.equals(data.getType())) {
			return;
		}

		/** 2.-获取数据库中的存储的信息 */
		UserCardRecord userCard = getUserCardInfoByCardNo(data.getCardNo());

		/** 3-判断是使用还是增加用户卡的卡剩余兑换次数 */
		if (data.getCount() < 0) {
			/** 3.1-减少（使用）卡剩余兑换次数 */
			/** -检查卡卡剩余兑换次数是否够用 */
			if ((data.getExchangeCount() + userCard.getExchangSurplus()) < 0) {
				throw new MpException(JsonResultCode.CODE_MEMBER_CARD_EXCHANGSURPLUS_UPDATE_FAIL);
			}
			/** -消费会员卡剩余次数 */
			consumpUserCard(data, RemarkTemplate.ADMIN_EXCHANGE_GOODS.code);
		} else {
			/** 3.2-增加(充值)卡剩余次数 */
			chargeUserCard(data, RemarkTemplate.ADMIN_EXCHANGE_GOODS.code);
		}

		/** 4-更新user_card用户会员卡的卡剩余兑换次数 */
		updateUserCard(data, userCard, RemarkTemplate.ADMIN_EXCHANGE_GOODS.code);
		// TODO模板消息
		/**
		 * 5-记录交易明细
		 */
		insertCardAccountTradesRecord(data, tradeOpt);
	}

	/**
	 * 充值用户卡
	 * 
	 * @param data
	 */
	private void chargeUserCard(CardConsumpData data, Integer code) {
		setDefaultReason(data, code);
		insertIntoChargeMoney(data);
	}

	/**
	 * 	消费用户卡
	 * 
	 * @throws MpException
	 */
	private void consumpUserCard(CardConsumpData data, Integer code) {

		setDefaultReason(data, code);
		insertIntoCardConsumer(data);
	}

	/**
	 * 设置默认的充值 | 消费原因
	 * 
	 */
	private void setDefaultReason(CardConsumpData data, Integer code) {
		/** 1-若reason原因为空 则设置为默认值 */
		if (data.getReason()==null) {
			data.setReasonId(code);
		}
	}

	/**
	 * 更新user_card表
	 * 
	 * @param data
	 * @param userCard
	 */
	private void updateUserCard(CardConsumpData data, UserCardRecord userCard, Integer code) {
		/** 更新用户卡余额 */
		if (RemarkTemplate.ADMIN_CARD_ACCOUNT.code.equals(code)) {
			BigDecimal money = userCard.getMoney().add(data.getMoney());
			db().update(USER_CARD).set(USER_CARD.MONEY, money).where(USER_CARD.CARD_NO.eq(userCard.getCardNo()))
					.execute();
		} else if (RemarkTemplate.ADMIN_STORE_SERIVICE.code.equals(code)) {
			/** 更新用户卡消费次数 */
			Integer surplus = userCard.getSurplus() + data.getCount();
			db().update(USER_CARD).set(USER_CARD.SURPLUS, surplus).where(USER_CARD.CARD_NO.eq(userCard.getCardNo()))
					.execute();
		} else if (RemarkTemplate.ADMIN_EXCHANGE_GOODS.code.equals(code)) {
			/** 更新用户卡兑换次数 */
			Integer exchangeSurplus = userCard.getExchangSurplus() + data.getExchangeCount();
			db().update(USER_CARD).set(USER_CARD.EXCHANG_SURPLUS, exchangeSurplus)
					.where(USER_CARD.CARD_NO.eq(userCard.getCardNo())).execute();
		}
	}

	/**
	 * 记录会员余额交易明细
	 */
	private void insertCardAccountTradesRecord(CardConsumpData data, TradeOptParam param) {

		logger().info("记录用户会员卡余额交易记录");
		TradeOptParam tradeOpt = TradeOptParam.builder().userId(data.getUserId()).tradeNum(data.getMoney().abs())
				.tradeSn(data.getOrderSn()).tradeContent(TRADE_CONTENT_CASH.val()).tradeType(param.getTradeType())
				.tradeFlow(param.getTradeFlow()).tradeStatus(getTradeStatus(param.getTradeFlow())).build();

		tradeService.insertTradeRecord(tradeOpt);
	}

	private Byte getTradeStatus(Byte tradeFlow) {
		return isTradeFlowToBeConfirm(tradeFlow) ? TRADE_FLOW_IN.val() : tradeFlow;
	}

	private boolean isTradeFlowToBeConfirm(Byte tradeFlow) {
		return TRADE_FLOW_TO_BE_CONFIRMED.val().equals(tradeFlow);
	}

	/**
	 * 会员卡充值记录添加到charge_money
	 * 
	 * @param data
	 */
	private void insertIntoChargeMoney(CardConsumpData data) {
		ChargeMoneyRecord chargeMoneyRecord = db().newRecord(CHARGE_MONEY,data);
		/** 处理数据库表中带下划线的字段 */
		if (data.getUserId() != null) {
			chargeMoneyRecord.setUserId(data.getUserId());
		}
		if (data.getCardId() != null) {
			chargeMoneyRecord.setCardId(data.getCardId());
		}
		/** 充值的钱 */
		if (data.getMoney() != null) {
			chargeMoneyRecord.setCharge(data.getMoney());
		}

		if (data.getPrepayId() != null) {
			chargeMoneyRecord.setPrepayId(data.getPrepayId());
		}
		if (data.getOrderSn() != null) {
			chargeMoneyRecord.setOrderSn(data.getOrderSn());
		}
		if (data.getOrderStatus() != null) {
			chargeMoneyRecord.setOrderStatus(data.getOrderStatus());
		}
		if (data.getMoneyPaid() != null) {
			chargeMoneyRecord.setMoneyPaid(data.getMoneyPaid());
		}
		if (data.getChargeType() != null) {
			chargeMoneyRecord.setChargeType(data.getChargeType());
		}
		if (data.getCardNo() != null) {
			chargeMoneyRecord.setCardNo(data.getCardNo());
		}
		if (data.getAliTradeNo() != null) {
			chargeMoneyRecord.setAliTradeNo(data.getAliTradeNo());
		}
		if (data.getExchangeCount() != null) {
			chargeMoneyRecord.setExchangCount(data.getExchangeCount());
		}
		chargeMoneyRecord.insert();

	}

	/**
	 * 会员卡消费记录添加到card_consumer
	 * 
	 * @param data
	 */
	private void insertIntoCardConsumer(CardConsumpData data) {
		CardConsumerRecord cardConsumerRecord = db().newRecord(CARD_CONSUMER,data);
		/** 处理数据库表中带下划线的字段 */
		if (data.getUserId() != null) {
			cardConsumerRecord.setUserId(data.getUserId());
		}
		if (data.getCardId() != null) {
			cardConsumerRecord.setCardId(data.getCardId());
		}
		if (data.getCardNo() != null) {
			cardConsumerRecord.setCardNo(data.getCardNo());
		}
		if (data.getExchangeCount() != null) {
			cardConsumerRecord.setExchangCount(data.getExchangeCount());
		}
		if (data.getOrderSn() != null) {
			cardConsumerRecord.setOrderSn(data.getOrderSn());
		}
		cardConsumerRecord.insert();
	}

	/**
	 * 通过会员卡号获取用户持有的会员卡信息
	 * 
	 * @param cardNo 会员卡号
	 */
	public UserCardRecord getUserCardInfoByCardNo(String cardNo) {
//		UserCardRecord userCard = db()
//				.select(USER_CARD.USER_ID, USER_CARD.CARD_ID, USER_CARD.CREATE_TIME, USER_CARD.FLAG, USER_CARD.CARD_NO,
//						USER_CARD.EXPIRE_TIME, USER_CARD.UPDATE_TIME, USER_CARD.IS_DEFAULT, USER_CARD.MONEY,
//						USER_CARD.SURPLUS, USER_CARD.ACTIVATION_TIME, USER_CARD.EXCHANG_SURPLUS)
//				.from(USER_CARD.join(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
//				.where(USER_CARD.CARD_NO.eq(cardNo)).fetchOne().into(UserCardRecord.class);
//		return userCard;
        return db().fetchAny(USER_CARD,USER_CARD.CARD_NO.eq(cardNo));
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public MemberCardPojo getMemberCardInfoById(int id) {
		return db().select().from(MEMBER_CARD).where(MEMBER_CARD.ID.eq(id))
				.and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchOne().into(MemberCardPojo.class);
	}

	/**
	 * - 获取所有的持卡会员
	 * 
	 * @param param
	 */
	public PageResult<CardHolderVo> getAllCardHolder(CardHolderParam param) {

		PageResult<CardHolderVo> allCardHolder = cardDao.getAllCardHolder(param);
		/** - 如果查询的状态是过期的，设置返回的flag为2过期 */
		for (CardHolderVo item : allCardHolder.dataList) {
			if (item.getExpireTime() != null &&
					DateUtil.getLocalDateTime().after(item.getExpireTime())) {
				item.setFlag(UCARD_FG_EXPIRED);
			}
		}
		return allCardHolder;
	}

	/**
	 * 分页查询会员卡领取详情
	 */
	public PageResult<CodeReceiveVo> getReceiveList(CodeReceiveParam param) {
		logger().info("处理遮掩码");
		PageResult<CodeReceiveVo> result = cardDao.getReceiveListSql(param);
		/** 处理code 和 card_pwd */
		for (CodeReceiveVo vo : result.dataList) {
			String code = vo.getCode();
			if(!StringUtils.isBlank(code)) {
				int lengthOfCode = code.length() - 4;
				if (lengthOfCode > 0) {
					String tmp = IntStream.range(0, lengthOfCode).mapToObj(i -> "*").collect(Collectors.joining());
					vo.setCode(code.substring(0, 2).concat(tmp).concat(code.substring(lengthOfCode + 2)));
				}
			}
			String cardPwd = vo.getCardPwd();
			if(!StringUtils.isBlank(cardPwd)) {
				int lengthOfCardPwd = cardPwd.length() - 4;
				if (lengthOfCardPwd > 0) {
					String tmp = IntStream.range(0, lengthOfCardPwd).mapToObj(i -> "*").collect(Collectors.joining());
					vo.setCardPwd(cardPwd.substring(0, 2).concat(tmp).concat(cardPwd.substring(lengthOfCardPwd + 2)));
				}
			}
		}
		return result;

	}

	/**
	 * 返回会员卡所有批次信息
	 */
	public List<CardBatchVo> getCardBatchList(Integer cardId) {
		List<CardBatchRecord> cardBatchList = cardReceiveCode.getAvailableCardBatchByCardId(cardId);
		if (cardBatchList != null) {
			List<CardBatchVo> res = new ArrayList<>();
			for (CardBatchRecord cb : cardBatchList) {
				CardBatchVo vo = new CardBatchVo();
				vo.setId(cb.getId());
				vo.setName(cb.getName());
				res.add(vo);
			}
			return res;
		} else {
			return null;
		}

	}

	/**
	 * 废除指定会员卡批次
	 * 
	 * @param id
	 */
	public void deleteCardBatch(Integer id) {

		cardDao.deleteCardBatchSql(id);
	}

	/**
	 * 分页查询会员卡充值明细
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<ChargeVo> getChargeList(ChargeParam param,String language) {
		return cardDao.getChargeList(param,language);
	}

	/**
	 * 分页查询会员卡消费明细
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<ChargeVo> getConsumeList(ChargeParam param,String language) {

		return cardDao.getConsumeList(param,language);
	}

	/**
	 * 分页查询激活审核信息
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<ActiveAuditVo> getActivateAuditList(ActiveAuditParam param) {

		PageResult<ActiveAuditVo> results = cardVerifyService.getPageList(param);
		// deal with industry and education
		for (ActiveAuditVo activeAuditVo : results.dataList) {
			// education
			String educationStr = MemberEducationEnum.getNameByCode((int)activeAuditVo.getEducation());
			activeAuditVo.setEducationStr(educationStr);
			// industry
			String industry = MemberIndustryEnum.getNameByCode((int)activeAuditVo.getIndustryInfo());
			activeAuditVo.setIndustry(industry);
		}
		return results;

	}

	/**
	 * 审核通过
	 * 
	 * @param param
	 * @return
	 */
	public void passActivateAudit(ActiveAuditParam param) {
		this.transaction(() -> {
			Timestamp now = DateUtil.getSqlTimestamp();
			logger().info("申请激活会员卡通过: " + now);
			// 更新card_examine 信息
			CardExamineRecord record = setPassData(param.getId(), now);
			cardDao.updateCardExamine(record);
			// 更新激活
			cardDao.updateUserCardByCardNo(param.getCardNo(), now);
		});
	}

	/**
	 * 审核通过数据
	 * 
	 * @param id
	 * @return
	 */
	private CardExamineRecord setPassData(Integer id, Timestamp now) {
		CardExamineRecord record = new CardExamineRecord();
		record.setId(id);
		record.setPassTime(now);
		record.setStatus(VERIFIED);
		return record;
	}

	/**
	 * 审核不通过数据
	 * 
	 * @param
	 * @return
	 */
	private CardExamineRecord setRejectData(ActiveAuditParam param) {
		CardExamineRecord record = new CardExamineRecord();
		record.setId(param.getId());
		record.setRefuseTime(DateUtil.getSqlTimestamp());
		record.setRefuseDesc(param.getRefuseDesc());
		record.setStatus(REFUSED);
		return record;
	}

	/**
	 * 审核不通过
	 * 
	 * @param param
	 */
	public void rejectActivateAudit(ActiveAuditParam param) {
		CardExamineRecord record = setRejectData(param);
		cardDao.updateCardExamine(record);
	}

	/**
	 * 会员卡订单分页查询
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<CardConsumeVo> getCardConsumeOrderList(CardConsumeParam param) {
		PageResult<CardConsumeVo> result = cardDao.getCardConsumeOrderList(param);
		for (CardConsumeVo vo : result.dataList) {
			// 门店服务
			if (!SHORT_ZERO.equals(vo.getCount())) {
				ServiceOrderDetailVo serviceOrder = getServiceOrderInfo(vo.getOrderSn());
				if (serviceOrder != null) {
					vo.setGoodsImg(serviceOrder.getServiceImg());
					vo.setGoodsName(serviceOrder.getServiceName());
				}
			}
			// 商品兑换服务
			if (!SHORT_ZERO.equals(vo.getExchangCount())) {
				OrderGoodsVo orderGoods = getOrderGoodsInfo(vo.getOrderSn());
				if (orderGoods != null) {
					vo.setGoodsImg(orderGoods.getGoodsImg());
					vo.setGoodsName(orderGoods.getGoodsName());
				}
			}
		}

		return result;
	}

	/**
	 * 获取一个订单商品信息
	 * 
	 * @param orderSn
	 * @return
	 */
	private OrderGoodsVo getOrderGoodsInfo(String orderSn) {
		List<OrderGoodsVo> results = orderGoodsDao.getGoodsInfoByOrderSn(orderSn).into(OrderGoodsVo.class);
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	/**
	 * 获取门店服务订单服务
	 * 
	 * @param orderSn
	 * @return
	 */
	private ServiceOrderDetailVo getServiceOrderInfo(String orderSn) {
		return serviceOrderDao.getServiceOrderDetail(orderSn);
	}
	
	public CardBatchVo generateCardCode(CardBatchParam param) {
		logger().info("正在添加添加领取码");
		System.out.println(param.getBatchId());
		this.transaction(() -> {
			// 插入并获取批次Id
			Integer batchId = cardDao.createCardBatch(param);
			param.setBatchId(batchId);

			Integer groupId = cardDao.generateGroupId(batchId);
			param.setGroupId(groupId);

			if (MCARD_RA_CODE.equals(param.getReceiveAction())) {
				// 获取领取码
				List<String> codeList = generateRandomCode(param);
				cardDao.insertIntoCardReceiveCode(param, codeList);
			} else if (MCARD_RA_PWD.equals(param.getReceiveAction())) {
				// 获取卡号
				List<String> cardNoList = generateRandCardNo(param);
				// 获取密码
				List<String> pwdList = generateRandCardPwd(param);
				cardDao.insertIntoCardReceiveCode(param, cardNoList, pwdList);
			}

		});
		CardBatchVo vo = new CardBatchVo();
		vo.setId(param.getBatchId());
		vo.setName(param.getBatchName());
		return vo;
	}

	/**
	 * 生成随机的领取码
	 * 
	 * @param param
	 * @return
	 */
	private List<String> generateRandomCode(CardBatchParam param) {
		Integer number = param.getNumber();
		List<String> codeList = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			while (true) {
				String code = generateRandomStr(param.getCodePrefix(), param.getCodeSize());
				if (!codeList.contains(code) && !cardDao.isExistCodeInBatch(code,param.getBatchIdStr())) {
					codeList.add(code);
					break;
				}
			}
		}
		return codeList;
	}

	/**
	 * 生成随机密码
	 * 
	 * @param param
	 * @return
	 */
	private List<String> generateRandCardPwd(CardBatchParam param) {
		List<String> pwdList = new ArrayList<>();
		Integer number = param.getNumber();
		for (int i = 0; i < number; i++) {
			while (true) {
				String pwd = generateRandomStr("", param.getCardPwdSize());
				if (!pwdList.contains(pwd)) {
					pwdList.add(pwd);
					break;
				}
			}
		}
		return pwdList;
	}

	/**
	 * 生成随机的卡号
	 * 
	 * @param param
	 * @return
	 */
	private List<String> generateRandCardNo(CardBatchParam param) {
		List<String> cardNoList = new ArrayList<>();
		Integer number = param.getNumber();
		for (int i = 0; i < number; i++) {
			while (true) {
				String cardNo = generateRandomStr(param.getCodePrefix(), param.getCodeSize());
				if (!cardNoList.contains(cardNo) && !cardDao.isExistCardNoInBatch(cardNo,param.getBatchIdStr())) {
					cardNoList.add(cardNo);
					break;
				}
			}
		}
		return cardNoList;
	}

	/**
	 * 生成随机的字符串
	 * 
	 * @param param
	 */
	private String generateRandomStr(String prefix, int length) {
		char[] arr = NUM_LETTERS.toCharArray();
		StringBuilder container = new StringBuilder(prefix);
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				char c = arr[Util.randomInteger(0, arr.length)];
				container.append(c);
			} else {
				char c = arr[Util.randomInteger(0, 9)];
				container.append(c);
			}
		}
		return container.toString();
	}

	/**
	 * 返回会员等级-按照持有会员等级卡划分，若无持有等级会员卡，则返回null
	 * 
	 * @param user_id
	 * @return
	 */
	public String getUserGrade(Integer userId) {
		return userCardService.getUserGrade(userId);
	}

	/**
	 * 获取会员卡id列表根据会员卡类型
	 * 
	 * @param type {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE }
	 */
	public List<Integer> getCardIdByType(Byte type) {
		return cardDao.getCardIdByType(type);
	}


	private boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	private boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * Gets single field by condition.获取购物送积分策略json数据
	 *
	 * @param cardNo the card no
	 * @return the single field by condition
	 */
	public String getSendScoreStrategy(String cardNo) {
		return db().select(MEMBER_CARD.BUY_SCORE).from(USER_CARD).leftJoin(MEMBER_CARD)
				.on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)).where(USER_CARD.CARD_NO.eq(cardNo))
				.fetchOneInto(String.class);
	}

	public List<CardBatchDetailVo> getBatchCfg(Integer batchId) {
		return cardDao.selectBatchCfgById(batchId);
	}


    /**
     * 	小程序装修会员卡模块显示异步调用
     * status : -1未领取， 1已领取， 2过期= ， 3停用,4已达到领取上限，5无库存
     * @param moduleCard
     * @param userId
     * @return
     */
	public MemberCardPageDecorationVo getPageIndexMemberCard(ModuleCard moduleCard, int userId){
        MemberCardPageDecorationVo vo = db().select().from(MEMBER_CARD).where(MEMBER_CARD.ID.eq(moduleCard.getCardId())).fetchSingle().into(MemberCardPageDecorationVo.class);
        vo.setCardId(vo.getId());
        if(vo.getFlag().equals(CardConstant.MCARD_FLAG_STOP)){
            //已停用
            vo.setStatus((byte)3);
        }else if(vo.getExpireType().equals(MCARD_ET_FIX) && DateUtil.getLocalDateTime().after(vo.getEndTime())){
            //已过期
            vo.setStatus((byte)2);
        }

        //用户已经领取该卡的数量
        int userHasGotNumber = userCardService.userCardDao.getNumHasSendUser(userId,moduleCard.getCardId());
        if(vo.getCardType().equals(MCARD_TP_LIMIT)){
            //限次卡，还有库存 或 不限库存
        	int hasSend = userCardService.userCardDao.calcNumCardById(moduleCard.getCardId());
        	boolean canSend = vo.getStock() > 0 && hasSend < vo.getStock();
            if(vo.getStock() == 0 || canSend){
                if(vo.getLimit() == null || vo.getLimit() == 0 || userHasGotNumber < vo.getLimit()){
                    //未达到领取上限
                    vo.setStatus((byte)-1);
                }else{
                    vo.setStatus((byte)4);
                }
            }else{
                //已经没有库存
                vo.setStatus((byte)5);
            }
        }else if(vo.getCardType().equals(MCARD_TP_GRADE)){
            //只要拥有一张等级卡，就认为是已领取
        	MemberCardRecord mCard = userCardService.userCardDao.getUserGradeCard(userId);
            if( mCard != null && moduleCard.getCardId() == mCard.getId()){
            	if(CardUtil.isStopUsing(mCard.getFlag())) {
            		vo.setStatus((byte)3);
            	}else {
            		vo.setStatus((byte)1);
            	}
                
            }else {
            	vo.setStatus((byte)-1);
            }
            logger().info("等级卡->status: "+vo.getStatus());
        }else{
            //普通卡 只能拥有一张
            if(userHasGotNumber > 0){
                vo.setStatus((byte)1);
            }else if(vo.getStatus() != 3 && vo.getStatus() != 2){
                //有效，可以领取
                vo.setStatus((byte)-1);
            }
        }
        logger().info("卡->status: "+vo.getStatus());
        //图片域名
        String shopAvatar = saas().shop.getShopAvatarById(getShopId());
        if(StringUtil.isNotEmpty(shopAvatar)){
            vo.setShipImg(domainConfig.imageUrl(shopAvatar));
        }else{
            vo.setShipImg(domainConfig.imageUrl("image/admin/shop_logo_default.png"));
        }
        if(StringUtil.isNotEmpty(vo.getBgImg())){
            vo.setBgImg(domainConfig.imageUrl(vo.getBgImg()));
        }

        vo.setHiddenCard(moduleCard.getHiddenCard());

        return vo;
    }

	public List<MemberCardRecord> getGradeCardList(String grade) {
		return db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING))
			.and(MEMBER_CARD.GRADE.greaterThan(grade))
			.and(MEMBER_CARD.DEL_FLAG.eq((byte)0))
			.orderBy(MEMBER_CARD.GRADE)
			.fetchInto(MemberCardRecord.class);
		
	}
	
	public List<MemberCardRecord> getGradeCardList() {
		return db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING))
			.and(MEMBER_CARD.DEL_FLAG.eq((byte)0))
			.orderBy(MEMBER_CARD.GRADE)
			.fetchInto(MemberCardRecord.class);
		
	}

	public void sendCoupon(Integer userId, Integer cardId) {
		logger().info("开卡赠送优惠券或优惠券礼包");
		MemberCardRecord mCard = getCardById(cardId);
		if(mCard==null) {
			return;
		}
		if(CardUtil.isOpenCardSendCoupon(mCard.getSendCouponSwitch())) {
			List<Integer> sendCouponList = CardUtil.parseCouponList(mCard.getSendCouponIds());
			if(CardUtil.isSendCoupon(mCard.getSendCouponType()) && sendCouponList.size()>0) {
				couponGiveService.sendVoucher(userId,sendCouponList,CouponSrcConstant.OPEN_CARD);
			}else if(NumberUtils.BYTE_ONE.equals(mCard.getSendCouponType()) || sendCouponList.size()>0){
				// TODO  
				logger().info("虚拟商品下单");
				// cardOrder.createCardOrder()
			}
		}
	}
	
	public List<String> getAllNoDeleteCardGrade(){
		return gradeCardService.getAllNoDeleteCardGrade();
	}
	
	/**
	 * 获取处理背景色与背景图片
	 * @param type 卡背景类型 
	 * @param bgColor 背景颜色
	 * @param bgImg 背景图片
	 * @return CardBgBean背景信息
	 */
	public  CardBgBean getBackground(Byte type,String bgColor,String bgImg) {
		CardBgBean bean = new CardBgBean();
		if(CardUtil.isBgImgType(type)) {
			// 背景图片
			if(!StringUtils.isBlank(bgImg)) {
				String imageUrl = imageService.imageUrl(bgImg);
				bean.setBgImg(imageUrl);
			}
		}
		// 背景色
		if(StringUtils.isBlank(bgColor)) {
			// 默认背景色
			bgColor = CardUtil.getDefaultBgColor();
		}
		bean.setBgColor(bgColor);
		return bean;
	}
	
	/**
	 * 持卡会员导出
	 * @param param
	 * @param lang
	 * @return
	 */
	public Workbook getAllCardHolderExport(CardHolderParam param,String lang) {
		List<CardHolderExcelVo> allCardHolderAll = cardDao.getAllCardHolderAll(param);
		String expire = Util.translateMessage(lang, JsonResultMessage.USER_CARD_ONOK, "excel","messages");
		String ok = Util.translateMessage(lang, JsonResultMessage.USER_CARD_OK, "excel","messages");
		String abolition = Util.translateMessage(lang, JsonResultMessage.USER_CARD_ABOLITION, "excel","messages");
		for (CardHolderExcelVo item : allCardHolderAll) {
			if (item.getExpireTime() != null &&
					DateUtil.getLocalDateTime().after(item.getExpireTime())) {
				String dateFormat = DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, item.getUpdateTime());
				item.setNflag(abolition+"("+dateFormat+")");
			}else {
				Byte flag = item.getFlag();
				if(Objects.equals(flag, (byte)0)) {
					item.setNflag(ok);
				}
				if(Objects.equals(flag, (byte)2)) {
					item.setNflag(expire);
				}
			}
		}
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(allCardHolderAll, CardHolderExcelVo.class);
		return workbook;
	}
	
	/**
	 * 领取码导入模板
	 * @param lang
	 * @return
	 */
	public Workbook getCardNoTemplate(String lang) {
		List<CardNoExcelVo> list = new ArrayList<CardNoExcelVo>();
		for (int i = 11; i < 21; i++) {
			CardNoExcelVo vo = new CardNoExcelVo();
			vo.setCode("C1111" + i);
			list.add(vo);
		}
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(list, CardNoExcelVo.class);
		return workbook;
	}
	/**
	 * 文件导入领取码
	 * @param lang
	 * @param param
	 * @return
	 */
	public CardInsertVo insertCardNo(String lang, CardBatchParam param) {
		MultipartFile multipartFile = param.getFile();
		ExcelTypeEnum type = ExcelUtil.checkFile(multipartFile);
		if (type == null) {
			// 文件类型不正确，请上传Excel文件
			return new CardInsertVo(JsonResultCode.CODE_EXCEL_ERRO,0);
		}
		Workbook workbook = null;
		try {
			InputStream inputStream = multipartFile.getInputStream();
			workbook = ExcelFactory.createWorkbook(inputStream, type);
		} catch (IOException e) {
			logger().info("excel读取错误");
			logger().info(e.getMessage(), e);
			return new CardInsertVo(JsonResultCode.CODE_EXCEL_READ_ERRO,0);
		}
		UserImExcelWrongHandler handler = new UserImExcelWrongHandler();
		ExcelReader excelReader = new ExcelReader(lang, workbook, handler);
		Byte receiveAction = param.getReceiveAction();
		if (Objects.equals(receiveAction, (byte) 1)) {
			logger().info("领取码");
			List<CardNoExcelVo> models = excelReader.readModelList(CardNoExcelVo.class);
			return importCardCode(models, param);
		}
		if (Objects.equals(receiveAction, (byte) 2)) {
			logger().info("卡号+密码");
			List<CardNoPwdExcelVo> models = excelReader.readModelList(CardNoPwdExcelVo.class);
			return importCardPwd(models, param);
		}
		logger().info("excelReader："+excelReader);
		return new CardInsertVo(JsonResultCode.CODE_FAIL,0);
	}
	
	/**
	 * 插入领取码
	 * @param list
	 * @param param
	 * @return
	 */
	public CardInsertVo importCardCode(List<CardNoExcelVo> list,CardBatchParam param) {
		int newNumber = list.size();
		if (newNumber > 10000) {
			// return 单个批次不能超过10000';
			return new CardInsertVo(JsonResultCode.CODE_EXCEL_NUM_MAX,0);
		}
		if (newNumber == 0) {
			// return 单个批次不能为0';
			return new CardInsertVo(JsonResultCode.CODE_EXCEL_NUM_MIN,0);
		}
		List<String> list2 = new ArrayList<String>();
		for (CardNoExcelVo cardNoExcelVo : list) {
			list2.add(cardNoExcelVo.getCode());
		}
		boolean isRepeat = list2.size() != new HashSet<String>(list2).size();
		if (isRepeat) {
			// return "存在重复的领取码，请检查！";
			return new CardInsertVo(JsonResultCode.CODE_EXCEL_HAVE_SAME,0);
		}
		CardBatchParam param2=new CardBatchParam();
		param2.setAction((byte)2);
		param2.setNumber(newNumber);
		param2.setBatchName(param.getBatchName());
		param2.setBatchIdStr(param.getBatchIdStr());
		Integer batchId = cardDao.createCardBatch(param2);
		if(batchId==0) {
			logger().info("生成batchId错误");
			return new CardInsertVo(JsonResultCode.CODE_FAIL,0);
		}
		Integer groupId = cardDao.generateGroupId(batchId);
		param2.setBatchId(batchId);
		param2.setGroupId(groupId);
		int num = cardDao.insertCardReceiveCodeByCheck(param2, list2);
		if(num==0) {
			logger().info("导入存在问题");
			return new CardInsertVo(JsonResultCode.CODE_FAIL,0);
		}
		return new CardInsertVo(JsonResultCode.CODE_SUCCESS,batchId);
	}
	
	
	/**
	 * 插入卡号密码
	 * @param list
	 * @param param
	 * @return
	 */
	public CardInsertVo importCardPwd(List<CardNoPwdExcelVo> list,CardBatchParam param) {
		int newNumber = list.size();
		if (newNumber > 10000) {
			// return 单个批次不能超过10000';
			return new CardInsertVo(JsonResultCode.CODE_EXCEL_NUM_MAX, 0);
		}
		if (newNumber == 0) {
			// return 单个批次不能为0';
			return new CardInsertVo(JsonResultCode.CODE_EXCEL_NUM_MIN, 0);
		}
		List<String> list2 = new ArrayList<String>();
		List<String> list3 = new ArrayList<String>();
		for (CardNoPwdExcelVo cardNoExcelVo : list) {
			list2.add(cardNoExcelVo.getCardNo());
			list3.add(cardNoExcelVo.getCardPwd());
		}
//		boolean isRepeat = list2.size() != new HashSet<String>(list2).size();
//		if (isRepeat) {
//			// return "存在重复的卡号，请检查！";
//			return JsonResultCode.CODE_EXCEL_HAVE_SAME;
//		}
		CardBatchParam param2=new CardBatchParam();
		param2.setAction((byte)2);
		param2.setNumber(newNumber);
		param2.setBatchName(param.getBatchName());
		Integer batchId = cardDao.createCardBatch(param2);
		if(batchId==0) {
			logger().info("生成batchId错误");
			return new CardInsertVo(JsonResultCode.CODE_FAIL, 0);
		}
		Integer groupId = cardDao.generateGroupId(batchId);
		param2.setBatchId(batchId);
		param2.setGroupId(groupId);
		int num = cardDao.insertIntoCardReceiveCodeByCheck(param2, list2,list3);
		if(num==0) {
			logger().info("导入存在问题");
			return new CardInsertVo(JsonResultCode.CODE_FAIL, 0);
		}
		return new CardInsertVo(JsonResultCode.CODE_SUCCESS, batchId);
	}

	/**
	 * 获得生成/导入记录
	 * @param batchId
	 * @return
	 */
	public BatchGroupVo getBatchGroupList(Integer batchId) {
		CardBatchRecord batch = cardDao.getBatch(batchId);
		if(batch==null) {
			return new BatchGroupVo();
		}
		CardBatchDetailVo into = batch.into(CardBatchDetailVo.class);
		List<CodeReceiveVo> list = cardDao.getBatchGroupList(batchId);
		int successNum = 0;
		int failNum = 0;
		boolean flag=false;
		for (CodeReceiveVo vo : list) {
			if(StringUtil.isEmpty(vo.getCode())) {
				flag=true;
			}
			String errorMsg = vo.getErrorMsg();
			if (StringUtils.isEmpty(errorMsg)) {
				successNum++;
			} else {
				failNum++;
			}
		}
		return new BatchGroupVo(batchId, successNum, failNum,into.getName(),into.getCreateTime(),flag);
	}
	
	
	/**
	 * 返回Excel信息
	 * 
	 * @param batchId
	 * @param lang
	 * @return
	 */
	public Workbook getExcel(Integer batchId, String lang, Boolean success, Boolean isPwd) {
		if (success) {
			logger().info("获取导入成功的信息");
			if (isPwd) {
				logger().info("卡号+密码领取导出");
				return getModelPwdMsg(lang, null, getSuccessPwdById(batchId, lang));
			} else {
				logger().info("领取码领取导出");
				return getModelMsg(lang, null, getSuccessById(batchId, lang));
			}
		} else {
			logger().info("获取导入失败的信息");
			if (isPwd) {
				logger().info("卡号+密码领取导出");
				return getModelPwdMsg(lang, getErrorMsgPwdById(batchId, lang), null);
			} else {
				logger().info("领取码领取导出");
				return getModelMsg(lang, getErrorMsgById(batchId, lang), null);
			}
		}

	}

	/**
	 * 领取码领取导出
	 * @param lang
	 * @param list
	 * @param list2
	 * @return
	 */
	public Workbook getModelMsg(String lang, List<CardNoExcelFailVo> list, List<CardNoExcelVo> list2) {
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		if (null == list) {
			excelWriter.writeModelList(list2, CardNoExcelVo.class);
		}
		if (null == list2) {
			excelWriter.writeModelList(list, CardNoExcelFailVo.class);
		}
		return workbook;
	}

	/**
	 * 查询领取码错误的
	 * @param batchId
	 * @param lang
	 * @return
	 */
	private List<CardNoExcelFailVo> getErrorMsgById(Integer batchId, String lang) {
		Result<CardReceiveCodeRecord> fetch = cardDao.getBatchGroupListByMsg(batchId, false);
		List<CardNoExcelFailVo> list = new ArrayList<CardNoExcelFailVo>();
		if (fetch != null) {
			list = fetch.into(CardNoExcelFailVo.class);
		}
		for (CardNoExcelFailVo vo : list) {
			String errorMsg = CardNoImportTemplate.getNameByCode(vo.getErrorMsg(), lang);
			if (StringUtil.isNotEmpty(errorMsg)) {
				vo.setErrorMsg(errorMsg);
			}
		}
		return list;
	}

	/**
	 * 查询领取码正确的
	 * @param batchId
	 * @param lang
	 * @return
	 */
	private List<CardNoExcelVo> getSuccessById(Integer batchId, String lang) {
		Result<CardReceiveCodeRecord> fetch = cardDao.getBatchGroupListByMsg(batchId, true);
		List<CardNoExcelVo> list = new ArrayList<CardNoExcelVo>();
		if (fetch != null) {
			list = fetch.into(CardNoExcelVo.class);
		}
		return list;
	}
	
	/**
	 * 卡号+密码领取导出
	 * @param lang
	 * @param list
	 * @param list2
	 * @return
	 */
	public Workbook getModelPwdMsg(String lang, List<CardNoPwdExcelFailVo> list, List<CardNoPwdExcelVo> list2) {
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		if (null == list) {
			excelWriter.writeModelList(list2, CardNoPwdExcelVo.class);
		}
		if (null == list2) {
			excelWriter.writeModelList(list, CardNoPwdExcelFailVo.class);
		}
		return workbook;
	}
	
	/**
	 * 查询卡号+密码错误的
	 * @param batchId
	 * @param lang
	 * @return
	 */
	private List<CardNoPwdExcelFailVo> getErrorMsgPwdById(Integer batchId, String lang) {
		Result<CardReceiveCodeRecord> fetch = cardDao.getBatchGroupListByMsg(batchId, false);
		List<CardNoPwdExcelFailVo> list = new ArrayList<CardNoPwdExcelFailVo>();
		if (fetch != null) {
			list = fetch.into(CardNoPwdExcelFailVo.class);
		}
		for (CardNoPwdExcelFailVo vo : list) {
			String errorMsg = CardNoImportTemplate.getNameByCode(vo.getErrorMsg(), lang);
			if (StringUtil.isNotEmpty(errorMsg)) {
				vo.setErrorMsg(errorMsg);
			}
		}
		return list;
	}
	
	/**
	 * 查询卡号+密码正确的
	 * @param batchId
	 * @param lang
	 * @return
	 */
	private List<CardNoPwdExcelVo> getSuccessPwdById(Integer batchId, String lang) {
		Result<CardReceiveCodeRecord> fetch = cardDao.getBatchGroupListByMsg(batchId, true);
		List<CardNoPwdExcelVo> list = new ArrayList<CardNoPwdExcelVo>();
		if (fetch != null) {
			list = fetch.into(CardNoPwdExcelVo.class);
		}
		return list;
	}
	
	/**
	 * 会员卡号+密码模板
	 * @param lang
	 * @return
	 */
	public Workbook getCardNoPwdTemplate(String lang) {
		List<CardNoPwdExcelVo> list = new ArrayList<CardNoPwdExcelVo>();
		for (int i = 11; i < 21; i++) {
			CardNoPwdExcelVo vo = new CardNoPwdExcelVo();
			vo.setCardNo("C1111" + i);
			vo.setCardPwd("123456");
			list.add(vo);
		}
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(list, CardNoPwdExcelVo.class);
		return workbook;
	}
	
	/**
	 * 	获取会员卡的分享二维码
	 * @param cardId
	 */
	public ShareQrCodeVo getShareCode(Integer cardId) {
		//String paramStr = String.format("cardId=%d&inviteId=", cardId);
		String paramStr = String.format("cardId=%d", cardId);
		String imageUrl = qrCodeService.getMpQrCode(QrCodeTypeEnum.USER_CARD_INFO,paramStr);
		ShareQrCodeVo vo = new ShareQrCodeVo();
		vo.setImageUrl(imageUrl);
		vo.setPagePath(QrCodeTypeEnum.USER_CARD_INFO.getPathUrl(paramStr));
		return vo;
	}

	public List<CardBasicVo> getCardById(List<Integer> param) {
		return  db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.ID.in(param))
			.fetchInto(CardBasicVo.class);
	}
	
	/**
	 * 	获取会员卡使用统计使用数据
	 */
	public CardUseStats getCardUseStatistic(MemberCardRecord card) {
		return CardUseStats.builder()
					.haveCardUser(userCardService.getCardUserNum(card.getId()))
					.haveReceivedNum(userCardService.getReceiveCardNum(card.getId()))
					.haveNormalNum(userCardService.getCanUseCardNum(card))
					.build();
	}
	
}