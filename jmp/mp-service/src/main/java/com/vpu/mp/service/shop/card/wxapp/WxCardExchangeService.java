package com.vpu.mp.service.shop.card.wxapp;

import static com.vpu.mp.db.shop.Tables.CHECKED_GOODS_CART;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.CheckedGoodsCartRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.member.card.create.CardExchangGoods;
import com.vpu.mp.service.pojo.shop.member.card.create.CardExchangGoods.GoodsCfg;
import com.vpu.mp.service.pojo.shop.member.card.create.CardExchangGoods.TimeType;
import com.vpu.mp.service.pojo.shop.member.card.dao.CardFullDetail;
import com.vpu.mp.service.pojo.shop.member.card.show.CardUseCondition;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.card.param.CardAddExchangeGoodsParam;
import com.vpu.mp.service.pojo.wxapp.card.param.CardExchaneGoodsJudgeParam;
import com.vpu.mp.service.pojo.wxapp.card.param.CardExchangeGoodsParam;
import com.vpu.mp.service.pojo.wxapp.card.vo.CardCheckedGoodsVo;
import com.vpu.mp.service.pojo.wxapp.card.vo.CardExchangTipVo;
import com.vpu.mp.service.pojo.wxapp.card.vo.CardExchangeGoodsVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchContentVo;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchMpOuterParam;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchMpParam;
import com.vpu.mp.service.pojo.wxapp.user.UserCheckedGoodsParam;
import com.vpu.mp.service.pojo.wxapp.user.UserCheckedGoodsVo;
import com.vpu.mp.service.shop.card.CardExchangService;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.mp.GoodsSearchMpService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.card.LimitCardOpt;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserCheckedGoodsService;
/**
 * 	会员卡兑换商品服务
 * @author 黄壮壮
 *
 */
@Service
@Primary
public class WxCardExchangeService extends ShopBaseService {
	@Autowired public MemberCardService mCardSvc;
	@Autowired private ShopCommonConfigService shopCommonCfgSvc;
	@Autowired private GoodsService goodsSvc;
	@Autowired public UserCheckedGoodsService userCheckedGoodsSvc;
	@Autowired private DomainConfig domainConfig;
    @Autowired public UserCardService userCard;
    @Autowired private CardExchangService cardExchangSvc;
    @Autowired private LimitCardOpt limitCardOpt;
    @Autowired private GoodsSearchMpService goodsSearchMpSvc;

    /**
     * 退限次卡次数
     * @param order
     * @param returnGoods
     */
    public void limitCardRefundTimes(OrderInfoVo order, List<OrderReturnGoodsVo> returnGoods) throws MpException {
        returnGoods = returnGoods.stream().filter(x->x.getIsGift() != null && x.getIsGift() == 0).collect(Collectors.toList());
        if(Lists.newArrayList(OrderInfoService.orderTypeToByte(order.getGoodsType())).contains(BaseConstant.ACTIVITY_TYPE_EXCHANG_ORDER) && CollectionUtils.isNotEmpty(returnGoods)) {
            CardConsumpData cardConsumpData = new CardConsumpData();
            cardConsumpData.setUserId(order.getUserId());
            cardConsumpData.setCardId(order.getActivityId());
            cardConsumpData.setCardNo(order.getCardNo());
            cardConsumpData.setReasonId(RemarkTemplate.ORDER_LIMIT_EXCHGE_GOODS.code);
            cardConsumpData.setType(CardConstant.MCARD_TP_LIMIT);
            cardConsumpData.setOrderSn(order.getOrderSn());
            cardConsumpData.setExchangeCount(returnGoods.stream().map(OrderReturnGoodsVo::getGoodsNumber).reduce(0, Integer::sum).shortValue());
            cardConsumpData.setPayment("");
            mCardSvc.updateMemberCardExchangeSurplus(cardConsumpData);
        }
    }
	/**
	 * 	分页查询兑换商品列表
	 * @param param
	 */
	public CardExchangeGoodsVo changeGoodsList(CardExchangeGoodsParam param) {
		logger().info("兑换商品列表");
		CardExchangeGoodsVo vo = new CardExchangeGoodsVo();
		if(StringUtils.isBlank(param.getCardNo())) {
			return vo;
		}
		CardFullDetail cardDetail = mCardSvc.getCardDetailByNo(param.getCardNo());
		if(cardDetail!= null) {
			UserCardRecord userCard = cardDetail.getUserCard();
			MemberCardRecord memberCard = cardDetail.getMemberCard();
			
			if(userCard.getActivationTime() == null 
						|| !CardUtil.isLimitCard(memberCard.getCardType()) 
						|| !CardUtil.canExchangGoods(memberCard.getIsExchang())) {
				logger().info("该会员卡不可兑换");
				return vo;
			}
			
			if(userCard.getExchangSurplus()<=0) {
				logger().info("该会员卡已无兑换次数");
				return vo;
			}
			//	商品查询
			GoodsSearchMpParam searchParam = new GoodsSearchMpParam();
			searchParam.setKeyWords(param.getSearch());
			searchParam.setPageFrom(GoodsSearchMpParam.PAGE_FROM_CARD_EXCHANGE_GOODS);
			GoodsSearchMpOuterParam outerPageParam = new GoodsSearchMpOuterParam();
			outerPageParam.setCardNo(param.getCardNo());
			searchParam.setOuterPageParam(outerPageParam);
			GoodsSearchContentVo searchGoodsGate = goodsSearchMpSvc.searchGoodsGate(searchParam);
			PageResult<? extends GoodsListMpVo> pageResult = searchGoodsGate.getPageResult();
			List<? extends GoodsListMpVo> dataList = pageResult.dataList;
			//	为每个商品设置限制兑换的次数
			for(int i=0;i<pageResult.dataList.size();i++) {
				GoodsListMpVo goods = pageResult.dataList.get(i);
				goods.getGoodsId();
				Integer limitTimes = getCardAllowExchangGoodsTimes(goods.getGoodsId(),memberCard);
				goods.setLimitExchangNum(limitTimes);
			}
			
			vo.setGoodsPageResult(pageResult);
			//  兑换商品数量配置，时间限制
			CardExchangTipVo cardExchangTip = getCardExchangTip(param.getCardNo(),memberCard);
			vo.setCardExchangeTip(cardExchangTip);
			//	获取用户已勾选的
			Integer totalNumber = userCheckedGoodsSvc.getUserCheckedCount(param.getUserId(),param.getCardNo());
			vo.setTotalNumber(totalNumber);
			
			Map<String, Object> cardInfo = userCard.intoMap();
			vo.setCardInfo(cardInfo);
		}
		return vo;
	}
	
	
	
