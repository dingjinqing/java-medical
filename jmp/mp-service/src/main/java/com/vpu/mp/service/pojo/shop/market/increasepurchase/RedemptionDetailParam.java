package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import lombok.Data;

/**
 * @author liufei
 * @date 2019/8/15
 * @description
 */
@Data
public class RedemptionDetailParam {
    private Integer activityId;
    private String nickName;
    private String phoneNumber;
    private Integer redemptionNum;

    private Integer currentPage;
    private Integer pageRows;
}
