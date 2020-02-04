package com.vpu.mp.controller.wxapp;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.CardReceiveVo;
import com.vpu.mp.service.pojo.shop.member.account.UserCardGetParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardJudgeVo;
import com.vpu.mp.service.pojo.shop.member.account.UserCardMaParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.account.UserIdAndCardIdParam;
import com.vpu.mp.service.pojo.shop.member.account.WxAppUserCardVo;
import com.vpu.mp.service.pojo.shop.member.card.ChargeVo;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.exception.CardActivateException;
import com.vpu.mp.service.pojo.shop.member.exception.UserCardNullException;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardParam;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardVo;
import com.vpu.mp.service.pojo.shop.member.ucard.CardUseListParam;
import com.vpu.mp.service.pojo.shop.member.ucard.DefaultCardParam;
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
		param.setUserId(wxAppAuth.user().getUserId());
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
		UserCardJudgeVo vo = shop().user.userCard.userCardJudgement(param,getLang());
		return success(vo);
	}
	/**
	 * 领取会员卡
	 */
	@PostMapping(value="/api/card/getCard")
	public JsonResult getCard(@RequestBody @Validated UserCardGetParam param) {
		UserIdAndCardIdParam para = new UserIdAndCardIdParam();
		logger().info("领取会员卡");
		WxAppSessionUser user = wxAppAuth.user();
		para.setUserId(user.getUserId());
		para.setGetType(param.getGetType());
		para.setCardId(param.getCardId()!=null?param.getCardId():param.getCardInfo().getCardId());
		try {
			CardReceiveVo vo = shop().user.userCard.getCard(para);
			if(!StringUtils.isBlank(vo.getCardNo())) {
				return success(vo);
			}else {
				return fail();
			}
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
	 * 	会员卡激活
	 */
	@PostMapping(value="/api/wxapp/activation/card")
	public JsonResult activationCard(@RequestBody @Validated ActivateCardParam param) {
		logger().info("获取会员卡激活信息"+param);
		param.setUserId(this.wxAppAuth.user().getUserId());
		try {
			ActivateCardVo vo = shop().user.wxUserCardService.activationCard(param,getLang());
			if(NumberUtils.BYTE_ONE.equals(param.getIsSetting())) {
				return success();
			}else if(vo != null){
				return this.success(vo);
			}
			return fail();
		} catch (CardActivateException e) {
			return fail(e.getErrorCode());
		}
	}
	
	/**
	 * 	设置默认会员卡
	 */
	@PostMapping(value="/api/wxapp/card/default")
	public JsonResult setDefault(@RequestBody @Validated DefaultCardParam param) {
		logger().info("设置默认会员卡");
		param.setUserId(wxAppAuth.user().getUserId());
		shop().user.wxUserCardService.setDefault(param);
		return success();
	}
	
	
	/**
	 * 	会员卡使用记录
	 */
	@PostMapping(value="/api/wxapp/card/use")
	public JsonResult cardConsume(@RequestBody @Validated CardUseListParam param) {
		logger().info("会员卡使用记录");
		UserCardMaParam useList = shop().user.wxUserCardService.getUseList(param,getLang());
		if(useList==null) {
			return fail(JsonResultCode.CODE_CARD_NO);
		}
		return success(useList);
	}

}
