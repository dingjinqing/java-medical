package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.RequestUtil;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawListParam;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawParam;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawVo;
import com.vpu.mp.service.shop.rebate.DoctorWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@RestController
public class WxAppDoctorWithdrawController extends WxAppBaseController{
    @Autowired
    private DoctorWithdrawService doctorWithdrawService;

    /**
     * 提现列表
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/doctor/withdraw/list")
    public JsonResult getPageList(@RequestBody DoctorWithdrawListParam param){
        PageResult<DoctorWithdrawVo> result= doctorWithdrawService.getPageList(param);
        return success(result);
    }

    /**
     * 发起提现申请
     * @param param
     * @return
     */
    @RedisLock(prefix = JedisKeyConstant.NotResubmit.DOCTOR_WITHDRAW_APPLY, noResubmit = true)
    @PostMapping("/api/wxapp/doctor/withdraw/apply")
    public JsonResult apply(@RequestBody DoctorWithdrawParam param){
        try {
            param.setClientIp(RequestUtil.getIp(request));
            doctorWithdrawService.addDoctorWithdraw(param);
        } catch (MpException e) {
            return fail(e.getErrorCode());
        }
        return success();
    }
}
