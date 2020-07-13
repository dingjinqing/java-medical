package com.vpu.mp.service.pojo.wxapp.distribution;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author 常乐
 * @Date 2020-05-20
 */
@Data
public class UserRebateVo {
    /**分销员ID*/
    private Integer userId;
    /**用户头像*/
    private String userAvatar;
    /**分销员昵称*/
    private String username;
    /**返利金额*/
    private BigDecimal FinalMoney;
}
