package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 用于小程序商品列表展示时的商品活动信息类
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityForListInfo extends ActivityBaseInfo{
    /**活动对应的价格*/
    @JsonIgnore
    protected BigDecimal activityPrice;
}
