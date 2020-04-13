package com.vpu.mp.service.shop.card.wxapp;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomAction;
import com.vpu.mp.service.shop.card.CardDetailService;

/**
 * 	微信小程序会员卡详情
 * @author 黄壮壮
 *
 */
@Service
public class WxCardDetailService extends ShopBaseService{
	@Autowired
	private CardDetailService cardDetailSvc;
	/**
	 * 	获取自定义的激活项
	 */
	public List<CardCustomAction> getNeedActivationCustomOptions(MemberCardRecord card) {
		List<CardCustomAction> customOptions = cardDetailSvc.getCustomAction(card);
		customOptions.removeIf(action->action.getChecked()==NumberUtils.BYTE_ZERO);
		return customOptions;
	}
	
}
