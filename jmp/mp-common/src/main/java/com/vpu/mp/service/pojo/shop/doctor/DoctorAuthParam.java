package com.vpu.mp.service.pojo.shop.doctor;

import lombok.Data;

/**
 * @Description
 * @Author 赵晓东
 * @Create 2020-07-22 14:25
 **/

@Data
public class DoctorAuthParam {
    /**
     * 医师姓名
     */
    private String doctorName;
    /**
     * 医师电话
     */
    private String mobile;
    /**
     * 医师医院唯一编码
     */
    private String hospitalCode;
    /**
     * 当前用户id
     */
    private Integer userId;
    /**
     * 用户缓存信息
     */
    private String token;
}
