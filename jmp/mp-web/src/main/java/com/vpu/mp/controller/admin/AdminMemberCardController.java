package com.vpu.mp.controller.admin;


import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LIMIT_NUM_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NORMAL_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.RANK_TYPE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.CardVo;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月26日
 * @Description: 会员卡管理
 */
@RestController
@RequestMapping(value="/api/admin/member")
public class AdminMemberCardController extends AdminBaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 创建一张会员卡
	 * @param card
	 * @return
	 */
	@PostMapping("/card/add")
	public JsonResult createMemberCard(@RequestBody CardParam card) {
		/** logger info*/
		logger.info(card.getDesc());
		logger.info(Util.toJson(card));
		this.shop().member.card.addMemberCard(card);
		return this.success();
	}
	
	
	/**
	 * 返回相应的会员卡列表
	 * @return
	 */
	@PostMapping("/card/list")
	public JsonResult getCardList(@RequestBody SearchCardParam param) {
		
		logger.info(param.toString());
		PageResult<? extends CardVo> result = shop().member.card.getCardList(param);
		return success(result);
	}
}
