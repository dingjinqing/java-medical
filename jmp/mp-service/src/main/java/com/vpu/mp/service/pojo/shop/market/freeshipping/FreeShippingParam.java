package com.vpu.mp.service.pojo.shop.market.freeshipping;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/29 17:39
 */
@Data
public class FreeShippingParam {
    private Integer   id;
    private String    name;
    private Timestamp startTime;
    private Timestamp endTime;
    /**
     *  有效期 0 固定期限 1永久有效
     */
    private Byte      expireType;
    /**
     * 条件 0全部 1部分
     */
    private Integer   type;
    /**
     * 优先级 最大100
     */
    private Byte      level;
    private String    recommendGoodsId;
    private String    recommendCatId;
    private String    recommendSortId;

    private List<FreeShippingRuleParam> ruleList;


}
