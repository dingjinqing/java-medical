package com.vpu.mp.service.pojo.wxapp.goods.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年11月11日
 */
@Data
public class GoodsActivityBaseMp {
    protected Integer activityId;
    protected Byte activityType;
    /**活动对应的价格*/
    @JsonIgnore
    protected BigDecimal activityPrice;
}