	/**
	 * 判断是否可以兑换
	 * @throws MpException 
	 */
	public boolean judgeCardGoods(CardExchaneGoodsJudgeParam param) throws MpException {
		Map<Integer,Integer> goodsMap = new HashMap<>();
		goodsMap.put(param.getGoodsId(), param.getGoodsNumber());
		return judgeExchangGoodsAvailable(goodsMap,param.getCardNo(),Boolean.TRUE);
	}
	
	/**
	 * 	添加活动商品
	 * @throws MpException 
	 */
	public void addExchangeGoods(CardAddExchangeGoodsParam param) throws MpException {
		logger().info("兑换商品加购");
		Optional<GoodsRecord> record = goodsSvc.getGoodsById(param.getGoodsId());
		GoodsRecord goodsRecord = record.get();
		if(goodsRecord == null) {
			return;
		}
		UserCheckedGoodsParam checkedParam = new UserCheckedGoodsParam();
		checkedParam.setAction(CardConstant.MCARD_TP_LIMIT);
		checkedParam.setUserId(param.getUserId());
		checkedParam.setProductId(param.getProductId());
		checkedParam.setIdentityId(param.getCardNo());
		checkedParam.setGoodsId(param.getGoodsId());
		CheckedGoodsCartRecord userCheckedGoods = userCheckedGoodsSvc.getUserCheckedGoods(checkedParam);
		
		if(NumberUtils.BYTE_ONE.equals(param.getSource())) {
			//	加购更新商品数量转化为添加商品数量
			param.setPrdNumber(param.getPrdNumber()-userCheckedGoods.getGoodsNumber());
		}
		Map<Integer,Integer> goodsMap = new HashMap<>();
		goodsMap.put(param.getGoodsId(), param.getPrdNumber());
		boolean judegeRes = judgeExchangGoodsAvailable(goodsMap, param.getCardNo(),Boolean.TRUE);
		if(judegeRes) {
			logger().info("可以加购");
			
			if(userCheckedGoods == null) {
				// 添加
				logger().info("添加新商品");
				CheckedGoodsCartRecord newRecord = db().newRecord(CHECKED_GOODS_CART);
				newRecord.setUserId(param.getUserId());
				newRecord.setGoodsId(param.getGoodsId());
				newRecord.setProductId(param.getProductId());
				newRecord.setGoodsNumber(param.getPrdNumber());
				newRecord.setAction(CardConstant.MCARD_TP_LIMIT);
				newRecord.setIdentityId(param.getCardNo());
				newRecord.insert();
			}else {
				// 更新
				userCheckedGoods.setGoodsNumber(param.getPrdNumber()+userCheckedGoods.getGoodsNumber());
				if(userCheckedGoods.getGoodsNumber()<=0) {
					//	直接删除改加购商品
					UserCheckedGoodsParam param2 = new UserCheckedGoodsParam();
					param2.setIdentityId(param.getCardNo());
					param2.setGoodsId(param.getGoodsId());
					param2.setProductId(param.getProductId());
					param2.setUserId(param.getUserId());
					userCheckedGoodsSvc.removeChoosedGoods(param2);
				}else {
					db().executeUpdate(userCheckedGoods);
				}
			}
		}
	}


