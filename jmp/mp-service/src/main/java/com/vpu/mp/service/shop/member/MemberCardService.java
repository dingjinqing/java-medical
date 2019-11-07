package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.GOODS_CARD_COUPLE;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.EXCHANGE_GOODS_NUM;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.MEMBER_CARD_ACCOUNT;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.STORE_SERVICE_TIMES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUTTON_ON;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_CRASH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_SCORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CHECKED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_BRAND;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_PLAT;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUPLE_TP_STORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LANGUAGE_TYPE_MEMBER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_BGT_COLOR;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_BGT_IMG;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DIS_ALL;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DIS_PART;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_DAY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_MONTH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_WEEK;
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
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_RA_CODE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_RA_PWD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_STP_ALL;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_STP_BAN;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_STP_PART;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_SUSE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_SUSE_OK;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_GRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_LIMIT;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_NORMAL;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MONTH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NUM_LETTERS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PAY_OWN_GOOD_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.REFUSED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.SHORT_ZERO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_FG_EXPIRED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.VERIFIED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.WEEK;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ZERO;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.ACCOUNT_DEFAULT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.POWER_MEMBER_CARD_ACCOUNT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_TO_BE_CONFIRMED;
import static org.jooq.impl.DSL.count;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep7;
import org.jooq.Result;
import org.jooq.SelectSeekStep1;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardConsumerRecord;
import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.db.shop.tables.records.ChargeMoneyRecord;
import com.vpu.mp.db.shop.tables.records.GoodsCardCoupleRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.MemberEducationEnum;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum;
import com.vpu.mp.service.pojo.shop.member.account.AddMemberCardParam;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;
import com.vpu.mp.service.pojo.shop.member.account.MemberCardVo;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditVo;
import com.vpu.mp.service.pojo.shop.member.card.BaseCardVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBasicVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBatchParam;
import com.vpu.mp.service.pojo.shop.member.card.CardBatchVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumeParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumeVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderParam;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderVo;
import com.vpu.mp.service.pojo.shop.member.card.CardIdParam;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.ChargeParam;
import com.vpu.mp.service.pojo.shop.member.card.ChargeVo;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveParam;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveVo;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.pojo.shop.member.card.LimitNumCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.LimitNumCardVo;
import com.vpu.mp.service.pojo.shop.member.card.MemberCardPojo;
import com.vpu.mp.service.pojo.shop.member.card.NormalCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.NormalCardVo;
import com.vpu.mp.service.pojo.shop.member.card.PowerCardParam;
import com.vpu.mp.service.pojo.shop.member.card.RankCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.RankCardVo;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.card.SimpleMemberCardVo;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.store.service.order.ServiceOrderDetailVo;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.operation.RecordMemberTradeService;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;

/**
 *
 * @author 黄壮壮
 * @Date: 2019年7月30日
 * @Description: 
 */
@Service
public class MemberCardService extends ShopBaseService {
	@Autowired private RecordMemberTradeService tradeService;
	@Autowired private CardDaoService cardDao;
	@Autowired private OrderGoodsService orderGoodsDao;
	@Autowired private ServiceOrderService serviceOrderDao;
	@Autowired private UserCardService userCardService;
	@Autowired private CardVerifyService cardVerifyService;

	/**
	 * 添加会员卡
	 */
	public JsonResultCode createMemberCard(CardParam card) {
		MemberCardRecord cardRecord = initMembercardRecordByCfgData(card);
		insertIntoMemberCard(cardRecord);
		return null;
	}

	/**
	 * 更新会员卡
	 */
	public void updateMemberCard(CardParam card) {
		MemberCardRecord cardRecord = initMembercardRecordByCfgData(card);

		// TODO 如果Record的属性值设置为null,会跟新数据库信息为null吗
		/** 更新数据 */
		updateMemberCardById(cardRecord, card.getId());
	}

	/**
	 * 用配置信息初始化MemberCardRecord
	 */
	private MemberCardRecord initMembercardRecordByCfgData(CardParam card) {
		logger().info("正在处理会卡配置信息");
		MemberCardRecord cardRecord = new MemberCardRecord();
		setCommonCardAttr(card, cardRecord);

		Byte cardType = card.getCardType();

		/** 判断卡类型 */
		if (MCARD_TP_NORMAL.equals(cardType) || MCARD_TP_LIMIT.equals(cardType)) {

			/** 会员有效期 */
			Byte expiredType = card.getExpiredType();
			if (MCARD_ET_FIX.equals(expiredType)) {
				cardRecord.setExpireType(MCARD_ET_FIX);
				cardRecord.setStartTime(card.getStartTime());
				cardRecord.setEndTime(card.getEndTime());
			} else if (MCARD_ET_DURING.equals(expiredType)) {
				cardRecord.setExpireType(MCARD_ET_DURING);
				cardRecord.setReceiveDay(card.getReceiveDay());
				cardRecord.setDateType(card.getDateType());
			} else if (MCARD_ET_FOREVER.equals(expiredType)) {
				cardRecord.setExpireType(MCARD_ET_FOREVER);
			}

			/** 使用门店 */
			String storeListType = card.getStoreListType();
			if (MCARD_STP_ALL.equals(storeListType)) {
				 cardRecord.setStoreList(MCARD_STP_ALL);
				 cardRecord.setStoreUseSwitch(MCARD_SUSE_OK);
			} else if (MCARD_STP_PART.equals(storeListType)) {
				if (card.getStoreList() != null) {
					String storeList = String.join(",", card.getStoreList());
					cardRecord.setStoreList(storeList);
					 cardRecord.setStoreUseSwitch(MCARD_SUSE_OK);
				} else {
					cardRecord.setStoreList(MCARD_STP_BAN);
					cardRecord.setStoreUseSwitch(MCARD_SUSE_NO);
				}
			} else if (MCARD_STP_BAN.equals(storeListType)) {
				    cardRecord.setStoreList(MCARD_STP_BAN);
				    cardRecord.setStoreUseSwitch(MCARD_SUSE_NO);
			}

			/** 领取设置 */
			/** 购买设置 */
			Byte isPay = card.getIsPay();
			if (MCARD_ISP_DEFAULT.equals(isPay)) {
				cardRecord.setIsPay(MCARD_ISP_DEFAULT);
			} else if (MCARD_ISP_BUY.equals(isPay)) {
				cardRecord.setIsPay(MCARD_ISP_BUY);
				if (BUY_BY_CRASH.equals(card.getPayType())) {
					cardRecord.setPayFee(card.getPayMoney());
				} else if (BUY_BY_SCORE.equals(card.getPayType())) {
					cardRecord.setPayFee(card.getPayScore());
				}
			} else if (MCARD_ISP_CODE.equals(isPay)) {
				// todo 领取码
				cardRecord.setIsPay(MCARD_ISP_CODE);
			}
		}

		/** 设置限次与等级会员卡的公共属性 */
		setLimitAndRankCard(card, cardRecord);

		/** 处理普通会员卡 */
		if (MCARD_TP_NORMAL.equals(cardType)) {
			dealWithNormalCard(card, cardRecord);
		} else if (MCARD_TP_LIMIT.equals(cardType)) {
			/** 处理限次会员卡 */
			dealWithLimitNumCard(card, cardRecord);
		} else if (MCARD_TP_GRADE.equals(cardType)) {
			dealWithRankCard(card, cardRecord);
		}

		return cardRecord;
	}

