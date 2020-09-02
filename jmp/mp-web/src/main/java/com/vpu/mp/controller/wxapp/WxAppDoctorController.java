package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.department.DepartmentListVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAuthParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorMainShowVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.message.DoctorMainShowParam;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountVo;
import com.vpu.mp.service.pojo.shop.patient.PatientSmsCheckParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.message.UserMessageService;
import com.vpu.mp.service.shop.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.common.foundation.data.JsonResultCode.CODE_SUCCESS;
import static com.vpu.mp.common.foundation.data.JsonResultCode.DOCTOR_LOGIN_AUTH_ERROR;

/**
 * @Description 医师端
 * @Author 赵晓东
 * @Create 2020-07-22 14:15
 **/

@RestController
public class WxAppDoctorController extends WxAppBaseController {


    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserMessageService messageService;

    @Autowired
    private SmsService smsService;

    /**
     * 医师认证接口
     * @param doctorAuthParam 认证医师姓名，手机号，医师医院唯一编码
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/doctor/auth")
    public JsonResult doctorAuth(@RequestBody DoctorAuthParam doctorAuthParam) {
        doctorAuthParam.setUserId(wxAppAuth.user().getUserId());
        Integer doctorId = doctorService.doctorAuth(doctorAuthParam);
        // 如果医师id!=null 更新缓存
        if (doctorId != null) {
            wxAppAuth.updateUserType(doctorId);
            return success();
        } else {
            return fail(DOCTOR_LOGIN_AUTH_ERROR);
        }
    }

    /**
     * 发送验证码
     * @param param 电话号
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/doctor/send/check/code")
    public JsonResult sendCheckSms(@RequestBody @Validated PatientSmsCheckParam param){
        param.setUserId(wxAppAuth.user().getUserId());
        try {
            JsonResultCode jsonResultCode = smsService.checkIsOutOfSmsNum(wxAppAuth.user().getUserId(), "");
            if (!jsonResultCode.equals(CODE_SUCCESS)){
                return fail(jsonResultCode);
            }
            smsService.sendCheckSms(param);
        } catch (MpException e) {
            return fail();
        }
        return success();
    }

    /**
     * 获取当前医师登录的信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/doctor/auth/info")
    public JsonResult getDoctorAuthInfo(){
        WxAppSessionUser user=wxAppAuth.user();
        DoctorOneParam doctor= doctorService.getOneInfo(user.getDoctorId());
        return success(doctor);
    }

    /**
     * 医师端首页信息展示 消息统计和医师个人信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/doctor/main")
    public JsonResult doctorMainShow(@RequestBody DoctorMainShowParam doctorMainShowParam){
        // 获取缓存中当前用户信息
        WxAppSessionUser user = wxAppAuth.user();
        // 获取页面消息统计信息
        DoctorMessageCountVo doctorMessageCountVo =
            messageService.countDoctorMessage(user.getDoctorId(), doctorMainShowParam);
        // 获取医师首页个人信息
        DoctorOneParam oneInfo = doctorService.getOneInfo(user.getDoctorId());
        DoctorMainShowVo doctorMainShowVo = new DoctorMainShowVo();
        //添加医师职称
        String duty = doctorService.selectDoctorTitle(oneInfo);
        doctorMainShowVo.setDoctorTitle(duty);
        FieldsUtil.assign(oneInfo, doctorMainShowVo);
        doctorMainShowVo.setDoctorMessageCountVo(doctorMessageCountVo);
        // 获取医师所属科室列表
        List<DepartmentListVo> departmentListVos =
            doctorService.selectDepartmentsByDoctorId(user.getDoctorId());
        List<String> list = new ArrayList<>();
        for (DepartmentListVo departmentListVo : departmentListVos){
            list.add(departmentListVo.getName());
        }
        doctorMainShowVo.setDepartmentName(list);
        return super.success(doctorMainShowVo);
    }
}
