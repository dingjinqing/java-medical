package com.vpu.mp.service.shop.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.CardBatchRecord;
import com.vpu.mp.db.shop.tables.records.GoodsCardCoupleRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopVo;
import com.vpu.mp.service.pojo.shop.member.builder.CardBatchVoBuilder;
import com.vpu.mp.service.pojo.shop.member.card.BaseCardVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBatchVo;
import com.vpu.mp.service.pojo.shop.member.card.CardIdParam;
import com.vpu.mp.service.pojo.shop.member.card.LimitNumCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.NormalCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.RankCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomAction;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomRights;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;
import com.vpu.mp.service.pojo.shop.member.card.create.CardGive;
import com.vpu.mp.service.pojo.shop.member.card.create.CardGive.CardGiveSwitch;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRenew;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRight;
import com.vpu.mp.service.pojo.shop.member.card.create.CardTag;
import com.vpu.mp.service.pojo.shop.member.card.create.CardTag.CardTagSwitch;
import com.vpu.mp.service.pojo.shop.member.tag.TagVo;
import com.vpu.mp.service.pojo.shop.member.card.create.CardRenew.DateType;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.member.CardReceiveCodeService;
import com.vpu.mp.service.shop.member.GoodsCardCoupleService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.TagService;
import com.vpu.mp.service.shop.store.store.StoreService;

/**
 * 会员卡配置详情获取服务
 * @author 黄壮壮
 *
 */
@Service
public class CardDetailService extends ShopBaseService{
	@Autowired
	private CouponGiveService couponGiveService;
	
	@Autowired
	private GoodsCardCoupleService goodsCardCoupleService;
	
    @Autowired
    private DomainConfig domainConfig;
    
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private MemberCardService memberCardSvc;
	
	@Autowired
	private CardFreeShipService freeShipSvc;
	
	@Autowired
	private CardReceiveCodeService cardReceiveCode;
	
	@Autowired
	private TagService tagSvc;

	
	/**
	 * 根据ID获取该会员卡的详细信息
	 */
	public BaseCardVo getCardDetailById(CardIdParam param) {
		MemberCardRecord card = memberCardSvc.getCardById(param.getId());

		if (CardUtil.isNormalCard(card.getCardType())) {
			return changeToNormalCardDetail(card);
		} else if (CardUtil.isLimitCard(card.getCardType())) {
			return changeToLimitCardDetail(card);
		} else if (CardUtil.isGradeCard(card.getCardType())) {
			return changeToGradeCardDetail(card);
		}
		return null;
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
		// 自定义激活项
		normalCard.setCustomAction(getCustomAction(card));
		// 用户标签
		normalCard.setMyCardTag(getCardTag(card));
		return normalCard;
	}
	
	private LimitNumCardToVo changeToLimitCardDetail(MemberCardRecord card) {
		logger().info("正在获取限次会员卡");
		LimitNumCardToVo limitCard = card.into(LimitNumCardToVo.class);
		assignCardBatch(limitCard);
		int numOfSendCard = memberCardSvc.getNumSendCardById(limitCard.getId());
		limitCard.setHasSend(numOfSendCard);
		changeCardJsonCfgToDetailType(limitCard);
		// 自定义权益信息
		limitCard.setCardCustomRights(getCustomRights(card));
		// 自定义激活项
		limitCard.setCustomAction(getCustomAction(card));
		// 同步打标签
		limitCard.setMyCardTag(getCardTag(card));
		// 转赠数据
		limitCard.setCardGive(getCardGive(card));
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
	/**
	 * 获取卡的自定义激活项
	 * @param MemberCardRecord
	 * @return CardCustomAction
	 */
	public List<CardCustomAction> getCustomAction(MemberCardRecord card) {
		logger().info("获取卡的自定义激活项");
		String customOptions = card.getCustomOptions();
		if(!StringUtils.isBlank(customOptions)) {
			 return Util.json2Object(customOptions,new TypeReference<List<CardCustomAction>>() {
	        }, false);
			
		}else {
			return Collections.<CardCustomAction>emptyList();
		}
	}
	
	/**
	 * 	获取会员卡同步打标签数据
	 */
	private CardTag getCardTag(MemberCardRecord card) {
		logger().info("获取会员卡同步打标签数据");
		CardTagSwitch cardTag = CardTag.CardTagSwitch.values()[card.getCardTag()];
		String cardTagId = card.getCardTagId();
		List<TagVo> tags = new ArrayList<>();
		if(!StringUtils.isBlank(cardTagId)) {
			List<Integer> ids = Util.json2Object(cardTagId,new TypeReference<List<Integer>>() {
	        }, false);
			tags = tagSvc.getTagsById(ids);
		}
	
		return CardTag.builder()
					.cardTag(cardTag)
					.cardTags(tags)
					.build();
	}
	
	/**
	 * 	获取会员卡转赠数据
	 */
	private CardGive getCardGive(MemberCardRecord card) {
		logger().info("获取会员卡转赠数据");
		CardGiveSwitch[] switchs = CardGive.CardGiveSwitch.values();
		return CardGive.builder()
					.cardGiveAway(switchs[card.getCardGiveAway()])
					.cardGiveContinue(switchs[card.getCardGiveContinue()])
					.mostGiveAway(card.getMostGiveAway())
					.build();
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
	
	
	/**
	 * 获取专享商品id
	 */
	private List<Integer> getOwnGoodsId(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnGoods(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	/**
	 * 获取专享平家id
	 */
	private List<Integer> getOwnStoreCategory(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnStoreCategory(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	/**
	 * 获取专享平台id
	 */
	private List<Integer> getOwnPlatformCategory(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnPlatformCategory(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	/**
	 * 获取专享品牌id
	 */
	private List<Integer> getOwnBrandId(Integer cardId) {
		List<GoodsCardCoupleRecord> list = goodsCardCoupleService.getOwnBrandId(cardId);
		return list.stream().map(GoodsCardCoupleRecord::getGctaId).collect(Collectors.toList());
	}

	
	private <T> boolean isListAvailable(List<T> list) {
		return list != null && list.size() > 0;
	}

	
}
