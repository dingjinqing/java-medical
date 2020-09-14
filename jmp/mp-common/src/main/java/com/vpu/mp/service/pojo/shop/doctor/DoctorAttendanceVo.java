package com.vpu.mp.service.pojo.shop.doctor;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2020/9/14 16:55
 */
@Data
public class DoctorAttendanceVo {

    /**
     * 接诊量
     */
    private Integer receivingNumber;
    /**
     * 出勤率
     */
    private String doctorAttendanceRate;
    /**
     * 处方数
     */
    private Integer prescriptionNum;

}