	/**
	 * 设置限次与等级会员卡的公共属性
	 */
	private void setLimitAndRankCard(CardParam card, MemberCardRecord cardRecord) {
		Byte cardType = card.getCardType();
		Byte payOwnGood;
		if (MCARD_TP_NORMAL.equals(cardType) || MCARD_TP_GRADE.equals(cardType)) {
			boolean flag = false;
			/** 是否专属购买商品 0不是 1是 */
			payOwnGood = (byte) (BUTTON_ON.equals(card.getPowerPayOwnGood()) ? 1 : 0);
			cardRecord.setPayOwnGood(payOwnGood);

			/** 1. 会员折扣 */
			if (CHECKED.equals(card.getPowerCount())) {
				flag = true;
				cardRecord.setDiscount(card.getDisCount());
			} else {
				/** 没有勾选值设置为null */
				cardRecord.setDiscount(null);
			}
			/** 折扣部分商品还是全部商品 */
			if (MCARD_DIS_ALL.equals(card.getDiscountIsAll())) {
				cardRecord.setDiscountIsAll(MCARD_DIS_ALL);
			} else if (MCARD_DIS_PART.equals(card.getDiscountIsAll())) {
				cardRecord.setDiscountIsAll(MCARD_DIS_PART);
				/** 处理需要折扣的部分商品,平台,商家 */
				updateDiscountPartGoods(card, cardRecord);
			}

			/** 2. 会员专享商品 */
			if (BUTTON_ON.equals(card.getPowerPayOwnGood())) {
				//处理允许会员专享的商品，商家，分类，平台等
				List<Integer> cardList =  new ArrayList<>();
				cardList.add(card.getId());
				/** 专享商品-商品id */
				if(card.getOwnGoodsId() != null) {
					batchUpdateGoods(card.getOwnGoodsId(), cardList);
				}

				/** 专享商品-商家分类id */
				if(card.getOwnStoreCategoryIds() != null) {
					batchUpdateStoreCategory(card.getOwnStoreCategoryIds(), cardList);
				}

				/** 专享商品-平台分类 */
				if(card.getOwnPlatFormCategoryIds() != null) {
					batchUpdatePlatformCategory(card.getOwnPlatFormCategoryIds(), cardList);
				}
				/** 专享商品-品牌分类 */
				if(card.getOwnBrandId() != null) {
					batchUpdateBrandId(card.getOwnBrandId(),cardList);
				}

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
			} else {
				// 准备设置为空
				cardRecord.setSorce(null);
				cardRecord.setBuyScore(null);

			}

			if (!flag && MCARD_TP_GRADE.equals(cardType)) {
				/** 必须选择一项会员权益 针对等级卡 */
				throw new IllegalArgumentException("必须选择一项会员权益 针对等级卡");
				// JsonResultCode.CODE_MEMBER_CARD_RIGHTS_EMPTY;
			}

		}
	}

	/**
	 * 为单张会员卡设置专享商品 | 商家分类 | 平台分类
	 * @param cardId 会员卡id
	 * @param ownId 专享商品id列表 |   商家分类id列表 |  平台分类id列表
	 * @param type 标签关联类型 如： {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.GOODS_TYPE }
	 */
	public void addGoodsCardCouple(Integer cardId, List<Integer> ownId,Byte type) {
		this.batchUpdateGoods(ownId, Arrays.asList(cardId), type);
	}

	/**
	 * 通过会员卡id,更新折扣指定商品信息： 商品，商家，平台等信息
     *
	 * @param card
	 */
	public void updateDiscountPartGoodsByCardId(CardParam card) {
		logger().info("更新会员折扣指定商品");
		MemberCardRecord cardRecord = new MemberCardRecord();
		Integer id = card.getId();

		/** 准备更新数据 */
		updateDiscountPartGoods(card, cardRecord);

		/** 更新到数据库 */
		updateMemberCardById(cardRecord, id);
	}


    /**
	 * 根据会员卡id批量更新会员专享商品
	 * @param goodsIdList
	 * @param cardIdList
	 */
	public void batchUpdateGoods(List<Integer> goodsIdList,List<Integer> cardIdList) {
		batchUpdateGoods(goodsIdList,cardIdList,COUPLE_TP_GOODS);
	}


	/**
	 * 根据会员卡id批量更新会员专享商品-商家分类
	 * @param storeIdList
	 * @param cardIdList
	 */
	public void batchUpdateStoreCategory(List<Integer> storeIdList,List<Integer> cardIdList) {
		logger().info("更新会员专享商品-商家分类");
		batchUpdateGoods(storeIdList,cardIdList,COUPLE_TP_STORE);
	}

	/**
	 * 根据会员卡id批量更新会员专享商品-平台分类
	 * @param platformIdList
	 * @param cardIdList
	 */
	public void batchUpdatePlatformCategory(List<Integer> platformIdList,List<Integer> cardIdList) {
		batchUpdateGoods(platformIdList,cardIdList,COUPLE_TP_PLAT);
	}


    public void batchUpdateBrandId(List<Integer> brandIdList,List<Integer> cardIdList) {
		batchUpdateGoods(brandIdList,cardIdList,COUPLE_TP_BRAND);
	}


    /**
	 * 根据会员id以及标签关联类型，批量更新会员专享商品： 商品，平台分类，商家分类
	 * @param goodsIdList
	 * @param cardIdList
	 * @param type
	 */
	public void batchUpdateGoods(List<Integer> goodsIdList,List<Integer> cardIdList,Byte type) {
		logger().info("根据会员卡id批量更新会员专享商品");
		this.transaction(()->{

			/** 删除会员专享商品记录 */
			int deleteNum = deleteOwnEnjoyGoods(cardIdList,type);
			logger().info("成功删除："+deleteNum);

			InsertValuesStep3<GoodsCardCoupleRecord, Integer, Integer, Byte> insert = db().
												insertInto(GOODS_CARD_COUPLE)
												.columns(GOODS_CARD_COUPLE.CARD_ID,GOODS_CARD_COUPLE.GCTA_ID,GOODS_CARD_COUPLE.TYPE);

			for(Integer cardId: cardIdList) {
				for(Integer goodsId:  goodsIdList) {
					insert.values(cardId, goodsId,type);
				}
			}

			int execute = insert.execute();
			logger().info("成功更新会员专享商品： "+execute+"行");
		});
	}

