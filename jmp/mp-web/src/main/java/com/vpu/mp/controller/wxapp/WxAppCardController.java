package com.vpu.mp.controller.wxapp;


import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.account.UserIdAndCardIdParam;
import com.vpu.mp.service.pojo.shop.member.account.WxAppUserCardVo;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.exception.CardReceiveFailException;
import com.vpu.mp.service.pojo.shop.member.exception.UserCardNullException;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardParam;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardVo;
import com.vpu.mp.service.pojo.shop.member.ucard.ReceiveCardParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;

/**
* @author 黄壮壮
* @Date: 2019年10月23日
* @Description: 微信小程序会员卡控制器
*/
@RestController
public class WxAppCardController extends WxAppBaseController {
	/**
	 * 获取用户的会员卡列表
	 * @return
	 */
	@PostMapping(value="/api/card/list")
	public JsonResult getUserCard(@RequestBody SearchCardParam param) {
		logger().info("wxapp request for card list of person.");
		PageResult<WxAppUserCardVo> cardList = shop().user.userCard.getAllCardsOfUser(param);
		return success(cardList);
	}
	
	/**
	 * 会员卡详情
	 */
	@PostMapping(value="/api/card/detail")
	public JsonResult getUserCardDetail(@RequestBody UserCardParam param) {
		logger().info("WxAppCardController: request for card detail");
		WxAppUserCardVo userCardDetail;
		try {
			userCardDetail = shop().user.userCard.getUserCardDetail(param);
		} catch (UserCardNullException e) {
			return fail(e.getErrorCode());
		}
		return success(userCardDetail);
	}
	
	/**
	 * 领取会员卡判断
	 */
	@PostMapping(value="/api/card/judgement")
	public JsonResult userCardJudgement(@RequestBody UserIdAndCardIdParam param) {
		logger().info("判断是否有会员卡");
		WxAppSessionUser user = wxAppAuth.user();
		param.setUserId(user.getUserId());
		shop().user.userCard.userCardJudgement(param,getLang());
		return success();
	}
	/**
	 * 领取会员卡
	 */
	@PostMapping(value="/api/card/getCard")
	public JsonResult getCard(@RequestBody UserIdAndCardIdParam param) {
		logger().info("领取会员卡");
		WxAppSessionUser user = wxAppAuth.user();
		param.setUserId(user.getUserId());
		try {
			return success(shop().user.userCard.getCard(param));
		} catch (MpException e) {
			return fail(e.getErrorCode());
		}	
	}
	/**
	 * 用户通过领取码领取会员卡
	 */
	@PostMapping(value="/api/wxapp/card/code/receive")
	public JsonResult receiveCard(@RequestBody @Validated ReceiveCardParam param) {
		logger().info("用户通过领取码领取会员卡");
		param.setUserId(wxAppAuth.user().getUserId());
		try {
			shop().user.wxUserCardService.receiveCard(param);
		} catch (MpException e) {
			return fail(e.getErrorCode());
		} 
		return success();
	}
	
	/**
	 * 	获取会员卡激活信息
	 */
	@PostMapping(value="/api/wxapp/activation/card")
	public JsonResult activationCard(@RequestBody @Validated ActivateCardParam param) {
		logger().info("获取会员卡激活信息"+param);
		param.setUserId(this.wxAppAuth.user().getUserId());
		ActivateCardVo vo = shop().user.wxUserCardService.activationCard(param,getLang());
		if(NumberUtils.INTEGER_ONE.equals(param.getIsSetting())) {
			return success();
		}else if(vo != null){
			return this.success(vo);
		}
		return fail();
	}
	
}
