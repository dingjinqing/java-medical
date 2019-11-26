package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;

/**
* @author 黄壮壮
* @Date: 2019年11月26日
* @Description:
*/
@RestController
public class AdminUserCardController extends AdminBaseController {
	/**
	 * 用户卡 - 删除
	 */
	@PostMapping("/api/admin/user/card/delete")
	public JsonResult deleteUserCard(@RequestBody UserCardParam userCard) {
		logger().info("删除用户会员卡");
		shop().userCard.repealCardByCardNo(userCard.getCardNo());
		return this.success("删除用户会员卡成功");
	}
}
