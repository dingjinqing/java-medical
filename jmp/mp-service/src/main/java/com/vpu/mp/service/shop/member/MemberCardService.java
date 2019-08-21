package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ACTIVE_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ALL_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ALL_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BG_COLOR_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BG_IMG_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUTTON_ON;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_CRASH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUY_BY_SCORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CHECKED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DAY_DATE_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DISCOUNT_ALL_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DISCOUNT_PART_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DURING_TIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FOREVER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.GET_DIRECTLY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LIMIT_NUM_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MONTH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MONTH_DATE_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NEED_BUY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NEED_CODE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NONE_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NORMAL_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PART_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PART_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PROHIBITED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.WEEK;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.WEEK_DATE_TYPE;
import static org.jooq.impl.DSL.count;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.validation.Valid;

import org.jooq.InsertValuesStep7;
import org.jooq.Result;
import org.jooq.SelectSeekStep1;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateWhereStep;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.account.AddMemberCardParam;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;
import com.vpu.mp.service.pojo.shop.member.account.MemberCardVo;
import com.vpu.mp.service.pojo.shop.member.card.BaseCardVo;
import com.vpu.mp.service.pojo.shop.member.card.CardIdParam;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.pojo.shop.member.card.LimitNumCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.LimitNumCardVo;
import com.vpu.mp.service.pojo.shop.member.card.NormalCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.NormalCardVo;
import com.vpu.mp.service.pojo.shop.member.card.PowerCardParam;
import com.vpu.mp.service.pojo.shop.member.card.RankCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.RankCardVo;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.card.SimpleMemberCardVo;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月30日
 * @Description:
 */
@Service
public class MemberCardService extends ShopBaseService {
	Logger logger = logger();

	/**
	 * 添加会员卡
	 */
	public JsonResultCode addMemberCard(CardParam card) {
		/** 处理会员卡的基本信息 */
		MemberCardRecord cardRecord = dealWithCard(card);

		/** 插入数据库 */
		insertIntoMemberCard(cardRecord);
		return null;
	}

	/**
	 * 更新会员卡
	 */
	public void updateMemberCard(CardParam card) {
		/** 处理会员卡的基本信息 */
		MemberCardRecord cardRecord = dealWithCard(card);

		// TODO 如果Record的属性值设置为null,会跟新数据库信息为null吗
		/** 更新数据 */
		updateMemberCardById(cardRecord, card.getId());
	}

	/**
	 * 处理会员卡的基本信息
	 */
	private MemberCardRecord dealWithCard(CardParam card) {
		logger.info("正在处理会卡信息");
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
		setLimitAndRankCard(card, cardRecord);

		/** 处理普通会员卡 */
		if (NORMAL_TYPE.equals(cardType)) {
			dealWithNormalCard(card, cardRecord);
		} else if (LIMIT_NUM_TYPE.equals(cardType)) {
			/** 处理限次会员卡 */
			dealWithLimitNumCard(card, cardRecord);
		} else if (RANK_TYPE.equals(cardType)) {
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
				cardRecord.setDiscountIsAll(DISCOUNT_PART_GOODS);
				/** 处理需要折扣的部分商品,平台,商家 */
				updateDiscountPartGoods(card, cardRecord);

			}

			/** 2. 会员专享商品 */
			if (BUTTON_ON.equals(card.getPowerPayOwnGood())) {
				// TODO 处理允许会员专享的商品，商家，分类，平台等

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
			}

			if (!flag && RANK_TYPE.equals(cardType)) {
				/** 必须选择一项会员权益 针对等级卡 */
				throw new IllegalArgumentException("必须选择一项会员权益 针对等级卡");
				// JsonResultCode.CODE_MEMBER_CARD_RIGHTS_EMPTY;
			}

		}
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
	 * @param goodsId
	 * @param cardIdList
	 */
	public void batchUpdateGoods(List<Integer> goodsId,List<Integer> cardIdList) {
		CardParam card = new CardParam();
		card.setGoodsId((Integer[]) goodsId.toArray());
		batchUpdateGoods(card,cardIdList);
	}
	
	
	
