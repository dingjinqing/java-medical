package com.vpu.mp.controller.admin;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
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
	
	
	/**
	 * 创建一张会员卡
	 * @param card
	 * @return
	 */
	@PostMapping("/add")
	public JsonResult createMemberCard(@RequestBody CardParam card) {
		//TODO
		System.out.println(card);
		
		this.shop().member.card.addMemberCard(card);
		return this.success();
	}
	
}
