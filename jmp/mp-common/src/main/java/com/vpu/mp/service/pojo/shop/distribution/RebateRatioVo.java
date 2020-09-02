package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

/**
 * @Author 常乐
 * @Date 2020-03-12
 */
@Data
public class RebateRatioVo {
    /**
     * 返利策略id
     */
    private Integer rebateId;
    /**
     * 是否自购返利 0：关闭，1：开启
     */
    private Byte selfPurchase;
    /**
     * 首单返利比例
     */
    private Double firstRatio;
    /**
     * 直接返利比例
     */
    private Double fanliRatio;
    /**
     * 间接返利比例
     */
    private Double rebateRatio;
    /**
     * 首单返利开关
     */
    private Byte firstRebate;
}
