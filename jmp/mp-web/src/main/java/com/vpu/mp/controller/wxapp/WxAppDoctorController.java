package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAuthParam;
import com.vpu.mp.service.shop.doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 医师端
 * @Author 赵晓东
 * @Create 2020-07-22 14:15
 **/

@RestController
@RequestMapping("/api/wxapp/doctor")
public class WxAppDoctorController extends BaseController {


    @Autowired
    private DoctorService doctorService;

    /**
     * 医师认证接口
     * @param doctorAuthParam 认证医师姓名，手机号，医师医院唯一编码
     * @return JsonResult
     */
    @RequestMapping("/auth")
    @ResponseBody
    public JsonResult doctorAuth(DoctorAuthParam doctorAuthParam) {
        return this.success(doctorService.doctorAuth(doctorAuthParam));
    }
}