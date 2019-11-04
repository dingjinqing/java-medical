package com.vpu.mp.service.pojo.wxapp.activity.info.list;

import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsPrdForListInfo extends ActivityForListInfo {
    /**是否是默认规格0否1是*/
    private Boolean defaultPrd;
    /**最小值*/
    private BigDecimal minPrice;
    /**最大值*/
    private BigDecimal maxPrice;
}
