package com.vpu.mp.service.pojo.shop.market.integralconvert;

import com.vpu.mp.common.foundation.util.PageResult;

import lombok.Data;

/**
 * @author liangchen
 * @date 2020.06.10
 */
@Data
public class IntegralConvertScoreVo<T> extends PageResult<T> {
    private Integer scoreProportion;
}
