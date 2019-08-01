package com.vpu.mp.service.pojo.shop.market.freeshipping;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/31 10:46
 */
@Data
public class FreeShippingVo {

    private Integer   id;
    private String    name;
    private Timestamp startTime;
    private Timestamp endTime;
    private Byte      expireType;
    private Byte      status;
    private Integer   type;
    private Byte      level;
    private String    recommendGoodsId;
    private String    recommendCatId;
    private String    recommendSortId;

    private List<FreeShippingRuleVo> ruleList;


}
