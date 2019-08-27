package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.vpu.mp.service.foundation.data.JsonResult;

import com.vpu.mp.service.foundation.exception.MpException;

import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.shop.member.MemberService;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.ACCOUNT_DEFAULT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_OUTCOME;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
/**
* @author 黄壮壮
* @Date: 2019年8月26日
* @Description: 会员余额控制器
*/
public class AdminAccountController extends AdminBaseController {
	
	/**
	 * 会员用户余额变动
	 * @param param
	 * @return
	 */
	@PostMapping("/account/update")
	public JsonResult updateMemberAccount(@RequestBody AccountParam param) {
		int adminUser = 0;
		Byte tradeType = ACCOUNT_DEFAULT.getValue();
		Byte tradeFlow = null;
		if(BigDecimalUtil.compareTo(param.getAmount(), null)==-1) {
			tradeFlow = TRADE_FLOW_OUTCOME.getValue();
		}else {
			tradeFlow = TRADE_FLOW_INCOME.getValue();
		}
		
		MemberService member = shop().member;
		try {
			member.account.addUserAccount(param,adminUser,tradeType,tradeFlow);
		} catch (MpException e) {
			return this.fail(e.getErrorCode());
		}
		return success();
	}
}
