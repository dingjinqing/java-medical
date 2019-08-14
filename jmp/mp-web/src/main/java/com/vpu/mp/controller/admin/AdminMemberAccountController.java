package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListVo;

/**
* @author 黄壮壮
* @Date: 2019年8月13日
* @Description: 会员的余额管理控制器
*/
@RestController
public class AdminMemberAccountController extends AdminBaseController {
	
	/**
	 *  获取会员用户的余额明细
	 */
	@PostMapping("/api/admin/member/account/list")
	public JsonResult getAccountDetails(@RequestBody @Valid AccountPageListParam param) {
		logger().info("正在查询会员用户的余额明细");
		logger().info(param.getOrderSn());
		
		PageResult<AccountPageListVo> pageList = shop().member.account.getPageListOfAccountDetails(param);
		return success(pageList);
	}
	
}
