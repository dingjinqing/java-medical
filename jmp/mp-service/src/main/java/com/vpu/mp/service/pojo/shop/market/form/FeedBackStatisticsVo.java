package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;

import java.util.List;

/**
 * @author liufei
 * @date 2019/8/9
 * @description
 */
@Data
public class FeedBackStatisticsVo {
    private Integer participantsNum;
    private Integer submitNum;
    private Byte validityPeriod;
    private Byte status;
    List<FeedBackInnerVo> sexList;
    List<FeedBackInnerVo> slideList;
    List<FeedBackInnerVo> chooseList;
}
