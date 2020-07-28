package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.db.shop.tables.Department;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author 赵晓东
 * @description 医师端首页信息
 * @create 2020-07-28 13:47
 **/

@Data
public class DoctorMainShowVo {

    /**
     * 医师端首页消息提示
     */
    private DoctorMessageCountVo doctorMessageCountVo;

    /**
     * 医师端首页个人信息
     */
    private DoctorOneParam doctorOneParam;

    /**
     * 医师科室名称
     */
    private List<String> departmentName;


}