	/**
	 * 根据会员卡id批量更新，专属商品，折扣指定商品的： 商品，商家，平台，品牌
	 * @param card 会员卡参数，主要存户商品id,商家分类id，平台分类id,品牌id
	 * @param cardIdList 需要批量更新的会员卡id
	 */
	public void batchUpdateGoods(CardParam card,List<Integer> cardIdList) {
		UpdateSetFirstStep<MemberCardRecord> update = db().update(MEMBER_CARD);
		
		/** 折扣指定商品 */
		/** 商品 */
		if(card.getGoodsId() != null) {
			update.set(MEMBER_CARD.DISCOUNT_GOODS_ID, Util.listToString(Arrays.asList(card.getGoodsId())));
		}
		
		/** 平台分类 */
		if(card.getPlatformCategoryIds()!=null) {
			update.set(MEMBER_CARD.DISCOUNT_CAT_ID,Util.listToString(Arrays.asList(card.getPlatformCategoryIds())));
		}
		
		/** 商家分类  */
		if(card.getShopCategoryIds()!=null) {
			update.set(MEMBER_CARD.DISCOUNT_SORT_ID,Util.listToString(Arrays.asList(card.getShopCategoryIds())));
		}
		
		//TODO品牌
		/** 会员专享商品 */
		
		
		((UpdateWhereStep<MemberCardRecord>) update).where(MEMBER_CARD.ID.in(cardIdList));
		
	}
	
	
	
	

	/**
	 * 更新折扣指定商品信息： 商品，商家，平台
	 * 
	 * @param card
	 * @param cardRecord
	 */
	private void updateDiscountPartGoods(CardParam card, MemberCardRecord cardRecord) {
		logger().info("更新折扣指定商品信息： 商品，商家，平台");
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
		cardRecord.setExpireType(FOREVER);

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
	private void dealWithLimitNumCard(CardParam card, MemberCardRecord cardRecord) {

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
		logger.info("成功更新： " + num + " 行数据");
	}

	/**
	 * 分页查询等级会员卡
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<RankCardVo> getRankCardList(SearchCardParam param) {
		/** 构建sql语句 */
		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.CARD_TYPE.equal(RANK_TYPE)).and(MEMBER_CARD.DEL_FLAG.equal(DELETE_NO))
				.orderBy(MEMBER_CARD.ID.desc());

