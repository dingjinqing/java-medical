package com.vpu.mp.service.shop.member.wxapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.UserCardMaParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.ChargeParam;
import com.vpu.mp.service.pojo.shop.member.card.ChargeVo;
import com.vpu.mp.service.pojo.shop.member.exception.CardActivateException;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardParam;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardVo;
import com.vpu.mp.service.pojo.shop.member.ucard.CardUseListParam;
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
	
	private static final byte ONE = 1;
	private static final byte NEONE = -1;
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
	
	public UserCardMaParam getUseList(CardUseListParam param) {
		Record userCardInfo = userCardService.userCardDao.getUserCardInfoBycardNo(param.getCardNo());
		if(userCardInfo==null) {
			//该卡不存在
			return null;
		}
		UserCardMaParam into = userCardInfo.into(UserCardMaParam.class);
		ChargeParam param2=new ChargeParam();
		param2.setCardNo(param.getCardNo());
		param2.setCurrentPage(param.getCurrentPage());
		param2.setPageRows(param.getPageRows());
		Byte showType = param.getShowType();
		if(showType.equals(ONE)) {
			logger().info("showType为1");
			PageResult<ChargeVo> chargeList = userCardService.cardDao.getChargeList(param2);
			into.setChargeList(chargeList.dataList);
		}
		if(showType.equals(NEONE)) {
			logger().info("showType为-1");
			PageResult<ChargeVo> consumeList = userCardService.cardDao.getConsumeList(param2);
			into.setChargeList(consumeList.dataList);
		}
		return into;
	}
	

}
