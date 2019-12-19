package com.vpu.mp.service.pojo.shop.market.payaward;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 孔德成
 * @date 2019/12/19 15:44
 */
@Getter
@Setter
public class PayAwardOrderVo {

    /**
     * 奖品类型
     */
    private PayAwardContentBo payAwardContentBo;
    /**
     * 当前层级
     */
    private Integer currentAwardTimes;
    /**
     * 一共层级
     */
    private Integer payAwardSize=0;
}