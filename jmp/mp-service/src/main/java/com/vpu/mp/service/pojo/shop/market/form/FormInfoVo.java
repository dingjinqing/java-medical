package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/7
 * @description
 */
@Data
public class FormInfoVo {
    private String pageName;
    private Timestamp createTime;
    /**反馈数量*/
    private Integer submitNum;
    private Byte status;
    /**有效期*/
    private Byte validityPeriod;
}
