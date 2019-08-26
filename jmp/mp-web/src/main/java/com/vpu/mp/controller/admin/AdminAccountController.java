package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.shop.member.MemberService;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.ACCOUNT_DEFAULT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;
import static com.vpu.mp.service.foundation.data.JsonResultCode.CODE_MEMBER_ACCOUNT_UPDATE_FAIL;
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
		Byte tradeFlow = TRADE_FLOW_INCOME.getValue();
		MemberService member = shop().member;
		JsonResultCode ret = member.account.addUserAccount(param,adminUser,tradeType,tradeFlow);
		
		if(ret == CODE_MEMBER_ACCOUNT_UPDATE_FAIL) {
			return this.fail(JsonResultMessage.MSG_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
		
		return success();
	}
}