	/**
	 * 已选商品列表
	 * @return 
	 */
	public CardCheckedGoodsVo changeCheckedGoodsList(UserCheckedGoodsParam param) {
		param.setAction(CardConstant.MCARD_TP_LIMIT);
		CardCheckedGoodsVo vo = new CardCheckedGoodsVo();
		PageResult<UserCheckedGoodsVo> usercheckedList = userCheckedGoodsSvc.getUsercheckedList(param);
		
		// 处理图片
		for(UserCheckedGoodsVo goods: usercheckedList.dataList) {
			if(!StringUtils.isBlank(goods.getPrdImg())) {
				goods.setPrdImg(domainConfig.imageUrl(goods.getPrdImg()));
			}
			if(!StringUtils.isBlank(goods.getGoodsImg())) {
				goods.setGoodsImg(domainConfig.imageUrl(goods.getGoodsImg()));
			}
		}
		
		vo.setGoodsList(usercheckedList);
		//		获取用户已勾选的
		Integer totalNumber = userCheckedGoodsSvc.getUserCheckedCount(param.getUserId(),param.getIdentityId());
		vo.setTotalNumber(totalNumber);
		return vo;
	}

	/**
	 * 	删除兑换商品
	 * @param param
	 */
	public void removeChoosedGoods(UserCheckedGoodsParam param) {
		userCheckedGoodsSvc.removeChoosedGoods(param);
	}
	
