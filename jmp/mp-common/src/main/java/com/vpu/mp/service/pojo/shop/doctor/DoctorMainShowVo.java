package com.vpu.mp.service.pojo.shop.doctor;

import lombok.Data;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-28 13:47
 **/

@Data
public class DoctorMainShowVo {

    /**
     * 医师姓名
     */
    private String name;

    /**
     * 医师头像
     */
    private String url;

    /**
     * 聘任职务
     */
    private String duty;

    /**
     * 注册医院
     */
    private String registerHospital;


}
