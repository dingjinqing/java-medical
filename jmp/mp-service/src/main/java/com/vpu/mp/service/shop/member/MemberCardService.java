package com.vpu.mp.service.shop.member;


import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
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
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DISCOUNT_ALL_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DISCOUNT_PART_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DURING_TIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FOREVER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.GET_DIRECTLY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LIMIT_NUM_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NEED_BUY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NEED_CODE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NONE_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NORMAL_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PART_GOODS;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PART_SHOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.PROHIBITED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE;
import static org.jooq.impl.DSL.count;

import java.util.Map;

import javax.validation.Valid;

import org.jooq.SelectSeekStep1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
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
/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月30日
 * @Description:
 */
@Service
public class MemberCardService extends ShopBaseService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
		
		/** 更新数据 */
		updateMemberCardById(cardRecord,card.getId());
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
			}else {
				//准备设置为空
			}

			if (!flag && RANK_TYPE.equals(cardType)) {
				/** 必须选择一项会员权益 针对等级卡 */
				throw new IllegalArgumentException("必须选择一项会员权益 针对等级卡");
				//JsonResultCode.CODE_MEMBER_CARD_RIGHTS_EMPTY;
			}

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
	 * @param cardRecord
	 */
	private void updateMemberCardById(MemberCardRecord cardRecord,Integer id) {
		int num = db().executeUpdate(cardRecord, MEMBER_CARD.ID.eq(id));
		logger.info("成功更新： "+ num + " 行数据");
	}
	
	
	/**
	 * 分页查询等级会员卡
	 * @param param
	 * @return
	 */
	public PageResult<RankCardVo> getRankCardList(SearchCardParam param) {
		/** 构建sql语句 */
		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
									.where(MEMBER_CARD.CARD_TYPE.equal(RANK_TYPE))
									.and(MEMBER_CARD.DEL_FLAG.equal(DELETE_NO))
									.orderBy(MEMBER_CARD.ID.desc());
		
		PageResult<RankCardVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),RankCardVo.class);
		/** 执行转换 */
		pageResult.dataList.stream().forEach(vo->vo.changeJsonCfg());
		return pageResult;
	}

	/**
	 * 分页查询限次会员卡
	 * @param param
	 * @return
	 */
	public PageResult<LimitNumCardVo> getLimitCardList(SearchCardParam param) {
		/** 构建select语句 */
	
		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
			.where(MEMBER_CARD.CARD_TYPE.equal(LIMIT_NUM_TYPE))
			.and(MEMBER_CARD.DEL_FLAG.equal(DELETE_NO))
			.orderBy(MEMBER_CARD.ID.desc());
			
		PageResult<LimitNumCardVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),LimitNumCardVo.class);
		/** 查询领取次数 */
		Map<Integer, Integer> intoMap = db().select(USER_CARD.CARD_ID,count())
											.from(USER_CARD)
											.groupBy(USER_CARD.CARD_ID)
											.fetch()
											.intoMap(USER_CARD.CARD_ID, count());
		
		
		for(LimitNumCardVo vo: pageResult.dataList) {
			/** 设置未领取值 */
			vo.setHasSend(intoMap.get(vo.getId())==null?0:intoMap.get(vo.getId()));
			vo.changeJsonCfg();
		}
		return pageResult;
	}

	/**
	 * 分页查询普通会员卡
	 * @param param
	 */
	public PageResult<NormalCardVo> getNormalCardList(SearchCardParam param) {
		/**
		 * select card_name from b2c_member_card where card_type=0 and is_delete=0 order by id desc;
		 * 
		 */
		SelectSeekStep1<MemberCardRecord, Integer> select = db().selectFrom(MEMBER_CARD)
			.where(MEMBER_CARD.CARD_TYPE.equal(NORMAL_TYPE))
			.and(MEMBER_CARD.DEL_FLAG.equal(DELETE_NO))
			.orderBy(MEMBER_CARD.ID.desc());
		
		PageResult<NormalCardVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), NormalCardVo.class);
		/** 将json配置文件转化成合适的数据给前端 */
		for(NormalCardVo vo: pageResult.dataList) {
			vo.changeJsonCfg();
		}
		
		return pageResult;
	}

	/**
	 * 获取会员卡列表
	 * @param param
	 * @return
	 */
	public PageResult<? extends BaseCardVo> getCardList(SearchCardParam param) {

		Byte cardType = param.getCardType();
		/** 处理普通会员卡 */
		if(NORMAL_TYPE.equals(cardType)) {
			logger.info("正在分页查询普通会员卡");
			return getNormalCardList(param);
		}else if(LIMIT_NUM_TYPE.equals(cardType)) {
			logger.info("正在分页查询限次会员卡");
			return getLimitCardList(param);
		}else if(RANK_TYPE.equals(cardType)) {
			logger.info("正在分页查询等级会员卡");
			return getRankCardList(param);
		}
		return null;
	}

	/**
	 * 设置会员卡启动或禁止状态
	 * @param param
	 */
	public void powerCard(PowerCardParam param) {
		/**
		 * UPDATE b2c_member_card set flag = 51 where id=825;
		 */
		/** SQL语句执行 */
		int result = db().update(MEMBER_CARD).set(MEMBER_CARD.FLAG, param.getFlag()).where(MEMBER_CARD.ID.eq(param.getId())).execute();
		logger.info("设置会员卡状态成功，受影响行： "+result);
	}

	/**
	 * 删除会员卡
	 * @param paramH
	 */
	public void deleteCard(@Valid CardIdParam param) {
		/**
		 *  update `b2c_member_card` set `is_delete` = '1' where `id` = '819'  
		 */
		/** 假删除会员卡 */
		int result = db().update(MEMBER_CARD).set(MEMBER_CARD.DEL_FLAG, DELETE_YES).where(MEMBER_CARD.ID.eq(param.getId())).execute();
		logger.info("删除会员卡成功，受影响行： "+result);
	}

	/**
	 * 根据ID获取该会员卡的详细信息
	 * @param param
	 * @return
	 */
	public BaseCardVo getCardById(CardIdParam param) {
		MemberCardRecord record = db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.ID.eq(param.getId())).fetchOne();
		/** 会员卡类型 */
		Byte cardType = record.getCardType();
		if(NORMAL_TYPE.equals(cardType)) {
			logger.info("查询出普通会员卡");
			NormalCardToVo card = record.into(NormalCardToVo.class);
			/**执行策略 */
			card.changeJsonCfg();
			return card;
		}else if(LIMIT_NUM_TYPE.equals(cardType)) {
			logger.info("查询出限次会员卡");
			/** 执行策略 */
			LimitNumCardToVo card = record.into(LimitNumCardToVo.class);
			/** 查询已经被领取的数量 */
			int hasSend  = 0;
			hasSend = db().fetchCount(USER_CARD,USER_CARD.CARD_ID.eq(param.getId()));
			card.setHasSend(hasSend);
			card.changeJsonCfg();
			return card;
		}else if(RANK_TYPE.equals(cardType)){
			logger.info("查询出等级会员卡");
			RankCardToVo card = record.into(RankCardToVo.class);
			card.changeJsonCfg();
			return card;
		}
		return null;
	}

}