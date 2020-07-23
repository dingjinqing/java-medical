package com.vpu.mp.service.pojo.wxapp.medical.im.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 小程序会话列表
 * @author 李晓冰
 * @date 2020年07月23日
 */
@Data
public class ImSessionListVo {
    /**会话id*/
    private Integer sessionId;
    /**订单号*/
    private String orderSn;
    /**医生id*/
    private Integer doctorId;
    /**科室id*/
    private Integer departmentId;
    /**科室名*/
    private String departmentName;
    /**患者id*/
    private Integer patientId;
    /**患者名*/
    private String patientName;
    /**用户id*/
    private Integer userId;
    /**会话状态*/
    private Byte sessionStatus;

    private Timestamp createTime;
}
