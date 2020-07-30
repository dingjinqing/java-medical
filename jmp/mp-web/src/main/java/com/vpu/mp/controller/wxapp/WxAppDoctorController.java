package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.controller.BaseController;
import com.vpu.mp.db.shop.tables.Message;
import com.vpu.mp.service.pojo.shop.department.DepartmentListVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAuthParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorMainShowVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 医师端
 * @Author 赵晓东
 * @Create 2020-07-22 14:15
 **/

@RestController
@RequestMapping("/api/wxapp/doctor")
public class WxAppDoctorController extends WxAppBaseController {


    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MessageService messageService;

    /**
     * 医师认证接口
     * @param doctorAuthParam 认证医师姓名，手机号，医师医院唯一编码
     * @return JsonResult
     */
    @RequestMapping("/auth")
    public JsonResult doctorAuth(@RequestBody DoctorAuthParam doctorAuthParam) {
        doctorAuthParam.setUserId(wxAppAuth.user().getUserId());
        doctorAuthParam.setToken(wxAppAuth.user().getToken());
        Byte doctorAuth = doctorService.doctorAuth(doctorAuthParam);
        if (DelFlag.NORMAL_VALUE.equals(doctorAuth)) {
            return success(doctorAuth);
        } else {
            return fail();
        }
    }

    /**
     * 获取当前医师登录的信息
     * @return JsonResult
     */
    @PostMapping("/auth/info")
    public JsonResult getDoctorAuthInfo(){
        WxAppSessionUser user=wxAppAuth.user();
        DoctorOneParam doctor= doctorService.getOneInfo(user.getDoctorId());
        return success(doctor);
    }

    /**
     * 医师端首页信息展示 消息统计和医师个人信息
     * @return JsonResult
     */
    @RequestMapping("/main")
    public JsonResult doctorMainShow(){
        Integer doctorId = wxAppAuth.user().getDoctorId();
        DoctorMessageCountVo doctorMessageCountVo = messageService.countDoctorMessage(doctorId);
        DoctorOneParam oneInfo = doctorService.getOneInfo(doctorId);
        DoctorMainShowVo doctorMainShowVo = new DoctorMainShowVo();
        FieldsUtil.assign(oneInfo, doctorMainShowVo);
        doctorMainShowVo.setDoctorMessageCountVo(doctorMessageCountVo);
        List<DepartmentListVo> departmentListVos = doctorService.selectDepartmentsByDoctorId(doctorId);
        List<String> list = new ArrayList<>();
        for (DepartmentListVo departmentListVo : departmentListVos){
            list.add(departmentListVo.getName());
        }
        doctorMainShowVo.setDepartmentName(list);
        return super.success(doctorMainShowVo);
    }
}