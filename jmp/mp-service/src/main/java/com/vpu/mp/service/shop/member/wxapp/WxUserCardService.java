package com.vpu.mp.service.shop.member.wxapp;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.exception.CardActivateException;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardParam;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardVo;
import com.vpu.mp.service.pojo.shop.member.ucard.DefaultCardParam;
import com.vpu.mp.service.pojo.shop.member.ucard.ReceiveCardParam;
import com.vpu.mp.service.shop.member.UserCardService;
/**
 * @author 黄壮壮
 * 	小程序会员卡服务
 */
@Service
public class WxUserCardService extends ShopBaseService {
	@Autowired
	private WxAppCardReceiveSerive wxAppCardReceiveSerive;
	@Autowired
	private WxAppCardActivationService wxAppCardActivationService;
	@Autowired 
	private UserCardService userCardService;
	/**
	 * 通过领取码领取会员卡
	 * @throws MpException 
	 */
	public void receiveCard(ReceiveCardParam param) throws MpException {
		wxAppCardReceiveSerive.receiveCard(param);
	}
	/**
	 * 	会员卡激活
	 * @throws CardActivateException  激活失败
	 */
	public ActivateCardVo activationCard(ActivateCardParam param, String lang) throws CardActivateException {
		if(NumberUtils.BYTE_ONE.equals(param.getIsSetting())) {
			wxAppCardActivationService.setActivationCard(param);
		}else {
			return wxAppCardActivationService.getActivationCard(param,lang);
		}
		return null;
	}
	/**
	 * 	设置为默认会员卡
	 */
	public void setDefault(DefaultCardParam param) {
		userCardService.setDefault(param);
		
	}

}