	/**
	 * 获取该卡指定时间范围兑换次数
	 * @param goodsId 商品Id goodsId为null表示兑换的全部商品
	 * @param cardNo 卡号Id
	 * @param timeType 时间范围类型
	 * @return Integer 次数
	 */
	private Integer getExchangGoodsTimesDuringPeriod(Integer goodsId,String cardNo,TimeType timeType) {
		logger().info("获取指定时间范围兑换某个商品的次数");
		Timestamp[] times = cardExchangSvc.getIntervalTime(timeType.val);
		Condition condition = DSL.noCondition();
		
		condition = condition
				.and(ORDER_GOODS.IS_GIFT.eq(OrderConstant.IS_GIFT_N))
				.and(ORDER_INFO.EXCHANG.eq(CardConstant.MCARD_TP_LIMIT));	
		
		if(goodsId != null) {
			condition = condition.and(ORDER_GOODS.GOODS_ID.eq(goodsId));
		}
		
		if(null != times) {
			//	有效期范围内
			condition = condition
					.and(ORDER_INFO.CREATE_TIME.ge(times[0]))
					.and(ORDER_INFO.CREATE_TIME.le(times[1]));
		}
		 return db()
			.select(DSL.sum(ORDER_GOODS.GOODS_NUMBER))
			.from(ORDER_GOODS)
			.innerJoin(ORDER_INFO).on(ORDER_GOODS.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
			.where(condition)
			.fetchOne(0, int.class);
	}


	/**
	 * 获取该卡兑换某个商品的次数
	 * @param goodsId 商品Id
	 * @param cardNo 卡号Id
	 * @return Integer 次数
	 */
	private Integer getExchangGoodsAllTimes(Integer goodsId,String cardNo) {
		return getExchangGoodsTimesDuringPeriod(goodsId,cardNo,CardExchangGoods.TimeType.NO_LIMIT);
	}

	/**
	 * 判断该卡能够兑换该商品
	 * @param goodsMap 兑换商品ID: key->goodsId,商品数量：value->goodsNum
	 * @param cardNo 会员卡卡号
	 * @param considerUserCheckedGoods 是否考虑已加购商品 true是，考虑；false否，不考虑
	 * @throws MpException 兑换商品异常 表示
	 * @return true 表示可兑换
	 */
	public boolean judgeExchangGoodsAvailable(Map<Integer,Integer> goodsMap,String cardNo,Boolean considerUserCheckedGoods) throws MpException {
		CardFullDetail cardDetail = mCardSvc.getCardDetailByNo(cardNo);
		MemberCardRecord memberCard = cardDetail.getMemberCard();
		UserCardRecord userCard = cardDetail.getUserCard();
		Integer userCheckedCount = userCheckedGoodsSvc.getUserCheckedCount(userCard.getUserId(),cardNo);
		//	1-检测该限次卡是否用
		CardUseCondition judegeCardUsable = limitCardOpt.judegeCardUsable(userCard,memberCard);
		
		if(!judegeCardUsable.isUsable()) {
			throw new MpException(judegeCardUsable.getErrorCode());
		}
		
		for(Map.Entry<Integer, Integer> entry: goodsMap.entrySet()) {
			Integer goodsId = entry.getKey();
			Integer goodsNum = entry.getValue();
			logger().info("判断该卡能够兑换该商品: "+goodsId+" "+goodsNum);
			if(goodsNum==null) {
				//	默认兑换商品数量为1
				goodsNum = 1;
			}
			//	2-检测该兑换商品是否满足该限次卡的设定的配置
			//	已经兑换商品的总次数
			Integer exchangGoodsAllTimes = getExchangGoodsAllTimes(goodsId,cardNo);
			//	获取会员卡配置的该商品允许兑换的次数
			Integer allowExchangTimes = getCardAllowExchangGoodsTimes(goodsId, memberCard);
			if(allowExchangTimes==null) {
				// 该会员卡不支持兑换该商品
				logger().info("该会员卡不支持兑换该商品:"+goodsId);
				throw new MpException(JsonResultCode.MSG_CARD_NOT_SUPPORT_GOODS);
			}
			
			if(CardConstant.INFINITE_EXCHANGE.equals(allowExchangTimes)) {
				logger().info("该商品能够兑换不限制次数");
			}else {
				logger().info("start: 检测该兑换商品是否满足该限次卡的设定的配置");
				if(considerUserCheckedGoods) {
					Integer remain = allowExchangTimes-exchangGoodsAllTimes;
					//	该卡本商品剩余可兑换{remain}件，已加购{userCheckedCount}件
					if(goodsNum+userCheckedCount>remain) {
						logger().info("该卡本商品剩余可兑换"+remain+"件，已加购"+userCheckedCount+"件");
						throw new MpException(JsonResultCode.MSG_CARD_ALLOW_TIME_OVER_ALIAS,"",String.valueOf(remain),String.valueOf(userCheckedCount));
					}
				}else {
					if((exchangGoodsAllTimes+goodsNum)>allowExchangTimes) {
						//	本卡允许兑换该商品的次数{allowExchangTimes},您已经兑换了{exchangGoodsAllTimes}次
						logger().info("本卡允许兑换该商品的次数"+allowExchangTimes+", 您已经兑换了"+exchangGoodsAllTimes);
						throw new MpException(JsonResultCode.MSG_CARD_ALLOW_TIME_OVER,"",String.valueOf(allowExchangTimes),String.valueOf(exchangGoodsAllTimes));
					}
				}
				logger().info("end: 检测该兑换商品是否满足该限次卡的设定的配置");
			}
			//	3-会员卡配置的兑换商品兑换总次数次数是否可用
			//	已经加购的商品
			logger().info("start: 会员卡配置的兑换商品兑换总次数次数是否可用");
			Integer exchangSurplus = userCard.getExchangSurplus();
			if(considerUserCheckedGoods) {
				if(exchangSurplus<=0 || (userCheckedCount+goodsNum)>exchangSurplus) {
					//	本卡剩余可兑换{exchangSurplus}件，已加购{userCheckedCount}件
					logger().info("本卡剩余可兑换"+exchangSurplus+"件,已加购"+userCheckedCount);
					throw new MpException(JsonResultCode.MSG_CARD_ADD_TIMES_OVER,"",String.valueOf(exchangSurplus),String.valueOf(userCheckedCount));
				}
			}else {
				if(exchangSurplus<=0 || goodsNum>exchangSurplus) {
					// 本卡剩余可兑换{exchangSurplus}件
					logger().info("本卡剩余可兑换"+exchangSurplus+"件");
					throw new MpException(JsonResultCode.MSG_CARD_ADD_TIMES_OVER_ALIAS,"",String.valueOf(exchangSurplus));
				}
			}
			logger().info("end: 会员卡配置的兑换商品兑换总次数次数是否可用");
			//	4-会员卡配置的指定时间范围内兑换的次数检验
			Byte periodLimit = memberCard.getPeriodLimit();
			if(TimeType.NO_LIMIT.val.equals(periodLimit)) {
				logger().info("该商品兑换时间限制无限制");
			}else {
				logger().info("start: 会员卡配置的指定时间范围内兑换的次数检验");
				TimeType[] values = TimeType.values();
				if(periodLimit != null && periodLimit < values.length) {
					//	期间内兑换的次数
					Integer periodExchangGoodsTimes = getExchangGoodsTimesDuringPeriod(goodsId,cardNo,values[periodLimit]);
					Integer periodNum = memberCard.getPeriodNum();
					//	期间内剩余兑换次数
					Integer remainExchangTimes = periodNum-periodExchangGoodsTimes;
					//	期间描述
					String periodLimitDesc = CardExchangService.getPeriodLimitDesc(periodLimit, null);
					if(considerUserCheckedGoods) {
						if(remainExchangTimes<=0 || (userCheckedCount+goodsNum)>remainExchangTimes) {
							
							// 该卡{periodLimitDesc}剩余兑换次数{remainExchangTimes},已加购{userCheckedCount}件
							logger().info("该卡"+periodLimitDesc+"剩余兑换次数"+remainExchangTimes+",已加购"+userCheckedCount+"件");
							throw new MpException(JsonResultCode.MSG_CARD_PERIOD_ADD_TIMES_OVER,"",periodLimitDesc,String.valueOf(remainExchangTimes),String.valueOf(userCheckedCount));
						}
					}else {
						if(remainExchangTimes<=0 || goodsNum>remainExchangTimes) {
							// 该卡{periodLimitDesc}剩余兑换次数{remainExchangTimes}
							logger().info("该卡"+periodLimitDesc+"剩余兑换次数"+remainExchangTimes);
							throw new MpException(JsonResultCode.MSG_CARD_PERIOD_ADD_TIMES_OVER_ALIAS,"",periodLimitDesc,String.valueOf(remainExchangTimes));
						}
					}
				}
				logger().info("end: 会员卡配置的指定时间范围内兑换的次数检验");
			}
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 获取该会员卡配置的该商品允许兑换次数
	 * @param goodsId 商品Id
	 * @param memberCard 会员卡Record
	 * @return 该会员卡配置的该商品允许兑换次数 | null 表示该会员卡未配置该商品
	 */
	public Integer getCardAllowExchangGoodsTimes(Integer goodsId, MemberCardRecord memberCard) {
		Integer allowExchangTime = null;
		CardExchangGoods cardExchangGoodsCfg = cardExchangSvc.getCardExchangGoodsService(memberCard);
		if(CardUtil.isExchangPartGoods(cardExchangGoodsCfg.getIsExchange())) {
			logger().info("在部分兑换商品中查找");
			//	部分兑换商品
			List<GoodsCfg> exchangGoods = cardExchangGoodsCfg.getExchangGoods();
			for(GoodsCfg goodsCfg: exchangGoods) {
				if(goodsCfg.getGoodsIds().contains(goodsId)){
					allowExchangTime = goodsCfg.getMaxNum();
					break;
				}
			}
		}else if(CardUtil.isExchangAllGoods(cardExchangGoodsCfg.getIsExchange())){
			logger().info("在全部兑换商品中查找");
			//	全部兑换商品
			allowExchangTime = cardExchangGoodsCfg.getEveryGoodsMaxNum();
		}else {
			logger().info("不在查询范围");
			allowExchangTime = null;
		}
		return allowExchangTime;
	}
	
	/**
	 * 获取兑换卡可兑换的商品Id
	 * @param cardNo 会员卡卡号
	 * @return null 表示全部商品 | List 可兑换商品的Id
	 */
	public List<Integer> getCardExchangGoodsIds(String cardNo) {
		CardFullDetail cardDetail = mCardSvc.getCardDetailByNo(cardNo);
		 List<Integer> res = cardExchangSvc.getExchangGoodsAllIds(cardDetail.getMemberCard());
		 logger().info("兑换商品IDs: "+res);
		 return res;
	}
	
	/**
	 * 获取会员卡已兑换商品提示
	 * @return
	 */
	public CardExchangTipVo getCardExchangTip(String cardNo,MemberCardRecord card) {
		CardExchangTipVo vo = new CardExchangTipVo();
		Byte periodLimit = card.getPeriodLimit();
		TimeType[] values = TimeType.values();
		
		Integer exchangGoodsNum = getExchangGoodsTimesDuringPeriod(null,cardNo,values[periodLimit]);
		
		vo.setPeriodExchangGoodsNum(exchangGoodsNum);
		vo.setPeriodLimit(periodLimit);
		vo.setPeriodNumber(card.getPeriodNum());
		return vo;
		
	}
}