    /**
     * 根据商品或分类id获取相应的会员卡id
     * @author 李晓冰
     * @param gctaId 商品或分类id
     * @param type 类型
     * @return 会员卡ids
     */
    public List<Integer> selectOwnEnjoyCardByGcta(Integer gctaId, Byte type) {
        List<Integer> cardIds = db().select(GOODS_CARD_COUPLE.CARD_ID).from(GOODS_CARD_COUPLE)
            .where(GOODS_CARD_COUPLE.GCTA_ID.eq(gctaId)).and(GOODS_CARD_COUPLE.TYPE.eq(type)).fetchInto(Integer.class);

        return cardIds;
    }
    /**
     *  删除商品会员卡的专属信息
     * @author 李晓冰
     * @param gctaId   商品或分类id
     * @param type     类型
     */
    public void deleteOwnEnjoyGoodsByGcta(List<Integer> gctaId, Byte type) {
        db().deleteFrom(GOODS_CARD_COUPLE).where(GOODS_CARD_COUPLE.GCTA_ID.in(gctaId)).and(GOODS_CARD_COUPLE.TYPE.eq(type)).execute();
    }
	/**
	 * 根据会员卡id,标签关联类型进行删除
	 * @param cardIdList
	 * @param type
	 * @return
	 */
	private int deleteOwnEnjoyGoods(List<Integer> cardIdList,Byte type) {
		return db().deleteFrom(GOODS_CARD_COUPLE)
				.where(GOODS_CARD_COUPLE.CARD_ID.in(cardIdList))
				.and(GOODS_CARD_COUPLE.TYPE.eq(type))
				.execute();
	}


    /**
	 * 更新折扣指定商品信息： 商品，商家，平台
     *
	 * @param card
	 * @param cardRecord
	 */
	private void updateDiscountPartGoods(CardParam card, MemberCardRecord cardRecord) {
		logger().info("更新折扣指定商品信息： 商品，商家，平台,品牌");
		/** 添加商品 */
		if (card.getGoodsId() != null) {
			String discountGoodsIds = Util.listToString(Arrays.asList(card.getGoodsId()));
			logger().info("折扣商品id: " + discountGoodsIds);
			cardRecord.setDiscountGoodsId(discountGoodsIds);
		}

		/** 折扣商家分类id */
		if (card.getShopCategoryIds() != null) {
			String discountSortIds = Util.listToString(Arrays.asList(card.getShopCategoryIds()));
			logger().info("折扣商家分类id " + discountSortIds);
			cardRecord.setDiscountSortId(discountSortIds);
		}

		/** 添加平台 */
		if (card.getPlatformCategoryIds() != null) {
			String discountCatId = Util.listToString(Arrays.asList(card.getPlatformCategoryIds()));
			logger().info("折扣平台分类id " + discountCatId);
			cardRecord.setDiscountCatId(discountCatId);
		}

		/** 商品品牌 */
		if(card.getDiscountBrandId() != null) {
			String brandId = Util.listToString(Arrays.asList(card.getDiscountBrandId()));
			logger().info("折扣品牌id "+brandId);
			cardRecord.setDiscountBrandId(brandId);
		}
	}

	/**
	 * 处理等级会员卡的信
     *
	 * @param card
	 * @param cardRecord
	 * @return
	 */
	private void dealWithRankCard(CardParam card, MemberCardRecord cardRecord) {

		/** 启用 */
		cardRecord.setFlag(card.getFlag());

		/** 有效时间 */
		cardRecord.setExpireType(MCARD_ET_FOREVER);

		/** 升级条件 */
		GradeConditionJson conditon = card.getGradeConditionJson();
		cardRecord.setGradeCondition(Util.toJson(conditon));

		/** 等级 */
		cardRecord.setGrade(card.getGrade());
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
		if (MCARD_BGT_COLOR.equals(bgType)) {
			/** 背景色 */
			cardRecord.setBgType(MCARD_BGT_COLOR);
			cardRecord.setBgColor(card.getBgColor());
		} else if (MCARD_BGT_IMG.equals(bgType)) {
			cardRecord.setBgType(MCARD_BGT_IMG);
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
		if (MCARD_ACT_NO.equals(card.getActivation())) {
			cardRecord.setActivation(MCARD_ACT_NO);
		} else if (MCARD_ACT_YES.equals(card.getActivation())) {
			cardRecord.setActivation(MCARD_ACT_YES);
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
	private void dealWithLimitNumCard(CardParam card, MemberCardRecord cardRecord) {

		/** 适用商品 */
		Byte isExchange = card.getIsExchange();
		if (MCARD_ISE_NON.equals(isExchange)) {
			cardRecord.setIsExchang(MCARD_ISE_NON);
		} else if (MCARD_ISE_PART.equals(isExchange) || MCARD_ISE_ALL.equals(isExchange)) {
			cardRecord.setIsExchang(isExchange);
			/** 设置允许兑换次数 */
			cardRecord.setExchangCount(card.getExchangCount());
			/** 运费策略 */
			cardRecord.setExchangFreight(card.getExchangFreight());

			if (MCARD_ISE_PART.equals(isExchange)) {
				/** 处理允许的店铺 */
				String exchangGoods = String.join(",", card.getExchangGoods());
				cardRecord.setExchangGoods(exchangGoods);
			}
		}

		/** 处理使用门店 */
		if (!MCARD_STP_BAN.equals(card.getStoreListType())) {
			/** 处理工作日，工作日 */
			cardRecord.setUseTime(card.getUseTime());
			/** 使用次数 */
			cardRecord.setCount(card.getCount());
		}

		/** 会员卡数量 */
		cardRecord.setStock(card.getStock());

		/** 每人领取次数 */
		cardRecord.setLimit(card.getLimits());
	}

	/**
	 * 处理普通会员卡
     *
	 * @param card
	 * @param cardRecord
	 * @return
	 */
	private void dealWithNormalCard(CardParam card, MemberCardRecord cardRecord) {

		/** 处理会员权益 */
		boolean flag = false;

		/** 4. 卡充值 */
		if (CHECKED.equals(card.getPowerCard())) {
			flag = true;
			/** 设置金额 */
			cardRecord.setSendMoney(card.getSendMoney());
			/** 设置开卡策略 */
			cardRecord.setChargeMoney(Util.toJson(card.getPowerCardJson()));
		}else {
			cardRecord.setSendMoney(null);
			cardRecord.setChargeMoney(null);
		}
		if (!flag) {
			/** 必须选择一项会员权益 */
			throw new IllegalArgumentException("必须选择一项会员权益 针对等级卡");
		}
	}

	/**
	 * 将数据放入到数据库
     *
	 * @param cardRecord
	 */
	private int insertIntoMemberCard(MemberCardRecord cardRecord) {

		return db().executeInsert(cardRecord);
	}

	/**
	 * 会员卡数据更新
     *
	 * @param cardRecord
	 */
	private void updateMemberCardById(MemberCardRecord cardRecord, Integer id) {
		int num = db().executeUpdate(cardRecord, MEMBER_CARD.ID.eq(id));
		logger().info("成功更新： " + num + " 行数据");
	}

	/**
	 * 查询等级会员卡,按照等级降序
	 */
	public PageResult<RankCardVo> getRankCardList(SearchCardParam param) {
		logger().info("正在分页查询等级会员卡");
		SelectSeekStep1<MemberCardRecord, String> select = db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.CARD_TYPE.equal(MCARD_TP_GRADE)).and(MEMBER_CARD.DEL_FLAG.equal(MCARD_DF_NO))
		.orderBy(MEMBER_CARD.GRADE.desc());
		PageResult<RankCardVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),
				RankCardVo.class);
		/** 执行转换 */
		pageResult.dataList.stream().forEach(vo -> vo.changeJsonCfg());
		return pageResult;
	}

