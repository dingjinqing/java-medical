package com.vpu.mp.service.pojo.wxapp.distribution;

import lombok.Data;

/**
 * @Auther 常乐
 * @Date 2019-12-10
 */
@Data
public class DistributorApplyDetailParam {
    public Integer id;
    /**
     * 用户ID
     */
    public Integer userId;
    /**
     * 审核状态 0：审核中；1：通过；2：拒绝
     */
    public Integer status;
}
