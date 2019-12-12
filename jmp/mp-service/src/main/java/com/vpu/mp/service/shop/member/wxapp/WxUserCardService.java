package com.vpu.mp.service.shop.member.wxapp;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardParam;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardVo;
import com.vpu.mp.service.pojo.shop.member.ucard.ReceiveCardParam;
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
	/**
	 * 通过领取码领取会员卡
	 * @throws MpException 
	 */
	public void receiveCard(ReceiveCardParam param) throws MpException {
		wxAppCardReceiveSerive.receiveCard(param);
	}

	public ActivateCardVo activationCard(ActivateCardParam param) {
		if(NumberUtils.BYTE_ONE.equals(param.getIsSetting())) {
			logger().info("获取会员卡激活信息");
			return wxAppCardActivationService.getActivationCard(param);
		}else {
			logger().info("设置会员卡激活信息");
			wxAppCardActivationService.setActivationCard(param);
		}
		return null;
	}

}
