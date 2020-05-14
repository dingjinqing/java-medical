package com.vpu.mp.service.shop.card.wxapp;

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
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.CHECKED_GOODS_CART;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
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
			
			GoodsSearchMpParam searchParam = new GoodsSearchMpParam();
			searchParam.setKeyWords(param.getSearch());
			searchParam.setPageFrom(GoodsSearchMpParam.PAGE_FROM_CARD_EXCHANGE_GOODS);
			GoodsSearchMpOuterParam outerPageParam = new GoodsSearchMpOuterParam();
			outerPageParam.setCardNo(param.getCardNo());
			searchParam.setOuterPageParam(outerPageParam);
			GoodsSearchContentVo searchGoodsGate = goodsSearchMpSvc.searchGoodsGate(searchParam);
			PageResult<? extends GoodsListMpVo> pageResult = searchGoodsGate.getPageResult();
			vo.setGoodsPageResult(pageResult);
			//	Integer count = userCard.getExchangSurplus();
			// TODO 兑换商品数量配置，时间限制
			
			//	获取用户已勾选的
			UserCheckedGoodsParam checkedGoodsParam = new UserCheckedGoodsParam();
			checkedGoodsParam.setAction(CardConstant.MCARD_TP_LIMIT);
			checkedGoodsParam.setIdentityId(userCard.getCardNo());
			checkedGoodsParam.setUserId(param.getUserId());
			Integer totalNumber = userCheckedGoodsSvc.getUserCheckedCount(checkedGoodsParam);
			vo.setTotalNumber(totalNumber);
			
			Map<String, Object> cardInfo = userCard.intoMap();
			vo.setCardInfo(cardInfo);
		}
		return vo;
	}
	
	
	
	/**
	 * 判断是否可以兑换或加入兑换列表
	 * @throws MpException 
	 */
	public boolean judgeCardGoods(CardExchaneGoodsJudgeParam param) throws MpException {
		return judgeExchangGoodsAvailable(param.getUserId(),param.getCardNo());
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
		
		boolean judegeRes = judgeExchangGoodsAvailable(param.getGoodsId(), param.getCardNo());
		if(judegeRes) {
			CheckedGoodsCartRecord userCheckedGoods = userCheckedGoodsSvc.getUserCheckedGoods(checkedParam);
			if(userCheckedGoods == null) {
				// 添加
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
				userCheckedGoods.setGoodsNumber(param.getPrdNumber());
				db().executeUpdate(userCheckedGoods);
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
	 * 获取该卡指定时间范围兑换某个商品的次数
	 * @param goodsId 商品Id
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
				.and(ORDER_GOODS.GOODS_ID.eq(goodsId))
				.and(ORDER_INFO.EXCHANG.eq(CardConstant.MCARD_TP_LIMIT));	
		
		if(null != times) {
			//	有效期范围内
			condition = condition
					.and(ORDER_INFO.CREATE_TIME.ge(times[0]))
					.and(ORDER_INFO.CREATE_TIME.le(times[1]));
		}
		 return db()
			.select(DSL.sum(ORDER_GOODS.GOODS_NUMBER))
			.from(ORDER_GOODS)
			.leftJoin(ORDER_INFO).on(ORDER_GOODS.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
			.and(condition)
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
	 * @throws MpException 兑换商品异常 表示
	 * @return true 表示可兑换
	 */
	public boolean judgeExchangGoodsAvailable(Integer goodsId,String cardNo) throws MpException {
		CardFullDetail cardDetail = mCardSvc.getCardDetailByNo(cardNo);
		MemberCardRecord memberCard = cardDetail.getMemberCard();
		UserCardRecord userCard = cardDetail.getUserCard();
		//	检测该限次卡是否用
		CardUseCondition judegeCardUsable = limitCardOpt.judegeCardUsable(userCard,memberCard);
		
		if(!judegeCardUsable.isUsable()) {
			throw new MpException(judegeCardUsable.getErrorCode());
		}
		
		//	检测该兑换商品是否满足该限次卡的设定的配置
		//	已经兑换商品的总次数
		Integer exchangGoodsAllTimes = getExchangGoodsAllTimes(goodsId,cardNo);
		//	获取会员卡配置的该商品允许兑换的次数
		Integer allowExchangTimes = getCardAllowExchangGoodsTimes(goodsId, memberCard);
		if(allowExchangTimes==null) {
			// 该会员卡不支持兑换该商品
			throw new MpException(JsonResultCode.MSG_CARD_NOT_SUPPORT_GOODS);
		}
		//	是否为不限制次数
		if(!CardConstant.INFINITE_EXCHANGE.equals(allowExchangTimes)) {
			if(exchangGoodsAllTimes>=allowExchangTimes) {
				//	本卡允许兑换该商品的次数{allowExchangTimes},您已经兑换了{exchangGoodsAllTimes}次
				throw new MpException(JsonResultCode.MSG_CARD_ALLOW_TIME_OVER,"",String.valueOf(allowExchangTimes),String.valueOf(exchangGoodsAllTimes));
			}
		}
		
		//	会员卡配置的兑换商品兑换总次数次数是否可用
		//	已经加购的商品
		Integer userCheckedCount = userCheckedGoodsSvc.getUserCheckedCount(userCard.getUserId(),cardNo);
		Integer exchangSurplus = userCard.getExchangSurplus();
		if(exchangSurplus<=0 || userCheckedCount>=exchangSurplus) {
			//	本卡剩余可兑换{exchangSurplus}件，已加购{userCheckedCount}件
			throw new MpException(JsonResultCode.MSG_CARD_ADD_TIMES_OVER,"",String.valueOf(exchangSurplus),String.valueOf(userCheckedCount));
		}
		
		//	会员卡配置的指定时间范围内兑换的次数检验
		Byte periodLimit = memberCard.getPeriodLimit();
		TimeType[] values = TimeType.values();
		if(periodLimit != null && periodLimit < values.length) {
			//	期间内兑换的次数
			Integer periodExchangGoodsTimes = getExchangGoodsTimesDuringPeriod(goodsId,cardNo,values[periodLimit]);
			Integer periodNum = memberCard.getPeriodNum();
			//	期间内剩余兑换次数
			Integer remainExchangTimes = periodNum-periodExchangGoodsTimes;
			if(remainExchangTimes<=0 || userCheckedCount>=remainExchangTimes) {
				//	期间描述
				String periodLimitDesc = CardExchangService.getPeriodLimitDesc(periodLimit, null);
				// 该卡{periodLimitDesc}剩余兑换次数{remainExchangTimes},已加购{userCheckedCount}件
				throw new MpException(JsonResultCode.MSG_CARD_PERIOD_ADD_TIMES_OVER,"",periodLimitDesc,String.valueOf(remainExchangTimes),String.valueOf(userCheckedCount));
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
	private Integer getCardAllowExchangGoodsTimes(Integer goodsId, MemberCardRecord memberCard) {
		Integer allowExchangTime = null;
		CardExchangGoods cardExchangGoodsCfg = cardExchangSvc.getCardExchangGoodsService(memberCard);
		if(CardUtil.isExchangPartGoods(cardExchangGoodsCfg.getIsExchange())) {
			//	部分兑换商品
			List<GoodsCfg> exchangGoods = cardExchangGoodsCfg.getExchangGoods();
			for(GoodsCfg goodsCfg: exchangGoods) {
				if(goodsCfg.getGoodsIds().contains(goodsId)){
					allowExchangTime = goodsCfg.getMaxNum();
					break;
				}
			}
		}else if(CardUtil.isExchangAllGoods(cardExchangGoodsCfg.getIsExchange())){
			//	全部兑换商品
			allowExchangTime = cardExchangGoodsCfg.getEveryGoodsMaxNum();
		}else {
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
}
