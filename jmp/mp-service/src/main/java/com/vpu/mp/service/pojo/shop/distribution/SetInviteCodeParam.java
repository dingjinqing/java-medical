package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

/**
 * @Author 常乐
 * @Date 2020-02-13
 */
@Data
public class SetInviteCodeParam {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 邀请码
     */
    private String InviteCode;
}