		PageResult<RankCardVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),
				RankCardVo.class);
		/** 执行转换 */
		pageResult.dataList.stream().forEach(vo -> vo.changeJsonCfg());
		return pageResult;
	}

	/**
	 * 分页查询限次会员卡
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<LimitNumCardVo> getLimitCardList(SearchCardParam param) {
		/** 构建select语句 */

		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.CARD_TYPE.equal(LIMIT_NUM_TYPE)).and(MEMBER_CARD.DEL_FLAG.equal(DELETE_NO))
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
		/**
		 * select card_name from b2c_member_card where card_type=0 and is_delete=0 order
		 * by id desc;
		 * 
		 */
		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.CARD_TYPE.equal(NORMAL_TYPE)).and(MEMBER_CARD.DEL_FLAG.equal(DELETE_NO))
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
		/** 处理普通会员卡 */
		if (NORMAL_TYPE.equals(cardType)) {
			logger.info("正在分页查询普通会员卡");
			return getNormalCardList(param);
		} else if (LIMIT_NUM_TYPE.equals(cardType)) {
			logger.info("正在分页查询限次会员卡");
			return getLimitCardList(param);
		} else if (RANK_TYPE.equals(cardType)) {
			logger.info("正在分页查询等级会员卡");
			return getRankCardList(param);
		}
		return null;
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
		logger.info("设置会员卡状态成功，受影响行： " + result);
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
		int result = db().update(MEMBER_CARD).set(MEMBER_CARD.DEL_FLAG, DELETE_YES)
				.where(MEMBER_CARD.ID.eq(param.getId())).execute();
		logger.info("删除会员卡成功，受影响行： " + result);
	}

	/**
	 * 根据ID获取该会员卡的详细信息
	 * 
	 * @param param
	 * @return
	 */
	public BaseCardVo getCardById(CardIdParam param) {
		MemberCardRecord record = db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.ID.eq(param.getId())).fetchOne();
		/** 会员卡类型 */
		Byte cardType = record.getCardType();
		if (NORMAL_TYPE.equals(cardType)) {
			logger.info("查询出普通会员卡");
			NormalCardToVo card = record.into(NormalCardToVo.class);
			/** 执行策略 */
			card.changeJsonCfg();
			return card;
		} else if (LIMIT_NUM_TYPE.equals(cardType)) {
			logger.info("查询出限次会员卡");
			/** 执行策略 */
			LimitNumCardToVo card = record.into(LimitNumCardToVo.class);
			/** 查询已经被领取的数量 */
			int hasSend = 0;
			hasSend = db().fetchCount(USER_CARD, USER_CARD.CARD_ID.eq(param.getId()));
			card.setHasSend(hasSend);
			card.changeJsonCfg();
			return card;
		} else if (RANK_TYPE.equals(cardType)) {
			logger.info("查询出等级会员卡");
			RankCardToVo card = record.into(RankCardToVo.class);
			card.changeJsonCfg();
			return card;
		}
		return null;
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
	 * 会员卡列表弹窗
	 * 
	 * @return
	 */
	public MemberCardVo getAllCardList() {

		Result<MemberCardRecord> cardRecords = selectAllMemberCard();
		MemberCardVo vo = new MemberCardVo();
		/** 分类 普通 | 限次 | 等级 会员卡 */
		logger.info("正在分类处理");
		for (MemberCardRecord card : cardRecords) {
			Byte cardType = card.getCardType();
			MemberCard cardVo = card.into(MemberCard.class);
			/** 执行策略 */
			cardVo.changeJsonCfg();

			if (NORMAL_TYPE.equals(cardType)) {
				vo.getNormalCard().add(cardVo);
			} else if (LIMIT_NUM_TYPE.equals(cardType)) {
				vo.getLimitNumCard().add(cardVo);
			} else if (RANK_TYPE.equals(cardType)) {
				vo.getRankCard().add(cardVo);
			}

		}

		return vo;
	}

	/**
	 * 查询所有的会员卡
	 * 
	 * @return
	 */
	private Result<MemberCardRecord> selectAllMemberCard() {
		logger.info("查询所有会员卡");
		Result<MemberCardRecord> cardRecords = db().selectFrom(MEMBER_CARD).fetch();
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
		Result<MemberCardRecord> memberCardList = db().selectFrom(MEMBER_CARD)
				.where(MEMBER_CARD.ID.in(param.getCardIdList())).fetch();
		logger.info("一共查询到: " + memberCardList.size() + " 张会员卡");

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expireTime = null;
		/** 过期时间-多少日内 */
		for (MemberCardRecord memberCard : memberCardList) {
			if (DURING_TIME.equals(memberCard.getExpireType())) {
				/** 计算过期时间 */
				Byte dateType = memberCard.getDateType();
				/** 领取之日起n */
				Integer receiveDay = memberCard.getReceiveDay();
				if (DAY_DATE_TYPE.equals(dateType)) {
					expireTime = now.plusDays(receiveDay);
				} else if (WEEK_DATE_TYPE.equals(dateType)) {
					expireTime = now.plusDays(WEEK * receiveDay);
				} else if (MONTH_DATE_TYPE.equals(dateType)) {
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
				MemberCardRecord memberCard = memberCardList.get(j);
				insert.values(userIdList.get(i), cardIdList.get(j), cardNoList.poll(), memberCard.getEndTime(),
						memberCard.getCount(), Timestamp.valueOf(now), memberCard.getExchangCount());
			}
		}

		int execute = insert.execute();
		logger.info("成功添加： " + execute + " 行记录");

		/** add record */
		// List<String> userNameList =
		// db().select(USER.USERNAME).from(USER).where(USER.USER_ID.in(userIdList)).fetch().into(String.class);
		Map<Integer, String> userNameMap = db().select(USER.USER_ID, USER.USERNAME).from(USER)
				.where(USER.USER_ID.in(userIdList)).fetch().intoMap(USER.USER_ID, USER.USERNAME);
		List<String> tmpData = new ArrayList<>();
		String messageFormat = RecordContentTemplate.MEMBER_CARD_SEND.getMessage();
		/** generate template message */
		for (int i = 0; i < sizeOfUserId; i++) {
			for (int j = 0; j < sizeOfCardId; j++) {
				tmpData.add(String.format(messageFormat, userIdList.get(i), userNameMap.get(userIdList.get(i)),
						memberCardList.get(j).getCardName()));
			}
		}

		saas().getShopApp(getShopId()).record.insertRecord(
				Arrays.asList(new Integer[] { RecordContentTemplate.MEMBER_CARD_SEND.code }),
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
			cardNo.append(String.format("%04d", cardId).substring(0, 4));
			cardNo.append(Util.randomInteger(1000, 10000));

			/** 确保数据库会员卡号具有唯一性 */
			int count = db().fetchCount(USER_CARD, USER_CARD.CARD_NO.eq(cardNo.toString()));
			if (count == 0) {
				break;
			}
			/** clear string buffer */
			cardNo.setLength(0);
		}

		logger.info("cardNo: " + cardNo.toString());
		return cardNo.toString();
	}
}