package com.vpu.mp.controller.admin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月26日
 * @Description: 会员卡管理
 */
@RestController
@RequestMapping(value="/api/admin/member/card")
public class AdminMemberCardController extends AdminBaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 创建一张会员卡
	 * @param card
	 * @return
	 */
	@PostMapping("/add")
	public JsonResult createMemberCard(@RequestBody CardParam card) {
		/** logger info*/
		logger.info(Util.toJson(card));
		this.shop().member.card.addMemberCard(card);
		return this.success();
	}
	
}