	/**
	 * 分页查询限次会员卡
	 */
	public PageResult<LimitNumCardVo> getLimitCardList(SearchCardParam param) {

		logger().info("正在分页查询限次会员卡");
		
		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.CARD_TYPE.equal(MCARD_TP_LIMIT)).and(MEMBER_CARD.DEL_FLAG.equal(MCARD_DF_NO))
				.orderBy(MEMBER_CARD.ID.desc());

		PageResult<LimitNumCardVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),
				LimitNumCardVo.class);
		/** 查询领取次数 */
		Map<Integer, Integer> intoMap = db().select(USER_CARD.CARD_ID, count()).from(USER_CARD)
				.groupBy(USER_CARD.CARD_ID).fetch().intoMap(USER_CARD.CARD_ID, count());

		for (LimitNumCardVo vo : pageResult.dataList) {
			/** 设置未领取值 */
			vo.setHasSend(intoMap.get(vo.getId()) == null ? 0 : intoMap.get(vo.getId()));
			vo.changeJsonCfg();
		}
		return pageResult;
	}

	/**
	 * 分页查询普通会员卡
     *
	 * @param param
	 */
	public PageResult<NormalCardVo> getNormalCardList(SearchCardParam param) {
		logger().info("正在分页查询普通会员卡");
		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.CARD_TYPE.equal(MCARD_TP_NORMAL)).and(MEMBER_CARD.DEL_FLAG.equal(MCARD_DF_NO))
				.orderBy(MEMBER_CARD.ID.desc());

		PageResult<NormalCardVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),
				NormalCardVo.class);
		/** 将json配置文件转化成合适的数据给前端 */
		for (NormalCardVo vo : pageResult.dataList) {
			vo.changeJsonCfg();
		}

		return pageResult;
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
		if (MCARD_TP_NORMAL.equals(cardType)) {
			result =  getNormalCardList(param);
		} else if (MCARD_TP_LIMIT.equals(cardType)) {
			result = getLimitCardList(param);
		} else if (MCARD_TP_GRADE.equals(cardType)) {
			result = getRankCardList(param);
		}

		if(result != null) {
			// 获取头像
            String avatar = saas().shop.getShopAvatarById(this.getShopId());
			result.dataList.stream().forEach(item->item.setAvatar(avatar));

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

	public MemberCardRecord getCardById(Integer cardId) {
		MemberCardRecord card = cardDao.getCardById(cardId);
		return card != null ? card : new MemberCardRecord();
	}

	/**
	 * 根据ID获取该会员卡的详细信息
     *
	 * @param param
	 * @return
	 */
	public BaseCardVo getCardDetailById(CardIdParam param) {
		MemberCardRecord card = getCardById(param.getId());
		if(card != null) {
			Byte cardType = card.getCardType();
			if (MCARD_TP_NORMAL.equals(cardType)) {
				return changeToNormalCardDetail(card);
			} else if (MCARD_TP_LIMIT.equals(cardType)) {
				return changeToLimitCardDetail(card);
			} else if (MCARD_TP_GRADE.equals(cardType)) {
				return changeToGradeCardDetail(card);
			}
		}
		return null;
	}

	private NormalCardToVo changeToNormalCardDetail(MemberCardRecord card) {
		logger().info("正在处理普通会员卡");
		NormalCardToVo normalCard = card.into(NormalCardToVo.class);
		normalCard.changeJsonCfg();
		return normalCard;
	}

	private LimitNumCardToVo changeToLimitCardDetail(MemberCardRecord card) {
		logger().info("正在处理出限次会员卡");
		LimitNumCardToVo limitCard = card.into(LimitNumCardToVo.class);
		int numOfSendCard = getNumSendCardById(limitCard.getId());
		limitCard.setHasSend(numOfSendCard);
		limitCard.changeJsonCfg();
		return limitCard;
	}

	private RankCardToVo changeToGradeCardDetail(MemberCardRecord card) {
		logger().info("正在处理等级会员卡");
		RankCardToVo gradeCard = card.into(RankCardToVo.class);
		gradeCard.changeJsonCfg();
		return gradeCard;
	}

	/**
	 * 获取已经发放的卡的数量
	 * @param cardId
	 * @return
	 */
	public int getNumSendCardById(Integer cardId) {
		return userCardService.getNumCardsWithSameId(cardId);
	}


    /**
	 * 根据会员卡ID字符串（逗号分隔）取会员卡信息列表
     *
	 */
	public List<SimpleMemberCardVo> getMemberCardByCardIdsString(String cardIdsString) {
		return db().select(MEMBER_CARD.ID, MEMBER_CARD.CARD_NAME).from(MEMBER_CARD)
				.where(DslPlus.findInSet(cardIdsString, MEMBER_CARD.ID))
				.and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchInto(SimpleMemberCardVo.class);
	}

	/**
	 * 获取可用的所有会员卡弹窗
	 * 返回的对象信息为会员卡的id与名称
	 */
	public List<CardBasicVo> getAllUserCard() {
		// 等级降序
		logger().info("正在获取所有的可用会员卡，按等级排序");
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		List<CardBasicVo> cardList = db().select(MEMBER_CARD.ID,MEMBER_CARD.CARD_NAME).from(MEMBER_CARD)
			.where(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING))
			.and(
					(MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)
			                .and(MEMBER_CARD.END_TIME.ge(localDateTime))
					).or(
							MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING,MCARD_ET_FOREVER)
						)
				)
			.orderBy(MEMBER_CARD.CARD_TYPE.asc(),MEMBER_CARD.GRADE.asc(),MEMBER_CARD.ID.asc())
			.fetch()
			.into(CardBasicVo.class);

		return cardList;
	}

	/**
	 * 获取专属会员卡
	 * @return List<CardBasicVo>
	 */
	public List<CardBasicVo> getCardExclusive() {
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		List<CardBasicVo> cardList = db().select(MEMBER_CARD.ID,MEMBER_CARD.CARD_NAME).from(MEMBER_CARD)
			.where(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING))
			.and(
					(MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)
			                .and(MEMBER_CARD.END_TIME.ge(localDateTime))
					).or(
							MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING,MCARD_ET_FOREVER)
						)
				)
			.and(MEMBER_CARD.PAY_OWN_GOOD.eq(PAY_OWN_GOOD_YES))
			.and(MEMBER_CARD.CARD_TYPE.in(MCARD_TP_NORMAL,MCARD_TP_GRADE))
			.orderBy(MEMBER_CARD.GRADE.asc(),MEMBER_CARD.ID.asc())
			.fetch()
			.into(CardBasicVo.class);

		return cardList;
	}


    /**
	 * 会员卡列表
     *
	 * @return
	 */
	public MemberCardVo getAllCardList() {

		Result<MemberCardRecord> cardRecords = selectAllMemberCard();
		MemberCardVo vo = new MemberCardVo();
		/** 分类 普通 | 限次 | 等级 会员卡 */
		logger().info("正在分类处理");
		for (MemberCardRecord card : cardRecords) {
			Byte cardType = card.getCardType();
			MemberCard cardVo = card.into(MemberCard.class);
			/** 执行策略 */
			cardVo.changeJsonCfg();

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
	 * 查询所有的会员卡
	 */
	private Result<MemberCardRecord> selectAllMemberCard() {
		logger().info("查询所有会员卡");
		Result<MemberCardRecord> cardRecords = db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.DEL_FLAG.equal(MCARD_DF_NO)).fetch();
		return cardRecords;
	}

	/**
	 * 为会员分配会员卡
	 */
	public void addCardForMember(AddMemberCardParam param) {

		/** 准备数据 */
		int sizeOfUserId = param.getUserIdList().size();
		int sizeOfCardId = param.getCardIdList().size();
		List<Integer> cardIdList = param.getCardIdList();
		List<Integer> userIdList = param.getUserIdList();

		/** cardNo */
		Queue<String> cardNoList = new LinkedList<>();
		for (int i = 0; i < sizeOfUserId; i++) {
			for (int j = 0; j < sizeOfCardId; j++) {
				/** 确保cardNo唯一性 */
				while (true) {
					String cardNo = generateCardNo(cardIdList.get(j));
					if (!cardNoList.contains(cardNo)) {
						cardNoList.add(cardNo);
						break;
					}
				}
			}
		}

		/** 查询所有的会员卡 */
		Map<Integer, MemberCardRecord> map = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.ID.in(param.getCardIdList())).fetch().intoMap(MEMBER_CARD.ID, MemberCardRecord.class);

		logger().info("一共查询到: " + map.size() + " 张会员卡");

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expireTime = null;
		/** 过期时间-多少日内 */
		for (Integer i : map.keySet()) {
			MemberCardRecord memberCard = map.get(i);
			if (MCARD_ET_DURING.equals(memberCard.getExpireType())) {
				/** 计算过期时间 */
				Byte dateType = memberCard.getDateType();
				/** 领取之日起n */
				Integer receiveDay = memberCard.getReceiveDay();
				if (MCARD_DT_DAY.equals(dateType)) {
					expireTime = now.plusDays(receiveDay);
				} else if (MCARD_DT_WEEK.equals(dateType)) {
					expireTime = now.plusDays(WEEK * receiveDay);
				} else if (MCARD_DT_MONTH.equals(dateType)) {
					expireTime = now.plusDays(MONTH * receiveDay);
				}
				memberCard.setEndTime(Timestamp.valueOf(expireTime));
			}
		}

		/** insert */
		/** USER_CARD.SURPLUS-门店兑换次数，USER_CARD.EXCHANG_SURPLUS-商品兑换次数 */
		InsertValuesStep7<UserCardRecord, Integer, Integer, String, Timestamp, Integer, Timestamp, Integer> insert = db()
				.insertInto(USER_CARD).columns(USER_CARD.USER_ID, USER_CARD.CARD_ID, USER_CARD.CARD_NO,
						USER_CARD.EXPIRE_TIME, USER_CARD.SURPLUS, USER_CARD.ACTIVATION_TIME, USER_CARD.EXCHANG_SURPLUS);

		for (int i = 0; i < sizeOfUserId; i++) {
			for (int j = 0; j < sizeOfCardId; j++) {
                MemberCardRecord memberCard = map.get(cardIdList.get(j));
				if(memberCard.getCount() == null) {
					memberCard.setCount(0);
				}
				if(memberCard.getExchangCount() == null) {
					memberCard.setExchangCount(0);
				}
				insert.values(userIdList.get(i), cardIdList.get(j), cardNoList.poll(), memberCard.getEndTime(),
						memberCard.getCount(), Timestamp.valueOf(now), memberCard.getExchangCount());
			}
		}

		int execute = insert.execute();
		logger().info("成功添加： " + execute + " 行记录");

		/** add record */

		Map<Integer, String> userNameMap = db().select(USER.USER_ID, USER.USERNAME).from(USER)
				.where(USER.USER_ID.in(userIdList)).fetch().intoMap(USER.USER_ID, USER.USERNAME);
		List<String> tmpData = new ArrayList<>();
		String messageFormat = RecordContentTemplate.MEMBER_CARD_SEND.getMessage();
		/** generate template message */
		for (int i = 0; i < sizeOfUserId; i++) {
			for (int j = 0; j < sizeOfCardId; j++) {
                MemberCardRecord memberCard = map.get(cardIdList.get(j));
				tmpData.add(String.format(messageFormat, userIdList.get(i), userNameMap.get(userIdList.get(i)),
						memberCard.getCardName()));
			}
		}

		saas().getShopApp(getShopId()).record.insertRecord(
				Arrays.asList(RecordContentTemplate.MEMBER_CARD_SEND.code),
				tmpData.stream().toArray(String[]::new));
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
     * @param data 用户卡相关数据
	 * @param adminUser 操作员
	 * @param tradeType  交易类型 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @param tradeFlow  资金流向 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @throws MpException
	 */
	public void updateMemberCardAccount(CardConsumpData data,Integer adminUser,Byte tradeType,Byte tradeFlow,String language) throws MpException {

        /** 1.-获取数据库中的存储的信息 */
		UserCardRecord userCard = getUserCardInfoByCardNo(data.getCardNo());

        /** 2-判断会员卡余额是属于充值还是消费 */
		int compare = data.getMoney().compareTo(ZERO);
		if(compare<0) {
			/** 2.1-如果消费余额超出用户会员卡现有余额，则抛出异常 */
			if((data.getMoney().add(userCard.getMoney())).compareTo(ZERO) == -1) {
				throw new MpException(JsonResultCode.CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
			}
			/** -消费会员卡余额 */
			consumpUserCard(data,MEMBER_CARD_ACCOUNT,language);
		}else {
			/** 2.2 充值会员卡余额 */
			chargeUserCard(data,MEMBER_CARD_ACCOUNT,language);
		}


        /** 3-更新user_card用户会员卡的余额 */
		updateUserCard(data, userCard,MEMBER_CARD_ACCOUNT);
		//TODO模板消息
		/**
		 * 4-记录交易明细
		 */
		insertTradesRecord(data, tradeType, tradeFlow);


    }


    /**
	 * 更新用户卡且为限次会员卡的消费次数（门店）
     * @param data 用户卡相关数据
	 * @param adminUser 操作员
	 * @param tradeType  交易类型 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @param tradeFlow  资金流向 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
     * @throws MpException
	 */
	public void updateMemberCardSurplus(CardConsumpData data,Integer adminUser,Byte tradeType,Byte tradeFlow,String language) throws MpException {
		/** 1-判断是不是限次会员卡则结束 */
		if(!data.getType().equals(MCARD_TP_LIMIT)) {
		    throw new MpException(JsonResultCode.CODE_PARAM_ERROR);
		}

        /** 2.-获取数据库中的存储的信息 */
		UserCardRecord userCard = getUserCardInfoByCardNo(data.getCardNo());

        /** 3-判断是使用还是增加用户卡的卡剩余次数 */
		if(data.getCount()<0) {
			/** 3.1-减少（使用）卡剩余次数 */
			/** -检查卡剩余次数是否够用 */
			if((data.getCount()+userCard.getSurplus())<0) {
				throw new MpException(JsonResultCode.CODE_MEMBER_CARD_SURPLUS_UPDATE_FAIL);
			}
			/** -消费会员卡剩余次数 */
			consumpUserCard(data,STORE_SERVICE_TIMES,language);
		}else {
			/** 3.2-增加(充值)卡剩余次数 */
			chargeUserCard(data,STORE_SERVICE_TIMES,language);
		}

        /** 4-更新user_card用户会员卡的消费次数 */
		updateUserCard(data, userCard,STORE_SERVICE_TIMES);
		//TODO模板消息
		/**
		 * 5-记录交易明细
		 */
		insertTradesRecord(data, tradeType, tradeFlow);
	}


    /**
	 * 更新用户卡且为限次会员卡的卡剩余兑换次数
     * @param data 用户卡相关数据
	 * @param adminUser 操作员
	 * @param tradeType  交易类型 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @param tradeFlow  资金流向 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
     * @throws MpException
	 */
	public void updateMemberCardExchangeSurplus(CardConsumpData data,Integer adminUser,Byte tradeType,Byte tradeFlow,String language) throws MpException {
		/** 1-判断是不是限次会员卡则结束 */
		if(!MCARD_TP_LIMIT.equals(data.getType())) {
			return;
		}

        /** 2.-获取数据库中的存储的信息 */
		UserCardRecord userCard = getUserCardInfoByCardNo(data.getCardNo());

        /** 3-判断是使用还是增加用户卡的卡剩余兑换次数 */
		if(data.getCount()<0) {
			/** 3.1-减少（使用）卡剩余兑换次数 */
			/** -检查卡卡剩余兑换次数是否够用 */
			if((data.getExchangeCount()+userCard.getExchangSurplus())<0) {
				throw new MpException(JsonResultCode.CODE_MEMBER_CARD_EXCHANGSURPLUS_UPDATE_FAIL);
			}
			/** -消费会员卡剩余次数 */
			consumpUserCard(data,EXCHANGE_GOODS_NUM,language);
		}else {
			/** 3.2-增加(充值)卡剩余次数 */
			chargeUserCard(data,EXCHANGE_GOODS_NUM,language);
		}

        /** 4-更新user_card用户会员卡的卡剩余兑换次数 */
		updateUserCard(data, userCard,EXCHANGE_GOODS_NUM);
		//TODO模板消息
		/**
		 * 5-记录交易明细
		 */
		insertTradesRecord(data, tradeType, tradeFlow);
	}


    /**
	 * 充值用户卡
	 * @param data
	 */
	private void chargeUserCard(CardConsumpData data,MemberOperateRecordEnum memberOperate,String language) {
		setDefaultReason(data,memberOperate,language);
		insertIntoChargeMoney(data);
	}

	/**
	 * 消费用户卡
	 * @param data
	 * @param userCard
	 * @throws MpException
	 */
	private void consumpUserCard(CardConsumpData data,MemberOperateRecordEnum memberOperate,String language){

        setDefaultReason(data,memberOperate,language);
		insertIntoCardConsumer(data);
	}

	/**
	 * 设置默认的充值 | 消费原因
	 * @param data
	 */
	private void setDefaultReason(CardConsumpData data,MemberOperateRecordEnum memberOperate,String language) {
		/** 1-若reason原因为空 则设置为默认值 */
		if(StringUtils.isBlank(data.getReason())) {
			/** - 会员卡余额 */
			String value = memberOperate.getValue();
			value = Util.translateMessage(language,value,LANGUAGE_TYPE_MEMBER);
			String t = data.getReason()+value;
			data.setReason(t);
		}
	}


    /**
	 * 更新user_card表
	 * @param data
	 * @param userCard
	 */
	private void updateUserCard(CardConsumpData data, UserCardRecord userCard,MemberOperateRecordEnum memberOperate) {
		/** 更新用户卡余额 */
		if(memberOperate == MEMBER_CARD_ACCOUNT) {
			BigDecimal money = userCard.getMoney().add(data.getMoney());
			db().update(USER_CARD).set(USER_CARD.MONEY,money)
				.where(USER_CARD.CARD_NO.eq(userCard.getCardNo()))
				.execute();
		}else if(memberOperate == STORE_SERVICE_TIMES) {
			/** 更新用户卡消费次数 */
			Integer surplus = userCard.getSurplus()+data.getCount();
			db().update(USER_CARD).set(USER_CARD.SURPLUS,surplus)
				.where(USER_CARD.CARD_NO.eq(userCard.getCardNo()))
				.execute();
		}else if(memberOperate == EXCHANGE_GOODS_NUM) {
			/** 更新用户卡兑换次数 */
			Integer exchangeSurplus = userCard.getExchangSurplus()+data.getExchangeCount();
			db().update(USER_CARD).set(USER_CARD.EXCHANG_SURPLUS, exchangeSurplus)
				.where(USER_CARD.CARD_NO.eq(userCard.getCardNo()))
				.execute();
		}
	}


    /**
	 * 记录会员余额交易明细
	 * @param data
	 * @param tradeType  交易类型说明 如  微信支付类型{@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.WX_PAY }
	 * @param tradeFlow  资金流向类型  如收入 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME}
	 */
	private void insertTradesRecord(CardConsumpData data, Byte tradeType, Byte tradeFlow) {
		/** 交易明细 */
		TradesRecordRecord record = new TradesRecordRecord();
		/** 是否为余额充值 */
		BigDecimal tradeNum = tradeType==POWER_MEMBER_CARD_ACCOUNT.getValue()?data.getMoney():data.getMoney().abs();
		record.setTradeNum(tradeNum);

        String tradeSn = data.getOrderSn()==null?"":data.getOrderSn();
		record.setTradeSn(tradeSn);

        record.setUserId(data.getUserId());
		record.setTradeContent(ACCOUNT_DEFAULT.getValue());
		record.setTradeType(tradeType);
		record.setTradeFlow(tradeFlow);
		if(tradeFlow == TRADE_FLOW_TO_BE_CONFIRMED.getValue()) {
			tradeFlow = TRADE_FLOW_INCOME.getValue();
		}
		record.setTradeStatus(tradeFlow);
		tradeService.insertRecord(record);
	}


    /**
	 * 会员卡充值记录添加到charge_money
	 * @param data
	 */
	private void insertIntoChargeMoney(CardConsumpData data) {
		ChargeMoneyRecord chargeMoneyRecord = new ChargeMoneyRecord();
		FieldsUtil.assignNotNull(data,chargeMoneyRecord);
		/** 处理数据库表中带下划线的字段 */
		if(data.getUserId()!=null) {
			chargeMoneyRecord.setUserId(data.getUserId());
		}
		if(data.getCardId() != null) {
			chargeMoneyRecord.setCardId(data.getCardId());
		}
		/** 充值的钱 */
		if(data.getMoney()!=null) {
			chargeMoneyRecord.setCharge(data.getMoney());
		}

        if(data.getPrepayId()!=null) {
			chargeMoneyRecord.setPrepayId(data.getPrepayId());
		}
		if(data.getOrderSn()!=null) {
			chargeMoneyRecord.setOrderSn(data.getOrderSn());
		}
		if(data.getOrderStatus()!=null) {
			chargeMoneyRecord.setOrderStatus(data.getOrderStatus());
		}
		if(data.getMoneyPaid()!=null) {
			chargeMoneyRecord.setMoneyPaid(data.getMoneyPaid());
		}
		if(data.getChargeType()!=null) {
			chargeMoneyRecord.setChargeType(data.getChargeType());
		}
		if(data.getCardNo()!=null) {
			chargeMoneyRecord.setCardNo(data.getCardNo());
		}
		if(data.getAliTradeNo()!=null) {
			chargeMoneyRecord.setAliTradeNo(data.getAliTradeNo());
		}
		if(data.getExchangeCount()!= null) {
			chargeMoneyRecord.setExchangCount(data.getExchangeCount());
		}
		chargeMoneyRecord.insert();

    }
	/**
	 * 会员卡消费记录添加到card_consumer
	 * @param data
	 */
	private void insertIntoCardConsumer(CardConsumpData data) {
		CardConsumerRecord cardConsumerRecord = new CardConsumerRecord();
		FieldsUtil.assignNotNull(data,cardConsumerRecord);
		/** 处理数据库表中带下划线的字段 */
		if(data.getUserId()!=null) {
			cardConsumerRecord.setUserId(data.getUserId());
		}
		if(data.getCardId() != null) {
			cardConsumerRecord.setCardId(data.getCardId());
		}
		if(data.getCardNo()!=null) {
			cardConsumerRecord.setCardNo(data.getCardNo());
		}
		if(data.getExchangeCount()!=null) {
			cardConsumerRecord.setExchangCount(data.getExchangeCount());
		}
		if(data.getOrderSn() != null) {
			cardConsumerRecord.setOrderSn(data.getOrderSn());
		}
		cardConsumerRecord.insert();
	}

	/**
	 * 通过会员卡号获取用户持有的会员卡信息
	 * @param cardNo 会员卡号
	 */
	public UserCardRecord getUserCardInfoByCardNo(String cardNo) {
		UserCardRecord userCard = db().select(USER_CARD.USER_ID,USER_CARD.CARD_ID,USER_CARD.CREATE_TIME,USER_CARD.FLAG,USER_CARD.CARD_NO,USER_CARD.EXPIRE_TIME,
				USER_CARD.UPDATE_TIME,USER_CARD.IS_DEFAULT,USER_CARD.MONEY,USER_CARD.SURPLUS,USER_CARD.ACTIVATION_TIME,USER_CARD.EXCHANG_SURPLUS)
		.from(USER_CARD.join(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
		.where(USER_CARD.CARD_NO.eq(cardNo))
		.fetchOne()
		.into(UserCardRecord.class);
		return userCard;
	}

    /**
     *
     * @param id
     * @return
     */
    public MemberCardPojo getMemberCardInfoById(int id){
        return db().select().from(MEMBER_CARD).where(MEMBER_CARD.ID.eq(id)).and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchOne().into(MemberCardPojo.class);
    }

    /**
     * - 获取所有的持卡会员
     * @param param
     */
	public PageResult<CardHolderVo> getAllCardHolder(CardHolderParam param) {

        PageResult<CardHolderVo> allCardHolder = cardDao.getAllCardHolder(param);
		 /** - 如果查询的状态是过期的，设置返回的flag为2过期  */
		 for(CardHolderVo item: allCardHolder.dataList) {
			 if(DateUtil.getLocalDateTime().after(item.getExpireTime())) {
				 item.setFlag(UCARD_FG_EXPIRED);
			 }
		 }
		 return allCardHolder;
	}

	/**
	 * 分页查询会员卡领取详情
	 * @param param
	 * @return
	 */
	public PageResult<CodeReceiveVo> getReceiveList(CodeReceiveParam param) {
		PageResult<CodeReceiveVo> result = cardDao.getReceiveListSql(param);
		/** 处理code 和 card_pwd */
		for(CodeReceiveVo vo: result.dataList) {
			String code = vo.getCode();
			int lengthOfCode = code.length()-4;

            if(lengthOfCode>0) {
				String tmp = IntStream.range(0, lengthOfCode).mapToObj(i -> "*").collect(Collectors.joining());
				vo.setCode(code.substring(0,2).concat(tmp).concat(code.substring(lengthOfCode+2)));
			}

            String cardPwd = vo.getCardPwd();
			int lengthOfCardPwd = cardPwd.length()-4;
			if(lengthOfCardPwd>0) {
				String tmp = IntStream.range(0, lengthOfCardPwd).mapToObj(i->"*").collect(Collectors.joining());
				vo.setCardPwd(cardPwd.substring(0,2).concat(tmp).concat(cardPwd.substring(lengthOfCardPwd+2)));
			}
		}
		return result;

    }
	/**
	 * 返回会员卡所有批次信息
	 * @param cardId
	 * @return
	 */
	public List<CardBatchVo> getCardBatchList(Integer cardId) {
		Result<?> cardBatchList = cardDao.getCardBatchList(cardId);
		if(cardBatchList != null) {
			return cardBatchList.into(CardBatchVo.class);
		}else {
			return null;
		}

	}

    /**
	 * 废除指定会员卡批次
	 * @param id
	 */
	public void deleteCardBatch(Integer id) {

        cardDao.deleteCardBatchSql(id);
	}
	/**
	 * 分页查询会员卡充值明细
	 * @param param
	 * @return
	 */
	public PageResult<ChargeVo> getChargeList(ChargeParam param) {
		 return  cardDao.getChargeList(param);
	}
	/**
	 * 分页查询会员卡消费明细
	 * @param param
	 * @return
	 */
	public PageResult<ChargeVo> getConsumeList(ChargeParam param) {

        return cardDao.getConsumeList(param);
	}
	/**
	 * 分页查询激活审核信息
	 * @param param
	 * @return
	 */
	public PageResult<ActiveAuditVo> getActivateAuditList(ActiveAuditParam param) {

        PageResult<ActiveAuditVo> results = cardVerifyService.getPageList(param);
        // deal with industry and education
		for(ActiveAuditVo activeAuditVo: results.dataList) {
			// education
			String educationStr = MemberEducationEnum.getNameByCode(activeAuditVo.getEducation());
			activeAuditVo.setEducationStr(educationStr);
			// industry
			String industry = MemberIndustryEnum.getNameByCode(activeAuditVo.getIndustryInfo());
			activeAuditVo.setIndustry(industry);
		}
		return results;

    }

	/**
	 * 审核通过
	 * @param param
	 * @return
	 */
	public void passActivateAudit(ActiveAuditParam param) {
		this.transaction(()->{
			Timestamp now = DateUtil.getSqlTimestamp();
			logger().info("申请激活会员卡通过: "+ now);
			// 更新card_examine 信息
			CardExamineRecord record = setPassData(param.getId(),now);
			cardDao.updateCardExamine(record);
			// 更新激活
			cardDao.updateUserCardByCardNo(param.getCardNo(),now);
		});
	}

    /**
	 * 审核通过数据
	 * @param id
	 * @return
	 */
	private CardExamineRecord setPassData(Integer id,Timestamp now) {
		CardExamineRecord record = new CardExamineRecord();
		record.setId(id);
		record.setPassTime(now);
		record.setStatus(VERIFIED);
		return record;
	}
	/**
	 * 审核不通过数据
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
	 * @param param
	 */
	public void rejectActivateAudit(ActiveAuditParam param) {
		CardExamineRecord record = setRejectData(param);
		cardDao.updateCardExamine(record);
	}

    /**
	 * 会员卡订单分页查询
	 * @param param
	 * @return
	 */
	public PageResult<CardConsumeVo> getCardConsumeOrderList(CardConsumeParam param) {
		PageResult<CardConsumeVo> result = cardDao.getCardConsumeOrderList(param);
		for(CardConsumeVo vo: result.dataList) {
			// 门店服务
			if(!SHORT_ZERO.equals(vo.getCount())) {
				ServiceOrderDetailVo serviceOrder = getServiceOrderInfo(vo.getOrderSn());
				if(serviceOrder != null) {
					vo.setGoodsImg(serviceOrder.getServiceImg());
					vo.setGoodsName(serviceOrder.getServiceName());
				}
			}
			// 商品兑换服务
			if(!SHORT_ZERO.equals(vo.getExchangCount())) {
				OrderGoodsVo orderGoods = getOrderGoodsInfo(vo.getOrderSn());
				if(orderGoods != null) {
					vo.setGoodsImg(orderGoods.getGoodsImg());
					vo.setGoodsName(orderGoods.getGoodsName());
				}
			}
		}

        return result;
	}

    /**
	 * 获取一个订单商品信息
	 * @param orderSn
	 * @return
	 */
	private OrderGoodsVo getOrderGoodsInfo(String orderSn) {
		List<OrderGoodsVo> results = orderGoodsDao.getGoodsInfoByOrderSn(orderSn).into(OrderGoodsVo.class);
		if(results.size()>0) {
			return results.get(0);
		}
		return null;
	}
	/**
	 * 获取门店服务订单服务
	 * @param orderSn
	 * @return
	 */
	private ServiceOrderDetailVo getServiceOrderInfo(String orderSn) {
		return serviceOrderDao.getServiceOrderDetail(orderSn);
	}


    public void generateCardCode(CardBatchParam param) {
		logger().info("正在添加添加领取码");
		this.transaction(()->{
			// 插入并获取批次Id
			Integer batchId = cardDao.createCardBatch(param);
			param.setBatchId(batchId);

			Integer groupId = cardDao.generateGroupId(batchId);
			param.setGroupId(groupId);

            if(MCARD_RA_CODE.equals(param.getReceiveAction())) {
				// 获取领取码
				List<String> codeList = generateRandomCode(param);
				cardDao.insertIntoCardReceiveCode(param,codeList);
			}else if(MCARD_RA_PWD.equals(param.getReceiveAction())) {
				// 获取卡号
				List<String> cardNoList = generateRandCardNo(param);
				// 获取密码
				List<String> pwdList = generateRandCardPwd(param);
				cardDao.insertIntoCardReceiveCode(param, cardNoList,pwdList);
			}

        });


    }


/**
	 * 生成随机的领取码
	 * @param param
	 * @return
	 */
	private List<String> generateRandomCode(CardBatchParam param) {
		Integer number = param.getNumber();
		List<String> codeList = new ArrayList<>();
		for(int i = 0;i<number;i++) {
			while(true) {
				String code = generateRandomStr(param.getCodePrefix(),param.getCodeSize());
				if(!codeList.contains(code) && !cardDao.isExistCode(code)) {
					codeList.add(code);
					break;
				}
			}
		}
		return codeList;
	}

    /**
	 * 生成随机密码
	 * @param param
	 * @return
	 */
	private List<String> generateRandCardPwd(CardBatchParam param) {
		List<String> pwdList = new ArrayList<>();
		Integer number = param.getNumber();
		for(int i = 0;i<number;i++) {
			while(true) {
				String pwd = generateRandomStr("",param.getCardPwdSize());
				if(!pwdList.contains(pwd)) {
					pwdList.add(pwd);
					break;
				}
			}
		}
		return pwdList;
	}

    /**
	 * 生成随机的卡号
	 * @param param
	 * @return
	 */
	private List<String> generateRandCardNo(CardBatchParam param){
		List<String> cardNoList = new ArrayList<>();
		Integer number = param.getNumber();
		for(int i = 0;i<number;i++) {
			while(true) {
				String cardNo = generateRandomStr(param.getCodePrefix(),param.getCodeSize());
				if(!cardNoList.contains(cardNo) && !cardDao.isExistCardNo(cardNo)) {
					cardNoList.add(cardNo);
					break;
				}
			}
		}
		return cardNoList;
	}


    /**
	 * 生成随机的字符串
	 * @param param
	 */
	private String generateRandomStr(String prefix,int length) {
		char[] arr = NUM_LETTERS.toCharArray();
		StringBuilder container = new StringBuilder(prefix);
		for(int i=0;i<length;i++) {
			if(i==0) {
				char c = arr[Util.randomInteger(0, arr.length)];
				container.append(c);
			}else {
				char c = arr[Util.randomInteger(0, 9)];
				container.append(c);
			}
		}
		return container.toString();
	}

    /**
	 * 返回会员等级-按照持有会员等级卡划分，若无持有等级会员卡，则返回null
	 * @param user_id
	 * @return
	 */
	public String getUserGrade(Integer userId) {
		return userCardService.getUserGrade(userId);
	}

    /**
	 * 获取会员卡id列表根据会员卡类型
	 * @param type {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE }
	 */
	public List<Integer> getCardIdByType(Byte type) {
		 return  cardDao.getCardIdByType(type);
	}

    public boolean isNormalCard(Byte cardType) {
		return MCARD_TP_NORMAL.equals(cardType);
	}

    public boolean isLimitCard(Byte cardType) {
		return MCARD_TP_LIMIT.equals(cardType);
	}

	public boolean isGradeCard(Byte cardType) {
		return MCARD_TP_GRADE.equals(cardType);
	}

    /**
     * Gets single field by condition.获取购物送积分策略json数据
     *
     * @param cardNo the card no
     * @return the single field by condition
     */
    public String getSendScoreStrategy(String cardNo) {
        return db().select(MEMBER_CARD.BUY_SCORE).from(USER_CARD)
            .leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID))
            .where(USER_CARD.CARD_NO.eq(cardNo))
            .fetchOneInto(String.class);
    }
}