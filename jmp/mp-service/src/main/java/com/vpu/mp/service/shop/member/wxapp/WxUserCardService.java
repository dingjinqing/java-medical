package com.vpu.mp.service.shop.member.wxapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.pojo.shop.member.exception.CardReceiveFailException;
import com.vpu.mp.service.pojo.shop.member.ucard.ReceiveCardParam;
/**
 * @author 黄壮壮
 * 小程序会员卡服务
 */
@Service
public class WxUserCardService extends ShopBaseService {
	@Autowired
	private WxAppCardReceiveSerive wxAppCardReceiveSerive;
	/**
	 * 通过领取码领取会员卡
	 * @throws CardReceiveFailException 
	 */
	public void receiveCard(ReceiveCardParam param) throws CardReceiveFailException {
		wxAppCardReceiveSerive.receiveCard(param);
		
	}

}
