package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.RequestUtil;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawListParam;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawUpdateParam;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@RestController
public class AdminDoctorWithdrawController extends AdminBaseController{

    @PostMapping("/api/admin/doctor/withdraw/list")
    public JsonResult getPageList(@RequestBody DoctorWithdrawListParam param){
        PageResult<DoctorWithdrawVo> result=shop().doctorWithdrawService.getPageList(param);
        return success(result);
    }

    /**
     * 提现审核
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctor/withdraw/audit")
    public JsonResult audit(@RequestBody DoctorWithdrawUpdateParam param){
        try {
            param.setClientIp(RequestUtil.getIp(request));
            shop().doctorWithdrawService.audit(param);
        } catch (MpException e) {
            return fail(e.getErrorCode());
        }
        return success();
    }
}
