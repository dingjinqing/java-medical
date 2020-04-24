package com.vpu.mp.service.shop.card.wxapp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.dao.CardFullDetail;
import com.vpu.mp.service.pojo.wxapp.card.param.CardExchangeGoodsParam;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.member.MemberCardService;

/**
 * 	会员卡兑换商品服务
 * @author 黄壮壮
 *
 */
@Service
public class WxCardExchangeService extends ShopBaseService {
	@Autowired private MemberCardService mCardSvc;
	@Autowired private ShopCommonConfigService shopCommonCfgSvc;
	/**
	 * 兑换商品列表
	 * @param param
	 */
	@SuppressWarnings("null")
	public void changeGoodsList(CardExchangeGoodsParam param) {
		logger().info("兑换商品列表");
		if(StringUtils.isBlank(param.getCardNo())) {
			return;
		}
		CardFullDetail cardDetail = mCardSvc.getCardDetailByNo(param.getCardNo());
		if(cardDetail==null) {
			UserCardRecord userCard = cardDetail.getUserCard();
			MemberCardRecord memberCard = cardDetail.getMemberCard();
			
			if(userCard.getActivationTime() == null 
						|| !CardUtil.isLimitCard(memberCard.getCardType()) 
						|| !CardUtil.canExchangGoods(memberCard.getIsExchang())) {
				logger().info("该会员卡不可兑换");
				return;
			}
			
			if(userCard.getExchangSurplus()<=0) {
				logger().info("该会员卡已无兑换次数");
				return;
			}
			
			Byte soldOutGoods = shopCommonCfgSvc.getSoldOutGoods();
			List<Integer> goodsIdList = Util.splitValueToList(memberCard.getExchangGoods());
			
			
		}
		
	}
	
}
