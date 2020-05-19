package com.vpu.mp.service.pojo.shop.config.trade.third;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 第三方rep推送配置
 * @author 孔德成
 * @date 2020/5/19
 */
@Setter
@Getter
@ToString
public class ThirdErpPushParam {
    @NotNull
    @Min(0)
    @Max(1)
    private Byte action;
    @NotNull
    private String type;
}
