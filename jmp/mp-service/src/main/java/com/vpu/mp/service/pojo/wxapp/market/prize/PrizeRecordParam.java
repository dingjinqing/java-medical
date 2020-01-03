package com.vpu.mp.service.pojo.wxapp.market.prize;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 我的奖品
 *
 * @author 孔德成
 * @date 2020/1/3 9:39
 */
@Getter
@Setter
public class PrizeRecordParam extends BasePageParam {
    @NotNull
    private Byte status;
}
