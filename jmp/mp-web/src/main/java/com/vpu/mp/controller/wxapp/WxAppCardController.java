package com.vpu.mp.controller.wxapp;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.account.WxAppUserCardVo;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.exception.UserCardNullException;

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
	 * 领取会员卡
	 */
//	@PostMapping(value="/api/card/getcard")
//	public JsonResult getCard() {
//		logger().info("领取会员卡");
//		
//		return success();
//	}
	
}
