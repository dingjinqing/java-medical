package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 分销员审核列表入参类
 * @author 常乐
 * 2019年9月20日
 */
@Data
public class DistributorCheckListParam {
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String username;

    /**
     * 申请开始时间
     */
    private Timestamp startTime;

    /**
     * 申请结束时间
     */
    private Timestamp endTime;

    private Integer currentPage;
    private Integer pageRows;
    /**
     * tab状态 0：待审核；1：审核通过；2：未通过
     */
    private Byte nav;
}
