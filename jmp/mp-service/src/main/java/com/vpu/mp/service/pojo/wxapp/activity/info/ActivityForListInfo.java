package com.vpu.mp.service.pojo.wxapp.activity.info;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityForListInfo extends ActivityBaseInfo{
    protected BigDecimal activityPrice;
}
