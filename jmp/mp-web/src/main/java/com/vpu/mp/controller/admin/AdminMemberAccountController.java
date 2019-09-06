package com.vpu.mp.controller.admin;

import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.ACCOUNT_DEFAULT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_OUTCOME;

import javax.validation.Valid;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListVo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.shop.member.MemberService;

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
	
	
	/**
	 * 会员用户余额变动
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/member/account/update")
	public JsonResult updateMemberAccount(@RequestBody AccountParam param) {
		int adminUser = 0;
		Byte tradeType = ACCOUNT_DEFAULT.getValue();
		Byte tradeFlow = null;
		if(BigDecimalUtil.compareTo(param.getAmount(), null)==-1) {
			tradeFlow = TRADE_FLOW_OUTCOME.getValue();
		}else {
			tradeFlow = TRADE_FLOW_INCOME.getValue();
		}
		
		/** 获取语言 用于国际化 */
		String language = StringUtils.isEmpty(request.getHeader("V-Lang"))?"":request.getHeader("V-Lang");
		MemberService member = shop().member;
		
		
		try {
			member.account.addUserAccount(param,adminUser,tradeType,tradeFlow,language);
		} catch (MpException e) {
			return this.fail(e.getErrorCode());
		}
		return success();
	}
	
}
