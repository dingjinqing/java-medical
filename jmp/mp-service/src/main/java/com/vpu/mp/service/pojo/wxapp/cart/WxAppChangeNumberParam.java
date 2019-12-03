package com.vpu.mp.service.pojo.wxapp.cart;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @author 孔德成
 * @date 2019/10/17 11:34
 */
@Getter
@Setter
public class WxAppChangeNumberParam {

    private Integer productId;
    @Min(value = 0)
    private Integer cartNumber;
}
