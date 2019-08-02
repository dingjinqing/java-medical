package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/1 16:23
 */
@Data
public class GroupBuyAnalysisParam {
    private Integer id;

    private Timestamp startTime;
    private Timestamp endTime;

}
