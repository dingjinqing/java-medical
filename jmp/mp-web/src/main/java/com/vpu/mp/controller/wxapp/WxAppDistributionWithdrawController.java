package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.pojo.wxapp.withdraw.WithdrawApplyParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序端分销提现
 * @author ws
 */
@RestController
@RequestMapping("/api/wxapp/distribution/withdraw")
public class WxAppDistributionWithdrawController extends WxAppBaseController {

	@PostMapping("query")
	public JsonResult distributorGroupList() {
		return null;
	}

    @RedisLock(prefix = JedisKeyConstant.NotResubmit.WITHDRAW_APPLY, noResubmit = true)
    @PostMapping("/apply")
    public JsonResult apply(@RequestBody WithdrawApplyParam param) throws MpException {
        param.setUserId(wxAppAuth.user().getUserId());
        shop().withdrawService.apply(param);
        return success();
    }
}
